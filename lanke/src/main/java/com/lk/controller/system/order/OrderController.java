package com.lk.controller.system.order;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.order.OrderManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 说明：订单管理
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
	String menuUrl = "order/list.do"; // 菜单地址(权限用)

	@Resource(name = "orderService")
	private OrderManager orderService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;

	/**
	 * 首页列表（分页数据，3个月内，按下单时间降序）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "列表Order");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		List<PageData> storeList = null;
		PageData pd = this.getPageData();
		// 查询条件（关键词keywords，下单的开始时间lastLoginStart，结束时间lastLoginEnd，门店筛选STORE_ID，状态筛选STATE）
		String keywords = pd.getString("keywords");
		if (StringUtil.isNotEmpty(keywords)) {
			pd.put("keywords", keywords.trim());
			pd.put("word", URLEncoder.encode(keywords, "utf-8"));// 加密后的微信昵称搜索
		}
		String lastLoginEnd = pd.getString("lastLoginEnd");
		if (StringUtil.isNotEmpty(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd + " 23:59:59");
		}

		// 判断角色及其相关联门店信息
		pd.put("internetId", user.getINTENET_ID());
		if (user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {// 网吧老板
			storeList = storeService.listByIntenet(pd);// 筛选门店列表展示
		} else {
			storeList = storeService.getStoreList(user.getUSER_ID()); // 通过user_id去找关联门店id和name的集合
			List<String> list = new ArrayList<>();
			for (int i = 0; i < storeList.size(); i++) {
				list.add(i, storeList.get(i).get("STORE_ID").toString());
			}
			pd.put("list", Joiner.on("','").join(list)); // 门店id集合
		}

		page.setPd(pd);
		List<PageData> varList = orderService.list(page);
		for (int i = 0; i < varList.size(); i++) {
			if (varList.get(i).containsKey("NAME")) {
				// 对会员昵称解码
				varList.get(i).put("NAME", URLDecoder.decode(varList.get(i).getString("NAME"), "utf-8"));
			}
		}

		mv.addObject("pd", pd); // 搜索条件
		mv.addObject("varList", varList);
		mv.addObject("storeList", storeList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		mv.setViewName("system/order/order_list"); // 页面路径
		return mv;
	}

	/**
	 * 提货
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public JSONObject edit(HttpServletRequest request) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改提货order");
		JSONObject json = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有修改权限，请联系管理员！");
			return json;
		}
		PageData pd = this.getPageData();
		pd.put("STATE", "3"); // 3:提货成功
		pd.put("SEND_TIME", Tools.date2Str(new Date()));
		orderService.edit(pd);
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "提货成功！");
		return json;
	}

	/**
	 * 导出到excel（3个月内，按下单时间降序）
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}

		ModelAndView mv = new ModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = new PageData();
		this.getRequest().setCharacterEncoding("UTF-8");
		pd = this.getPageData();

		// 查询条件（关键词keywords，下单的开始时间lastLoginStart，结束时间lastLoginEnd，门店筛选STORE_ID，状态筛选STATE）
		String keywords = pd.getString("keywords");
		if (StringUtil.isNotEmpty(keywords)) {
			pd.put("keywords", keywords.trim());
			pd.put("word", URLEncoder.encode(keywords, "utf-8"));
		}
		String lastLoginStart = pd.getString("lastLoginStart"); // 开始时间
		String lastLoginEnd = pd.getString("lastLoginEnd"); // 结束时间
		if (StringUtil.isNotEmpty(lastLoginStart)) {
			pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
		}
		if (StringUtil.isNotEmpty(lastLoginEnd)) {
			pd.put("lastLoginEnd", lastLoginEnd + " 23:59:59");
		}

		pd.put("INTERNET_ID", user.getINTENET_ID());
		if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)
				&& !user.getROLE_ID().equals(PublicManagerUtil.ADMINROLEID)) {
			PageData pdStore = new PageData();
			pdStore.put("USER_ID", user.getUSER_ID());
			pdStore = storeUserService.findByUserId(pdStore);
			pd.put("STORE_ID2", pdStore.get("STORE_ID"));
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("门店名称"); // 1
		titles.add("订单号"); // 2
		titles.add("购买时间"); // 3
		titles.add("商品名称"); // 4
		titles.add("发货方式"); // 5
		titles.add("会员昵称"); // 6
		titles.add("会员卡号"); // 7
		titles.add("订单状态"); // 8
		titles.add("发货时间"); // 9
		dataMap.put("titles", titles);
		List<PageData> varOList = orderService.exportExcel(pd);
		List<PageData> varList = new ArrayList<PageData>();

		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("STORE_NAME")); // 1
			vpd.put("var2", varOList.get(i).getString("ORDER_NUMBER")); // 2
			vpd.put("var3", varOList.get(i).getString("CREATE_TIME")); // 3
			vpd.put("var4", varOList.get(i).getString("ANAME")); // 4
			String DELIVERY = varOList.get(i).getString("DELIVERY");
			if (DELIVERY.equals("1")) {
				DELIVERY = "到店提";
			}
			vpd.put("var5", DELIVERY); // 5
			/* 对遍历出来的昵称进行解码bug */
			String NAME = URLDecoder.decode(varOList.get(i).getString("NAME"), "utf-8");

			vpd.put("var6", NAME); // 6
			vpd.put("var7", varOList.get(i).getString("CARDED")); // 7
			String STATE = varOList.get(i).getString("STATE");
			if (STATE.equals("1")) {
				STATE = "未提货";
			} else if (STATE.equals("2")) {
				STATE = "申请中";
			} else if (STATE.equals("3")) {
				STATE = "提货成功";
			}
			vpd.put("var8", STATE); // 8
			vpd.put("var9", varOList.get(i).getString("SEND_TIME")); // 9
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

}
