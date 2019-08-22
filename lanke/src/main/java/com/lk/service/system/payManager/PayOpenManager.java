package com.lk.service.system.payManager;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.lk.util.PageData;

public interface PayOpenManager {

	/**
	 * 保存开通
	 * @param pd（保存数据）
	 * @return
	 * @throws Exception
	 */
	public JSONObject savePayOpen(PageData pd) throws Exception;
	
	
	/**
	 * 根据主键id查找
	 * @param pd（）
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;
	
	
	/**
	 * 开通的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> payOpenList(PageData pd) throws Exception;


	/**
	 * 保存审核情况
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveReview(PageData pd) throws Exception;


	/**
	 * 获取日志
	 * @param pd（intenetdatum_id，state）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByInternetDatumId(PageData pd) throws Exception;


	/**
	 * 保存快递单号
	 * @param pd（user，快递信息）
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveExpress(PageData pd) throws Exception;


	/**
	 * 开通在线支付
	 * @param pd（主键id，商户号）
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveOpen(PageData pd) throws Exception;


	/**
	 * 导出Excel
	 * @param pd（搜索条件）
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportExcel(PageData pd) throws Exception;
	
	
}
