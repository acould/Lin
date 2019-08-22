package com.lk.service.internet.groupstore;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 组局和分店关系表接口
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
public interface GroupStoreManager{

	/**新增 组局和分店关系表
	 * @param pd组局和分店关系表相关数据
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除 组局和分店关系表
	 * @param pd 包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd包含组局和分店关系表相关数据
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page包含检索字段
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过GROUP_ID获取数据
	 * @param pd 组局主键来
	 * @throws Exception
	 */
	public List<PageData> listStore(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS组局和分店关系表主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	/**通过GROUP_ID删除关系表
	 * @param pd 组局主键来
	 * @throws Exception
	 */
	public void deleteByGroupId(PageData pd)throws Exception;
	
}

