package com.lk.service.system.payManager;

import net.sf.json.JSONObject;

import com.lk.util.PageData;

public interface PayManager {

	/**
	 * 保存开通
	 * @param pd（保存数据）
	 * @return
	 * @throws Exception
	 */
	public JSONObject savePay(PageData pd) throws Exception;
	
	
	/**
	 * 根据主键id查找
	 * @param pd（）
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

}
