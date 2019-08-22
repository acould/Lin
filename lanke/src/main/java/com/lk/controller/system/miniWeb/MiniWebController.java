package com.lk.controller.system.miniWeb;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Jurisdiction;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.service.system.miniWeb.MiniWebManager;

/** 
 * 说明：微官网
 * 创建人：洪智鹏
 * 创建时间：2016-10-31
 */
@Controller
@RequestMapping(value="/miniWeb")
public class MiniWebController extends BaseController {
	
	String menuUrl = "miniWeb/list.do"; //菜单地址(权限用)
	@Resource(name="miniWebService")
	private MiniWebManager miniWebService;
	
	/**
	 * 列表
	 * 通过网吧id去查询微官网信息	
	 * @param page 
	 * @return  mv--返回指定视图和信息
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page,HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"miniWeb -- 微官网首页 -- list");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = this.getPageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData>	varList = miniWebService.list(page);//通过网吧id去查询微官网信息	
		mv.setViewName("system/miniWeb/miniWeb_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"miniWeb -- 去新增页面 -- goAdd");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/miniWeb/miniWeb_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		return mv;
	}	
	
	/**
	 * 去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"miniWeb -- 去修改页面 -- goEdit");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = miniWebService.findById(pd);	//根据网吧id查询微官网信息
		mv.setViewName("system/miniWeb/miniWeb_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 保存微官网链接
	 * @param PageData 包含微官网信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveMiniWeb")
	@ResponseBody
	public JSONObject saveMiniWeb() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增或修改保存微官网链接");
		JSONObject json = new JSONObject();
		User user = this.getUser();//获取用户

		PageData pd = this.getPageData();
		//校验权限
		if(StringUtil.isNotEmpty(pd.getString("MINIWEB_ID"))){
			//修改
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有修改权限，请联系管理员！");
				return json;
			}
			pd.put("CREATE_TIME", Tools.date2Str(new Date()));
			String url = URLDecoder.decode(pd.getString("URL"), "UTF-8");
			pd.put("URL", url);
			json=miniWebService.edit(pd);//修改,通过MINIWEB_ID去修改微官网信息
			return json;
		}else{
			//新增
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有新增权限，请联系管理员！");
				return json;
			}
			pd.put("MINIWEB_ID", this.get32UUID());
			pd.put("INTERNET_ID", user.getINTENET_ID());
			pd.put("CREATE_TIME", Tools.date2Str(new Date()));
			String url = URLDecoder.decode(pd.getString("URL"), "UTF-8");
			pd.put("URL", url);
			json=miniWebService.save(pd);//新增微官网信息
			return json;
		}
	}
	
	/**
	 * 删除微官网链接
	 * @param PageData 包含微官网删除的主键
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除微官网链接delete");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有新增权限，请联系管理员！");
			return json;
		}
		json=miniWebService.delete(pd);//通过MINIWEB_ID删除相关微官网信息
		return json;
	}
}
