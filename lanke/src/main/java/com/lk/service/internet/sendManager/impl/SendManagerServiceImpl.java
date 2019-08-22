package com.lk.service.internet.sendManager.impl;

import com.lk.controller.internet.msgManager.MsgManagerController;
import com.lk.dao.DaoSupport;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.userDown.UserDownService;
import com.lk.service.billiCenter.userFlow.UserFlowHistoryService;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.internet.matches.impl.MatchesServiceImpl;
import com.lk.service.internet.sendManager.SendManagerService;
import com.lk.service.intuser.wxML.impl.WxMLServiceImpl;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.tb.TemplateRecord.TemplateRecordService;
import com.lk.service.user.notify.NotifyService;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.service.weixin.sendRecord.SendRecordManager;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.TemplateMsgUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author myq
 * @description 群发管理——业务层
 * @create 2019-06-04 17:47
 */
@Service
public class SendManagerServiceImpl implements SendManagerService {

    private static final Logger log = LoggerFactory.getLogger(SendManagerServiceImpl.class);
    private static String mapperName = "SendManagerMapper.";

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Autowired
    private TemplateRecordService templateRecordService;

    @Autowired
    private AutoReplyService autoReplyService;
    @Autowired
    private WeixinService weixinService;
    @Resource(name = "wechatuserService")
    private WeChatUserManager wechatuserService;
    @Resource(name = "bunduserService")
    private BundUserManager bunduserService;
    @Autowired
    private UserFlowHistoryService userFlowHistoryService;

    @Resource(name = "userDownService")
    private UserDownService userDownService;
    @Autowired
    private NotifyService notifyService;

    @Resource(name="internetmaterialService")
    private InternetMaterialManager internetmaterialService;
    @Resource(name="recordMaterialService")
    private RecordMaterialManager recordMaterialService;
    @Resource(name = "sendRecordService")
    private SendRecordManager sendRecordService;

    @Resource(name = "cardService")
    private CardManager cardService;

    @Autowired
    private MatchesService matchesService;
    @Autowired
    private StoreManager storeService;

    /**
     * 新增
     * @param pd
     * @throws Exception
     */
    @Override
    public int save(PageData pd) throws Exception {
        return (int) dao.save(mapperName + "save", pd);
    }

    /**
     * 删除
     *
     * @param id
     * @throws Exception
     */
    @Override
    public int delete(String id) throws Exception {
        return (int) dao.delete(mapperName + "delete", id);
    }

    /**
     * 修改
     *
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int edit(PageData pd) throws Exception {
        return (int) dao.update(mapperName + "edit", pd);
    }

    /**
     * 根据主键查找id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PageData findById(String id) throws Exception {
        PageData pd = (PageData) dao.findForObject(mapperName + "findById", id);
        return pd;
    }

    @Override
    public PageData findTempById(String id) throws Exception {
        PageData pd = (PageData) dao.findForObject(mapperName + "findTempById", id);
        return pd;
    }
    @Override
    public PageData findActById(String id) throws Exception {
        PageData pd = (PageData) dao.findForObject(mapperName + "findActById", id);
        return pd;
    }


    @Override
    public List<PageData> dataListpage(Page page) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "datalistPage", page);
    }


    @Override
    public List<PageData> findByTypeAndOtherType(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList(mapperName + "findByTypeAndOtherType", pd);
    }

    /*******************************************************业务代码***************************************************************/


    /**
     * 查询分页列表
     *
     * @param pd   关键词，类型等
     * @param page 分页
     * @return
     * @throws Exception
     */
    @Override
    public LayMessage getList(PageData pd, Page page) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();
        pd.put("internet_id", user.getINTENET_ID());

        page.setPd(pd);
        List<PageData> list = this.dataListpage(page);
        for (PageData pdd : list) {

            pdd.put("create_time", pdd.get("create_time").toString());
            pdd.put("send_time", pdd.get("send_time").toString());
            //门店名称
        }

        return LayMessage.ok(page.getTotalResult(), list);
    }

    /**
     * 查询模板消息的内容
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message loadTempMsg(PageData pd) throws Exception {

        String send_id = pd.getString("send_id");


        PageData pdTemp = this.findTempById(send_id);
        if (pdTemp == null) {
            return Message.error("找不到数据");
        }

        return Message.ok().addData("pd", pdTemp);
    }


    /**
     * 查询活动消息的内容
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message loadActMsg(PageData pd) throws Exception {

        String send_id = pd.getString("send_id");

        PageData pdAct = this.findActById(send_id);
        if (pdAct == null) {
            return Message.error("找不到数据");
        }

        if(pdAct.getString("act_type").equals("card")){
            pd.put("CARD_ID", pdAct.getString("foreign_id"));
            PageData pdd = cardService.findByCardId(pd);
            pdd = WxMLServiceImpl.comboCardTime(pdd);
            pdAct.put("pdCard", pdd);
        }
        if(pdAct.getString("act_type").equals("matches")){
            PageData pdd = matchesService.getById(pdAct.getString("foreign_id"));

            pdd.put("enroll_time", pdd.get("enroll_start_time").toString().substring(0,10) + " 至 " +pdd.get("enroll_end_time").toString().substring(0,10));
            pdd.put("game_time", pdd.get("start_time").toString().substring(0,10) + " 至 " +pdd.get("end_time").toString().substring(0,10));
            pdAct.put("pdMatches", pdd);
        }

        return Message.ok().addData("pd", pdAct);
    }


    /**
     * 查询通知可发送次数
     * @param pddd
     * @return
     * @throws Exception
     */
    @Override
    public Message loadInitTimes(PageData pddd) throws Exception{
        User user = Jurisdiction.getSessionUser().getUser();

        PageData pdOther = new PageData();
        String internet_id = user.getINTENET_ID();
        pdOther.put("internet_id", internet_id);
        pdOther.put("type", "temp");

        String now = Tools.date2Str(new Date()).substring(0,10);
        pdOther.put("begin_time", now + " 00:00:00");
        pdOther.put("end_time", now + " 23:59:59");

        PageData pdd = new PageData();
        pdOther.put("temp_type", "server_maintenance");
        List<PageData> list1 = this.findByTypeAndOtherType(pdOther);
        pdd.put("sm_times", 3 - list1.size());

        pdOther.put("temp_type", "equipment_failure");
        List<PageData> list2 = this.findByTypeAndOtherType(pdOther);
        pdd.put("ef_times", 3 - list2.size());

        pdOther.put("temp_type", "failure_recovery");
        List<PageData> list3 = this.findByTypeAndOtherType(pdOther);
        pdd.put("fr_times", 3 - list3.size());


        return Message.ok().addData("pd", pdd);
    }

    /**
     * 保存模板消息
     *
     * @param pd temp_type  first_data keyword_data remark_data url, send_token
     * @return
     * @throws Exception
     */
    @Override
    public Message saveNotify(PageData pd) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();
        PageData pdParam = new PageData();
        pdParam.put("INTERNET_ID", user.getINTENET_ID());

        String temp_type = pd.getString("temp_type");
        String send_obj = pd.getString("send_obj");
        String store_ids = pd.getString("store_ids");
        String sel_result = "";
        List<PageData> objList = new ArrayList<>();
        if (send_obj.equals("fans")) {//粉丝
            sel_result = "全部粉丝";
            objList = wechatuserService.findBySend(pdParam);
        } else if (send_obj.equals("member")) {//会员
            pdParam.put("internet_id", user.getINTENET_ID());
            pdParam.put("store_ids", store_ids.split(","));
            objList = wechatuserService.findByStoreIds(pdParam);
            sel_result = objList.size() + "位会员";
        }
        PageData pdSelJson = new PageData();//粉丝或会员
        pdSelJson.put("send_obj", send_obj);

        //获取群发会员列表
        String open_ids = "";
        if (objList.size() == 0) {
            return Message.error("所选的群发对象为空");
        }
        for (PageData pdUser : objList) {
            open_ids += pdUser.getString("OPEN_ID") + ",";
        }
        open_ids = open_ids.substring(0, open_ids.length() - 1);


        //保存群发记录
        String record_id = StringUtil.get32UUID();
        PageData pdSend = new PageData();
        pdSend.put("id", StringUtil.get32UUID());
        pdSend.put("internet_id", user.getINTENET_ID());
        pdSend.put("foreign_id", record_id);
        pdSend.put("type", "temp");//模板消息
        pdSend.put("temp_type", temp_type);//模板消息类型
        pdSend.put("title", pd.getString("first_data"));
        pdSend.put("store_ids", store_ids);//门店ids
        pdSend.put("store_names", pd.getString("store_names"));//门店ids
        pdSend.put("sel_json", JSONObject.fromObject(pdSelJson).toString());//查询对象的条件
        pdSend.put("sel_list", open_ids);//查询到的open_id列表，英文逗号隔开
        pdSend.put("sel_result", sel_result);//查询的结果
        pdSend.put("create_time", Tools.date2Str(new Date()));
        pdSend.put("update_time", Tools.date2Str(new Date()));
        pdSend.put("send_time", Tools.date2Str(new Date()));
        pdSend.put("status", 1);//状态：0未群发；1已群发
        pdSend.put("d_state", 0);//逻辑删除：1已删除；0未删除
        this.save(pdSend);

        //单独保存一条记录
        PageData pdRecord = new PageData();
        pdRecord.put("id", record_id);
        pdRecord.put("create_time", new Date());
        pdRecord.put("update_time", new Date());
        pdRecord.put("send_time", new Date());

        pdRecord.put("internet_id", user.getINTENET_ID());
        pdRecord.put("first_data", pd.getString("first_data"));
        pdRecord.put("keyword_data", pd.getString("keyword_data"));
        pdRecord.put("remark_data", pd.getString("remark_data"));
        pdRecord.put("temp_content", pd.getString("temp_content"));//群发内容
        String url = pd.getString("pre") + "wxML/goTempContent.do?temp_id=" + pdRecord.getString("id");
        if(pd.getString("is_has_url").equals("true")){
            pdRecord.put("url", url);
        }

        templateRecordService.save(pdRecord);

        //启动线程发送
        String send_token = pd.getString("send_token");
        MsgManagerController.sendMap.put(send_token, "1");//正在群发
        new Thread(new sendTemplate(user.getINTENET_ID(), temp_type, pd, objList)).start();

        return Message.ok("发送成功").addData("id", pdSend.getString("id"));
    }


    //线程
    class sendTemplate implements Runnable {
        private String internet_id;
        private String temp_type;
        private PageData pd;
        private List<PageData> objList;

        public sendTemplate(String internet_id, String temp_type, PageData pd, List<PageData> objList) {
            this.internet_id = internet_id;
            this.temp_type = temp_type;
            this.pd = pd;
            this.objList = objList;
        }

        @Override
        public void run() {
            String shot = TemplateMsgUtil.getTypeShort(temp_type);
            try {
                //预处理模板
                JSONObject checkTemp = weixinService.preHandleTemplate(internet_id, temp_type, shot);

                //获取token
                String token = checkTemp.getString("token");
                String template_id = checkTemp.getString("template_id");

                //封装微信模板消息内容
                PageData pdTemplate = new PageData();
                pdTemplate.put("first_data", pd.getString("first_data"));
                pdTemplate.put("remark_data", pd.getString("remark_data"));

                List<String> keywordList = new ArrayList<>();
                JSONObject keywordJSON = JSONObject.fromObject(pd.getString("keyword_data"));
                for (int i = 1; i < 6; i++) {
                    if (keywordJSON.containsKey("keyword" + i)) {
                        keywordList.add(keywordJSON.getString("keyword" + i));
                    }
                }
                JSONObject templateJSON = TemplateMsgUtil.commonTemplate(pdTemplate, keywordList);

                for (PageData pdd : objList) {
                    String open_id = pdd.getString("OPEN_ID");
                    //发送模板消息
                    String record_id = StringUtil.get32UUID();
                    String url = null;
                    if(pd.getString("is_has_url").equals("true")){
                        url = pd.getString("pre") + "wxML/goTempContent.do?temp_id=" + record_id
                                +  "&openid=" + open_id;//跳转url
                    }

                    JSONObject sendResult = TemplateMsgUtil.sendTemplate(token, open_id,
                            template_id, url, null,
                            templateJSON);// 发送模板消息

                    PageData pdRecord = new PageData();
                    pdRecord.put("id", record_id);
                    pdRecord.put("create_time", new Date());
                    pdRecord.put("update_time", new Date());
                    pdRecord.put("send_time", new Date());

                    pdRecord.put("open_id", open_id);
                    pdRecord.put("internet_id", internet_id);
                    pdRecord.put("template_id", checkTemp.getString("template_id"));

                    pdRecord.put("first_data", pd.getString("first_data"));
                    pdRecord.put("keyword_data", pd.getString("keyword_data"));
                    pdRecord.put("remark_data", pd.getString("remark_data"));
                    pdRecord.put("temp_content", pd.getString("temp_content"));//群发内容
                    pdRecord.put("url", url);

                    pdRecord.put("errmsg", sendResult.toString());
                    pdRecord.put("h_state", "3");//不处理
                    templateRecordService.save(pdRecord);
                }

                //发送完成去除缓存
                String send_token = pd.getString("send_token");
                MsgManagerController.sendMap.remove(send_token);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("群发模板消息发送异常");
            }

        }
    }


    /**
     * 保存图文消息
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message saveNews(PageData pd) throws Exception {

        User user = Jurisdiction.getSessionUser().getUser();

        //素材id
        String material_id = pd.getString("material_id");
        //新增
        if(StringUtil.isEmpty(material_id)){
            //保存记录
            PageData pdRecord = new PageData();
            pdRecord.put("SENDRECORD_ID", StringUtil.get32UUID());
            pdRecord.put("INTERNET_ID", user.getINTENET_ID());
            pdRecord.put("CREATE_TIME", Tools.date2Str(new Date()));
            pdRecord.put("STATE", "1");//未发（草稿）
            sendRecordService.save(pdRecord);

            //保存素材
            PageData pdMaterial = new PageData();
            pdMaterial.put("INTERNETMATERIAL_ID", StringUtil.get32UUID());
            pdMaterial.put("TITLE", pd.getString("TITLE"));
            pdMaterial.put("THUMB_MEDIA_ID", pd.getString("THUMB_MEDIA_ID"));
            pdMaterial.put("CREATE_USER", pd.getString("CREATE_USER"));
            pdMaterial.put("DIGEST", pd.getString("DIGEST"));
            pdMaterial.put("CONTENT", pd.getString("CONTENT"));
            pdMaterial.put("CONTENT_SOURCE_URL", pd.getString("CONTENT_SOURCE_URL"));
            pdMaterial.put("INTERNET_ID", user.getINTENET_ID());
            pdMaterial.put("CREATE_TIME", Tools.date2Str(new Date()));
            internetmaterialService.save(pdMaterial);


            //保存关联表
            PageData pdRecordMaterial = new PageData();
            pdRecordMaterial.put("RECORDMATERIAL_ID", StringUtil.get32UUID());
            pdRecordMaterial.put("SENDRECORD_ID", pdRecord.getString("SENDRECORD_ID"));
            pdRecordMaterial.put("INTERNETMATERIAL_ID", pdMaterial.getString("INTERNETMATERIAL_ID"));
            pdRecordMaterial.put("SEQUENCE", 0);//序号
            pdRecordMaterial.put("PICTURE_ID", pd.getString("PICTURE_ID"));//图片id
            recordMaterialService.save(pdRecordMaterial);
        }else{
            //修改
            PageData pdMaterial = new PageData();
            pdMaterial.put("INTERNETMATERIAL_ID", material_id);
            pdMaterial.put("TITLE", pd.getString("TITLE"));
            pdMaterial.put("THUMB_MEDIA_ID", pd.getString("THUMB_MEDIA_ID"));
            pdMaterial.put("CREATE_USER", pd.getString("CREATE_USER"));
            pdMaterial.put("DIGEST", pd.getString("DIGEST"));
            pdMaterial.put("CONTENT", pd.getString("CONTENT"));
            pdMaterial.put("CONTENT_SOURCE_URL", pd.getString("CONTENT_SOURCE_URL"));
            pdMaterial.put("CREATE_TIME", Tools.date2Str(new Date()));
            internetmaterialService.edit(pdMaterial);

        }


        return null;
    }


    /**
     * 发布图文
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message issueNews(PageData pd) throws Exception {


        return null;
    }


    /**
     * 保存并发布活动
     * @param pd act_type
     * @return
     * @throws Exception
     */
    @Override
    public Message saveActivity(PageData pd) throws Exception{

        User user = Jurisdiction.getSessionUser().getUser();

        String act_type = pd.getString("act_type");
        String obj_id = pd.getString("obj_id");//卡券或者赛事id
        String store_ids = pd.getString("store_ids");//store_id，用英文逗号隔开
        String store_names = pd.getString("store_names");//store_name，用英文逗号隔开
        String sel_json = pd.getString("sel_json");//sel_json查询条件
        String open_ids = pd.getString("sel_list");//open_id列表，用英文逗号隔开
        String sel_result = pd.getString("sel_result");//查询结果

        List<PageData> objList = new ArrayList<>();
        if(sel_result.contains("全部会员")){
            PageData pdParam = new PageData();
            pdParam.put("internet_id", user.getINTENET_ID());
            pdParam.put("store_ids", store_ids.split(","));
            objList = wechatuserService.findByStoreIds(pdParam);

            for (PageData pdUser : objList) {
                open_ids += pdUser.getString("OPEN_ID") + ",";
            }
            if (open_ids.equals("")) {
                return Message.error("门店下尚无会员，无法群发");
            }
            open_ids = open_ids.substring(0, open_ids.length() - 1);
        }
        if (open_ids.equals("")) {
            return Message.error("门店下尚无会员，无法群发");
        }


        //群发记录
        PageData pdSend = new PageData();
        pdSend.put("id", StringUtil.get32UUID());
        pdSend.put("internet_id", user.getINTENET_ID());
        pdSend.put("foreign_id", obj_id);
        pdSend.put("type", "act_msg");//活动消息
        pdSend.put("act_type", act_type);//活动消息类型
        pdSend.put("title", pd.getString("first_data"));//卡券名称/赛事名称/消息
        if(act_type.equals("msg")){
            pdSend.put("content", pd.getString("content"));//消息内容
        }
        pdSend.put("store_ids", store_ids);//门店ids
        pdSend.put("store_names", store_names);//门店名称
        pdSend.put("sel_json", sel_json);//查询对象的条件
        pdSend.put("sel_list", open_ids);//查询到的open_id列表，英文逗号隔开
        pdSend.put("sel_result", sel_result);//查询的结果
        pdSend.put("ad_content", pd.getString("ad_content"));//查询的结果
        pdSend.put("create_time", Tools.date2Str(new Date()));
        pdSend.put("update_time", Tools.date2Str(new Date()));
        pdSend.put("send_time", Tools.date2Str(new Date()));
        pdSend.put("status", 1);//状态：0未群发；1已群发
        pdSend.put("d_state", 0);//逻辑删除：1已删除；0未删除
        this.save(pdSend);

        //发送给用户消息，保存用户记录
        String[] open_idarr = open_ids.split(",");
        for (String str : open_idarr) {
            PageData pdNotify = new PageData();
            pdNotify.put("id", StringUtil.get32UUID());
            pdNotify.put("internet_id", user.getINTENET_ID());
            pdNotify.put("open_id", str);
            pdNotify.put("send_id", pdSend.getString("id"));
            pdNotify.put("foreign_id", str);
            pdNotify.put("type", act_type);
            pdNotify.put("is_read", 0);//是否已读；1已读；0未读
            pdNotify.put("create_time", Tools.date2Str(new Date()));
            notifyService.save(pdNotify);
        }

        //去除token
        String send_token = pd.getString("send_token");
        MsgManagerController.sendMap.remove(send_token);

        return Message.ok("群发成功");
    }


    /**
     * 删除群发记录
     * @param pd id
     * @return
     * @throws Exception
     */
    @Override
    public Message delRecord(PageData pd) throws Exception{

        PageData pdSend = new PageData();
        pdSend.put("id", pd.getString("id"));
        pdSend.put("d_state", 1);//逻辑删除
        int num = this.edit(pdSend);
        if(num > 0){
            return Message.ok("删除成功");
        }

        return Message.error("数据已删除");
    }

    /**
     * 批量删除
     * @param pd ids
     * @return
     */
    @Override
    public Message delMoreRecord(PageData pd) throws Exception{
        String ids = pd.getString("ids");

        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            PageData pdSend = new PageData();
            pdSend.put("id", idArr[i]);
            pdSend.put("d_state", 1);//逻辑删除
            int num = this.edit(pdSend);

        }

        return Message.ok("删除成功");
    }

    /**
     *
     * @param pd store_ids begin_time end_time average_money1 average_money2 uptime1 uptime2 overage
     * @return 筛选用户
     * @throws Exception
     */
    @Override
    public LayMessage getUserList(Page page, PageData pd) throws Exception{
        User user = Jurisdiction.getSessionUser().getUser();
        pd.put("internet_id", user.getINTENET_ID());
        PageData pdParam = new PageData();

        String store_ids = pd.getString("store_ids");
        List<String> ss = new ArrayList<>();
        if(StringUtil.isNotEmpty(store_ids)){
            String[] arr = store_ids.split(",");
            for (String s : arr) {
                ss.add(s);
            }
            pd.put("store_ids", ss);
            pdParam.put("store_ids", ss);
        }else{
            PageData pdp = new PageData();
            pdp.put("internet_id", user.getINTENET_ID());
            List<PageData> storeList = storeService.findStoreByOthers(pdp);
            for (PageData pdd : storeList) {
                ss.add(pdd.getString("STORE_ID"));
            }
            pd.put("store_ids", ss);
            pdParam.put("store_ids", ss);
        }

        //String[] strArr = {"mem_overage", "mem_money_average", "mem_principal", "mem_time_average"};
        double mem_money_average1 = (double) pd.get("mem_money_average1");
        //pd.get("mem_money_average2")
        double mem_time_average1 = (double) pd.get("mem_time_average1");
        //pd.get("mem_time_average2")
        String begin_time = pd.getString("begin_time");
        String end_time = pd.getString("end_time");
        int day = (int) (((Tools.str2Date2(end_time).getTime() - Tools.str2Date2(begin_time).getTime()) / 1000) / 60 / 60 / 24);
        if(day == 0){
            day = 1;
        }

        //分页
        page.setPd(pd);
        List<PageData> userList = bunduserService.listByStoreIds(pd);
        String[] flow_types = {"4", "8", "32"};//消费，收银端扣费，第三方扣费
        int money_type = 2;//0总，1加钱；2消费
        for (int i = 0; i < userList.size(); i++) {
            PageData pdd = userList.get(i);

            pdParam.put("flow_types", "");

            PageData calMoney = userFlowHistoryService.calMoney(pdd.getString("STORE_ID"), pdd.getString("CARDED")
                    , flow_types, money_type, begin_time, end_time);


            double amount = (double) calMoney.get("amount");
            double reward = (double) calMoney.get("reward");
            double sum = -(amount + reward);
            pdd.put("OVERAGE_MONEY", (sum / day) );//平均每天消费金额

            //统计平均每天消费金额
            if(sum < mem_money_average1){
                userList.remove(pdd);
                i--;
                continue;
            }
            if(StringUtil.isNotEmpty(pd.get("mem_money_average2")) && sum > Double.parseDouble(pd.get("mem_money_average2").toString())){
                userList.remove(pdd);
                i--;
                continue;
            }

            //统计平均每天活跃时长
            PageData calTime = userDownService.calUserUpTime(pdd.getString("STORE_ID"), pdd.getString("CARDED")
                    , begin_time, end_time);

            double uptime = Double.parseDouble(calTime.get("uptime").toString());
            pdd.put("UPTIME", (uptime / day) + "");//平均每天活跃时长
            if(uptime < mem_time_average1){
                userList.remove(pdd);
                i--;
                continue;
            }
            if(StringUtil.isNotEmpty(pd.get("mem_time_average2")) && uptime > Double.parseDouble(pd.get("mem_time_average2").toString())){
                userList.remove(pdd);
                i--;
                continue;
            }
        }


        return LayMessage.ok(page.getTotalResult(), userList);
    }





}
