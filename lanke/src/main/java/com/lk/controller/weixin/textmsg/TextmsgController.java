package com.lk.controller.weixin.textmsg;


import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.lk.service.weixin.command.CommandService;
import com.lk.service.weixin.imgmsg.ImgmsgService;
import com.lk.service.weixin.textmsg.TextmsgService;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Tools;

/** 
 * 类名称：textmsgController(关注回复)
 * 创建人：FH 
 * 创建时间：2015-05-05
 */
@Controller
@RequestMapping(value="/textmsg")
public class TextmsgController extends BaseController {
	
	String menuUrl = "textmsg/list.do"; //菜单地址(权限用)
	@Resource(name="textmsgService")
	private TextmsgService textmsgService;
	@Resource(name="commandService")
	private CommandService commandService;
	@Resource(name="imgmsgService")
	private ImgmsgService imgmsgService;
	
	/**新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

	    
		PageData pd = this.getPageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		pd.put("TEXTMSG_ID", this.get32UUID());	//主键
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); //创建时间
		textmsgService.save(pd);
		
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;}
		
		PageData pd = new PageData();
		pd = this.getPageData();
		textmsgService.delete(pd);
		
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	
	/**修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		
		PageData pd = new PageData();
		pd = this.getPageData();
		textmsgService.edit(pd);
		
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表textmsg");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
	    
		PageData pd = this.getPageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		String KEYWORD = pd.getString("KEYWORD");
		if(null != KEYWORD && !"".equals(KEYWORD)){
			pd.put("KEYWORD", KEYWORD.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = textmsgService.list(page);	//列出textmsg列表
		
		mv.setViewName("weixin/textmsg/textmsg_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @return
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, Jurisdiction.getUsername()+"去新增textmsg页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		mv.setViewName("weixin/textmsg/textmsg_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去关注回复页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goSubscribe")
	public ModelAndView goSubscribe() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"去关注回复页面");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
	    
		PageData pd = this.getPageData();
		pd.put("KEYWORD", "关注");
		pd.put("INTERNET_ID", user.getINTENET_ID());
		PageData msgpd = new PageData();
		msgpd = textmsgService.findByKw(pd);
		if(null != msgpd){
			mv.addObject("msg", "文本消息");
			mv.addObject("content", msgpd.getString("CONTENT"));
		}else{
			msgpd = imgmsgService.findByKw(pd);
			if(null != msgpd){
				mv.addObject("msg", "图文消息");
				mv.addObject("content", "标题："+msgpd.getString("TITLE1"));
			}else{
				msgpd = commandService.findByKw(pd);
				if(null != msgpd){
					mv.addObject("msg", "命令");
					mv.addObject("content", "执行命令："+msgpd.getString("COMMANDCODE"));
				}else{
					mv.addObject("msg", "无回复");
				}
			}
		}
		mv.setViewName("weixin/subscribe");
		mv.addObject("pd", msgpd);
		return mv;
	}
	
	/**去修改页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"去修改textmsg页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = textmsgService.findById(pd);	//根据ID读取
		
		mv.setViewName("weixin/textmsg/textmsg_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**批量删除
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception {
		logBefore(logger, "批量删除textmsg");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
		
		PageData pd = new PageData();		
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			textmsgService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", PublicManagerUtil.OK);
		}else{
			pd.put("msg", PublicManagerUtil.NO);
		}
		
		List<PageData> pdList = new ArrayList<PageData>();
		pdList.add(pd);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", pdList);
		
		return AppUtil.returnObject(pd, map);
	}
	
	/**判断关键词是否存在
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/hasK")
	@ResponseBody
	public Object hasK() throws Exception{
		User user = this.getUser();//得到用户
	    
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = PublicManagerUtil.SUCCESS;
		PageData pd = this.getPageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		pd.put("KEYWORD", this.getRequest().getParameter("KEYWORD"));
		String textmsgId = this.getRequest().getParameter("TEXTMSG_ID");
		if(textmsgId == null || textmsgId.equals("")){//为空时，表示是新增功能
			if(textmsgService.findByKw(pd) != null || imgmsgService.listByKw(pd).size() > 0){//不为空，表示已有该关键字
				errInfo = PublicManagerUtil.ERROR;
			}
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出textmsg到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("关键词");//1
			titles.add("内容");	//2
			titles.add("创建时间");//3
			titles.add("状态");	//4
			titles.add("备注");	//5
			dataMap.put("titles", titles);
			List<PageData> varOList = textmsgService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("KEYWORD"));	//1
				vpd.put("var2", varOList.get(i).getString("CONTENT"));	//2
				vpd.put("var3", varOList.get(i).getString("CREATETIME"));	//3
				vpd.put("var4", varOList.get(i).get("STATUS").toString());	//4
				vpd.put("var5", varOList.get(i).getString("BZ"));	//5
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
