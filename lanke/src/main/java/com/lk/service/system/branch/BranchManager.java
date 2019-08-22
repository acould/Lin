package com.lk.service.system.branch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 分店图片接口
 * 创建人：洪智鹏
 * 创建时间：2017-04-14
 * @version
 */
public interface BranchManager{

	/**
	 * 新增
	 * 保存门店id,图片url,创建时间到分点图片表
	 * @param pd--门店信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 包含分店图片相关信息的主键BRANCH_ID
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 *图片url,创建时间到分点图片表
	 * @param pd--门店信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page 展示 分店图片信息包含检索可以words
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 门店管理(修改)
	 * 通过门店id去查询门店图片
	 * @param pd--门店id
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 门店管理(修改)
	 * 通过门店id去查询门店图片
	 * @param pd 
	 * @param pd--门店id
	 * @throws Exception
	 */
	public ModelAndView listAll1(HttpServletRequest request, ModelAndView mv, PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS 包含主键id数组信息
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 删除该门店所有图片
	 * @param pd(门店id-STORE_ID)
	 * @throws Exception
	 */
	public void deleteStore(PageData pd) throws Exception;

	
	
}

