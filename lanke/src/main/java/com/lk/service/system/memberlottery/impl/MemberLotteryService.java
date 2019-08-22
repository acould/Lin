package com.lk.service.system.memberlottery.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.util.PageData;
import com.lk.service.system.memberlottery.MemberLotteryManager;

/** 
 * 说明： 会员抽奖记录
 * 创建人：洪智鹏
 * 创建时间：2017-02-23
 * @version
 */
@Service("memberlotteryService")
public class MemberLotteryService implements MemberLotteryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd包含会员抽奖记录的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MemberLotteryMapper.save", pd);
	}
	
	/**删除
	 * @param pd包含会员抽奖记录的主键
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MemberLotteryMapper.delete", pd);
	}
	
	/**修改
	 * @param pd包含会员抽奖记录的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MemberLotteryMapper.edit", pd);
	}
	
	/**
	 * 列表
	 * 列出MemberLottery列表(查询所有的会员兑奖信息)
	 * @param page会员抽奖记录页面和检索字段信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberLotteryMapper.datalistPage", page);
	}
	
	/**
	 * 微信端，查询会员奖品列表
	 * @param page设置pd（userId，STATE）
	 */
	public List<PageData> wxlist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberLotteryMapper.wxdatalist", page);
	}
	
	/**
	 * 列表
	 * 查询全部会员兑奖信息
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listExcel(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberLotteryMapper.listExcel", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberLotteryMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param  pd包含会员抽奖记录的主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberLotteryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS会员抽奖记录主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MemberLotteryMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 兑换奖品后
	 * 通过MEMBERLOTTERY_ID修改状态和时间
	 * @param pd
	 * @throws Exception
	 */
	public void editSqdj(PageData pd) throws Exception {
		dao.update("MemberLotteryMapper.editSqdj", pd);
	}

	/**
	 * 根据奖品id获取该奖品的兑换记录
	 * @param pd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findRecordByLottery(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("MemberLotteryMapper.findRecordByLottery", pd);
	}
	
}

