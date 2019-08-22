package com.lk.controller.system.menu;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.system.Menu;
import com.lk.entity.system.Role;
import com.lk.service.system.menu.MenuManager;
import com.lk.service.system.role.impl.RoleService;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.RightsHelper;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 类名称：MenuController (菜单管理) 菜单处理 创建人：FH 创建时间：2015年10月27日
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	String menuUrl = "menu.do"; // 菜单地址(权限用)
	@Resource(name = "menuService")
	private MenuManager menuService;
	@Resource(name = "roleService")
	private RoleService roleService;

	/**
	 * 显示菜单列表
	 * 
	 * @param pd
	 *            包含页面传递来的相关信息
	 * @param MENU_ID
	 *            菜单id
	 * @return
	 */
	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String MENU_ID = (null == pd.get("MENU_ID") || "".equals(pd.get("MENU_ID").toString())) ? "0": pd.get("MENU_ID").toString();
			List<Menu> menuList = menuService.listSubMenuByParentId(MENU_ID);
			mv.addObject("pd", menuService.getMenuById(pd)); // 传入父菜单所有信息
			mv.addObject("MENU_ID", MENU_ID);
			mv.addObject("MSG", null == pd.get("MSG") ? "list" : pd.get("MSG").toString()); // MSG=change 则为编辑或删除后跳转过来的
			mv.addObject("menuList", menuList);
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("system/menu/menu_list");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 请求新增菜单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd = this.getPageData();
			String MENU_ID = (null == pd.get("MENU_ID") || "".equals(pd.get("MENU_ID").toString())) ? "0"
					: pd.get("MENU_ID").toString();// 接收传过来的上级菜单ID,如果上级为顶级就取值“0”
			pd.put("MENU_ID", MENU_ID);
			mv.addObject("pds", menuService.getMenuById(pd)); // 传入父菜单所有信息
			mv.addObject("MENU_ID", MENU_ID); // 传入菜单ID，作为子菜单的父菜单ID用
			mv.addObject("MSG", PublicManagerUtil.ADD); // 执行状态 add 为添加
			mv.setViewName("system/menu/menu_edit");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存菜单信息
	 * 
	 * @param menu
	 *            菜单相关信息
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject add(Menu menu) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {return null;} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "保存菜单");
		PageData pd = new PageData();
		pd = this.getPageData();
		JSONObject json = new JSONObject();	
		try {
			menu.setMENU_ID(String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString()) + 1));
			menu.setMENU_ICON("menu-icon fa fa-leaf black");// 默认菜单图标
			menuService.saveMenu(menu); // 保存菜单

			// 修改admin 的菜单权限
			List<Menu> list = menuService.listMenu(null);
			String menu_ids = "";
			for (int i = 0; i < list.size(); i++) {
				if (StringUtil.isNotEmpty(list.get(i).getMENU_ID())) {
					menu_ids += list.get(i).getMENU_ID();
					if (i != list.size() - 1) {
						menu_ids += ",";
					}
				}
			}
			System.out.println(menu_ids);
			if (StringUtil.isNotEmpty(menu_ids)) {
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menu_ids));// 用菜单ID做权处理
				Role role = roleService.getRoleById(PublicManagerUtil.ADMINROLEID); // 通过id获取角色对象
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role); // 更新当前角色菜单权限
			}
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "新增成功");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			json.put("result", PublicManagerUtil.FAIL);
			json.put("message", "新增失败");
		}
		return json;
	}

	/**
	 * 删除菜单
	 * 
	 * @param MENU_ID
	 *            根据主键删除操作
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete(@RequestParam String MENU_ID){
		logger.info(Jurisdiction.getUsername()+ "删除菜单---menu/delete");
		
		JSONObject result = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			result.put("result", "false");
			result.put("message", "您没有删除菜单的权限");
			return result;
		} 
		
		try {
			if (menuService.listSubMenuByParentId(MENU_ID).size() > 0) {// 判断是否有子菜单，是：不允许删除
				result.put("result", "false");
				result.put("message", "请先删除该菜单下的子菜单");
				return result;
			} else {
				menuService.deleteMenuById(MENU_ID);

				// 修改admin 的菜单权限
				List<Menu> list = menuService.listMenu(null);
				String menu_ids = "";
				for (int i = 0; i < list.size(); i++) {
					if (StringUtil.isNotEmpty(list.get(i).getMENU_ID())) {
						menu_ids += list.get(i).getMENU_ID();
						if (i != list.size() - 1) {
							menu_ids += ",";
						}
					}
				}
				System.out.println(menu_ids);
				if (StringUtil.isNotEmpty(menu_ids)) {
					BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menu_ids));// 用菜单ID做权处理
					Role role = roleService.getRoleById(PublicManagerUtil.ADMINROLEID); // 通过id获取角色对象
					role.setRIGHTS(rights.toString());
					roleService.updateRoleRights(role); // 更新当前角色菜单权限
				}
				result.put("result", "true");
				result.put("message", "删除成功");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "删除失败");
		}
		return result;
	}

	/**
	 * 请求编辑菜单页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("MENU_ID", id); // 接收过来的要修改的ID
			pd = menuService.getMenuById(pd); // 读取此ID的菜单数据
			mv.addObject("pd", pd); // 放入视图容器
			pd.put("MENU_ID", pd.get("PARENT_ID").toString()); // 用作读取父菜单信息
			mv.addObject("pds", menuService.getMenuById(pd)); // 传入父菜单所有信息
			mv.addObject("MENU_ID", pd.get("PARENT_ID").toString()); // 传入父菜单ID，作为子菜单的父菜单ID用
			mv.addObject("MSG", PublicManagerUtil.EDIT);
			pd.put("MENU_ID", id); // 复原本菜单ID
			mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
			mv.setViewName("system/menu/menu_edit");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 保存编辑
	 * 
	 * @param menu
	 *            菜单字段
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(Menu menu) throws Exception {
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		logBefore(logger, Jurisdiction.getUsername() + "修改菜单");
		JSONObject json = new JSONObject();
		try {
			menuService.edit(menu);
			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "修改成功");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			json.put("result", PublicManagerUtil.FAIL);
			json.put("message", "修改失败");
		}
		return json;
	}

	/**
	 * 请求编辑菜单图标页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/toEditicon")
	public ModelAndView toEditicon(String MENU_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("MENU_ID", MENU_ID);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/menu_icon");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 	修改菜单图标
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editicon")
	@ResponseBody
	public JSONObject editicon(){
		logger.info("修改图标---menu/editicon");
		
		JSONObject result = new JSONObject();
		
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			result.put("result", "false");
			result.put("message", "您没有修改图标的权限");
			return result;
		} 
		
		PageData pd = this.getPageData();
		try {
			pd = menuService.editicon(pd);
			result.put("result", "true");
			result.put("message", "修改成功");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "修改失败");
		}
		
		//返回
		return result;
		
	}


	/**
	 * 显示菜单列表ztree(菜单管理)
	 * 
	 * @param model
	 * @param MENU_ID
	 *            菜单主键
	 * @return
	 */
	@RequestMapping(value = "/listAllMenu")
	public ModelAndView listAllMenu(Model model, String MENU_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			JSONArray arr = JSONArray.fromObject(menuService.listAllMenu("0"));
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked").replaceAll("MENU_URL", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("MENU_ID", MENU_ID);
			mv.setViewName("system/menu/menu_ztree");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 显示菜单列表ztree(拓展左侧四级菜单)
	 * 
	 * @param model
	 * @param MENU_ID菜单主键
	 * @return
	 */
	@RequestMapping(value = "/otherlistMenu")
	public ModelAndView otherlistMenu(Model model, String MENU_ID) throws Exception {
		ModelAndView mv = this.getModelAndView();
		try {
			PageData pd = new PageData();
			pd.put("MENU_ID", MENU_ID);
			String MENU_URL = menuService.getMenuById(pd).getString("MENU_URL");
			if ("#".equals(MENU_URL.trim()) || "".equals(MENU_URL.trim()) || null == MENU_URL) {
				MENU_URL = "login_default.do";
			}
			String roleRights = Jurisdiction.getSession()
					.getAttribute(Jurisdiction.getUsername() + Const.SESSION_ROLE_RIGHTS).toString(); // 获取本角色菜单权限
			List<Menu> athmenuList = menuService.listAllMenuQx(MENU_ID); // 获取某菜单下所有子菜单
			athmenuList = this.readMenu(athmenuList, roleRights); // 根据权限分配菜单
			JSONArray arr = JSONArray.fromObject(athmenuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name")
					.replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked").replaceAll("MENU_URL", "url")
					.replaceAll("#", "");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("MENU_URL", MENU_URL); // 本ID菜单链接
			mv.setViewName("system/menu/menu_ztree_other");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 根据角色权限获取本权限的菜单列表(递归处理)
	 * 
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
		for (int i = 0; i < menuList.size(); i++) {
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if (menuList.get(i).isHasMenu() && "1".equals(menuList.get(i).getMENU_STATE())) { // 判断是否有此菜单权限并且是否隐藏
				this.readMenu(menuList.get(i).getSubMenu(), roleRights); // 是：继续排查其子菜单
			} else {
				menuList.remove(i);
				i--;
			}
		}
		return menuList;
	}

}
