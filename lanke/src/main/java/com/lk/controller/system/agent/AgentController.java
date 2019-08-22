package com.lk.controller.system.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.agent.AgentManager;
import com.lk.service.tb.Datum.DatumManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 代理商管理
 * @author lzh
 */
@Controller
@RequestMapping(value = "/agent")
public class AgentController extends BaseController {
	String menuUrl = "agent/list.do"; // 菜单地址(权限用)
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "datumService")
	private DatumManager datumService;

	/**
	 * 列表 代理商管理列表(后台)
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "代理商列表(后台)");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		// 用户信息列表
		List<PageData> list = agentService.list(page, pd);
		mv.setViewName("system/agent/agent_list");
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 查看用户(查看代理商详情)
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/toShow")
	public ModelAndView toShow(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查看用户");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> list = agentService.show(page,pd);
		for (int i = 0; i < list.size(); i++) {
			if(StringUtil.isEmpty(list.get(i).get("STATUS"))) {
				list.get(i).put("STATUS", 0);
			}
		}
		mv.setViewName("system/agent/agent_show");
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去编辑代理商页面
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit")
	public ModelAndView toEdit(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "去编辑代理商页面");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

		pd = agentService.findById(pd); // 通过id获取用户信息
		//获取代理商资料的图片
		pd.put("intenetdatum_id", pd.getString("id"));
		pd.put("sort", "2");
		List<PageData> imgList = datumService.findByInternet(pd);
		String[] typeList = PublicManagerUtil.typeAgentList;
		for(PageData pdd : imgList){
			for(int i=0;i<typeList.length;i++){
				if(pdd.getString("type").equals(typeList[i])){
					pd.put(typeList[i]+"_url", pdd.getString("url"));
				}
			}
		}
		pd.put("state", "edit");
		pd.put("NAME", user.getNAME());
		mv.setViewName("system/agent/agent_edit");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增代理商页面
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "去新增代理商页面");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		User user = this.getUser();//得到用户

		pd.put("NAME", user.getNAME());
		pd.put("state", "add");
		mv.setViewName("system/agent/agent_edit");
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 保存新增/修改代理商信息
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public JSONObject edit(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "保存新增/修改代理商信息");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();
		pd.put("INTENET_ID", user.getINTENET_ID());
		//保存新增/修改代理商信息
		json=agentService.toEdit(pd);
		return json;
	}

	/**
	 * 导出到excel
	 * 
	 * @param STORE_ID--门店id
	 * @return mv--返回指定信息和视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出代理商信息到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		this.getRequest().setCharacterEncoding("UTF-8");
		pd = this.getPageData();

		// 管理员可以看到全部代理商
		Page page = new Page();
		page.setPd(pd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("公司名称");
		titles.add("省");
		titles.add("市");
		titles.add("区");
		titles.add("联系人");
		titles.add("手机号码");
		titles.add("代理商编号");
		dataMap.put("titles", titles);

		String keywords = new String(pd.getString("keywords").getBytes("iso-8859-1"), "utf-8");// 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String str = this.getRequest().getParameter("str");
		List<String> list = Arrays.asList(str.split(","));
		List<PageData> varList = new ArrayList<PageData>();
		for (int j = 0; j < list.size(); j++) {
			pd.put("id", list.get(j));
			List<PageData> varOList = agentService.list(page, pd); // 加V信息导出为excel
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("company_name"));
				vpd.put("var2", varOList.get(i).getString("province"));
				vpd.put("var3", varOList.get(i).getString("city"));
				vpd.put("var4", varOList.get(i).getString("county"));
				vpd.put("var5", varOList.get(i).getString("contacts_name"));
				vpd.put("var6", varOList.get(i).getString("phone"));
				vpd.put("var7", varOList.get(i).getString("agent_number"));
				vpd.put("var8", varOList.get(i).getString("agent_number"));
				varList.add(vpd);
			}
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
