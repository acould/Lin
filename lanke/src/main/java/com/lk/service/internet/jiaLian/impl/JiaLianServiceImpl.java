package com.lk.service.internet.jiaLian.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.jiaLian.AuditResult;
import com.lk.entity.jiaLian.ShopInfo;
import com.lk.entity.jiaLian.ShopPic;
import com.lk.service.internet.StoreLog.StoreLogService;
import com.lk.service.system.store.StoreManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.JiaLian.JiaLianUtils;
import com.lk.util.JiaLian.YikeConfig;
import com.lk.entity.system.User;
import com.lk.service.internet.jiaLian.JiaLianService;
import com.lk.service.internet.newPictures.NewPicturesService;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.util.*;
import com.lk.wechat.util.SmsLogUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author myq
 * @description 嘉联支付业务——实现类
 * @create 2019-04-29 15:37
 */
@Service
public class JiaLianServiceImpl implements JiaLianService {

    private static final Logger log = LoggerFactory.getLogger(JiaLianServiceImpl.class);
    private static String mapperName = "JiaLianShopMapper.";
    private static String mapperName2 = "InternetMatchStoreMapper.";
    private static Map<String, String> urlMap = new HashMap<>();

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Resource(name = "smslogService")
    private SmslogManager smslogService;

    @Autowired
    private NewPicturesService newPicturesService;
    @Autowired
    private StoreLogService storeLogService;
    @Resource(name = "messageService")
    private MessageManager messageService;
    @Resource(name = "storeService")
    private StoreManager storeService;



    @Override
    public int save(PageData pd) throws Exception {
        return (int) dao.save(mapperName + "save", pd);
    }

    @Override
    public int saveMatchStore(PageData pd) throws Exception {
        return (int) dao.save(mapperName2 + "save", pd);
    }

    @Override
    public int deleteMatchStoreByStoreId(PageData pd) throws Exception{
        return (int) dao.delete(mapperName2 + "deleteMatchStoreByStoreId", pd);
    }

    @Override
    public int deleteMatchStoreByForeignId(PageData pd) throws Exception{
        return (int) dao.delete(mapperName2 + "deleteMatchStoreByForeignId", pd);
    }

    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete(mapperName + "delete", id);
    }

    @Override
    public int edit(PageData pd) throws Exception {
        return (int) dao.update(mapperName + "edit", pd);
    }

    @Override
    public int editByShopNo(PageData pd) throws Exception{
        return (int) dao.update(mapperName + "editByShopNo", pd);
    }

    @Override
    public PageData findById(String id) throws Exception {
        return (PageData) dao.findForObject(mapperName + "findById", id);
    }

    @Override
    public PageData findByStoreId(String id)  throws Exception {
        return (PageData) dao.findForObject(mapperName + "findByStoreId", id);
    }

    @Override
    public PageData findByShopNo(String shopNo) throws Exception{
        return (PageData) dao.findForObject(mapperName + "findByShopNo", shopNo);
    }

    @Override
    public List<PageData> dataListpage(Page page) throws Exception{
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }

    @Override
    public PageData findPaymentByStoreId(String store_id) throws Exception{
        return (PageData) dao.findForObject(mapperName + "findPaymentByStoreId", store_id);
    }

    @Override
    public PageData findByPhone(PageData pd) throws Exception{
        return (PageData) dao.findForObject(mapperName + "findByPhone", pd);
    }

    @Override
    public List<PageData> getOpenList(String internet_id) throws Exception {
        return (List<PageData>) dao.findForList(mapperName2 + "getOpenList", internet_id);
    }

    @Override
    public Message saveShop(PageData pd) throws Exception {

        String store_id = pd.getString("store_id");
        String id = StringUtil.get32UUID();

        PageData pdShop = this.findByStoreId(store_id);
        if(StringUtil.isNotEmpty(pdShop)){
            //状态：1已开通；2待申请；3申请中；4未通过
            if(pdShop.get("status").toString().equals("3")){
                return Message.error("申请中的资料无法修改");
            }
            id = pdShop.getString("id");
        }

        //处理数据
        pd.put("user_name", pd.getString("mobile"));//默认登录名为手机号，且初始密码为123456
        if(pd.getString("mobile").equals("18222956710")){
            pd.put("user_name", "myq_" + new Date().getTime());//测试的时候数据
        }
        pd.put("shop_merchant_no", YikeConfig.shop_merchant_no);//代理商编号
        pd.put("net_type", "3");//入网类型，默认小微商户
        pd.put("rate", "0.4");//费率默认0.4
        pd.put("phone", pd.getString("mobile"));



        //save:onlySave,submit
        if (pd.getString("save").equals("onlySave")) {
            if(StringUtil.isEmpty(pd.getString("id"))){
                pd.put("status", 2);
            }
        }else if(pd.getString("save").equals("submit")) {

            //需要判断手机号和身份证
            JSONObject result = CommonJudge.judgePhone(pd.getString("mobile"));
            if (result.getString("result").equals("false")) {
                return Message.error(result.getString("message"));
            }
            result = CommonJudge.judgeCard(pd.getString("cert_no"));
            if (result.getString("result").equals("false")) {
                return Message.error(result.getString("message"));
            }

            if (!pd.getString("mobile").equals("18222956710")) {
                //需要验证码
                PageData pdSms = new PageData();
                pdSms.put("phone", pd.getString("mobile"));
                pdSms = smslogService.findLastMsg(pdSms);
                if (StringUtil.isEmpty(pdSms) || !pdSms.getString("CODE").equals(pd.getString("code"))) {
                    return Message.error("请输入正确的验证码").addData("mobile", pd.getString("mobile"));
                }
            }
        }

        //新增或编辑商户信息
        if(StringUtil.isEmpty(pd.getString("id"))){
            PageData pdShopInfo = this.findByPhone(pd);
            //判断手机号唯一
            if(StringUtil.isNotEmpty(pdShopInfo)){
                return Message.error("该手机号已提交过信息");
            }

            if(pd.getString("mobile").equals("18222956710")){
                pd.put("user_name", "myq_" + new Date().getTime());
            }
            pd.put("id", id);
            pd.put("create_time", Tools.date2Str(new Date()));
            pd.put("update_time", Tools.date2Str(new Date()));

            save(pd);

            //保存关联表
            PageData pdMatchStore = new PageData();
            pdMatchStore.put("foreign_id", pd.getString("id"));
            pdMatchStore.put("type", 1);
            pdMatchStore.put("store_id", store_id);
            this.saveMatchStore(pdMatchStore);
        }else{
            PageData pdShopInfo = this.findByPhone(pd);
            //判断手机号唯一
            if(StringUtil.isNotEmpty(pdShopInfo) && !pdShopInfo.getString("id").equals(pd.getString("id"))){
                return Message.error("该手机号已提交过信息");
            }

            if(StringUtil.isNotEmpty(pdShopInfo) && pdShopInfo.get("status").toString().equals("1")){
                //已开通的，不支持修改手机号
                pd.remove("mobile");
            }


            pd.put("id", id);
            pd.put("update_time", Tools.date2Str(new Date()));

            edit(pd);

            PageData pdd = this.findById(id);
            pd.put("shop_no", pdd.getString("shop_no"));
        }

        if(!pd.getString("save").equals("edit")){
            //新增或编辑图片
            String[] sortList = getByNetType(pd.getString("net_type"), pd.getString("account_type"));
            saveImg(pd, 5, sortList);
        }


        //如果是提交，就通过接口，将信息上传给嘉联
        if(pd.getString("save").equals("submit")){
            //跑线程
            new Thread(new submit2JL(pd, store_id)).start();
        }

        return Message.ok().addData("id", id);
    }


    /**
     * 保存图片
     * @param pd（store_id,id,pre,sort类型的图片）
     * @throws Exception
     */
    public void saveImg(PageData pd, int type, String[] sortList) throws Exception{
        PageData pdImg = new PageData();
        pdImg.put("foreign_id", pd.getString("id"));
        pdImg.put("store_id", pd.getString("store_id"));
        pdImg.put("type", type);//5嘉联支付 6赛事

        List<PageData> picList = new ArrayList<>();
        if(type == 5){
            picList = newPicturesService.findByStoreIdAndType(pdImg);
        }else if(type == 6){
            picList = newPicturesService.findByForeignIdAndType(pdImg);
        }


        for (int j = 0; j < sortList.length; j++) {
            String sort = sortList[j];
            if (StringUtil.isNotEmpty(pd.getString(sort)) && pd.getString(sort).startsWith("data:image")) {

                String flag = "add";
                for (int i = 0; i < picList.size(); i++) {
                    PageData pdPic = picList.get(i);
                    if (sort.equals(pdPic.getString("sort"))) {
                        //存在即修改
                        flag = "edit";
                        pdPic.put("flag", flag);
                        pdPic = putImg(pd, pdPic);
                        newPicturesService.edit(pdPic);
                    }
                }
                if (flag.equals("add")) {
                    PageData pdPic = new PageData();
                    pdPic.put("foreign_id", pd.getString("id"));
                    pdPic.put("store_id", pd.getString("store_id"));
                    pdPic.put("create_time", Tools.date2Str(new Date()));
                    pdPic.put("sort", sort);
                    pdPic.put("type", type);
                    pdPic.put("flag", flag);
                    pdPic = putImg(pd, pdPic);
                    newPicturesService.save(pdPic);
                }
            }
        }
        return;
    }


    /**
     * 保存图片到本地
     * @param pd（flag,sort类型的图片）
     * @param pdImg（sort类型的key值字符串）
     * @return
     * @throws Exception
     */
    public PageData putImg(PageData pd, PageData pdImg) throws Exception{
        if(pdImg.getString("flag").equals("add")){
            pdImg.put("id", StringUtil.get32UUID());
        }

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        //上传到本地
        String file = pd.getString(pdImg.getString("sort"));
        String suffixName = "";
        if(file.contains(PublicManagerUtil.BASE64_JPEG)){
            suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
        }else if(file.contains(PublicManagerUtil.BASE64_PNG)){
            suffixName = PublicManagerUtil.SUFFIXNAME_PNG;
        }else{
            suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
        }

        String fileName = new Date().getTime() + suffixName;
        String path = user.getINTENET_ID() + "/" + pd.getString("store_id") + "/" + fileName ;
        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
        Tools.pic(file,filePath);
        pdImg.put("name", fileName);
        pdImg.put("path", path);
        pdImg.put("url", pd.getString("pre") + Const.FILEPATHIMG + path);

        urlMap.put(pdImg.getString("sort"), path);
        return pdImg;
    }



    @Override
    public Message delShop(PageData pd) throws Exception {
        int num = this.delete(pd.getString("id"));
        if(num > 0){
            return Message.ok("删除成功");
        }
        return Message.error("数据已删除");
    }

    @Override
    public Message delImg(PageData pd) throws Exception{
        pd.put("foreign_id", pd.getString("id"));
        int num = newPicturesService.delByForeignIdAndSort(pd);
        if(num > 0){
            return Message.ok("删除成功");
        }
        return Message.error("图片已删除");
    }

    @Override
    public Message shareShopNo(PageData pd) throws Exception {

        PageData pdShop = this.findByShopNo(pd.getString("shop_no"));
        if(pdShop == null){
            return Message.error("请输入您已有的商户号");
        }
        User user = Jurisdiction.getSessionUser().getUser();//得到用户


        pdShop.put("STORE_ID", pdShop.getString("store_id"));
        PageData pdStore = storeService.findById(pdShop);
        if(!user.getINTENET_ID().equals(pdStore.getString("INTERNET_ID"))){
            return Message.error("该商户号不是您名下的");
        }

        PageData pdMatchStore = this.findByStoreId(pd.getString("store_id"));
        if(pdMatchStore != null){
            if(pdMatchStore.get("status").toString().equals("1")){
                return Message.error("该门店已存在商户信息");
            }
            pdMatchStore.put("type", 1);
            this.deleteMatchStoreByStoreId(pdMatchStore);
        }

        //保存关联表
        pdMatchStore = new PageData();
        pdMatchStore.put("foreign_id", pdShop.getString("id"));
        pdMatchStore.put("type", 1);
        pdMatchStore.put("store_id", pd.getString("store_id"));
        this.saveMatchStore(pdMatchStore);

        return Message.ok("新增数据失败");
    }

    @Override
    public LayMessage getList(PageData pd, Page page) throws Exception {


        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);


        return LayMessage.ok(list.size(), list);
    }


    @Override
    public List<PageData> getAuditInfo(String id) throws Exception{
        PageData pd = new PageData();
        pd.put("foreign_id", id);
        pd.put("type", 3);//嘉联支付审核
        return storeLogService.findByForeignId(pd);
    }

    @Override
    public Message updateAuditStatus(PageData pd) throws Exception{
        PageData pdShop = this.findById(pd.getString("id"));

        if(StringUtil.isEmpty(pdShop)){
            return Message.error("找不到数据");
        }

        if(StringUtil.isNotEmpty(pdShop) && pdShop.get("status").toString().equals("3")){
            //申请中信息时，去更新
            Message m = JiaLianUtils.selAudit(pdShop.getString("shop_no"));
//            if(m.getErrcode() == 0 && m.getData().get("status").toString().equals("1")){
//                addOkLogById(pdShop.getString("id"), pdShop.getString("store_id"), pdShop.getString("phone"), pdShop.getString("merch_name"));
//            }else if(m.getErrcode() == 0 && m.getData().get("status").toString().equals("2")){
//                addErrorLogById(pd.getString("id"), pdShop.getString("store_id"), m.getErrmsg(),pd.getString("phone"),pd.getString("merch_name"));
//            }
            return m;
        }
        return Message.error("无需更新数据");
    }

    /**
     * 通过入网类型，获取图片字符集
     * @param net_type
     * @param account_type
     * @return
     */
    private String[] getByNetType(String net_type, String account_type){
        //结算账户类型，0：对私，1：对公
        if(net_type.equals("1") && account_type != null && account_type.equals("0")){
            net_type = "1_1";
        }
        if(net_type.equals("1") && account_type != null && account_type.equals("1")){
            net_type = "1_2";
        }
        //入网类型  1：营业执照入网2：租赁商户入网3：小微商户入网
        switch (net_type){
            case "1_1":
                return YikeConfig.yyzzds;
            case "1_2":
                return YikeConfig.yyzzdg;
            case "2":
                return YikeConfig.zlsh;
            case "3":
                return YikeConfig.xwsh;
        }
        return null;
    }

    @Override
    public boolean handleShopAudit(JSONObject obj){
        if(obj == null || StringUtil.isEmpty(obj.get("code"))){
            return false;
        }
        try{
            String code = obj.get("code").toString();
            String status = obj.getJSONObject("data").get("status").toString();
            String merchNo = obj.getJSONObject("data").get("merchNo").toString();

            PageData pdShop = this.findByShopNo(merchNo);
            if(pdShop == null){
                log.info(merchNo + "：：该商户号信息不存在");
                return false;
            }else if(pdShop.get("status").toString().equals("1")){
                log.info(merchNo + "：：该商户号审核已通过");
                return true;
            }

            if(code.equals("R0001") && status.equals("1")){
                //审核通过,
                //商户结算类型-审核成功则返回
                //0：T+0
                //1：T+1
                //3：D+1
                String settleType = obj.getJSONObject("data").get("settleType").toString();
                String shopSecret = obj.getJSONObject("data").getJSONObject("shopInform").getString("shopSecret");

                //修改状态
                PageData pd = new PageData();
                pd.put("shop_no", merchNo);
                pd.put("status", 1);//审核通过
                pd.put("settle_type", settleType);
                pd.put("shop_secret", shopSecret);//商户密钥
                pd.put("update_time", Tools.date2Str(new Date()));
                this.editByShopNo(pd);

                if(!pdShop.get("status").toString().equals("1")){
                    //记录日志
                    PageData pdLog = new PageData();
                    pdLog.put("store_id", pdShop.getString("store_id"));
                    pdLog.put("foreign_id", pdShop.getString("id"));
                    pdLog.put("type", 3);//嘉联支付
                    pdLog.put("checker", "第三方审核");
                    pdLog.put("is_checked", 1);//审核通过
                    pdLog.put("check_reason", "审核通过");
                    pdLog.put("check_time", Tools.date2Str(new Date()));
                    pdLog.put("apply_time", Tools.date2Str(new Date()));
                    storeLogService.save(pdLog);

                    PageData pdStore = new PageData();
                    pdStore.put("STORE_ID", pdShop.getString("store_id"));
                    pdStore = storeService.findById(pdStore);

                    //保存未读消息提醒
                    PageData pdMessage = new PageData();
                    pdMessage.put("id", StringUtil.get32UUID());
                    pdMessage.put("message_id", pdShop.getString("id"));
                    pdMessage.put("internet_id", pdStore.getString("INTERNET_ID"));
                    pdMessage.put("title", "嘉联支付开通成功");
                    pdMessage.put("type", "jl_pay");
                    pdMessage.put("content", "您的企业"+pdStore.getString("STORE_NAME")+"，在网晶揽客申请的在线支付已正式开通，详情请查看");
                    pdMessage.put("announce_time", Tools.date2Str(new Date()));
                    pdMessage.put("state", "0");//未读
                    messageService.save(pdMessage);

                    //资料审核通过，给用户发送短信通知
                    SmsLogUtil.sendOpenCondition(pdShop.getString("phone"), pdShop.getString("merch_name"), "material_success");
                }
            }

            if(code.equals("R0001") && status.equals("0")){
                String reason = obj.getJSONObject("data").get("reason").toString();
                //审核失败
                //修改状态
                PageData pd = new PageData();
                pd.put("shop_no", merchNo);
                pd.put("status", 4);//审核不通过
                pd.put("update_time", Tools.date2Str(new Date()));
                this.editByShopNo(pd);

                if(reason.contains("商户已入驻")){
                    reason += "（"+pdShop.getString("phone")+"手机号已存在）";
                }

                if(!pdShop.get("status").toString().equals("4")){
                    //记录日志
                    PageData pdLog = new PageData();
                    pdLog.put("store_id", pdShop.getString("store_id"));
                    pdLog.put("foreign_id", pdShop.getString("id"));
                    pdLog.put("type", 3);//嘉联支付
                    pdLog.put("checker", "第三方审核");
                    pdLog.put("is_checked", 2);//审核不通过
                    pdLog.put("check_reason", reason);
                    pdLog.put("check_time", Tools.date2Str(new Date()));
                    pdLog.put("apply_time", Tools.date2Str(new Date()));
                    storeLogService.save(pdLog);

                    PageData pdStore = new PageData();
                    pdStore.put("STORE_ID", pdShop.getString("store_id"));
                    pdStore = storeService.findById(pdStore);

                    PageData pdMessage = new PageData();
                    pdMessage.put("id", StringUtil.get32UUID());
                    pdMessage.put("message_id", pdShop.getString("id"));
                    pdMessage.put("internet_id", pdStore.getString("INTERNET_ID"));
                    pdMessage.put("title", "嘉联支付开通—资料审核不通过");
                    pdMessage.put("type", "jl_pay");
                    pdMessage.put("content", "您的企业"+pdStore.getString("STORE_NAME")+"，资料审核未通过。原因如下："+reason+"。<br>请尽快修改资料，重新提交！");
                    pdMessage.put("announce_time", Tools.date2Str(new Date()));
                    pdMessage.put("state", "0");//未读
                    messageService.save(pdMessage);

                    //资料审核不通过，给用户发送短信通知
                    SmsLogUtil.sendOpenCondition(pdShop.getString("phone"), pdShop.getString("merch_name"), "material_false");
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 提交给嘉联
     */
    class submit2JL implements Runnable {
        private String store_id;
        private PageData pd;

        public submit2JL(PageData pd, String store_id) {
            this.store_id = store_id;
            this.pd = pd;
        }
        @Override
        public void run() {
            try {
                log.info("嘉联——开始商户进件");
                ShopInfo shopInfo = putShopInfo(pd);
                 boolean b = StringUtil.isEmpty(shopInfo.getShop_no());
                //提交商户信息(新增或修改)
                if(StringUtil.isEmpty(shopInfo.getShop_no())){
                    getshopInfo(pd,store_id,shopInfo);
                    //新增提交
                    Message message = JiaLianUtils.shopReq(shopInfo);
                    if(message.getErrcode() != 0){
                        //提交商户信息返回错误, 添加日志
                        addErrorLogById(pd.getString("id"), store_id, message.getErrmsg(),pd.getString("mobile"),pd.getString("merch_name"));
                        return ;
                    }
                    //提交成功后，返回商户号
                    PageData pdShop = new PageData();
                    pdShop.put("id", pd.getString("id"));
                    pdShop.put("shop_no", message.getData().get("shop_no"));
                    pdShop.put("status", 3);//审核中
                    pdShop.put("update_time", Tools.date2Str(new Date()));
                    edit(pdShop);
                }else{
                    getshopInfo(pd,store_id,shopInfo);
                    List<JSONObject> listp = new ArrayList<>();
                    System.out.println(shopInfo.getPicList());
                    for (int i = 0; i < shopInfo.getPicList().size(); i++) {
                        JSONObject o = new JSONObject();
                        o.put("picType", shopInfo.getPicList().get(i).getPicType());
                        o.put("picPath", shopInfo.getPicList().get(i).getPicPath());
                        listp.add(o);
                    }
                    JSONObject param = JSONObject.fromObject(shopInfo);
                    param.remove("picList");
                    param.put("picList", listp);
                    //修改提交
                    Message message = JiaLianUtils.updateShopReq(shopInfo);

                    if(message.getErrcode() != 0){
                        //提交商户信息返回错误, 添加日志
                        addErrorLogById(pd.getString("id"), store_id, message.getErrmsg(),pd.getString("mobile"),pd.getString("merch_name"));

                        if(message.getErrmsg().equals("商户审核中，无法修改！！！")){
                            PageData pdShop = new PageData();
                            pdShop.put("id", pd.getString("id"));
                            pdShop.put("status", 3);//审核中
                            pdShop.put("update_time", Tools.date2Str(new Date()));
                            edit(pdShop);
                        }
                        return ;
                    }
                    //提交成功后，返回商户号
                    PageData pdShop = new PageData();
                    pdShop.put("id", pd.getString("id"));
                    pdShop.put("status", 3);//审核中
                    pdShop.put("update_time", Tools.date2Str(new Date()));
                    edit(pdShop);
                }

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("进阶异常：：：：");
            }
        }
    }

    private void addErrorLogById(String id, String store_id, String errmsg, String phone, String merch_name) throws Exception{
        //修改状态
        PageData pdShop = new PageData();
        pdShop.put("id", id);
        pdShop.put("status", 4);//审核不通过
        pdShop.put("update_time", Tools.date2Str(new Date()));
        edit(pdShop);

        if(errmsg.contains("商户已入驻")){
            errmsg += "（"+phone+"手机号已存在）";
        }

        //记录日志
        PageData pdLog = new PageData();
        pdLog.put("store_id", store_id);
        pdLog.put("foreign_id", id);
        pdLog.put("type", 3);//嘉联支付
        pdLog.put("checker", "第三方审核");
        pdLog.put("is_checked", 2);//审核不通过
        pdLog.put("check_reason", errmsg);
        pdLog.put("check_time", Tools.date2Str(new Date()));
        pdLog.put("apply_time", Tools.date2Str(new Date()));
        storeLogService.save(pdLog);

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        //保存资料审核不通过消息提醒
        PageData pdMessage = new PageData();
        pdMessage.put("id", StringUtil.get32UUID());
        pdMessage.put("message_id", id);
        pdMessage.put("internet_id", user.getINTENET_ID());
        pdMessage.put("title", "嘉联支付开通—资料审核不通过");
        pdMessage.put("type", "jl_pay");
        pdMessage.put("content", "您的企业"+merch_name+"，资料审核未通过。原因如下："+errmsg+"。<br>请尽快修改资料，重新提交！");
        pdMessage.put("announce_time", Tools.date2Str(new Date()));
        pdMessage.put("state", "0");//未读
        messageService.save(pdMessage);

        //资料审核不通过，给用户发送短信通知
        SmsLogUtil.sendOpenCondition(phone, merch_name, "material_false");

    }


    private void addOkLogById(String id, String store_id, String phone, String merch_name) throws Exception{
        //修改状态
        PageData pdShop = new PageData();
        pdShop.put("id", id);
        pdShop.put("status", 1);//审核通过
        pdShop.put("update_time", Tools.date2Str(new Date()));
        edit(pdShop);

        //记录日志
        PageData pdLog = new PageData();
        pdLog.put("store_id", store_id);
        pdLog.put("foreign_id", id);
        pdLog.put("type", 3);//嘉联支付
        pdLog.put("checker", "第三方审核");
        pdLog.put("is_checked", 1);//审核通过
        pdLog.put("check_reason", "审核通过");
        pdLog.put("check_time", Tools.date2Str(new Date()));
        pdLog.put("apply_time", Tools.date2Str(new Date()));
        storeLogService.save(pdLog);

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        //保存未读消息提醒
        PageData pdMessage = new PageData();
        pdMessage.put("id", StringUtil.get32UUID());
        pdMessage.put("message_id", id);
        pdMessage.put("internet_id", user.getINTENET_ID());
        pdMessage.put("title", "嘉联支付开通成功");
        pdMessage.put("type", "jl_pay");
        pdMessage.put("content", "您的企业"+merch_name+"，在网晶揽客申请的在线支付已正式开通，详情请查看");
        pdMessage.put("announce_time", Tools.date2Str(new Date()));
        pdMessage.put("state", "0");//未读
        messageService.save(pdMessage);



        //资料审核不通过，给用户发送短信通知
        SmsLogUtil.sendOpenCondition(phone, merch_name, "material_success");

    }

    private static ShopInfo putShopInfo(PageData pd){

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_no(pd.getString("shop_no"));
        shopInfo.setShop_merchant_no(pd.getString("shop_merchant_no"));
        shopInfo.setCall_back_url(pd.getString("call_back_url"));
        shopInfo.setNet_type(pd.getString("net_type"));
        shopInfo.setUser_name(pd.getString("user_name"));
        shopInfo.setMerch_name(pd.getString("merch_name"));
        shopInfo.setShop_contact_name(pd.getString("shop_contact_name"));

        shopInfo.setCert_no(pd.getString("cert_no"));
        shopInfo.setCert_start_date(pd.getString("cert_start_date"));
        shopInfo.setCert_expire_date(pd.getString("cert_expire_date"));
        shopInfo.setCustomer_service_phone(pd.getString("customer_service_phone"));
        shopInfo.setMobile(pd.getString("mobile"));
        shopInfo.setEmail(pd.getString("email"));
        shopInfo.setLicense_no(pd.getString("license_no"));
        shopInfo.setDet_address(pd.getString("det_address"));
        shopInfo.setProv_code(pd.getString("prov_code"));
        shopInfo.setCity_code(pd.getString("city_code"));
        shopInfo.setArea_code(pd.getString("area_code"));

        shopInfo.setAccount_type(pd.getString("account_type"));
        shopInfo.setAccount_no(pd.getString("account_no"));
        shopInfo.setAccount_name(pd.getString("account_name"));
        shopInfo.setUnion_bank_no(pd.getString("union_bank_no"));
        shopInfo.setUnion_bank_name(pd.getString("union_bank_name"));
        shopInfo.setRate(pd.getString("rate"));
        shopInfo.setPicList((List<ShopPic>) pd.get("picList"));
        return shopInfo;
    }

    public void getshopInfo(PageData pd,String store_id,ShopInfo shopInfo) throws Exception{
        //新增需要提交图片
        PageData pdImg = new PageData();
        //找到图片
        pdImg.put("foreign_id", pd.getString("id"));
        pdImg.put("store_id", store_id);
        pdImg.put("type", 5);//嘉联支付类的图片
        List<PageData> picList = newPicturesService.findByStoreIdAndType(pdImg);

        List<ShopPic> shopPicList = new ArrayList<>();

        for (PageData pdd : picList) {
            System.out.println("2222222");
            String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + pdd.getString("path");
            System.out.println(filePath);
            Message m = new JiaLianUtils().picReq(pdd.getString("sort"), filePath);
            if(m.getErrcode() != 0){
                //上传图片出现问题, 添加日志
                addErrorLogById(pd.getString("id"), store_id, m.getErrmsg(),pd.getString("mobile"),pd.getString("merch_name"));
                return ;
            }
            pdd.put("jl_path", m.getData().get("path"));
            newPicturesService.edit(pdd);

            ShopPic shopPic = new ShopPic(pdd.getString("sort"), m.getData().get("path").toString());
            shopPicList.add(shopPic);
        }
        shopInfo.setPicList(shopPicList);
    }
}
