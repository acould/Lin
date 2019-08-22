package com.lk.service.weixin.pay;

import java.util.List;

import net.sf.json.JSONObject;

import com.lk.entity.Page;
import com.lk.entity.unionPay.Recharge;
import com.lk.util.PageData;

public interface GenerateOrderService {
	/**创建订单*/
	public Boolean addOrder(PageData pd) throws Exception;
	
	/**by ( id  or  merOrderId)查询订单表*/
	public PageData findByIdormerOrderId(PageData pd) throws Exception;
	
	/**修改订单支付状态*/
	public void editOrderState(PageData pd) throws Exception;
	
	/**bymerOrderId删除订单表*/
	public void deleteOrder(String merOrderId) throws Exception;
	
	/**添加历史订单表*/
	public void addOrderHistory(PageData pd) throws Exception;
	
	/**根据门店ID查看是否开通在线支付*/
	public PageData findByStoreId(PageData pd) throws Exception;
	
	/**查看全部订单*/
	public List<PageData> listqb(PageData pd) throws Exception;
	
	/**查看订单详情*/
	public PageData findByIdorder(String id) throws Exception;
	
	/**清理失败订单*/
	public void deleteFailOrder(String user_id)throws Exception;
	
	/**充值推送*/
	public void tuisong(PageData pd) throws Exception;
	
	/**查看充值规则*/
	public List<PageData> listRule(String storeid) throws Exception;
	
	/**验证充值规则*/
	public Boolean yzRule(String id,String rincipal_balance,String reward_balance) throws Exception;
	
	/**保存支付连接状态*/
	public void addwarning(PageData pd) throws Exception;
	/***
	 * 查询连接状态预警
	 * @throws Exception
	 */
	public void findwarning() throws Exception;

    List<PageData> findByCardIdAndUserId(String user_id, String card_id, String lastTime, String nowTime)  throws Exception;
}
