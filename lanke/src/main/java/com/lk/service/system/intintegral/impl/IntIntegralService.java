package com.lk.service.system.intintegral.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.intintegral.IntIntegralManager;

/** 
 * 说明： j积分规则表
 * 创建人：洪智鹏
 * 创建时间：2016-11-01
 * @version
 */
@Service("intintegralService")
public class IntIntegralService implements IntIntegralManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 新增积分规则
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("IntIntegralMapper.save", pd);
	}
	
	/**
	 * 删除
	 * 通过INTINTEGRAL_ID删除积分规则
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("IntIntegralMapper.delete", pd);
	}
	
	/**
	 * 修改
	 * 通过INTINTEGRAL_ID保存修改后的信息
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("IntIntegralMapper.edit", pd);
	}
	
	/**
	 * 列表
	 * 列出IntIntegral列表(通过网吧id/关键词查询相关数据)
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("IntIntegralMapper.datalistPage", page);
	}
	
	/**
	 * 列表(全部)
	 * 列出IntIntegral列表(通过网吧id获取信息)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("IntIntegralMapper.listAll", pd);
	}
	
	/**查询本网吧设置积分规则
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listIntenet(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("IntIntegralMapper.listIntenet", pd);
	}
	
	/**
	 * 通过id获取数据
	 * 根据ID读取(通过积分表id查询数据)
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("IntIntegralMapper.findById", pd);
	}
	
	/**
	 * 批量删除
	 * 通过INTINTEGRAL_ID批量删除积分规则
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("IntIntegralMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void saveSign(Map map) throws Exception {
		dao.save("IntIntegralMapper.saveSign",map);
	}


	@Override
	public List<PageData> getTypes(Map map) throws Exception {
		return (List<PageData>) dao.findForList("IntIntegralMapper.getTypes", map);
	}

	@Override
	public void updateSign(Map map) throws Exception {
		dao.update("IntIntegralMapper.updateSign", map);
	}

	@Override
	public List<PageData> findnums(Map map) throws Exception {
		return (List<PageData>) dao.findForList("IntIntegralMapper.findnums", map);
	}

	@Override
	public List<PageData> findBySign(String intenet_id) throws Exception {
		Map map = new HashMap();
		map.put("intenet_id",intenet_id);
		return (List<PageData>) dao.findForList("IntIntegralMapper.findBySign",map);
	}

	@Override
	public List<PageData> getStores(String intenet_id) throws Exception {
		Map map = new HashMap();
		map.put("intenet_id",intenet_id);
		return (List<PageData>) dao.findForList("IntIntegralMapper.getStores",map);
	}

	@Override
	public List<PageData> getscanStores(@Param(value = "list") List<PageData> list) throws Exception {
		return (List<PageData>) dao.findForList("IntIntegralMapper.getscanStores",list);
	}

	@Override
	public List<PageData> getVStores(String intenet_id) throws Exception {
		Map map = new HashMap();
		map.put("intenet_id",intenet_id);
		return (List<PageData>) dao.findForList("IntIntegralMapper.getVStores",map);
	}

	@Override
	public PageData getSign(Map map) throws Exception {
		return (PageData)dao.findForObject("IntIntegralMapper.getSign", map);
	}

	@Override
	public PageData getScanUp(Map map) throws Exception {
		return (PageData)dao.findForObject("IntIntegralMapper.getScanUp", map);
	}

	@Override
	public void addSign(@Param(value = "list") List list) throws Exception {
		dao.save("IntIntegralMapper.addSign",list);
	}

	@Override
	public void addSign2(@Param(value = "list") List list) throws Exception {
		dao.save("IntIntegralMapper.addSign2",list);
	}

	@Override
	public PageData checkSign(Map map) throws Exception {
		return (PageData) dao.findForObject("IntIntegralMapper.checkSign",map);
	}

	@Override
	public PageData checkSign2(Map map) throws Exception {
		return (PageData) dao.findForObject("IntIntegralMapper.checkSign2",map);
	}

	@Override
	public void deleteSign(Map map) throws Exception {
		dao.delete("IntIntegralMapper.deleteSign",map);
	}

	@Override
	public void deleteSign2(Map map) throws Exception {
		dao.delete("IntIntegralMapper.deleteSign2",map);
	}

	@Override
	public List<PageData> getAllSign(PageData pd ) throws Exception {
		return (List<PageData>) dao.findForList("IntIntegralMapper.getAllSign",pd);
	}

	@Override
	public void saveSigns(@Param(value = "list") List list) throws Exception {
		dao.update("IntIntegralMapper.saveSigns",list);
	}

	@Override
	public void saveSigns2(@Param(value = "list") List list) throws Exception {
		dao.update("IntIntegralMapper.saveSigns2",list);
	}

	/**
	 * 通过类型获取数据
	 * 通过类型和网吧id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCATEGRORY(PageData pd) throws Exception {
		return (PageData)dao.findForObject("IntIntegralMapper.findByCATEGRORY", pd);
	}
	
}

