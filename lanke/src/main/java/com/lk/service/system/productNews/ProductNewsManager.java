package com.lk.service.system.productNews;

import com.lk.util.PageData;
import net.sf.json.JSONObject;

/**
 * 产品动态接口
 * @author lzh
 */
public interface ProductNewsManager {

	/**
	 * 查询版本预告信息
	 * @throws Exception
	 */
	public PageData list() throws Exception;
 
	/**
	 * 编辑版本预告信息
	 * @param pd 
	 * @throws Exception
	 */
	public JSONObject edit(PageData pd) throws Exception;

	/**
	 * 删除版本预告信息
	 * @throws Exception
	 */
	public JSONObject deleteNews() throws Exception;
	
}
