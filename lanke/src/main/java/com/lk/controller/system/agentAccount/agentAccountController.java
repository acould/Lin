package com.lk.controller.system.agentAccount;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.agent.AgentManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.tb.Datum.DatumManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;

/**
 * 代理商账户管理
 * 
 * @author lzh
 */
@Controller
@RequestMapping(value = "/agentAccount")
public class agentAccountController extends BaseController {
	String menuUrl = "agentAccount/list.do"; // 菜单地址(权限用)
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "datumService")
	private DatumManager datumService;

	/**
	 * 代理商账户中心
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "代理商账户中心");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pd1 = new PageData();
		User user = this.getUser();//得到用户

		pd.put("USER_ID", user.getUSER_ID());
		page.setPd(pd);
		List<PageData> storeList = userService.selAgentStore(page, pd);// 通过用户id去获取代理商门店信息
		for (int i = 0; i < storeList.size(); i++) {
			if(StringUtil.isEmpty(storeList.get(i).get("STATUS"))) {
				storeList.get(i).put("STATUS", 0);
			}
		}
		pd1 = userService.selAgent(pd);// 通过用户id去获取代理商个人信息
		pd1.put("province", pd.getString("province"));
		pd1.put("city", pd.getString("city"));
		pd1.put("county", pd.getString("county"));
		pd1.put("STATE", pd.getString("STATE"));
		pd1.put("STATUS", pd.getString("STATUS"));
		mv.setViewName("system/agentAccount/agentAccount_list");
		mv.addObject("storeList", storeList);
		mv.addObject("pd", pd1);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 查看代理商信息
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/show")
	public ModelAndView show(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查看代理商信息");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

		pd.put("USER_ID", user.getUSER_ID());
		page.setPd(pd);
		pd = userService.selAgentShow(pd);// 通过用户id去获取代理商所有信息
		// 获取代理商资料的图片
		pd.put("intenetdatum_id", pd.getString("id"));
		pd.put("sort", "2");
		List<PageData> imgList = datumService.findByInternet(pd);
		String[] typeList = PublicManagerUtil.typeAgentList;
		for (PageData pdd : imgList) {
			for (int i = 0; i < typeList.length; i++) {
				if (pdd.getString("type").equals(typeList[i])) {
					pd.put(typeList[i] + "_url", pdd.getString("url"));
				}
			}

		}
		pd.put("state", "show");
		pd.put("NAME", user.getNAME());
		mv.setViewName("system/agent/agent_edit");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

}
