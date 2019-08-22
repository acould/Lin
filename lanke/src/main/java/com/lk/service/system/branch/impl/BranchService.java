package com.lk.service.system.branch.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.util.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.branch.BranchManager;

/** 
 * 说明： 分店图片
 * 创建人：洪智鹏
 * 创建时间：2017-04-14
 * @version
 */
@Service("branchService")
public class BranchService implements BranchManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 保存门店id,图片url,创建时间到分点图片表
	 * @param pd--门店信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BranchMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BranchMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BranchMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BranchMapper.datalistPage", page);
	}
	
	/**
	 * 门店管理(修改)
	 * 通过门店id去查询门店图片
	 * @param pd--门店id
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		List<PageData> varList = (List<PageData>)dao.findForList("BranchMapper.listAll", pd);
		for (int i = 0; i < varList.size(); i++) {
			if(StringUtil.isNotEmpty(pd.getString("pre"))){
				varList.get(i).put("picture_url", Tools.replaceLanker(pd.getString("pre")) + Const.FILEPATHIMG + varList.get(i).getString("URL"));
			}
		}
		return varList;
	}
	
	/**
	 * 门店管理(修改)
	 * 通过门店id去查询门店图片
	 * @param pd--门店id
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView listAll1(HttpServletRequest request, ModelAndView mv, PageData pd)throws Exception{
		pd.put("STORE_ID", request.getParameter("STORE_ID"));
		List<PageData> picList=(List<PageData>)dao.findForList("BranchMapper.listAll", pd);
		mv.addObject("picList", picList);
		mv.setViewName("system/storeShow/storeShow_add");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		return mv;
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BranchMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BranchMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 删除该门店所有图片
	 * @param pd(门店id-STORE_ID)
	 * @throws Exception
	 */
	@Override
	public void deleteStore(PageData pd) throws Exception {
		dao.delete("BranchMapper.deleteStore", pd);
	}
	
}

