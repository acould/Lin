package com.lk.service.system.internetmaterial.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.internetmaterial.InternetMaterialManager;

/** 
 * 说明： 网吧素材
 * 创建人：洪智鹏
 * 创建时间：2017-06-05
 * @version
 */
@Service("internetmaterialService")
public class InternetMaterialService implements InternetMaterialManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd（素材表INTERNET_MATERIAL相关的信息）
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("InternetMaterialMapper.save", pd);
	}
	
	/**
	 * 根据主键id删除素材信息
	 * @param pd（INTERNETMATERIAL_ID主键id）
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("InternetMaterialMapper.delete", pd);
	}
	
	/**
	 * 根据主键id修改素材信息（INTERNETMATERIAL_ID主键id）
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("InternetMaterialMapper.edit", pd);
	}
	
	/**
	 *分页查询列表素材列表
	 * @param page中设置pd（查询和筛选参数）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("InternetMaterialMapper.datalistPage", page);
	}
	
	/**
	 * 查询列表
	 * @param pd（暂无参数）
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("InternetMaterialMapper.listAll", pd);
	}
	
	/**
	 * 根据主键id查图片信息
	 * @param pd（INTERNETMATERIAL_ID）
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("InternetMaterialMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS（INTERNETMATERIAL_ID组成的数组）
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("InternetMaterialMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public int delBySendRecordId(String sendrecord_id) throws Exception {
		return (int) dao.delete("InternetMaterialMapper.delBySendRecordId", sendrecord_id);
	}
}

