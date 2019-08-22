package com.lk.service.system.myop;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 开放平台参数管理接口
 * 创建人：洪智鹏
 * 创建时间：2017-08-01
 * @version
 */
public interface MyopManager{

	/**新增
	 * @param pd含开放平台参数管理相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd包含开放平台参数管理相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page 包含页面相关信息和检索信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd包含开放平台参数管理主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS包含多个开放平台参数管理主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 通过缓存或者数据库读取数据COMPONENT_ACCESS_TOKEN
	 * @param pdmyop包含APPID和时间TOKEN_TIME（必须为date格式yyyy-MM-dd HH:mm:ss）,COMPONENT_VERIFY_TICKET(选填)
	 * @throws Exception
	 */
	public PageData findByAppId(PageData pdmyop)throws Exception;
	
}

