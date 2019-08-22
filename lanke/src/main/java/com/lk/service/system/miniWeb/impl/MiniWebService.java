package com.lk.service.system.miniWeb.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.system.miniWeb.MiniWebManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;

import net.sf.json.JSONObject;

@Service("miniWebService")
public class MiniWebService implements MiniWebManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * 新增网吧微官网信息
	 * @param pd--微官网信息
	 * @throws Exception
	 */
	public JSONObject save(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.save("MiniWebMapper.save", pd);
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "保存成功！");	
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试!");	
		}
		return json;
	}

	/**
	 * 删除
	 * 通过MINIWEB_ID删除相关微官网信息
	 * @param pd--MINIWEB_ID
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.delete("MiniWebMapper.delete", pd);
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "删除成功！");	
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试!");	
		}
		return json;
	}

	/**修改
	 * 通过MINIWEB_ID去修改微官网信息
	 * @param pd --MINIWEB_ID,CREATE_TIME--创建时间,url
	 * @throws Exception
	 */
	public JSONObject edit(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		try {
			dao.update("MiniWebMapper.edit", pd);
			json.put("result", PublicManagerUtil.TRUE);
			json.put("message", "保存成功！");	
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统繁忙,请稍后再试!");	
		}
		return json;
	}

	/**
	 * 列表
	 * 通过网吧id去查询微官网信息	
	 * @param page--网吧id
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("MiniWebMapper.datalistPage", page);
	}

	/**
	 * 通过id获取数据
	 * 通过网吧id去查询微官网信息	
	 * @param pd--网吧id
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MiniWebMapper.findById", pd);
	}

	public PageData findByInternetId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MiniWebMapper.findByInternetId", pd);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("MiniWebMapper.deleteAll", ArrayDATA_IDS);
		
	}

}
