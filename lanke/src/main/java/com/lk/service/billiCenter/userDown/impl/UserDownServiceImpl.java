package com.lk.service.billiCenter.userDown.impl;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.Page;
import com.lk.entity.billecenter.SWQuery;
import com.lk.entity.billecenter.SWUserDownSel;
import com.lk.entity.billecenter.SWUserDown2;
import com.lk.service.billiCenter.base.SWBaseService;
import com.lk.service.billiCenter.userDown.UserDownService;
import com.lk.service.hd.CallBack;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.tb.tbMarketingContext.MarkingContextService;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.*;
import com.lk.wechat.util.TemplateMsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userDownService")
public class UserDownServiceImpl implements UserDownService{

	public static final Logger log = LoggerFactory.getLogger(UserDownServiceImpl.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;

	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "cardService")
	private CardManager cardService;

	@Resource(name = "weixinService")
	private WeixinService weixinService;
	@Autowired
	private MarkingContextService markingContextService;

	@Autowired
	private SWBaseService swBaseService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserDownMapper.save", pd);
	}

    /**
     * 统计某用户的上机时长
     * @param store_id
     * @param card_id
     * @param begin_time
     * @param end_time
     * @return
     */
    @Override
    public PageData calUserUpTime(String store_id, String card_id
            , String begin_time, String end_time) throws Exception{
        PageData pd = new PageData();
        pd.put("store_id", store_id);
        pd.put("card_id", card_id);
        pd.put("begin_time", begin_time);
        pd.put("end_time", end_time);
        return (PageData) dao.findForObject("UserDownMapper.calUserUpTime", pd);
    }

    @Override
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>) dao.findForList("UserDownMapper.datalist", page);
	}

	
	/**
	 * 根据上网唯一标识guid查询上下机记录
	 * pd中包含guid
	 */
	public PageData findByGuid(PageData pd) throws Exception {
		return (PageData)dao.findForObject("UserDownMapper.findByGuid", pd);
	}
	

	/**
	 * 发送下机模板消息
	 * @param json：接口返回数据
	 * 下机的json 中的body为JSONObject
	 * @throws Exception
	 */
	public void pushUserDown(JSONObject json, String store_id) throws Exception {
		log.info("推送下机信息store_id== " + store_id);

		String card_id = json.getString("card_id");
		PageData pdStore = new PageData();
		pdStore.put("STORE_ID", store_id);
		pdStore = storeService.findById(pdStore);              //查看门店信息
		String internet_id = pdStore.getString("INTERNET_ID"); //网吧id
		String store_name = pdStore.getString("STORE_NAME");   //门店名称
		
		//保存下机信息
		if(json.getJSONObject("head").getString("errcode").equals("0") && StringUtil.isNotEmpty(json.getJSONObject("body"))){
			JSONObject bodyBoard = json.getJSONObject("body");
			
			PageData pdDown = new PageData();
			pdDown.put("guid", bodyBoard.get("guid"));
			pdDown = this.findByGuid(pdDown);
			if(StringUtil.isEmpty(pdDown)){
				pdDown = new PageData();
				pdDown.put("down_id", StringUtil.get32UUID());
				pdDown.put("store_id", store_id);
				
				pdDown.put("computer_name", bodyBoard.get("computer_name"));
				pdDown.put("card_id", bodyBoard.get("card_id"));
				pdDown.put("member_level", bodyBoard.get("member_level"));
				pdDown.put("id_card", bodyBoard.get("id_card"));
				pdDown.put("id_type", bodyBoard.get("id_type"));
				pdDown.put("user_name", bodyBoard.get("user_name"));
				
				pdDown.put("pay_type", bodyBoard.get("pay_type"));
				pdDown.put("card_remain", bodyBoard.get("card_remain"));
				pdDown.put("consume_fee", bodyBoard.get("consume_fee"));
				pdDown.put("up_duration", bodyBoard.get("up_duration"));
				pdDown.put("up_timestamp", bodyBoard.get("up_timestamp"));
				pdDown.put("down_timestamp", bodyBoard.get("down_timestamp"));
				String up_time = bodyBoard.get("up_time").toString().replaceAll("/", "-");//接口可能返回（2018-10-18 下午 04:57:57）2019-4-6 星期六   5:30:48
				up_time = Tools.parseTime(up_time);
				up_time =  Tools2.date2Str(Tools2.str2Date(up_time));

			    String down_time =bodyBoard.get("down_time").toString().replaceAll("/", "-");
				down_time = Tools.parseTime(down_time);
			    down_time = Tools2.date2Str(Tools2.str2Date(down_time));
				pdDown.put("up_time", up_time);
				pdDown.put("down_time", down_time);
				pdDown.put("guid", bodyBoard.get("guid"));
				this.save(pdDown);
			}




			//推送模板消息
			PageData pdBind = new PageData();
			pdBind.put("STORE_ID", store_id);
			pdBind.put("CARDED", pdDown.getString("id_card"));
			pdBind = bunduserService.findByCard(pdBind);
			//绑定后的会员才有推送信息
			if(StringUtil.isNotEmpty(pdBind)){

				JSONObject obj = new JSONObject();
				obj.put("keyword1", store_name);// 网吧门店
				obj.put("keyword2", pdDown.get("card_id"));// 会员卡号
				obj.put("keyword3", pdDown.get("computer_name"));// 机器编号（电脑名称）
				obj.put("keyword4", pdDown.get("consume_fee") + "");// 消费金额
				obj.put("keyword5", pdDown.get("card_remain") + "");// 网费余额
				//模板推送（点击跳转到微信的上网记录中）
				String pre = PublicManagerUtil.URL1;
				String url = pre + "myMember/internetRecord?user_id="+pdBind.getString("USER_ID");

				PageData pdData = new PageData();
				pdData.put("first_data", "你好，你的网吧会员卡已下机");//标题
				pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
				pdData.put("remark_data", "欢迎下次光临！");//备注
				pdData.put("url", url);//跳转

				//发送下机模板
				PageData pdWx = wechatuserService.findByUserId(pdBind);
				 weixinService.sendTamplate(internet_id, pdWx.getString("OPEN_ID"), "user_down", pdData);


				//更新用户余额
				pdBind.put("OVERAGE", pdDown.get("card_remain")+"");
				bunduserService.edit(pdBind);

				
				//接下来去判断该门店是否有连续上网满时长券(有效的)
				PageData pd1 = new PageData();
				pd1.put("store_id", store_id); //门店id
				pd1.put("internet_id", internet_id); //网吧id
				pd1.put("id_card", bodyBoard.get("id_card"));//卡号
				pd1.put("open_id", pdWx.getString("OPEN_ID"));//open_id
				pd1.put("user_id", pdBind.getString("USER_ID")); //用户id
				pd1.put("up_duration", bodyBoard.get("up_duration"));//本次上机时常
				pd1.put("up_time", bodyBoard.get("up_time"));//上机时间
				pd1.put("down_time", bodyBoard.get("down_time"));//下机时间
				cardService.getCards(pd1);

				//会员下机改变二维码状态
				PageData pdCom = new PageData();
				pdCom.put("store_id", store_id);
				pdCom.put("computer_name", bodyBoard.get("computer_name"));
				pdCom.put("state", 2);//离线
				pdCom.put("user_id", "");
				dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);

				//推送下机活动
				PageData pdActivity = new PageData();
				pdActivity.put("send_type", "user_down");
				pdActivity.put("current_time", pdDown.getString("down_time"));
				pdActivity.put("internet_id", internet_id);
				List<PageData> list = markingContextService.findByTypeAndTime(pdActivity);
				if(list.size() > 0){
					SendTemplateThread thread = new SendTemplateThread(internet_id, pdWx.getString("OPEN_ID"), list.get(0));
					new Thread(thread).start();
				}
			}
		}
	}



	@Override
	public Message2 sendSelUserDown(String store_id, SWQuery query) throws Exception {
		return swBaseService.sendToSW(store_id, query, MsgUtil.MSG_USERDOWN);
	}

	@Override
	public Message2 getSelUserDownByFlag(String msgFlag) throws Exception {
		List<SWUserDownSel> list = new ArrayList();

		Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
		if(m2.getErrcode() != 0){
			return m2;
		}

		if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
			JSONArray arr = (JSONArray) m2.getData().get("arr");
			SWUserDownSel swUserDown = (SWUserDownSel) JSONObject.toBean(arr.getJSONObject(0), SWUserDownSel.class);
			list.add(swUserDown);
		}
		return Message2.ok().addData("list", list);
	}

	@Override
	public Message2 userDown(String store_id, String card_id) throws Exception{

		SWUserDown2 swUserDown2 = new SWUserDown2();
		swUserDown2.setCard_id(card_id);
        swUserDown2.setPay_type(2);//卡余额支付
        swUserDown2.setMemo("揽客下机");


		Message2 m2 = swBaseService.sendToSW(store_id, swUserDown2, MsgUtil.USER_DOWN);
		if(m2.getErrcode() != 0){
			return m2;
		}

		Message2 m22 = swBaseService.getMsgFromCache(m2.getData().get("msg_flag").toString());
		if(m22.getErrcode() != 0){
			return m22;
		}

		return m22;
	}

	class SendTemplateThread implements Runnable {
		private String internet_id;
		private String open_id;
		private PageData pdActivity;
		public SendTemplateThread(String internet_id, String open_id, PageData pdActivity) {
			this.internet_id = internet_id;
			this.open_id = open_id;
			this.pdActivity = pdActivity;
		}
		public void run() {
			try {
				JSONObject act = JSONObject.fromObject(pdActivity.getString("mass_context"));

				PageData pdTrade = new PageData();
				pdTrade.put("trade_title", act.getString("title"));// 活动标题
				pdTrade.put("trade_type", act.getString("activtyType"));// 活动类型
				pdTrade.put("trade_context", act.getString("activtyContent"));// 活动内容
				pdTrade.put("trade_time",act.getString("createtime"));// 活动时间
				pdTrade.put("trade_remark", act.getString("remark"));// 备注(选填) JSONObject
				JSONObject jsonData2 = TemplateMsgUtil.memberMark(pdTrade);

				String url2 = "";
				//发送模板消息前，处理模板设置(下机模板)
				//自定义类型：会员营销
				JSONObject tempJson2 = weixinService.preHandleTemplate(internet_id, "member_mark", TemplateMsgUtil.member_mark_short);

				TemplateMsgUtil.sendTemplate(tempJson2.getString("token"), open_id, tempJson2.getString("template_id"),
						url2, null, jsonData2);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



}

