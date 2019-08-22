package com.lk.service.system.dictionaries;

import java.util.List;

import com.lk.entity.Page;
import com.lk.entity.system.Dictionaries;
import com.lk.util.PageData;

/** 
 * 说明： 数据字典接口类
 * 创建人：洪智鹏
 * 创建时间：2016-9-10
 * @version
 */
public interface DictionariesManager{

	/**新增
	 * @param pd保存数据字典信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd包含表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd包含需要修改数据字典信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page展现字典信息包含检索
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd包含主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过编码获取数据
	 * @param pd 包含编码
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listSubDictByParentId(String parentId) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listAllDict(String parentId) throws Exception;
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFromTbs(PageData pd)throws Exception;
	
}

