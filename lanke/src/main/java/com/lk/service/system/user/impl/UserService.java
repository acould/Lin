package com.lk.service.system.user.impl;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.google.common.base.Joiner;
import com.lk.entity.system.SessionUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.memberMarke.impl.MemberMarkeService;
import com.lk.service.system.user.UserManager;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;

/**
 * 系统用户
 * 
 * @author fh313596790qq(青苔) 修改时间：2015.11.2
 */
@Service("userService")
public class UserService implements UserManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 登录判断
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.getUserInfo", pd);
	}

	/**
	 * 更新登录时间
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("UserMapper.updateLastLogin", pd);
	}

	/**
	 * 通过用户ID获取用户信息和角色信息
	 * 
	 * @param (必填:角色id--USER_ID,状态--STATUS)
	 * @return 满足条件的用户信息
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}

	/**
	 * 通过USERNAEME获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUsername", pd);
	}

	public PageData findByUserNameAndInternetId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUserNameAndInternetId", pd);
	}

	/**
	 * 通过PHONE获取数据
	 * 
	 * @param pd(必填:手机号/用户名--PHONE)
	 * @return 指定用户信息
	 * @throws Exception
	 */
	public PageData findByUserPhone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUserPhone", pd);
	}

	/**
	 * 通过PHONE获取数据
	 * 
	 * @param pd(必填:手机号/用户名--PHONE)
	 * @return 指定用户信息
	 * @throws Exception
	 */
	public JSONObject findByUserPhone1(PageData pd, PageData pdUser, String PHONE) throws Exception {
		JSONObject json = new JSONObject();
		Pattern pattern = Pattern.compile(PublicManagerUtil.PHONE_REG);
		Matcher matcher = pattern.matcher(PHONE);
		if (!matcher.find()) {// 手机格式不正确
			json.put("result", PublicManagerUtil.ERROR1);
			return json;
		}
		PageData pdPhone = (PageData) dao.findForObject("UserMapper.findByUserPhone", pd);
		if (pdPhone != null && !PHONE.equals(pdUser.getString("PHONE"))) { // 手机号已存在，但不是改之前的手机号
			json.put("result", PublicManagerUtil.ERROR2);
			json.put("message", "该手机号已存在");
		} else {
			json.put("result", PublicManagerUtil.TRUE);
		}
		return json;
	}

	/**
	 * 通过PHONE和USERNAME获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByPhoneName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByPhoneName", pd);
	}

	/**
	 * 列出某角色下的所有用户
	 * 
	 * @param pd(
	 *            必填:角色id-ROLE_ID)
	 * @return 该角色的所有用户
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllUserByRoldId", pd);

	}

	/**
	 * 保存用户IP
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd) throws Exception {
		dao.update("UserMapper.saveIP", pd);
	}

	/**
	 * 用户列表 通过page查询用户基本信息
	 * 
	 * @param page
	 *            关键词--keywords门店id--STORE_ID,网吧id--internetId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUsers(Page page) throws Exception {
		List<String> list= (List<String>)dao.findForList("UserMapper.userlist", page.getPd());
		PageData pd= new PageData();
		if(StringUtil.isNotEmpty((page.getPd().get("user_id")))){
			list.removeAll((Collection<String>)page.getPd().get("user_id"));
		}
		pd.put("list", Joiner.on("','").join(list));
		page.setPd(pd);
		if(StringUtil.isEmpty(list)){
			return null;
		}
		List<PageData> list2=(List<PageData>) dao.findForList("UserMapper.userlistPage", page);
		for(PageData p:list2){
			String store_name=(String)dao.findForObject("UserMapper.userl", p.getString("USER_ID"));
			p.put("STORE_NAME", store_name);
		}
		return list2;
	}

	/**
	 * 通过邮箱获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUE", pd);
	}

	/**
	 * 通过编号获取数据
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUN", pd);
	}

	/**
	 * 通过id获取数据 通过用户id去获取用户的详细信息
	 * 
	 * @param pd(必填:用户id--USER_ID)
	 * @return (USER_ID--用户id,NAME--用户名,USERNAME--用户名,PASSWORD--密码,EMAIL--邮箱,PHONE--手机号,INTENET_NAME--授权公众号,INSERT_TIME--认证时间)
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findById", pd);
	}

	/**
	 * 通过id获取数据 通过用户id去获取用户的详细信息
	 * 
	 * @param pd(必填:用户id--USER_ID)
	 * @return (USER_ID--用户id,NAME--用户名,USERNAME--用户名,PASSWORD--密码,EMAIL--邮箱,PHONE--手机号,INTENET_NAME--授权公众号,INSERT_TIME--认证时间)
	 * @throws Exception
	 */
	public PageData findByUI(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUI", pd);
	}

	/**
	 * 旧密码核对 通过旧密码判断旧密码是否正确
	 * 
	 * @param pd(必填:pd--封装信息,user--用户缓存信息,oldPassword--所写旧密码)
	 * @return json
	 * @throws Exception
	 */
	@Override
	public JSONObject findById(String oldPassword) throws Exception {
		PageData pd = new PageData();
		JSONObject json = new JSONObject();
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		pd.put("USER_ID", user.getUSER_ID());
		String PASSWORD = new SimpleHash("SHA-1", oldPassword).toString(); // 旧密码加密
		PageData pdUser = (PageData) dao.findForObject("UserMapper.findById", pd); // 旧密码核对
		if (pdUser != null) {
			if (pdUser.getString("PASSWORD").equals(PASSWORD)) { // 旧密码相同
				json.put("result", PublicManagerUtil.SUCCESS);
				json.put("message", "旧密码正确");
			} else { // 旧密码不同
				json.put("result", PublicManagerUtil.ERROR);
				json.put("message", "旧密码错误");
			}
		}
		return json;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param pd--包含用户基本信息
	 *            USER_NAME--用户名,NAME--昵称
	 *            ROLE_ID--角色id,PHONE--登陆所用手机号,STORE_ID--门店id
	 * @throws Exception
	 */
	public void saveU(PageData pd) throws Exception {
		dao.save("UserMapper.saveU", pd);
	}

	/**
	 * 保存用户信息
	 * 
	 * @param pd--包含用户基本信息
	 *            USER_NAME--用户名,NAME--昵称
	 *            ROLE_ID--角色id,PHONE--登陆所用手机号,STORE_ID--门店id
	 * @throws Exception
	 */
	public void saveU1(PageData pd) throws Exception {
		pd.put("LAST_LOGIN", ""); // 最后登录时间
		pd.put("IP", ""); // 最后登录的IP
		pd.put("STATUS", "0");
		pd.put("SKIN", "default");
		pd.put("RIGHTS", "");
		pd.put("INTEGRAL", 0);
		pd.put("EMAIL", "");
		pd.put("ROLE_ID", PublicManagerUtil.BRANCHESROLEID);
		dao.save("UserMapper.saveU", pd);
	}

	/**
	 * 修改用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd) throws Exception {
		dao.update("UserMapper.editU", pd);
	}

	/**
	 * 保存用户修改信息
	 * 
	 * @param pd--修改后的用户信息
	 * @throws Exception
	 */
	public JSONObject editUP(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("UserMapper.editUP", pd);
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "系统繁忙,请稍后再试!");
		}
		return json;
	}

	/**
	 * 修改用户积分
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editUserJf(PageData pd) throws Exception {
		dao.update("UserMapper.editUserJf", pd);
	}

	/**
	 * 保存新手机号/新邮箱/新密码 通过用户id去新手机号/新邮箱/新密码
	 * 
	 * @param pd(必填:用户id--USER_ID;选填:新手机号--PHONE,新邮箱--EMAIL,新密码--PASSWORD)
	 * @throws Exception
	 */
	public void userEdit(PageData pd) throws Exception {
		dao.update("UserMapper.userEdit", pd);
	}

	/**
	 * 保存新密码 通过用户id去保存新密码
	 * 
	 * @param (必填:pd--封装信息,user--缓存用户信息;选填:新密码--PASSWORD)
	 * @throws Exception
	 */
	@Override
	public JSONObject userEditPassword(String newPassword) {
		PageData pd = new PageData();
		JSONObject json = new JSONObject();
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		String PASSWORD = new SimpleHash("SHA-1", newPassword).toString(); // 新密码加密
		pd.put("PASSWORD", PASSWORD);
		pd.put("USER_ID", user.getUSER_ID());
		try {
			dao.update("UserMapper.userEdit", pd); // 保存新密码
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "密码修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试");
		}
		return json;
	}

	/**
	 * 保存新手机号 通过用户id去保存新手机号
	 * 
	 * @param (新手机号--Phone)
	 * @throws Exception
	 */
	@Override
	public JSONObject userEditPhone(String Phone) throws Exception {
		PageData pd = new PageData();
		JSONObject json = new JSONObject();
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		Pattern pattern = Pattern.compile("^(1[3|4|5|7|8]\\d{9})$");
		Matcher matcher = pattern.matcher(Phone);
		if (!matcher.find()) {
			json.put("result", PublicManagerUtil.ERROR); // 格式不正确
			json.put("message", "手机格式不正确");
		}
		try {
			pd.put("PHONE", Phone);
			pd.put("USER_ID", user.getUSER_ID());
			dao.update("UserMapper.userEdit", pd);//先修改用户表手机号
		} catch (Exception e1) {
			e1.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试");
		}
		PageData pd1 = (PageData) dao.findForObject("UserMapper.findAgentId", pd);//通过user_id获取代理商id
		if(StringUtil.isNotEmpty(pd1) && StringUtil.isNotEmpty(pd1.getString("ANGET_ID"))) {//如果代理商id不为空
			pd1.put("PHONE", Phone);
			dao.update("UserMapper.editAgentId", pd1);//通过代理商id修改代理商表手机号
		}
		json.put("PHONE", Phone);
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "手机修改成功");
		return json;
	}

	/**
	 * 保存新邮箱 通过用户id去保存新邮箱
	 * 
	 * @param (新邮箱--newEmail)
	 * @throws Exception
	 */
	@Override
	public JSONObject userEditEmail(String newEmail) {
		PageData pd = new PageData();
		JSONObject json = new JSONObject();
        User user = Jurisdiction.getSessionUser().getUser();//得到用户

		try {
			pd.put("EMAIL", newEmail);
			pd.put("USER_ID", user.getUSER_ID());
			dao.update("UserMapper.userEdit", pd); // 保存新密码
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "邮箱修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
			json.put("message", "系统繁忙,请稍后再试");
		}
		return json;
	}

	/**
	 * 找回密码 通过用户id去保存新密码
	 * 
	 * @param pd(必填:新密码加密--PASSWORD,用户id--USER_ID)
	 * @throws Exception
	 */
	public void editUPwd(PageData pd) throws Exception {
		dao.update("UserMapper.editUPwd", pd);
	}

	/**
	 * 删除用户 删除指定用户信息
	 * 
	 * @param pd--指定用户id
	 * @throws Exception
	 */
	public JSONObject deleteU(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("UserMapper.deleteU", pd);
			json.put("result", PublicManagerUtil.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
		}
		return json;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		dao.delete("UserMapper.deleteAllU", USER_IDS);
	}

	/**
	 * 用户列表(全部)
	 * 
	 * @param USER_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listAllUser", pd);
	}

	/**
	 * 获取总数
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.getUserCount", pd);
	}

	public User getUserByOpenId(String openid) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserByOpenId", openid);
	}

	public void editUserStatus(PageData pd) throws Exception {
		dao.update("UserMapper.editUserStatus", pd);

	}

	/**
	 * 添加门店用户
	 */
	public void saveStoreUser(PageData pd) throws Exception {
		dao.save("UserMapper.StoreUser", pd);

	}

	public PageData findByStoreUser(PageData pd) throws Exception {
		PageData pd1=(PageData) dao.findForObject("UserMapper.findByStoreUser", pd);
		
		String store_name=(String)dao.findForObject("UserMapper.userl", pd1.getString("USER_ID"));
		pd1.put("STORE_NAME", store_name);
		return pd1;
	}

	/**
	 * 批量删除 通过用户id去删除指定用户信息
	 * 
	 * @param pd--ArrayDATA_IDS--用户id数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("UserMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 修改name
	 */
	public void editUPname(PageData pd) throws Exception {
		dao.update("UserMapper.editUPname", pd);
	}

	/**
	 * 揽客用户列表 查询全部网吧管理员(网吧老板)
	 * 
	 * @param page
	 *            (选填:关键词--keywords,用户状态--STATE),
	 * @return 返回用户信息(USERNAME--用户名,PHONE--手机号,EMAIL--邮箱,INTENET_NAME--网吧名称,UPDATE_TIME--授权时间,STATE--用户状态,INTENET_ID--网吧id,USER_ID--用户id)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> lkUsers(Page page, PageData pd) throws Exception {
		List<PageData> userList = (List<PageData>) dao.findForList("IntenetMapper.userlistPage", page);
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).get("STATE").equals(PublicManagerUtil.INTERNET_STORE_STATE_V0)) { // 注册用户没有授权时间,非法用户有可能有但是不给显示
				userList.get(i).put("auth_time", null);
			}
		}
		return userList;
	}

	/*
	 * 获取网吧授权时间
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getTime(Page page) throws Exception {
		return (List<PageData>) dao.findForList("IntenetMapper.getTime", page);
	}

	/**
	 * 揽客用户门店 查询指定用户的所有门店
	 * 
	 * @param page
	 *            (选填:关键词--keywords,网吧id--internetId,province--省,city--市,county--区)
	 * @return (STORE_NAME--门店名称,PROVINCE--省,CITY--市,COUNTY--区,ADDRESS--详细地址,TELEPHONE--手机号,STORE_ID--门店id)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listStores(Page page, PageData pd) throws Exception {
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {// 关键词检索条件
			pd.put("keywords", keywords.trim());
		}
		String PROVINCE = pd.getString("province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("province"));
				pd.put("county", pd.getString("city"));
				pd.put("city", pd.getString("province"));
			}
		}
		page.setPd(pd);
		List<PageData> varList = (List<PageData>) dao.findForList("StoreMapper.listshow", page);
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("city", pd.getString("county"));
			}
		}
		return varList;
	}

	/**
	 * 停用用户 停用指定用户
	 * 
	 * @param page(必填:门店id--STORE_ID)
	 * @return
	 * @throws Exception
	 */
	public JSONObject disableUsers(Page page) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("IntenetMapper.disableUsers", page);
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "停用成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
			json.put("message", "系统繁忙,请稍后再试");
		}
		return json;
	}

	/**
	 * 启用用户 启用指定用户
	 * 
	 * @param page(必填:门店id--STORE_ID)
	 * @return
	 * @throws Exception
	 */
	public JSONObject enableUser(Page page) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("IntenetMapper.enableUser", page);
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "停用成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.ERR);
			json.put("message", "系统繁忙,请稍后再试");
		}
		return json;
	}

	/**
	 * 登陆判断用户状态
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> judge(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.judge", pd);
	}

	/**
	 * 判断公众号用户状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> decideUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.decideUser", pd);
	}

	/**
	 * 通过用户名和手机号去查询是否存在
	 * 
	 * @param pdUser--用户名/手机号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hasU(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.hasU", pd);
	}

	/**
	 * 根据用户名和密码去读取用户信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData checkLogin(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.checkLogin", pd);
	}

	/**
	 * 分页查询系统用户
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listSysUsers(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.listPageSysUser", page);
	}

	/**
	 * 逻辑删除系统用户
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData deleteUser(PageData pd) throws Exception {
		dao.delete("UserMapper.deleteUser", pd);
		return null;
	}

	/**
	 * 通过网吧id查询授权时间
	 * 
	 * @param pd(必填:网吧id--INTENET_ID)
	 * @return INTENET_ID--网吧id,INSERT_TIME--授权时间
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getTime(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserMapper.getTime", pd);
	}

	/**
	 * 根据user_id查找用户，并封装到User类中
	 * 
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public User getUserById(String user_id) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserById", user_id);
	}

	/**
	 * 根据user_id查找用户，并封装到PageData类中
	 * 
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public PageData findByUserId(String user_id) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByUserId", user_id);
	}

	/**
	 * 根据用户名修改代理商账户信息
	 * 
	 * @param USERNAME
	 * @return
	 * @throws Exception
	 */
	@Override
	public void editAgent(PageData pd) throws Exception {
		dao.save("UserMapper.editAgent", pd);
	}

	/**
	 * 根据用户id获取代理商账户信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData selAgent(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.selAgent", pd);
	}

	/**
	 * 根据用户id获取代理商门店信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> selAgentStore(Page page, PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String PROVINCE = pd.getString("province");
		if (StringUtil.isNotEmpty(PROVINCE)) {
			if (PROVINCE.equals("北京") || PROVINCE.equals("天津") || PROVINCE.equals("上海") || PROVINCE.equals("台湾")
					|| PROVINCE.equals("重庆") || PROVINCE.equals("香港") || PROVINCE.equals("澳门")) {
				pd.put("province", pd.getString("province"));
				pd.put("county", pd.getString("city"));
				pd.put("city", pd.getString("province"));
			}
		}
		List<PageData> list = (List<PageData>) dao.findForList("UserMapper.selAgentlistPages", page);
		return list;
	}

	/**
	 * 根据用户id获取代理商所有信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData selAgentShow(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.selAgentShow", pd);
	}

	/**
	 * 根据代理商id获取代理商账户的手机号
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData findeByAgentId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findeByAgentId", pd);
	}

	@Override
	public PageData findByStoreisNot(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserMapper.findByStoreisNot", pd);
	}
}
