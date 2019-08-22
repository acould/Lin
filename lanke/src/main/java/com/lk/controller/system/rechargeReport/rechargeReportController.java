package com.lk.controller.system.rechargeReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.lk.entity.unionPay.TBRecharge;
import com.lk.service.system.rechargeReport.RechargeReportManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;

/**
 * 充值报表(网吧)
 * 
 * @author lzh
 */
@Controller
@RequestMapping(value = "/rechargeReport")
public class rechargeReportController extends BaseController {
	String menuUrl = "rechargeReport/list.do"; // 菜单地址(权限用)
	@Resource(name = "rechargeReportService")
	private RechargeReportManager rechargeReportService;
	@Resource(name="generateOrderService")
	private GenerateOrderService generateOrderService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	private static SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",java.util.Locale.ENGLISH);
	
	/**
	 * 充值报表列表
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/listManages")
	public JSONObject listManages(Page page) throws Exception {
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		if(!"admin".equals(pd.getString("state"))){
		pd.put("internet_id", user.getINTENET_ID());
		}
		String endTime = pd.getString("endTime"); // 结束时间
		if (endTime != null && !"".equals(endTime)) {
			pd.put("endTime", endTime + " 24:00:00");
		}
		if (!PublicManagerUtil.INTERNETROLEID.equals(user.getROLE_ID())
				&& !PublicManagerUtil.ADMINROLEID.equals(user.getROLE_ID())) {// 不是网吧老板
			if(StringUtil.isEmpty(pd.getString("store_id"))){
				List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
				pd.put("store_id", Joiner.on("','").join(storeid));
				page.setPd(pd);
			}
		}
		// 后台查看所有
		pd.put("page", page);
		JSONObject json = rechargeReportService.list(pd);// 获取网吧的充值报表信息
		System.out.println(json);
		return json;
	}
	
	@RequestMapping(value = "/listManage")
	public ModelAndView listManage(Page page,String state,String falg) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "充值报表");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {return null;} // 校验权限
		ModelAndView mv = this.getModelAndView();
		List<PageData> storeList = new ArrayList<PageData>();
		User user = this.getUser();//得到用户

		PageData pd1 = new PageData();
		PageData pd = this.getPageData();
		PageData pds = new PageData();

		if(!"admin".equals(state)){
		pd.put("internet_id", user.getINTENET_ID());
		pds.put("internet_id", user.getINTENET_ID());
		}
		pd.put("state", state);
		page.setPd(pd);
		if("admin".equals(state)){
			storeList= rechargeReportService.storeList(page);// 获取所有门店信息
		}else{
			List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
			if (!PublicManagerUtil.INTERNETROLEID.equals(user.getROLE_ID())
					&& !PublicManagerUtil.ADMINROLEID.equals(user.getROLE_ID())) {// 不是网吧老板
				if(StringUtil.isEmpty(pd.getString("STORE_ID"))){
					pd.put("STORE_IDS", Joiner.on("','").join(storeid));
					pds.put("store_id", Joiner.on("','").join(storeid));
				}
				page.setPd(pd);
				storeList = storeService.listUU(page);// 门店信息列表
			}else{
				storeList = rechargeReportService.storeList(page);// 获取该网吧所有门店
			}
				
		}
		if("SUM".equals(falg)){
			mv.setViewName("system/payManager/rechargeReport_count");
			mv.addObject("pds", rechargeReportService.tosum(pds));
		}else{
			mv.setViewName("system/payManager/rechargeReport_list");
		}
		mv.addObject("storeList", storeList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 前往金额统计页面
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toCountManage")
	public PageData toCountManage() throws Exception {
		User user = this.getUser();//得到用户

		logBefore(logger, Jurisdiction.getUsername() + "前往金额统计页面");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		PageData pd = this.getPageData();
		String state=pd.getString("state");
		if(!"admin".equals(state)){
			pd.put("internet_id", user.getINTENET_ID());
		}
		String endTime = pd.getString("endTime"); // 结束时间
		if (endTime != null && !"".equals(endTime)) {
			pd.put("endTime", endTime + " 24:00:00");
		}
		List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
		if (!PublicManagerUtil.INTERNETROLEID.equals(user.getROLE_ID())
				&& !PublicManagerUtil.ADMINROLEID.equals(user.getROLE_ID())) {// 不是网吧老板
			if(StringUtil.isEmpty(pd.getString("store_id"))){
				pd.put("store_id", Joiner.on("','").join(storeid));
			}
		}
		PageData pds= rechargeReportService.tosum(pd);// 获取金额统计数据
		if(pds==null){
			pds=new PageData();
			pds.put("reward_sum", 0);
			pds.put("rincipal_sum", 0);
		}
		return pds;
	}
	
	/**
	 * 导出到excel
	 * 
	 * @param --门店id
	 * @return mv--返回指定信息和视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		User user = this.getUser();//得到用户

		logBefore(logger, Jurisdiction.getUsername() + "导出充值报表到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		this.getRequest().setCharacterEncoding("UTF-8");
		pd = this.getPageData();
		String state=pd.getString("state");
		pd.put("pay_state", pd.getString("pay_state_jsp"));
		if(!"admin".equals(state)){
			pd.put("internet_id", user.getINTENET_ID());
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("门店名称");
		titles.add("计费ID");
		titles.add("会员姓名");
		titles.add("会员卡号");
		titles.add("订单号");
		titles.add("充值时间");
		titles.add("充值类型");
		titles.add("充值金额");
		titles.add("奖励金额");
		titles.add("充值状态");
		if("admin".equals(state)) {
			titles.add("商户号");
		}
		dataMap.put("titles", titles);
		String endTime = pd.getString("endTime"); // 结束时间
		if (endTime != null && !"".equals(endTime)) {
			pd.put("endTime", endTime + " 24:00:00");
		}
		List<PageData> varList = new ArrayList<PageData>();
		List<String> storeid=storeUserService.listfindstoreId(user.getUSER_ID());
		if (!PublicManagerUtil.INTERNETROLEID.equals(user.getROLE_ID())
				&& !PublicManagerUtil.ADMINROLEID.equals(user.getROLE_ID())) {// 不是网吧老板
			if(StringUtil.isEmpty(pd.getString("store_id"))){
				pd.put("store_id", Joiner.on("','").join(storeid));
			}
		}
		List<TBRecharge> lists = rechargeReportService.lists(pd);
			for (int i = 0; i < lists.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", lists.get(i).getStore_name());
				vpd.put("var2", lists.get(i).getStore_id());
				vpd.put("var3", lists.get(i).getUser_name());
				vpd.put("var4", lists.get(i).getCarded());
				vpd.put("var5", lists.get(i).getMerOrderId());
				String ctreatime=lists.get(i).getCreatetime().toString();
				vpd.put("var6", sfEnd.format(sfStart.parse(ctreatime)));
                if(StringUtil.isNotEmpty(lists.get(i).getMemo()) && lists.get(i).getMemo().contains("揽客抵用券")){
                    vpd.put("var7", lists.get(i).getMemo());
                }else{
                    vpd.put("var7", "揽客充值");
                }

				vpd.put("var8", lists.get(i).getRincipal_balance());
				vpd.put("var9", lists.get(i).getReward_balance());
				if("internet".equals(state)) {
				    if(StringUtil.isNotEmpty(lists.get(i).getMemo()) && lists.get(i).getMemo().contains("揽客抵用券")){
                        vpd.put("var10", "充值成功");
                    }else{
                        if("a".equals(lists.get(i).getPay_state())) {
                            vpd.put("var10", "充值成功");
                        }else {
                            vpd.put("var10", "充值处理中");
                        }
                    }


				}
				if("admin".equals(state)) {
                    if(StringUtil.isNotEmpty(lists.get(i).getMemo()) && lists.get(i).getMemo().contains("揽客抵用券")){
                        vpd.put("var10", "充值成功");
                    }else{
                        if ("2".equals(lists.get(i).getPay_state())) {
                            vpd.put("var10", "支付成功");
                        }else
                        if ("a".equals(lists.get(i).getPay_state())) {
                            vpd.put("var10", "充值成功");
                        }else {
                            vpd.put("var10", "充值处理中");
                        }
                    }


					vpd.put("var11", lists.get(i).getBusiness_number());
				}
				varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}
}
