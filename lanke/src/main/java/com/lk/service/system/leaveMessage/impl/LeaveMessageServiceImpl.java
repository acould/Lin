package com.lk.service.system.leaveMessage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.LeaveMessage;
import com.lk.entity.system.Pictures;
import com.lk.service.system.leaveMessage.LeaveMessageService;
import com.lk.util.PageData;

/**
 * 留言管理---业务层
 * @author myq
 *
 */
@Service("LeaveMessageService")
public class LeaveMessageServiceImpl implements LeaveMessageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	/**
	 * 获取留言管理列表（同时加载其图片信息），根据投诉时间倒序
	 */
	@SuppressWarnings("unchecked")
	public List<LeaveMessage> getAllLm() throws Exception {
		List<LeaveMessage> lealst = (List<LeaveMessage>) dao.findForList("LeaveMessageMapper.getAllLm", null); 
		for(LeaveMessage le : lealst){
			le.setPlist((List<Pictures>)dao.findForList("PicturesMapper.findByLMId", le.getLM_ID()));
		}
		return lealst;
	}

	/**
	 * 新增
	 */
	public int addLm(LeaveMessage leaveMessage) throws Exception {
		return (Integer) dao.save("LeaveMessageMapper.addLm", leaveMessage);
	}

	/**
	 * 根据主键id删除
	 */
	public int deleteLm(String id) throws Exception {
		return (Integer) dao.delete("LeaveMessageMapper.deleteLm", id);
	}

	/**
	 * 根据主键id获取留言管理（同时加载其图片信息）
	 */
	public LeaveMessage getOneByLMID(String lm_id) throws Exception {
		LeaveMessage leas = (LeaveMessage) dao.findForObject("LeaveMessageMapper.getOneByLMID", lm_id);
		@SuppressWarnings("unchecked")
		List<Pictures> picList = (List<Pictures>)dao.findForList("PicturesMapper.findByLMId", leas.getLM_ID());
		if(picList.size() > 0){
			leas.setPlist(picList);
		}
		return leas;
	}

	/**
	 * 根据网吧id查询列表（3个月内），根据投诉时间倒序
	 */
	@SuppressWarnings("unchecked")
	public List<LeaveMessage> getLmByIntId(String INTERNET_ID) throws Exception {
		return (List<LeaveMessage>) dao.findForList("LeaveMessageMapper.getLmByIntId", INTERNET_ID);
	}

	/**
	 * 查询列表（导出excel用），根据投诉时间倒序
	 * pd中可有INTERNET_ID，keywords（加密后的KEYWORD），LM_STATE，STORE_ID，lastLoginStart，lastLoginEnd
	 * 分别为网吧id，关键词，筛选状态，筛选的门店id，开始时间和结束时间
	 */
	@SuppressWarnings("unchecked")
	public List<LeaveMessage> getLm(PageData pd) throws Exception {
		return (List<LeaveMessage>) dao.findForList("LeaveMessageMapper.getLm", pd);
	}

	/**
	 * 根据主键id修改（仅支持修改LM_STATE，LM_BACKTIME，BACKCONTENT，USER_ID）
	 * pd中必须有LM_ID，后面选填（分别为状态，回复时间，回复内容，处理人的user_id）
	 */
	public void updateLm(PageData pg) throws Exception {
		dao.update("LeaveMessageMapper.updateLm", pg);
	}

	/**
	 * 根据投诉人的user_id查询，根据投诉时间倒序
	 * pd中包含LM_USERID
	 */
	@SuppressWarnings("unchecked")
	public List<LeaveMessage> getlmByUid(PageData pg) throws Exception {
		return (List<LeaveMessage>) dao.findForList("LeaveMessageMapper.getlmByUid", pg);
	}

	/**
	 * 查询分页列表（3个月内），根据投诉时间倒序
	 * page中包含pd
	 * pd中可有INTERNET_ID，keywords（加密后的KEYWORD），LM_STATE，STORE_ID，lastLoginStart，lastLoginEnd，STORE_ID2
	 * 分别为网吧id，关键词，筛选状态，筛选的门店id，开始时间和结束时间，操作用户的所在门店id（如果操作用户是门店管理员时）
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("LeaveMessageMapper.datalistPage", page);
	}

	@Override
	public List<PageData> findLmByCondition(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("LeaveMessageMapper.findLmByCondition", pd);
	}
}
