package com.lk.controller.system.recharge;


import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.Jurisdiction;
import com.lk.util.StringUtil;

@Controller
@RequestMapping(value="/recharge")
public class RechargeController extends BaseController {
	
	String menuUrl = "recharge/list.do"; //菜单地址(权限用)
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表recharge");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(StringUtil.isNotEmpty(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		
		pd.put("menuUrl", menuUrl);
		mv.setViewName("system/payManager/rechargeRule_list");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/payManager/rechargeRule_edit");
		return mv;
	}	
	
	
}