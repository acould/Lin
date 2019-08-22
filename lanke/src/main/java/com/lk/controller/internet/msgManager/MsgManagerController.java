package com.lk.controller.internet.msgManager;

import com.lk.controller.base.BaseController;
import com.lk.entity.LayImgMessage;
import com.lk.entity.LayMessage;
import com.lk.entity.Message;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.sendManager.SendManagerService;
import com.lk.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author myq
 * @description 群发管理——控制层
 * @create 2019-06-02 17:14
 */
@Controller
@RequestMapping(value = "/msgManager")
public class MsgManagerController extends BaseController {

    public static Map<String, String> sendMap = new HashMap<>();//发送时验证senMap中send_token是否存在,定时清空
    @Autowired
    private SendManagerService sendManagerService;

    /******************************************* 请求页面 ********************************/

    /**
     * 消息列表
     * @return
     */
    @RequestMapping(value = "/goList")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();

        mv.addObject("pd", this.getPageData());
        mv.setViewName("internet/msgManager/msg_list");
        return mv;
    }


    /**
     * 去通知编辑页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goNotifyEdit")
    public ModelAndView goNotifyEdit(@RequestParam(value = "type") String type) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();

        if(StringUtil.isEmpty(pd.getString("send_id"))){
            String send_token = "temp_" + StringUtil.get32UUID();
            mv.addObject("send_token", send_token);
            sendMap.put(send_token, "0");//可群发状态
        }

        if(type.equals("server_maintenance")){//服务器维护通知
            mv.setViewName("internet/msgManager/notify/sm_edit");
        }else if(type.equals("equipment_failure")){//设备故障通知
            mv.setViewName("internet/msgManager/notify/ef_edit");
        }else if(type.equals("failure_recovery")){//故障恢复通知
            mv.setViewName("internet/msgManager/notify/fr_edit");
        }

        mv.addObject("pd", pd);
        return mv;
    }


    /**
     * 去图文编辑页面
     * @return
     */
    @RequestMapping(value = "/goNewsEdit")
    public ModelAndView goNewsEdit(){
        ModelAndView mv = this.getModelAndView();

        String send_token = "news_" + StringUtil.get32UUID();
        mv.addObject("send_token", send_token);
        sendMap.put(send_token, "0");//可群发状态

        mv.addObject("pd", this.getPageData());
        mv.setViewName("internet/msgManager/news/n_edit");
        return mv;
    }


    /**
     * 去活动编辑页面
     * @return
     */
    @RequestMapping(value = "/goActivityEdit")
    public ModelAndView goActivityEdit(){
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        if(pd.get("send_id") == null){
            String send_token = "act_" + StringUtil.get32UUID();
            mv.addObject("send_token", send_token);
            sendMap.put(send_token, "0");//可群发状态
        }


        mv.addObject("pd", pd);
        mv.setViewName("internet/msgManager/activity/act_edit");
        return mv;
    }


    /**
     * 去设置群发对象页面
     * @return
     */
    @RequestMapping(value = "/goSetMembers")
    public ModelAndView goSetMembers(){
        ModelAndView mv = this.getModelAndView();

        mv.addObject("pd", this.getPageData());
        mv.setViewName("internet/msgManager/activity/set_edit");
        return mv;
    }


    /******************************************* 请求方法 ********************************/


    /**
     * 获取群发列表
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public LayMessage getList(Page page) throws Exception{
        PageData pd = this.getPageData();

        return sendManagerService.getList(pd, page);
    }

    /**
     * 保存并发送通知模板
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveNotify")
    public Message saveNotify() throws Exception{
        PageData pd = this.getPageData();

        //验证token
        String send_token = pd.getString("send_token");
        if(!sendMap.containsKey(send_token)){
            return Message.error("该页面已失效");
        }
        if(sendMap.get(send_token).equals("1")){
            return Message.error("正在群发");
        }


        String store_ids = pd.getString("store_ids");
        if(pd.getString("send_obj").equals("member") && StringUtil.isEmpty(store_ids)){
            return Message.error("请选择门店列表");
        }

        //判断是否可发送
        String temp_type = pd.getString("temp_type");
        Message m = sendManagerService.loadInitTimes(pd);
        PageData pdd = (PageData) m.getData().get("pd");
        if(temp_type.equals("server_maintenance") && pdd.get("sm_times").toString().equals("0")){
            return Message.error("每天仅可发送3次");
        }else if(temp_type.equals("equipment_failure") && pdd.get("ef_times").toString().equals("0")){
            return Message.error("每天仅可发送3次");
        }else if(temp_type.equals("failure_recovery") && pdd.get("fr_times").toString().equals("0")){
            return Message.error("每天仅可发送3次");
        }

        pd.put("pre", getPre());
        return sendManagerService.saveNotify(pd);
    }


    /**
     * 保存图文(该功能暂时去除)
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveNews")
    public Message saveNews() throws Exception{
        PageData pd = this.getPageData();

        return sendManagerService.saveNews(pd);
    }


    /**
     * 发布图文(该功能暂时去除)
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/issueNews")
    public Message issueNews() throws Exception{
        PageData pd = this.getPageData();

        //验证token
        String send_token = pd.getString("send_token");
        if(!sendMap.containsKey(send_token)){
            return Message.error("该页面已失效");
        }
        if(sendMap.get(send_token).equals("1")){
            return Message.error("正在群发");
        }

        return sendManagerService.issueNews(pd);
    }


    /**
     * 保存并群发活动
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveActivity")
    public Message saveActivity() throws Exception{
        PageData pd = this.getPageData();

        System.out.println("param=========" + pd.toString());
        //验证token
        String send_token = pd.getString("send_token");
        if(!sendMap.containsKey(send_token)){
            return Message.error("该页面已失效");
        }
        if(sendMap.get(send_token).equals("1")){
            return Message.error("正在群发");
        }

        return sendManagerService.saveActivity(pd);
    }



    /**
     * 删除群发记录
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delRecord")
    public Message delRecord() throws Exception{
        PageData pd = this.getPageData();//id

        return sendManagerService.delRecord(pd);
    }

    /**
     * 批量删除
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delMoreRecord")
    public Message delMoreRecord() throws Exception{
        PageData pd = this.getPageData();//ids

        return sendManagerService.delMoreRecord(pd);
    }


    /**
     * 加载模板消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadTempMsg")
    public Message loadTempMsg() throws Exception{
        PageData pd = this.getPageData();


        return sendManagerService.loadTempMsg(pd);
    }


    /**
     * 加载活动消息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadActMsg")
    public Message loadActMsg() throws Exception{
        PageData pd = this.getPageData();

        return sendManagerService.loadActMsg(pd);
    }


    /**
     * 初始化通知可发送次数
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadInitTimes")
    public Message loadInitTimes() throws Exception{
        PageData pd = this.getPageData();

        return sendManagerService.loadInitTimes(pd);
    }


    /******************************************* 可通用方法 ********************************/


    /**
     * 筛选群发对象 store_ids
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getUserList")
    public LayMessage getUserList(Page page) throws Exception{

        //传入store_ids 及以下查询参数
        //会员姓名 mem_name，会员卡号 mem_card，会员性别 mem_sex，会员等级 mem_level
        //卡余额区间 mem_overage1，mem_overage2
        //平均每天消费金额区间 mem_money_average1，mem_money_average2
        //本金余额区间 mem_principal1，mem_principal2
        //平均每天活跃时长区间 mem_time_average1，mem_time_average2
        //近期上网记录 mem_online_record，mem_record_time1，mem_record_time2
        PageData pd = this.getPageData();

        //更新值
        pd = putValue(pd);
//        System.out.println("param-===========" + pd.toString());

        return sendManagerService.getUserList(page, pd);
    }

    /**
     * 将提交的值转换一下
     * @param pd
     * @return
     */
    public PageData putValue(PageData pd) {

        String[] strArr = {"mem_overage", "mem_money_average", "mem_principal", "mem_time_average"};
        for (int i = 0; i < strArr.length; i++) {
            double num1 = 0;
            if (StringUtil.isNotEmpty(pd.getString(strArr[i] + "1"))){
                num1 = Double.parseDouble(pd.getString(strArr[i] + "1"));
            }
            pd.put(strArr[i] + "1", num1);


            if (StringUtil.isNotEmpty(pd.getString(strArr[i] + "2"))) {
                double num2 = Double.parseDouble(pd.getString(strArr[i] + "2"));
                pd.put(strArr[i] + "2", num2);
            }
        }
        return pd;
    }


    /**
     * layui上传图片
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/layUploadImg")
    public LayImgMessage layUploadImg(MultipartFile file) throws Exception{
        User user = this.getUser();


        if(file == null){
            return LayImgMessage.fail("请选择图片", null, null);
        }
        // 执行上传
        String fileName = new Date().getTime() + ".jpg";
        String path = this.getPre() + Const.FILEPATHIMG
                + user.getINTENET_ID() + "/" + fileName;
        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG
                + user.getINTENET_ID() ;
        FileUpload.fileUp(file, filePath, fileName);

        return LayImgMessage.ok(path, fileName);
    }


}
