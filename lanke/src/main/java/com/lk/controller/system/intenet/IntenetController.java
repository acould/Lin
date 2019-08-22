package com.lk.controller.system.intenet;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechat.WechatManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.WechatMenuUtil;

/** 
 * 说明：网吧(网吧管理/网吧基本信息)
 * 创建人：洪智鹏
 * 创建时间：2016-10-20
 */
@Controller
@RequestMapping(value="/intenet")
public class IntenetController extends BaseController {
	
	String menuUrl = "intenet/list.do"; //菜单地址(权限用)
	@Resource(name="intenetService")
	private IntenetManager intenetService;
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="wechatService")
	private WechatManager wechatService;
	@Resource(name="terraceService")
	private TerraceManager terraceService;
	@Resource(name = "autoReplyService")
    private AutoReplyService autoReplyService;
	
	/**保存
	 * @param PageData 包含网吧新增需要的数据
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Intenet");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pdUser = new PageData();
		pdUser.put("USER_ID", this.get32UUID());

		User user = this.getUser();//获取用户

		pd.put("INTENET_ID", this.get32UUID());	//主键
		pd.put("CREATE_USER", user.getUSER_ID());	//创建人
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("UPDATE_USER",user.getUSER_ID());	//修改人
		pd.put("USER_ID", pdUser.get("USER_ID"));   //ID 主键 
		intenetService.save(pd);
		pdUser.put("LAST_LOGIN", "");				//最后登录时间
		pdUser.put("IP", "");						//IP
		pdUser.put("STATUS", "0");					//状态
		pdUser.put("SKIN", "default");
		pdUser.put("RIGHTS", "");		
		pdUser.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USER_NAME"), pd.getString("PASSWORD")).toString());	//密码加密
		pdUser.put("USERNAME", pd.getString("USER_NAME"));
		pdUser.put("INTENET_ID", pd.get("INTENET_ID"));
		pdUser.put("ANGET_ID", user.getANGET_ID());
		pdUser.put("INTEGRAL", 0);
		pdUser.put("EMAIL", pd.get("EMAIL"));
		pdUser.put("NAME", pd.get("INTENET_NAME"));
		pdUser.put("ROLE_ID", PublicManagerUtil.INTERNETROLEID);
		if((null == userService.findByUsername(pdUser))&& (null==userService.findByUE(pdUser))){	//判断用户名是否存在
			userService.saveU(pdUser); 					//执行保存
			
			mv.addObject("msg",PublicManagerUtil.SUCCESS);
		}else{
			mv.addObject("msg",PublicManagerUtil.FAILED);
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out  PageData删除网吧数据
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Intenet");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		intenetService.delete(pd);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	/**设置公众号菜单
	 * @param out  设置网吧对应的公众号菜单
	 * @throws Exception
	 */
	@RequestMapping(value="/setupMenu")
	public void setupMenu(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"设置菜单");
          String intenetId=this.getRequest().getParameter("INTENET_ID");		
		Intenet org=intenetService.getIntenetById(intenetId);
		//获取公众号凭证
		String access_token = autoReplyService.getAuthToken(org.getINTENET_ID());
				
		WechatMenuUtil.createMenu(org,access_token);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	
	/**删除公众号菜单
	 * @param out 删除网吧对应公众号菜单
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteMenu")
	public void deleteMenu(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"设置菜单");
          String intenetId=this.getRequest().getParameter("INTENET_ID");	
          
		Intenet org=intenetService.getIntenetById(intenetId);
		//获取公众号凭证
		String access_token = autoReplyService.getAuthToken(org.getINTENET_ID());
		
		 WechatMenuUtil.deleteMenu(org,access_token);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}
	
	/**修改
	 * @param PageData 修改网吧信息
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Intenet");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = this.getUser();//获取用户

		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("UPDATE_USER",user.getUSER_ID());	//修改人
		intenetService.edit(pd);
		mv.addObject("msg",PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page 包含检索字典传在keywords 查询网吧列表
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Intenet");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
        User user = this.getUser();//获取用户

		if(!user.getROLE_ID().endsWith("1")){
			pd.put("userId", user.getUSER_ID());
		}
		page.setPd(pd);
		List<PageData>	varList = intenetService.list(page);	//列出Intenet列表
		
		mv.setViewName("system/intenet/intenet_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
	
		if(user.getROLE_ID().endsWith("1")){
			mv.addObject("cdsz", 1);
		}
	    mv.addObject("abcd", 1);
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
		
		mv.setViewName("system/intenet/intenet_edit");
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
		pd = intenetService.findById(pd);	//根据ID读取
		mv.setViewName("system/intenet/intenet_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param 批量删除网吧信息
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Intenet");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			intenetService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Intenet到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("网吧名称");	//1
		titles.add("微信公众号id");	//2
		titles.add("微信key");	//3
		titles.add("状态");	//4
		titles.add("省");	//5
		titles.add("城市");	//6
		titles.add("地址");	//7
		titles.add("电话号码");	//8
		titles.add("支付宝账户");	//9
		titles.add("邮箱");	//10
		titles.add("创建人");	//11
		titles.add("创建时间");	//12
		titles.add("修改人");	//13
		titles.add("修改时间");	//14
		dataMap.put("titles", titles);
		List<PageData> varOList = intenetService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INTENET_NAME"));	//1
			vpd.put("var2", varOList.get(i).getString("WECHAT_ID"));	//2
			vpd.put("var3", varOList.get(i).getString("WECHAT_KET"));	//3
			vpd.put("var4", varOList.get(i).getString("STATE"));	//4
			vpd.put("var5", varOList.get(i).getString("PROVINCE"));	//5
			vpd.put("var6", varOList.get(i).getString("CITY"));	//6
			vpd.put("var7", varOList.get(i).getString("ADDRESS"));	//7
			vpd.put("var8", varOList.get(i).getString("PHONE"));	//8
			vpd.put("var9", varOList.get(i).getString("TAO_BAO"));	//9
			vpd.put("var10", varOList.get(i).getString("EMAIL"));	//10
			vpd.put("var11", varOList.get(i).getString("CREATE_USER"));	//11
			vpd.put("var12", varOList.get(i).getString("CREATE_TIME"));	//12
			vpd.put("var13", varOList.get(i).getString("UPDATE_USER"));	//13
			vpd.put("var14", varOList.get(i).getString("UPDATE_TIME"));	//14
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
