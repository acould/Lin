package com.lk.service.system.wechatuser;

import java.util.List;

import com.lk.entity.LayMessage;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.wechat.response.WechatUser;

/** 
 * 说明： 微信用户接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-31
 * @version
 */
public interface WeChatUserManager{

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
	
	/**列表
	 * 通过网吧id和门店id去查询会员信息
	 * @param page 网吧id--intenetId,门店id--storeId
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
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 根据open_id查询微信用户信息
	 * @param wechatUser（必填OPEN_ID）
	 */
	public PageData findByOpenId(PageData wechatUser)throws Exception;

	/**
	 * 根据open_id查询微信用户信息
	 * @param openid
	 */
	public WechatUser findByOpenId(String openid) throws Exception;

	/**
	 * 通过用户id获取微信用户的open_id
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUserId(PageData wechatUser)throws Exception;

	/**
	 * 通过新用户openid查找推荐用户获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByOldwithId(PageData person)throws Exception;
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getAppUserCount(PageData pd)throws Exception;

	/**
	 * （当pd中的area=province时，统计获取所有微信用户的省份列表；area=city时，统计获取所有微信用户的城市列表）
	 * @param pd（area，INTERNET_ID）
	 */
	public List<PageData> findArea(PageData pd)throws Exception;
	
	/**
	 * 发送图文时，根据群发设置查找范围内的微信用户列表
	 * @param pd（必填：INTERNET_ID，选填：GROUP_ID是否会员设置，GROUP_SEX性别设置，GROUP_PROVINCE所在省份设置）
	 */
	public List<PageData> findBySend(PageData pd)throws Exception;


	public List<PageData> findByCondition(PageData pd)throws Exception;


    LayMessage getWechatUserList(PageData pd, Page page) throws Exception;

    List<PageData> findByStoreIds(PageData pd) throws Exception;
}

