package com.lk.service.system.intintegral;

import java.util.List;
import java.util.Map;

import com.lk.entity.Page;
import com.lk.util.PageData;

/** 
 * 说明： j积分规则表接口
 * 创建人：洪智鹏
 * 创建时间：2016-11-01
 * @version
 */
public interface IntIntegralManager{

	/**
	 * 新增
	 * 新增积分规则
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 删除
	 * 通过INTINTEGRAL_ID删除积分规则
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * 修改
	 * 通过INTINTEGRAL_ID保存修改后的信息
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 列表
	 * 列出IntIntegral列表(通过网吧id/关键词查询相关数据)
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**
	 * 列表(全部)
	 * 列出IntIntegral列表(通过网吧id获取信息)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 通过id获取数据
	 * 根据ID读取(通过积分表id查询数据)
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 通过类型获取数据
	 * 通过类型和网吧id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCATEGRORY(PageData pd)throws Exception;
	
	/**
	 * 批量删除
	 * 通过INTINTEGRAL_ID批量删除积分规则
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

    void saveSign(Map map) throws Exception;

	List<PageData> getTypes(Map map) throws Exception;

	void updateSign(Map map) throws Exception;

	List<PageData> findnums(Map map) throws Exception;

	List<PageData> findBySign(String intenet_id) throws Exception;

	List<PageData> getStores(String intenet_id) throws Exception;

	List<PageData> getscanStores(List<PageData> list) throws Exception;

	List<PageData> getVStores(String intenet_id) throws Exception;

	PageData getSign(Map map) throws  Exception;

	PageData getScanUp(Map map) throws Exception;

	void addSign(List list) throws Exception;

	void addSign2(List list)throws Exception;

	PageData checkSign(Map map) throws Exception;

	PageData checkSign2(Map map) throws Exception;

	void deleteSign(Map map) throws Exception;

	void deleteSign2(Map map) throws Exception;

	List<PageData> getAllSign(PageData pd ) throws Exception;

	void saveSigns(List list) throws Exception;

	void saveSigns2(List list) throws Exception;
}

