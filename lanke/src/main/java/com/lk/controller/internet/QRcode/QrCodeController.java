package com.lk.controller.internet.QRcode;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.QRcode.QrCodeService;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.store.StoreManager;
import com.lk.util.*;
import com.lk.util.data.Operation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author myq
 * @description 网吧二维码--控制层
 * @create 2018-12-13 15:28
 */
@Controller
@RequestMapping(value = "/qrCode")
public class QrCodeController extends BaseController {

    private String general_list = "/qrCode/general.do";
    private static final Logger log = LoggerFactory.getLogger(QrCodeController.class);

    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private StoreManager storeService;
    @Autowired
    private BundUserManager bundUserService;



    /**
     * 后台--开通一码通页面
     * @return
     */
    @RequestMapping(value = "/general")
    public ModelAndView generalList() throws Exception{
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        pd.put("STORE_ID", pd.getString("store_id"));
        PageData pdStore = storeService.findById(pd);

        mv.addObject("pdStore", pdStore);
        mv.setViewName("internet/qrCode/general_list");
        return mv;
    }


    /**
     * 加载二维码列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadQrList")
    public JSONObject loadQrList(Page page){

        JSONObject result = new JSONObject();

        //传入参数（store_id,搜索keywords，分页）
        PageData pd = this.getPageData();

        try{
            result = qrCodeService.loadQrList(pd,page);
        }catch (Exception e){
            log.info("loadQrList===数据加载失败");
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 去导入电脑页面
     * @return
     */
    @RequestMapping(value = "/goImport")
    public ModelAndView goImport() throws Exception{
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        mv.addObject("pd", pd);
        mv.setViewName("internet/qrCode/qrcode_import");
        return mv;
    }


    /**
     * 批量导入电脑名称
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importCN")
    public JSONObject importCN(@RequestParam(value="file", required = true) MultipartFile file,
                               @RequestParam(value = "store_id", required = true) String store_id){

        JSONObject result = new JSONObject();

        //传入参数（store_id）
        PageData pd = this.getPageData();
        pd.put("store_id", store_id);
        pd.put("file", file);

        try {
            result = qrCodeService.importCN(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    /**
     * 下载导入模板
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downloadTemp")
    public ModelAndView downloadTemp(){

        Map<String, Object> dataMap = new HashMap<String, Object>();

        PageData pdHead = new PageData();
        pdHead.put("name", "*为必填项，若想网络唤醒，则同时添加后两项");
        dataMap.put("pdHead", pdHead);

        /********头部*******/
        List<String> titles = new ArrayList<String>();
        titles.add("*电脑编号");
        titles.add("*电脑名称");
        titles.add("ip地址");
        titles.add("mac地址");

        dataMap.put("titles", titles);

        /*********值******/
        List<PageData> varList = new ArrayList<PageData>();
        PageData vpd = new PageData();
        vpd.put("var1", "示例：01");
        vpd.put("var2", "示例：网吧电脑名称");
        vpd.put("var3", " ");
        vpd.put("var4", " ");

        varList.add(vpd);

        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        return new ModelAndView(erv, dataMap);

    }


    /**
     * 去导入电脑页面
     * @return
     */
    @RequestMapping(value = "/goGeneralCode")
    public ModelAndView goGeneralCode() throws Exception{
        ModelAndView mv = this.getModelAndView();

        //传入参数（store_id）
        PageData pd = this.getPageData();

        JSONObject result = new JSONObject();
        try {
            result = qrCodeService.downloadQrs(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mv.addObject("result", result);
        mv.setViewName("internet/qrCode/general_code");
        return mv;
    }


    /**
     * 批量下载二维码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/downloadQrs")
    public JSONObject downloadQrs(){

        JSONObject result = new JSONObject();

        //传入参数（store_id）
        PageData pd = this.getPageData();

        try {
            result = qrCodeService.downloadQrs(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    /**
     * 启用/禁用电脑（单个）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/operation")
    public JSONObject operation(){

        JSONObject result = new JSONObject();

        //传入参数（主键id，status）
        PageData pd = this.getPageData();

        try {
            result = qrCodeService.editStatus(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 禁用所有电脑
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/banAll")
    public JSONObject banAll(){

        JSONObject result = new JSONObject();

        //传入参数（store_id,status）
        PageData pd = this.getPageData();

        try {
            result = qrCodeService.banAll(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 去编辑单个电脑页面
     * @return
     */
    @RequestMapping(value = "/goEditQr")
    public ModelAndView goEditQr() throws Exception{
        ModelAndView mv = this.getModelAndView();

        PageData pd = this.getPageData();
        if(StringUtil.isNotEmpty(pd.getString("id"))){
            pd = qrCodeService.findById(pd);
        }
        mv.addObject("pd", pd);
        mv.setViewName("internet/qrCode/qrcode_edit");
        return mv;
    }

    /**
     * 新增/编辑单个电脑
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveMachine")
    public JSONObject saveMachine(){

        JSONObject result = new JSONObject();

        //传入参数（store_id，serial_number，computer_name，id）
        PageData pd = this.getPageData();

        try {
            result = qrCodeService.saveQr(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    /**
     * 删除单个电脑
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delMachine")
    public JSONObject delMachine(){

        JSONObject result = new JSONObject();

        //传入参数（id）
        PageData pd = this.getPageData();

        try {
            result = qrCodeService.deleteQr(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    /**
     * 删除选择的电脑
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delMore")
    public JSONObject delMore(){

        JSONObject result = new JSONObject();

        //传入参数（id）
        PageData pd = this.getPageData();

        if(StringUtil.isEmpty(pd.getString("store_id"))){
            result.put("result", "false");
            result.put("message", "参数为空");
            return result;
        }

        try {
            result = qrCodeService.delMore(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    /***************************************分割线*******************************************/


    /**
     * 去用户上机、换机、下机页面
     * @return
     */
    @RequestMapping(value = "/goUserUp")
    public ModelAndView goUserUp() throws Exception{
        ModelAndView mv = this.getModelAndView();
        JSONObject result = new JSONObject();

        //传入参数（serial_number,computer_name,imgurl,neck_name,store_name,store_id, state）
        PageData pd = this.getPageData();

        //判断用户是否真的上机
        if(pd.getString("state").equals("1") || pd.getString("state").equals("3")){
            User user = this.getUser();//获取用户

            pd.put("user_id", user.getUSER_ID());
            JSONObject isUserUp = qrCodeService.handleUserIsUp(pd);
            if(isUserUp.containsKey("err_type") && isUserUp.getString("err_type").equals("invaid_board_err")){
                String url = "redirect:/intenetmumber/goCommonTips.do?" +
                        "title="+ URLEncoder.encode(Operation.opera_invalid_board.getTitle(), "utf-8")+
                        "&content="+URLEncoder.encode(Operation.opera_invalid_board.getContent(), "utf-8");
                return new ModelAndView(url);
            }
        }

        if(pd.getString("state").equals("2") || pd.getString("state").equals("3")){
            //上机或换机
            mv.setViewName("internet/qrCode/user_up");
        }else if (pd.getString("state").equals("1")){
            //下机
            mv.setViewName("internet/qrCode/user_online");
        }

        PageData pdBind = pd;
        PageData pdQr = pd;
        result.put("pdBind", pdBind);
        result.put("pdQr", pdQr);
        mv.addObject("result", result);
        return mv;
    }

    /**
     * 去用户下机页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goUserDown")
    public ModelAndView goUserDown() throws Exception{
        ModelAndView mv = this.getModelAndView();
        JSONObject result = new JSONObject();

        //传入参数（serial_number,computer_name,imgurl,neck_name,store_name,store_id, state）
        PageData pd = this.getPageData();



        PageData pdBind = pd;
        PageData pdQr = pd;
        result.put("pdBind", pdBind);
        result.put("pdQr", pdQr);
        mv.addObject("result", result);
        mv.setViewName("internet/qrCode/user_online");
        return mv;
    }


    /**
     * 用户上机/换机
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userUp")
    public JSONObject userUp(){
        JSONObject result = new JSONObject();


        //传入参数（store_id,computer_name,psd）
        PageData pd = this.getPageData();
        logger.info("qrcode--userUp请求参数====" + pd.toString());

        User user = this.getUser();//获取用户

        // 判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            result.put("result", "false");
            result.put("message", "无效的用户");
            result.put("err_type", "user_null_err");
            return result;
        }

        if(StringUtil.isEmpty(pd.getString("computer_name")) || StringUtil.isEmpty(pd.getString("store_id"))){
            result.put("result", "false");
            result.put("message", "缺少参数");
            return result;
        }


        try {
            result = qrCodeService.userUp(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 加载正在上机的页面信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadUserOnline")
    public JSONObject loadUserOnline() throws Exception{
        JSONObject result = new JSONObject();

        //传入参数（store_id,computer_name）
        PageData pd = this.getPageData();

        // 判断用户是否为空
        User user = this.getUser();//获取用户
        if (StringUtil.isEmpty(user)) {
            result.put("result", "false");
            result.put("message", "无效的用户");
            result.put("err_type", "user_null_err");
            return result;
        }


        result = qrCodeService.userOnline(pd);


        return result;
    }


    /**
     * 用户下机
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userDown")
    public JSONObject userDown(){

        //传入参数（store_id,computer_name）
        PageData pd = this.getPageData();

        JSONObject result = new JSONObject();
        try {
            result = qrCodeService.userDown(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



    /**
     * 获取wxConfig参数 微信端底部导航栏请求的
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWxConfig")
    public JSONObject getWxConfig(){

        //传入url
        PageData pd = this.getPageData();

        JSONObject result = new JSONObject();
        try {
            result = qrCodeService.getWxConfig(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取微信页面导航栏数据（用户上下机状态，我的卡券未核销）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getState")
    public JSONObject getState(){

        //传入
        PageData pd = this.getPageData();

        JSONObject result = new JSONObject();
        try {
            result = qrCodeService.getState(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



}
