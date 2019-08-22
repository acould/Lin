package com.lk.service.weixin.template.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.service.weixin.template.TemplateManager;
import com.lk.util.PageData;

@Service
public class TemplateService implements TemplateManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 保存新的消息模板
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TemplateMapper.save", pd);
	}
	
	/**
	 * 根据主键id查询
	 * @param pd（必填：internet_id，选填：type）
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TemplateMapper.findById", pd);
	}

	/**
	 * 根据网吧id查询模板列表
	 * @param pd（必填：internet_id）
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByInternetId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TemplateMapper.findByInternetId", pd);
	}
	
	
}
