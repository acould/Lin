package com.lk.service.system.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.order.OrderManager;
import com.lk.util.PageData;

/**
 * 说明：订单管理--业务层
 */
@Service("orderService")
public class OrderService implements OrderManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("OrderMapper.save", pd);
	}

	/**
	 * 根据主键id删除
	 * pd中有ORDER_ID
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("OrderMapper.delete", pd);
	}

	/**
	 * 根据主键id修改状态和发送时间
	 * pd中有ORDER_ID，选填STATE和SEND_TIME
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("OrderMapper.edit", pd);
	}

	/**
	 * 分页查询列表
	 * page中设置pd
	 * pd中包含（关键词keywords，开始时间lastLoginStart，结束时间lastLoginEnd，门店id筛选STORE_ID，状态筛选STATE）
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("OrderMapper.datalistPage", page);
	}
	
	/**
	 * 微信端的订单列表，不分页
	 * page中设置pd
	 * pd中包含（分类筛选typeId）
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> wxlist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.wxdatalist", page);
	}
	

	/**
	 * 查询所有列表
	 * pd中暂无搜索条件
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OrderMapper.listAll", pd);
	}

	/**
	 * 根据主键id查询
	 * pd中有ORDER_ID
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OrderMapper.findById", pd);
	}


	/**
	 * 导出Excel
	 * pd中有（关键词keywords，开始时间lastLoginStart，结束时间lastLoginEnd，门店id筛选STORE_ID，状态筛选STATE）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> exportExcel(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OrderMapper.exportExcel",pd);
	}

	/**
	 * 根据商品id查询
	 * pd中有Auction_ID
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByAuctionId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("OrderMapper.findByAuctionId",pd);
	}

}
