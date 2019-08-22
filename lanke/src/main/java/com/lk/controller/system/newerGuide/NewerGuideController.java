package com.lk.controller.system.newerGuide;


import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.role.RoleManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

@Controller
@RequestMapping(value = "/newerGuide")
public class NewerGuideController extends BaseController {
	
	String menuUrl = "newerGuide/list.do";
	
	String menuUrl2 = "newerGuide/messageList.do";
	public static final Logger log = LoggerFactory.getLogger(NewerGuideController.class);
	
	@Resource(name = "messageService")
	private MessageManager messageService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "roleService")
	private RoleManager roleService;
	
	/**
	 * 新手引导列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"获取图文列表sendRecord--List");
		ModelAndView mv = this.getModelAndView();

		mv.setViewName("system/newerGuide/newerGuide_list");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	/**
	 * 加载首页重要消息提醒
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMessage")
	@ResponseBody
	public JSONObject getMessage() throws Exception {
		JSONObject result = new JSONObject();
		User user = this.getUser();//得到用户

	    if(user.getROLE_ID().equals("1")){
			result.put("result", "false");
			return result;
		}
		if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)){
			result.put("result", "false");
			return result;
		}
		PageData pd = this.getPageData();
		pd.put("state", "0");//未读消息
		pd.put("internet_id", user.getINTENET_ID());
		List<PageData> varList = messageService.findByStateOrType(pd);
		result.put("result", "true");
		result.put("varList", varList);
		return result;
	}
	
	
	
	/**
	 * 去消息通知列表
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/messageList")
	public ModelAndView messageList() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/newerGuide/message_list");
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	/**
	 * 分页加载消息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/loadMessage")
	@ResponseBody
	public JSONObject loadMessage(Page page)throws Exception{
		log.info("分页加载消息列表--loadMessage");
		User user = this.getUser();//得到用户
	    
	    //传入搜索条件
	    PageData pd = this.getPageData();
	    pd.put("user", user);
	    pd.put("internet_id", user.getINTENET_ID());

	    JSONObject result = messageService.loadMessage(pd, page);
		System.out.println("pageController======"+page.toString());
	    return result;
	}
	
	
	
	@RequestMapping(value="/showDetail")
	@ResponseBody
	public JSONObject showDetail(Page page)throws Exception{
		log.info("查看--showDetail");
		User user = this.getUser();//得到用户

	    JSONObject result = new JSONObject();
	    if(!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {//不是网吧老板
	    	result.put("result", PublicManagerUtil.FALSE);
	    	result.put("message", "没有权限");
	    	return result;
		}
	    
	    //传入message_id，type
	    PageData pd = this.getPageData();
	    pd.put("internet_id", user.getINTENET_ID());
	    
	    if(StringUtil.isNotEmpty(pd.getString("message_id"))){
			PageData pdMessage = new PageData();
			pdMessage.put("message_id", pd.getString("message_id"));
			pdMessage.put("state", "1");
			pdMessage.put("user_id", user.getUSER_ID());
			pdMessage.put("read_time", Tools.date2Str(new Date()));
			messageService.edit(pdMessage);
	    }
	    
	    return  messageService.showDetail(pd);
	}
	
	/**
	 * 获取未读消息数量
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getMessageNum")
	@ResponseBody
	public JSONObject getMessageNum(Page page)throws Exception{
		log.info("获取未读消息数量--getMessageNum");
		JSONObject result = new JSONObject();
		User user = this.getUser();//得到用户
	    
	    String message_id = this.getRequest().getParameter("message_id");
	    if(StringUtil.isNotEmpty(message_id)){
			PageData pdMessage = new PageData();
			pdMessage.put("message_id", message_id);
			pdMessage.put("state", "1");
			pdMessage.put("user_id", user.getUSER_ID());
			pdMessage.put("read_time", Tools.date2Str(new Date()));
			messageService.edit(pdMessage);
	    }
		
		PageData pdd = new PageData();
		pdd.put("state", "0");
		pdd.put("internet_id", user.getINTENET_ID());
		int number = messageService.findNumber(pdd);
	    
	    result.put("number", number);
	    return  result;
	}
}
