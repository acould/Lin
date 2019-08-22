package com.lk.service.billiCenter.recharge.impl;

import com.lk.cache.CacheManager;
import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWRecharge;
import com.lk.entity.billecenter.SWRechargeReturn;
import com.lk.entity.billecenter.SWUserBoard;
import com.lk.service.billiCenter.base.SWBaseService;
import com.lk.service.billiCenter.recharge.RechargeService;
import com.lk.service.billiCenter.request.RequestService;
import com.lk.service.billiCenter.response.ResponseService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.store.StoreManager;
import com.lk.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="callBackService")
	private CallBack callBackService;
	@Resource(name = "requestService")
	private RequestService requestService;
	@Resource(name = "responseService")
	private ResponseService responseService;
	
	@Resource(name = "storeService")
	private StoreManager storeService;


	@Autowired
	private SWBaseService swBaseService;


	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RecordRechargeMapper.save", pd);
	}


	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RecordRechargeMapper.edit", pd);
	}


	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RecordRechargeMapper.findById", pd);
	}


	
	/**
	 * 加余额
	 *
	 * @return 返回错误码或者正确的数据（见文档）
	 * @throws Exception
	 */
	public JSONObject recharge(String store_id, JSONObject param) throws Exception {
		JSONObject jsonResult = new JSONObject();
		
		//判断store_id是否为空
		if(StringUtil.isEmpty(store_id)){
			return JSONObject.fromObject(ErrUtil.ERR_10002);
		}
		//判断store_id是否有效
		if(!storeService.checkStoreV(store_id)){
			return JSONObject.fromObject(ErrUtil.ERR_10001);
		}
		
		//判断请求参数条件
		jsonResult = check(param);
		if(!jsonResult.getString("errcode").equals("0")){
			return jsonResult;
		}
		
		
		System.out.println("请求数据==="+param.toString());
		//封装Message
		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.MSG_RECHARGE);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
		
		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.MAX_TIME);
		String result = callBackService.result(msg.getFlag());
		
		//保存请求数据
		PageData pd = new PageData();
		pd.put("json_data", param.toString());
		pd.put("request_id", StringUtil.get32UUID());
		pd.put("msg_type", MsgUtil.MSG_RECHARGE);
		pd.put("msg_id", msg.getFlag());
		pd.put("create_time", Tools.date2Str(new Date()));
		requestService.save(pd);
		//保存充值请求数据
		PageData pdRecharge = new PageData();
		pdRecharge.put("recharge_id", StringUtil.get32UUID());
		pdRecharge.put("store_id", store_id);
		pdRecharge.put("card_id", param.get("card_id"));
		pdRecharge.put("order_id", param.getString("order_id"));
		pdRecharge.put("pay_from", param.get("pay_from"));
		pdRecharge.put("memo", param.get("memo"));
		pdRecharge.put("amount", param.get("amount"));
		pdRecharge.put("reward", param.get("reward"));
		pdRecharge.put("allow_reward", param.get("allow_reward"));
		pdRecharge.put("request_time", pd.getString("create_time"));
		pdRecharge.put("request_id", pd.getString("request_id"));
		this.save(pdRecharge);
		
		//保存响应数据
		PageData pdResponse = new PageData();
		pdResponse.put("response_id", StringUtil.get32UUID());
		pdResponse.put("request_id", pd.getString("request_id"));
		pdResponse.put("json_result", result);
		pdResponse.put("create_time", Tools.date2Str(new Date()));
        if(StringUtil.isNotEmpty(result)){
    		JSONObject json2 = JSONObject.fromObject(result);
    		if(StringUtil.isNotEmpty(json2)){
    			pdResponse.put("errcode", json2.getJSONObject("head").getString("errcode"));
    			pdResponse.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			//保存响应充值后的结果
    			PageData rechargeResponse = findById(pdRecharge);
    			if(json2.getJSONObject("head").getString("errcode").equals("0")){
    				//这两个数据和文档上不一致principal，reward
    				JSONObject body = json2.getJSONObject("body");
    				rechargeResponse.put("principal_var", body.get("principal"));
    				rechargeResponse.put("reward_var", body.get("reward"));
    				rechargeResponse.put("installment_amount", body.getDouble("installment_amount"));
    				rechargeResponse.put("installment_month", body.getInt("installment_month"));
    				
    				jsonResult.put("body", body);
    				jsonResult.put("errcode", "0");
    				jsonResult.put("errmsg", "recharge success");
    			}else{
    				jsonResult.put("errcode", json2.getJSONObject("head").getString("errcode"));
    				jsonResult.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			}
    			rechargeResponse.put("response_time", pdResponse.getString("create_time"));
    			rechargeResponse.put("response_id", pdResponse.getString("response_id"));
    			this.edit(rechargeResponse);
    		}
    		responseService.save(pdResponse);
        }else{
        	pdResponse.put("errcode", "10037");
			pdResponse.put("errmsg", "result is null");
			responseService.save(pdResponse);
			
			jsonResult = JSONObject.fromObject(ErrUtil.ERR_10037);
        }
        Business.businesses.remove(msg.getFlag());
		return jsonResult;
	}


    /**
     * 新版发起充值，并接受数据
     * @param store_id
     * @param recharge
     * @return
     * @throws Exception
     */
	@Override
	public Message2 newRecharge(String store_id, SWRecharge recharge) throws Exception{

        Message2 m2 = this.sendRecharge(store_id, recharge);
        if(m2.getErrcode() != 0){
            return m2;
        }

        Message2 m22 = this.getRechargeByFlag(m2.getData().get("msg_flag").toString());
        if(m22.getErrcode() != 0){
            return m22;
        }
        SWRechargeReturn swRechargeReturn = null;
        if(((List<SWRechargeReturn>) m22.getData().get("list")).size() > 0){
            swRechargeReturn = ((List<SWRechargeReturn>) m22.getData().get("list")).get(0);
            if(swRechargeReturn != null && swRechargeReturn.getPrincipal() != null){
                return Message2.ok().addData("SWRechargeReturn", swRechargeReturn);
            }
        }

        return Message2.ok().addData("SWRechargeReturn", swRechargeReturn);
	}

	@Override
	public Message2 sendRecharge(String store_id, SWRecharge recharge) throws Exception {
        return swBaseService.sendToSW(store_id, recharge, MsgUtil.MSG_RECHARGE);
	}


	@Override
	public Message2 getRechargeByFlag(String msgFlag) throws Exception {
		List<SWRechargeReturn> list = new ArrayList();

		Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
		if(m2.getErrcode() != 0){
			return m2;
		}

		if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
			JSONArray arr = (JSONArray) m2.getData().get("arr");
			SWRechargeReturn rechargeReturn = (SWRechargeReturn) JSONObject.toBean(arr.getJSONObject(0), SWRechargeReturn.class);
			list.add(rechargeReturn);
		}
		return Message2.ok().addData("list", list);
	}


	/**
	 * 检查参数是否异常
	 *
	 * @return
	 */
	public JSONObject check(JSONObject param){
		
		if(StringUtil.isEmpty(param.get("amount"))){
			return JSONObject.fromObject(ErrUtil.ERR_10030);
		}
		if(StringUtil.isEmpty(param.get("reward"))){
			return JSONObject.fromObject(ErrUtil.ERR_10033);
		}
		if(StringUtil.isEmpty(param.get("allow_reward"))){
			return JSONObject.fromObject(ErrUtil.ERR_10031);
		}
		if(StringUtil.isEmpty(param.getString("pay_from"))){
			return JSONObject.fromObject(ErrUtil.ERR_10032);
		}
		if(StringUtil.isEmpty(param.getString("card_id"))){
			return JSONObject.fromObject(ErrUtil.ERR_10050);
		}
		
		try{
			Double.parseDouble(param.get("amount")+"");
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10030);
		}
		try{
			Double.parseDouble(param.get("reward")+"");
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10033);
		}
		try{
			Double.parseDouble(param.get("allow_reward")+"");
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10031);
		}
		
		return JSONObject.fromObject(ErrUtil.ERR_0);
	}



}

