package com.lk.controller.system.storeManger;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import net.sf.json.JSONObject;
import com.lk.service.system.store.StoreManager;

/**
 * 说明：门店管理(后台) 
 * 创建人：李泽华 
 * 创建时间：2017-02-27
 */
@Controller
@RequestMapping(value = "/storeManger")
public class StoreMangerController extends BaseController {

	String menuUrl = "storeManger/list.do"; // 菜单地址(权限用)
	@Resource(name = "storeService")
	private StoreManager storeService;

	/**
	 * 门店管理(后台)列表
	 * 查询所有门店及门店详细信息
	 * @param page keywords--关键词
	 * @return mv--返回指定信息和视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "门店管理（后台）列表");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;}//校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//查询加V缺失门店(完善数据库,可删(由于之前数据库不完善所用))
		storeService.getStoreAll(page);      
		
		// 列出全部Store列表(管理门店(后台)查全部门店)
		List<PageData> varList = storeService.listALL(page,pd);    
		mv.setViewName("system/storeManger/storeManger_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 门店详情列表 
	 * 去查看该门店的详情
	 * @param STORE_ID--指定门店id
	 * @return mv(指定视图,varList--门店详情)
	 * @throws Exception
	 */
	@RequestMapping(value = "/storeDetails")
	public ModelAndView storeDetails(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查看指定门店详情");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		// 列出门店详情(去查看该门店的详情)
		List<PageData> varList = storeService.storeDetails(pd);   
		mv.addObject("varList", varList);
		mv.setViewName("system/store/store_slist");
		mv.addObject("QX", Jurisdiction.getHC());  // 按钮权限
		return mv;
	}

	/**
	 * 停用指定门店
	 * 改变指定门店的状态
	 * @param STORE_ID--门店id
	 * @throws Exception
	 */
	@RequestMapping(value = "/disableUsers")
	@ResponseBody
	public JSONObject disableUsers(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "停用指定门店");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		JSONObject json=storeService.disableUsers(pd);   //停用指定门店
		return json;
	}

	/**
	 * 启用指定门店
	 * 改变指定门店的状态
	 * @param STORE_ID--门店id
	 * @throws Exception
	 */
	@RequestMapping(value = "/enableUser")
	@ResponseBody
	public Object enableUser(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "启用指定门店");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		JSONObject json=storeService.enableUser(pd);   //启用指定门店
		return json;
	}
}
