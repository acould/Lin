package com.lk.controller.system.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.role.RoleManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {
	String menuUrl = "account/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;

	/**
	 * 保存
	 * 保存新增人员的信息
	 * @param USER_NAME--用户名,NAME--昵称,ROLE_ID--角色id
	 *        PHONE--登陆所用手机号,STORE_ID--门店id
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {return null;} // 校验权限
		JSONObject json = new JSONObject();
		PageData pds = this.getPageData();
		User user = this.getUser();//获取用户

		String password = new SimpleHash("SHA-1", pds.getString("POSSWARD")).toString();
		PageData pdUser = new PageData();
		String userId = this.get32UUID();
		pdUser.put("USER_ID", userId);
		pdUser.put("USERNAME", pds.getString("USER_NAME"));
		pdUser.put("PASSWORD", password);
		pdUser.put("NAME", pds.getString("NAME"));
		pdUser.put("STATUS", "0");
		pdUser.put("SKIN", "default");
		pdUser.put("INTEGRAL", "0");
		pdUser.put("ROLE_ID", pds.getString("ROLE_ID"));
		pdUser.put("INTENET_ID", user.getINTENET_ID());
		pdUser.put("PHONE", pds.getString("USER_NAME"));
		pdUser.put("STORE_ID", pds.getString("ids"));
		userService.saveU(pdUser);//保存用户信息
		PageData pdStoreUser = new PageData();
		String ids=pds.getString("ids");
		List<String> list=Arrays.asList(ids.split(","));
		for(String o:list){
			pdStoreUser.put("STORE_USER_ID", this.get32UUID());
			pdStoreUser.put("STORE_ID", o);
			pdStoreUser.put("USER_ID", userId);
			storeUserService.save(pdStoreUser);//保存用户门店信息
		}
		json.put("msg", PublicManagerUtil.SUCCESS);
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "保存成功");
		return json;
	}

	/**
	 * 列表
	 * 该门店现有人员详细信息
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Store");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
		if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {// 不是管理员时(非网吧老板)
			if(StringUtil.isEmpty(pd.getString("STORE_ID"))){
				pd.put("STORE_ID", Joiner.on("','").join(storeid));
			}
		    Collection<String> c=new ArrayList<String>();
		    c.add(user.getUSER_ID());
			pd.put("user_id",c);
			
		}
		pd.put("internetId", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData> userList = userService.listUsers(page);// 用户信息列表
		pd.put("STORE_IDS", Joiner.on("','").join(storeid));
		page.setPd(pd);
		List<PageData> storeList = storeService.listUU(page);// 门店信息列表
		if(user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)){
			List<PageData> roleList = roleService.list(page);// 角色信息
			mv.addObject("roleList", roleList);
		}
		mv.setViewName("system/account/account_list");
		mv.addObject("userList", userList);
		mv.addObject("storeList", storeList);
		
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}
	/**
	 * 去新增页面
	 * 点击新增按钮,先去查询相关信息再返回指定页面
	 * @return mv--指定视图和指定信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pdUser = new PageData();
		pdUser.put("internetId", user.getINTENET_ID());
		List<PageData> plist = storeService.getStore(pdUser);        // 根据INTENET_ID进行筛选门店
		List<PageData> userlist = roleService.listByIntenet(pdUser); // 根据INTENET_ID进行筛选角色
		mv.setViewName("system/account/account_add");
		mv.addObject("userlist", userlist);
		mv.addObject("plist", plist);
		mv.addObject("STATE", PublicManagerUtil.INTERNET_STORE_STATE_V0);
		mv.addObject("msg", PublicManagerUtil.SAVE);
		return mv;
	}

	/**
	 * 去修改页面
	 * 点击编辑按钮,先去查询相关信息再返回指定页面
	 * @return mv--指定视图和指定信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdUser = new PageData();
		pdUser.put("USER_ID", this.getRequest().getParameter("USER_ID"));
		pdUser = userService.findByStoreUser(pd);
		PageData pdInternet = new PageData();
		pdInternet.put("internetId", user.getINTENET_ID());
		List<PageData> plist = storeService.listByIntenet(pdInternet);   // 根据INTENET_ID进行筛选门店
		List<PageData> userlist = roleService.listByIntenet(pdInternet); // 根据INTENET_ID进行筛选角色
		String ids=pdUser.getString("storeids");
		if(StringUtil.isNotEmpty(ids)){
			List<String> listids = Arrays.asList(ids.split(","));
			for(String p:listids){
				for(PageData o:plist){
					if(p.equals(o.getString("STORE_ID"))){
						o.put("falg", "checked='checked'");
					}
				}
			}
		}else{
			PageData findByStoreisNot = userService.findByStoreisNot(pd);
				for(PageData o:plist){
					if(o.getString("STORE_ID").equals(findByStoreisNot.getString("store_id"))){
						o.put("falg", "checked='checked'"); 
					}
			}
		}
		
		mv.setViewName("system/account/account_edit");
		mv.addObject("userlist", userlist);
		mv.addObject("STATE", PublicManagerUtil.INTERNET_STORE_STATE_V1);
		mv.addObject("plist", plist);
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pdUser);
		return mv;
	}

	/**
	 * 修改
	 * 点击保存按钮,去保存相关修改信息
	 * @param USERNAME--用户名,PASSWORD--密码,NAME--昵称
	 *        ROLE_ID--角色id,USER_ID--角色id,STORE_ID--门店id
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject Edit(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改account");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("NAME", request.getParameter("NAME"));
		pd.put("ROLE_ID", request.getParameter("ROLE_ID"));
		pd.put("USERNAME", request.getParameter("USER_NAME"));
		String PASSWORD=request.getParameter("POSSWARD");
		if(StringUtil.isNotEmpty(PASSWORD)) {
			PASSWORD = new SimpleHash("SHA-1", request.getParameter("POSSWARD")).toString();
		}
		pd.put("PASSWORD", PASSWORD);
		pd.put("USER_ID", request.getParameter("USER_ID"));
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		
		json=storeUserService.editStore(pd); //保存修改后的门店信息
		pd.put("STORE_ID", request.getParameter("ids"));
		json=userService.editUP(pd);         //保存用户修改信息
		json.put("msg", PublicManagerUtil.SUCCESS);
		return json;
	}

	/**
	 * 删除
	 * 点击删除按钮,删除选中角色
	 * @param pd--指定用户id
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		json=storeUserService.delete(pd); //删除角色门店关联表信息
		json=userService.deleteU(pd);     //删除指定用户信息
		return json;
	}

	/**
	 * 判断用户是否存在
	 * 新增/修改用户时先去判断该用户是否存在
	 * @param USER_NAME--用户名,USER_ID-角色id,STATE--状态(0--新增,1--修改)
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hasU")
	@ResponseBody
	public Object hasU(String USER_NAME, String STATE,String USER_ID) {
		Map<String, String> map = new HashMap<String, String>();
		PageData pdUser = new PageData();
		Pattern pattern = Pattern.compile("^(1[3|4|5|7|8]\\d{9})$");
		Matcher matcher = pattern.matcher(USER_NAME);
		if (!matcher.find()) {// 手机格式不正确
			map.put("result", "error1");
			return AppUtil.returnObject(new PageData(), map);
		}
		try {
			pdUser = this.getPageData();
			pdUser.put("USERNAME", this.getRequest().getParameter("USER_NAME"));
			pdUser.put("PHONE", this.getRequest().getParameter("USER_NAME"));
			List<PageData> list = userService.hasU(pdUser);//通过用户名和手机号去查询是否存在
			if (STATE.equals("0")) {// 0表示新增
				if (list.size() != 0) {// 证明手机号已存在
					map.put("result", PublicManagerUtil.ERROR);
				} else {
					map.put("result", PublicManagerUtil.SUCCESS);
				}
			}
			if (STATE.equals("1")) {//1表示修改
				if(list.size()==0 ) {//要么没
					map.put("result", PublicManagerUtil.SUCCESS);
				}
				else {
					if(USER_NAME.equals(list.get(0).get("PHONE")) && USER_ID.equals(list.get(0).get("USER_ID"))) {//是修改前的手机号
						map.put("result", PublicManagerUtil.SUCCESS);
					}
					else {
						map.put("result", PublicManagerUtil.ERROR);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 批量删除
	 * 通过用户id去删除
	 * @param DATA_IDS--用户id拼接的字符串
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除account");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {return null;} // 校验权限
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userService.deleteAll(ArrayDATA_IDS);//批量删除指定用户
			storeUserService.deleteUser(ArrayDATA_IDS);//批量删除指定用户门店关联表
			pd.put("msg", PublicManagerUtil.OK);
		} else {
			pd.put("msg", PublicManagerUtil.NO);
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 登陆警告
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goToWarn")
	public ModelAndView toWarn() throws Exception {
		System.out.println("该公众号已授权,进入警告页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/account/warn");
		return mv;
	}
	
	/**
	 * 授权警告
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goWarn")
	public ModelAndView goWarn() throws Exception {
		System.out.println("权限未给全,进入警告页面");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/account/warn1");
		return mv;
	}
}
