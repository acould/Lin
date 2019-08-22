package com.lk.service.system.order;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

/**
 * 订单管理
 * @author Administrator
 *
 */
public interface OrderManager {
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData page)throws Exception;
	
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
	
	public List<PageData> wxlist(Page page)throws Exception;
	
	/**导出Excel(修改导出时只有一页问题)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> exportExcel(PageData pd)throws Exception;
	
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
	

	/**
	 * 查找该商品的订单记录
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByAuctionId(PageData pd)throws Exception;
	
}
