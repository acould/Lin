package com.lk.controller.system.accountSettings;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.payManager.PayOpenManager;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.tb.Datum.DatumManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Des;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.SmsLogUtil;
/**
 * 说明：用户中心 (个人信息及门店信息)
 * 修改人：李泽华
 * 修改时间：2018-04-15
 */
@Controller
@RequestMapping(value = "/accountSettings")
public class AccountSettingsController extends BaseController {
	
	String menuUrl = "accountSettings/list.do"; // 菜单地址(权限用)
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "smslogService")
	private SmslogManager smslogService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	
	@Resource(name = "payOpenService")
	private PayOpenManager payOpenService;
	@Resource(name = "datumService")
	private DatumManager datumService;
	@Resource(name = "messageService")
	private MessageManager messageService;
	
	/**
	 * 用户中心信息列表展示
	 * 查询当前登录的用户详细信息和其关联的所有门店信息
	 * @param --关键词搜索,page--分页类(默认)
	 * @return pd--含有用户详细信息的封装对象,varList--该用户关联门店详细信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表accountSettings");
		User user = this.getUser();//得到用户

		ModelAndView mv = this.getModelAndView();
		
		//传入keywords，STATE，YH_STATUS
		PageData pd = this.getPageData();
		pd.put("user", user);
		pd.put("page", page);
		
		//查询网吧各门店计费系统，银行卡板顶的情况
		List<PageData> varList = storeService.listv(pd);              
		
		//获取用户信息
		PageData pdUser = new PageData();
		pdUser.put("USER_ID", user.getUSER_ID());
		pdUser = userService.findById(pdUser);
		pdUser.put("keywords", pd.getString("keywords"));
		pdUser.put("STATE", pd.getString("STATE"));
		pdUser.put("YH_STATUS", pd.getString("YH_STATUS"));
		pdUser.put("JL_STATUS", pd.getString("JL_STATUS"));

		
		mv.addObject("varList", varList);
		mv.addObject("pd", pdUser);
		mv.addObject("QX", Jurisdiction.getHC());//按钮权限
		mv.setViewName("system/accountSettings/accountSettings_list");
		return mv;
	}

	/**
	 * 旧密码核对
	 * 说明:用户中心修改密码时核对其旧密码
	 * @param  oldPassword--页面传过来的旧密码
	 * @return json(ajax返回date)
	 * @throws Exception
	 */
	@RequestMapping(value = "oldPassword")
	@ResponseBody
	public JSONObject oldPassword(String oldPassword) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "旧密码核对accountSettings");
		User user = this.getUser();//得到用户

		PageData    pd  = new PageData();
		pd.put("USER_ID", user.getUSER_ID());
		JSONObject json = userService.findById(oldPassword);            //通过用户id查询账户原密码(通过用户id去获取用户的详细信息)
		return json;
	}

	/**
	 * 保存新密码
	 * 通过用户Id去保存新密码
	 * @param newPassword--页面传过来的新密码
	 * @return json(ajax返回值data)
	 * @throws Exception
	 */
	@RequestMapping(value = "savePw")
	@ResponseBody
	public JSONObject savePw(String newPassword) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户中心保存密码accountSettings");
		JSONObject json=userService.userEditPassword(newPassword);      //保存新密码
		return json;
	}
	
	/**
	 * 保存手机号
	 * 验证完毕后去保存新的手机号
	 * @param PHONE--新手机号
	 * @return json(ajax返回值data)
	 * @throws Exception
	 */
	@RequestMapping(value = "savePhone")
	@ResponseBody
	public JSONObject savePhone(String PHONE) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户中心保存手机号accountSettings");
		JSONObject json=userService.userEditPhone(PHONE);                //保存新手机号
		return json;
	}

	/**
	 * 保存邮箱
	 * 保存新的邮箱
	 * @param newEmail--页面所写新邮箱
	 * @return json(ajax返回值data)
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEmail")
	@ResponseBody
	public JSONObject saveEmail(String newEmail) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "用户中心保存邮箱accountSettings");
		JSONObject json=userService.userEditEmail(newEmail);               //保存新邮箱
		return json;
	}

	/**
	 * 判断验证码是否正确（修改密码/换绑手机）
	 * 修改密码/换绑手机时通过手机号和验证码去判断验证码是否正确
	 * @param CODE--页面所输验证码,PHONE页面所传手机号
	 * @return json(ajax返回值data)
	 * @throws Exception
	 */
	@RequestMapping(value = "/verificationCheck")
	@ResponseBody
	public JSONObject verificationCheck(String CODE, String PHONE) throws Exception {
		JSONObject json = smslogService.findByPhone(PHONE,CODE);         //通过电话获取数据,返回结果
		return json;
	}

	/**
	 * 获取验证码(修改密码)
	 * 修改密码时通过用户id获取绑定手机号,并获取验证码
	 * @param USER_ID--用户id
	 * @return map(ajax返回值data)
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPhoneCode")
	@ResponseBody
	public JSONObject getPhone(String USER_ID) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取验证码(修改密码)accountSettings");
		PageData    pd1         = this.getPageData();
		JSONObject  json        = new JSONObject();
		JSONObject  resultJson  = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		User user = this.getUser();//得到用户

		pd1.put("USER_ID", user.getUSER_ID());
		PageData pdUser = userService.findById(pd1);   // 通过用户id获取修改前的手机号(通过用户id去获取用户的详细信息)
		String PHONE = pdUser.getString("PHONE");
		map.put("PHONE", PHONE);
		
		json = smslogService.getByPhone(PHONE);        //根据电话和时间查询数据
		if(json.get("result").equals("frequent")) {
			return json;
		}
		
		int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
		PageData pd = new PageData();
		pd.put("PHONE", PHONE);        // 电话号码
		pd.put("CONTENTS", "您的验证码是：" + mobileCode + ",为了您的账户安全，请不要泄露给其他人。"); // 内容
		pd.put("CODE", mobileCode);    // 验证码
		
		Intenet org = intenetService.getIntenetById(user.getINTENET_ID()); //通过网吧id获取网吧信息
		
		Boolean retMap = SmsLogUtil.uodatePassword(String.valueOf(mobileCode), PHONE); //阿里大鱼短信发送
		
		if (retMap) {
			resultJson.put("message", PublicManagerUtil.SUCCESS);
			resultJson.put("code", Des.encryptNoException(pd.getString("SMSLOG_ID"), Des.SMS_DES_KEY));
			resultJson.put("result", "短信验证码发送成功");
			json=smslogService.save1(pd);                        //短信发送后保存进日志表
			if(json.get("result").equals("false")) {
				return json;
			}
		} else {
			json.put("result", PublicManagerUtil.FAIL);   // 短信发送失败
			json.put("message", "短信发送失败");
		}
		return json;
	}

	/**
	 * 获取验证码(变更手机号)
	 * 变更手机号时通过指定手机号获取验证码
	 * @param PHONE--页面传来的手机号
	 * @return map(ajax返回值data)
	 * @throws Exception
	 */
	@RequestMapping(value = "/verification")
	@ResponseBody
	public Object verification(String PHONE) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取验证码(变更手机号)");
		User user = this.getUser();//得到用户

		PageData pd1 = this.getPageData();
		JSONObject resultJson = new JSONObject();
		JSONObject json = new JSONObject();
		pd1.put("USER_ID", user.getUSER_ID());
		
		json = smslogService.getByPhone(PHONE); //根据电话和时间查询数据
		if(json.get("result").equals("frequent")) {
			return json;
		}
		
		pd1.put("PHONE", PHONE);
		PageData pdUser = userService.findById(pd1);                    // 通过用户id获取修改前的手机号(通过用户id去获取用户的详细信息)
		json = userService.findByUserPhone1(pd1,pdUser,PHONE);          // 通过新手机号去查询是否存在(通过PHONE获取数据)
		if(json.get("result").equals("error1") || json.get("result").equals("error2")) {// 手机格式不正确或者手机号已存在，但不是改之前的手机号
			return json;
		}
		else {                                                          // 手机格式正确且手机号符合要求
			int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
			PageData pd = new PageData();
			pd.put("PHONE", PHONE);        // 电话号码
			pd.put("CONTENTS", "您的验证码是：" + mobileCode + ",为了您的账户安全，请不要泄露给其他人。"); // 内容
			pd.put("CODE", mobileCode);    // 验证码
			
			Intenet org = intenetService.getIntenetById(user.getINTENET_ID()); //通过网吧id获取网吧信息
			
			Boolean retMap = SmsLogUtil.uodatePhone(String.valueOf(mobileCode), PHONE); //阿里大鱼短信发送
			
			if (retMap) {
				resultJson.put("message", PublicManagerUtil.SUCCESS);
				resultJson.put("code", Des.encryptNoException(pd.getString("SMSLOG_ID"), Des.SMS_DES_KEY));
				resultJson.put("result", "短信验证码发送成功");
				json=smslogService.save1(pd);                        //短信发送后保存进日志表
				if(json.get("result").equals("err")) {
					return json;
				}
			} else {
				json.put("result", PublicManagerUtil.FAIL);   // 短信发送失败
				json.put("message", "短信发送失败");
			}
			return json;
		}
	}	

	/**
	 * 去加V特权页面
	 * 通过点击查看套餐/加V图片前往加V详情页面
	 * @param page--分页类
	 * @return mv(指定视图,storeList--门店信息),pd(intenet_name--网吧名称,store_id--门店id)
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAddv")
	public ModelAndView toAddv(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "去加V特权页面");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} //权限验证
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		pd.put("internetId", user.getINTENET_ID());
		List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());  
		// 找到操作用户所在的门店(通过用户id获取相关门店id)
		if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)  && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {  // 不是管理员时(非网吧老板)
			pd.put("STORE_ID", Joiner.on("','").join(storeid));
		}
		page.setPd(pd);
		// 查询该用户所有门店id及名称(管理禁用的不显示)
		List<PageData> storeList = storeService.storeInfo(page); 
		
		//查询网吧信息
		pd.put("INTENET_ID", user.getINTENET_ID());
		PageData pdInternet = intenetService.findById(pd);
		pd.put("intenet_name", pdInternet.get("INTENET_NAME"));
		
		mv.addObject("pd", pd);
		mv.addObject("storeList", storeList);
		mv.setViewName("system/accountSettings/accountSettings_showV");
		return mv;
	}

	/**
	 * 去加V特权页面
	 * 到加V详情页面,去查询该用户所有门店和门店状态
	 * @param STORE_ID--该用户的门店id
	 * @return map(result--ajax返回date,intenet_name--网吧名称,STORE_ID--门店id,store_name--门店名称,
	 *             VSTATE--状态,INSERT_TIME--授权时间,Through_Time--通过时间,EXPIRATION_TIME--到期时间)
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeStore")
	@ResponseBody
	public JSONObject changeStore() throws Exception {
		JSONObject result = new JSONObject();

		User user = this.getUser();//得到用户
		
		//传入STORE_ID
		PageData pd = this.getPageData();
			
		//查询网吧信息
		pd.put("INTENET_ID", user.getINTENET_ID());
		PageData pdInternet = intenetService.findById(pd);
		
		if (StringUtil.isNotEmpty(pd.get("STORE_ID"))) {
			
			PageData pdStoreOpenInfo = storeService.findStoreOpenInfo(pd);
			
			pdStoreOpenInfo.put("intenet_name", pdInternet.get("INTENET_NAME"));
			if(StringUtil.isNotEmpty(pdStoreOpenInfo.get("updatetime"))){
				pdStoreOpenInfo.put("updatetime", pdStoreOpenInfo.get("updatetime").toString());
			}
			// state = 0未加V,1已加V,2加V审核中,3加V未通过,Through_Time,EXPIRATION_TIME status = 0,1,2
			
			result.put("pd", pdStoreOpenInfo);
			result.put("result", "true");
		}else {
			result.put("result", "false");
			result.put("message", "门店信息有误，请刷新重试！");
		}
		return result;
	}
	
	/**
	 * 去加V信息页面(去绑定/查看/重新提交)
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "门店特权列表");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		this.getRequest().setCharacterEncoding("UTF-8");
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> addvLogList = storeService.addvLog(page); // 查询门店加V日志
		if (addvLogList.size() != 0) {
			mv.addObject("List", addvLogList);
		} else {
			pd.put("List", "0");
		}
		// 0表示去绑定,1表示去查看,2表示重新提交(全部) 
		Integer STATE = Integer.parseInt(this.getRequest().getParameter("STATE"));
			List<PageData> varList = storeService.listshowv(page); // 查询门店加V信息
			if(STATE == 0) {//状态为0时
				if(StringUtil.isNotEmpty(varList.get(0).getString("EXPIRATION_TIME"))) {
					pd.put("states", "1");//states为1时,代表此门店加过V切已到期
				}
			}
			//如果状态为0和2时,去获取手机号
			if(STATE == 0 || STATE ==2) {
				pd.put("STORE_ID", varList.get(0).getString("STORE_ID"));
				pd.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);
				page.setPd(pd);
				List<PageData> a = storeService.listPhone(page);//获取揽客绑定手机号
				String phone = a.get(0).get("PHONE").toString();
				varList.get(0).put("PHONE", phone);
			}
			mv.addObject("map", varList.get(0));
		if(STATE==1) { //查看时去修改加V表标识
			storeService.editV(pd);
		}
		List<PageData> a = storeService.queryName(page); // 查询网吧名称
		pd.put("INTENET_NAME", a.get(0).get("INTENET_NAME"));
		
		//阅读消息
		User user = this.getUser();//得到用户
		if(StringUtil.isNotEmpty(pd.getString("STORE_ID"))){
			PageData pdMessage = new PageData();
			pdMessage.put("message_id", pd.getString("STORE_ID"));
			pdMessage.put("state", "1");
			pdMessage.put("user_id", user.getUSER_ID());
			pdMessage.put("read_time", Tools.date2Str(new Date()));
			messageService.edit(pdMessage);
	    }
		
		mv.setViewName("system/storePrivilege/storePrivilege_edit");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/************************开通银联**************************/
	/**
	 * 去开通银联页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goPayOpen")
	public ModelAndView goPayOpen() throws Exception {
		User user = this.getUser();//得到用户

		ModelAndView mv = this.getModelAndView();
		//传入id,store_id
		PageData pd = this.getPageData();
		PageData pddd = pd;
		if(pd.containsKey("store_idd")){
			pd.put("id", pddd.get("idd"));
			pd.put("store_id", pddd.get("store_idd"));
		}
		pd = payOpenService.findById(pd);
		if(StringUtil.isEmpty(pd)){
			pd = new PageData();
			pd.put("id", pddd.get("idd"));
			pd.put("store_id", pddd.get("store_idd"));
		}else{
			//获取资料的图片
			pd.put("intenetdatum_id", pd.getString("id"));
			pd.put("sort", "1");
			List<PageData> imgList = datumService.findByInternet(pd);
			String[] typeList = PublicManagerUtil.typeList;
			for(PageData pdd : imgList){
				for(int i=0;i<typeList.length;i++){
					if(pdd.getString("type").equals(typeList[i])){
						pd.put(typeList[i]+"_url", pdd.getString("url"));
					}
				}
			}
			
			//如果该门店已经开通了，记录已读
			if(StringUtil.isNotEmpty(pd.get("status"))){
				PageData pdMessage = new PageData();
				pdMessage.put("message_id", pd.getString("id"));
				pdMessage.put("state", "1");
				pdMessage.put("user_id", user.getUSER_ID());
				pdMessage.put("read_time", Tools.date2Str(new Date()));
				messageService.edit(pdMessage);
			}
			
			//获取审核日志情况
			PageData pdLog = new PageData();
			pdLog.put("intenetdatum_id", pd.getString("id"));
			List<PageData> logList = payOpenService.findByInternetDatumId(pdLog);
			mv.addObject("logList", logList);
		}
		
		// 网吧公众号--主体信息（商户名称和logo）
		PageData pdStore = new PageData();
		pdStore.put("store_id", pd.getString("store_id"));
		pdStore = storeService.findStoreInfo(pdStore);
		pdStore.put("user_name", user.getNAME());
		if(StringUtil.isEmpty(pd.getString("phone"))) {
			pd.put("phone", user.getPHONE());
		}
		//判断操作用户和admin是否是同一家
		pd.put("user_type", "user");
		PageData pdUser = new PageData();
		pdUser.put("internet_id", user.getINTENET_ID());
		pdUser.put("user_name", "admin");
		pdUser = userService.findByUserNameAndInternetId(pdUser);
		if(StringUtil.isNotEmpty(pdUser)){
			pd.put("user_type", "system");
		}
		
		
		mv.addObject("pdStore", pdStore);
		mv.addObject("pd", pd);
		mv.setViewName("system/payManager/pay_open");
		return mv;
	}
	
	/**
	 * 保存开通
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePay")
	@ResponseBody
	public JSONObject savePay() throws Exception {
		logger.info("保存开通---accountSettings/savePay");
		User user = this.getUser();//得到用户

		
		//传入开通的数据
		PageData pd = this.getPageData();
		pd.put("user", user);
		
		
		//返回
		return payOpenService.savePayOpen(pd);
		
	}
	
	/**
	 * 保存快递单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExpress")
	@ResponseBody
	public JSONObject saveExpress() throws Exception {
		
		//传入intenetdatum_id2，和快递信息
		PageData pd = this.getPageData();
		
		return payOpenService.saveExpress(pd);
	}

	
	
}
