package com.lk.controller.system.personnel;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.model.SavedByEntry;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.system.Menu;
import com.lk.entity.system.Role;
import com.lk.entity.system.User;
import com.lk.service.system.menu.MenuManager;
import com.lk.service.system.personnel.impl.PersonnelService;
import com.lk.service.system.role.RoleManager;
import com.lk.service.system.user.UserManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.RightsHelper;
import com.lk.util.Tools;

/**
 * 说明：角色管理
 */
@Controller
@RequestMapping(value = "/personnel")
public class PersonnelController extends BaseController {

	String menuUrl = "personnel/List.do"; // 菜单地址(权限用)
	@Resource(name = "menuService")
	private MenuManager menuService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "personnelService")
	private PersonnelService personnelService;

	/**
	 * 角色管理 展示该角色旗下所有角色信息
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("INTENET_ID", user.getINTENET_ID()); // 获取网吧ID
			if (pd.getString("ROLE_ID") == null
					|| "".equals(pd.getString("ROLE_ID").trim())) {
				pd.put("ROLE_ID", "1"); // 默认列出第一组角色(初始设计系统用户和会员组不能删除)
			}
			PageData fpd = new PageData();
			fpd.put("ROLE_ID", "0");
			List<Role> roleList = roleService.listAllRolesByPId(fpd); // 列出组(页面横向排列的一级组)
			List<Role> roleList_z = personnelService.listAllRolesByPId(pd); // 列出此组下架角色
			pd = roleService.findObjectById(pd); // 取得点击的角色组(横排的)
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("system/personnel/personnel_list");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面 新增角色操作
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Model model) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		StringBuilder sb=listAllMenu(pd,"toAdd");
		mv.addObject("msg", "add");
		mv.addObject("sb",sb.toString());
		mv.setViewName("system/personnel/personnel_edit");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存新增角色 保存新增角色信息
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "新增角色");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("RIGHTS", ""); // 菜单权限
			pd.put("ROLE_ID", pd.getString("ROLE_ID")); // 主键
			pd.put("ADD_QX", "0"); // 初始新增权限为否
			pd.put("DEL_QX", "0"); // 删除权限
			pd.put("EDIT_QX", "0"); // 修改权限
			pd.put("CHA_QX", "0"); // 查看权限
			pd.put("INTENET_ID", user.getINTENET_ID()); // 将所属网吧ID保存
			personnelService.add(pd); // 保存新增角色
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("msg", PublicManagerUtil.FAILED);
		}
		saveMenuqx(pd);
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 请求编辑 前往修改页面
	 * 
	 * @param ROLE_ID
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String ROLE_ID ,Model model) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = personnelService.findObjectById(pd); // 通过id查找角色信息
			StringBuilder sb=listAllMenu(pd,"");
			mv.addObject("msg", "add");
			mv.addObject("sb",sb.toString());
			mv.addObject("msg", PublicManagerUtil.EDIT);
			mv.addObject("pd", pd);
			mv.setViewName("system/personnel/personnel_edit");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存修改 保存修改后角色信息
	 * 
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "修改角色");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			personnelService.edit(pd); // 保存角色修改信息(通过角色id修改修改权限)
			saveMenuqx(pd);
			mv.addObject("msg", PublicManagerUtil.SUCCESS);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("msg", PublicManagerUtil.FAILED);
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除角色 通过角色id删除指定角色信息
	 * 
	 * @param ROLE_ID
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object deleteRole(@RequestParam String ROLE_ID) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "删除角色");
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		String errInfo = "";
		try {
			pd.put("ROLE_ID", ROLE_ID);
			List<Role> roleList_z = personnelService.listAllRolesByPId(pd); // 列出此部门的所有下级
			if (roleList_z.size() > 0) {
				errInfo = PublicManagerUtil.FALSE; // 下级有数据时，删除失败
			} else {
				List<PageData> userlist = userService.listAllUserByRoldId(pd); // 列出此角色下的所有用户
				if (userlist.size() > 0) { // 此角色已被使用就不能删除
					errInfo = PublicManagerUtil.FALSE1;
				} else {
					personnelService.deleteRoleById(ROLE_ID); // 执行删除(通过角色id删除该角色信息)
					errInfo = PublicManagerUtil.SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}

	//获得角色菜单
	public StringBuilder listAllMenu(PageData pd,String msg)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		try {

		User user = this.getUser();//获取用户

		Session session = Jurisdiction.getSession();
		User userr = (User)session.getAttribute(Const.SESSION_USERROL);		//读取session中的用户信息(含角色信息)
		if(null == userr){
			user = userService.getUserAndRoleById(user.getUSER_ID());		//通过用户ID读取用户信息和角色信息
		}else{
			user = userr;
		}
		Role role = user.getRole();											//获取登录用户角色
		String roleRights = role!=null ? role.getRIGHTS() : "";				//登陆用户角色权限(菜单权限)
		List<Menu> allmenuListUser = menuService.listAllMenuQx("0");		//获取所有菜单
		if(Tools.notEmpty(roleRights)){
			allmenuListUser = this.readMenuUser(allmenuListUser, roleRights);//根据当前登录角色权限获取本权限的菜单列表（this.readMenuUser）
		}
		List<Menu> menuList2 = new ArrayList<Menu>();
			//拆分菜单
		Menu menu =null;
			for(int i=0;i<allmenuListUser.size();i++){
				menu = allmenuListUser.get(i);
				if(!"1".equals(menu.getMENU_TYPE())){
					menuList2.add(menu);
				}
			}
		List<Menu> menuListA = this.readMenu(menuList2, "");
			if(!"toAdd".equals(msg)){
				Role roleuserA = personnelService.getRoleById(pd.getString("ROLE_ID")); // 根据当前点击角色ID获取角色对象
				String roleRightsUserCha = roleuserA.getCHA_QX();
				menuListA = this.readMenu(menuList2, roleRightsUserCha);
			}
		/*（菜单权限）*/
			//根据角色权限处理菜单权限状态(递归处理)
		JSONArray arr = JSONArray.fromObject(menuListA);
		String json = arr.toString();
		json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
		for (Menu menu1:menuListA) {
			sb.append("<div class=\"layui-form-item\">");
			sb.append("<label class=\"layui-form-label\">"+menu1.getMENU_NAME()+"：</label>");
			sb.append("<div class=\"layui-input-block checkbox_item\"  data-id='"+menu1.getMENU_ID()+"' >");
			if("#".equals(menu1.getMENU_URL())){
				for (Menu menu2:menu1.getSubMenu()) {
					sb.append(listLoopMenu(menu2,""));
				}
			}
			sb.append("</div>");
			sb.append("</div>");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}
	
	public StringBuilder listLoopMenu(Menu menu,String ID){
		StringBuilder sb=new StringBuilder();
		if("#".equals(menu.getMENU_URL())){
			for (Menu menun:menu.getSubMenu()) {
				sb.append(listLoopMenu(menun,menu.getMENU_ID()));
			}
		}else{
			sb.append("<input type=\"checkbox\" "+(menu.isHasMenu()?"checked='checked'":"")+"data-type=\""+ID+"\" data-id=\""+menu.getMENU_ID()+"\" name=\"limit\" lay-skin=\"primary\"  title=\""+menu.getMENU_NAME()+"\">");
		}
		return sb;
	}
	
	/**
	 * 显示菜单、增删改查列表ztree(菜单、增删改查授权菜单) 编辑权限
	 * 
	 * @param model
	 * @return mv--指定视图及信息
	 */
	@RequestMapping(value = "/menuqx")
	public ModelAndView listAllMenu(Model model, String ROLE_ID)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			User user = this.getUser();//获取用户
			Session session = Jurisdiction.getSession();
			User userr = (User) session.getAttribute(Const.SESSION_USERROL); // 读取session中的用户信息(含角色信息)
			if (null == userr) {
				user = userService.getUserAndRoleById(user.getUSER_ID()); // 通过用户ID读取用户信息和角色信息
			} else {
				user = userr;
			}
			//String USERNAME = user.getUSERNAME(); // 获取登录用户名称
			Role role = user.getRole(); // 获取登录用户角色
			String roleRights = role != null ? role.getRIGHTS() : ""; // 登陆用户角色权限(菜单权限)
			List<Menu> allmenuListUser = menuService.listAllMenuQx("0"); // 获取所有菜单
			if (Tools.notEmpty(roleRights)) {
				allmenuListUser = this
						.readMenuUser(allmenuListUser, roleRights);// 根据当前登录角色权限获取本权限的菜单列表（this.readMenuUser）
			}
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			// 拆分菜单
			Menu menu = null;
			for (int i = 0; i < allmenuListUser.size(); i++) {
				menu = allmenuListUser.get(i);
				if ("1".equals(menu.getMENU_TYPE())) {
					menuList1.add(menu);
				} else {
					menuList2.add(menu);
				}
			}
			/* 获取当前点击用户的菜单权限 */
			Role roleuserA = personnelService.getRoleById(ROLE_ID); // 根据当前点击角色ID获取角色对象
			String roleRightsUser = roleuserA.getRIGHTS(); // 取出点击角色菜单权限

			/* （菜单权限） */
			List<Menu> menuListA = this.readMenu(menuList2, roleRightsUser); // 根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuListA);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes")
					.replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			/* (增) */
			String roleRightsUserAdd = roleuserA.getADD_QX(); // 新增权限
			List<Menu> menuListAdd = this
					.readMenu(menuList2, roleRightsUserAdd); // 根据角色权限处理新增权限状态(递归处理)
			JSONArray arrAdd = JSONArray.fromObject(menuListAdd);
			String jsonAdd = arrAdd.toString();
			jsonAdd = jsonAdd.replaceAll("MENU_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes")
					.replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodesAdd", jsonAdd);
			/* (删) */
			String roleRightsUserDel = roleuserA.getDEL_QX(); // 删除权限
			List<Menu> menuListDel = this
					.readMenu(menuList2, roleRightsUserDel); // 根据角色权限处理删除权限状态(递归处理)
			JSONArray arrDel = JSONArray.fromObject(menuListDel);
			String jsonDel = arrDel.toString();
			jsonDel = jsonDel.replaceAll("MENU_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes")
					.replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodesDel", jsonDel);
			/* (改) */
			String roleRightsUserEdit = roleuserA.getEDIT_QX(); // 修改权限
			List<Menu> menuListEdit = this.readMenu(menuList2,
					roleRightsUserEdit); // 根据角色权限处理修改权限状态(递归处理)
			JSONArray arrEdit = JSONArray.fromObject(menuListEdit);
			String jsonEdit = arrEdit.toString();
			jsonEdit = jsonEdit.replaceAll("MENU_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes")
					.replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodesEdit", jsonEdit);
			/* (查) */
			String roleRightsUserCha = roleuserA.getCHA_QX(); // 查看权限
			List<Menu> menuListCha = this
					.readMenu(menuList2, roleRightsUserCha); // 根据角色权限处理查看权限状态(递归处理)
			JSONArray arrCha = JSONArray.fromObject(menuListCha);
			String jsonCha = arrCha.toString();
			jsonCha = jsonCha.replaceAll("MENU_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes")
					.replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodesCha", jsonCha);

			mv.addObject("ROLE_ID", ROLE_ID);
			mv.setViewName("system/personnel/menuqx");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存角色（菜单、增删改查）权限 保存修改后角色权限
	 * 
	 * @param ROLE_ID
	 *            角色ID
	 * @param menuIds
	 *            菜单ID集合
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveMenuqx")
	public void saveMenuqx(@RequestParam String ROLE_ID,
			@RequestParam String menuIds, @RequestParam String menuIdsAdd,
			@RequestParam String menuIdsDel, @RequestParam String menuIdsEdit,
			@RequestParam String menuIdsCha, PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "修改菜单权限");
		PageData pageData = new PageData();
		pageData.put("ROLE_ID", ROLE_ID);
		pageData.put("menuIds", menuIds);
		pageData.put("menuIdsAdd", menuIdsAdd);
		pageData.put("menuIdsDel", menuIdsDel);
		pageData.put("menuIdsEdit", menuIdsEdit);
		pageData.put("menuIdsCha", menuIdsCha);
		pageData.put("getPageData", this.getPageData());
		PageData pdpd = personnelService.personnelSave(pageData); // 保存角色的权限
		if (pdpd.containsKey("msg") && pdpd.getString("msg") != null) {
			out.write(pdpd.getString("msg"));
			out.close();
		}
	}

	//保存修改后的权限
	public void saveMenuqx(PageData pd) throws Exception{
		//添加权限
		logBefore(logger, Jurisdiction.getUsername() + "修改菜单权限");
		String ids=StringUtils.join(pd.getString("ids").split(","), ",");
		PageData pageData = new PageData();
		pageData.put("ROLE_ID", pd.getString("ROLE_ID"));
		pageData.put("menuIds", ids);
		pageData.put("menuIdsAdd", ids);
		pageData.put("menuIdsDel", ids);
		pageData.put("menuIdsEdit", ids);
		pageData.put("menuIdsCha", ids);
		pageData.put("getPageData", this.getPageData());
		PageData pdpd = personnelService.personnelSave(pageData); // 保存角色的权限
		System.out.println(pdpd.getString("msg"));
	}
	
	
	/**
	 * 保存角色按钮权限 保存修改后角色按钮权限
	 * 
	 * @param ROLE_ID
	 * @param menuIds
	 * @param msg
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveB4Button")
	public void saveB4Button(@RequestParam String ROLE_ID,
			@RequestParam String menuIds, @RequestParam String msg,
			PrintWriter out) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
		} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "修改" + msg + "权限");
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if (null != menuIds && !"".equals(menuIds.trim())) {
				BigInteger rights = RightsHelper.sumRights(Tools
						.str2StrArray(menuIds));
				pd.put("value", rights.toString());
			} else {
				pd.put("value", "");
			}
			pd.put("ROLE_ID", ROLE_ID);
			personnelService.saveB4Button(msg, pd); // 权限(增删改查)
			out.write(PublicManagerUtil.SUCCESS);
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 请求角色按钮授权页面(增删改查)
	 * 
	 * @param ROLE_ID
	 *            ： 角色ID
	 * @param msg
	 *            ： 区分增删改查
	 * @param model
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/b4Button")
	public ModelAndView b4Button(@RequestParam String ROLE_ID,
			@RequestParam String msg, Model model) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			User user = this.getUser();//获取用户
			/* 获取登录用户的菜单权限列表 */
			Session session = Jurisdiction.getSession();
			User userr = (User) session.getAttribute(Const.SESSION_USERROL); // 读取session中的用户信息(含角色信息)
			if (null == userr) {
				user = userService.getUserAndRoleById(user.getUSER_ID()); // 通过用户ID读取用户信息和角色信息
			} else {
				user = userr;
			}
			String USERNAME = user.getUSERNAME();
			Role role = user.getRole(); // 获取用户角色
			String roleRights = role != null ? role.getRIGHTS() : ""; // 角色权限(菜单权限)
			List<Menu> allmenuList = new ArrayList<Menu>();
			if (null == session.getAttribute(USERNAME
					+ Const.SESSION_allmenuList)) {
				allmenuList = menuService.listAllMenuQx("0"); // 获取所有菜单
				if (Tools.notEmpty(roleRights)) {
					allmenuList = this.readMenu(allmenuList, roleRights); // 根据角色权限获取本权限的菜单列表
				}
			} else {
				allmenuList = (List<Menu>) session.getAttribute(USERNAME
						+ Const.SESSION_allmenuList);
			}
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			// 拆分菜单
			for (int i = 0; i < allmenuList.size(); i++) {
				Menu menu = allmenuList.get(i);
				if ("1".equals(menu.getMENU_TYPE())) {
					menuList1.add(menu);
				} else {
					menuList2.add(menu);
				}
			}
			Role roleUser = personnelService.getRoleById(ROLE_ID); // 根据角色ID获取角色对象
			String roleRightsUser = "";
			if ("add_qx".equals(msg)) {
				roleRightsUser = roleUser.getADD_QX(); // 新增权限
			} else if ("del_qx".equals(msg)) {
				roleRightsUser = roleUser.getDEL_QX(); // 删除权限
			} else if ("edit_qx".equals(msg)) {
				roleRightsUser = roleUser.getEDIT_QX(); // 修改权限
			} else if ("cha_qx".equals(msg)) {
				roleRightsUser = roleUser.getCHA_QX(); // 查看权限
			}
			List<Menu> menuList = new ArrayList<Menu>();
			menuList = this.readMenu(menuList2, roleRightsUser); // 根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id")
					.replaceAll("PARENT_ID", "pId")
					.replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes")
					.replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID", ROLE_ID);
			mv.addObject("msg", msg);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/personnel/b4Button");
		return mv;
	}

	/**
	 * 根据当前点击角色权限处理权限状态(递归处理)
	 * 
	 * @param menuList
	 *            ：传入的登录用户菜单
	 * @param roleRights
	 *            ：加密的权限字符串
	 * @return menuList
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(
					RightsHelper.testRights(roleRights, menuList.get(i)
							.getMENU_ID()));
			this.readMenu(menuList.get(i).getSubMenu(), roleRights); // 是：继续排查其子菜单
		}
		return menuList;
	}

	/**
	 * 根据当前登录角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param menuList
	 *            ：传入的总菜单
	 * @param roleRights
	 *            ：加密的权限字符串
	 * @return menuList
	 */
	public List<Menu> readMenuUser(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(
					RightsHelper.testRights(roleRights, menuList.get(i)
							.getMENU_ID()));
			if (menuList.get(i).isHasMenu()) { // 判断是否有此菜单权限
				this.readMenuUser(menuList.get(i).getSubMenu(), roleRights); // 是：继续排查其子菜单
			} else {
				menuList.remove(i);
				i--;
			}
		}
		return menuList;
	}

	/**
	 * 判断角色名是否重复
	 * 
	 * @return
	 */
	@RequestMapping(value = "/haha")
	@ResponseBody
	public Object role_Name() {
		Map<String, String> map = new HashMap<String, String>();
		User user = this.getUser();//获取用户

		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
		String errInfo = PublicManagerUtil.SUCCESS;
		try {
			if (personnelService.getRoleByName(pd) != null) { // 通过INTENET_ID和ROLE_NAME判断角色名是否重复
				errInfo = PublicManagerUtil.ERROR;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
}