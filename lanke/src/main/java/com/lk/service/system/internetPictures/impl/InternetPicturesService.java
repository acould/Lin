package com.lk.service.system.internetPictures.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.util.PageData;


@Service("internetPicturesService")
public class InternetPicturesService implements InternetPicturesManager{

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 分页列表
	 * @param page中设置pd（pd查询筛选的相关信息）
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InternetPicturesMapper.datalistPage", page);
	}
	
	/**新增保存图片信息
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InternetPicturesMapper.save", pd);
	}
	
	/**根据主键id删除图片
	 * @param pd（PICTURE_ID）
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InternetPicturesMapper.delete", pd);
	}
	
	/**
	 * 根据主键id修改图片相关信息
	 * @param pd（PICTURE_ID）
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InternetPicturesMapper.edit", pd);
	}
	
	/**通过主键id获取数据
	 * @param pd（PICTURE_ID）
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InternetPicturesMapper.findById", pd);
	}
	
	
	/**
	 * 通过INTERNET_ID获取图片列表，且MEDIA_ID不能为空
	 * @param pd（INTERNET_ID）
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByInternetId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InternetPicturesMapper.findByInternetId", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS（PICTURE_ID组成的数组）
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InternetPicturesMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据MEDIA_ID查找图片
	 * @param pd（必填：MEDIA_ID）
	 */
	public PageData findByMediaId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InternetPicturesMapper.findByMediaId", pd);
	}


	
	
	
	
}
