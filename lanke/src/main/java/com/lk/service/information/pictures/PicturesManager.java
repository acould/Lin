package com.lk.service.information.pictures;

import java.util.List;
import com.lk.entity.Page;
import com.lk.entity.system.Pictures;
import com.lk.util.PageData;


/** 图片管理接口
 * 修改时间：2015.11.2
 */
public interface PicturesManager {
	
	/**列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
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
	

	/**
	 * 根据LM_ID删除图片
	 * @param LM_ID（投诉id）
	 */
	public void deleteByLmID(String ID)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**批量获取
	 * @param ArrayDATA_IDS
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception;
	
	/**删除图片
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd)throws Exception;

	/**
	 * 根据网吧id查询图片列表
	 * @param pd（传入INTERNETID网吧id）
	 */
	public List<PageData> findByIntenet(PageData pd)throws Exception;
	
	/**
	 * 根据网吧id查询，且MATERIAL_ID不为null的列表
	 * @param pd（传入INTERNETID网吧id）
	 */
	public List<PageData> findByIntenetMetrial(PageData pd)throws Exception;
	
	/**
	 * 根据MATERIAL_ID查询图片
	 * @param pd（MATERIAL_ID素材id）
	 */
	public PageData findByMetrial(PageData pd)throws Exception;

	/**
	 * 根据LM_ID投诉id查询图片列表
	 * @param LM_ID（投诉id）
	 */
	public List<Pictures> findByLMId(String LM_ID)throws Exception;
	
	/**
	 * 根据Intenet_id查询图片列表
	 * @param pd（传入Intenet_id网吧id）
	 */
	public PageData findByIntenet_id(PageData pd)throws Exception;
}

