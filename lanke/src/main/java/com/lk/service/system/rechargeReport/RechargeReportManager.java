package com.lk.service.system.rechargeReport;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.entity.unionPay.TBRecharge;
import com.lk.util.PageData;

/**
 * 充值报表
 * 
 * @author lzh
 */
public interface RechargeReportManager {

	/**
	 * 获取网吧的充值报表信息
	 * 
	 * @param page
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject list(PageData pd) throws Exception;

	/**
	 * 获取网吧所有门店信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> storeList(Page page) throws Exception;

	/**
	 * 获取金额统计数据
	 * 
	 * @param page
	 * @param pd 
	 * @return
	 * @throws Exception
	 */
	public PageData tosum(PageData pd) throws Exception;

	/**
	 * 导出
	 * @param page
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<TBRecharge> lists(PageData pd) throws Exception;
}
