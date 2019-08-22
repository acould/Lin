package com.lk.service.weixin.pay.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.wechat.WeixinService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.entity.unionPay.Recharge;
import com.lk.entity.unionPay.Warning;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.service.weixin.template.TemplateManager;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.util.system.DateUtils;
import com.lk.wechat.util.SmsLogUtil;
import com.lk.wechat.util.TemplateMsgUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

@SuppressWarnings("unchecked")
@Service("generateOrderService")
public class GenerateOrderImpl implements GenerateOrderService{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
    @Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "weixinService")
	private WeixinService weixinService;
	@Resource(name = "userFlowService")
	private UserFlowService userFlowService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	/**
	 * 创建订单
	 */
	@Override
	public Boolean addOrder(PageData pd) {
		Boolean result=true;
		try {
			dao.save("PayMapper.addOrder", pd);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result; 
	}
	/**
	 * by订单号 查询 订单
	 */
	@Override
	public PageData findByIdormerOrderId(PageData pd){
		PageData pd1 = new PageData();
		try {
			pd1=(PageData) dao.findForObject("PayMapper.findByIdormerOrderId", pd);
		} catch (Exception e) {
			pd1.put("flag", "未查找到的订单");
			e.printStackTrace();
		}
		return pd1;
	}
	
	/**修改订单支付状态*/
	@Override
	public void editOrderState(PageData pd) throws Exception {
		dao.update("PayMapper.editOrderState", pd);
	}
	/**
	 * 删除订单
	 * 根据订单号删除merOrderId
	 */
	@Override
	public void deleteOrder(String merOrderId) throws Exception {
		dao.delete("PayMapper.deleteOrder", merOrderId);
	}
	/**
	 * 添加充值成功的订单到历史订单表
	 */
	@Override
	public void addOrderHistory(PageData pd) throws Exception {
		dao.save("PayMapper.addOrderHistory", pd);
	}
	

	/**
	 * 根据门店ID查看是否开通在线支付
	 */
	@Override
	public PageData findByStoreId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("InternetDatumMapper.findByStoreId", pd);
	}
	/**
	 * 查看list 全部 订单
	 */
	@Override
	public List<PageData> listqb(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PayMapper.listqb", pd);
	}
	/**
	 * 查看订单详情
	 */
	@Override
	public PageData findByIdorder(String id) throws Exception {
		return (PageData) dao.findForObject("PayMapper.findByIdorder", id);
	}
	
	/**
	 * 清理失败订单
	 */
	@Override
	public void deleteFailOrder(String user_id) throws Exception {
		dao.delete("PayMapper.deleteFailOrder", user_id);
	}
	@Override
	public void tuisong(PageData pd) throws Exception {
		/*****************充值成功，发送模板消息********************/
        //获取微信用户的open_id
        String open_id=pd.getString("open_id");
		//获取token
        String internet_id = pd.getString("internet_id");

		// 组装内容
		PageData pdBind = (PageData) dao.findForObject("PayMapper.findByStoreId", pd);
		String overage = pdBind.getString("OVERAGE");
		String reward = pdBind.getString("REWARD");
		double principal = Double.parseDouble(overage);
		String totalAmount = (principal + Double.parseDouble(pd.get("totalAmount").toString())) + "";//卡内余额
		//发送模板消息
		userFlowService.sendMoneyChangeTemp(internet_id, open_id,
				DateUtils.getDateTime(), pd.get("totalAmount").toString(),
				"揽客充值", totalAmount);


		String totalRewardAmount = (Double.parseDouble(reward) + Double.parseDouble(pd.get("rewardAmount").toString())) + "";//卡内奖励余额
		//更新用户余额
		pdBind.put("OVERAGE", totalAmount+"");//总的
		pdBind.put("REWARD", totalRewardAmount+"");
		bunduserService.edit(pdBind);
	}
	/**
	 * 查看充值规则
	 */
	@Override
	public List<PageData> listRule(String storeid) throws Exception {
		List<PageData> a=(List<PageData>)dao.findForList("PayMapper.listRule", storeid);
		return (List<PageData>)dao.findForList("PayMapper.listRule", storeid);
	}
	/**
	 * 验证充值规则
	 */
	@Override
	public Boolean yzRule(String id,String rincipal_balance,String reward_balance) throws Exception {
		PageData pd = (PageData)dao.findForObject("PayMapper.yzRule", id);
		if(pd.get("recharge_money").toString().equals(rincipal_balance) && pd.get("reward_money").toString().equals(reward_balance)){
			return true;
		}
		return false;
	}
	@Override
	public void addwarning(PageData pd) throws Exception {
		dao.save("PayMapper.addwarning", pd);
	}
	
	@Override
	public void findwarning() throws Exception {
		Date createtime = new Date();
		String phone="13185021992";
		List<Warning> countlist = (List<Warning>) dao.findForList("PayMapper.count", createtime);
		for(Warning o:countlist){
			if(o.getPercentage()>50){
				Boolean warningMessage = SmsLogUtil.warningMessage("aaaa", o.getPercentage()+"", o.getCount()+"", phone);
			}
		}
	}

    /**
     * 根据卡券id和用户id，查询订单
     * @param user_id
     * @param card_id
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> findByCardIdAndUserId(String user_id, String card_id, String lastTime, String nowTime) throws Exception{
        PageData pd = new PageData();
        pd.put("user_id", user_id);
        pd.put("internetrule_id", card_id);
        pd.put("lastTime", lastTime);
        pd.put("nowTime", nowTime);
        return (List<PageData>) dao.findForList("PayMapper.findByCardIdAndUserId", pd);
    }
}
