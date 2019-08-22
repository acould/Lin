package com.lk.service.system.internetmaterial;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 网吧素材接口
 * 创建人：洪智鹏
 * 创建时间：2017-06-05
 * @version
 */
public interface InternetMaterialManager{

	/**新增
	 * @param pd（素材表INTERNET_MATERIAL相关的信息）
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 根据主键id删除素材信息
	 * @param pd（INTERNETMATERIAL_ID主键id）
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 根据主键id修改素材信息（INTERNETMATERIAL_ID主键id）
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 *分页查询列表素材列表
	 * @param page 中设置pd（查询和筛选参数）
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 查询列表
	 * @param pd（暂无参数）
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 根据主键id查图片信息
	 * @param pd（INTERNETMATERIAL_ID）
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS（INTERNETMATERIAL_ID组成的数组）
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

    int delBySendRecordId(String sendrecord_id) throws Exception;

}

