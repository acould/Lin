package com.lk.service.information.pictures.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.Pictures;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.util.PageData;


/** 图片管理
 * 修改时间：2015.11.2
 */
@Service("picturesService")
public class PicturesService implements PicturesManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PicturesMapper.datalistPage", page);
	}
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PicturesMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PicturesMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PicturesMapper.edit", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PicturesMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PicturesMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**批量获取
	 * @param ArrayDATA_IDS
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("PicturesMapper.getAllById", ArrayDATA_IDS);
	}
	
	/**删除图片
	 * @param pd
	 * @throws Exception
	 */
	public void delTp(PageData pd)throws Exception{
		dao.update("PicturesMapper.delTp", pd);
	}

	/**
	 * 根据网吧id查询图片列表
	 * @param pd（传入INTERNETID网吧id）
	 */
	public List<PageData> findByIntenet(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PicturesMapper.findByIntenet", pd);
	}
	
	/**
	 * 根据网吧id查询，且MATERIAL_ID不为null的列表
	 * @param pd（传入INTERNETID网吧id）
	 */
	public List<PageData> findByIntenetMetrial(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PicturesMapper.findByIntenetMetrial", pd);
	}
	
	/**
	 * 根据LM_ID查询图片列表
	 * @param LM_ID（投诉id）
	 */
	public List<Pictures> findByLMId(String LM_ID) throws Exception {
		return (List<Pictures>) dao.findForList("PicturesMapper.findByLMId", LM_ID);
	}

	/**
	 * 根据LM_ID删除图片
	 * @param LM_ID（投诉id）
	 */
	public void deleteByLmID(String ID) throws Exception {
		dao.delete("PicturesMapper.deleteByLmID", ID);
	}

	/**
	 * 根据Intenet_id查询图片列表
	 * @param pd（传入Intenet_id网吧id）
	 */
	public PageData findByIntenet_id(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PicturesMapper.findByIntenet_id", pd);
	}

	/**
	 * 根据MATERIAL_ID查询图片
	 * @param pd（MATERIAL_ID素材id）
	 */
	public PageData findByMetrial(PageData pd) throws Exception {
		return (PageData)dao.findForObject("PicturesMapper.findByMetrial", pd);
	}
	
}

