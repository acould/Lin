package com.lk.service.system.intenet;

import java.util.List;


import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.util.PageData;

/** 
 * 说明： 网吧接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-20
 * @version
 */
public interface IntenetManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**通过公众号帐号基本信息修改
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception;
	
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
	
	/**
	 * 通过id获取数据
	 * 通过网吧id获取网吧信息
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * 通过网吧id获取网吧信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findById1(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 通过网吧id获取网吧信息
	 * @param orgId (必填:网吧id--INTENET_ID)
	 * @return 指定网吧信息
	 * @throws Exception
	 */
	public Intenet getIntenetById(String orgId)throws Exception;
	
	/**默认显示网吧信息列表admin(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> IntenetlistPage(Page page)throws Exception;

	/**
	 * 解除授权时修改sys_intenet状态
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	public void updateState(PageData pd) throws Exception;
	
	public Intenet getIntenetByWeiXinId(String angetId)throws Exception ;

	/**
	 * 通过网吧id获取揽客绑定手机号
	 * @param pd
	 * @throws Exception
	 */
	public PageData getPhone(String INTENET_ID) throws Exception ;

	/**
	 * 通过微信id获取揽客绑定手机号
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByappid(PageData pd) throws Exception ;

	/**
	 * 通过USERNAME获取揽客绑定手机号
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByUserName(PageData pd) throws Exception ;


	/**
	 * 根据情况筛选
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByState(PageData pd) throws Exception ;

    PageData findByUserIdAndInternetId(PageData pd) throws Exception;

    PageData findByInternetId(String intenet_id, boolean isGetHeadImg) throws Exception;
}

