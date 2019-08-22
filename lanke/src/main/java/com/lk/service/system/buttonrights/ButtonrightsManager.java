package com.lk.service.system.buttonrights;

import java.util.List;
import com.lk.util.PageData;

/** 
 * 说明：按钮权限 接口
 * 创建人：洪智鹏
 * 创建时间：2016-01-16
 * @version
 */
public interface ButtonrightsManager{

	/**新增 
	 * @param pd 包含按钮权限相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**通过(角色ID和按钮ID)获取数据
	 * @param pd   包含角色id和按钮id
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 包含按钮权限主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(全部)左连接按钮表,查出安全权限标识
	 * @param pd  包含角色id
	 * @throws Exception
	 */
	public List<PageData> listAllBrAndQxname(PageData pd)throws Exception;
	
}

