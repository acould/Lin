package com.lk.controller.internet;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.internet.internetStaff.InternetStaffService;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.List;

/**
 * @author myq
 * @description InternetStaff 网吧业务人员 控制层
 * @create 2019-04-11 11:44
 */
@Controller
@RequestMapping(value = "/internetStaff")
public class InternetStaffController extends BaseController {

    private static String list = "internetStaff/list.do";

    @Autowired
    private InternetStaffService internetStaffService;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception{
        ModelAndView mv = this.getModelAndView();

        User user = this.getUser();//获取用户
        List<PageData> storeList =internetStaffService.getStoreList(user.getINTENET_ID(), user.getROLE_ID(), user.getUSER_ID());

        mv.addObject("storeList", storeList);
        mv.setViewName("internet/internetStaff/staff_list");
        return mv;
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception{
        ModelAndView mv = this.getModelAndView();

        User user = this.getUser();//获取用户
        String url = internetStaffService.getQrcodeUrl(user.getINTENET_ID());

        mv.addObject("url", url);
        mv.setViewName("internet/internetStaff/staff_qr");
        return mv;
    }


    /**
     * 修改
     * @return
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit(String id) throws Exception{
        ModelAndView mv = this.getModelAndView();

        PageData pd = internetStaffService.findById(id);
        pd.put("neck_name", URLDecoder.decode(pd.getString("neck_name"), "utf-8"));

        User user = this.getUser();//获取用户
        List<PageData> storeList =internetStaffService.getStoreList(user.getINTENET_ID(), user.getROLE_ID(), user.getUSER_ID());


        mv.addObject("pd", pd);
        mv.addObject("storeList", storeList);
        mv.setViewName("internet/internetStaff/staff_edit");
        return mv;
    }


    /**
     * 加载首页数据
     * @return
     */
    @RequestMapping(value = "/loadStaffList")
    @ResponseBody
    public JSONObject loadStaffList(Page page) throws Exception{
        JSONObject result = this.getErrorJson();

        User user = this.getUser();//获取用户
        if(user == null){
            result.put("code", "-1");
            result.put("msg", "用户为空");
            result.put("message", "用户为空");
            return result;
        }

        PageData pd = this.getPageData();
        pd.put("internet_id", user.getINTENET_ID());
        return internetStaffService.loadStaffList(pd, page);
    }


    /**
     * 获取扫描的二维码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getQrUrl")
    @ResponseBody
    public JSONObject getQrUrl() throws Exception{
        JSONObject result = this.getErrorJson();

        User user = this.getUser();//获取用户
        if(user == null){
            result.put("message", "用户为空");
            return result;
        }

        String url = internetStaffService.getQrcodeUrl(user.getINTENET_ID());
        result = this.getOkJson();
        result.put("url", url);
        return result;
    }


    /**
     * 编辑保存信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveStaff")
    @ResponseBody
    public JSONObject saveStaff() throws Exception{
        JSONObject result = getErrorJson();

        User user = this.getUser();//获取用户
        if(user == null){
            result.put("message", "用户为空");
            return result;
        }


        //传入参数
        PageData pd = this.getPageData();
        String[] perms = this.getRequest().getParameterValues("perms");
        if(pd.getString("STORE_ID") == null){
            result.put("message", "请选择门店");
            return result;
        }
        pd.put("store_id", pd.getString("STORE_ID"));
        if(perms.length == 0){
            result.put("message", "请选择服务项");
            return result;
        }
        pd.put("internet_id", user.getINTENET_ID());
        return internetStaffService.saveStaff(pd, perms);
    }


    /**
     * 删除信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delStaff")
    @ResponseBody
    public JSONObject delStaff() throws Exception{
        JSONObject result = getErrorJson();
        //传入参数
        PageData pd = this.getPageData();
        if(pd.getString("id") == null){
            result.put("message", "数据错误");
            return result;
        }

        return internetStaffService.delStaff(pd.getString("id"));
    }




    /**
     * 编辑状态
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/editState")
    public JSONObject editState() throws Exception{
        JSONObject result = getErrorJson();
        //传入参数
        PageData pd = this.getPageData();
        if(pd.getString("id") == null){
            result.put("message", "参数错误");
            return result;
        }

        return internetStaffService.editState(pd);
    }

























}
