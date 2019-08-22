package com.lk.service.system.integralchange;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 积分变动表接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-26
 * @version
 */
public interface IntegralChangeManager{

	/**新增
	 * @param pd包含会员积分变动相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除会员积分变动记录信息
	 * @param pd包含主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param   pd包含会员积分变动相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page 包含页面传递的检索信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 包含主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

