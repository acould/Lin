package com.lk.service.internet.group;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 组局功能接口
 * 创建人：洪智鹏
 * 创建时间：2017-10-19
 * @version
 */
public interface GroupManager{

	/**新增 组局功能接
	 * @param pd里面包含组局相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除 组局功能接
	 * @param pd组局表主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd里面包含比赛相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page检索字段等信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd无信息查询全部
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * group or store列表
	 * @param pd 网吧id
	 * @throws Exception
	 */
	public List<PageData> listGroupAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pdd组局表主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 通过GROUP-id获取数据
	 * @param pd组局表主键
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listByGroupId(PageData pd)throws Exception;
	
	/**
	 * 通过获取组局功能选择的分店(改组局功能能在哪些分店开组局)
	 * @param storeList store网吧分店id主键数组
	 * @return
	 * @throws Exception
	 */
	public List<PageData> storeList(String[] storeList)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 保存service
	 * @param pd里面包含组局相关信息
	 * @return
	 * @throws Exception
	 */
	public PageData groupSave(PageData pd)throws Exception;
	
	/**
	 * 修改service
	 * @param pagedata里面包含组局相关信息
	 * @return
	 * @throws Exception
	 */
	public PageData groupEdit(PageData pd)throws Exception;
	
}

