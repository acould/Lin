package com.lk.service.system.storeuser;

import java.util.List;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/** 
 * 说明： 门店管理接口
 * 创建人：洪智鹏
 * 创建时间：2017-02-27
 * @version
 */
public interface StoreUserManager{

	/**
	 * 新增
	 * 将用户门店id存进关联表
	 * @param pd--门店id,用户id
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	
	
	/**删除门店下的人员
	 * @param pd
	 * @throws Exception
	 */
	public void deleteStoreById(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * 删除角色门店关联表信息
	 * @param pd--指定用户id
	 * @return 
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	/**
	 * 修改门店名称
	 * @param pd--修改后的门店信息
	 * @return 
	 * @throws Exception
	 */
	public JSONObject editStore(PageData pd)throws Exception;
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 门店批量删除
	 * 通过用户id去删除指定用户门店关联表
	 * @param pd--ArrayDATA_IDS--用户id数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 人员批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteUser(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 根据网吧id查询门店和用户的关系
	 * @param pd（必填：internetId）
	 */
	public List<PageData> listByIntenet(PageData pd)throws Exception;
	
	/**
	 * 通过用户id获取相关门店id
	 * @param pdStore(必填:用户id--USER_ID)
	 * @return 所需门店用户相关信息
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd)throws Exception;
	
	/**
	 * 查询门店下的人员 
	 * ArrayDATA_IDS（门店id的数组）
	 */
	public List<PageData> listAllSU(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 查询多家门店
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<String> listfindstoreId(String userid)throws Exception;
}

