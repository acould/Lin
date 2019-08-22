package com.lk.controller.system.rechargeRule;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.lk.entity.Message;
import com.lk.service.internet.matches.MatchesService;
import com.lk.service.system.menu.MenuManager;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.tb.RechargeRule.RechargeRuleManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;

@Controller
@RequestMapping(value="/rechargeRule")
public class RechargeRuleController extends BaseController {
	
	String menuUrl = "rechargeRule/list.do"; //菜单地址(权限用)
	public static final Logger log = LoggerFactory.getLogger(RechargeRuleController.class);
	
	@Resource(name = "rechargeRuleService")
	private RechargeRuleManager rechargeRuleService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Autowired
	private MatchesService matchesService;
	@Resource(name = "menuService")
	private MenuManager menuService;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		log.info("充值规则列表--list");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 
		
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
	    
		PageData pd = this.getPageData();
		pd.put("user", user);
		pd.put("page", page);
		List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
		if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID) && !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {// 不是管理员时(非网吧老板)
			if(StringUtil.isEmpty(pd.getString("store_id"))){
				pd.put("store_id", Joiner.on("','").join(storeid));
			}
		}
		//首页列表
		List<PageData> varList = rechargeRuleService.indexList(pd);
		
		//门店列表
		pd.put("internetId", user.getINTENET_ID());
		List<PageData> storeList = storeService.listByCard(pd);
		pd.remove("internetId");
		
		mv.addObject("storeList", storeList);
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		mv.setViewName("system/payManager/rechargeRule_list");
		return mv;
	}
	
	
	/**获取门店列表
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getStoreList")
	@ResponseBody
	public Message getStoreList()throws Exception{
		log.info("新增规则时，获取门店列表--getStoreList");
		User user = this.getUser();//得到用户

	    
		PageData pd = this.getPageData();
		pd.put("internet_id", user.getINTENET_ID());
		//type=pub_yl_jl  noType=noPub_noYl_noJl
		pd.put("type", "yl_jl");
		pd.put("noType", "noYl_noJl");
		pd.put("isEither", "true");

		//获取已开通和未开通银联/嘉联支付的门店
		Message message = matchesService.chooseStore(pd);


		//获取已创建规则的门店
		List<PageData> addedRulesStores = rechargeRuleService.addedRulesStores(pd);
		message.addData("addedRulesStores", addedRulesStores);

		//获取用户中心菜单的tab
		PageData pdMenu = new PageData();
		pdMenu.put("menu_url", "accountSettings/list.do");
		pdMenu.put("menu_name", "用户中心");
		pdMenu = menuService.findByUrlAndName(pdMenu);
		message.addData("pdMenu", pdMenu);

		return message;
	}
	
	/**
	 * 去新增规则
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAddRule")
	public ModelAndView goAddRule()throws Exception{
		log.info("去新增规则页面--goAddRule");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
		
		PageData pd = new PageData();
		// 网吧公众号--主体信息（商户名称和logo）
		pd.put("user_name", user.getNAME());
		//获取store_id列表和nameList
		String[] storeList = this.getRequest().getParameterValues("open_store_id");
		String idList = "";
		for(String str : storeList){
			idList += str +",";
		}
		idList = idList.substring(0, idList.length()-1);
		pd.put("storeList", idList);
		String nameList = this.getRequest().getParameter("nameList");
		pd.put("nameList", nameList);
		//标签列表
		pd.put("internet_id", user.getINTENET_ID());
		List<PageData> labelList = rechargeRuleService.labelList(pd);
		

		
		
		mv.addObject("pd", pd);
		mv.addObject("labelList", labelList);
		mv.setViewName("system/payManager/rechargeRule_edit");
		return mv;
	}
	
	
	/**
	 * 新增标签
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveLabel")
	@ResponseBody
	public JSONObject saveLabel()throws Exception{
		log.info("新增标签--saveLabel");
		User user = this.getUser();//得到用户
		
		//传入label_name,label_color
		PageData pd = this.getPageData();
		pd.put("user", user);
		
		return rechargeRuleService.saveLabel(pd);
	}
	
	
	/**
	 * 删除标签
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delLabel")
	@ResponseBody
	public JSONObject delLabel()throws Exception{
		log.info("删除标签--delLabel");
		User user = this.getUser();//得到用户
		
		//传入nameList
		PageData pd = this.getPageData();
		pd.put("user", user);
		
		return rechargeRuleService.deleteLabel(pd);
	}
	
	/**
	 * 保存规则
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveRule")
	@ResponseBody
	public JSONObject saveRule()throws Exception{
		log.info("新增保存规则--saveRule");
		User user = this.getUser();//得到用户
		
		//传入nameList
		PageData pd = this.getPageData();
		pd.put("user", user);
		
		return rechargeRuleService.saveRule(pd);
	}
	
	
	
	/**
	 * 缩略展示规则详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/showDetail")
	@ResponseBody
	public JSONObject showDetail()throws Exception{
		JSONObject result = new JSONObject();
		
		//传入store_id
		PageData pd = this.getPageData();
		
		//门店的充值规则列表
		List<PageData> ruleList = rechargeRuleService.findByStoreId(pd);
		
		//门店信息
		if(ruleList.size() > 0){
			result.put("nameList", ruleList.get(0).get("store_name"));
			result.put("ruleList", ruleList);
			result.put("result", "true");
		}else{
			result.put("result", "false");
			result.put("message", "该门店尚未创建充值规则");
		}
		
		return result;
	}
	
	
	/**
	 * 修改规则详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editDetail")
	public ModelAndView editDetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户
		
		//传入store_id
		PageData pd = this.getPageData();
		// 网吧公众号--主体信息（商户名称和logo）
		pd.put("user_name", user.getNAME());
		
		//门店的充值规则列表
		List<PageData> ruleList = rechargeRuleService.findByStoreId(pd);
		mv.addObject("ruleList", ruleList);
		
		//门店信息
		pd.put("storeList", ruleList.get(0).get("store_id"));
		pd.put("nameList", ruleList.get(0).get("store_name"));
		
		//标签列表
		pd.put("internet_id", user.getINTENET_ID());
		List<PageData> labelList = rechargeRuleService.labelList(pd);
		mv.addObject("labelList", labelList);
		
		
		mv.addObject("pd", pd);
		mv.setViewName("system/payManager/rechargeRule_edit");
		return mv;
	}
	
	
	/**
	 * 删除某门店的规则
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delRules")
	@ResponseBody
	public JSONObject delRules()throws Exception{
		JSONObject result = new JSONObject();
		//传入store_id
		PageData pd = this.getPageData();
		
		return rechargeRuleService.deleteRules(pd);
		
	}
	
	

	
	
	
	
}