package com.lk.controller.system.lottery;

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
import com.lk.service.system.lottery.LotteryManager;
import com.lk.service.system.memberlottery.MemberLotteryManager;

/**
 * 说明：网吧抽奖设置 (抽奖设置)
 * 创建人：洪智鹏 
 * 创建时间：2017-02-20
 */
@Controller
@RequestMapping(value = "/lottery")
public class LotteryController extends BaseController {
	// D_STATE(逻辑删除；1表示未删除；2表示已删除)
	public static final String D_STATE1 = "1";
	public static final String D_STATE2 = "2";
	// USING_STATE(是否启用;1表示已启用；2表示已禁用)
	public static final String USING_STATE1 = "1";
	public static final String USING_STATE2 = "2";

	String menuUrl = "lottery/list.do"; // 菜单地址(权限用)
	@Resource(name = "lotteryService")
	private LotteryManager lotteryService;
	@Resource(name = "memberlotteryService")
	private MemberLotteryManager memberlotteryService;

	/**
	 * 列表
	 * @param page 展示网吧抽奖包含页面和检索字段的信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表Lottery");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}

		User user = this.getUser();//获取用户

		pd.put("D_STATE", D_STATE1);
		pd.put("INTERNET_ID", user.getINTENET_ID());
		page.setPd(pd);
		List<PageData> varList = lotteryService.list(page); // 列出Lottery列表(通过网吧id查询抽奖设置)
		mv.setViewName("system/lottery/lottery_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 跳转到网吧抽奖设置新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/lottery/lottery_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		return mv;
	}

	/**
	 * 跳转到网吧抽奖设置修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = lotteryService.findById(pd); // 根据ID读取(通过LOTTERY_ID获取抽奖设置)
		mv.setViewName("system/lottery/lottery_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 新增或修改保存网吧抽奖设置
	 * @param pagedata 包含网吧抽奖设置信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveLottery")
	@ResponseBody
	public JSONObject saveLottery() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增或修改保存saveLottery");
		JSONObject json = new JSONObject();
        User user = this.getUser();//获取用户

		PageData pd =  this.getPageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
		pd.put("INTERNET_ID", user.getINTENET_ID());
		pd.put("CREATETIME", Tools.date2Str(new Date()));
		// 判断新增还是修改
		if (StringUtil.isNotEmpty(pd.getString("LOTTERY_ID"))) {
			// 修改，校验权限
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有修改权限，请联系管理员！");
				return json;
			}
			lotteryService.edit(pd); //通过LOTTERY_ID保存修改后的抽奖设置信息
		} else {
			// 新增
			if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您没有新增权限，请联系管理员！");
				return json;
			}
			pd.put("LOTTERY_ID", this.get32UUID()); // 主键
			pd.put("D_STATE", D_STATE1);// 逻辑删除；1表示未删除；2表示已删除
			if (StringUtil.isEmpty(pd.getString("USING_STATE"))) {
				pd.put("USING_STATE", USING_STATE2);
			}
			lotteryService.save(pd); //保存新增的抽奖设置信息
		}
		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "保存成功！");
		return json;
	}

	/**
	 * 设置启用或禁用 根据状态来设置网吧抽奖项目的有效或者无效
	 * @param pd 包含网吧抽奖设置 的相关信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/usingState")
	@ResponseBody
	public JSONObject usingState() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "设置启用或禁用usingState");
		JSONObject json = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		}
		PageData pd = this.getPageData();
		pd.put("LOTTERY_ID", this.getRequest().getParameter("LOTTERY_ID"));
		PageData pgd = lotteryService.findById(pd);
		String USING_STATE = (String) pgd.get("USING_STATE");
		// 1表示已启用；2表示已禁用
		if (USING_STATE == USING_STATE1 || USING_STATE.equals(USING_STATE1)) {
			pgd.put("USING_STATE", USING_STATE2);
			lotteryService.edit(pgd);  //通过LOTTERY_ID保存修改后的抽奖设置信息
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "禁用成功！");
		} else if (USING_STATE == USING_STATE2 || USING_STATE.equals(USING_STATE2)) {
			pgd.put("USING_STATE", USING_STATE1);
			lotteryService.edit(pgd); //通过LOTTERY_ID保存修改后的抽奖设置信息
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "启用成功！");
		}

		return json;
	}

	/**
	 *  删除 网吧抽奖项
	 * @param PageData 包含主键id
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Lottery");
		JSONObject json = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = lotteryService.findById(pd);  //根据ID读取(通过LOTTERY_ID获取抽奖设置)
		if (pd.getString("USING_STATE").equals(USING_STATE2)) {
//			List<PageData> recordList = memberlotteryService.findRecordByLottery(pd); //根据奖品id获取该奖品的兑换记录
//			for (PageData pdRecord : recordList) {
//				if (!pdRecord.getString("STATE").equals("3")) {// 没有兑换成功的，又没有失效的，无法删除
//
//					Date lottery_time = Tools.str2Date(pdRecord.getString("LOTTERY_TIME"));
//					int expity_date = Integer.parseInt(pdRecord.getString("EXPIRY_DATE"));
//					Date available_time = Tools.sAddDays(lottery_time, expity_date);
//					if (new Date().getTime() < available_time.getTime()) {
//						// 当前时间 < 中奖时间+有效期 :没有失效
//						json.put("result", PublicManagerUtil.FALSE);
//						json.put("message", "该奖品在有效期内有会员尚未兑换，无法删除！");
//						return json;
//					}
//				}
//			}

            //现全部设为可逻辑删除
			pd.put("D_STATE", D_STATE2);
			// 逻辑删除；1表示未删除；2表示已删除
			lotteryService.edit(pd); //通过LOTTERY_ID保存修改后的抽奖设置信息
		} else {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "启用的奖品不能删除！");
		}

		json.put("result", PublicManagerUtil.TRUE);
		json.put("message", "删除成功！");
		return json;
	}

	/**
	 * 批量删除网吧抽奖项
	 * @param DATA_IDS 主键组成的数组
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public JSONObject deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "批量删除Lottery");
		JSONObject json = new JSONObject();
		// 校验权限
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "您没有删除权限，请联系管理员！");
			return json;
		}

		PageData pd = new PageData();
		pd = this.getPageData();
		if (StringUtil.isNotEmpty(pd.getString("DATA_IDS"))) {
			String DATA_IDS = pd.getString("DATA_IDS");
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData pdIsJin = new PageData();
				pdIsJin.put("LOTTERY_ID", ArrayDATA_IDS[i]);
				pdIsJin = lotteryService.findById(pdIsJin); //根据ID读取(通过LOTTERY_ID获取抽奖设置)
				if (pdIsJin.getString("USING_STATE").equals(USING_STATE1)) {
					json.put("result", PublicManagerUtil.FALSE);
					json.put("message", pdIsJin.getString("LOTTERY_NAME") + "奖品已经启用，不能删除！");
					return json;
				}

//				List<PageData> recordList = memberlotteryService.findRecordByLottery(pdIsJin); //根据奖品id获取该奖品的兑换记录
//				for (PageData pdRecord : recordList) {
//					if (!pdRecord.getString("STATE").equals("3")) {
//						// 没有兑换成功的，又没有失效的，无法删除
//						Date lottery_time = Tools.str2Date(pdRecord.getString("LOTTERY_TIME"));
//						int expity_date = Integer.parseInt(pdRecord.getString("EXPIRY_DATE"));
//						Date available_time = Tools.sAddDays(lottery_time, expity_date);
//						if (new Date().getTime() < available_time.getTime()) {
//							// 当前时间 < 中奖时间+有效期 :没有失效
//							json.put("result", PublicManagerUtil.FALSE);
//							json.put("message", "" + pdRecord.getString("LOTTERY_NAME") + "商品在有效期内有会员尚未兑换，无法删除！");
//							return json;
//						}
//					}
//				}
			}
			for (int i = 0; i < ArrayDATA_IDS.length; i++) {
				PageData pdIsJin = new PageData();
				pdIsJin.put("LOTTERY_ID", ArrayDATA_IDS[i]);
				// 逻辑删除；1表示未删除；2表示已删除
				pdIsJin.put("D_STATE", D_STATE2);
				lotteryService.edit(pdIsJin); //通过LOTTERY_ID保存修改后的抽奖设置信息
			}
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "删除成功！");
		} else {
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "请选择需要删除的选项！");
			return json;
		}
		return json;
	}

	/**
	 * 导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "导出Lottery到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("网吧id");  // 1
		titles.add("转盘名称"); // 2
		titles.add("是否中奖"); // 3
		titles.add("中奖类型"); // 4
		titles.add("中奖多少"); // 5
		dataMap.put("titles", titles);
		List<PageData> varOList = lotteryService.listAll(pd); //查询全部抽奖设置
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("INTERNET_ID")); // 1
			vpd.put("var2", varOList.get(i).getString("LOTTERY_NAME")); // 2
			vpd.put("var3", varOList.get(i).getString("LOTTERY_TYPE")); // 3
			vpd.put("var4", varOList.get(i).getString("LOTTERY_SET")); // 4
			vpd.put("var5", varOList.get(i).get("LOTTERY_NUMBER").toString()); // 5
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
