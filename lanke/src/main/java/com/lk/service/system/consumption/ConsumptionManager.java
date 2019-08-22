package com.lk.service.system.consumption;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 积分消耗表接口
 * 创建人：洪智鹏
 * 创建时间：2017-02-23
 * @version
 */
public interface ConsumptionManager{

	/**
	 * 新增
	 * 保存新的积分消耗信息
	 * @param pd新增会员积分消耗记录的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * 通过CONSUMPTION_ID删除积分消耗信息
	 * @param pd新增会员积分消耗记录的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * 通过CONSUMPTION_ID修改积分消耗信息
	 * @param pd会员积分消耗记录的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 * 列出Consumption列表(通过网吧id查询积分消耗信息)
	 * @param page 展现用户消耗积分记录
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列表(全部)
	 * 获取所有积分消耗信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * 根据ID读取(通过CONSUMPTION_ID查询积分消耗信息)
	 * @param pd 根据主键查询
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 批量删除
	 * 通过CONSUMPTION_ID批量删除积分消耗信息
	 * @param ArrayDATA_IDS 主键数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 通过网吧id和类型查询
	 * 通过CONSUMPTION_ID去查询id是否已存在
	 * @param pd包含网吧id和类型查询
	 * @throws Exception
	 */
	public PageData findByInetrnet(PageData pdCons)throws Exception;
	
}

