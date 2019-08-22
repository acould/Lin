package com.lk.service.billiCenter.userFlow.impl;

import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.Page;
import com.lk.entity.billecenter.SWQueryFlow;
import com.lk.entity.billecenter.SWUser;
import com.lk.entity.billecenter.SWUserFlow;
import com.lk.service.billiCenter.base.SWBaseService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.impl.CardService;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.util.*;
import com.lk.wechat.util.TemplateMsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userFlowService")
public class UserFlowServiceImpl implements UserFlowService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="callBackService")
	private CallBack callBackService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "generateOrderService")
	private GenerateOrderService generateOrderService;
	@Resource(name = "cardService")
	private CardService cardService;

	@Resource(name = "weixinService")
	private WeixinService weixinService;

    @Autowired
    private SWBaseService swBaseService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserFlowMapper.save", pd);
	}
	
	/**修改加奖励数据
	 * @param pd
	 * @throws Exception
	 */
	public void editBycardIdTime(PageData pd)throws Exception{
		dao.update("UserFlowMapper.editBycardIdTime", pd);
	}
	/**查询加奖励或本金
	 * @param pd
	 * @throws Exception
	 */
	public String findBycardIdTime(PageData pd)throws Exception{
		return (String)dao.findForObject("UserFlowMapper.findBycardIdTime", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UserFlowMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UserFlowMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserFlowMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserFlowMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserFlowMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserFlowMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 获取用户的流水
	 * pd包含card_id和store_id，begin_time，end_time，filter_type，
	 */
	public List<PageData> userFlowLocal(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserFlowMapper.userFlowLocal", pd);
	}
	
	/**
	 * 获取用户的流水
	 * pd包含card_id和store_id，order_id2，flow_time2，flow_time2，后三个参数为筛选条件
	 */
	public List<PageData> dataUserFlow(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("UserFlowMapper.dataUserFlow", pd);
	}
	
	/**
	 * 获取某个用户在某个门店的流水
	 * pd包含card_id和store_id
	 */
	public List<PageData> userStoreFlow(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserFlowMapper.userStoreFlow", pd);
	}
	/**
	 * 获取消费次数/消费总金额
	 */
	public PageData findByCardId(PageData pageData) throws Exception {
		return (PageData) dao.findForObject("UserFlowMapper.findByCardId", pageData);
	}

	
	/**
	 * 查询流水
	 * @param param
		 * ip：ip是否为空，格式是否满足要求，是否在可访问列表里
		 *  msg_from：是否为空
		 *  store_id：是否为空
		 *  card_id：是否为空
		 *  filter_type：是否为空，int类型
		 *  begin_time：是否为空，yyyy-MM-dd HH:mm:ss类型的字符串
		 *  end_time：是否为空，yyyy-MM-dd HH:mm:ss类型的字符串
		 *  flag：sel表示仅查询，timer表示查询并存储
		 * @return 返回错误码或者正确的数据，其中body是JSONArray类型
	 * @throws Exception
	 */
	public JSONObject userFlow(String store_id, JSONObject param) throws Exception{
		JSONObject json = new JSONObject();
		
		//判断store_id是否为空
		if(StringUtil.isEmpty(store_id)){
			return JSONObject.fromObject(ErrUtil.ERR_10002);
		}
		//判断store_id是否有效
		System.out.println(store_id+"判断store_id是否有效"+!storeService.checkStoreV(store_id));
		if(!storeService.checkStoreV(store_id)){
			return JSONObject.fromObject(ErrUtil.ERR_10001);
		}	
		//判断业务请求参数是否正常
		System.out.println("判断业务请求参数是否正常"+check(param));
		json = check(param);
		System.out.println("判断业务请求参数是否正常"+!json.getString("errcode").equals("0"));
		if(!json.getString("errcode").equals("0")){
			return json;
		}
		//将查询时间改为时间戳
		if(StringUtil.isNotEmpty(param.getString("begin_time"))){
			int begin_time = Integer.parseInt(( Tools.str2Date(param.getString("begin_time")).getTime()/1000) +"");
			param.put("begin_time", begin_time);
		}
		if(StringUtil.isNotEmpty(param.getString("end_time"))){
			int end_time3 = Integer.parseInt(( Tools.str2Date(param.getString("end_time")).getTime()/1000) +"");
			param.put("end_time", end_time3);
		}
		System.out.println("请求数据==="+param.toString());
		
		//封装Message
		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.MSG_USERFLOW);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
		
		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.MAX_TIME);
		String result = callBackService.result(msg.getFlag());
		
		//保存请求数据
		PageData pd = new PageData();
		pd.put("json_data", pd.toString());
		pd.put("request_id", StringUtil.get32UUID());
		pd.put("msg_type", MsgUtil.MSG_USERFLOW);
		pd.put("msg_id", msg.getFlag());
		pd.put("create_time", Tools.date2Str(new Date()));
//		requestService.save(pd);
		
		//保存响应数据
		PageData pdResponse = new PageData();
		pdResponse.put("response_id", StringUtil.get32UUID());
		pdResponse.put("request_id", pd.getString("request_id"));
		pdResponse.put("json_result", result);
		pdResponse.put("create_time", Tools.date2Str(new Date()));
        if(StringUtil.isNotEmpty(result)){
    		JSONObject json2 = JSONObject.fromObject(result);
    		Thread.sleep(1000);
    		if(StringUtil.isNotEmpty(json2)){
    			pdResponse.put("errcode", json2.getJSONObject("head").getString("errcode"));
    			pdResponse.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			//保存响应充值后的结果
    			if(json2.getJSONObject("head").getString("errcode").equals("0")){
    				if(json2.containsKey("body")){
    					if(json2.get("body") instanceof JSONObject){
    						JSONArray arr = new JSONArray();
    						arr.add(json2.getJSONObject("body"));
    						json2.put("body", arr);
    					}
    					JSONArray body = json2.getJSONArray("body");
    					if(!param.getString("flag").equals("sel")){
    						//表示只通过每日跑定时任务才可以保存流水信息，普通的查询不保存
    						for(int i=0;i<body.size();i++){
            					PageData pdData = new PageData();
            					pdData.put("flow_id", StringUtil.get32UUID());
            					pdData.put("store_id", store_id);
            					pdData.put("card_id", param.getString("card_id"));
            					
            					body.getJSONObject(i).put("flow_id", pdData.getString("flow_id"));
            					body.getJSONObject(i).put("store_id", pdData.getString("store_id"));
            					body.getJSONObject(i).put("card_id", pdData.getString("card_id"));
            					
            					pdData.put("flow_type", body.getJSONObject(i).get("flow_type"));
            					pdData.put("flow_desc", body.getJSONObject(i).get("flow_desc"));
            					pdData.put("amount", body.getJSONObject(i).get("amount"));
            					pdData.put("reward", body.getJSONObject(i).get("reward"));
            					//筛选需要保存的操作类型数据
            					if(body.getJSONObject(i).get("flow_desc").equals("购买商品")){

            					}else if(body.getJSONObject(i).get("flow_desc").equals("激活") || body.getJSONObject(i).get("flow_desc").equals("取消激活")){
            						continue;
            					}else if(body.getJSONObject(i).get("flow_desc").equals("减积分") || body.getJSONObject(i).get("flow_desc").equals("加积分")){
            						continue;
            					}else if(body.getJSONObject(i).get("flow_desc").equals("减奖励")){
            						pdData.put("reward", body.getJSONObject(i).get("amount"));
            						pdData.put("amount", 0);
            					}else if(body.getJSONObject(i).get("flow_desc").equals("退奖励")){
            						pdData.put("reward", body.getJSONObject(i).get("amount"));
            						pdData.put("amount", 0);
            					}
								//消费 ， 上机费， 第三方消费 金额置负
            					if(body.getJSONObject(i).get("flow_type").toString().equals("4")
									|| body.getJSONObject(i).get("flow_type").toString().equals("2")
										|| body.getJSONObject(i).get("flow_type").toString().equals("32")){
									if(Double.parseDouble(body.getJSONObject(i).get("amount")+"") > 0
										|| Double.parseDouble(body.getJSONObject(i).get("reward")+"") > 0) {
										pdData.put("amount", 0 - Double.parseDouble(body.getJSONObject(i).get("amount") + ""));
										pdData.put("reward", 0 - Double.parseDouble(body.getJSONObject(i).get("reward") + ""));
									}
								}

            					if("揽客优惠券".equals(body.getJSONObject(i).get("memo"))){
            						pdData.put("reward", body.getJSONObject(i).get("reward"));
            						pdData.put("amount", 0);
            					}else if("揽客充值".equals(body.getJSONObject(i).get("memo"))){
            						PageData PD=new PageData();
            						PD.put("merOrderId", body.getJSONObject(i).get("order_id"));
            						PageData findByIdormerOrderId = generateOrderService.findByIdormerOrderId(PD);
            						if(StringUtil.isNotEmpty(findByIdormerOrderId)){
            							pdData.put("reward", findByIdormerOrderId.get("reward_balance"));
            							pdData.put("amount", findByIdormerOrderId.get("rincipal_balance"));
            						}
            					}
            					String flow_time = Tools.date2Str(Tools.str2Date(body.getJSONObject(i).get("flow_time").toString().replaceAll("/", "-")));
            					pdData.put("flow_time", flow_time);
            					pdData.put("flow_timestamp", body.getJSONObject(i).get("flow_timestamp"));
            					pdData.put("pay_type", body.getJSONObject(i).get("pay_type"));
            					pdData.put("order_id", body.getJSONObject(i).get("order_id"));
            					pdData.put("order_from", body.getJSONObject(i).get("order_from"));
            					pdData.put("memo", body.getJSONObject(i).get("memo"));
            					if(body.getJSONObject(i).get("flow_desc").equals("加本金") || body.getJSONObject(i).get("flow_desc").equals("加奖励")){
            						PageData pdsc = new PageData();
            						pdsc.put("flow_time", flow_time);
            						pdsc.put("card_id", param.getString("card_id"));
            						String flow_id=this.findBycardIdTime(pdsc);
            						if(StringUtil.isNotEmpty(flow_id)){
            							if(body.getJSONObject(i).get("flow_desc").equals("加本金")){
            								pdsc.put("amount", body.getJSONObject(i).get("amount"));
            							}else if(body.getJSONObject(i).get("flow_desc").equals("加奖励")){
            								pdsc.put("reward", body.getJSONObject(i).get("amount"));
            							}
            							pdsc.put("flow_id", flow_id);
            							this.edit(pdsc);
            						}else{
            							//没有记录
            							if(body.getJSONObject(i).get("flow_desc").equals("加奖励")){
            								pdData.put("reward", body.getJSONObject(i).get("amount"));
            							}
            							this.save(pdData);
            						}
            					}else{
            						this.save(pdData);
            					}
            					
            				}
    					}
        				
        				json.put("body", body);
        				json.put("errcode", "0");
        				json.put("errmsg", "query user flow success");
        				
    				}else{
        				json = JSONObject.fromObject(ErrUtil.ERR_1);
    				}
    				
    			}else{
    				json.put("errcode", json2.getJSONObject("head").getString("errcode"));
    				json.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			}
    			
    		}
//    		responseService.save(pdResponse);
        }else{
        	pdResponse.put("errcode", "10037");
			pdResponse.put("errmsg", "result is null");
//			responseService.save(pdResponse);
			
			json = JSONObject.fromObject(ErrUtil.ERR_10037);
        }
        Business.businesses.remove(msg.getFlag());
		return json;
	}

	/**
	 * 检查参数是否异常
	 * @param param
	 * @return
	 */
	public JSONObject check(JSONObject param){
		
		if(StringUtil.isEmpty(param.getString("card_id"))){
			return JSONObject.fromObject(ErrUtil.ERR_10050);
		}
		if(StringUtil.isEmpty(param.get("filter_type"))){
			return JSONObject.fromObject(ErrUtil.ERR_10051);
		}
		
		if(StringUtil.isEmpty(param.get("begin_time"))){
			return JSONObject.fromObject(ErrUtil.ERR_10052);
		}
		if(StringUtil.isEmpty(param.get("end_time"))){
			return JSONObject.fromObject(ErrUtil.ERR_10053);
		}
		
		
		try{
			Integer.parseInt(param.get("filter_type")+"");
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10051);
		}
		try{
			Tools.str2Date(param.getString("begin_time"));
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10052);
		}
		try{
			Tools.str2Date(param.getString("end_time"));
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10053);
		}
		
		return JSONObject.fromObject(ErrUtil.ERR_0);
	}

	
	/**
	 * 优先从本地数据库中查询流水信息
	 * @param param
	 *  ip：ip是否为空，格式是否满足要求，是否在可访问列表里
	 *  msg_from：是否为空
	 *  store_id：是否为空
	 *  card_id：是否为空
	 *  filter_type：是否为空，int类型
	 *  begin_time：是否为空，yyyy-MM-dd HH:mm:ss类型的字符串
	 *  end_time：是否为空，yyyy-MM-dd HH:mm:ss类型的字符串
	 *  flag：sel表示仅查询，timer表示查询并存储
	 * @return 返回错误码或者正确的数据，其中body是JSONArray类型
	 * @throws Exception
	 */
	public JSONObject userFlowLocalToLanker(String store_id, JSONObject param) throws Exception {
		JSONObject json = new JSONObject();
		List<PageData> flowList = new ArrayList<PageData>();
		PageData pd =new PageData();
		pd.put("card_id", param.getString("card_id"));
		pd.put("store_id", store_id);
		pd.put("begin_time", param.getString("begin_time"));
		pd.put("end_time", param.getString("end_time"));
		pd.put("filter_type", param.getString("filter_type"));
		//数据库中的用户流水
		flowList = userFlowLocal(pd);
		System.out.println("更新前=====flowList的大小是====="+flowList.size());
		//判断查询的时间
		long last = 0;
		if(StringUtil.isEmpty(flowList)){
			last = Tools.str2Date(pd.getString("begin_time")).getTime();	
		}else{
            last = Tools.str2Date(flowList.get(0).getString("flow_time")).getTime();
            //加一秒钟
            String begin_time = Tools.formatTime((Tools.str2Date(flowList.get(0).getString("flow_time")).getTime() + 1000)/1000+"");
            param.put("begin_time", begin_time);
        }
		long end_time = Tools.str2Date(pd.getString("end_time")).getTime();
		
		//如果查询的结束时间 > 数据库中最新的流水时间，则调接口查数据（时间区间为数据中的最新时间--查询的结束时间），并保存
		//否则，直接返回数据库中的数据
		System.out.println(end_time > last);
		if(end_time > last){
			param.put("flag", "timer");
			userFlow(store_id,param); //查询流水
		}
		
		pd.put("filter_type2", param.get("filter_type2"));
		pd.put("flow_time2", param.get("flow_time2"));
		pd.put("order_id2", param.get("order_id2"));
		
		flowList = dataUserFlow(pd);
		System.out.println("更新后=====flowList的大小是====="+flowList.size());
		
		
		json.put("errcode", "0");
		json.put("errmsg", "query flow success");
		json.put("body", flowList);
		System.out.println(json);
		return json;
	}

	
	
	/**
	 * 发送余额变动模板
	 * @param json：接口返回数据
	 * @param store_id：门店id
	 * json中的body为JSONArray
	 * @throws Exception
	 */
	public void pushMoneyChange(JSONObject json, String store_id) throws Exception {
		
		PageData pdStore = new PageData();
		PageData pdBind = new PageData();
		pdStore.put("STORE_ID", store_id);
		pdStore = storeService.findById(pdStore);              //查看门店信息
		String internet_id = pdStore.getString("INTERNET_ID"); //网吧id
		String card_id = json.getString("card_id");
		pdStore.put("CARDED", card_id);
		pdBind = bunduserService.findByCard(pdStore);
		//绑定后的会员才有推送信息
		if(StringUtil.isNotEmpty(pdBind) && json.containsKey("body")
                && StringUtil.isNotEmpty(json.getJSONArray("body"))){

			JSONArray bodyFlow = json.getJSONArray("body");
			double overage = Double.parseDouble(pdBind.get("OVERAGE")+""); //卡余额(包含奖励金额)
			double reward = Double.parseDouble(pdBind.get("REWARD")+"");   //奖励金额
			for(int i=0;i<bodyFlow.size();i++){
				// 组装模板内容
				JSONObject body = bodyFlow.getJSONObject(i);

				double amount = Double.parseDouble(body.get("amount")+"");
				if(body.get("flow_desc").equals("购买商品") || body.get("flow_desc").equals("上机费") || body.get("flow_desc").toString().contains("消费模式转换") ){
					//将购买商品的置为负
					if(amount > 0){
                        amount = 0 - amount;
					}
				}
				overage += amount;
				pdBind.put("OVERAGE", overage+"");
				if(body.get("flow_desc").equals("加奖励") || body.get("flow_desc").equals("减奖励") || body.get("flow_desc").equals("退奖励")){
					reward += amount;
					pdBind.put("REWARD", reward+"");
				}


				PageData pdWx = wechatuserService.findByUserId(pdBind);
				sendMoneyChangeTemp(internet_id, pdWx.getString("OPEN_ID"), body.get("flow_time").toString(), amount+"", body.get("flow_desc").toString(), overage+"");

				//更新用户余额
				bunduserService.edit(pdBind);
			}
		}
	}

	//发送资金变动模板消息
	@Override
	public JSONObject sendMoneyChangeTemp(String internet_id, String open_id, String k1, String k2, String k3, String k4) throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("keyword1", k1);// 交易日期
		obj.put("keyword2", k2);// 交易金额
		obj.put("keyword3", k3);// 交易类型
		obj.put("keyword4", k4);// 卡内余额

		PageData pdData = new PageData();
		pdData.put("first_data", "您的会员卡账户发生如下交易：");//标题
		pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
		pdData.put("remark_data",  "感谢您的使用。");//备注

		//推送模板消息(账户资金变动提醒)
		JSONObject sendResult = weixinService.sendTamplate(internet_id, open_id, "money_change", pdData);
		return sendResult;
	}

	

	@Override
	public JSONObject request(String store_id, JSONObject param) throws Exception {
			JSONObject json = new JSONObject();
			
			//判断store_id是否为空
			if(StringUtil.isEmpty(store_id)){
				return JSONObject.fromObject(ErrUtil.ERR_10002);
			}
			//判断store_id是否有效
			if(!storeService.checkStoreV(store_id)){
				return JSONObject.fromObject(ErrUtil.ERR_10001);
			}	
			
			//判断业务请求参数是否正常
			json = check(param);
			if(!json.getString("errcode").equals("0")){
				return json;
			}
			
			//将查询时间改为时间戳
			if(StringUtil.isNotEmpty(param.getString("begin_time"))){
				int begin_time = Integer.parseInt(( Tools.str2Date(param.getString("begin_time")).getTime()/1000) +"");
				param.put("begin_time", begin_time);
			}
			if(StringUtil.isNotEmpty(param.getString("end_time"))){
				int end_time3 = Integer.parseInt(( Tools.str2Date(param.getString("end_time")).getTime()/1000) +"");
				param.put("end_time", end_time3);
			}
			
			System.out.println("请求数据==="+param.toString());
			
			//封装Message
			Message msg = new Message();
			msg.setBarId(store_id);
			msg.setType(MsgUtil.MSG_USERFLOW);
			msg.setFlag(Tools2.getCenterMsgId2());
			msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
			
			// 通过store_id获取具体需要通讯的客户端连接
			ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
			Business.exc(msg.getFlag(), MsgUtil.MAX_TIME);
			String result = callBackService.result(msg.getFlag());
			JSONObject results = JSONObject.fromObject(result);
		return results;
	}




    /*********************************** 新的查询方式******************************************/

    /**
     * 发送流水接口
     * @param store_id
     * @param queryFlow
     * @return
     * @throws Exception
     */
    @Override
    public Message2 sendSelUserFlow(String store_id, SWQueryFlow queryFlow) throws Exception {
        return swBaseService.sendToSW(store_id, queryFlow, MsgUtil.MSG_USERFLOW);
    }

    /**
     * 接收流水接口数据
     * @param msgFlag
     * @return
     * @throws Exception
     */
    @Override
    public Message2 getSelSWFlowByFlag(String msgFlag) throws Exception {

        Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
        if(m2.getErrcode() != 0){
            return m2;
        }

        List<SWUserFlow> list = new ArrayList();
        if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
            JSONArray arr = (JSONArray) m2.getData().get("arr");
            list = arr;
        }
        return Message2.ok().addData("list", list);
    }


    /**
     * 获取某门店，某会员，在某个时间段的流水数据
     * @param store_id
     * @param card_id
     * @param filter_type
     * @param begin_timestamp 时间戳
     * @param end_timestamp 时间戳
     * @return
     * @throws Exception
     */
	@Override
	public Message2 getUserFlow(String store_id, String card_id, int filter_type, int begin_timestamp, int end_timestamp) throws Exception{

        SWQueryFlow swQueryFlow = new SWQueryFlow();
        swQueryFlow.setCard_id(card_id);
        swQueryFlow.setFilter_type(filter_type);
        swQueryFlow.setBegin_time(begin_timestamp);
        swQueryFlow.setEnd_time(end_timestamp);

        Message2 m2 = this.sendSelUserFlow(store_id, swQueryFlow);
		if(m2.getErrcode() != 0){
			return m2;
		}

        Message2 m22 = this.getSelSWFlowByFlag(m2.getData().get("msg_flag").toString());
		if(m22.getErrcode() != 0){
			return m22;
		}

		return m22;
	}


}

