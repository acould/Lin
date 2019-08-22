package com.lk.controller.system.userManage;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.service.system.user.UserManager;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

/**
 * 说明：揽客用户管理 
 * 创建人：李泽华 
 * 创建时间：2018-02-28
 */

@Controller
@RequestMapping(value = "/userManage")
public class UserManageController extends BaseController {
	String menuUrl = "userManage/lkUsers.do"; // 菜单地址(权限用)
	@Resource(name = "userService")
	private UserManager userService;

	/**
	 * 显示揽客用户列表
	 * 查询全部网吧管理员(网吧老板)
	 * @return mv(指定视图,userList--用户信息列表)
	 * @throws Exception
	 */
	@RequestMapping(value = "/lkUsers")
	public ModelAndView listUsers(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "揽客用户（后台）列表");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");
		if (null != keywords && !"".equals(keywords)) {// 关键词检索条件
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		// 列出用户列表(查询全部网吧管理员(网吧老板))
		List<PageData> userList = userService.lkUsers(page,pd);  
		mv.setViewName("system/userManage/userManage_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC());// 按钮权限
		return mv;
	}

	/**
	 * 查询用户全部门店
	 * 查询指定用户的所有门店
	 * @param page
	 * @return mv(指定视图,varList--用户门店信息)
	 * @throws Exception
	 */
	@RequestMapping(value = "/lkStoreShow")
	public ModelAndView toShow(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查看指定用户的全部门店");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("internetId", this.getRequest().getParameter("INTENET_ID"));
		
		//查询用户所有门店
		List<PageData> varList = userService.listStores(page,pd);  
		mv.setViewName("system/userManage/userManage_slist");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC());// 按钮权限
		return mv;
	}

	/**
	 * 停用指定用户
	 * 改变指定用户状态
	 * @param page--网吧id
	 * @return map(ajax返回date)
	 * @throws Exception
	 */
	@RequestMapping(value = "/disableUsers")
	@ResponseBody
	public JSONObject disableUsers(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "停用指定用户");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		JSONObject json = userService.disableUsers(page);    //停用指定用户
		return json;
	}

	/**
	 * 启用指定用户
	 * 改变指定用户状态
	 * @param page--网吧id
	 * @return map(ajax返回date)
	 * @throws Exception
	 */
	@RequestMapping(value = "/enableUser")
	@ResponseBody
	public JSONObject enableUser(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "启用指定用户");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) { return null;} // 校验权限
        PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		JSONObject json = userService.enableUser(page);//启用指定用户
		return json;
	}
}
