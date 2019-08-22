package com.lk.service.system.lottery;

import java.util.List;
import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： 网吧抽奖设置接口
 * 创建人：洪智鹏
 * 创建时间：2017-02-20
 * @version
 */
public interface LotteryManager{

	/**
	 * 新增
	 * 保存新增的抽奖设置信息
	 * @param pd包含网吧抽奖设置相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd 包含主键信息
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * 通过LOTTERY_ID保存修改后的抽奖设置信息
	 * @param pd包含网吧抽奖设置相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 * 列出Lottery列表(通过网吧id查询抽奖设置)
	 * @param page  展示网吧抽奖包含页面和检索字段的信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列表(全部)
	 * 查询全部抽奖设置
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * 根据ID读取(通过LOTTERY_ID获取抽奖设置)
	 * @param pd 包含主键
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS主键组成的数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	/**批量删除
	 * @param pd包含网吧主键intenetid
	 * @throws Exception
	 */
	public List<PageData> listByInternet(PageData pd)throws Exception;

	
}

