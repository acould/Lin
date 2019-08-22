package com.lk.service.system.memberlottery;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 会员抽奖记录接口
 * 创建人：洪智鹏
 * 创建时间：2017-02-23
 * @version
 */
public interface MemberLotteryManager{

	/**新增
	 * @param pd包含会员抽奖记录的相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd包含会员抽奖记录的主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd包含会员抽奖记录的相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 * 列出MemberLottery列表(查询所有的会员兑奖信息)
	 * @param page会员抽奖记录页面和检索字段信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 微信端，查询会员奖品列表
	 * @param page设置pd（userId，STATE）
	 */
	public List<PageData> wxlist(Page page)throws Exception;
	
	/**
	 * 列表
	 * 查询全部会员兑奖信息
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listExcel(Page page)throws Exception;
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd包含会员抽奖记录的主键信息
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS会员抽奖记录主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 兑换奖品后
	 * 通过MEMBERLOTTERY_ID修改状态和时间
	 * @param pd
	 * @throws Exception
	 */
	public void editSqdj(PageData pd)throws Exception;

	/**
	 * 根据奖品id获取该奖品的兑换记录
	 * @param pd
	 * @return
	 */
	public List<PageData> findRecordByLottery(PageData pd) throws Exception ;
	
}

