package com.lk.service.system.auctionpictures;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

public interface AuctionPicturesManager {

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
	 * @param page
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
	 * 根据Auction_ID查找图片
	 * pd（Auction_ID）
	 */
	public PageData findByAuctionId(String auction_id)throws Exception;

	/**
	 * 通过商品id更换其图片和商品信息
	 * @param pdImg
	 * @throws Exception
	 */
	public void editByAuctionId(PageData pdImg)throws Exception;

	List<PageData> getStoreList(String intenet_id) throws Exception;

}
