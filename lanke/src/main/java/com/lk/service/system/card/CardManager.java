package com.lk.service.system.card;

import java.util.List;


import com.lk.entity.Message;
import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.util.PageData;

/**
 * 说明： 卡卷管理接口
 * 创建人：洪智鹏
 * 创建时间：2016-10-31
 */
public interface CardManager {

    /**
     * 新增
     *
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd) throws Exception;

    /**
     * 删除
     * 通过卡劵id删除卡劵信息
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception;

    /**
     * 修改
     * 修改卡券信息
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd) throws Exception;

    /**
     * 列表
     * 通过网吧id/用户id/门店id去查询卡劵信息
     * @param page 设置pd（必填：intenetId，选填：STORE_ID操作人员所在的门店id，keywords关键词）
     * @throws Exception
     */
    public List<PageData> list(Page page) throws Exception;

    /**
     * 列表(全部)
     * 查询全部卡劵信息
     * @param pd
     * @throws Exception
     */
    public List<PageData> listAll(PageData pd) throws Exception;

    /**
     * 通过卡劵id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception;

    /**
     * 通过卡劵id获取卡劵数据
     * @param pd
     * @throws Exception
     */
    public PageData findByCardId(PageData pd) throws Exception;

    /**
     * 批量删除
     * 通过卡劵id进行批量删除
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

    /**
     * 修改卡券状态、
     * @param pd（必填：CARD_ID，STATE）
     */
    public void updateState(PageData pd) throws Exception;

    /**
     * 微信端用，获取卡券列表
     * @param pd（选填：intenetId，STORE_ID，cardType卡券场景（BENEFIT_TYPE））
     */
    public List<PageData> listFl(PageData pd) throws Exception;

    /**
     * 通过场景查看优惠券(查看某场景对应的优惠券)
     * @param pdCard
     * @throws Exception
     */
    public PageData findByCenece(PageData pdCard) throws Exception;

    /**
     * 根据优惠类型，查看网吧的优惠券（按新增时间降序，限只取一条）
     * @param pd（INTERNET_ID，FAV_TYPE卡券场景（FAV_TYPE优惠类型））
     */
    public PageData findByInternetId(PageData pd) throws Exception;

    /**
     * 查看网吧所有的优惠券
     * @param pd（必填：INTERNET_ID）
     */
    public List<PageData> listByInternet(PageData pd) throws Exception;

    /**
     * 自定义菜单中使用，查看网吧的通用优惠券（FAV_TYPE = 'CURREN'和IS_ALL = '1'的通用券）
     * @param pd（INTERNET_ID）
     */
    public List<PageData> listByMenu(PageData pd) throws Exception;

    /**
     * 通过AUDIT_STATE获取数据
     *
     * @param pd
     * @throws Exception
     */
    public List<PageData> findByState(PageData pd) throws Exception;

    /**
     * 通过卡券id和user_id去判断是否可领取
     * @param pd
     * @return
     * @throws Exception
     */
	public JSONObject getCard(PageData pd) throws Exception;

	/**
	 * 通过网吧id去获取最近的秒抢券信息
	 * @param pd1
	 * @return
	 * @throws Exception
	 */
	public PageData cardGrab(PageData pd1) throws Exception;
	
	/**
	 * 通过网吧id去获取最近的连续上网满时长券信息
	 * @param pd1
	 * @return
	 * @throws Exception
	 */
	public PageData cardTerm(PageData pd1) throws Exception;

	/**
	 * 每个门店，每种时长的券只能存在一张
	 * @param
	 * @return
	 * @throws Exception
	 */
	public JSONObject judgeTerm(PageData pd, String[] storeList) throws Exception;
	
	/**通过门店id,上网时长去推送连续上网满时长券
	 * 
	 * @param pd1
	 * @throws Exception
	 */
	public void getCards(PageData pd1) throws Exception;

	/**
	 * 通过条件去获取卡券总和
	 * @param page
	 * @return
	 */
	public Integer lists(Page page) throws Exception;

	/**
	 * 保存冲送券卡券关系
	 * @param pd
	 * @throws Exception
	 */
	public void saveRush(PageData pd) throws Exception;

	/**
	 * 根据卡券id去获取赠送金额
	 * @param pdCard
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getHandSel(PageData pdCard)throws Exception;

    public void saveCardOpen(PageData pd1) throws Exception;

	/**
	 * 通过id查看领取情况
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public PageData findId(String string) throws Exception;

	/**
	 * 通过卡券id查询数据
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selRushs(String string)throws Exception;

	/**
	 * 改变发送状态
	 * @param pd1
	 * @throws Exception
	 */
	public void editCardOpen(PageData pd1) throws Exception;

	/**
	 * 卡券逻辑删除
	 * @param pdCard
	 * @return
	 * @throws Exception
	 */
    int logicDel(PageData pdCard) throws Exception;



    Message selCardList(PageData pd) throws Exception;

    PageData findCardAndSceneById(String card_id) throws Exception;

    Message saveCardss(PageData pd)throws Exception;

    Message saveRushCard(PageData pd)throws Exception;

    List<PageData> findValidRushByCardId(String card_id) throws Exception;

    List<PageData> findByStoreIdAndFavType(String store_id, String rush) throws Exception;

    PageData findByOrderIdAndCardId(PageData pd) throws Exception;

    Message rushReceived(String cardId, String open_id, String order_id, String cancel_id) throws Exception;

    int editByOrderIdAndCardIdAndOpenId(PageData pdCardOpen) throws Exception;

    PageData findCardOpenById(String id)  throws Exception;
}

