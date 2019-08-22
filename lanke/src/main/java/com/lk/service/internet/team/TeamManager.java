package com.lk.service.internet.team;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： Team接口
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
public interface TeamManager{

	/**新增
	 * @param pd组局功能的组队参加相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd组局功能的组队表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**GROUP_ID 删除
	 * @param pd 组局功能主键
	 * @throws Exception
	 */
	public void deleteByGroupId(PageData pd)throws Exception;
	
	/**修改
	 * @param pd组局功能的组队参加相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page页面检索信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(GroupidTeam)
	 * @param pd组局功能主键
	 * @throws Exception
	 */
	public List<PageData> listGroupidTeam(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd组局当中组队主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS组局当中组队主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

