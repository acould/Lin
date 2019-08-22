package com.lk.service.system.cancel;

import java.util.List;

import com.lk.entity.Page;
import com.lk.util.PageData;
import net.sf.json.JSONObject;

/** 
 * 说明： 优惠券核销接口
 * 创建人：洪智鹏
 * 创建时间：2017-03-20
 * @version
 */
public interface CancelManager{

	/**新增
	 * @param pd 包含核销优惠券相关信息
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;


	/**
	 * 修改
	 * 通过CANCEL_ID保存修改信息
	 * @param pd 修改审核信息相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;

	/**
	 * 列表
	 * 通过用户id/卡劵id/门店id查询Cancel信息
	 * @param page 查看审核列表包含页面传递的检索字段等信息
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 列表(全部)
	 * 通过条件查询到需要导出的数据 
	 * @param pd  包含导出优惠券核销需要的数据
	 * @throws Exception
	 */

	public List<PageData> listExcel(PageData pd)throws Exception;
	
	/**列表我的福利
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listMy(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS 主键数组
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 通过卡劵id,卡劵信息修改信息
	 * @param pd
	 * @throws Exception
	 */
	public void editByCard(PageData pd)throws Exception;
	
	/**
	 * 根据微信用户和优惠券id，查询卡券的核销信息（降序排列，只取最新一条数据）
	 *
	 * @param wechatpd（ 选填：OPEN_ID微信用户标识id，CARD_ID卡号）
	 */
	public PageData findByUser(PageData wechatpd)throws Exception;
	
	
	/**
	 * 根据网吧id，用户领取的卡券号，和状态，获取卡券的核销信息
	 *
	 * @param wechatpd（选填：INTERNET_ID，CARD，STATE，）
	 * @throws Exception
	 */
	public PageData findByCode(PageData wechatpd)throws Exception;

	/**
	 * 根据用户信息查找卡券的核销信息
	 *
	 * @param wechatpd（选填：CARD_ID，OPEN_ID，STATE，START_TIME，END_TIME）
	 */
	public List<PageData> listByUser(PageData wechatpd)throws Exception;

	/**我的优惠券下拉无分页(bug)
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listPageMyAll(Page page) throws Exception;
	
	/**
	 * 后台用，查看核销的卡券列表
	 * @param page（OPEN_ID，INTENET_ID，STATE）
	 */
	public List<PageData> listPageCeshi(Page page) throws Exception;
	
	/**
	 * 微信端用，查看我的优惠券
	 * @param page（OPEN_ID，INTENET_ID，STATE）
	 */
	public List<PageData> wxlistPageCeshi(Page page) throws Exception;
	
	/**
	 * 列表，优惠券
	 * 分店只能筛选他自己有的卡券 
	 * @param pdStore
	 * @throws Exception
	 */
	public List<PageData> listCard(PageData pdStore) throws Exception;

	/**
	 * 列表(操作用户)
	 * 分店只能看到他自己的操作用户组 
	 * @param pdStore
	 * @throws Exception
	 */
	public List<PageData> listUser(PageData pdStore) throws Exception;
	
	/**
	 * 通过卡劵id查询数据
	 * @param pdStore 卡劵id--cardid
	 * @throws Exception
	 */
	public List<PageData> findByCardId(PageData pdStore) throws Exception;
	
	/**
	 * 核销卡券
	 * @param code  用户卡券码
	 * @return
	 * @throws Exception
	 */
	public JSONObject saveCancel(String code)throws Exception;


	/**
	 * 接收到微信卡券被核销推送，同步揽客
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void updateCancel(PageData pd, String openid) throws Exception;


	/**
	 * 刷新订单，现金券核销pubwin充值调用失败时提供
	 * @param pd
	 * @throws Exception
	 */
	public JSONObject updateRefreshCancel(PageData pd) throws Exception;

	/**
	 * 充值失败的订单，重新发起充值
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateRechargeAgain(PageData pd) throws Exception;

	/**
	 * 通过open_id获取user_id
	 * @param card
	 * @return
	 * @throws Exception
	 */
	public PageData selectUserId(PageData card) throws Exception;


}

