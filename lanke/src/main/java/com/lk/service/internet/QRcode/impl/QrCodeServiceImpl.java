package com.lk.service.internet.QRcode.impl;


import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.Page;
import com.lk.entity.billecenter.SWUserBoard;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.entity.weixin.JSAPI;
import com.lk.service.billiCenter.userBoard.UserBoardService;
import com.lk.service.billiCenter.userDown.UserDownService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.internet.QRcode.QrCodeService;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.myMember.MyMemberManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.user.notify.NotifyService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.*;
import com.lk.util.qrcode.QrcodeUtil;
import com.lk.wechat.util.WechatUtil;
import com.lk.wechat.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author myq
 * @description 网吧二维码--业务实现层
 * @create 2018-12-14 15:11
 */
@Service("qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    private static final Logger log = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    @Autowired
    private StoreManager storeService;
    @Autowired
    private IntenetManager intenetService;

    @Autowired
    private BundUserManager bundUserService;
    @Resource(name="callBackService")
    private CallBack callBackService;
    @Autowired
    private AutoReplyService autoReplyService;


    @Resource(name = "myMemberService")
    private MyMemberManager myMemberService;
    @Resource(name = "indexMemberService")
    private IndexMemberManager indexMemberService;


    @Resource(name = "userBoardService")
    private UserBoardService userBoardService;

    @Resource(name = "userDownService")
    private UserDownService userDownService;
    @Autowired
    private NotifyService notifyService;

    @Override
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("InternetQrcodeMapper.findById", pd);
    }

    @Override
    public JSONObject loadQrList(PageData pd, Page page) throws Exception{
        JSONObject result = new JSONObject();

        page.setPd(pd);

        List<PageData> qrList = (List<PageData>) dao.findForList("InternetQrcodeMapper.datalistPage", page);
        for (PageData pdQr : qrList) {
            if(pdQr.get("status").toString().equals("2"))
                pdQr.put("status_info", "已禁用");
            if(pdQr.get("status").toString().equals("1") && pdQr.get("state").toString().equals("1"))
                pdQr.put("status_info", "使用中");
            if(pdQr.get("status").toString().equals("1") && pdQr.get("state").toString().equals("2"))
                pdQr.put("status_info", "离线");
        }

        result.put("data", qrList);
        result.put("count", page.getTotalResult());
        result.put("msg", "查询成功");
        result.put("showCount", page.getShowCount());
        result.put("code", "0");

        return result;
    }


    @Override
    public JSONObject importCN(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        MultipartFile file = (MultipartFile) pd.get("file");
        String store_id = pd.getString("store_id");

        //判断参数是否为空
        if(file == null){
            result.put("result", "false");
            result.put("message", "请选择文件");
            return result;
        }

        if(store_id == null){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        //文件上传路径
        String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
        //执行上传
        String fileName =  FileUpload.fileUp(file, filePath, StringUtil.get32UUID());
        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
        List<PageData> list = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);

        //顺序（编号，电脑名称）
        for (int i = 0; i < list.size(); i++) {
            if(StringUtil.isEmpty(list.get(i).get("var0")) || StringUtil.isEmpty(list.get(i).get("var1"))){
                //跳过空字符
                continue;
            }

            PageData pdQr = new PageData();
            pdQr.put("store_id", store_id);
            pdQr.put("computer_name", list.get(i).getString("var1"));
            pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQr);
            if(StringUtil.isNotEmpty(pdQr)){
                //执行修改操作
                pdQr.put("serial_number", list.get(i).getString("var0"));
                pdQr.put("ip", list.get(i).getString("var2"));
                pdQr.put("mac_address", list.get(i).getString("var3"));

                dao.update("InternetQrcodeMapper.edit", pdQr);
            }else{
                //新增操作
                pdQr = new PageData();
                pdQr.put("serial_number", list.get(i).getString("var0"));
                pdQr.put("computer_name", list.get(i).getString("var1"));
                pdQr.put("ip", list.get(i).getString("var2"));
                pdQr.put("mac_address", list.get(i).getString("var3"));

                pdQr.put("id", StringUtil.get32UUID());
                pdQr.put("status", 1);//启用
                pdQr.put("state", 2);//离线
                pdQr.put("internet_id", user.getINTENET_ID());
                pdQr.put("store_id", store_id);

                dao.save("InternetQrcodeMapper.save", pdQr);
            }



        }

        result.put("result", "true");
        result.put("message", "导入成功");
        return result;
    }


    @Override
    public JSONObject downloadQrs(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        if(pd.getString("store_id") == null){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }
        //获取加v门店信息
        PageData pdStoreV = storeService.findStoreVById(pd);
        if(!pdStoreV.get("STATE").toString().equals("1")){
            result.put("result", "false");
            result.put("message", "当前门店尚未开通计费系统");
            return result;
        }

        //获取公众号信息
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        pd.put("INTENET_ID", user.getINTENET_ID());
        PageData pdInternet = intenetService.findById(pd);

        String appid = pdInternet.getString("WECHAT_ID");//公众号id
        String state = pdStoreV.getString("pubwin_store_id");//pubwin_id
        String domain = PublicManagerUtil.URL1;
        String component_appid = PublicManagerUtil.APPID;


        List<PageData> qrList = (List<PageData>) dao.findForList("InternetQrcodeMapper.findByStoreId", pd);

        //拼装二维码连接
        for (PageData pdr :qrList){
            String url = getQrUrl(appid, domain, pdr.getString("computer_name"), state, component_appid);
            pdr.put("url", url);
        }
        System.out.println("二维码数量======="+qrList.size());

        result.put("result", "true");
        result.put("qrList", qrList);
        return result;
    }


    @Override
    public JSONObject editStatus(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        PageData pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findById", pd);

        if(pdQr == null){
            result.put("result", "false");
            result.put("message", "无效的参数");
            return result;
        }

        int status = (int) pdQr.get("status");
        if(status == 1){
            status = 2;//启用-->禁用
            result.put("message", "禁用成功");
        }else if(status == 2){
            status = 1;//禁用-->启用
            result.put("message", "启用成功");
        }
        pdQr.put("status", status);

        dao.update("InternetQrcodeMapper.edit", pdQr);

        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject banAll(PageData pd) throws Exception{
        JSONObject result = new JSONObject();


        PageData pdQr = new PageData();
        pdQr.put("store_id", pd.getString("store_id"));
        pdQr.put("status", pd.getString("status"));//禁用
        dao.update("InternetQrcodeMapper.editByStoreId", pdQr);


        result.put("result", "true");
        result.put("message", "禁用成功");
        return result;
    }

    @Override
    public JSONObject saveQr(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        if(StringUtil.isEmpty(pd.getString("store_id"))){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }
        if(StringUtil.isNotEmpty(pd.getString("id"))){
            //编辑
            PageData pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findById", pd);
            if(pdQr == null){
                result.put("result", "false");
                result.put("message", "无效的参数");
                return result;
            }

            PageData pdCon = new PageData();
            pdCon.put("store_id", pd.getString("store_id"));
            pdCon.put("serial_number", pd.getString("serial_number"));

            PageData pdQr2 = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdCon);
            if (pdQr2 != null && !pdQr2.getString("id").equals(pd.getString("id"))) {
                result.put("result", "false");
                result.put("message", "电脑编号已存在");
                return result;
            }
            pdCon.remove("serial_number");
            pdCon.put("computer_name", pd.getString("computer_name"));
            PageData pdQr3 = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdCon);
            if (pdQr3 != null && !pdQr3.getString("id").equals(pd.getString("id"))) {
                result.put("result", "false");
                result.put("message", "电脑名称已存在");
                return result;
            }

            //修改
            pdQr.put("serial_number", pd.getString("serial_number"));
            pdQr.put("computer_name", pd.getString("computer_name"));
            pdQr.put("ip", pd.getString("ip"));
            pdQr.put("mac_address", pd.getString("mac_address"));

            dao.update("InternetQrcodeMapper.edit", pdQr);
            Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
            PageData pdStoreV = storeService.findStoreVById(pd);
            String url = getQrUrl(org.getWECHAT_ID(), PublicManagerUtil.URL1, pdQr.getString("computer_name"),
                    pdStoreV.getString("pubwin_store_id"), PublicManagerUtil.APPID);
            result.put("url", url);
            result.put("serial_number", pdQr.getString("serial_number"));
        }else{
            //判断编号或者电脑是否唯一
            List<PageData> qrList = (List<PageData>) dao.findForList("InternetQrcodeMapper.findByStoreId", pd);
            for(PageData pdd : qrList){
                if(pdd.getString("serial_number").equals(pd.getString("serial_number"))){
                    result.put("result", "false");
                    result.put("message", "电脑编号已存在");
                    return result;
                }
                if(pdd.getString("computer_name").equals(pd.getString("computer_name"))){
                    result.put("result", "false");
                    result.put("message", "电脑名称已存在");
                    return result;
                }
            }

            //新增
            PageData pdQr = new PageData();
            pdQr.put("serial_number", pd.getString("serial_number"));
            pdQr.put("computer_name", pd.getString("computer_name"));
            pdQr.put("ip", pd.getString("ip"));
            pdQr.put("mac_address", pd.getString("mac_address"));

            pdQr.put("id", StringUtil.get32UUID());
            pdQr.put("status", 1);//启用
            pdQr.put("state", 2);//离线
            pdQr.put("internet_id", user.getINTENET_ID());
            pdQr.put("store_id", pd.getString("store_id"));

            dao.save("InternetQrcodeMapper.save", pdQr);
            Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
            PageData pdStoreV = storeService.findStoreVById(pd);
            String url = getQrUrl(org.getWECHAT_ID(), PublicManagerUtil.URL1, pdQr.getString("computer_name"),
                    pdStoreV.getString("pubwin_store_id"), PublicManagerUtil.APPID);
            result.put("url", url);
            result.put("serial_number", pdQr.getString("serial_number"));
        }


        result.put("result", "true");
        result.put("message", "保存成功");
        return result;
    }


    @Override
    public JSONObject deleteQr(PageData pd) throws Exception{
        JSONObject result = new JSONObject();
        result.put("result", "false");
        result.put("message", "参数错误");

        int num = (int) dao.delete("InternetQrcodeMapper.delete", pd);

        if(num > 0){
            result.put("result", "true");
            result.put("message", "删除成功");
        }

        return result;
    }


    @Override
    public JSONObject delMore(PageData pd)  throws Exception{
        JSONObject result = new JSONObject();
        User user = Jurisdiction.getSessionUser().getUser();//得到用户


        if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)){
            result.put("result", "false");
            result.put("message", "您不是网吧管理员，无权删除");
            return result;
        }

        String store_id = pd.getString("store_id");
        String ids = pd.getString("ids");
        if(StringUtil.isEmpty(ids) || ids.split(",").length == 0){
            result.put("result", "false");
            result.put("message", "请选择数据");
            return result;
        }
        pd.put("array", ids.split(","));

        dao.delete("InternetQrcodeMapper.deleteByIds", pd);

        result.put("result", "true");
        result.put("message", "删除成功");
        return result;
    }

    /********************************微信端--分割线********************************/

    @Override
    public JSONObject handleUser(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String code = pd.getString("code");
        String appid = pd.getString("appid");
        String store_id = pd.getString("store_id");


        //通过code，获取扫码的用户的信息
        result = indexMemberService.checkUserSubscribe(appid, code,"qr_", "user_up_qrcode");//场景值(上机二维码)
        if(result.getString("result").equals("false")){
            return result;
        }

        PageData pdUser = (PageData) JSONObject.toBean(result.getJSONObject("pdUser"), PageData.class);

        //判断用户是否绑定会员
        PageData pdBind = bundUserService.findUserByUserId(pdUser.getString("USER_ID"));
        if(StringUtil.isEmpty(pdBind)){
            result.put("result", "false");
            result.put("message", "请先绑定会员");
            result.put("err_type", "bind_err");
            result.put("viewName", "internet/qrCode/user_bind");
            return result;
        }
        result.put("pdBind", pdBind);

        //判断用户绑定的门店与 二维码所在的门店是否一致
        if (!store_id.equals(pdBind.getString("store_id"))) {
            result.put("result", "false");
            result.put("message", "会员绑定门店与二维码所在的门店不一致");
            result.put("err_type", "bind_store_err");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }


        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject handleQrInfo(PageData pd, PageData pdQr) throws Exception{
        JSONObject result = new JSONObject();

        if(StringUtil.isEmpty(pdQr)){
            result.put("result", "false");
            result.put("message", "揽客后台查找不到二维码信息");
            result.put("err_type", "qrr_expired_err");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }else if(pdQr.get("status").toString().equals("2")){
            result.put("result", "false");
            result.put("message", "二维码已被禁用");
            result.put("err_type", "qrr_baned_err");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }

        if (!ChannelCache.channelMap.containsKey(pd.getString("store_id"))) {
            result.put("result", "false");
            result.put("message", "揽客客户端已断开连接，请先连接");
            result.put("err_type", "lanke_client_stop_err");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        if(StringUtil.isNotEmpty(pdQr.getString("user_id"))
                && pdQr.get("state").toString().equals("1")
                && !pdQr.getString("user_id").equals(user.getUSER_ID())){
            //不是本人扫的二维码，显示被占用
            result.put("result", "false");
            result.put("message", "该电脑已被占用");
            result.put("err_type", "qrr_used_err");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }

        String store_id = pd.getString("store_id");
        String user_id = pd.getString("user_id");

        PageData pdQr2 = new PageData();
        pdQr2.put("store_id", store_id);
        pdQr2.put("user_id", user_id);
        pdQr2 = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQr2);
        //当扫的二维码和尚在上机的二维码不一致的时候，需要换机
        if(StringUtil.isNotEmpty(pdQr2) &&
                !pdQr2.getString("computer_name").equals(pdQr.getString("computer_name"))){
            pdQr.put("state", "3");//需要换机状态
        }

        result.put("pdQr", pdQr);
        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject handelComputerIsUp(PageData pd, PageData pdQr) throws Exception{
        JSONObject result = new JSONObject();

        String store_id = pd.getString("store_id");
        String computer_name = pd.getString("computer_name");


        if(pdQr.get("state").toString().equals("1")){
            //确定电脑是否真的被占用
            Message2 m2 = userBoardService.getUserBoard(store_id, null, computer_name);
            if (m2.getErrcode() == 0) {
                SWUserBoard swUserBoard = (SWUserBoard) m2.getData().get("SWUserBoard");
                if(StringUtil.isEmpty(swUserBoard.getUp_time())){
                    //查找不到上机信息
                    PageData pdCom = new PageData();
                    pdCom.put("store_id", store_id);
                    pdCom.put("computer_name", computer_name);
                    pdCom.put("state", 2);//离线
                    pdCom.put("user_id", "");
                    dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);
                }
            }else{
                result.put("result", "false");
                result.put("message", m2.getErrmsg());
                return result;
            }
        }

        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject handleUserIsUp(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String store_id = pd.getString("store_id");
        String user_id = pd.getString("user_id");

        PageData pdQr = new PageData();
        pdQr.put("store_id", store_id);
        pdQr.put("user_id", user_id);
        pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQr);

        if(pdQr != null){
            //确定用户是否正在上机
            PageData pdBind = bundUserService.findUserByUserId(user_id);
            String card_id = pdBind.getString("carded");

            Message2 m2 = userBoardService.getUserBoard(store_id, card_id, null);
            if (m2.getErrcode() == 0) {
                SWUserBoard swUserBoard = (SWUserBoard) m2.getData().get("SWUserBoard");
                if(StringUtil.isEmpty(swUserBoard.getUp_time())){
                    result.put("err_type", "invaid_board_err");//无效的上机状态
                    //查找不到上机信息
                    PageData pdCom = new PageData();
                    pdCom.put("store_id", store_id);
                    pdCom.put("computer_name", pdQr.getString("computer_name"));
                    pdCom.put("state", 2);//离线
                    pdCom.put("user_id", "");
                    dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);
                }
            }else{
                result.put("result", "false");
                result.put("message", m2.getErrmsg());
                return result;
            }
        }else{
            result.put("err_type", "invaid_board_err");//无效的上机状态
        }

        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject loadUserIndex(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String store_id = pd.getString("store_id");
        String computer_name = pd.getString("computer_name");

        PageData pdQr = new PageData();
        pdQr.put("store_id", store_id);
        pdQr.put("computer_name", computer_name);
        pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pd);

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("user_id", user.getUSER_ID());

        if(pdQr.get("state").toString().equals("1")){
            //判断上机的真实性

            //跑线程判断
            new Thread(new checkIsUserUp(pd, pdQr)).start();
        }

        result.put("pdQr", pdQr);
        return result;
    }


    @Override
    public JSONObject userIndex(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String computer_name = pd.getString("computer_name");
        String pubwin_store_id = pd.getString("state");

        //通过计费id获取其门店信息
        PageData pdStore = new PageData();
        pdStore.put("pubwin_store_id", pubwin_store_id);
        pdStore = storeService.findByPubwinStoreId(pdStore);
        if(pdStore == null){
            result.put("result", "false");
            result.put("message", "找不到您计费id所在的网吧");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }

        String store_id = pdStore.getString("STORE_ID");
        pd.put("store_id", store_id);

        //判断用户
        result = handleUser(pd);
        result.put("pdStore", pdStore);
        if("false".equals(result.getString("result"))){
            return result;
        }
        PageData pdUser = (PageData) JSONObject.toBean(result.getJSONObject("pdUser"), PageData.class);

        PageData pdBind = (PageData) JSONObject.toBean(result.getJSONObject("pdBind"), PageData.class);


        PageData pdQr = new PageData();
        pdQr.put("store_id", store_id);
        pdQr.put("computer_name", computer_name);
        pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pd);

        //二维码在上机的时候，判断真实性
        if(StringUtil.isNotEmpty(pdQr) && pdQr.get("state").toString().equals("1")){
            JSONObject userOnline = this.userOnline(pd);
            if(userOnline.getString("result").equals("false")){
                //如果查不到上机信息，则重新查询数据
                pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pd);
            }
        }

        //判断二维码
        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("user_id", user.getUSER_ID());
        result = handleQrInfo(pd, pdQr);
        if("false".equals(result.getString("result"))){
            return result;
        }
        pdQr = (PageData) JSONObject.toBean(result.getJSONObject("pdQr"), PageData.class);

        result.put("pdQr", pdQr);
        result.put("pdUser", pdUser);
        result.put("pdBind", pdBind);

        result.put("result", "true");
        return result;
    }


    /**
     * 请求上机
     * @param pd（store_id，psd，card_id，computer_name）
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject userUp(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        String store_id = pd.getString("store_id");
        String computer_name = pd.getString("computer_name");

        //二维码是否有效（store_id,computer_name）
        PageData pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pd);

        String user_id = user.getUSER_ID();
        pd.put("user_id", user_id);
        result = handleQrInfo(pd, pdQr);
        if(result.getString("result").equals("false")){
            return result;
        }

        //获取用户信息
        PageData pdUser = new PageData();
        pdUser.put("userId", user.getUSER_ID());
        pdUser = bundUserService.findByUser(pdUser);
        if(StringUtil.isEmpty(pdUser)){
            result.put("result", "false");
            result.put("message", "请先绑定门店");
            result.put("err_type", "bind_err");
            return result;
        }
        //优先卡号
        String card_id = StringUtil.isNotEmpty(pdUser.getString("CARDID")) ? pdUser.getString("CARDID"): pdUser.getString("CARDED");

        String psd = pd.getString("psd");
        if(StringUtil.isEmpty(psd)){
            //从数据库获取默认密码
            if(StringUtil.isEmpty(pdUser.get("PSD"))){
                psd = "123456";
            }else{
                psd = pdUser.get("PSD").toString();
            }
        }else{
            psd = psd.trim();
        }

        //请求上换机
        PageData pdQrUser = new PageData();
        pdQrUser.put("store_id", store_id);
        pdQrUser.put("user_id", user_id);
        pdQrUser = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQrUser);
        Message2 m2 = new Message2();
        if (StringUtil.isEmpty(pdQrUser)){
            //请求上机
            m2 = userBoardService.userUp(store_id, card_id, computer_name, psd);

        }else{
            //请求换机
            m2 = userBoardService.userUpChange(store_id, card_id, pdQrUser.getString("computer_name"), computer_name);
        }

        if(m2.getErrcode() != 0){
            System.out.println("上/换机接口返回码===========" + m2.toString());
            result.put("result", "false");
            result.put("message", m2.getErrmsg());

            if(m2.getErrmsg().equals("您输入的卡号密码不正确") || m2.getErrmsg().contains("密码不正确")){
                result.put("err_type", "psd_err");
            }else if(m2.getErrmsg().equals("卡未激活")){
                result.put("err_type", "card_unactivated_err");
            }else if(m2.getErrmsg().equals("卡已上机")){
                result.put("err_type", "qrr_used_err");
            }
            return result;
        }

        //修改保存密码
        PageData pdBind = new PageData();
        pdBind.put("USER_ID", user.getUSER_ID());
        pdBind.put("PSD", psd);
        bundUserService.editUser(pdBind);

        //修改二维码
        if (StringUtil.isEmpty(pdQrUser)){
            result.put("message", "上机成功");
            //修改二维码
            PageData pdCom = new PageData();
            pdCom.put("store_id", store_id);
            pdCom.put("computer_name", computer_name);
            pdCom.put("state", 1);//使用中
            pdCom.put("user_id", user.getUSER_ID());//使用中
            dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);
        }else{
            result.put("message", "换机成功");

            PageData pdCom = new PageData();
            pdCom.put("store_id", store_id);
            pdCom.put("computer_name", pdQrUser.getString("computer_name"));
            pdCom.put("state", 2);//离线
            pdCom.put("user_id", "");
            dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);

            //修改二维码
            PageData pdCom2 = new PageData();
            pdCom2.put("store_id", store_id);
            pdCom2.put("computer_name", computer_name);
            pdCom2.put("state", 1);//使用中
            pdCom2.put("user_id", user.getUSER_ID());//使用中
            dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom2);
        }

        //发起网络唤醒
        this.wakePower(pd);

        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject userOnline(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String store_id = pd.getString("store_id");
        String computer_name = pd.getString("computer_name");


        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        String user_id = user.getUSER_ID();

        PageData pdUser = new PageData();
        pdUser.put("userId", user_id);
        pdUser = bundUserService.findByUser(pdUser);


        //优先卡号
        String card_id = pdUser.getString("CARDED");
//        String card_id = StringUtil.isNotEmpty(pdUser.getString("CARDID")) ? pdUser.getString("CARDID"): pdUser.getString("CARDED");


        //查询用户正在上机情况
        SWUserBoard swUserBoard = null;
        Message2 m2 = userBoardService.getUserBoard(store_id, card_id, null);
        if(m2.getErrcode() == 0){
            swUserBoard = (SWUserBoard) m2.getData().get("SWUserBoard");
            if(StringUtil.isEmpty(swUserBoard.getUp_time())){
                PageData pdQr = new PageData();
                pdQr.put("store_id", store_id);
                pdQr.put("user_id", user_id);
                pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQr);
                if(pdQr != null){
                    PageData pdCom = new PageData();
                    pdCom.put("store_id", store_id);
                    pdCom.put("computer_name", pdQr.getString("computer_name"));
                    pdCom.put("state", 2);//离线
                    pdCom.put("user_id", "");
                    dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);
                }
                m2 = userBoardService.getUserBoard(store_id, null, computer_name);
            }
        }else{
            m2 = userBoardService.getUserBoard(store_id, null, computer_name);
        }

        if(m2.getErrcode() != 0){
            result.put("result", "false");
            result.put("message", m2.getErrmsg());
            return result;
        }

        swUserBoard = (SWUserBoard) m2.getData().get("SWUserBoard");
        if(StringUtil.isEmpty(swUserBoard.getUp_time())){
            PageData pdCom = new PageData();
            pdCom.put("store_id", store_id);
            pdCom.put("computer_name", computer_name);
            pdCom.put("state", 2);//离线
            pdCom.put("user_id", "");
            dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);

            result.put("result", "false");
            result.put("message", "查不到上机信息");
            return result;
        }
        swUserBoard = (SWUserBoard) m2.getData().get("SWUserBoard");
        result.put("pdUserBoard", swUserBoard);

        String up_time = swUserBoard.getUp_time();
        System.out.println("上机时间up_time::::::" + up_time);
        result.put("result", "true");
        return result;
    }


    @Override
    public JSONObject getSubscribeImg(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String appid = pd.getString("appid");

        //带场景的二维码
        pd.put("APP_ID", appid);
        PageData pdInternet = intenetService.findByappid(pd).get(0);

        //二维码(从缓存中查找，有效期1天)
        Cache cache = CacheManager.getWxQrCache();//得到缓存对象(含有效时间110分钟)
        JSONObject qr_json = (JSONObject) cache.getObject("qr_" + pdInternet.getString("INTENET_ID"));
        if(qr_json == null){
            String token = autoReplyService.getAuthToken(pdInternet.getString("INTENET_ID"));
            JSONObject scene = new JSONObject();
            scene.put("scene_str", "user_up_qrcode");//场景值(上机二维码)
            qr_json = WechatUtil.generateQCode("temp", token, 25*60*60, scene);//1天有效
            cache.insertObject("qr_"+pdInternet.getString("INTENET_ID"),qr_json);
        }

        result.put("qr_img", qr_json.getString("url"));
        result.put("qr_img_base64", QrcodeUtil.putBase64(qr_json.getString("url"),218,218));
        return result;
    }


    @Override
    public JSONObject userDown(PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        String store_id = pd.getString("store_id");
        String computer_name = pd.getString("computer_name");

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        // 判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            result.put("result", "false");
            result.put("message", "无效的用户");
            result.put("err_type", "user_null_err");
            return result;
        }
        PageData pdUser = new PageData();
        pdUser.put("userId", user.getUSER_ID());
        pdUser = bundUserService.findByUser(pdUser);

        //优先卡号
        String card_id = StringUtil.isNotEmpty(pdUser.getString("CARDID")) ? pdUser.getString("CARDID"): pdUser.getString("CARDED");

        Message2 m2 = userDownService.userDown(store_id, card_id);
        if(m2.getErrcode() != 0){
            result.put("result", "false");
            result.put("message", "下机失败:" + m2.getErrmsg());
            return result;
        }else{
            //下机成功，修改二维码
            PageData pdCom = new PageData();
            pdCom.put("store_id", store_id);
            pdCom.put("computer_name", computer_name);
            pdCom.put("state", 2);//离线
            pdCom.put("user_id", "");
            dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);

            result.put("result", "true");
            result.put("message", "下机成功");
        }
        return result;
    }


    @Override
    public JSONObject setQrParam(PageData pd) throws Exception{

        String store_id = pd.getString("store_id");
        String appid = pd.getString("appid");
        String lanke_url = pd.getString("lanke_url");
        String component_appid = pd.getString("component_appid");

        JSONObject parameters = new JSONObject();
        parameters.put("appid", appid);
        parameters.put("lanke_url", lanke_url);
        parameters.put("component_appid", component_appid);

        JSONObject param = new JSONObject();
        param.put("parameters", parameters);
        param.put("company_type", 2);//网晶

        System.out.println(param.toString());
        Message msg = new Message();
        msg.setBarId(store_id);
        msg.setType(MsgUtil.SET_QR_PARAM);
        msg.setFlag(Tools2.getCenterMsgId2());
        msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));

        // 通过store_id获取具体需要通讯的客户端连接
        ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
        Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
        callBackService.result(msg.getFlag());
        return null;
    }


    @Override
    public JSONObject getWxConfig(PageData pd) throws Exception{
        JSONObject result = new JSONObject();


        String url = pd.getString("url");

        User user = Jurisdiction.getSessionUser().getUser();//得到用户


        pd.put("user_id", user.getUSER_ID());
        pd.put("userId", user.getUSER_ID());
        PageData pdBind = bundUserService.findByUser(pd);
        if(StringUtil.isEmpty(pdBind)){
            result.put("result", "false");
            result.put("err_type", "bind_err");

            String backurl = "/intenetmumber/bindCard.do?label=index";
            result.put("backurl", backurl);
            return result;
        }


        //判断用户有没有上机（有的话就直接去一码通页面）
        pd.put("store_id", pdBind.getString("STORE_ID"));
        PageData pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pd);
        if(StringUtil.isEmpty(pdQr)){
            /****** 开放平台为公众号提供的token *****/
            JSAPI jsapi = indexMemberService.getWXJSdata(url, user.getINTENET_ID());

            result.put("appid", jsapi.getAppid());
            result.put("wx", jsapi);
            log.info("result_wx==============================="+result.toString());

            result.put("result", "true");
        }else{
            //去正在上机页面
            //（serial_number,computer_name,imgurl,neck_name,store_name,store_id, state）
            String backurl = "/qrCode/goUserUp.do?computer_name="+pdQr.getString("computer_name")
                    +"&serial_number="+pdQr.getString("serial_number")
                    +"&state=1"
                    +"&store_id="+pdBind.getString("STORE_ID")
                    +"&imgurl="+pdBind.getString("IMGURL")
                    +"&store_name="+pdBind.getString("STORE_NAME")
                    +"&NAME="+pdBind.getString("NAME")
                    +"&neck_name="+pdBind.getString("NAME");
            result.put("backurl", backurl);
            result.put("result", "false");
        }

        return result;
    }



    public JSONObject getState(PageData pd) throws Exception{
        JSONObject result = new JSONObject();
        result.put("up_state", "2");

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("userId", user.getUSER_ID());
        pd.put("user_id", user.getUSER_ID());
        PageData pdUser = bundUserService.findByUser(pd);
        if(StringUtil.isNotEmpty(pdUser)){


//            pd.put("store_id", pdUser.getString("STORE_ID"));
            PageData pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pd);
            if (StringUtil.isNotEmpty(pdQr)){

                JSONObject userOnline = this.userOnline(pdQr);
                if("true".equals(userOnline.getString("result"))){
                    result.put("up_state", pdQr.get("state"));

                    JSONObject obj = (JSONObject) userOnline.get("pdUserBoard");
                    ChannelCache.userUpMap.put(pdQr.getString("store_id") + "_" + pdUser.getString("CARDID"), obj);
                }


            }
        }


        //我的小红点
        JSONObject indexResult = myMemberService.loadMyData(pd, user);
        result.put("cardSize", indexResult.getString("cardSize"));

        //会员生活小红点
        pd.put("open_id", user.getOPEN_ID());
        pd.put("is_read", "0");//0未读；1已读
        List<PageData> mllist = notifyService.findUnreadByOpenId(pd);
        result.put("mlSize", mllist.size());


        return result;
    }


    private String getQrUrl(String appid, String domain, String computer_name, String pubwin_id, String component_appid){
        return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid="+appid +
                "&redirect_uri="+domain+"retrospectCenter/userIndex.do?computer_name="+ computer_name +
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=" + pubwin_id +
                "&component_appid="+ component_appid +
                "#wechat_redirect";

    }

    @Override
    public JSONObject wakePower(PageData pd) throws Exception{
            new Thread(new thread(pd)).start();
        return null;
    }

    class thread implements Runnable {
        private PageData pd;
        public thread(PageData pd) {
            this.pd = pd;
        }
        @Override
        public void run() {
            try {
                JSONObject result = new JSONObject();

                String store_id = pd.getString("store_id");
                String computer_name = pd.getString("computer_name");

                PageData pdQr = new PageData();
                pdQr.put("store_id", store_id);
                pdQr.put("computer_name", computer_name);
                pdQr = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQr);
                if(pdQr == null){
                    result.put("result", "false");
                    result.put("message", "查找不到该电脑");
                    return ;
                }


                JSONObject param = new JSONObject();
                param.put("computer_name", computer_name);
                param.put("ip", pdQr.getString("ip"));
                param.put("mac_address", pdQr.getString("mac_address"));
                //发送网络唤醒
                System.out.println("网络唤醒param=======" + param.toString());
                userBoardService.wakeToPower(store_id, param);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    class checkIsUserUp implements Runnable{
        private PageData pd ;//store_id,user_id,computer_name
        private PageData pdQr ;
        public checkIsUserUp(PageData pd, PageData pdQr){
            this.pd = pd;
            this.pdQr = pdQr;
        }

        @Override
        public void run() {
            try{

                handelComputerIsUp(pd, pdQr);

                handleUserIsUp(pd);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }



}
