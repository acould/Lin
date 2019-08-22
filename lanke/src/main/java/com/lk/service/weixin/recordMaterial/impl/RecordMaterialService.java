package com.lk.service.weixin.recordMaterial.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.util.PageData;



@SuppressWarnings("unchecked")
@Service("recordMaterialService")
public class RecordMaterialService implements RecordMaterialManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("RecordMaterialMapper.save", pd);
	}

	/**
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("RecordMaterialMapper.delete", pd);
	}

	/**
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("RecordMaterialMapper.edit", pd);
	}

	
	/**
	 * 查询dataListPage
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("RecordMaterialMapper.datalistPage", page);
	}

	/**
	 * findById
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RecordMaterialMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * @param ids数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ids) throws Exception {
		dao.delete("RecordMaterialMapper.deleteAll", ids);
	}


	/**
	 * 根据SENDRECORD_ID查找关系列表
	 * @param pd（必填：SENDRECORD_ID）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findBySendRecordId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("RecordMaterialMapper.findBySendRecordId", pd);
	}


	/**
	 * 根据序号查找关系列表
	 * @param pd（必填SENDRECORD_ID，SEQUENCE）
	 */
	public PageData findByIndex(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RecordMaterialMapper.findByIndex", pd);
	}

	/**
	 * 查找当前图文后面的图文（> SEQUENCE）
	 * @param pd（必填SENDRECORD_ID，SEQUENCE）
	 */
	public List<PageData> findRecordBySendRecordId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("RecordMaterialMapper.findRecordBySendRecordId", pd);
	}

	/**
	 * 获取预览的图文信息
	 * @param pd（TEMP_ID）
	 */
	public PageData getYuLanTemp(String tempId) throws Exception {
		return (PageData)dao.findForObject("MaterialTempMapper.findById", tempId);
	}
	
	/**
	 * 新增预览
	 * @param pd（TEMP_ID）
	 * @throws Exception
	 */
	public void saveTemp(PageData pd) throws Exception {
		dao.save("MaterialTempMapper.save", pd);
		
	}

	/**
	 * 删除预览
	 * @param tempId
	 * @throws Exception
	 */
	public void deleteTemp(String tempId) throws Exception {
		dao.delete("MaterialTempMapper.delete", tempId);
		
	}

	/**
	 * 自定义菜单预览图文用，根据序号查找具体图文信息
	 * @param pd（必填SENDRECORD_ID，SEQUENCE）
	 */
	public PageData findByMediaIdAndSequence(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RecordMaterialMapper.findByMediaIdAndSequence", pd);
	}

	/**
	 * 根据群发记录删除
	 * @param sendrecord_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delBySendRecordId(String sendrecord_id) throws Exception {
		return (int) dao.delete("RecordMaterialMapper.delBySendRecordId", sendrecord_id);
	}
}
