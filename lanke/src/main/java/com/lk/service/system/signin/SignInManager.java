package com.lk.service.system.signin;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 签到表接口
 * 创建人：洪智鹏
 * 创建时间：2016-12-14
 * @version
 */
public interface SignInManager{

	/**新增
	 * @param pd会员签到相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd会员签到相关信息的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd会员签到相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page签到列表包含页面和检索字段信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无，查询全部信息
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 会员签到主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS会员签到主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	public PageData findByUser(PageData pd) throws Exception;
	
}

