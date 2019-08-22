package com.lk.service.intuser.internetMember.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWUser;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.intuser.myMember.MyMemberManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.smslog.SmslogManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.util.*;
import com.lk.wechat.util.SmsLogUtil;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信端---首页---业务层
 *
 */
@Service("internetMemberService")
public class InternetMemberService implements InternetMemberManager{

	private static Logger logger = LoggerFactory.getLogger(InternetMemberService.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "myopService")
	private MyopManager myopService;
	@Resource(name = "terraceService")
	private TerraceManager terraceService;
	@Resource(name = "smslogService")
	private SmslogManager smslogService;
	@Resource(name = "myMemberService")
	private MyMemberManager myMemberService;
	@Resource(name = "storeService")
    private StoreManager storeService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;


    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;

    private void loggerInfo(String msg, String flag){
		logger.info(msg + "---" + flag + "---");
	}
	/**
	 * 根据code获取用户，传入参数都不是空时，说明通过微信菜单点击进入
	 * @param pd（传入code，intenetId）
	 * @return
	 * @throws Exception
	 */
	public User getUser(PageData pd) throws Exception{
		String code = pd.getString("code");
		String openid = pd.getString("openid");
        String appid = pd.getString("appid");

        logger.info("appid+++++++"+appid+"+++++++=code===="+code);

		Session session = Jurisdiction.getSession();
		User user = new User();
		//判断code是否为空，
		//不是空，说明是通过微信菜单点击进入，根据code调用微信接口获取用户信息，并将user放入session中
		//是空，说明从本地连接进入，从session中获取用户信息
		if (StringUtil.isNotEmpty(code)) {
			logger.info("code不为空===================");
            pd.put("APP_ID", appid);
            PageData pdInternet = intenetService.findByappid(pd).get(0);
			PageData pdTerrace = new PageData();
			pdTerrace.put("INTENET_ID", pdInternet.getString("INTENET_ID"));
			pdTerrace = terraceService.findByInternetId(pdTerrace);
			
			PageData pdMyop = new PageData();
			pdMyop.put("APPID", PublicManagerUtil.APPID);
			pdMyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
			pdMyop = myopService.findByAppId(pdMyop);
			
			JSONObject json = WeChatOpenUtil.getOpenAccessToken(pdTerrace.getString("APP_ID"), code,
					pdMyop.getString("APPID"), pdMyop.getString("COMPONENT_ACCESS_TOKEN"));
			logger.info("===============json===="+json.toString());
			if (json.containsKey("errcode") && json.getString("errcode").equals(PublicManagerUtil.ERRCODE1)) {
				return Jurisdiction.getSessionUser().getUser();
			}
			String openId = json.getString("openid");

			logger.info("openid==========="+openId);
            user = sysuserService.getUserByOpenId(openId);

            sysuserService.getByOpenId(openId);//微信用户信息
			sysuserService.getBundUserByOpenId(openId);//绑定的用户信息
		}else if(StringUtil.isNotEmpty(openid)){
            user = sysuserService.getUserByOpenId(openid);
            sysuserService.getByOpenId(openid);//微信用户信息
			sysuserService.getBundUserByOpenId(openid);//绑定的用户信息
		} else {
			user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
		}
		
		return user;
		
	}
	
	
	/**
	 * 获取手机验证码
	 * @param pdd（传入phone，user_id，internet_id，ip）
	 * @return 返回验证码
	 * @throws Exception
	 */
	public JSONObject getYZM(PageData pdd) throws Exception{
		
		String user_id = pdd.getString("user_id");
		String internet_id = pdd.getString("internet_id");
		String ip = pdd.getString("ip");
		String phone = pdd.getString("phone");
		
		JSONObject resultJson = new JSONObject();
		Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
		Matcher matcher = pattern.matcher(phone);
		if (!matcher.find()) {
			resultJson.put("message", PublicManagerUtil.FALSE);
			resultJson.put("result", "手机号码格式不匹配");
			return resultJson;
		}
		
		Date endTimeL = new Date();
		Date startTimeL = Tools.sAddMinute(endTimeL, -1); // 前60秒
		List<PageData> list = smslogService.getByPhone("reg", phone, startTimeL, endTimeL);
		if (list.size() > 0) {
			resultJson.put("result", "发送验证码太频繁，请稍候再试!!");
			resultJson.put("message", PublicManagerUtil.FALSE);
			return resultJson;
		}
		
		int mobileCode = (int) ((Math.random() * 9 + 1) * 100000);
		PageData pd = new PageData();
		pd.put("SMSLOG_ID", StringUtil.get32UUID()); // 主键
		pd.put("USER_ID", user_id); // 用户id
		pd.put("TYPE", "reg"); // 验证码类型
		pd.put("PHONE", phone); // 电话号码
		pd.put("CONTENTS", "您的验证码是：" + mobileCode + ",为了您的账户安全，请不要泄露给其他人。"); // 内容
		pd.put("CODE", mobileCode); // 验证码
		pd.put("CODE_TIME", Tools.date2Str(new Date())); // 发送时间
		pd.put("ADD_TIME", Tools.date2Str(new Date())); // 新增时间
		pd.put("ADD_IP", ip); // 新增ip
		pd.put("INTENET_ID", user_id); // 网吧id
		Intenet org = intenetService.getIntenetById(internet_id);
		Boolean retMap = SmsLogUtil.sendMessage(String.valueOf(mobileCode), phone, org.getINTENET_NAME());
		if (retMap) {
			resultJson.put("message", PublicManagerUtil.SUCCESS);
			resultJson.put("code", Des.encryptNoException(pd.getString("SMSLOG_ID"), Des.SMS_DES_KEY));
			resultJson.put("result", "短信验证码发送成功");
			smslogService.save(pd);
		} else {
			resultJson.put("message", PublicManagerUtil.FALSE);
			resultJson.put("result", "短信验证码发送失败，请重试");
		}
		
		
		return resultJson;
	}
	
	
	/**
	 * 前往更换绑定页面
	 * @param pd（传入internet_id，STORE_ID门店id，PHONE手机号）
	 * @return
	 * @throws Exception
	 */
	public ModelAndView changeBind(PageData pd) throws Exception{
		ModelAndView mv = new ModelAndView();

		String user_id = pd.getString("user_id");
		String internet_id = pd.getString("internet_id");
		String STORE_ID = pd.getString("STORE_ID");
		String PHONE = pd.getString("PHONE");

		//获取微信用户信息
		PageData pdWechatUser = new PageData();
		pdWechatUser.put("USER_ID", user_id);
		pdWechatUser = wechatuserService.findByUserId(pdWechatUser);
		mv.addObject("user", pdWechatUser);

		//获取已绑定的门店信息
		PageData pdBind = new PageData();
		pdBind.put("userId", user_id);
		pdBind = bunduserService.findByUser(pdBind);


		PageData pdStore = new PageData();
		pdStore.put("STORE_ID", STORE_ID);
		pdStore = storeService.findById(pdStore);
		//如果绑定的门店已经禁用，则默认选一个显示地址
		if (pdStore.getString("STATE").equals("1")) {
			//1表示启用
			mv.addObject("STORE_NAME", pdStore.getString("STORE_NAME"));
			mv.addObject("CITY", pdStore.getString("CITY"));
			mv.addObject("COUNTY", pdStore.getString("COUNTY"));
			mv.addObject("phone", PHONE);
		} else {
			pdStore.put("INTERNET_ID", internet_id);
			List<PageData> storeList = storeService.findNotBan(pdStore);
			if(storeList.size() > 0){
				mv.addObject("STORE_NAME", storeList.get(0).getString("STORE_NAME"));
				mv.addObject("CITY", storeList.get(0).getString("CITY"));
				mv.addObject("COUNTY", storeList.get(0).getString("COUNTY"));
				mv.addObject("phone", PHONE);
			}else{
				mv.addObject("STORE_NAME", "");
				mv.addObject("CITY", "");
				mv.addObject("COUNTY", "");
				mv.addObject("phone", "");
			}
		}


		mv.setViewName("intenetmumber/changeCard");
		return mv;
	}
	
	
	/**
	 * 保存绑定会员
	 * @param pdd（user_id,传入身份证sfz，STORE_ID门店id，手机号phone，姓名name，验证码verificationCode）
	 * @return 返回保存结果
	 * @throws Exception
	 */
	public JSONObject saveBind(PageData pdd) throws Exception{
		loggerInfo("绑定会员信息", "start");
		JSONObject result = new JSONObject();
		
		result = checkBind(pdd);
		if(result.getString("message").equals(PublicManagerUtil.FALSE)){
			return result;
		}
		
		String user_id = pdd.getString("user_id");
		String sfz = pdd.getString("sfz");
		String STORE_ID = pdd.getString("STORE_ID");
		String phone = pdd.getString("phone");
		String name = pdd.getString("name");
		if (StringUtil.isEmpty(sfz)) {
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "请输入身份证");
			return result;
		}
		if (StringUtil.isEmpty(name)) {
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "请输入姓名");
			return result;
		}
		
		//判断是否是重新绑定的
		PageData pdRebind = new PageData();
		pdRebind.put("userId", user_id);
		pdRebind = bunduserService.findByUser(pdRebind);
		if(StringUtil.isNotEmpty(pdRebind)){
			//是重新绑定的，删除之前的绑定数据
			bunduserService.delete(pdRebind);
		}
		
		
		PageData pdBind = new PageData();
		pdBind.put("CARDED", sfz); 
		pdBind.put("STORE_ID", STORE_ID); 
		pdBind = bunduserService.findByCard(pdBind);
		if(StringUtil.isNotEmpty(pdBind)){
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "所选分店已经绑定过该身份证");
			return result;
		}
		

		PageData pd = new PageData();
		pd.put("USER_ID", user_id);
		pd.put("CARDED", sfz);
		pd.put("STORE_ID", STORE_ID);
		pd.put("PHONE", phone);
		pd.put("NAME", name);
		pd.put("BUNDUSER_ID", StringUtil.get32UUID()); // 主键
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 创建时间
		pd.put("IS_SW", "0");//是否顺网对接数据1是0否

		//设置默认值
		pd.put("MEMBER_LEVEL", "0");// 会员级别
		pd.put("USABLE_INTEGRAL", "0");// 可用积分
		pd.put("OVERAGE", "0");// 卡余额
		pd.put("REWARD", "0");// 奖励金额

		//查询所绑定的门店是否已经加v
		PageData store = storeService.findByVId(pd);
		if (StringUtil.isNotEmpty(store) && store.get("v_state").toString().equals("1")) {

			//加v的门店会员，绑定时，需要判断其身份证号和姓名是否一致
			//调顺网接口获取用户数据
			Message2 m2 = userInfoService.getSWUser(STORE_ID, sfz);
			if(m2.getErrcode() == 0){
				SWUser swUser = (SWUser) m2.getData().get("SWUser");
				if(swUser == null){
					result.put("message", PublicManagerUtil.FALSE);
					result.put("result", "您的姓名或会员卡号有误");
					return result;
				}
				if(!swUser.getUser_name().equals(name)){
					//部分网吧用户名称后面加了(读)
					if(!(swUser.getUser_name()).equals(name+"(读)")){
						//中文括号情况
						if(!(swUser.getUser_name()).equals(name+"（读）")){
							result.put("message", PublicManagerUtil.FALSE);
							result.put("result", "身份证号与姓名不匹配，请重新输入");
							return result;
						}
					}
				}
				//更新信息
                pd.put("CARDED", swUser.getId_card());//证件号
                pd.put("CARDID", swUser.getCard_id());//卡号
				pd.put("MEMBER_LEVEL", swUser.getMember_level());// 会员级别
				pd.put("NAME", swUser.getUser_name());// 会员姓名（以前的人查询到信息，就更换）
				pd.put("USABLE_INTEGRAL", swUser.getUsable_integral());// 可用积分
				pd.put("OVERAGE", swUser.getOverage());// 卡余额
				pd.put("REWARD", swUser.getReward());// 奖励金额
				pd.put("IS_SW", "1");//是否顺网对接数据1是0否
			}else{
				result.put("message", "false");
				result.put("result", "您的姓名或会员卡号有误");
				result.put("err_type", m2.getData().get("err_type"));
				return result;
			}

		}
		bunduserService.save(pd);
		result.put("message", PublicManagerUtil.SUCCESS);
		result.put("result", "绑定成功");
		loggerInfo("绑定会员信息", "end");
		return result;
	}
	
	/**
	 * 检查参数是否异常
	 * @param pdd
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkBind(PageData pdd) throws Exception{
		JSONObject result = new JSONObject();
		
		String STORE_ID = pdd.getString("STORE_ID");
		String phone = pdd.getString("phone");
		String verificationCode = pdd.getString("verificationCode");
		
		
		if (StringUtil.isEmpty(STORE_ID)) {
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "请选择门店");
			return result;
		}
		if (StringUtil.isEmpty(phone)) {
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "请输入手机号");
			return result;
		}
		
		if (StringUtil.isEmpty(verificationCode)) {
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "请输入短信验证码");
			return result;
		}
		
		//万能验证码
//		if(verificationCode.equals("zq123456")){
//			result.put("message", "true");
//			return result;
//		}
		
		PageData pdSmslog = new PageData();
		pdSmslog.put("phone", phone);
		pdSmslog = smslogService.findByPhone(phone);
		if (StringUtil.isEmpty(pdSmslog) || !pdSmslog.getString("CODE").equals(verificationCode)) {
			result.put("message", PublicManagerUtil.FALSE);
			result.put("result", "短信验证码不正确");
			return result;
		}
		// 验证码5分钟有效，现在时间 > 发送时间 + 5分钟
		if (StringUtil.isNotEmpty(pdSmslog)) {
			long sendTime = Tools.str2Date(pdSmslog.getString("CODE_TIME")).getTime() + Const.AVAILABLE_TIME;
			long nowTime = new Date().getTime();
			if (nowTime > sendTime) {
				result.put("message", PublicManagerUtil.FALSE);
				result.put("result", "短信验证码已失效");
				return result;
			}
		}
		
		result.put("message", "true");
		return result;
	}
	
	
	/**
	 * 请求更换绑定信息
	 * @param pdd（user_id,传入，STORE_ID门店id，手机号phone，验证码verificationCode）
	 * @return 返回修改结果
	 * @throws Exception
	 */
	public JSONObject updateBind(PageData pdd) throws Exception{
		JSONObject result = new JSONObject();
		
		result = checkBind(pdd);
		if(result.getString("message").equals(PublicManagerUtil.FALSE)){
			return result;
		}
		String user_id = pdd.getString("user_id");
		
		String STORE_ID = pdd.getString("STORE_ID");
		String phone = pdd.getString("phone");
		
		PageData pdBind = new PageData();
		pdBind.put("userId", user_id);
		pdBind = bunduserService.findByUser(pdBind);
		
		PageData pd = new PageData();
		pd.put("STORE_ID", STORE_ID);
		pd.put("PHONE", phone);
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 修改时间
		pd.put("USER_ID", user_id);
		
		// todo 查询门店是否加v 如果加v调用pubwin ol接口，查询改会员信息
		PageData store = storeService.findByVId(pd);
		if (StringUtil.isNotEmpty(store) && store.get("v_state").toString().equals("1")) {

			//调顺网接口获取用户数据
			Message2 m2 = userInfoService.getSWUser(STORE_ID, pdBind.getString("CARDED"));
			if(m2.getErrcode() == 0) {
				SWUser swUser = (SWUser) m2.getData().get("SWUser");
				if (swUser != null) {
					pd.put("MEMBER_LEVEL", swUser.getMember_level());// 会员级别
					pd.put("NAME", swUser.getUser_name());// 会员姓名（以前绑定的人查询到，就更新）
					pd.put("USABLE_INTEGRAL", swUser.getUsable_integral());// 可用积分
					pd.put("OVERAGE", swUser.getOverage());// 卡余额
					pd.put("REWARD", swUser.getReward());// 奖励金额
					pd.put("IS_SW", "1");//是否顺网对接数据1是0否
                    pd.put("CARDED", swUser.getId_card());//证件号
                    pd.put("CARDID", swUser.getCard_id());//卡号
				}else{
					pd.put("MEMBER_LEVEL", "0");// 会员级别
					pd.put("USABLE_INTEGRAL", "0");// 可用积分
					pd.put("OVERAGE", "0");// 卡余额
					pd.put("REWARD", "0");// 奖励金额

					result.put("message", "false");
					result.put("result", "查找不到会员信息");
					return result;
				}
			}else{
				result.put("message", "false");
				result.put("result", m2.getErrmsg());
				return result;
			}
		}else{
			//不是加v的，都设为默认值
			pd.put("MEMBER_LEVEL", "0");// 会员级别
			pd.put("USABLE_INTEGRAL", "0");// 可用积分
			pd.put("OVERAGE", "0");// 卡余额
			pd.put("REWARD", "0");// 奖励金额
		}
		
		bunduserService.editUser(pd);
		result.put("message", "success");
		result.put("result", "更换绑定成功");
		return result;

	}
	
	
	/**
	 * 判断以前的老会员数据是否需要重新绑定
	 * @param pd（传入user_id,internet_id,backurl）
	 */
	public ModelAndView rebind(PageData pd) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		String user_id = pd.getString("user_id");
		String internet_id = pd.getString("internet_id");
		String backurl = pd.getString("backurl");

		//获取微信用户信息
		PageData pdWechatUser = new PageData();
		pdWechatUser.put("USER_ID", user_id);
		pdWechatUser = wechatuserService.findByUserId(pdWechatUser);
		pdWechatUser.put("NECK_NAME", URLDecoder.decode(pdWechatUser.getString("NECK_NAME"),"UTF-8"));
		mv.addObject("user", pdWechatUser);

		//获取之前绑定的数据
		pd.put("userId", user_id);
		PageData pdBind = bunduserService.findByUser(pd);//CARDED,PHONE,STORE_ID,STORE_NAME
		mv.addObject("pdBind", pdBind);
		

		
		mv.addObject("backurl", backurl);
		mv.setViewName("intenetmumber/bindCard");
		
		
		return mv;
	}


	@Override
	public JSONObject loadInternetStore(PageData pd) throws Exception{
		JSONObject result = new JSONObject();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
		if (user == null){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}

		pd.put("internetId", user.getINTENET_ID());
		List<PageData> varList = storeService.listByIntenet(pd);
		// 将已禁用门店排除
		for (int i = 0; i < varList.size(); i++) {
			varList.get(i).put("picture_url", Tools.replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG + varList.get(i).getString("url"));
			if (!varList.get(i).getString("STATE").equals("1")) {
				//state=1，表示启用
				varList.remove(i);
			}
		}


		result.put("varList", varList);
		result.put("result", "true");
		return result;
	}
}
