package com.lk.service.billiCenter.userInfo.impl;

import com.lk.dao.DaoSupport;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import com.lk.entity.billecenter.SWUser;
import com.lk.service.billiCenter.base.SWBaseService;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.util.ErrUtil;
import com.lk.util.MsgUtil;
import com.lk.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Autowired
	private SWBaseService swBaseService;


    public JSONObject check(JSONObject json){
        if(StringUtil.isEmpty(json.getString("field_value"))){
            return JSONObject.fromObject(ErrUtil.ERR_10060);
        }
        if(StringUtil.isEmpty(json.get("field_type"))){
            return JSONObject.fromObject(ErrUtil.ERR_10061);
        }

        try{
            Integer.parseInt(json.get("field_type")+"");
        }catch(Exception e){
            return JSONObject.fromObject(ErrUtil.ERR_10061);
        }
        return JSONObject.fromObject(ErrUtil.ERR_0);
    }

	/**
	 *
	 * @param store_id
	 * @param ：是否为空
	 * @return 返回错误码或者正确的接口数据，其中body是JSONArray类型
	 * @throws Exception
	 */
	/*
	public JSONObject userInfo(String store_id, JSONObject param) throws Exception {
		JSONObject jsonResult = new JSONObject();

		//判断业务请求参数是否正常
		jsonResult = userSaleService.check(param);
		if(!jsonResult.getString("errcode").equals("0")){
			return jsonResult;
		}

		//封装Message
		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.MSG_USERINFO);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));
		
		// 发起通讯
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());



        if(StringUtil.isNotEmpty(result)){
    		JSONObject json2 = JSONObject.fromObject(result);
    		if(StringUtil.isNotEmpty(json2)){
    			//保存响应充值后的结果
    			if(json2.getJSONObject("head").getString("errcode").equals("0")){
    				if(json2.containsKey("body")){
    					if(json2.get("body") instanceof JSONObject){
    						JSONArray arr = new JSONArray();
    						arr.add(json2.getJSONObject("body"));
    						json2.put("body", arr);
    					}
	    				JSONArray body = json2.getJSONArray("body");
	    				for(int i=0;i<body.size();i++){
	    					JSONObject son = body.getJSONObject(i);
	    					
	    					PageData pdUser = new PageData();
	    					pdUser.put("store_id", store_id);
	    					pdUser.put("card_id", son.getString("card_id"));
	    					pdUser = memberUserService.findByCardIdAndStoreId(pdUser);
	    					//更新会员信息
	    					if(StringUtil.isNotEmpty(pdUser)){
	    						pdUser.put("id_card", son.get("id_card"));
	    						pdUser.put("id_type", son.get("id_type"));
	    						pdUser.put("user_name", son.get("user_name"));
	    						pdUser.put("user_password", son.get("user_password"));
	    						pdUser.put("phone_number", son.get("phone_number"));
	    						pdUser.put("member_level", son.get("member_level"));
	    						pdUser.put("usable_integral", son.get("usable_integral"));
	    						pdUser.put("overage", son.get("overage"));
	    						pdUser.put("reward", son.get("reward"));
	    						pdUser.put("update_time", Tools2.date2Str(new Date()));
	    						memberUserService.edit(pdUser);
	    					}else{
	    						pdUser = new PageData();
	    						pdUser.put("member_id", StringUtil.get32UUID());
	    						pdUser.put("store_id", store_id);
	    						
		    					pdUser.put("card_id", son.get("card_id"));
	    						pdUser.put("id_card", son.get("id_card"));
	    						pdUser.put("id_type", son.get("id_type"));
	    						pdUser.put("user_name", son.get("user_name"));
	    						pdUser.put("user_password", son.get("user_password"));
	    						pdUser.put("phone_number", son.get("phone_number"));
	    						pdUser.put("member_level", son.get("member_level"));
	    						pdUser.put("usable_integral", son.get("usable_integral"));
	    						pdUser.put("overage", son.get("overage"));
	    						pdUser.put("reward", son.get("reward"));
	    						
	    						pdUser.put("confirm_type", 0);
	    						pdUser.put("create_time", Tools2.date2Str(new Date()));
	    						pdUser.put("update_time", pdUser.getString("create_time"));
	    						memberUserService.save(pdUser);
	    					}
	    				}
	    				jsonResult.put("body", body);
	    				jsonResult.put("errcode", "0");
	    				jsonResult.put("errmsg", "query user info success");
    				}else{
    					//如果通过证件号类型查找不到信息，则通过卡号的方式查询，0表示卡号；1表示证件号
    					if(param.getString("field_type").equals("1")) {
    						Business.businesses.remove(msg.getFlag());
    						param.put("field_type", "0");
    						jsonResult = this.userInfo(store_id, param);
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
        }else{
			jsonResult = JSONObject.fromObject(ErrUtil.ERR_10037);
        }
		Business.businesses.remove(msg.getFlag());
		System.out.println("lanker返回码==="+jsonResult);
		return jsonResult;
	}
	*/

	@Override
	public Message2 sendSelUserInfo(String store_id, SWQuery query) throws Exception{
		return swBaseService.sendToSW(store_id, query, MsgUtil.MSG_USERINFO);
	}

	@Override
	public Message2 getSelSWUserByFlag(String msgFlag) throws Exception{
		List<SWUser> list = new ArrayList();

		Message2 m2 = swBaseService.getMsgFromCache(msgFlag);
		if(m2.getErrcode() != 0){
			return m2;
		}

		if(StringUtil.isNotEmpty(m2.getData().get("arr"))){
			JSONArray arr = (JSONArray) m2.getData().get("arr");
			SWUser swUser = (SWUser) JSONObject.toBean(arr.getJSONObject(0), SWUser.class);
			list.add(swUser);
		}
		return Message2.ok().addData("list", list);
	}


	@Override
	public Message2 getSWUser(String store_id, String card_id) throws Exception{

		SWQuery query = new SWQuery();
		query.setField_type(0);//0卡号；1证件号
		query.setField_value(card_id);

		//发送通讯
		Message2 m2 = this.sendSelUserInfo(store_id, query);
		if(m2.getErrcode() != 0){
			return m2;
		}

		//返回通讯
        SWUser swUser = null;
		Message2 m22 = this.getSelSWUserByFlag(m2.getData().get("msg_flag").toString());
		if(m22.getErrcode() == 0){
			List<SWUser> list = ((List<SWUser>) m22.getData().get("list"));
			if(list != null && list.size() > 0){
				swUser = ((List<SWUser>) m22.getData().get("list")).get(0);
			}
			if(swUser != null && StringUtil.isNotEmpty(swUser.getUser_name())){
			    return Message2.ok().addData("SWUser", swUser);
			}
		}

        //按证件号再查一遍
        query.setField_type(1);//0卡号；1证件号
        Message2 m2_2 = this.sendSelUserInfo(store_id, query);
        if(m2_2.getErrcode() != 0){
            return m2_2;
        }
        Message2 m22_2 = this.getSelSWUserByFlag(m2_2.getData().get("msg_flag").toString());
        if(m22_2.getErrcode() == 0){
            List<SWUser> list = ((List<SWUser>) m22_2.getData().get("list"));
            if(list != null && list.size() > 0){
                swUser = ((List<SWUser>) m22_2.getData().get("list")).get(0);
            }
			if(swUser != null && StringUtil.isNotEmpty(swUser.getUser_name())){
				System.out.println("sw:::" + swUser.toString());
				return Message2.ok().addData("SWUser", swUser);
			}
        }
		return m22_2;
	}

	public static void main(String[] args) throws Exception{
		Message2 m2 =  new UserInfoServiceImpl().getSWUser("f42d93ef-ef09-424e-878b-274ac254849d", "330681199204044418");

		System.out.println(m2.toString());
	}

}

