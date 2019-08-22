package com.lk.controller.system.sysintegral;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Jurisdiction;
import com.lk.util.Tools;
import com.lk.service.system.sysintegral.SysIntegralManager;

/** 
 * 说明：系统初始积分规则表(积分设置)
 * 创建人：洪智鹏
 * 创建时间：2016-10-07
 */
@Controller
@RequestMapping(value="/sysintegral")
public class SysIntegralController extends BaseController {
	
	String menuUrl = "sysintegral/list.do"; //菜单地址(权限用)
	@Resource(name="sysintegralService")
	private SysIntegralManager sysintegralService;
	
	/**保存
	 * @param pagedata 系统初始积分规则表相关信息
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		User user = this.getUser();//得到用户

		logBefore(logger, Jurisdiction.getUsername()+"新增SysIntegral");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		ModelAndView mv = this.getModelAndView();
		pd = this.getPageData();
		List<PageData> varList = sysintegralService.listAll(pd);
		if(varList.size() > 0){
			mv.addObject("msg",PublicManagerUtil.SUCCESS);
			mv.setViewName("save_result");
		}else{
			pd.put("INTEGRAL_NAME", "奖励");
			pd.put("INTEGRAL_TYPE", 1);
			pd.put("INTENET_ID", user.getINTENET_ID());
			pd.put("CREATE_TIME",Tools.date2Str(new Date()));
			pd.put("INTEGRAL_ID", this.get32UUID());	//主键
			//if(StringUtils.isNoneEmpty(pd.getString("WEEKEND_SEND"))){
				if(pd.getString("WEEKEND_SEND").isEmpty()){
				pd.put("WEEKEND_SEND", 0);
			}
			sysintegralService.save(pd);
			mv.addObject("msg",PublicManagerUtil.SUCCESS);
			mv.setViewName("save_result");
		}
		return mv;
	}
	
	
	/**删除
	 * @param pagedata 系统初始积分规则表相关信息的主键
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除SysIntegral");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		sysintegralService.delete(pd);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	
	/**修改
	 * @param pagedata 系统初始积分规则表相关信息
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改SysIntegral");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ws = request.getParameter("WEEKEND_SEND");
		if(ws == null || ws == ""){
			pd.put("WEEKEND_SEND", null);
		}else{
			Double wsd = Double.parseDouble(request.getParameter("WEEKEND_SEND"));
			Math.floor(wsd);
			pd.put("WEEKEND_SEND", wsd);
		}
		Double a = Double.parseDouble(request.getParameter("INTEGRAL_SEND"));
		Math.floor(a);
		pd.put("INTEGRAL_SEND", a);
		pd.put("INTEGRAL_ID", request.getParameter("INTEGRAL_ID"));
		sysintegralService.edit(pd);
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page页面查询检索信息
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表SysIntegral");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = sysintegralService.list(page);	//列出SysIntegral列表
		mv.setViewName("system/sysintegral/sysintegral_list");
		mv.addObject("varList", varList);
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
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/sysintegral/sysintegral_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = sysintegralService.findById(pd);	//根据ID读取
		mv.setViewName("system/sysintegral/sysintegral_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param pagedata 系统初始积分规则表相关信息的主键组成数组
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SysIntegral");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			sysintegralService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", PublicManagerUtil.OK);
		}else{
			pd.put("msg", PublicManagerUtil.NO);
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SysIntegral到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("主键");	    //1
		titles.add("积分名称");	//2
		titles.add("积分类型");	//3
		titles.add("奖励积分");	//4
		titles.add("所属网吧id");	//5
		titles.add("状态");	    //6
		dataMap.put("titles", titles);
		List<PageData> varOList = sysintegralService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INTEGRAL_ID"));	      //1
			vpd.put("var2", varOList.get(i).getString("INTEGRAL_NAME"));	  //2
			vpd.put("var3", varOList.get(i).getString("INTEGRAL_TYPE"));	  //3
			vpd.put("var4", varOList.get(i).get("INTEGRAL_SEND").toString()); //4
			vpd.put("var5", varOList.get(i).getString("ANGET_ID"));	          //5
			vpd.put("var6", varOList.get(i).getString("STATE"));	          //6
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
