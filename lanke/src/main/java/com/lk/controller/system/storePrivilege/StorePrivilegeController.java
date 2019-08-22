package com.lk.controller.system.storePrivilege;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
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

import net.sf.json.JSONObject;

/**
 * 说明：门店特权 创建人：李泽华 创建时间：2018-04-08
 */
@Controller
@RequestMapping(value = "/storePrivilege")
public class StorePrivilegeController extends BaseController {
	String menuUrl = "storePrivilege/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "smslogService")
	private SmslogManager smslogService;
	
	@Resource(name = "messageService")
	private MessageManager messageService;


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
		
		// 查询门店加V日志
		List<PageData> addvLogList = storeService.addvLog(page); 
		if (addvLogList.size() != 0) {
			mv.addObject("List", addvLogList);
		} else {
			pd.put("List", "0");
		}
		// 0表示去绑定,1表示去查看,2表示重新提交(全部) 
		Integer STATE = Integer.parseInt(this.getRequest().getParameter("STATE"));
		
		// 查询门店加V信息
		List<PageData> varList = storeService.listshowv(page); 
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

	/**
	 * 判断企业名称唯一性
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/hasName")
	@ResponseBody
	public JSONObject hasName(String name,String STORE_ID) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "判断企业名称唯一性");
		JSONObject result = new JSONObject();
		PageData pd = new PageData();
		pd.put("Company_Name", name);
		List<PageData> list = storeService.hasName(pd);// 去查询企业名称是否重复
		if (list.size() == 0) {// 不重复
			result.put("result", PublicManagerUtil.SUCCESS);
		} else {
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("store_id").equals(STORE_ID)) {
					result.put("result", PublicManagerUtil.SUCCESS);
				}
				else {
					result.put("result", PublicManagerUtil.ERROR);
				}
			}
		}
		return result;
	}
	
	/**
	 * 判断PUBWIN门店id唯一性
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/hasID")
	@ResponseBody
	public JSONObject hasID(String id,String STORE_ID) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "判断门店id是否存在");
		JSONObject result = new JSONObject();
		PageData pd = new PageData();
		pd.put("pubwin_store_id", id);
		List<PageData> list = storeService.hasID(pd);// 去查询门店id是否重复
		if (list.size() == 0) {// 不重复
			result.put("result", PublicManagerUtil.SUCCESS);
		} else {
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).get("store_id").equals(STORE_ID)) {
						result.put("result", PublicManagerUtil.SUCCESS);
					}
					else {
						result.put("result", PublicManagerUtil.ERROR);
					}
				}
		}
		return result;
	}
	
	/**
	 * 判断服务商号是否存在
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/hasSERVICE_ID")
	@ResponseBody
	public JSONObject hasSERVICE_ID() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "判断服务商号是否存在");
		PageData pd = this.getPageData();
		JSONObject json = storeService.hasSERVICE_ID(pd);// 去查询服务商号是否重复
		return json;
	}

	/**
	 * 保存加V信息页面(去绑定/重新提交)
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "加V审核结果");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		User user = this.getUser();//得到用户

		PageData pdUser = new PageData();
		JSONObject result = new JSONObject();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow = dateFm.format(new Date());
		String ODDS=request.getParameter("ODDS");
		if(StringUtil.isEmpty(ODDS)) {//没有查到免费机会的
			pdUser.put("ODDS", "0"); //同一默认为0--免费机会
		}else {
			pdUser.put("ODDS", ODDS); //查到什么给什么
		}
		pdUser.put("STORE_ID", request.getParameter("STORE_ID"));
		String STATE=request.getParameter("STATE1");
		//先去判断数据库该门店状态,然后进行操作(如果是恶意攻击,则返回操作失败)
		List<PageData>list=storeService.selState(pdUser);//通过门店id去查询该门店当前状态
		/*if(!list.get(0).get("STATE").equals(STATE)) {//状态不同
			result.put("message", "操作失败");
			result.put("result", PublicManagerUtil.FALSE);
			return result;
		}*/
		if(list.get(0).get("STATE").equals("0") && !list.get(0).get("STATE").equals(STATE)) {//未绑定--对应STATE为0
			result.put("message", "操作失败");
			result.put("result", PublicManagerUtil.FALSE);
			return result;
		}else if(list.get(0).get("STATE").equals("1") && !list.get(0).get("STATE").equals(STATE)) {//已绑定--对应STATE为1
			result.put("message", "操作失败");
			result.put("result", PublicManagerUtil.FALSE);
			return result;
		}else if(list.get(0).get("STATE").equals("3") && !STATE.equals("2")) {//加V未通过--对应STATE为2
			result.put("message", "操作失败");
			result.put("result", PublicManagerUtil.FALSE);
			return result;
		}
		pdUser.put("pubwin_ip", "");   
		pdUser.put("UPDATETIME", timeNow);
		pdUser.put("UPDATE_USERNAME", user.getUSERNAME());
		// 去绑定/重新提交(0/2)
		pdUser.put("Company_Name", request.getParameter("Company_Name"));
		pdUser.put("pubwin_store_id", request.getParameter("pubwin_store_id"));
		pdUser.put("STORE_PHONE", request.getParameter("STORE_PHONE"));
		pdUser.put("ADD_USERNAME", user.getUSERNAME());
		pdUser.put("ADDTIME", timeNow);
		pdUser.put("msg_from", "lanker");
		pdUser.put("SERVICE_ID", request.getParameter("SERVICE_ID"));
		pdUser.put("CHOOSE_PACKAGE", request.getParameter("CHOOSE_PACKAGE"));
		pdUser.put("chargingtype", request.getParameter("chargingtype"));
		pdUser.put("producttype", request.getParameter("producttype"));
		storeService.saveV(pdUser);
		result.put("message", "操作成功");
		result.put("result", PublicManagerUtil.TRUE);

		//给谭总发短信通知
		SmsLogUtil.storeVApply(Const.tan_phone, request.getParameter("Company_Name"));

		return result;
	}

	/**
	 * 获取验证码
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getcodeA")
	@ResponseBody
	public Object getCode(Page page, String PHONE ,String STORE_ID) throws Exception {
		if ("".equals(PHONE)) {// 如果未填写手机号去查询揽客手机号
			PageData pd = new PageData();
			pd = this.getPageData();
			pd.put("STORE_ID", STORE_ID);
			pd.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);
			page.setPd(pd);
			List<PageData> a = storeService.listPhone(page);//获取揽客绑定手机号
			PHONE = a.get(0).get("PHONE").toString();
		}
		JSONObject resultJson = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = PublicManagerUtil.SUCCESS;
		
		Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
		Matcher matcher = pattern.matcher(PHONE);
		if (!matcher.find()) {// 手机格式不正确
			map.put("result", PublicManagerUtil.ERROR);
			return AppUtil.returnObject(new PageData(), map);
		}
		
		Date endTimeL = new Date();
		Date startTimeL = Tools.sAddMinute(endTimeL, -1); // 前60秒
		List<PageData> list = smslogService.getByPhone("reg", PHONE, startTimeL, endTimeL);
		if (list != null && !list.isEmpty()) {
			errInfo = "frequent";
			map.put("result", errInfo); // 太过频繁
			return AppUtil.returnObject(new PageData(), map);
		}
		int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
		PageData pd = new PageData();
		pd.put("SMSLOG_ID", this.get32UUID()); // 主键
		pd.put("USER_ID", ""); // 用户id(-------)
		pd.put("TYPE", "reg"); // 验证码类型
		pd.put("PHONE", PHONE); // 电话号码
		pd.put("CONTENTS", "您的验证码是：" + mobileCode + ",为了您的账户安全，请不要泄露给其他人。"); // 内容
		pd.put("CODE", mobileCode); // 验证码
		pd.put("CODE_TIME", Tools.date2Str(new Date())); // 发送时间
		pd.put("ADD_TIME", Tools.date2Str(new Date())); // 新增时间
		pd.put("ADD_IP", ""); // 新增ip
		pd.put("INTENET_ID", ""); // 网吧id(----------)
		Boolean retMap = SmsLogUtil.sendMessage(String.valueOf(mobileCode), PHONE, "");
		if (retMap) {
			resultJson.put("message", PublicManagerUtil.SUCCESS);
			resultJson.put("code", Des.encryptNoException(pd.getString("SMSLOG_ID"), Des.SMS_DES_KEY));
			resultJson.put("result", "短信验证码发送成功");
			smslogService.save(pd);
			errInfo = PublicManagerUtil.SUCCESS;
			map.put("result", errInfo); // 短信发送成功
			map.put("phone", PHONE);
			return AppUtil.returnObject(new PageData(), map);
		} else {
			errInfo = PublicManagerUtil.FAIL;
			map.put("result", errInfo); // 短信发送失败
			map.put("phone", PHONE);
			return AppUtil.returnObject(new PageData(), map);
		}
	}

	/**
	 * 判断修改验证码是否正确
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hasY")
	@ResponseBody
	public Object hasCode(String yzm, String PHONE, Page page) throws Exception {
		if ("".equals(PHONE)) {// 如果未填写手机号去查询揽客手机号
			PageData pd = this.getPageData();
			pd.put("STORE_ID", this.getRequest().getParameter("STORE_ID"));
			pd.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);
			page.setPd(pd);
			List<PageData> a = storeService.listPhone(page);//获取揽客注册手机号
			PHONE = a.get(0).get("PHONE").toString();
		}
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = PublicManagerUtil.SUCCESS;
		try {
			PageData SmslogPd = new PageData();
			
			/*//万能验证码流程
			if(!"000000".equals(yzm)) {
			SmslogPd = smslogService.findByPhone(PHONE);
			if (null == SmslogPd || SmslogPd.getString("CODE") == null || !SmslogPd.getString("CODE").equals(yzm)) {
				errInfo = PublicManagerUtil.ERROR;
				return AppUtil.returnObject(new PageData(), map);
			   }
			}*/
			
			//正常流程
			SmslogPd = smslogService.findByPhone(PHONE);//通过手机号获取验证码
			if (null == SmslogPd || SmslogPd.getString("CODE") == null || !SmslogPd.getString("CODE").equals(yzm)) {//比较验证码
				errInfo = PublicManagerUtil.ERROR;
			}
			
			// 验证码5分钟有效，现在时间 > 发送时间 + 5分钟
			if (StringUtil.isNotEmpty(SmslogPd)) {//检验验证码是否过期
				long sendTime = Tools.str2Date(SmslogPd.getString("CODE_TIME")).getTime() + Const.AVAILABLE_TIME;
				long nowTime = new Date().getTime();
				if (nowTime > sendTime) {
					errInfo = "code_invalid";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
