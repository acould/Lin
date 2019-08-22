package com.lk.service.system.lottery.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.lottery.LotteryManager;

/** 
 * 说明： 网吧抽奖设置
 * 创建人：洪智鹏
 * 创建时间：2017-02-20
 * @version
 */
@Service("lotteryService")
public class LotteryService implements LotteryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增
	 * 保存新增的抽奖设置信息
	 * @param pd包含网吧抽奖设置相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("LotteryMapper.save", pd);
	}
	
	/**删除
	 * @param pd包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("LotteryMapper.delete", pd);
	}
	
	/**
	 * 修改
	 * 通过LOTTERY_ID保存修改后的抽奖设置信息
	 * @param pd包含网吧抽奖设置相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("LotteryMapper.edit", pd);
	}
	
	/**
	 * 列表
	 * 列出Lottery列表(通过网吧id查询抽奖设置)
	 * @param page  展示网吧抽奖包含页面和检索字段的信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LotteryMapper.datalistPage", page);
	}
	
	/**
	 * 列表(全部)
	 * 查询全部抽奖设置
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LotteryMapper.listAll", pd);
	}
	
	/**
	 * 通过id获取数据
	 * 根据ID读取(通过LOTTERY_ID获取抽奖设置)
	 * @param pd 包含主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LotteryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("LotteryMapper.deleteAll", ArrayDATA_IDS);
	}

	/**通过网吧id获取数据
	 * @param pd包含网吧主键intenetid
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByInternet(PageData pd) throws Exception {
		return  (List<PageData>)dao.findForList("LotteryMapper.listByInternet", pd);
	}

	
}

