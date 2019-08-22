package com.lk.service.system.cardStore;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 卡卷管理接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-31
 * @version
 */
public interface CardStoreManager{

	/**
	 * 新增
	 * 新建卡劵(更换门店)
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * 通过卡劵id删除卡劵(更换门店)
	 * @param pd
	 * @throws Exception
	 */
	public void deleteByCardId(PageData pd)throws Exception;
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
	 * 通过卡劵号和门店id获取信息
	 * @param  pdCard
	 * @throws Exception
	 */
	public List<PageData> findByCardId(PageData pdCard)throws Exception;

	/**
	 * 查询卡券和门店的关系
	 * @param pd（必填：CARD_ID卡券id，STORE_ID门店id）
	 */
	public PageData findByCardIdAndStoreId(PageData pdCard)throws Exception;
	
	
}

