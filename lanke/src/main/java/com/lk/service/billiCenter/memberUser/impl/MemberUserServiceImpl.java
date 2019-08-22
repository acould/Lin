package com.lk.service.billiCenter.memberUser.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.billiCenter.memberUser.MemberUserService;
import com.lk.service.billiCenter.request.RequestService;
import com.lk.service.billiCenter.response.ResponseService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.system.store.StoreManager;
import com.lk.util.ErrUtil;
import com.lk.util.MsgUtil;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

@Service("memberUserService")
public class MemberUserServiceImpl implements MemberUserService{

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
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MemberUserMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MemberUserMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MemberUserMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberUserMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberUserMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberUserMapper.findById", pd);
	}
	
	/**
	 * 根据card_id和store_id查询用户信息
	 * pd中包含card_id和store_id
	 */
	public PageData findByCardIdAndStoreId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MemberUserMapper.findByCardIdAndStoreId", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MemberUserMapper.deleteAll", ArrayDATA_IDS);
	}

	
	/**
	 * 新增会员接口（待测）
	 * @param store_id 门店id
	 * @param param（card_id，user_name，user_password，id_card，id_type，phone_number，member_level，confirm_type）
	 * 
	 */
	public JSONObject addMember(String store_id, JSONObject param) throws Exception {
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
		msg.setType(MsgUtil.ADD_MEMBER);
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
		pd.put("msg_type", MsgUtil.ADD_MEMBER);
		pd.put("msg_id", msg.getFlag());
		pd.put("create_time", Tools.date2Str(new Date()));
		requestService.save(pd);
		
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
    			//保存响应后的结果
    			if(json2.getJSONObject("head").getString("errcode").equals("0")){
    				
    				PageData pdUser = new PageData();
    				pdUser.put("member_id", StringUtil.get32UUID());
    				pdUser.put("store_id", store_id);
					
					pdUser.put("card_id", param.get("card_id"));
					pdUser.put("id_card", param.get("id_card"));
					pdUser.put("id_type", param.get("id_type"));
					pdUser.put("user_name", param.get("user_name"));
					pdUser.put("user_password", param.get("user_password"));
					pdUser.put("phone_number", param.get("phone_number"));
					pdUser.put("member_level", param.get("member_level"));
					
					pdUser.put("confirm_type", param.get("confirm_type"));
					pdUser.put("create_time", Tools2.date2Str(new Date()));
					pdUser.put("update_time", pd.getString("create_time"));
    				this.save(pdUser);
    				
    				jsonResult.put("errcode", "0");
    				jsonResult.put("errmsg", "add member success");
    			}else{
    				jsonResult.put("errcode", json2.getJSONObject("head").getString("errcode"));
    				jsonResult.put("errmsg", json2.getJSONObject("head").getString("errmsg"));
    			}
    			
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
	 * 检查参数格式是否正确
	 * @param param
	 * @return
	 */
	public JSONObject check(JSONObject param){
		
		if(StringUtil.isEmpty(param.get("card_id"))){
			return JSONObject.fromObject(ErrUtil.ERR_10050);
		}
		
		if(StringUtil.isEmpty(param.get("user_name"))){
			return JSONObject.fromObject(ErrUtil.ERR_10070);
		}
		if(StringUtil.isEmpty(param.get("user_password"))){
			return JSONObject.fromObject(ErrUtil.ERR_10071);
		}
		if(StringUtil.isEmpty(param.get("id_card"))){
			return JSONObject.fromObject(ErrUtil.ERR_10072);
		}
		if(StringUtil.isEmpty(param.get("id_type"))){
			return JSONObject.fromObject(ErrUtil.ERR_10073);
		}
		if(StringUtil.isEmpty(param.get("phone_number"))){
			return JSONObject.fromObject(ErrUtil.ERR_10074);
		}
		if(StringUtil.isEmpty(param.get("member_level"))){
			return JSONObject.fromObject(ErrUtil.ERR_10075);
		}
		if(StringUtil.isEmpty(param.get("member_level"))){
			return JSONObject.fromObject(ErrUtil.ERR_10075);
		}
		
		try{
			Integer.parseInt(param.get("id_type")+"");
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10073);
		}
		
		try{
			if(StringUtil.isNotEmpty(param.get("confirm_type"))){
				Integer.parseInt(param.get("confirm_type")+"");
			}
		}catch(Exception e){
			return JSONObject.fromObject(ErrUtil.ERR_10076);
		}
		
		return JSONObject.fromObject(ErrUtil.ERR_0);
	}

	
	
	

	
	
	
	
}

