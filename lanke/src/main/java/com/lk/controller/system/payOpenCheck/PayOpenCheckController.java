package com.lk.controller.system.payOpenCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.payManager.PayOpenManager;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.Jurisdiction;

@Controller
@RequestMapping(value="/payOpenReview")
public class PayOpenCheckController extends BaseController {
	
	String menuUrl = "payOpenReview/list.do"; //菜单地址(权限用)
	@Resource(name = "payOpenService")
	private PayOpenManager payOpenService;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表payOpenReview");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
	    
	    //传入关键词搜索，筛选等条件
		PageData pd = this.getPageData();
		pd.put("user", user);
		pd.put("page", page);
		
		List<PageData> varList = payOpenService.payOpenList(pd);
		
		mv.addObject("pd", pd);
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("system/payOpenReview/payOpenReview_list");
		return mv;
	} 
	
	
	
	 /**
	  * 去查看页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goDetail")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		
		mv.addObject("pd", pd);
		mv.setViewName("system/payOpenReview/payOpenReview_edit");
		return mv;
	}	
	
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/review")
	@ResponseBody
	public JSONObject review() throws Exception {
		User user = this.getUser();//得到用户
	    
		PageData pd = this.getPageData();
		pd.put("user", user);
		
		return payOpenService.saveReview(pd);
	}
	
	/**
	 * 开通
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/open")
	@ResponseBody
	public JSONObject open() throws Exception {
		User user = this.getUser();//得到用户
	    PageData pd = this.getPageData();
	    pd.put("user", user);
		
		return payOpenService.saveOpen(pd);
	}
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/exportExcel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Excel");
		
		PageData pd = this.getPageData();
		
		return payOpenService.exportExcel(pd);
		
	}
	
}
