package com.lk.controller.system.role;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.lk.service.system.menu.MenuManager;
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
 * 类名称：RoleController 角色权限管理(角色(基础权限))
 * 创建人：洪智鹏
 * 修改时间：2015年11月6日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="userService")
	private UserManager userService;
	
	
	/** 进入权限首页
	 * @param pd包含角色id
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView list()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("ROLE_ID") == null || "".equals(pd.getString("ROLE_ID").trim())){
				pd.put("ROLE_ID", "1");										//默认列出第一组角色(初始设计系统用户和会员组不能删除)
			}
			PageData fpd = new PageData();
			fpd.put("ROLE_ID", "0");
			List<Role> roleList = roleService.listAllRolesByPId(fpd);		//列出组(页面横向排列的一级组)
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此组下架角色
			pd = roleService.findObjectById(pd);							//取得点击的角色组(横排的)
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
			mv.addObject("roleList_z", roleList_z);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
			mv.setViewName("system/role/role_list");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**去新增页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.addObject("msg", PublicManagerUtil.ADD);
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存新增角色 
	 * @param pagedata  角色权限管理相关信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject add(){
		logger.info(Jurisdiction.getUsername()+ "新增角色---role/add");
		
		JSONObject result = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			result.put("result", "false");
			result.put("message", "您没有新增角色的权限");
			return result;
		} 
		
		try{
			PageData pd = this.getPageData();
			String parent_id = pd.getString("PARENT_ID");		//父类角色id
			pd.put("ROLE_ID", parent_id);			
			if("0".equals(parent_id)){
				pd.put("RIGHTS", "");							//菜单权限
			}else{
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights)?"":rights);	//组菜单权限
			}
			pd.put("ROLE_ID", this.get32UUID());				//主键
			pd.put("ADD_QX", "0");	//初始新增权限为否
			pd.put("DEL_QX", "0");	//删除权限
			pd.put("EDIT_QX", "0");	//修改权限
			pd.put("CHA_QX", "0");	//查看权限
			roleService.add(pd);
			
			result.put("result", "true");
			result.put("message", "保存成功");
		} catch(Exception e){
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "保存失败");
		}
		return result;
	}
	
	/**请求编辑
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			mv.addObject("msg", PublicManagerUtil.EDIT);
			mv.addObject("pd", pd);
			mv.setViewName("system/role/role_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存修改
	 * @param  pagedata  角色权限管理相关信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public JSONObject edit(){
		logger.info(Jurisdiction.getUsername()+ "修改角色---role/edit");
		
		JSONObject result = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			result.put("result", "false");
			result.put("message", "您没有修改角色的权限");
			return result;
		} 
		
		try{
			PageData pd = this.getPageData();
			roleService.edit(pd);
			result.put("result", "true");
			result.put("message", "修改成功");
		} catch(Exception e){
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "修改失败");
		}
		return result;
	}
	
	/**删除角色
	 * @param ROLE_ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject deleteRole(@RequestParam String ROLE_ID)throws Exception{
		logger.info(Jurisdiction.getUsername()+ "删除角色---role/delete");
		JSONObject result = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			result.put("result", "false");
			result.put("message", "您没有删除权限");
			return result;
		} 
		
		PageData pd = new PageData();
		try{
			pd.put("ROLE_ID", ROLE_ID);
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		    //列出此部门的所有下级
			if(roleList_z.size() > 0){//下级有数据时，删除失败
				result.put("result", "false");
				result.put("message", "删除失败，请先删除下级角色!");
				return result;
			}else{
				List<PageData> userlist = userService.listAllUserByRoldId(pd);	//此角色下的用户
				if(userlist.size() > 0){//此角色已被使用就不能删除		
					result.put("result", "false");
					result.put("message", "删除失败，此角色已被使用就不能删除!");
					return result;
				}else{
					roleService.deleteRoleById(ROLE_ID);	                        //执行删除
					result.put("result", "true");
					result.put("message", "删除成功");
				}
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "删除失败");
		}
		return result;
	}
	
	/**
	 * 显示菜单列表ztree(菜单授权菜单)
	 * @param model
	 * @param ROLE_ID角色id主键
	 * @return
	 */
	@RequestMapping(value="/menuqx")
	public ModelAndView listAllMenu(Model model,String ROLE_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			Role role = roleService.getRoleById(ROLE_ID);			//根据角色ID获取角色对象
			String roleRights = role.getRIGHTS();					//取出本角色菜单权限
			List<Menu> menuList = menuService.listAllMenuQx("0");	//获取所有菜单
			menuList = this.readMenu(menuList, roleRights);			//根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.setViewName("system/role/menuqx");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**保存角色菜单权限
	 * @param ROLE_ID 角色ID
	 * @param menuIds 菜单ID集合
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveMenuqx")
	@ResponseBody
	public JSONObject saveMenuqx(@RequestParam String ROLE_ID,@RequestParam String menuIds){
		logger.info(Jurisdiction.getUsername()+ "保存角色菜单权限---role/saveMenuqx");
		
		JSONObject result = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			result.put("result", "false");
			result.put("message", "您没有修改的权限");
			return result;
		} 
		
		PageData pd = new PageData();
		try{
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));//用菜单ID做权处理
				Role role = roleService.getRoleById(ROLE_ID);	//通过id获取角色对象
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);				//更新当前角色菜单权限
				pd.put("rights",rights.toString());
			}else{
				Role role = new Role();
				role.setRIGHTS("");
				role.setROLE_ID(ROLE_ID);
				roleService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				pd.put("rights","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.setAllRights(pd);					//更新此角色所有子角色的菜单权限
			result.put("result", "true");
			result.put("message", "修改成功");
		} catch(Exception e){
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "修改失败");
		}
		return result;
	}

	/**请求角色按钮授权页面(增删改查)
	 * @param ROLE_ID： 角色ID
	 * @param msg： 区分增删改查
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/b4Button")
	public ModelAndView b4Button(@RequestParam String ROLE_ID,@RequestParam String msg,Model model)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try{
			List<Menu> menuList = menuService.listAllMenuQx("0"); //获取所有菜单
			Role role = roleService.getRoleById(ROLE_ID);		  //根据角色ID获取角色对象
			String roleRights = "";
			if("add_qx".equals(msg)){
				roleRights = role.getADD_QX();	//新增权限
			}else if("del_qx".equals(msg)){
				roleRights = role.getDEL_QX();	//删除权限
			}else if("edit_qx".equals(msg)){
				roleRights = role.getEDIT_QX();	//修改权限
			}else if("cha_qx".equals(msg)){
				roleRights = role.getCHA_QX();	//查看权限
			}
			menuList = this.readMenu(menuList, roleRights);		//根据角色权限处理菜单权限状态(递归处理)
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("MENU_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("ROLE_ID",ROLE_ID);
			mv.addObject("msg", msg);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("system/role/b4Button");
		return mv;
	}
	
	/**根据角色权限处理权限状态(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			this.readMenu(menuList.get(i).getSubMenu(), roleRights);					//是：继续排查其子菜单
		}
		return menuList;
	}
	
	/**
	 * 保存角色按钮权限
	 */
	/**
	 * @param ROLE_ID
	 * @param menuIds
	 * @param msg
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/saveB4Button")
	@ResponseBody
	public JSONObject saveB4Button(@RequestParam String ROLE_ID,@RequestParam String menuIds,@RequestParam String msg){
		logger.info(Jurisdiction.getUsername()+ "保存角色按钮权限---role/saveB4Button");
		
		JSONObject result = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			result.put("result", "false");
			result.put("message", "您没有修改的权限");
			return result;
		} 
		
		try{
			PageData pd =  this.getPageData();
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put("value",rights.toString());
			}else{
				pd.put("value","");
			}
			pd.put("ROLE_ID", ROLE_ID);
			roleService.saveB4Button(msg,pd);
			
			result.put("result", "true");
			result.put("message", "修改成功");
		} catch(Exception e){
			logger.error(e.toString(), e);
			result.put("result", "false");
			result.put("message", "修改失败");
		}
		return result;
	}
	
}