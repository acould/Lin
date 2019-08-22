package com.lk.service.system.dictenty;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 字典内容表接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-12
 * @version
 */
public interface DictEntyManager{

	/**新增
	 * @param pd 包含字典新增信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/** 根据主键删除字典
	 * @param pd 包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd 包含修改字段信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page 展示字典列表
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列表(全部)
	 * 通过优惠劵类型查询信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 字典主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/** 批量删除字典信息
	 * @param ArrayDATA_IDS 包含字典主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	/** 根据类型查询字典包含的信息
	 * @param PageData  字典码和类型
	 * @throws Exception
	 */
	public PageData findBySc(PageData pdSce)throws Exception;
	
}

