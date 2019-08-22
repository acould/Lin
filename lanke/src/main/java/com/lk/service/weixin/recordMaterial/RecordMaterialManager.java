package com.lk.service.weixin.recordMaterial;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

public interface RecordMaterialManager {
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 查找分页列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/***
	 * 根据主键id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 根据SENDRECORD_ID查找关系列表
	 * @param pd（必填：SENDRECORD_ID）
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findBySendRecordId(PageData pd)throws Exception;
	
	/**
	 * 根据序号查找关系列表
	 * @param pd（必填SENDRECORD_ID，SEQUENCE）
	 */
	public PageData findByIndex(PageData pd)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ids)throws Exception;
	
	/**
	 * 查找当前图文后面的图文（> SEQUENCE）
	 * @param pd（必填SENDRECORD_ID，SEQUENCE）
	 */
	public List<PageData> findRecordBySendRecordId(PageData pd)throws Exception;
	
	
	/**
	 * 获取预览的图文信息
	 * @param pd（TEMP_ID）
	 */
	public PageData getYuLanTemp(String tempId) throws Exception;
	
	/**
	 * 新增预览
	 * @param pd（TEMP_ID）
	 * @throws Exception
	 */
	public void saveTemp(PageData pd) throws Exception;
	
	/**
	 * 删除预览
	 * @param tempId
	 * @throws Exception
	 */
	public void deleteTemp(String tempId) throws Exception;
	
	/**
	 * 自定义菜单预览图文用，根据序号查找具体图文信息
	 * @param pd（必填SENDRECORD_ID，SEQUENCE）
	 */
	public PageData findByMediaIdAndSequence(PageData pd)throws Exception;

    int delBySendRecordId(String sendrecord_id) throws Exception;
}
