package com.lk.service.system.leaveMessage;

import java.util.List;

import com.lk.entity.Page;
import com.lk.entity.system.LeaveMessage;
import com.lk.util.PageData;

public interface LeaveMessageService {
	/**
	 * 获取留言管理列表（同时加载其图片信息），根据投诉时间倒序
	 */
	public List<LeaveMessage> getAllLm()throws Exception;
	
	/**
	 * 根据网吧id查询列表（3个月内），根据投诉时间倒序
	 */
	public List<LeaveMessage> getLmByIntId(String INTERNET_ID)throws Exception;
	
	/**
	 * 根据主键id获取留言管理（同时加载其图片信息）
	 */
	public LeaveMessage getOneByLMID(String lm_id)throws Exception;
	
	/**
	 * 根据投诉人的user_id查询，根据投诉时间倒序
	 * pd中包含LM_USERID
	 */
	public List<LeaveMessage> getlmByUid(PageData pd)throws Exception;
	
	/**
	 * 新增
	 */
	public int addLm(LeaveMessage leaveMessage)throws Exception;
	
	/**
	 * 根据主键id删除
	 */
	public int deleteLm(String lM_ID)throws Exception;
	
	/**
	 * 查询列表（导出excel用），根据投诉时间倒序
	 * pd中可有INTERNET_ID，keywords（加密后的KEYWORD），LM_STATE，STORE_ID，lastLoginStart，lastLoginEnd
	 * 分别为网吧id，关键词，筛选状态，筛选的门店id，开始时间和结束时间
	 */
	public List<LeaveMessage> getLm(PageData pg)throws Exception;
	
	/**
	 * 根据主键id修改（仅支持修改LM_STATE，LM_BACKTIME，BACKCONTENT，USER_ID）
	 * pd中必须有LM_ID，后面选填（分别为状态，回复时间，回复内容，处理人的user_id）
	 */
	public void updateLm(PageData pg)throws Exception;
	
	/**
	 * 查询分页列表（3个月内），根据投诉时间倒序
	 * page中包含pd
	 * pd中可有INTERNET_ID，keywords（加密后的KEYWORD），LM_STATE，STORE_ID，lastLoginStart，lastLoginEnd，STORE_ID2
	 * 分别为网吧id，关键词，筛选状态，筛选的门店id，开始时间和结束时间，操作用户的所在门店id（如果操作用户是门店管理员时）
	 */
	public List<PageData> list(Page page)throws Exception;


	/**
	 * 根据情况查询（1.查未读的已回复投诉；）
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findLmByCondition(PageData pd)throws Exception;

	
}
