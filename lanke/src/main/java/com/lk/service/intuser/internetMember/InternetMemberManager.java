package com.lk.service.intuser.internetMember;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.lk.entity.system.User;
import com.lk.util.PageData;

public interface InternetMemberManager {
	
	
	/**
	 * 根据code获取用户，传入参数都不是空时，说明通过微信菜单点击进入
	 * @param pd（传入code，intenetId）
	 * @return
	 * @throws Exception
	 */
	public User getUser(PageData pd) throws Exception;
	
	
	/**
	 * 获取手机验证码
	 * @param pd（传入phone，user_id，internet_id，ip）
	 * @return 返回验证码
	 * @throws Exception
	 */
	public JSONObject getYZM(PageData pd) throws Exception;
	
	
	/**
	 * 前往更换绑定页面
	 * @param pd（传入internet_id，STORE_ID门店id，PHONE手机号）
	 * @return
	 * @throws Exception
	 */
	public ModelAndView changeBind(PageData pd) throws Exception;
	
	
	/**
	 * 请求保存绑定会员
	 * @param pd（user_id,传入身份证sfz，STORE_ID门店id，手机号phone，姓名name，验证码verificationCode）
	 * @return 返回保存结果
	 * @throws Exception
	 */
	public JSONObject saveBind(PageData pd) throws Exception;
	
	
	/**
	 * 请求更换绑定信息
	 * @param pd（user_id,传入，STORE_ID门店id，手机号phone，验证码verificationCode）
	 * @return 返回修改结果
	 * @throws Exception
	 */
	public JSONObject updateBind(PageData pd) throws Exception;

	/**
	 * 判断以前的老会员数据是否需要重新绑定
	 * @param pd（传入user_id,internet_id,backurl）
	 */
	public ModelAndView rebind(PageData pd) throws Exception;

	/**
	 * 加载网吧的门店列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadInternetStore(PageData pd) throws Exception;


	
}
