package com.lk.controller.system.memberlottery;

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

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.Const;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.Jurisdiction;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.service.system.memberlottery.MemberLotteryManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;

/** 
 * 说明：会员抽奖记录(兑奖管理)
 * 创建人：洪智鹏
 * 创建时间：2017-02-23
 */
@Controller
@RequestMapping(value="/memberlottery")
public class MemberLotteryController extends BaseController {
	
	String menuUrl = "memberlottery/list.do"; //菜单地址(权限用)
	@Resource(name="memberlotteryService")
	private MemberLotteryManager memberlotteryService;
	@Resource(name="storeService")
	private StoreManager storeService;
	@Resource(name="storeUserService")
	private StoreUserManager storeUserService;
	
	
	/**会有抽奖记录列表
	 * @param page 会员抽奖记录页面和检索字段信息
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表MemberLottery");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		
		pd.put("STATE", this.getRequest().getParameter("STATE"));
		pd.put("DUIHUAN_STORE", this.getRequest().getParameter("DUIHUAN_STORE"));
		pd.put("intenetId",user.getINTENET_ID() );
		String keywords = pd.getString("keywords");				//关键词检索条件
		
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
			keywords = URLEncoder.encode(keywords, "utf-8");
			pd.put("word", keywords);
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 24:00:00");
		} 
		if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){
			PageData pdStoreUser = new PageData();
			pdStoreUser.put("USER_ID", user.getUSER_ID());
			pdStoreUser = storeUserService.findByUserId(pdStoreUser); //找到操作用户所在的门店(通过用户id获取相关门店id)
			pd.put("storeId",pdStoreUser.getString("STORE_ID"));
		}
		
		page.setPd(pd);
		List<PageData>	varList = memberlotteryService.list(page);	 //列出MemberLottery列表(查询所有的会员兑奖信息)
		
		PageData pd2 = new PageData();
		pd2.put("internetId", user.getINTENET_ID());
		List<PageData>	sList = storeService.listByIntenet(pd2);   //通过网吧id查询现有门店(没有被禁用的)
		for(int i = 0;i < varList.size(); i ++ ){
			if(varList.get(i).containsKey("NAME")){
				varList.get(i).put("NAME", URLDecoder.decode(varList.get(i).getString("NAME"), "utf-8"));
			}
			
			if(varList.get(i).containsKey("LOTTERY_TIME")){
				String lotteryTime = varList.get(i).getString("LOTTERY_TIME");
				String expiryDate = varList.get(i).getString("EXPIRY_DATE");
				String availableTime = Tools.dateAddDay(Tools.str2Date(lotteryTime), Integer.parseInt(expiryDate)) + " " + lotteryTime.substring(11, 19);
				varList.get(i).put("available_time", availableTime);
				varList.get(i).put("now_date", Tools.date2Str(new Date()));
			}
		}
		
		mv.setViewName("system/memberlottery/memberlottery_list");
		mv.addObject("varList", varList);
		mv.addObject("sList",sList);
		mv.addObject("pd", pd);
		mv.addObject("user", user);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**
	 * 兑换奖品PageData包含兑奖需要的信息
	 * @param 
	 * @return  通过抽奖记录来兑换奖品
	 * @throws Exception
	 */
	@RequestMapping(value="/exchange")
	@ResponseBody
	public JSONObject exchange() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"兑换抽奖奖品");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		if(StringUtil.isNotEmpty(pd)){
			pd.put("STATE", "3");	//中奖状态
			pd.put("CONVERT_TIME", Tools.date2Str(new Date()));	//兑换时间
			memberlotteryService.editSqdj(pd); //兑换奖品后通过MEMBERLOTTERY_ID修改状态和时间
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "兑换成功！");
		}else{
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙，请稍后再试！");
		}
		return json;
	}
	
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出MemberLottery到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		this.getRequest().setCharacterEncoding("UTF-8");
		pd = this.getPageData();
		String keywords = new String(pd.getString("keywords").getBytes("iso-8859-1"),"utf-8");//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
			keywords = URLEncoder.encode(keywords, "utf-8");
			pd.put("word", keywords);
		}
		String lastLoginStart = pd.getString("lastLoginStart");	//开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd");		//结束时间
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 24:00:00");
		}

		User user = this.getUser();//得到用户
		pd.put("intenetId",user.getINTENET_ID() );
		if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)){
			PageData pdStoreUser = new PageData();
			pdStoreUser.put("USER_ID", user.getUSER_ID());
			pdStoreUser = storeUserService.findByUserId(pdStoreUser);  //找到操作用户所在的门店(通过用户id获取相关门店id)
			pd.put("storeId",pdStoreUser.getString("STORE_ID"));
		}
		
		Page page = new Page();
		page.setPd(pd);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("兑换门店");	//1
		titles.add("会员昵称");	//2
		titles.add("会员卡号");	//3
		titles.add("奖品");		//4
		titles.add("中奖状态");	//5
		titles.add("中奖时间");	//6
		titles.add("兑换时间");   //7
		dataMap.put("titles", titles);
		List<PageData> varOList = memberlotteryService.listExcel(page); //查询全部会员兑奖信息
		
		for(int i = 0;i < varOList.size(); i ++ ){
			if(varOList.get(i).containsKey("NAME")){
				varOList.get(i).put("NAME", URLDecoder.decode(varOList.get(i).getString("NAME"), "utf-8"));
			}
		}
		
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STORE_NAME"));	//1
			vpd.put("var2", varOList.get(i).getString("NAME"));	//2
			vpd.put("var3", varOList.get(i).getString("CARDED"));	//3
			vpd.put("var4", varOList.get(i).getString("LOTTERY_NAME"));	//4
			String STATE = varOList.get(i).getString("STATE");
			
			String lotteryTime = varOList.get(i).getString("LOTTERY_TIME");
			String expiryDate = varOList.get(i).getString("EXPIRY_DATE");
			String availableTime = Tools.dateAddDay(Tools.str2Date(lotteryTime), Integer.parseInt(expiryDate)) + " " + lotteryTime.substring(11, 19);
			String nowDate = Tools.date2Str(new Date());
			
			if(Tools.str2Date(nowDate).getTime() > Tools.str2Date(availableTime).getTime()){
				STATE = "已失效";
			}else{
				if(STATE.equals("2")){
					STATE = "兑奖中";
				}else if(STATE.equals("3")){
					STATE = "兑换成功";
				}else if(STATE.equals("1")){
					STATE = "未兑奖";
				}
			}
			
			vpd.put("var5", STATE);	//5
			vpd.put("var6", varOList.get(i).getString("LOTTERY_TIME"));	//6
			vpd.put("var7", varOList.get(i).getString("CONVERT_TIME"));	//6
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
