package com.lk.controller.system.welcome;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.invite.InviteManager;
import com.lk.service.system.welcome.WelcomeManager;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.DateUtil;
import com.lk.util.FileUpload;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;

/** 
 * 说明： 网吧邀请好友设置(欢迎语设置)
 * 创建人：白弋冉
 * 创建时间：2017-07-04
 * @version
 */
@Controller
@RequestMapping(value="/welcome")
public class WelcomeController extends BaseController {

	String menuUrl = "welcome/list.do"; //菜单地址(权限用)
	@Resource(name="welcomeService")
	private WelcomeManager welcomeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(@RequestParam(value="WELCOME",required=false)  String WELCOME) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增welcome");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("INTENET_ID", user.getINTENET_ID());
		pd.put("WELCOME", WELCOME);
		welcomeService.save(pd);
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除intenet_welcome");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		try {
			welcomeService.delete(pd);
			map.put("result", PublicManagerUtil.SUCCESS);
		} catch (Exception e) {
			map.put("result", PublicManagerUtil.ERR);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			 @RequestParam(value="WELCOME",required=false)  String WELCOME) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改intenet_welcome");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		User user = this.getUser();//获取用户

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("WELCOME", WELCOME);
		pd.put("INTENET_ID", user.getINTENET_ID());
		welcomeService.edit(pd);
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表welcome");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("INTENET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData>	varList = welcomeService.list(page);	//列出invite列表
		mv.setViewName("system/welcome/welcome_list");
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
		User user = this.getUser();//获取用户

		pd.put("INTENET_ID", user.getINTENET_ID());
		mv.setViewName("system/welcome/welcome_edit");
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
		pd = welcomeService.findById(pd);	//根据ID读取
		mv.setViewName("system/welcome/welcome_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除invite");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			welcomeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出invite到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("网吧id");	//1
		titles.add("地址");	//2
		titles.add("联系电话");	//3
		titles.add("网吧图片1");	//4
		titles.add("图片2");	//5
		titles.add("图片3");	//6
		titles.add("图片4");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = welcomeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INTENET_ID"));	//1
			vpd.put("var2", varOList.get(i).getString("ADDRESS"));	//2
			vpd.put("var3", varOList.get(i).getString("PHONE"));	//3
			vpd.put("var4", varOList.get(i).getString("PIC_ONE"));	//4
			vpd.put("var5", varOList.get(i).getString("PIC_TWO"));	//5
			vpd.put("var6", varOList.get(i).getString("PIC_THREE"));	//6
			vpd.put("var7", varOList.get(i).getString("PIC_FOUR"));	//7
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
}
