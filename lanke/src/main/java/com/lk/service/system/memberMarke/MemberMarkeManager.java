package com.lk.service.system.memberMarke;

import java.util.List;

import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.util.PageData;

import net.sf.json.JSONObject;

/**
 * 说明： 会员营销接口 创建人：lzh 创建时间：2018-07-24
 */
public interface MemberMarkeManager {

	/**
	 * 去查看该用户所有门店
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> storeList(Page page) throws Exception;

	/**
	 * 去查看该用户所有粉丝
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> fanList(PageData pd) throws Exception;

	/**
	 * 保存群发模板并群发消息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void toMarket(PageData pd) throws Exception;

	/**
	 * 去查询会员营销列表
	 * 
	 * @param page
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> list(Page page, PageData pd) throws Exception;

	/**
	 * 去查询指定营销信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData view(PageData pd) throws Exception;

	/**
	 * 删除指定营销信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject delete(PageData pd) throws Exception;

	/**
	 * 批量删除指定营销信息
	 * 
	 * @param arrayDATA_IDS
	 * @throws Exception
	 */
	public JSONObject deleteAll(String[] arrayDATA_IDS) throws Exception;

	/**
	 * 通过门店id获取门店名称
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByStoreId(PageData pd) throws Exception;

	/**
	 * 通过storeid去查询门店标签
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject selectTips(PageData pd) throws Exception;

	/**
	 * 通过id去查询链接信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findLink(PageData pdLink) throws Exception;

	/**
	 * 查询满足条件角色,并发送消息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject sendMessage(PageData pd) throws Exception;

	/**
	 * 通过mass_id去查询信息
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findContent(PageData pd) throws Exception;

	/**
	 * 获取符合条件的卡券
	 * 
	 * @param page
	 */
	public List<PageData> selectCard(Page page) throws Exception;

	/**
	 * 通过卡券id获取卡券信息
	 * 
	 * @param page
	 */
	public List<PageData> cardView(PageData pd) throws Exception;

	/**
	 * 通过门店id数组获取门店名称
	 * 
	 * @param page
	 */
	public List<PageData> storeName(String[] store_ids) throws Exception;

	/**
	 * 通过门店id获取门店名称
	 * 
	 * @param page
	 */
	public PageData selectName(PageData pd2) throws Exception;

	/**
	 * 通过记录表id去查询查看详情状态(view_state)
	 * 
	 * @param pd
	 */
	public PageData findRecord(PageData pd) throws Exception;

	/**
	 * 通过记录表id去修改查看详情状态(view_state)
	 * 
	 * @param pd
	 */
	public void editRecord(PageData pd) throws Exception;

	/**
	 * 获取js-sdk 卡券接口的相关信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCard_Ext(PageData pd) throws Exception;
	
	/**
	 * 获取js-sdk 卡券接口的相关信息
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCard_Ext1(PageData pd) throws Exception;

	/**
	 * 这里通过open_id和卡券id去判断用户是否已领取该卡券(未核销)
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findCard(PageData pd) throws Exception;

	/**
	 * 这里通过user_id和卡券id去判断用户是否可以领取该卡券
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject judgeUser(PageData pd) throws Exception;

	/**
	 * 通过卡劵id获取信息
	 * @param pdStore 卡劵id--cardId
	 * @throws Exception
	 */
	public List<PageData> listByCardId(PageData pdStore) throws Exception;


	/**
	 * 检查是否下机推送，且检查参数
	 * @param pd （type，）
	 * @return
	 * @throws Exception
	 */
    JSONObject checkTypeSend(PageData pd) throws Exception;

	/**
	 * 修改是否要下机推送
	 * @param pd
	 * @return
	 */
	JSONObject updateSendType(PageData pd) throws Exception;
}
