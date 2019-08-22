package com.lk.service.system.rechargeReport.impl;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.unionPay.TBRecharge;
import com.lk.service.system.rechargeReport.RechargeReportManager;
import com.lk.util.PageData;
import com.lk.util.StringUtil;

/**
 * 说明：充值报表--业务层
 */
@Service("rechargeReportService")
public class RechargeReportService implements RechargeReportManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取网吧的充值报表信息
	 * 
	 * @param page
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject list(PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		JSONObject result = new JSONObject();
		
		Page pa = (Page) pd.get("page");
		
		int currentPage = 1;
		int showCount = 10;
		if(!StringUtil.isEmpty(pd.get("currentPage"))){
			currentPage = Integer.parseInt(pd.get("currentPage").toString());
		}
		if(!StringUtil.isEmpty(pd.get("showCount"))){
			showCount = Integer.parseInt(pd.get("showCount").toString());
		}
		pa.setShowCount(showCount);
		pa.setCurrentPage(currentPage);
		
		pa.setPd(pd);
		List<PageData> list = (List<PageData>) dao.findForList("RechargeReportMapper.datalistPage", pa);
		for(PageData pdd : list){
			if(StringUtil.isNotEmpty(pdd.get("createtime"))){
				pdd.put("createtime", pdd.get("createtime").toString());
				pdd.put("user_name", URLDecoder.decode(pdd.getString("user_name"), "utf-8"));
				pdd.put("store_name", pdd.getString("storename"));
			}
		}
		int count = (int) dao.findForObject("RechargeReportMapper.totalNum", pa);
		result.put("data", list);
		result.put("count", count);
		result.put("msg", "查询成功");
		result.put("showCount", showCount);
		result.put("code", "0");
		return result;
	}

	/**
	 * 导出excel
	 * @param page
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TBRecharge> lists(PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		List<TBRecharge> list = (List<TBRecharge>) dao.findForList("RechargeReportMapper.datalist", pd);
		for(TBRecharge pdd : list){
			if(StringUtil.isNotEmpty(pdd.getStore_name())){
				pdd.setUser_name(URLDecoder.decode(pdd.getUser_name(), "utf-8"));
				pdd.setStore_name(pdd.getStorename());
			}
		}
		return list;
	}
	
	/**
	 * 获取网吧所有门店信息
	 * 
	 * @param page
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> storeList(Page page) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("RechargeReportMapper.storeList", page);
		return list;
	}

	/**
	 * 获取金额统计数据
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData tosum(PageData pd) throws Exception {
		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		PageData tosum = (PageData) dao.findForObject("RechargeReportMapper.tosum", pd);
		return tosum;
	}
}
