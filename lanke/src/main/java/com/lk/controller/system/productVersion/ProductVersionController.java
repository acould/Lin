package com.lk.controller.system.productVersion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;

/** 
 * 说明：产品列表
 */
@Controller
@RequestMapping(value="/productVersion")
public class ProductVersionController extends BaseController{
	
	String menuUrl = "productVersion/list.do"; //菜单地址(权限用)
	
	/** 
	 * 产品列表
	 * 展示所有版本信息
	 * @return mv--指定视图及信息
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/productVersion/productVersion_list");
		return mv;
	}
}
