package com.lk.service.system.cardStore.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.cardStore.CardStoreManager;
import com.lk.util.PageData;

/** 
 * 说明： 卡卷管理
 * 创建人：洪智鹏
 * 创建时间：2016-10-31
 * @version
 */
@Service("cardStoreService")
public class CardStoreService implements CardStoreManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 新建卡劵(更换门店)
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CardStoreMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CardStoreMapper.delete", pd);
	}
	
	/**
	 * 删除
	 * 通过卡劵id删除卡劵(更换门店)
	 * @param pd
	 * @throws Exception
	 */
	public void deleteByCardId(PageData pd)throws Exception{
		dao.delete("CardStoreMapper.deleteByCardId", pd);
	}
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CardStoreMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CardStoreMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CardStoreMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CardStoreMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CardStoreMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 通过卡劵号和门店id获取信息
	 * @param  pdCard
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByCardId(PageData pdCard) throws Exception {
		return (List<PageData>)dao.findForList("CardStoreMapper.findByCardId", pdCard);
	}

	/**
	 * 查询卡券和门店的关系
	 * @param pd（必填：CARD_ID卡券id，STORE_ID门店id）
	 */
	public PageData findByCardIdAndStoreId(PageData pdCard) throws Exception {
		return (PageData)dao.findForObject("CardStoreMapper.findByCardIdAndStoreId", pdCard);
	}

}

