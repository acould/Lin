package com.lk.service.system.sysscene;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 优惠券使用场景接口
 * 创建人：洪智鹏
 * 创建时间：2017-06-08
 * @version
 */
public interface SysSceneManager{

	/**新增
	 * @param pd优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd优惠券使用场景的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd优惠券使用场景相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page检索字段信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd优惠券使用场景的主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS优惠券使用场景相关信息的主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**根据名称判断是否重复
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**根据名称判断是否重复
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCard(PageData pd)throws Exception;
	/**通过cardid获取数据
	 * @param pdSce 场景信息
	 * @param pdCancle 审核信息
	 * @param pdUser 用户信息
	 * @throws Exception
	 */
	public JSONObject SceneCancel( PageData pdSce,PageData pd,PageData pdUser)throws Exception;
	
}

