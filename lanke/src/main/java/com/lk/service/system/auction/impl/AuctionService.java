package com.lk.service.system.auction.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.auction.AuctionManager;
import com.lk.util.PageData;

/**
 *  说明：积分商城商品--业务层
 *
 */
@Service("auctionService")
public class AuctionService implements AuctionManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd 积分商城商品的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AuctionMapper.save", pd);
	}
	
	/**
	 * 根据主键id删除
	 * @param pd（必填Auction_ID）
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AuctionMapper.delete", pd);
	}
	
	/**修改
	 * @param pd（必填：Auction_ID，选填：ANAME商品名称，CONTENT商品详情，STORING序号，STATE上下架状态，INTEGRAL需要积分，DELIVERY发货方式，D_STATE逻辑删除）
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AuctionMapper.edit", pd);
	}
	
	/**
	 * 分页列表（逻辑未删除的，1表示没删除 ，根据商品序号升序，创建时间降序排 ）
	 * @param page
	 * page中设置pd（pd中有关键词keywords）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AuctionMapper.datalistPage", page);
	}
	
	/**
	 * 查询列表（逻辑未删除的，1表示没删除 ，根据商品序号升序，创建时间降序排 ）
	 * @param pd（选填：internetId，STATE）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AuctionMapper.listAll", pd);
	}
	
	
	/**
	 * 通过主键id获取数据
	 * @param pd（必填：Auction_ID）
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AuctionMapper.findById", pd);
	}
	
	
	/**批量删除
	 * @param ArrayDATA_IDS（主键id数组）
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AuctionMapper.deleteAll", ArrayDATA_IDS);
	}

	
	/**
	 * 查询该网吧下的该商品名称的PageData
	 * pd（必填：INTERNET_ID网吧id，ANAME商品名称）
	 */
	public PageData findByAname(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AuctionMapper.findByAname", pd);
	}

	/**
	 * 上下架
	 * pd(必填：Auction_ID,State)
	 */
	@Override
	public void editState(PageData pd) throws Exception {
		dao.update("AuctionMapper.editState", pd);
	}


}
