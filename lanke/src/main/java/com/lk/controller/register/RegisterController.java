
package com.lk.controller.register;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.util.PublicManagerUtil;
import com.lk.controller.base.BaseController;
import com.lk.entity.system.User;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.personnel.impl.PersonnelService;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.user.UserManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Des;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.SmsLogUtil;
import com.lk.wechat.util.WeChatOpenUtil;

/**
 * 核心请求处理类
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController extends BaseController {
    //注册后授权链接
    public static String authorizedUrl1 = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=" + PublicManagerUtil.APPID + "&pre_auth_code=";
    //注册后授权链接
	public static String authorizedUrl2 = "&redirect_uri="+PublicManagerUtil.URL1+"wechat/authorization.do?intenetId=";

    @Resource(name = "intenetService")
    private IntenetManager intenetService;
    @Resource(name = "userService")
    private UserManager userService;
    @Resource(name = "myopService")
    private MyopManager myopService;
    @Resource(name = "smslogService")
    private SmslogManager smslogService;
	@Resource(name = "personnelService")
	private PersonnelService personnelService;
    /**
     * 去注册
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/gotoZhuCe")
    public ModelAndView gotoZhuCe() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/register/register");
        return mv;
    }
    
    /**
     * 授权加载提示页面--访问微信之前的空白提示
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goAccredit")
    public ModelAndView goAccredit() throws Exception {
    	Session session = Jurisdiction.getSession();
    	session.removeAttribute(Const.SESSION_USER);
    	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("system/register/accredit");
        return mv;
    }

    @RequestMapping(value = "/goAccreditError")
    public ModelAndView goAccreditError() throws Exception {
    	Session session = Jurisdiction.getSession();
    	session.removeAttribute(Const.SESSION_USER);
    	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("system/register/accreditError");
        return mv;
    }
    
    /**
     * 用户协议
     *
     * @return mv
     * @throws Exception
     */
    @RequestMapping(value = "/user_agreement")
    public ModelAndView user_agreement() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/register/user_agreement");
        return mv;
    }

    /**
     * 用户注册-授权
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/zhuce")
    @ResponseBody
    public JSONObject register() throws Exception {
    	JSONObject result = new JSONObject();
    	
       	PageData pd = this.getPageData();

        String yzm = pd.getString("yzm");

        //万能验证码
        if(!yzm.equals("zhangqiang123")) {
            //正常流程
            PageData SmslogPd = smslogService.findByPhone(pd.getString("PHONE"));
            if (StringUtil.isEmpty(SmslogPd) || !SmslogPd.getString("CODE").equals(pd.get("yzm"))) {
                result.put("result", "false");
                result.put("message", "验证码错误");
                return result;
            }

            //验证码5分钟有效，现在时间    > 发送时间 + 5分钟
            if(StringUtil.isNotEmpty(SmslogPd)){
                long sendTime = Tools.str2Date(SmslogPd.getString("CODE_TIME")).getTime()+ Const.AVAILABLE_TIME;
                long nowTime = new Date().getTime();
                if(nowTime > sendTime){
                    result.put("result", "false");
                    result.put("message", "验证码已过期");
                    return result;
                }
            }
        }

        
        PageData pdUser = new PageData();
        pdUser.put("USER_ID", this.get32UUID());
        pd.put("INTENET_ID", this.get32UUID());                //主键
        pd.put("CREATE_TIME", Tools.date2Str(new Date()));    //创建时间
        pd.put("UPDATE_TIME", Tools.date2Str(new Date()));    //更新时间
        pd.put("USER_ID", pdUser.get("USER_ID"));             //ID 主键
        pd.put("STATE", "0");                                 //默认为0,未授权
        pdUser.put("LAST_LOGIN", "");                         //最后登录时间
        pdUser.put("IP", "");                                 //IP
        pdUser.put("SKIN", "default");
        pdUser.put("RIGHTS", "");
        pdUser.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("PASSWORD")).toString());    //密码加密
        pdUser.put("USERNAME", pd.getString("USERNAME"));           //用户名
        pdUser.put("INTENET_ID", pd.get("INTENET_ID"));             //网吧ID
        pdUser.put("INTEGRAL", 0);                                  //积分
        pdUser.put("EMAIL", pd.get("EMAIL"));                       //邮箱
        pdUser.put("NAME", pd.get("USERNAME"));                     //用户名
        pdUser.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);  //角色权限
        pdUser.put("PHONE", pd.get("PHONE"));  //手机号
        pdUser.put("STATUS", "0"); //注册默认为0  
        pdUser.put("auth_time", "");//授权时间
        if (null == userService.findByUsername(pd) && null == userService.findByUserPhone(pd) && null == userService.findByUE(pd)) {    //判断用户名是否存在
            intenetService.save(pd);
            userService.saveU(pdUser);                    //执行保存
          //添加默认角色
            Map<String,String> map=new HashMap<>();//店长
            for(int i=0;i<3;i++){
            	if(i==0){
            		map.put("menu_id", "43846292386472279473724500234016732261220177936384");
            		map.put("role_name", "店长");
            		map.put("ROLE_DESCRIBE", "店长拥有管理自己所属门店功能的权限，不可运营公众号和微官网。");
            	}else if(i==1){
            		map.put("menu_id", "752650150491946905895194860114112412437708800");
            		map.put("role_name", "店员");
            		map.put("ROLE_DESCRIBE", "店员仅负责打理自己所属门店，与会员的握手交互，如核销卡券、上货发货、兑奖等。");
            	}else{
            		map.put("menu_id", "47893411465227584350545640563989450032284871461699584");
            		map.put("role_name", "运营者");
            		map.put("ROLE_DESCRIBE", "运营者主要是负责打理运营公众号和微官网，并且有已分配门店的营销管理权限。");
            	}
                PageData pdrole = new PageData();
                pdrole.put("RIGHTS", map.get("menu_id")); // 菜单权限
                pdrole.put("ROLE_ID", this.get32UUID()); // 主键
                pdrole.put("ADD_QX", map.get("menu_id")); // 初始新增权限为否
                pdrole.put("DEL_QX", map.get("menu_id")); // 删除权限
                pdrole.put("EDIT_QX", map.get("menu_id")); // 修改权限
                pdrole.put("CHA_QX", map.get("menu_id")); // 查看权限
                pdrole.put("INTENET_ID", pd.get("INTENET_ID")); // 将所属网吧ID保存
                pdrole.put("ROLE_NAME",map.get("role_name"));
                pdrole.put("PARENT_ID", "1");
                pdrole.put("ROLE_DESCRIBE",map.get("ROLE_DESCRIBE"));
    			personnelService.add(pdrole); // 保存新增角色
            }
            
            
            //end
        }else{
        	result.put("result", "false");
        	result.put("message", "用户名已存在");
        	return result;
        }
        
        result.put("result", "true");
        result.put("backUrl", "/register/scan.do?internet_id="+pd.get("INTENET_ID"));
        return result;
    }
    
    /**
     * 去授权页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/scan")
    public ModelAndView scan() throws Exception {
        ModelAndView mv = this.getModelAndView();
        
        //传入internet_id
        PageData pd = this.getPageData();
        
        mv.setViewName("system/register/scan");
        mv.addObject("internet_id", pd.get("internet_id"));
        return mv;
    }
    
    @RequestMapping(value = "/goAuthoriz")
    @ResponseBody
    public JSONObject goAuthoriz() throws Exception {
    	JSONObject result = new JSONObject();
    	
    	//传入internet_id
        PageData pd = this.getPageData();
        
        PageData pdmyop = new PageData();
        pdmyop.put("APPID", PublicManagerUtil.APPID);
        pdmyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
        //首先从缓存中拿取数据
        JSONObject shouUrl = new JSONObject();
        String url = "";
        try{
        	pdmyop = myopService.findByAppId(pdmyop);
        	String aaa = pdmyop.getString("COMPONENT_ACCESS_TOKEN");
        	JSONObject pre_auth_code = WeChatOpenUtil.getPreAuthCode(PublicManagerUtil.APPID, aaa);
        	String preAuthCode = pre_auth_code.getString("pre_auth_code");
        	
        	url = authorizedUrl1 + preAuthCode + authorizedUrl2 + pd.get("internet_id");
        	String urlLong = url.replaceAll("&", "%26");
        	shouUrl = WeChatOpenUtil.getShotUrl(urlLong);
        }catch(Exception e){
        	e.printStackTrace();
        	result.put("result", "false");
        	result.put("message", "-1，系统繁忙，请稍后再试");
        	return result;
        }
        
        
    	result.put("result", "true");
        result.put("url", shouUrl.get("url_short"));
        result.put("url1", url);
    	return result;
    }

    /**
     * 判断用户名是否存在
     *
     * @return
     */
    @RequestMapping(value = "/hasU")
    @ResponseBody
    public Object hasU() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = PublicManagerUtil.SUCCESS;
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (pd.get("USERNAME").equals("") || pd.get("USERNAME") == null) {
                errInfo = PublicManagerUtil.NULL;
            } else if (userService.findByUsername(pd) != null) {
                errInfo = PublicManagerUtil.ERROR;
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);   //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 判断邮箱是否存在
     *
     * @return
     */
    @RequestMapping(value = "/hasE")
    @ResponseBody
    public Object hasE() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = PublicManagerUtil.SUCCESS;
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (pd.get("EMAIL").equals("") || pd.get("EMAIL") == null) {
            	errInfo = PublicManagerUtil.NULL;
            } else if (userService.findByUE(pd) != null) {
            	errInfo = PublicManagerUtil.ERROR;
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 判断手机号是否存在
     *
     * @return
     */
    @RequestMapping(value = "/hasP")
    @ResponseBody
    public Object hasPhone() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = PublicManagerUtil.SUCCESS;
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (pd.get("PHONE").equals("") || pd.get("PHONE") == null) {
            	errInfo = PublicManagerUtil.NULL;
            } else if (userService.findByUserPhone(pd) != null) {
            	errInfo = PublicManagerUtil.ERROR;
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }


    /**
     * 注册验证码
     *
     * @throws Exception
     */
    @RequestMapping(value = "/getcodeA")
    @ResponseBody
    public Object getCode(String PHONE , String USERNAME) throws Exception {
        JSONObject resultJson = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = PublicManagerUtil.SUCCESS;
        Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
        Matcher matcher = pattern.matcher(PHONE);
        if (!matcher.find()) {
            errInfo = PublicManagerUtil.ERROR;
            map.put("result", errInfo);                //格式不正确
            return AppUtil.returnObject(new PageData(), map);
        }
        
        PageData pd1 = new PageData();
        pd1.put("USERNAME", USERNAME);
        List<PageData> list1=intenetService.findByUserName(pd1);                  //通过用户名获取绑定手机号
        if(StringUtil.isNotEmpty(list1)) {
        	String phone=list1.get(0).get("PHONE").toString();
        	if(!PHONE.equals(phone)) {                                            //手机号不是绑定手机
        		errInfo = PublicManagerUtil.ERROR1;
        		map.put("result", errInfo);            //手机不对
        		return AppUtil.returnObject(new PageData(), map);
        	}
        }
        
        Date endTimeL = new Date();
        Date startTimeL = Tools.sAddMinute(endTimeL, -1); // 前60秒
        List<PageData> list = smslogService.getByPhone("reg", PHONE, startTimeL, endTimeL);
        if (list != null && !list.isEmpty()) {
            errInfo = "frequent";
            map.put("result", errInfo);            //太过频繁
            return AppUtil.returnObject(new PageData(), map);
        }
        int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
        PageData pd = new PageData();
        pd.put("SMSLOG_ID", this.get32UUID()); // 主键
        pd.put("USER_ID", "");                             // 用户id(-------)
        pd.put("TYPE", "reg");                            // 验证码类型
        pd.put("PHONE", PHONE);                           // 电话号码
        pd.put("CONTENTS", "您的验证码是：" + mobileCode + ",为了您的账户安全，请不要泄露给其他人。"); // 内容
        pd.put("CODE", mobileCode);                       // 验证码
        pd.put("CODE_TIME", Tools.date2Str(new Date()));  // 发送时间
        pd.put("ADD_TIME", Tools.date2Str(new Date()));   // 新增时间
        pd.put("ADD_IP", "");                             // 新增ip
        pd.put("INTENET_ID", "");                         // 网吧id(----------)
        Boolean retMap = SmsLogUtil.sendMessage(String.valueOf(mobileCode), PHONE, "");
        if (retMap) {
            resultJson.put("message", PublicManagerUtil.SUCCESS);
            resultJson.put("code", Des.encryptNoException(pd.getString("SMSLOG_ID"), Des.SMS_DES_KEY));
            resultJson.put("result", "短信验证码发送成功");
            smslogService.save(pd);
            errInfo = PublicManagerUtil.SUCCESS;
            map.put("result", errInfo);            //短信发送成功
            return AppUtil.returnObject(new PageData(), map);
        } else {
            errInfo = PublicManagerUtil.FAIL;
            map.put("result", errInfo);            //短信发送失败
            return AppUtil.returnObject(new PageData(), map);
        }
    }

    /**
     * 去找回
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gotoretrieve")
    public ModelAndView gotoRetrieve() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        mv.addObject("pd", pd);
        mv.setViewName("system/retrieve/retrieve");
        return mv;
    }

    /**
     * 修改密码(找回密码)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "zhaohui")
    public ModelAndView zhaohui() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData pdd = new PageData();
        pdd = userService.findByPhoneName(pd);
        if (userService.findByUserPhone(pd) != null) {//执行修改
            String PASSWORD = new SimpleHash("SHA-1", pd.getString("PASSWORD")).toString();    //密码加密
            pd.put("USER_ID", pdd.getString("USER_ID"));
            pd.put("PASSWORD", PASSWORD);
            userService.editUPwd(pd);
            mv.addObject("msg", PublicManagerUtil.SUCCESS);
        } else {
            mv.addObject("msg", PublicManagerUtil.FAILED);
        }
        mv.setViewName("system/retrieve/consequence");
        return mv;
    }

    /**
     * 判断手机号是否存在和用户名、密码匹配
     *
     * @return
     */
    @RequestMapping(value = "/hasPw")
    @ResponseBody
    public Object hasPw() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = PublicManagerUtil.SUCCESS;
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            if (userService.findByPhoneName(pd) == null) {
                errInfo = "mismatch";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }
    
    /**
     * 判断验证码是否正确
     *
     * @return
     */
    @RequestMapping(value = "/hasY")
    @ResponseBody
    public Object hasCode() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData SmslogPd = new PageData();
            SmslogPd = smslogService.findByPhone(pd.getString("PHONE"));
            if (null == SmslogPd || SmslogPd.getString("CODE") == null || !SmslogPd.getString("CODE").equals(pd.get("yzm"))) {
                errInfo = "error";
            }
          //验证码5分钟有效，现在时间    > 发送时间 + 5分钟 
			if(StringUtil.isNotEmpty(SmslogPd)){
				long sendTime = Tools.str2Date(SmslogPd.getString("CODE_TIME")).getTime()+ Const.AVAILABLE_TIME;
				long nowTime = new Date().getTime();
				if(nowTime > sendTime){
					errInfo = "code_invalid";
				}
			}
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 关于我们
     *
     * @return mv
     * @throws Exception
     */
    @RequestMapping(value = "/about")
    public ModelAndView about() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/index/about");
        return mv;
    }

    /**
	 * 发送验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendCode")
	@ResponseBody
	public JSONObject sendCode() throws Exception {
        User user = this.getUser();//获取用户

		//传入phone
		PageData pd = this.getPageData();
		
		//发送验证码
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("request_ip", Tools.getIpAddress(this.getRequest()));
		
		//返回
		return smslogService.sendCode(pd);
	}
    
    //==================================帮助中心==================================
	// 页面访问后台  地址格式    /register/*****     
	/**
	 * 如何设置充值
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay_set.do")
	public ModelAndView pay_set() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/register/pay_set");
		return mv;
	}
	
	/**
	 * 会员如何充值
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user_pay.do")
	public ModelAndView user_pay() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/register/user_pay");
		return mv;
	}
	
	/**
	 * 如何对账
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account_check.do")
	public ModelAndView account_check() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/register/account_check");
		return mv;
	}
	
	/**
	 * 如何会员营销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user_marketing.do")
	public ModelAndView user_marketing() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/register/user_marketing");
		return mv;
	}
	
	/**
	 * 用户协议
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/go_paySet.do")
	public ModelAndView go_paySet1() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/register/pay_set");
		return mv;
	}
	@RequestMapping(value = "/introduction")
	public ModelAndView introduction() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/register/introduction");
		return mv;
	}
    @RequestMapping(value = "/helpCenter")
    public ModelAndView helpCenter() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/register/helpCenter");
        return mv;
    }

	//==================================END==================================
}
