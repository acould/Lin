package com.lk.controller.system.consumption;

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
import com.lk.service.system.consumption.ConsumptionManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;

/** 
 * 说明：积分消耗表(积分消耗设置)
 * 创建人：洪智鹏
 * 创建时间：2017-02-23
 */
@Controller
@RequestMapping(value="/consumption")
public class ConsumptionController extends BaseController {
	
	String menuUrl = "consumption/list.do"; //菜单地址(权限用)
	@Resource(name="consumptionService")
	private ConsumptionManager consumptionService;
	
	
	/**列表
	 * @param page 展现用户消耗积分记录
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Consumption");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("INTENET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData>	varList = consumptionService.list(page);	//列出Consumption列表(通过网吧id查询积分消耗信息)
		mv.setViewName("system/consumption/consumption_list");
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
		mv.setViewName("system/consumption/consumption_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
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
		pd = consumptionService.findById(pd);	//根据ID读取(通过CONSUMPTION_ID查询积分消耗信息)
		mv.setViewName("system/consumption/consumption_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	
	/**
	 * 新增或修改保存
	 * @param pd 新增会员积分消耗记录
	 * @throws Exception
	 */
	@RequestMapping(value="/saveConsumption")
	@ResponseBody
	public JSONObject saveConsumption() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增或修改保存Consumption");
		JSONObject json = new JSONObject();

		User user = this.getUser();//得到用户

		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
		//判断新增还是修改
		if(StringUtil.isNotEmpty(pd.getString("CONSUMPTION_ID"))){
			//修改，校验权限
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有修改权限，请联系管理员！");
				return json;
			}
			consumptionService.edit(pd); //通过CONSUMPTION_ID修改积分消耗信息
		}else{
			//新增
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有新增权限，请联系管理员！");
				return json;
			}
			pd.put("CONSUMPTION_ID", this.get32UUID());	//主键
			
			PageData pdExist = consumptionService.findByInetrnet(pd); //通过CONSUMPTION_ID去查询id是否已存在
			if(StringUtil.isNotEmpty(pdExist)){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "当前类型的积分已存在，请确认后再提交！");
				return json;
			}else{
				consumptionService.save(pd); //保存新的积分消耗信息
			}
		}
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "保存成功！");
		return json;
	}
	
	
	/**删除
	 * @return  删除会员积分消耗记录通过主键删除
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JSONObject delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Consumption");
		JSONObject json = new JSONObject();
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		} 
		PageData pd = this.getPageData();
		consumptionService.delete(pd); //通过CONSUMPTION_ID删除积分消耗信息
		
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "删除成功！");
		return json;
	}
	
	
	 /**批量删除
	 * @param  批量删除会员积分消耗记录
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public JSONObject deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Consumption");
		JSONObject json = new JSONObject();
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		} 
		
		PageData pd = new PageData();		
		pd = this.getPageData();
		if(StringUtil.isNotEmpty(pd.getString("DATA_IDS"))){
			String DATA_IDS = pd.getString("DATA_IDS");
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			consumptionService.deleteAll(ArrayDATA_IDS); //通过CONSUMPTION_ID批量删除积分消耗信息
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "删除成功！");
		}else{
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "请选择需要删除的选项！");
			return json;
		}
		return json;
	}
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Consumption到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("网吧id");	//1
		titles.add("消耗类型");	//2
		titles.add("消耗多少");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = consumptionService.listAll(pd); //获取所有积分消耗信息
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INTENET_ID"));	//1
			vpd.put("var2", varOList.get(i).getString("CONSUMPTION_TYPE"));	//2
			vpd.put("var3", varOList.get(i).get("NUMBER").toString());	//3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
}
