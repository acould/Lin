package com.lk.service.billiCenter.userBoard.impl;

import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.*;
import com.lk.service.billiCenter.base.SWBaseService;
import com.lk.service.billiCenter.userBoard.UserBoardService;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
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
import java.util.Date;
import java.util.List;

@Service("userBoardService")
public class UserBoardServiceImpl implements UserBoardService{

	public static final Logger log = LoggerFactory.getLogger(UserBoardServiceImpl.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="callBackService")
	private CallBack callBackService;

	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;

	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	
	@Resource(name = "storeService")
	private StoreManager storeService;

	@Resource(name = "weixinService")
	private WeixinService weixinService;

	@Autowired
	private SWBaseService swBaseService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserBoardMapper.save", pd);
	}


	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserBoardMapper.findById", pd);
	}

	
	/**
	 * 查询上机信息
	 * @param store_id：是否为空
	 * @param param：json参数
	 * @return 返回错误码或者正确的数据（见文档）
	 * @throws Exception
	 */
	public JSONObject userBoard(String store_id, JSONObject param) throws Exception {
		JSONObject jsonResult = new JSONObject();
		
		//判断store_id是否为空
		if(StringUtil.isEmpty(store_id)){
			return JSONObject.fromObject(ErrUtil.ERR_10002);
		}
		//判断store_id是否有效
		if(!storeService.checkStoreV(store_id)){
			return JSONObject.fromObject(ErrUtil.ERR_10001);
		}
		
		//判断业务请求参数是否正常
		jsonResult = userInfoService.check(param);
		if(!jsonResult.getString("errcode").equals("0")){
			return jsonResult;
		}
		
		
		System.out.println("请求数据==="+param.toString());
		//封装Message
		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.MSG_USERINFO);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
		
		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());
		
		
		//保存请求数据
		PageData pd = new PageData();
		pd.put("json_data", param.toString());
		pd.put("request_id", StringUtil.get32UUID());
		pd.put("msg_type", MsgUtil.MSG_USERBOARD);
		pd.put("msg_id", msg.getFlag());
		pd.put("create_time", Tools2.date2Str(new Date()));
//		requestService.save(pd);
		
		//保存响应数据
		PageData pdResponse = new PageData();
		pdResponse.put("response_id", StringUtil.get32UUID());
		pdResponse.put("request_id", pd.getString("request_id"));
		pdResponse.put("json_result", result);
		pdResponse.put("create_time", Tools2.date2Str(new Date()));
        if(StringUtil.isNotEmpty(result)){
    		JSONObject json2 = JSONObject.fromObject(result);
    		if(StringUtil.isNotEmpty(json2)){
    			pdResponse.put("errcode", json2.getJSONObject("head").getString("errcode"));
    			pdResponse.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			//保存响应查询上机后的结果
    			if(json2.getJSONObject("head").getString("errcode").equals("0")){
    				if(json2.containsKey("body")){
    					if(json2.get("body") instanceof JSONObject){
        					JSONArray arr = new JSONArray();
        					arr.add(json2.getJSONObject("body"));
        					json2.put("body", arr);
        				}
    					JSONArray body2 = json2.getJSONArray("body");
        				for(int i=0;i<body2.size();i++){
        					JSONObject body = body2.getJSONObject(i);
        					
        					//查询该条上机信息是否存在，不存在则保存
        					PageData pdBoard = new PageData();
            				pdBoard.put("guid", body.get("guid"));
            				pdBoard = findByGuid(pdBoard);
            				if(StringUtil.isEmpty(pdBoard)){
            					pdBoard = new PageData();
        	    				pdBoard.put("board_id", StringUtil.get32UUID());
        	    				pdBoard.put("store_id", store_id);
        	    				
        	    				body2.getJSONObject(i).put("board_id", pdBoard.getString("board_id"));
        	    				body2.getJSONObject(i).put("store_id", pdBoard.getString("store_id"));
        	    				
        	    				pdBoard.put("computer_name", body.get("computer_name"));
        	    				pdBoard.put("computer_ip", body.get("computer_ip"));
        	    				pdBoard.put("computer_mac", body.get("computer_mac"));
        	    				pdBoard.put("card_id", body.get("card_id"));
        	    				pdBoard.put("id_card", body.get("id_card"));
        	    				pdBoard.put("id_type", body.get("id_type"));
        	    				pdBoard.put("user_name", body.get("user_name"));
        	    				pdBoard.put("user_level", body.get("user_level"));
        	    				pdBoard.put("remain_fee", body.get("remain_fee"));
        	    				pdBoard.put("remain_reward", body.get("remain_reward"));
        	    				pdBoard.put("consume_fee", body.get("consume_fee"));
        	    				pdBoard.put("rate_fee", body.get("rate_fee"));
        	    				pdBoard.put("remain_min", body.get("remain_min"));
        	    				pdBoard.put("guid", body.get("guid"));
        	    				pdBoard.put("up_timestamp", body.get("up_timestamp"));
								String up_time = body.get("up_time").toString().replaceAll("/", "-");//接口可能返回（2018-10-18 下午 04:57:57）2019-4-6 星期六   5:30:48
								up_time = Tools.parseTime(up_time);
								up_time =  Tools2.date2Str(Tools2.str2Date(up_time));

        	    				pdBoard.put("up_time", up_time);
        	    				this.save(pdBoard);
            				}else{
            					body2.getJSONObject(i).put("board_id", pdBoard.getString("board_id"));
            					body2.getJSONObject(i).put("store_id", pdBoard.getString("store_id"));
            				}
        				}
        				jsonResult.put("body", body2);
        				jsonResult.put("errcode", "0");
        				jsonResult.put("errmsg", "query user board success");
    				}else{
    					//如果通过证件号类型查找不到信息，则通过卡号的方式查询，0表示卡号；1表示证件号
    					if(param.getString("field_type").equals("1")) {
    						Business.businesses.remove(msg.getFlag());
    						param.put("field_type", "0");
    						jsonResult = this.userBoard(store_id, param);
    						return jsonResult;
    					}else {
    						jsonResult = JSONObject.fromObject(ErrUtil.ERR_1);
    					}
    				}
    				
    			}else{
    				jsonResult.put("errcode", json2.getJSONObject("head").getString("errcode"));
    				jsonResult.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			}
    		}
//    		responseService.save(pdResponse);
        }else{
        	pdResponse.put("errcode", "10037");
			pdResponse.put("errmsg", "result is null");
//			responseService.save(pdResponse);
			
			jsonResult = JSONObject.fromObject(ErrUtil.ERR_10037);
        }
        Business.businesses.remove(msg.getFlag());
		
		return jsonResult;
	}


	/**
	 * 根据上网唯一标识guid查询上下机记录
	 * pd中包含guid
	 */
	public PageData findByGuid(PageData pd) throws Exception {
		return (PageData)dao.findForObject("UserBoardMapper.findByGuid", pd);
	}

	
	/**
	 * 发送上机模板消息
	 * @param json：接口返回数据
	 * 上机的json 中的body为JSONArray
	 * @throws Exception
	 */
	public void pushUserBoard(JSONObject json, String store_id) throws Exception {
		log.info("推送上机信息store_id== " + store_id);

		String card_id = json.getString("card_id");
		PageData pdStore = new PageData();
		pdStore.put("STORE_ID", store_id);
		pdStore = storeService.findById(pdStore);
		String internet_id = pdStore.getString("INTERNET_ID");
		String store_name = pdStore.getString("STORE_NAME");
		
		//保存上机信息, 注意返回的body是个数组
		if(json.getJSONObject("head").getString("errcode").equals("0") && StringUtil.isNotEmpty(json.getJSONArray("body"))){
			JSONObject bodyBoard = json.getJSONArray("body").getJSONObject(0);
			
			PageData pdBoard = new PageData();
			pdBoard.put("guid", bodyBoard.get("guid"));
			pdBoard = this.findByGuid(pdBoard);
			if(StringUtil.isEmpty(pdBoard)){
				pdBoard = new PageData();
				pdBoard.put("board_id", StringUtil.get32UUID());
				pdBoard.put("store_id", store_id);
				
				pdBoard.put("computer_name", bodyBoard.get("computer_name"));
				pdBoard.put("computer_ip", bodyBoard.get("computer_ip"));
				pdBoard.put("computer_mac", bodyBoard.get("computer_mac"));
				pdBoard.put("card_id", bodyBoard.get("card_id"));
				pdBoard.put("id_card", bodyBoard.get("id_card"));
				pdBoard.put("id_type", bodyBoard.get("id_type"));
				pdBoard.put("user_name", bodyBoard.get("user_name"));
				pdBoard.put("user_level", bodyBoard.get("user_level"));
				pdBoard.put("remain_fee", bodyBoard.get("remain_fee"));
				pdBoard.put("remain_reward", bodyBoard.get("remain_reward"));
				pdBoard.put("consume_fee", bodyBoard.get("consume_fee"));
				pdBoard.put("rate_fee", bodyBoard.get("rate_fee"));
				pdBoard.put("remain_min", bodyBoard.get("remain_min"));
				pdBoard.put("guid", bodyBoard.get("guid"));
				pdBoard.put("up_timestamp", bodyBoard.get("up_timestamp"));
				String up_time = bodyBoard.get("up_time").toString().replaceAll("/", "-");//接口可能返回（2018-10-18 下午 04:57:57）
				up_time = Tools.parseTime(up_time);
				up_time =  Tools2.date2Str(Tools2.str2Date(up_time));

				pdBoard.put("up_time", up_time);
				this.save(pdBoard);
			}



			//推送模板消息
			PageData pdBind = new PageData();
			pdBind.put("STORE_ID", store_id);
			pdBind.put("CARDED", pdBoard.getString("id_card"));
			pdBind = bunduserService.findByCard(pdBind);
			//绑定后的会员才有推送信息
			if(StringUtil.isNotEmpty(pdBind)){



				JSONObject obj = new JSONObject();
				obj.put("keyword1", store_name);// 网吧门店
				obj.put("keyword2", pdBoard.get("card_id"));// 会员卡号
				obj.put("keyword3", pdBoard.get("computer_name"));// 机器编号（电脑名称）
				obj.put("keyword4", pdBoard.get("remain_fee") + "");// 网费余额
				//模板推送（点击跳转到微信的上网记录中）
				String pre = PublicManagerUtil.URL1;
				String url = pre + "myMember/internetRecord?user_id="+pdBind.getString("USER_ID");

				PageData pdData = new PageData();
				pdData.put("first_data", "你好，你的网吧会员卡已上机");//标题
				pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
				pdData.put("remark_data", "本次上机已产生最低消费，消费单价：" + pdBoard.get("rate_fee") + "元/小时。感谢您的使用。");//备注
				pdData.put("url", url);//跳转

				PageData pdWx = wechatuserService.findByUserId(pdBind);
				JSONObject sendResult = weixinService.sendTamplate(internet_id, pdWx.getString("OPEN_ID"), "user_up", pdData);


				//更新用户余额
				pdBind.put("OVERAGE", pdBoard.get("remain_fee")+"");
				pdBind.put("REWARD", pdBoard.get("remain_reward")+"");
				bunduserService.edit(pdBind);

				//会员上机改变二维码状态
				PageData pdQrUser = new PageData();
				pdQrUser.put("store_id", store_id);
				pdQrUser.put("user_id", pdBind.getString("USER_ID"));
				pdQrUser = (PageData) dao.findForObject("InternetQrcodeMapper.findByCondition", pdQrUser);

				if (StringUtil.isEmpty(pdQrUser)){
					log.info("门店store_id" + store_id + "，卡号card_id : " + card_id + "，上机成功");
					//修改二维码
					PageData pdCom = new PageData();
					pdCom.put("store_id", store_id);
					pdCom.put("computer_name", bodyBoard.get("computer_name"));
					pdCom.put("state", 1);//使用中
					pdCom.put("user_id", pdBind.getString("USER_ID"));//使用中
					dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);
				}else{
					//用户已经上机了，但不是一台机器
					if(!pdQrUser.getString("computer_name").equals(bodyBoard.get("computer_name").toString())){
						log.info("门店store_id" + store_id + "，卡号card_id : " + card_id + "，换机成功");

						PageData pdCom = new PageData();
						pdCom.put("store_id", store_id);
						pdCom.put("computer_name", pdQrUser.getString("computer_name"));
						pdCom.put("state", 2);//离线
						pdCom.put("user_id", "");
						dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom);

						//修改二维码
						PageData pdCom2 = new PageData();
						pdCom2.put("store_id", store_id);
						pdCom2.put("computer_name", bodyBoard.get("computer_name"));
						pdCom2.put("state", 1);//使用中
						pdCom2.put("user_id", pdBind.getString("USER_ID"));//使用中
						dao.update("InternetQrcodeMapper.editByStoreAndCom", pdCom2);
					}
				}


			}
		}
		
		
	}

	@Override
	public Message2 wakeToPower(String store_id, JSONObject param) throws Exception {
        Message2 m2 = swBaseService.sendToSW(store_id, param, MsgUtil.WAKE_POWER);

        if(m2.getErrcode() != 0){
            return m2;
        }
        System.out.println("发送网络唤醒成功");
        return m2;
	}

	/**
	 *
	 * @param store_id
	 * @param param computer_name（电脑名称） opter_type 操作类型1表示开机，其他无效
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message2 swWakeToPower(String store_id, JSONObject param) throws Exception {
		param.put("opter_type", 1);
		Message2 m2 = swBaseService.sendToSW(store_id, param, MsgUtil.SW_WAKE_POWER);

		if(m2.getErrcode() != 0){
			return m2;
		}
		System.out.println("通知客户端开机");
		return m2;
	}



	@Override
	public Message2 sendSelUserUP(String store_id, SWQuery query) throws Exception {
		return swBaseService.sendToSW(store_id, query, MsgUtil.MSG_USERBOARD);
	}

	@Override
	public Message2 getSelUserUPByFlag(String msgFlag) throws Exception {
		List<SWUserBoard> list = new ArrayList();

		Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
		if(m2.getErrcode() != 0){
			return m2;
		}

		if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
			JSONArray arr = (JSONArray) m2.getData().get("arr");
			SWUserBoard swUserBoard = (SWUserBoard) JSONObject.toBean(arr.getJSONObject(0), SWUserBoard.class);
			list.add(swUserBoard);
		}
		return Message2.ok().addData("list", list);
	}

	@Override
	public Message2 getUserBoard(String store_id, String card_id, String computer_name) throws Exception{

        SWQuery query = new SWQuery();
	    if(card_id != null){
            query.setField_type(1);//0卡号；1证件号
            query.setField_value(card_id);
        }
	    if(computer_name != null){
            query.setField_type(2);//2电脑名称
            query.setField_value(computer_name);
        }

        Message2 m2 = this.sendSelUserUP(store_id, query);
        if(m2.getErrcode() != 0){
            return m2;
        }

        Message2 m22 = this.getSelUserUPByFlag(m2.getData().get("msg_flag").toString());
        if(m22.getErrcode() != 0){
            return m22;
        }

		SWUserBoard swUserBoard = new SWUserBoard();
        if(((List<SWUserBoard>) m22.getData().get("list")).size() > 0){
			swUserBoard = ((List<SWUserBoard>) m22.getData().get("list")).get(0);
		}

        return Message2.ok().addData("SWUserBoard", swUserBoard);

	}

    @Override
    public Message2 userUp(String store_id, String card_id, String computer_name, String psd)throws Exception {

        SWUserUp swUserUp = new SWUserUp();
        swUserUp.setCard_id(card_id);
        swUserUp.setComputer_name(computer_name);
        swUserUp.setUser_password(psd);

        Message2 m2 = swBaseService.sendToSW(store_id, swUserUp, MsgUtil.USER_UP);
        if(m2.getErrcode() != 0){
            return m2;
        }

        Message2 m22 = swBaseService.getMsgFromCache(m2.getData().get("msg_flag").toString());
        if(m22.getErrcode() != 0){
            return m22;
        }

        return m22;
    }

    @Override
    public Message2 userUpChange(String store_id,String card_id, String from_computer, String to_computer) throws Exception{

        SWUserUpChange swUserUpChange = new SWUserUpChange();
        swUserUpChange.setCard_id(card_id);
        swUserUpChange.setFrom_computer(from_computer);
        swUserUpChange.setTo_computer(to_computer);

        Message2 m2 = swBaseService.sendToSW(store_id, swUserUpChange, MsgUtil.USER_UP_CHANGE);
        if(m2.getErrcode() != 0){
            return m2;
        }

        Message2 m22 = swBaseService.getMsgFromCache(m2.getData().get("msg_flag").toString());
        if(m22.getErrcode() != 0){
            return m22;
        }

        return m22;
    }

	@Override
	public Message2 setQrParam(String store_id, SWQrParam swQrParam) throws Exception{

		Message2 m2 = swBaseService.sendToSW(store_id, swQrParam, MsgUtil.SET_QR_PARAM);
		if(m2.getErrcode() != 0){
			return m2;
		}

		Message2 m22 = swBaseService.getMsgFromCache(m2.getData().get("msg_flag").toString());
		if(m22.getErrcode() != 0){
			return m22;
		}

		return m22;
	}
}

