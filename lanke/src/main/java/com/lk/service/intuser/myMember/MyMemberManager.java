package com.lk.service.intuser.myMember;

import net.sf.json.JSONObject;

import com.lk.entity.system.User;
import com.lk.util.PageData;

public interface MyMemberManager {

	/**
	 * 获取User实体类
	 * @param user_id用户id
	 * @return
	 * @throws Exception
	 */
	public User getUser(String user_id) throws Exception;
	
	/**
	 * 获取微信用户的基本信息（包含用户昵称，图片，卡号等信息）
	 * @param pd（传USER_ID）
	 * @return
	 * @throws Exception
	 */
	public PageData getWechatUserInfo(PageData pd) throws Exception;
	
	/**
	 * 更新顺网的用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData updateUserInfo(PageData pd) throws Exception;
	
	/**
	 * 获取该网吧下所有城市，以及城市里的所有的区域，以及该区域内所有的门店
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCityList(String internetId) throws Exception;
	
	
	/**
	 * 我的明细---加载我的明细
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMyStateMents(PageData pd,PageData pdBind) throws Exception;
	
	
	/**
	 * 我的上网---加载我的上网记录
	 * @param pd（internet_id）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMyUserOnLine(PageData pd) throws Exception;
	
	
	/**
	 * 我的抽奖---加载我的抽奖
	 * @param pd（STATE状态1.未兑奖；2兑奖中；3已兑奖；4已失效，userId用户id）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMySpoil(PageData pd) throws Exception;
	
	
	/**
	 * 我的抽奖---申请兑换奖品
	 * @param pd（mayId为奖表中的主键MEMBERLOTTERY_ID）
	 * @return 返回申请结果信息
	 * @throws Exception
	 */
	public JSONObject applySpoil(PageData pd) throws Exception;
	
	
	/**
	 * 我的卡券---加载我的卡券列表
	 * @param pd（STATE卡券状态，user_id用户id，internet_id用户所在网吧id）
	 * @return 返回我的卡券列表
	 * @throws Exception
	 */
	public JSONObject loadMyCard(PageData pd) throws Exception;
	
	
	/**
	 * 我的订单---加载我的订单
	 * @param pd（typeId订单状态，user_id用户id，internet_id用户所在网吧id）
	 * @return 返回我的订单列表
	 * @throws Exception
	 */
	public JSONObject loadMyOrder(PageData pd) throws Exception;
	
	
	/**
	 * 我的订单---申请提货
	 * @param pd（ORDER_ID订单主键id）
	 * @return 返回申请结果
	 * @throws Exception
	 */
	public JSONObject applyOrder(PageData pd) throws Exception;
	

	/**
	 * 我的投诉---加载我的投诉信息
	 * @param pd（user_id用户id，internet_id用户所在网吧id）
	 * @return 返回我的投诉信息
	 * @throws Exception
	 */
	public JSONObject loadMyLm(PageData pd) throws Exception;


	/**
	 * 加载我的数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMyData(PageData pd, User user) throws Exception;

	
	
	
	
	
	
}
