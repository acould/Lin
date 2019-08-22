package com.lk.controller.system.leaveMessage;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.LeaveMessage;
import com.lk.entity.system.Pictures;
import com.lk.entity.system.User;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.service.system.dictenty.DictEntyManager;
import com.lk.service.system.intenet.impl.IntenetService;
import com.lk.service.system.leaveMessage.LeaveMessageService;
import com.lk.service.system.store.impl.StoreService;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;


/**
 * 留言管理---控制层
 *
 */
@Controller
@RequestMapping(value="/lm")
public class LeaveMessageController extends BaseController {
	
	
	String menuUrl = "lm/list.do"; //菜单地址(权限用)
	@Resource(name="LeaveMessageService")
	private LeaveMessageService lmService;
	@Resource(name="picturesService")
	private PicturesManager picturesService;
	@Resource(name="intenetService")
	private IntenetService internetService;
	@Resource(name="sysuserService")
	private SysUserManager sysuserService;
	@Resource(name="storeService")
	private StoreService storeService;
	@Resource(name="storeUserService")
	private StoreUserManager storeUserService;
	
	@Resource(name = "dictentyService")
	private DictEntyManager dictentyService;
	
	
	/**
	 * 留言管理列表页面
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表LeaveMessage");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		List<PageData> plist = null;
		//搜索条件（关键词keywords，开始时间lastLoginStart，结束时间lastLoginEnd，门店id筛选STORE_ID，状态筛选LM_STATE）
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");//关键词
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(StringUtil.isNotEmpty(keywords)){
			//查询会员昵称需要加密
			pd.put("keywords", keywords);//未加码的
			pd.put("KEYWORD", URLEncoder.encode(keywords, "utf-8"));//加码的
		}
		if(StringUtil.isNotEmpty(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(StringUtil.isNotEmpty(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 23:59:59");
		}
		
		//判断角色及其相关联门店信息
		pd.put("internetId", user.getINTENET_ID());
		if(user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {//网吧老板
			plist = storeService.listByIntenet(pd);//筛选门店列表展示
		}else {
			plist = storeService.getStoreList(user.getUSER_ID()); // 通过user_id去找关联门店id和name的集合
			List<String> list = new ArrayList<>();
			for (int i = 0; i < plist.size(); i++) {
				list.add(i,plist.get(i).get("STORE_ID").toString());
			}
			pd.put("list", Joiner.on("','").join(list)); //门店id集合
		}
		
		//分页查询LeaveMessage列表
		page.setPd(pd);
		List<PageData>	lmList = lmService.list(page);
		
		//对列表信息数据处理
		for(PageData lea : lmList){
			//加载每条留言的图片信息
			List<Pictures> pList = picturesService.findByLMId(lea.getString("LM_ID"));
			lea.put("pList", pList);
			//对微信昵称解密
			lea.put("LM_USERNAME",URLDecoder.decode(lea.getString("LM_USERNAME"), "utf-8"));
			if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {
				lea.put("LM_USERNAME","***");
			}
			//加载处理留言人的信息
			String USER_ID = lea.get("USER_ID").toString();
			if(!USER_ID.isEmpty()){
				PageData pdUser = new PageData();
				pdUser.put("USER_ID", USER_ID);
				pdUser = sysuserService.findById(pdUser);
				pdUser.put("NAME", URLDecoder.decode(pdUser.getString("NAME"), "utf-8"));
				lea.put("pdUser",pdUser);
			}
		}
		mv.addObject("plist", plist);
		mv.addObject("pd", pd);
		mv.addObject("lmList", lmList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("system/LeaveMessage/leaveMessage_list");
		return mv;
	}
	
	
	 /**
	  * 查看详情页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goLmEdit")
	public ModelAndView goLmEdit(String lm_id)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据主键id查询留言
		LeaveMessage lmList = lmService.getOneByLMID(lm_id);
		//对微信昵称解密
		lmList.setLM_USERNAME(URLDecoder.decode(lmList.getLM_USERNAME(), "utf-8"));

		//加载留言的图片信息
		pd.put("USER_ID", lmList.getUSER_ID());
		PageData pduser = new PageData();
		pduser = sysuserService.findById(pd);
		pduser.put("NAME", URLDecoder.decode(pduser.getString("NAME"), "utf-8"));

		List<Pictures> picList = picturesService.findByLMId(lmList.getLM_ID());
		
		//判断微信昵称是否可见，0表示不可见；1表示可见
		mv.addObject("flag", "1");
		User user = this.getUser();//得到用户
		if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){
			mv.addObject("flag", "0");
		}
		
		//加载投诉的类型名称
		PageData pdType = new PageData();
    	pdType.put("DICT_TYPE", "LM001");
    	pdType.put("DICT_CODE", lmList.getLM_TYPEID());
    	pdType = dictentyService.findBySc(pdType);
    	
		lmList.setSYSNAME("查看投诉");
		
		mv.setViewName("system/LeaveMessage/LeaveMessage_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pduser", pduser);
		mv.addObject("pd", lmList);
		mv.addObject("pdType", pdType);
		mv.addObject("picList", picList);
		return mv;
	}
	 
	
	 /**
	  * 回复页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goLmback")
	public ModelAndView goLmback(String LM_ID)throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据主键id查询留言
		LeaveMessage lmList = lmService.getOneByLMID(LM_ID);

		//加载留言的图片信息
		lmList.setLM_USERNAME(URLDecoder.decode(lmList.getLM_USERNAME(), "utf-8"));
		pd.put("USER_ID", lmList.getUSER_ID());
		List<Pictures> picList = picturesService.findByLMId(lmList.getLM_ID());
		
		//判断微信昵称是否可见，0表示不可见；1表示可见
		mv.addObject("flag", "1");
		User user = this.getUser();//得到用户

		if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){
			mv.addObject("flag", "0");
		}
		
		//加载投诉的类型名称
		PageData pdType = new PageData();
    	pdType.put("DICT_TYPE", "LM001");
    	pdType.put("DICT_CODE", lmList.getLM_TYPEID());
    	pdType = dictentyService.findBySc(pdType);
    	
		lmList.setSYSNAME("查看投诉");
		mv.addObject("pd", lmList);
		mv.addObject("pdType", pdType);
		mv.addObject("picList", picList);
		mv.addObject("msg", "backLm");
		mv.setViewName("system/LeaveMessage/LeaveMessage_edit");
		return mv;
	}	
	
	
	/**
	 * 保存回复
	 * @param
	 */
	@RequestMapping(value="/backLm")
	@ResponseBody
	public JSONObject backLm() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"回复LeaveMessage");
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = this.getUser();//得到用户

		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有回复权限，请联系管理员！");
			return json;
		}
		
		pd.put("LM_ID", this.getRequest().getParameter("LM_ID"));
		pd.put("LM_STATE","1");//已回复
		pd.put("IS_VIEW", "2");//未读
		pd.put("USER_ID", user.getUSER_ID());
		pd.put("BACKCONTENT", this.getRequest().getParameter("BACKCONTENT"));
		pd.put("LM_BACKTIME", Tools.date2Str(new Date()));
		lmService.updateLm(pd);
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "回复成功！");
		return json;
	}
		
		
	 /**
	  * 导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出LeaveMessage到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		User user = this.getUser();//得到用户

		ModelAndView mv = new ModelAndView();
		this.getRequest().setCharacterEncoding("UTF-8");
		PageData pd = this.getPageData();
		//关键词和筛选条件（关键词keywords，开始时间lastLoginStart，结束时间lastLoginEnd，门店id筛选STORE_ID，状态筛选LM_STATE）
		String keywords = pd.getString("keywords");
		if(StringUtil.isNotEmpty(keywords)){
			pd.put("keywords", keywords.trim());
			pd.put("KEYWORD", URLEncoder.encode(keywords.trim(), "utf-8"));//加码的
		}
		
		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");
		if(StringUtil.isNotEmpty(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(StringUtil.isNotEmpty(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 23:59:59");
		}
		pd.put("INTERNET_ID", user.getINTENET_ID());
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("门店名称");	//1
		titles.add("会员昵称");	//2
		titles.add("留言时间");	//3
		titles.add("回复时间");	//4
		titles.add("回复人");	//5
		titles.add("状态");	//6
		dataMap.put("titles", titles);
		List<LeaveMessage> varOList = lmService.getLm(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getLM_STROE_NAME());	//1
			if(user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)){
				//对微信昵称进行解密，非网吧管理员导出其昵称展示位***
				String NAME = URLDecoder.decode(varOList.get(i).getLM_USERNAME(), "utf-8");
				vpd.put("var2", NAME);	//2
			}else{
				vpd.put("var2", "***");	//2
			}
			
			vpd.put("var3", varOList.get(i).getLM_DATE());	//3
			vpd.put("var4", varOList.get(i).getLM_BACKTIME());	//4
			String USER_ID = varOList.get(i).getUSER_ID();
			PageData pdUser = new PageData();
			pdUser.put("USER_ID", USER_ID);
			pdUser = sysuserService.findById(pdUser);
			if(pdUser != null){
				vpd.put("var5", pdUser.get("NAME"));	//5
				}else{
				vpd.put("var5", "");	//5
			}
			String STATE = varOList.get(i).getLM_STATE();
			if(STATE.equals("1")){
				STATE = "已回复";
			}else if(STATE.equals("2")){
				STATE = "待回复";
			}
			vpd.put("var6", STATE);	//6
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
