package com.lk.controller.internet.group;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.util.PublicManagerUtil;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.Jurisdiction;
import com.lk.util.Tools;
import com.lk.service.internet.group.GroupManager;
import com.lk.service.internet.groupstore.GroupStoreManager;
import com.lk.service.internet.team.TeamManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;

/** 
 * 说明：组局功能(该功能后期需要)
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping(value="/group")
public class GroupController extends BaseController {
	// 几种常用返回值状态标识()
	public static final String SOMEONE = "1";  //有人
	public static final String UNMANNED = "2"; //无人
	// 几种常用返回值状态标识
	public static final String INTHEGAME = "bisaizhong"; //比赛中
	public static final String NOTFINISHED = "weijieshu";//未结束
	public static final String END = "jieshu";           //已结束
	public static final String UNKNOWN = "weizhi";       //未知
	//发布组局功能的状态(1为保存或者取消发布，2为发布)
	public static final String STATE1 = "1"; 
	public static final String STATE2 = "2"; 
	
	String menuUrl = "group/list.do"; //菜单地址(权限用)
	@Resource(name="groupService")
	private GroupManager groupService;
	@Resource(name="storeService")
	private StoreManager storeService;
	@Resource(name="storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name="groupstoreService")
	private GroupStoreManager groupstoreService;
	@Resource(name="teamService")
	private TeamManager teamService;
	
	/**保存组局功能
	 * @param pageData 里面包含组局相关信息
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Group");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//获取用户

	    PageData pd = this.getPageData();
	    PageData pagedata = new PageData();
	    pagedata.put("user", user);
	    pagedata.put("pd", pd);
	    PageData pdpd = groupService.groupSave(pagedata);
		if(pdpd.containsKey("msg")&&pdpd.getString("msg")!=null){
			if(pdpd.containsKey("result")&&pdpd.getString("result")!=null){
				mv.addObject("result", pdpd.getString("result"));
			}
			mv.addObject("msg",pdpd.getString("msg"));
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 发布组局功能修改状态字段
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateState")
	@ResponseBody
	public Object updateState() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"发布组局状态");
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		PageData pdpd = groupService.findById(pd);
		Date dt = Tools.str2Date(pdpd.getString("BEGIN_TIME"));//报名截止时间
		if(dt.getTime() >= new Date().getTime()){
			pd.put("GROUP_ID", this.getRequest().getParameter("GROUP_ID"));
			pd.put("STATE", STATE2);//1为保存或者取消发布，2为发布
			groupService.edit(pd);
			map.put("result", PublicManagerUtil.SUCCESS);
		}else{
			map.put("result",  PublicManagerUtil.ERR);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 取消发布 组局功能 已经发布的取消发布
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/endState")
	@ResponseBody
	public Object endState() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"发布组局状态");
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		pd.put("GROUP_ID", this.getRequest().getParameter("GROUP_ID"));
		pd.put("STATE", STATE1);//1为保存或者取消发布，2为发布
		groupService.edit(pd);
		map.put("result", PublicManagerUtil.SUCCESS);
		return AppUtil.returnObject(pd, map);
	}
	
	/**删除组局功能
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Group");
		Map<String,Object> map = new HashMap<String, Object>();
		PageData pd = this.getPageData();
		try {
			//先删除组局和门店关系
			groupstoreService.deleteByGroupId(pd);
			//再删除门店跟战队关系
			teamService.deleteByGroupId(pd);
			groupService.delete(pd);
			map.put("result", PublicManagerUtil.SUCCESS);
		} catch (Exception e) {
			map.put("result", PublicManagerUtil.ERR);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**修改组局功能
	 * @param PageData
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Group");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		PageData pagedata = new PageData();
		pagedata.put("request", request);
		pagedata.put("pd", pd);
		PageData pdpd = groupService.groupEdit(pagedata);
		if(pdpd.containsKey("msg")&&pdpd.getString("msg")!=null){
			mv.addObject("msg",PublicManagerUtil.SUCCESS);
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page  包含检索字段
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Group");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		User user = this.getUser();//获取用户

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		pd.put("INTERNET_ID", user.getINTENET_ID());
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 24:00:00");
		}else if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		PageData pdd = new PageData();
		pdd.put("GROUP_ID", this.getRequest().getParameter("GROUP_ID"));
		List<PageData> sList =  groupService.listByGroupId(pdd);
		List<String> sb = new ArrayList<String>();
		if(sList.size() > 0){
			for (int i = 0; i < sList.size(); i++) {
				sb.add(sList.get(i).getString("STORE_ID"));
			}
		}
		if(user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) || user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){
			page.setPd(pd);
		}else{
			List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
			pd.put("STORE_ID", Joiner.on("','").join(storeid));
			page.setPd(pd);
		}
		List<PageData> varList = groupService.list(page);	//列出Group列表
		List<PageData> teamlist = teamService.listGroupidTeam(pdd); //列出Team表
		Date dt = null;
		Date da = null;
		for (PageData pageData : varList) {
			if(teamlist.size()>0){
				pageData.put("zuju", SOMEONE);//战队有人
			}else{
				pageData.put("zuju", UNMANNED);//战队无人
			}
			dt = Tools.str2Date(pageData.getString("BEGIN_TIME"));//比赛时间
			da = Tools.str2Date(pageData.getString("BM_TIME"));//截止时间
			if(da.getTime() >= new Date().getTime() && dt.getTime() <= new Date().getTime()){
				pageData.put("zhuangtai", INTHEGAME);
			}else if(dt.getTime() >= new Date().getTime()){
				pageData.put("zhuangtai", NOTFINISHED);
			}else if(dt.getTime() <= new Date().getTime()){
				pageData.put("zhuangtai", END);
			}else{
				pageData.put("zhuangtai", UNKNOWN);
			}
		}
		mv.setViewName("internet/group/group_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("sb", sb);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param  点击新增按钮触发
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = this.getUser();//获取用户

		List<PageData> storeList =  new ArrayList<PageData>();
		if(user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) || user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){//网吧管理员可以看到所有分店
			PageData pdStore = new PageData();
			pdStore.put("internetId", user.getINTENET_ID());
			storeList = storeService.listByIntenet(pdStore);
		}else{//其他成员无非就是看到自己所在的门店
			PageData pdStore = new PageData();
			pdStore.put("USER_ID", user.getUSER_ID());
			pdStore = storeUserService.findByUserId(pdStore);
			storeList = storeService.listByStore(pdStore);
		}
		mv.addObject("storeList", storeList);
		mv.setViewName("internet/group/group_edit");
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
		PageData pd = this.getPageData();
		pd = groupService.findById(pd);	//根据ID读取
		User user = this.getUser();//获取用户

		List<PageData> storeList =  new ArrayList<PageData>();
		if(user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) || user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){//网吧管理员可以看到所有分店
			PageData pdStore = new PageData();
			pdStore.put("internetId", user.getINTENET_ID());
			storeList = storeService.listByIntenet(pdStore);
		}else{//其他成员无非就是看到自己所在的门店
			PageData pdStore = new PageData();
			pdStore.put("USER_ID", user.getUSER_ID());
			pdStore = storeUserService.findByUserId(pdStore);
			storeList = storeService.listByStore(pdStore);
		}
		Date dt = null;
		Date da = null;
		dt = Tools.str2Date(pd.getString("BEGIN_TIME"));//比赛时间
		da = Tools.str2Date(pd.getString("BM_TIME"));//截止时间
		if(da.getTime() >= new Date().getTime() && dt.getTime() <= new Date().getTime()){
			pd.put("zhuangtai", INTHEGAME);
		}else if(dt.getTime() >= new Date().getTime()){
			pd.put("zhuangtai", NOTFINISHED);
		}else if(dt.getTime() <= new Date().getTime()){
			pd.put("zhuangtai", END);
		}
		List<PageData> slist = groupstoreService.listStore(pd); //获得本条组局所对应的门店
		List<String> sb = new ArrayList<String>();
		if(slist.size() > 0){
			for (int i = 0; i < slist.size(); i++) {
				sb.add(slist.get(i).getString("STORE_ID"));
			}
		}
		mv.addObject("storeList", storeList);
		mv.setViewName("internet/group/group_update");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		mv.addObject("sb", sb);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Group");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			groupService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Group到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("战局名称");	    //1
		titles.add("比赛开始时间");	//2
		titles.add("报名截止时间");	//3
		titles.add("战队数量");	    //4
		titles.add("队伍最低人数");	//5
		titles.add("队伍最高人数");	//6
		titles.add("状态");	        //7(1为保存或者取消发布，2为发布)
		titles.add("创建时间");	    //8
		titles.add("创建用户");	    //9
		dataMap.put("titles", titles);
		List<PageData> varOList = groupService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME"));	//1
			vpd.put("var2", varOList.get(i).getString("BEGIN_TIME"));	//2
			vpd.put("var3", varOList.get(i).getString("BM_TIME"));	//3
			vpd.put("var4", varOList.get(i).get("TEAM_NUMBER").toString());	//4
			vpd.put("var5", varOList.get(i).get("MIN_NUMBER").toString());	//5
			vpd.put("var6", varOList.get(i).get("MAX_NUMBER").toString());	//6
			vpd.put("var7", varOList.get(i).getString("STATE"));	//7
			vpd.put("var8", varOList.get(i).getString("CREATE_TIME"));	//8
			vpd.put("var9", varOList.get(i).getString("CREATE_USER"));	//9
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
