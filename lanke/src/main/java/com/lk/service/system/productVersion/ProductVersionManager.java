package com.lk.service.system.productVersion;

import java.util.List;
import java.util.Map;

import com.lk.entity.Page;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/**
 * 产品版本
 * 
 * @author lzh
 */
public interface ProductVersionManager {

	/**
	 * 查询版本列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public JSONObject lists(Page page) throws Exception;

	/**
	 * 查询指定版本信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData showVersion(PageData pd) throws Exception;

	/**
	 * 查询指定版本详情
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> showDetail(PageData pd) throws Exception;

	/**
	 * 新增版本日志
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject editVersion(PageData pd) throws Exception;

	/**
	 * 删除版本日志
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject deleteVersion(PageData pd) throws Exception;

	/**
	 * 查询最新版本的id
	 * @throws Exception
	 */
	public PageData selectId() throws Exception;

	/**
	 * 查询所有版本日志
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectAllId() throws Exception;

}
