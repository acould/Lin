package com.lk.service.system.task;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.entity.Page;
import com.lk.service.billiCenter.clientConnectLog.ClientConnectLogService;
import com.lk.service.system.card.impl.CardService;
import com.lk.service.system.store.StoreManager;
import com.lk.service.tb.Message.MessageManager;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.SmsLogUtil;
import com.lk.wechat.util.TemplateMsgUtil;
import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

@Service()
@Component
@Lazy(false) // 防止延迟加载
public class Timers {

	@Resource(name = "TaskService")
	private TaskService taskService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "messageService")
	private MessageManager messageService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "cardService")
	private CardService cardService;

	@Resource(name = "weixinService")
	private WeixinService weixinService;

	/**
	 * 每六个小时执行一次 0 0 0/6 * * ?(卡券审核状态查询)
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0/6 * * ?")
	public void Zerotask() throws Exception {
		taskService.mission();
	}

	/**
	 * 凌晨3点半触发，更新所有用户的流水信息
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 30 03 * * ?")
	public void Zerotask2() throws Exception {
		// 更新所有用户的流水信息
		taskService.addFlow();
	}

	/**
	 * 凌晨12点半触发(修改过期门店状态)
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 30 00 * * ?")
	public void Zerotask3() throws Exception {
		// 先去查询过期门店并修改状态
		List<PageData> list = storeService.selV();
		for (int i = 0; i < list.size(); i++) {
			PageData pd = new PageData();
			// 过期：到期日期 < 本日日期
			String EXPIRATION_TIME = list.get(i).getString("EXPIRATION_TIME");
			Date expire = Tools.str2Date(EXPIRATION_TIME, "yyyy-MM-dd");
			if (expire.getTime() < new Date().getTime()) {
				pd.put("STORE_ID", list.get(i).getString("STORE_ID"));

				// 加v过期门店，更改状态
				Cache storeVCache = CacheManager.getStoreVCache();
				storeVCache.insertObject(PublicManagerUtil.client_prefix + pd.getString("STORE_ID"), "0");


				// 同时主动断开该门店客户端
				Iterator<Entry<String, ChannelHandlerContext>> iterator = ChannelCache.channelMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, ChannelHandlerContext> entry = iterator.next();
					// 通过门店id,断开连接,并清除缓存
					if (entry.getKey().equals(pd.getString("STORE_ID"))) {
						ChannelHandlerContext ctx = ChannelCache.channelMap.get(pd.getString("STORE_ID"));
						ChannelCache.channelMap.values().remove(entry.getValue());
						ctx.close();

                        //保存客户端连接情况
                        PageData pdClient = new PageData();
                        pdClient.put("id", StringUtil.get32UUID());
                        pdClient.put("store_id", pd.getString("STORE_ID"));
                        pdClient.put("create_time", Tools.date2Str(new Date()));
                        pdClient.put("status", "4");//到期断开连接
                        ClientConnectLogService ccls = (ClientConnectLogService) ContextLoader.getCurrentWebApplicationContext().getBean("clientConnectLogServiceImpl");
                        ccls.save(pdClient);
                        break;
					}
				}
			}
		}
	}

	/**
	 * 早上9点半(快过期门店消息通知(提前3天))
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 30 9 * * ?")
	public void Zerotask5() throws Exception {
		List<PageData> list = storeService.selectV();

		for (int i = 0; i < list.size(); i++) {
            PageData pdStoreV = list.get(i);

            long date1 = Tools.str2Date2(pdStoreV.get("EXPIRATION_TIME").toString()).getTime();// 过期日期
            long date2 = new Date().getTime(); // 当前日期
			long date3 = date1 - date2;
			//提前3天通知
			if(date3 < 0 || (date3 > 0 && date3 < (3 * 24 * 60 * 60 * 1000))){
                if(StringUtil.isEmpty(pdStoreV.getString("notice"))
                        || pdStoreV.getString("notice").equals("0")){
                    // 未发送过过期通知
                    // 满足条件的门店,发送短信+模板消息
                    // 下列为门店名称,门店联系方式,两个参数(短信用)
                    String store_name = pdStoreV.getString("STORE_NAME");
                    String phone = pdStoreV.getString("STORE_PHONE");

                    // 然后发送短信(加V过期预警)
                    SmsLogUtil.sendNotice(store_name, phone, pdStoreV.get("EXPIRATION_TIME").toString());

                    //后台提示信息
                    PageData pdMessage = new PageData();
                    // 保存消息通知信息
                    pdMessage.put("id", StringUtil.get32UUID());// 主键id
                    pdMessage.put("message_id", pdStoreV.getString("STORE_ID"));// 消息id
                    pdMessage.put("internet_id", pdStoreV.getString("INTERNET_ID"));// 网吧id
                    pdMessage.put("title", "门店计费系统过期提醒");// 标题
                    pdMessage.put("type", "pubwin");// 消息类型：pubwin表示计费系统
                    pdMessage.put("content", "您的" + store_name + "将在" + pdStoreV.getString("EXPIRATION_TIME")
                            + "过期，请尽快续费，否则部分功能将无法使用，造成不必要的困扰。");// 消息内容
                    pdMessage.put("announce_time", Tools.date2Str(new Date()));// 通知时间
                    pdMessage.put("state", "0");// 0--未读
                    messageService.save(pdMessage);

                    // 通过门店id去修改标识状态
                    PageData pdStore = new PageData();
                    pdStore.put("store_id", pdStoreV.getString("STORE_ID"));// 消息id
                    storeService.editStoreV(pdStore);
                }
            }
        }
	}

	/**
	 * 早上9点(去发送今日抵用券)
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 00 09 * * ?")
	public void Zerotask6() throws Exception {
		System.out.println("***********************每日9点定时发送抵用券*********************");
        String need_time = Tools.dateStr(new Date());
		List<PageData> list = cardService.selRush(need_time);// 获取今日需要发送的抵用券
        System.out.println("今日要发送抵用券量:" + list.size());

        for (int i = 0; i < list.size(); i++) {
            PageData pdList = list.get(i);
//            PageData pdCard = cardService.findCardAndSceneById(pdList.getString("card_id"));
			sendRushCard(pdList);
        }
	}

	public JSONObject sendRushCard(PageData pdList) throws Exception{
		String sequence = pdList.get("sequence").toString();
        sequence = (Integer.parseInt(sequence) + 1) + "";

		JSONObject obj = new JSONObject();
		obj.put("keyword1", "赠送抵用券");//服务类型
		obj.put("keyword2", "正在发放中");//服务状态
		obj.put("keyword3", pdList.getString("full_term"));//服务时间

		PageData pdData = new PageData();
		pdData.put("first_data", "亲爱的用户，您的抵用券服务持续发送中");//标题
		pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
		pdData.put("remark_data", "点击详情领取第"+sequence+"张抵用券");//备注
		pdData.put("url", PublicManagerUtil.URL2 + "wxML/goLqCard.do?CARD_ID="
				+ pdList.getString("card_id")
				+ "&openid=" + pdList.getString("open_id")
				+ "&order_id=" + pdList.getString("order_id")
				+ "&sequence="+pdList.get("sequence").toString());//跳转

		JSONObject sendResult = weixinService.sendTamplate(pdList.getString("internet_id"), pdList.getString("open_id"), "server_plan_notify", pdData);
		if (StringUtil.isNotEmpty(sendResult.getString("errcode"))) {
			if (sendResult.getString("errcode").equals("0")) {//发送成功
				PageData pdSendCard = new PageData();
				pdSendCard.put("id", pdList.getString("id"));//主键Id
				pdSendCard.put("real_time", Tools.date2Str(new Date()));//实际发送时间
				pdSendCard.put("state", "1");//发送成功
				//去改变抵用券发送状态
				cardService.editCardOpen(pdSendCard);
			}else{
				PageData pdSendCard = new PageData();
				pdSendCard.put("id", pdList.getString("id"));//主键Id
				pdSendCard.put("state", "2");//发送失败
				//去改变抵用券发送状态
				cardService.editCardOpen(pdSendCard);
			}
		}
		return sendResult;
	}

}
