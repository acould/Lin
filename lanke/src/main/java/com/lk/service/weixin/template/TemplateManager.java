package com.lk.service.weixin.template;

import java.util.List;

import com.lk.util.PageData;

/**
 * 说明：模板消息 --业务层
 */
public interface TemplateManager {

	/**
	 * 新增
	 * 保存新的消息模板
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 根据主键id查询模板
	 * @param pd（必填：internet_id，选填：type）
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 根据网吧id查询模板列表
	 * @param pd（必填：internet_id）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByInternetId(PageData pd)throws Exception;
}
