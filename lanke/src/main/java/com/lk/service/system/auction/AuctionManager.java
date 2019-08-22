package com.lk.service.system.auction;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

/**
 * 积分商城商品
 * @author Administrator
 *
 */
public interface AuctionManager {

	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**逻辑删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	
	/**修改
	 * @param pd（必填：Auction_ID，选填：ANAME商品名称，CONTENT商品详情，STORING序号，STATE上下架状态，INTEGRAL需要积分，DELIVERY发货方式，D_STATE逻辑删除）
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 分页列表（逻辑未删除的，1表示没删除 ，根据商品序号升序，创建时间降序排 ）
	 * @param page
	 * page中设置pd（pd中有关键词keywords）
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 查询列表（逻辑未删除的，1表示没删除 ，根据商品序号升序，创建时间降序排 ）
	 * @param pd（选填：internetId，STATE）
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 通过主键id获取数据
	 * @param pd（必填：Auction_ID）
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS（主键id数组）
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 查询该网吧下的该商品名称的PageData
	 * pd（必填：INTERNET_ID网吧id，ANAME商品名称）
	 */
	public PageData findByAname(PageData pd)throws Exception;

	/**
	 * 上下架
	 * pd(必填：Auction_ID,State)
	 */
    void editState(PageData pd)throws Exception;

}
