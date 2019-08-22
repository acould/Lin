package com.lk.service.tb.RechargeRule;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.util.PageData;

public interface RechargeRuleManager {

	/**
	 * 首页列表
	 * @return
	 * @throws Exception
	 */
	public List<PageData> indexList(PageData pd) throws Exception;


	/**
	 * 已经添加规则的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	List<PageData> addedRulesStores(PageData pd) throws Exception;

	
	/**
	 * 获取标签列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> labelList(PageData pd) throws Exception;


	/**
	 * 新增标签
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveLabel(PageData pd) throws Exception;


	/**
	 * 删除标签
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteLabel(PageData pd) throws Exception;


	/**
	 * 保存规则
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveRule(PageData pd) throws Exception;


	/**
	 * 查找门店的规则
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByStoreId(PageData pd) throws Exception;


	/**
	 * 删除门店的规则列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteRules(PageData pd) throws Exception;
	
	
	
	
}
