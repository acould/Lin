package com.lk.service.system.user;

import java.util.List;


import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.PageData;


/** 用户接口类
 * @author fh313596790qq(青苔)
 * 修改时间：2015.11.2
 */
public interface UserManager {
	
	/**登录判断
	 * 根据用户名和密码去读取用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception;
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception;
	
	/**
	 * 通过用户ID获取用户信息和角色信息
	 * @param (必填:角色id--USER_ID,状态--STATUS)
	 * @return 满足条件的用户信息
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception;
	/*
	 * 添加门店用户
	 */
	public void saveStoreUser(PageData pd)throws Exception;
	
	/**通过用户openId获取用户信息和角色信息
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public User getUserByOpenId(String openid) throws Exception;
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception;
	
	public PageData findByUserNameAndInternetId(PageData pd)throws Exception;
	/**
	 * 通过PHONE获取数据
	 * @param pd(必填:手机号/用户名--PHONE)
	 * @return 指定用户信息
	 * @throws Exception
	 */
	public PageData findByUserPhone(PageData pd)throws Exception;
	
	/**
	 * 通过PHONE获取数据
	 * @param pdUser 
	 * @param PHONE 
	 * @param pd(必填:手机号/用户名--PHONE)
	 * @return 指定用户信息
	 * @throws Exception
	 */
	public net.sf.json.JSONObject findByUserPhone1(PageData pd, PageData pdUser, String PHONE)throws Exception;
	
	/**通过PHONE和USERNAME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByPhoneName(PageData pd)throws Exception;
	
	/**
	 * 列出某角色下的所有用户
	 * @param pd( 必填:角色id-ROLE_ID)
	 * @return 该角色的所有用户
	 * @throws Exception
	 */
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception;
	
	/**
	 * 用户列表
	 * 通过page查询用户基本信息
	 * @param page 关键词--keywords门店id--STORE_ID,网吧id--internetId
	 * @throws Exception
	 */
	public List<PageData> listUsers(Page page)throws Exception;
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd)throws Exception;
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * 通过用户id去获取用户的详细信息
	 * @param pd(必填:用户id--USER_ID)
	 * @return (USER_ID--用户id,NAME--用户名,USERNAME--用户名,PASSWORD--密码,EMAIL--邮箱,PHONE--手机号,INTENET_NAME--授权公众号,INSERT_TIME--认证时间)
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * 通过用户id去获取用户的详细信息
	 * @param pd(必填:用户id--USER_ID)
	 * @return (USER_ID--用户id,NAME--用户名,USERNAME--用户名,PASSWORD--密码,EMAIL--邮箱,PHONE--手机号,INTENET_NAME--授权公众号,INSERT_TIME--认证时间)
	 * @throws Exception
	 */
	public PageData findByUI(PageData pd)throws Exception;
	
	/**
	 * 旧密码核对
	 * 通过旧密码判断旧密码是否正确
	 * @param pd(必填:pd--封装信息,user--用户缓存信息,oldPassword--所写旧密码)
	 * @return json
	 * @throws Exception
	 */
	public net.sf.json.JSONObject findById(String oldPassword) throws Exception;
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	/**
	 * 保存用户修改信息
	 * @param pd--修改后的用户信息
	 * @return 
	 * @throws Exception
	 */
	public net.sf.json.JSONObject editUP(PageData pd)throws Exception;
	
	/**
	 * 找回密码
	 * 通过用户id去保存新密码
	 * @param pd(必填:新密码加密--PASSWORD,用户id--USER_ID)
	 * @throws Exception
	 */
	public void editUPwd(PageData pd)throws Exception;
	
	/**
	 * 保存新手机号/新邮箱/新密码
	 * 通过用户id去新手机号/新邮箱/新密码
	 * @param pd(必填:用户id--USER_ID;选填:新手机号--PHONE,新邮箱--EMAIL,新密码--PASSWORD)
	 * @throws Exception
	 */
	public void userEdit(PageData pd)throws Exception;
	
	/**
	 * 保存新密码
	 * 通过用户id去保存新密码
	 * @param (必填:pd--封装信息,user--缓存用户信息;选填:新密码--PASSWORD)
	 * @throws Exception
	 */
	public net.sf.json.JSONObject userEditPassword(String newPassword);
	
	/**
	 * 保存新手机号
	 * 通过用户id去保存新手机号
	 * @param (新手机号--Phone)
	 * @throws Exception
	 */
	public net.sf.json.JSONObject userEditPhone(String Phone) throws Exception;
	
	/**
	 * 保存新邮箱
	 * 通过用户id去保存新邮箱
	 * @param (新邮箱--newEmail)
	 * @throws Exception
	 */
	public net.sf.json.JSONObject userEditEmail(String newEmail);
	
	/**修改用户积分接口
	 * @param pd
	 * @throws Exception
	 */
	public void editUserJf(PageData pd)throws Exception;
	
	
	/**
	 * 保存用户信息
	 * @param pd--包含用户基本信息 USER_NAME--用户名,NAME--昵称
	 *        ROLE_ID--角色id,PHONE--登陆所用手机号,STORE_ID--门店id
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**
	 * 保存用户信息
	 * @param pd--包含用户基本信息 USER_NAME--用户名,NAME--昵称
	 *        ROLE_ID--角色id,PHONE--登陆所用手机号,STORE_ID--门店id
	 * @throws Exception
	 */
	public void saveU1(PageData pd)throws Exception;
	
	/**
	 * 删除用户
	 * 删除指定用户信息
	 * @param pd--指定用户id
	 * @return 
	 * @throws Exception
	 */
	public net.sf.json.JSONObject deleteU(PageData pd)throws Exception;
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception;
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pd)throws Exception;
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(PageData pd)throws Exception;

	public void editUserStatus(PageData userPd)throws Exception;

	public PageData findByStoreUser(PageData pd)throws Exception;
	
	public PageData findByStoreisNot(PageData pd)throws Exception;
	/**
	 * 批量删除
	 * 通过用户id去删除指定用户信息
	 * @param pd--ArrayDATA_IDS--用户id数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public void editUPname(PageData pd)throws Exception;

	/**
	 * 揽客用户列表
	 * 查询全部网吧管理员(网吧老板)
	 * @param page (选填:关键词--keywords,用户状态--STATE),
	 * @param pd 
	 * @return 返回用户信息(USERNAME--用户名,PHONE--手机号,EMAIL--邮箱,INTENET_NAME--网吧名称,UPDATE_TIME--授权时间,STATE--用户状态,INTENET_ID--网吧id,USER_ID--用户id)
	 * @throws Exception
	 */
	public List<PageData> lkUsers(Page page, PageData pd) throws Exception;

	/**
	 * 揽客用户门店
	 * 查询指定用户的所有门店
	 * @param page (选填:关键词--keywords,网吧id--internetId,province--省,city--市,county--区)
	 * @param pd 
	 * @return (STORE_NAME--门店名称,PROVINCE--省,CITY--市,COUNTY--区,ADDRESS--详细地址,TELEPHONE--手机号,STORE_ID--门店id)
	 * @throws Exception
	 */
	public List<PageData> listStores(Page page, PageData pd) throws Exception;
	
	/**
	 * 停用用户
	 * 停用指定用户
	 * @param page(必填:门店id--STORE_ID)
	 * @return 
	 * @throws Exception
	 */
	public net.sf.json.JSONObject disableUsers(Page page) throws Exception;
	
	/**
	 * 启用用户
	 * 启用指定用户
	 * @param page(必填:门店id--STORE_ID)
	 * @return 
	 * @throws Exception
	 */
	public net.sf.json.JSONObject enableUser(Page page) throws Exception;
	/**
	 * 登陆判断用户状态
	 * @param pd
	 * @throws Exception 
	 */
	public List<PageData> judge(PageData pd) throws Exception;
	/*
	 * 微信公众号判断用户状态
	 */
	public List<PageData> decideUser(PageData pd) throws Exception;
	
	/**
	 * 通过用户名和手机号去查询是否存在
	 * @param pdUser--用户名/手机号
	 * @throws Exception
	 */
	public List<PageData> hasU(PageData pdUser) throws Exception;

	/**
	 * 根据用户名和密码去读取用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData checkLogin(PageData pd)throws Exception;

	/**
	 * 分页查询系统用户
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSysUsers(Page page)throws Exception;

	/**
	 * 逻辑删除系统用户
	 * @param pd
	 * @throws Exception
	 */
	public PageData deleteUser(PageData pd)throws Exception;
	
	/**
	 * 通过网吧id查询授权时间
	 * @param pd(必填:网吧id--INTENET_ID)
	 * @return INTENET_ID--网吧id,INSERT_TIME--授权时间
	 * @throws Exception
	 */
	public List<PageData> getTime(PageData pd) throws Exception;

	/**
	 * 根据user_id查找用户，并封装到User类中
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public User getUserById(String user_id) throws Exception;

	
	/**
	 * 根据user_id查找用户，并封装到PageData类中
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public PageData findByUserId(String user_id) throws Exception;

	/**
	 * 根据用户名修改代理商账户信息
	 * @param USERNAME
	 * @return
	 * @throws Exception
	 */
	public void editAgent(PageData pd) throws Exception;

	/**
	 * 根据用户id获取代理商账户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selAgent(PageData pd) throws Exception;

	/**
	 * 根据用户id获取代理商门店信息
	 * @param page
	 * @param pd 
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selAgentStore(Page page, PageData pd) throws Exception;

	/**
	 * 根据用户id获取代理商所有信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selAgentShow(PageData pd) throws Exception;

	/**
	 * 根据代理商id获取代理商账户的手机号
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findeByAgentId(PageData pd) throws Exception;

}
