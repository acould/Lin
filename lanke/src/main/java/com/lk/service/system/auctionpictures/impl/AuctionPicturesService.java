package com.lk.service.system.auctionpictures.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.auctionpictures.AuctionPicturesManager;
import com.lk.util.PageData;

@Service("auctionPicturesService")
public class AuctionPicturesService implements AuctionPicturesManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AuctionPicturesMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AuctionPicturesMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AuctionPicturesMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AuctionPicturesMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AuctionPicturesMapper.listAll", pd);
	}
	/**查询本网吧设置积分规则
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listIntenet(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AuctionPicturesMapper.listIntenet", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AuctionPicturesMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AuctionPicturesMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据Auction_ID查找图片
	 * pd（Auction_ID）
	 */
	public PageData findByAuctionId(String auction_id) throws Exception {
		return (PageData) dao.findForObject("AuctionPicturesMapper.findByAuctionId", auction_id);
	}

	/**
	 * 通过商品id更换其图片和商品信息
	 * @param pdImg
	 * @throws Exception
	 */
	public void editByAuctionId(PageData pdImg) throws Exception {
		dao.update("AuctionPicturesMapper.editByAuctionId", pdImg);
	}

	@Override
	public List<PageData> getStoreList(String intenet_id) throws Exception {
		Map map = new HashMap();
		map.put("INTERNET_ID",intenet_id);
		List<PageData> list = (List<PageData>) dao.findForList( "AuctionPicturesMapper.getStoreList",map);
		return list;
	}


}
