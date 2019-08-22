package com.lk.service.system.welcome.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.welcome.WelcomeManager;
import com.lk.util.PageData;

/**
 * 说明： 网吧邀请好友设置 创建人：白弋冉 创建时间：2017-07-04
 * 
 * @version
 */
@Service("welcomeService")
public class WelcomeService implements WelcomeManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("WelcomeMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("WelcomeMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("WelcomeMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WelcomeMapper.datalistPage",
				page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WelcomeMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WelcomeMapper.findById", pd);
	}

	/**
	 * 通过网吧id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByInternetId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WelcomeMapper.findByInternetId", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("WelcomeMapper.deleteAll", ArrayDATA_IDS);
	}


}
