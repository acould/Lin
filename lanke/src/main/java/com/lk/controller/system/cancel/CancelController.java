package com.lk.controller.system.cancel;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Joiner;
import com.lk.service.system.wechatuser.WeChatUserManager;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 说明：优惠券核销(卡劵核销)
 * 创建人：洪智鹏
 * 创建时间：2017-03-20
 */
@Controller
@RequestMapping(value = "/cancel")
public class CancelController extends BaseController {

    String menuUrl = "cancel/list.do"; //菜单地址(权限用)
    public static Map<String, String> cardMap = new HashMap<String, String>();

    @Resource(name = "cancelService")
    private CancelManager cancelService;
    @Resource(name = "storeService")
    private StoreManager storeService;
    @Resource(name = "storeUserService")
    private StoreUserManager storeUserService;
    @Resource(name = "wechatuserService")
    private WeChatUserManager wechatuserService;


    /**
     * 核销列表
     *
     * @param page  查看审核列表
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表Cancel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//得到用户

        PageData pd = this.getPageData();

        //String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
        String lastLoginEnd = pd.getString("lastLoginEnd");        //结束时间

        if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
            pd.put("lastLoginEnd", lastLoginEnd + " 24:00:00");
        }
        pd.put("internetId", user.getINTENET_ID());
        String keywords = pd.getString("keywords");                //关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
            keywords = URLEncoder.encode(keywords, "utf-8");
            pd.put("word", keywords.trim());
        }

        PageData pdStore = new PageData();
        pdStore.put("internetId", user.getINTENET_ID());
        if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {
            PageData pdStoreUser = new PageData();
            pdStoreUser.put("USER_ID", user.getUSER_ID());
            pdStoreUser = storeUserService.findByUserId(pdStoreUser);   //找到操作用户所在的门店
            pd.put("storeId", pdStoreUser.getString("STORE_ID"));
            pdStore.put("storeId", pdStoreUser.getString("STORE_ID"));
        }

        pd.put("INTERNET_ID", user.getINTENET_ID());
        String USER_ID = this.getRequest().getParameter("USER_ID");
        String CARD_ID = this.getRequest().getParameter("CARD_ID");
        String STORE_ID = this.getRequest().getParameter("STORE_ID");
        pd.put("USER_ID", USER_ID);
        pd.put("CARD_ID", CARD_ID);
        pd.put("STORE_ID", STORE_ID);


        pd.put("store_ids", Joiner.on("','").join(user.getStore_ids()));

        System.out.println("============");
        System.out.println(pd.toString());
        page.setPd(pd);
        List<PageData> varList = cancelService.list(page);    //通过用户id/卡劵id/门店id查询Cancel信息

        for (int i = 0; i < varList.size(); i++) {
            if (varList.get(i).containsKey("NECK_NAME")) {
                varList.get(i).put("NECK_NAME", URLDecoder.decode(varList.get(i).getString("NECK_NAME"), "utf-8"));
            }
            if (varList.get(i).containsKey("STAFF_NECK_NAME")) {
                varList.get(i).put("STAFF_NECK_NAME", URLDecoder.decode(varList.get(i).getString("STAFF_NECK_NAME"), "utf-8"));
            }
        }
        if (pd.containsKey("keywords")) {
            String key = pd.getString("keywords");
            key = URLDecoder.decode(key, "utf-8");
            pd.put("keywords", key);
        }
        pd.put("ROLE_ID", user.getROLE_ID());


        List<PageData> cardList = cancelService.listCard(pdStore);//列表，优惠券(分店只能筛选他自己有的卡券)
        List<PageData> storeList = storeService.listByIntenet(pdStore); //通过网吧id查询现有门店(没有被禁用的)
        List<PageData> userList = cancelService.listUser(pdStore);//列表(操作用户),分店只能看到他自己的操作用户组
        //

        mv.setViewName("system/cancel/cancel_list");
        mv.addObject("varList", varList);
        mv.addObject("userList", userList);
        mv.addObject("cardList", cardList);
        mv.addObject("storeList", storeList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 导出到excel
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出Cancel到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        User user = this.getUser();//得到用户

        this.getRequest().setCharacterEncoding("UTF-8");
        PageData pd =  this.getPageData();
        String keywords = new String(pd.getString("keywords").getBytes("iso-8859-1"), "utf-8");//关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
            keywords = URLEncoder.encode(keywords, "utf-8");
            pd.put("word", keywords);
        }
        //分店员
        if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {
            PageData pdStoreUser = new PageData();
            pdStoreUser.put("USER_ID", user.getUSER_ID());
            pdStoreUser = storeUserService.findByUserId(pdStoreUser);  //找到操作用户所在的门店
            pd.put("storeId", pdStoreUser.getString("STORE_ID"));
        }
        String endTime = this.getRequest().getParameter("lastLoginEnd");
        pd.put("INTERNET_ID", user.getINTENET_ID());
        pd.put("lastLoginEnd", endTime + "24:00:00");

        List<PageData> varOList = cancelService.listExcel(pd); //通过条件查询到需要导出的数据 

        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("操作用户");    //1
        titles.add("核销时间");    //2
        titles.add("优惠券码");    //3
        titles.add("优惠劵名称");   //4
        titles.add("会员昵称");    //5
        titles.add("会员卡号");    //6
        titles.add("卡券类型");    //7
        titles.add("金额");    //8

        dataMap.put("titles", titles);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("NAME"));    //1
            Date date = new Date();
            date = (Date) varOList.get(i).get("UPDATE_TIME");
            String dateNew = Tools.date2Str(date);
            vpd.put("var2", dateNew);    //2
            vpd.put("var3", varOList.get(i).getString("CARD"));    //3
            vpd.put("var4", varOList.get(i).getString("TITLE"));    //4
            vpd.put("var5", URLDecoder.decode(varOList.get(i).getString("NECK_NAME"), "utf-8"));    //5
            vpd.put("var6", varOList.get(i).getString("CARDED"));    //6

            if(varOList.get(i).get("CARD_TICKET").toString().equals("1")){
                vpd.put("var7", "现金券");    //7
            }else if(varOList.get(i).get("CARD_TICKET").toString().equals("2")){
                vpd.put("var7", "非现金券");    //7
            }
            vpd.put("var8", varOList.get(i).getString("CASH_NUMBER"));    //8
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }


    /**
     * 核销页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.setViewName("system/cancel/cancel_edit");
        mv.addObject("msg", PublicManagerUtil.SAVE);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 核销卡券
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cardCancel")
    @ResponseBody
    public JSONObject cardCancel(HttpServletRequest request){
        logBefore(logger, Jurisdiction.getUsername() + "核销卡券cardCancel");

        JSONObject json = new JSONObject();
        String code = request.getParameter("CARD");
        if (StringUtil.isEmpty(code)) {
            json.put("result", "false");
            json.put("message", "优惠券码不能为空！");
            return json;
        }
        User user = this.getUser();//得到用户
        if(StringUtil.isEmpty(user)) {
        	json.put("result", "false");
            json.put("message", "无效的核销用户！");
            return json;
        }
        //判断是否批量调用同一优惠券码，通过限制ip
        String ip = Tools.getIpAddress(request);
        String key = ip + code;
        System.out.println("key=="+key);

        if(cardMap.containsKey(key)) {
        	json.put("result", "false");
            json.put("message", "正在核销中");
            return json;
        }else {
        	//放入key
        	cardMap.put(key, "1");
        }
        try {
            json = cancelService.saveCancel(code);
        }catch (Exception e) {
            e.printStackTrace();
            cardMap.remove(key);
            if(e.getMessage() != null && !e.getMessage().equals("Cannot forward after response has been committed")){
                json.put("result", "false");
                json.put("message", "-1，系统繁忙，请稍后再试");
                return json;
            }
        }
        //移除key
        System.out.println("是否存在key=="+cardMap.containsKey(key));
        cardMap.remove(key);

        logger.info("json的数据==="+json.toString());
        return json;
    }

    /**
     * 后台卡券=现金券，充值中的订单刷新
     * @return
     */
    @RequestMapping(value = "/refreshCancel")
    @ResponseBody
    public JSONObject refreshCancel(){
        logBefore(logger, Jurisdiction.getUsername() + "刷新核销卡券refreshCancel");
        JSONObject result = new JSONObject();

        //传入参数CANCEL_ID
        PageData pd = this.getPageData();

        try {
            result = cancelService.updateRefreshCancel(pd);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","false");
            result.put("message", "-1,系统繁忙，请稍后再试");
        }

        return result;
    }

    /**
     * 后台卡券=现金券--》充值失败--》重新充值
     * @return
     */
    @RequestMapping(value = "/rechargeAgain")
    @ResponseBody
    public JSONObject rechargeAgain(){
        logBefore(logger, Jurisdiction.getUsername() + "rechargeAgain");
        JSONObject result = new JSONObject();

        //传入参数CANCEL_ID
        PageData pd = this.getPageData();

        try {
            result = cancelService.updateRechargeAgain(pd);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","false");
            result.put("message", "-1,系统繁忙，请稍后再试");
        }

        return result;
    }



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }


}
