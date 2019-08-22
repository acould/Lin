package com.lk.service.system.miniWeb;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

public interface MiniWebManager {

	/**新增
	 * 新增网吧微官网信息
	 * @param pd--微官网信息
	 * @return 
	 * @throws Exception
	 */
	public JSONObject save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * 通过MINIWEB_ID删除相关微官网信息
	 * @param pd--MINIWEB_ID
	 * @return 
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd)throws Exception;
	
	/**修改
	 * 通过MINIWEB_ID去修改微官网信息
	 * @param pd --MINIWEB_ID,CREATE_TIME--创建时间,url
	 * @return 
	 * @throws Exception
	 */
	public JSONObject edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 * 通过网吧id去查询微官网信息	
	 * @param page--网吧id
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	
	/**
	 * 通过id获取数据
	 * 通过网吧id去查询微官网信息	
	 * @param pd--网吧id
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByInternetId(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
