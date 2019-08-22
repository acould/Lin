package com.lk.service.system.wechat;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 获取微信通讯口令接口
 * 创建人：洪智鹏
 * 创建时间：2017-03-22
 * @version
 */
public interface WechatManager{

	/**新增
	 * @param pd包含微信通讯口令需要的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 包含微信通讯口令的主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd包含微信通讯口令需要的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page检索字段和页面部分信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无，查询全部
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd 包含网吧id
	 * @throws Exception
	 */
	public PageData findByInternetId(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS包含微信通讯口令主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	
}

