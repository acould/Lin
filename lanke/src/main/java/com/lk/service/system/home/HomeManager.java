package com.lk.service.system.home;

import java.util.List;
import java.util.Map;
import com.lk.entity.Page;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/**
 * 说明： 首页接口 创建人：李泽华 创建时间：2018-10-18
 * 
 * @version
 */
public interface HomeManager {

	/**
	 * 计算待查看消息,待发货商品,待核销卡券,待回复留言
	 * 
	 * @param page
	 * @throws Exception
	 */
	public Map<String, Object> count(Page page) throws Exception;

	/**
	 * 通过条件查询收入
	 * 
	 * @param page
	 * @throws Exception
	 */
	public JSONObject incomeCount(Page page) throws Exception;

	/**
	 * 通过条件查询粉丝/会员数量
	 * 
	 * @param page
	 * @throws Exception
	 */
	public JSONObject userCount(Page page) throws Exception;

	/**
	 * 查询相关门店信息
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> storeList(Page page)throws Exception;

	/**
	 * 通过菜单url获取菜单目录id
	 * @param page
	 * @throws Exception
	 */
	public PageData selectMenu(PageData pd) throws Exception;

	/**
	 * 获取门店列表
	 * @param page
	 * @return
	 */
	public List<PageData> storeLists(Page page) throws Exception;

}
