package com.lk.service.system.cancel.impl;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWRecharge;
import com.lk.entity.billecenter.SWRechargeReturn;
import com.lk.entity.billecenter.SWUser;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.wechat.WeixinService;
import com.lk.util.*;
import com.lk.wechat.util.WechatMessageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.recharge.RechargeService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.internet.scene.SceneManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.cardStore.CardStoreManager;
import com.lk.service.system.intenet.impl.IntenetService;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.template.TemplateManager;
import com.lk.wechat.util.TemplateMsgUtil;
import com.lk.wechat.util.WechatCardUtil;

/** 
 * 说明： 优惠券核销
 * 创建人：洪智鹏
 * 创建时间：2017-03-20
 * @version
 */
@Service("cancelService")
public class CancelService implements CancelManager {

	private static final Logger log = LoggerFactory.getLogger(CancelService.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "cancelService")
	private CancelManager cancelService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "sceneService")
	private SceneManager sceneService;
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "cardStoreService")
	private CardStoreManager cardStoreService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;

	@Resource(name = "rechargeService")
	private RechargeService rechargeService;
	@Resource(name = "userFlowService")
	private UserFlowService userFlowService;


	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;

	/**
	 * 新增
	 *
	 * @param pd（包含核销优惠券相关信息）
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("CancelMapper.save", pd);
	}


	/**
	 * 修改
	 * 通过CANCEL_ID保存修改信息
	 *
	 * @param pd 修改审核信息相关信息
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("CancelMapper.edit", pd);
	}

	/**
	 * 列表
	 * 通过用户id/卡劵id/门店id查询Cancel信息
	 *
	 * @param page 查看审核列表包含页面传递的检索字段等信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 *
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.listAll", pd);
	}

	/**
	 * 列表(全部)
	 * 通过条件查询到需要导出的数据
	 *
	 * @param pd 包含导出优惠券核销需要的数据
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listExcel(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("CancelMapper.listExcel", pd);

		return list;
	}

	/**
	 * 列表(操作用户)
	 * 分店只能看到他自己的操作用户组
	 *
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUser(PageData pd) throws Exception {

		List<PageData> list = (List<PageData>) dao.findForList("CancelMapper.listUser", pd);
		//需要额外的加上核销员
        List<PageData> listStaff = (List<PageData>) dao.findForList("CancelMapper.listStaff", pd);
        for (PageData pdd : listStaff) {
            pdd.put("NAME", "核销员-" + URLDecoder.decode(pdd.getString("NECK_NAME"), "UTF-8"));
        }


        list.addAll(listStaff);
		return list;
	}

	/**
	 * 通过id获取数据
	 *
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CancelMapper.findById", pd);
	}

	/**
	 * 批量删除
	 *
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("CancelMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 通过卡劵id,卡劵信息修改信息
	 *
	 * @param pd
	 * @throws Exception
	 */
	public void editByCard(PageData pd) throws Exception {
		dao.update("CancelMapper.editByCard", pd);
	}


	@Override
	public PageData findByUser(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CancelMapper.findByUser", pd);
	}


	@Override
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData) dao.findForObject("CancelMapper.findByCode", pd);
	}


	@Override
	public List<PageData> listByUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.listByUser", pd);
	}

	@Override
	public List<PageData> listMy(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.listMy", pd);
	}


	@Override
	public List<PageData> listCard(PageData pdStore) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.listCard", pdStore);
	}


	@Override
	public List<PageData> listPageMyAll(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.listPageMyAll", page);
	}


	@Override
	public List<PageData> listPageCeshi(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.listPageCeshi", page);
	}


	@Override
	public List<PageData> wxlistPageCeshi(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.wxlistCeshi", page);
	}

	@Override
	public List<PageData> findByCardId(PageData pdStore) throws Exception {
		return (List<PageData>) dao.findForList("CancelMapper.findByCardId", pdStore);
	}

	/*****************************业务逻辑**********************************/

	/**
	 * 后台核销卡券
	 * @param code  用户卡券码
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject saveCancel(String code) throws Exception {
		User user = Jurisdiction.getSessionUser().getUser();//得到用户

		JSONObject json = new JSONObject();

		PageData pd = new PageData();
		pd.put("CARD", code);
		pd.put("USER_ID", user.getUSER_ID());    //操作用户


		//查询该卡券码是否存在，是否已被核销
		PageData pdCancel = new PageData();
		pdCancel.put("CARD", pd.get("CARD"));
		pdCancel.put("INTERNET_ID", user.getINTENET_ID());
		pdCancel = cancelService.findByCode(pdCancel);

		//根据CARD_ID查询卡券的数据
		PageData pdCard = new PageData();
		pdCard = cardService.findById(pdCancel);

		if (pdCancel == null) {
			json.put("result", "false");
			json.put("message", "该卡券无效，请先核对卡号！！");
			return json;
		} else {
			//现金券时，判断是否已核销=2，是否已充值成功=1
			if (pdCard.get("CARD_TICKET").equals("1") &&
					pdCancel.getString("STATE").equals("2") &&
					!pdCancel.getString("MONEY_STATE").equals("1")) {
				//现金券时，判断是否已充值成功=1
				json.put("result", "false");
				json.put("message", "现金券正在充值中！");
				return json;
			}

			//非现金券时，判断是否已核销=2
			if (pdCancel.getString("STATE").equals("2")) {
				json.put("result", "false");
				json.put("message", "该卡券已被核销！");
				return json;
			}
		}

		//查看拥有卡券的用户是否绑定门店
		PageData pdUser = new PageData();
		pdUser.put("CARD", pd.get("CARD"));
		pdUser = bunduserService.findByCode(pdUser);
		if (pdUser == null) {
			json.put("result", "false");
			json.put("message", "请通知用户先绑定当前门店再核销！");
			return json;
		}
		String store_id = pdUser.getString("STORE_ID");//会员绑定的门店

		// 查看是否可核销（1.操作用户所在的门店是否在卡券的通用门店里；2.用户绑定的门店是否属于操作用户所在的门店）
		if (!user.getROLE_ID().equals(PublicManagerUtil.INTERNETROLEID)) {// 不是网吧管理员时
			//找到操作用户所在的门店
			PageData pdStoreUser = new PageData();
			pdStoreUser.put("USER_ID", user.getUSER_ID());
			pdStoreUser = storeUserService.findByUserId(pdStoreUser);
			//判断该门店是否有权核销
			PageData pdd = new PageData();
			pdd.put("CARD", pd.get("CARD"));// 微信的卡券号
			String storeId = pdStoreUser.getString("STORE_ID");
			pdd.put("STORE_ID", storeId);
			List<PageData> pdStoreList = cardStoreService.findByCardId(pdd); //通过卡劵号和门店id获取信息
			if (pdStoreList.size() == 0) {
				json.put("result", "false");
				json.put("message", "您无权核销该卡券");
				return json;
			}
			//判断拥有卡券的用户，是否绑定了当前操作的门店
			if (!pdUser.getString("STORE_ID").equals(storeId)) {
				json.put("result", "false");
				json.put("message", "用户绑定的门店与当前操作用户的门店不符， 请通知用户先绑定当前门店再核销！");
				return json;
			}
		}

		//判断拥有卡券的用户，是否满足卡券设置的场景
		PageData pdSce = new PageData();
		pdSce.put("CARD_ID", pdCancel.getString("CARD_ID"));
		pdSce = sceneService.findByCard(pdSce);

		JSONObject jsonObject = sceneService.SceneCancel(pdSce, pdCancel, pdUser);
		if (jsonObject.containsKey("msg") && jsonObject.get("msg").toString().equals("false")) {
			json.put("result", "false");
			json.put("message", jsonObject.get("result").toString());
			log.info("场景判断失败的数据===" + jsonObject.get("result").toString());
			return json;
		}

		//获取公众号的凭证
		String access_token = autoReplyService.getAuthToken(user.getINTENET_ID());
		//判断微信卡券码是否正常
		JSONObject canCancel = WechatCardUtil.queryCard(access_token, pd.getString("CARD"), pdCancel.getString("CARD_ID"));
		if (!canCancel.getString("errcode").equals("0")) {
			json.put("result", "false");
			json.put("message", "无效的微信卡券，请先核对卡号！！");
			return json;
		}

		//是否是现金券
		if (pdCard.get("CARD_TICKET").toString().equals("1")) {

			//判断客户端连接是否存在
			if (!ChannelCache.channelMap.containsKey(store_id)) {
				json.put("result", "false");
				json.put("message", "揽客客户端已断开连接，请先连接");
				return json;
			}

			PageData pdStoreV = new PageData();
			pdStoreV.put("STORE_ID", store_id);
			pdStoreV = storeService.findByVId(pdStoreV); //获取绑定的门店信息
			//判断优惠券卡号所属的微信用户，其绑定的门店是否加v
			if (StringUtil.isNotEmpty(pdStoreV) &&
					!pdStoreV.get("v_state").toString().equals(PublicManagerUtil.INTERNET_STORE_STATE_V1)) {
				json.put("result", "false");
				json.put("message", pdStoreV.getString("STORE_NAME") + "，该门店尚未加v");
				return json;
			}
		}


		//调用微信接口核销卡券
		pd.put("OPEN_ID", pdCancel.getString("OPEN_ID"));
		pd.put("CARD_ID", pdCancel.getString("CARD_ID"));
		JSONObject resultJSON = WechatCardUtil.cancelCard(access_token, pd);
		if (resultJSON.getString("errmsg").endsWith("ok")) {
			//判断是否是新手券，是的话，给老用户发送老带新卡券
			if (pdCard.getString("FAV_TYPE").endsWith("NEW")) {
				PageData person = new PageData();
				person.put("OPEN_ID", resultJSON.get("openid"));
				person = wechatuserService.findByOldwithId(person); //通过新用户openid查找推荐用户获取数据
				if (person != null) {
					long timestamp = new Date().getTime() / 1000;
					PageData pdOldCard = new PageData();
					pdOldCard.put("intenetId", user.getINTENET_ID());
					pdOldCard.put("cardType", "OLD");
					pdOldCard = cardService.findByCenece(pdOldCard); //通过场景查看优惠券(查看某场景对应的优惠券)
					JSONObject jsonType = new JSONObject();
					jsonType.put("card_id", pdOldCard.getString("CARD_ID"));
					//给老用户发送新卡券
					WechatMessageUtil.sendMessageByCustom(access_token, person.getString("OPEN_ID"), "wxcard"
							, jsonType);
				}
			}
			pd.put("STATE", "2");    //已核销状态
			pd.put("CONSUME_SOURCE", "FROM_API");//接口核销

		} else {
			pd.put("STATE", "1");//未核销
			json.put("result", "false");
			json.put("message", "微信核销卡券失败！");
			return json;
		}

		//是否是现金券
		if (pdCard.get("CARD_TICKET").toString().equals("1")) {
			//pubwin充值
			pd = pubRecharge(pdCard.get("CASH_NUMBER").toString(),  store_id, user.getINTENET_ID(),
					pd, pdUser);
		}

		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));

		cancelService.editByCard(pd);
		json.put("result", "true");
		if(pdCard.get("CARD_TICKET").toString().equals("2") && pd.getString("STATE").equals("2")){
			json.put("message", "核销成功！");
		}else if(pdCard.get("CARD_TICKET").toString().equals("1") && pd.getString("MONEY_STATE").equals("1")){
			json.put("message", "核销成功！");
		}else if (pdCard.get("CARD_TICKET").toString().equals("1") && pd.getString("MONEY_STATE").equals("2")) {
			json.put("message", "pubwin充值中！");
		}

		return json;
	}

	/**
	 * 微信卡券助手核销卡券
	 * @param pd
	 * @param openid
	 * @throws Exception
	 */
	@Override
	public void updateCancel(PageData pd, String openid) throws Exception {

		pd.put("CARD", pd.getString("USER_CARD"));
		PageData pdCancel = cancelService.findByCode(pd);

		if(pdCancel != null && pdCancel.getString("STATE").equals("1")){
			//未核销状态时
			pdCancel.put("CONSUME_SOURCE", pd.getString("CONSUME_SOURCE"));
			pdCancel.put("STAFF_OPEN_ID", pd.getString("STAFF_OPEN_ID"));
			pdCancel.put("OUTER_STR", pd.getString("OUTER_STR"));
			pdCancel.put("STATE", "2");//已核销


			//判断是否是现金券
			PageData pdCard = new PageData();
			pdCard.put("CARD_ID", pdCancel.getString("CARD_ID"));
			pdCard = cardService.findByCardId(pdCard);
			PageData pdUser = bunduserService.findByOpenId(openid);
			String store_id = pdUser.getString("STORE_ID");
			if(pdCard != null && pdCard.get("CARD_TICKET").toString().equals("1")){
				//现金券 并且是用户当前绑定的门店是加v的时候，进行充值操作

				if(pdUser != null){
					//核销时，判断上网的门店和绑定 的门店是否一致，不一致，则充值到上网的门店
					pdCancel = pubRecharge(pdCard.get("CASH_NUMBER").toString(), store_id,
							pdCancel.getString("INTERNET_ID"), pdCancel, pdUser);

				}else{
					pdCancel.put("MONEY_STATE", "3");//充值失败
				}
			}

			pdCancel.put("UPDATE_TIME", Tools.date2Str(new Date()));
			this.edit(pdCancel);


			//如果是核销新手券的时候，推送老带新券
			PageData pdCard2 = new PageData();
			pdCard2 = cardService.findById(pdCancel);
			if (pdCard2.getString("FAV_TYPE").endsWith("NEW")) {
				PageData person = new PageData();
				person.put("OPEN_ID", pdCancel.getString("OPEN_ID"));
				person = wechatuserService.findByOldwithId(person); //通过新用户openid查找推荐用户获取数据
				if (person != null) {
					long timestamp = new Date().getTime() / 1000;
					PageData pdOldCard = new PageData();
					pdOldCard.put("intenetId", pdCancel.getString("INTERNET_ID"));
					pdOldCard.put("cardType", "OLD");
					pdOldCard = cardService.findByCenece(pdOldCard); //通过场景查看优惠券(查看某场景对应的优惠券)
					JSONObject jsonType = new JSONObject();
					jsonType.put("card_id", pdOldCard.getString("CARD_ID"));
					//给老用户发送新卡券
					String access_token = autoReplyService.getAuthToken(pdCancel.getString("INTERNET_ID"));
					WechatMessageUtil.sendMessageByCustom(access_token, person.getString("OPEN_ID"), "wxcard"
							, jsonType);
				}
			}

		}
	}

	/**
	 * 后台卡券=现金券，充值中的订单刷新
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateRefreshCancel(PageData pd) throws Exception{
		JSONObject result = new JSONObject();

		PageData pdCancel = this.findById(pd);
		if(pdCancel == null || pdCancel.getString("MONEY_STATE").equals("1")){
			result.put("result","false");
			result.put("message", "该订单无需刷新");
			return result;
		}

		PageData pdUser = bunduserService.findByOpenId(pdCancel.getString("OPEN_ID"));

		//判断客户端连接是否存在
		if(!ChannelCache.channelMap.containsKey(pdUser.getString("STORE_ID"))){
			result.put("result","false");
			result.put("message", "揽客客户端已断开连接，请先连接");
			return result;
		}

		//没接收到信息
		//1.先去调查pubwin流水，判断有没有充值成功，成功则可以核销，不成功则请重新核销
		JSONObject param = new JSONObject();
		param.put("filter_type", "16"); // 0表示全部类型的流水
		param.put("card_id", pdUser.getString("CARDED"));
		param.put("msg_from", "lanker");
		param.put("flag", "sel"); // sel表示只查询；timer表示查询并保存流水
		param.put("begin_time", Tools.formatTime(pdCancel.getString("CREAT_TIME")));
		param.put("end_time", pdCancel.get("UPDATE_TIME").toString());

		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
		pdCancel.put("UPDATE_TIME", Tools.date2Str(new Date()));
		JSONObject userFlow = userFlowService.userFlow(pdUser.getString("STORE_ID"), param);
		if (StringUtil.isNotEmpty(userFlow) && userFlow.getString("errcode").equals("0")
				&& StringUtil.isNotEmpty(userFlow.getJSONArray("body"))) {
			JSONArray body = userFlow.getJSONArray("body");
			for (int j = 0; j < body.size(); j++) {
				JSONObject json = body.getJSONObject(j);

				if (json.getString("order_id").equals(pdCancel.getString("ORDER_ID"))) {
					pdCancel.put("MONEY_STATE", "1");
					pdCancel.put("STORE_ID", pdUser.getString("STORE_ID"));
					this.edit(pdCancel);

					//查到该订单号，说明pubwin充值成功
					result.put("result", "true");
					result.put("message", "刷新成功");
					return result;
				}

				if (!json.getString("order_id").equals(pdCancel.getString("ORDER_ID")) && j == (body.size() - 1)) {
					//直到最后一个也没有该订单时，表示pubwin没有充值成功
					pdCancel.put("MONEY_STATE", "3");
					pdCancel.put("STORE_ID", pdUser.getString("STORE_ID"));
					this.edit(pdCancel);

					result.put("result", "false");
					result.put("type", "recharge_fail");
					result.put("message", "该订单充值失败，请重新发起充值");
				}
			}
		}else{
			pdCancel.put("MONEY_STATE", "3");
			pdCancel.put("STORE_ID", pdUser.getString("STORE_ID"));
			this.edit(pdCancel);

			result.put("result", "false");
			result.put("message", "查不到该订单信息");
		}
		return result;
	}

	/**
	 * 后台卡券=现金券--》充值失败--》重新充值
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateRechargeAgain(PageData pd) throws Exception{
		JSONObject result = new JSONObject();

		//查询核销的信息
		PageData pdCancel = this.findById(pd);
		if(pdCancel == null || pdCancel.getString("MONEY_STATE").equals("1")){
			result.put("result","false");
			result.put("message", "该订单已完成");
			return result;
		}
		PageData pdUser = bunduserService.findByOpenId(pdCancel.getString("OPEN_ID"));
		//判断客户端连接是否存在
		if(!ChannelCache.channelMap.containsKey(pdUser.getString("STORE_ID"))){
			result.put("result","false");
			result.put("message", "揽客客户端已断开连接，请先连接");
			return result;
		}

		//查询卡券的信息
		PageData pdCard = new PageData();
		pdCard.put("CARD_ID", pdCancel.getString("CARD_ID"));
		pdCard = cardService.findById(pdCard);
		if(pdCard == null || !pdCard.get("CARD_TICKET").toString().equals("1")){
			//卡券是否存在，且是否是现金券
			result.put("result","false");
			result.put("message", "该现金券不存在");
			return result;
		}

		String reward = pdCard.getString("CASH_NUMBER");

		pdCancel = pubRecharge(reward, pdUser.getString("STORE_ID"), pdCancel.getString("INTERNET_ID"),
				pdCancel, pdUser);

		pdCancel.put("UPDATE_TIME", Tools.date2Str(new Date()));
		this.edit(pdCancel);
		if(pdCancel.getString("MONEY_STATE").equals("1")){
			result.put("result","true");
			result.put("message", "充值成功");
		}else{
			result.put("result","false");
			result.put("message", "充值失败，" + pdCancel.getString("MEMO"));
		}

		return result;
	}


	/**
	 * 实际往顺网充值
	 * @param money
	 * @param store_id
	 * @param internetId
	 * @param pd
	 * @param pdUser
	 * @return
	 * @throws Exception
	 */
	private PageData pubRecharge(String money, String store_id, String internetId,
							  PageData pd, PageData pdUser) throws Exception{

		pd.put("STORE_ID", store_id);
		String cardid = StringUtil.isNotEmpty(pdUser.getString("CARDID")) ? pdUser.getString("CARDID") : pdUser.getString("CARDED");
		JSONObject pdBoard = ChannelCache.userUpMap.get(store_id + "_" + cardid);
		if(pdBoard == null){
			List<PageData> storeList = storeService.listByInternetId(internetId);
			for (PageData pdd : storeList) {
				PageData pdStore = pdd;
				pdBoard = ChannelCache.userUpMap.get(pdStore.getString("STORE_ID") + "_" + cardid);
				if(pdBoard != null){
					//在同一个公众号的其他网吧上网时，将充值页面的参数更换为其他网吧的
					store_id = pdStore.getString("STORE_ID");
					pd.put("STORE_ID", store_id);

					//判断上机门店是否在卡券适用的门店列表中
					PageData pddd = new PageData();
					pddd.put("CARD", pd.getString("CARD"));// 微信的卡券号
					pddd.put("STORE_ID", store_id);
					List<PageData> pdStoreList = cardStoreService.findByCardId(pdd); //通过卡劵号和门店id获取信息
					if (pdStoreList.size() == 0) {
						//该门店不能核销该卡券
						pd.put("MONEY_STATE", "3");//充值失败
						pd.put("MEMO", "核销时的门店，与卡券设置的门店不符合");
						return pd;
					}

					Message2 m2 = userInfoService.getSWUser(pdStore.getString("STORE_ID"), cardid);
					if(m2.getErrcode() == 0){
						SWUser swUser = (SWUser) m2.getData().get("SWUser");
						if(swUser != null){
							pdUser.put("CARDED", swUser.getId_card());
							pdUser.put("CARDID", swUser.getCard_id());
						}
					}
					continue;
				}
			}
		}


		double amount = 0.00;//本金
		double reward = Double.parseDouble(money);//奖励金额

        cardid = StringUtil.isNotEmpty(pdUser.getString("CARDID")) ? pdUser.getString("CARDID") : pdUser.getString("CARDED");
        String order_id = Tools.getCenterMsgId();

        SWRecharge swRecharge = new SWRecharge();
        swRecharge.setCard_id(cardid);
        swRecharge.setOrder_id(order_id);
        swRecharge.setPay_from("lanker");
        swRecharge.setMemo("揽客优惠券");
        swRecharge.setAmount(amount);
        swRecharge.setReward(reward);
        swRecharge.setAllow_reward(0);

        //调顺网充值接口充值
        pd.put("ORDER_ID", order_id);//订单号
        Message2 m2 = rechargeService.newRecharge(store_id, swRecharge);
        if (m2.getErrcode() == 0 && m2.getData().get("SWRechargeReturn") != null) {
            SWRechargeReturn swRechargeReturn = (SWRechargeReturn) m2.getData().get("SWRechargeReturn");
            log.info("揽客优惠券订单号==" + order_id + "，顺网充值成功");

            //充值成功
            double principal = swRechargeReturn.getPrincipal();
            double reward2 = swRechargeReturn.getReward();

            //充值成功，修改用户余额
            double OLD_OVERAGE = Double.parseDouble(pdUser.getString("OVERAGE"));
            double OLD_REWARD = Double.parseDouble(pdUser.getString("REWARD"));
            pdUser.put("OVERAGE", OLD_OVERAGE + principal+reward2);//总余额=原余额+充值本金+充值奖励
            pdUser.put("REWARD", OLD_REWARD + reward2);//总奖励=原有奖励+充值奖励
            bunduserService.edit(pdUser);  //充值成功，修改用户余额


            /*****************充值成功，发送模板消息********************/

            //获取微信用户的open_id
            PageData pdWechatUser = wechatuserService.findByUserId(pdUser); //通过用户id获取微信用户的open_id

            //发送模板消息
			userFlowService.sendMoneyChangeTemp(internetId, pdWechatUser.getString("OPEN_ID"),
					Tools.date2Str(new Date()), (principal + reward2)+"", "揽客优惠券", pdUser.get("OVERAGE").toString());

            pd.put("MONEY_STATE", "1");//充值成功
            pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
        }else {
            //充值失败或者没返回消息
            pd.put("MONEY_STATE", "2");//充值中
            pd.put("UPDATE_TIME", Tools.date2Str(new Date()));
			pd.put("MEMO", m2.getErrmsg());
        }

		return pd;
	}

	/**
	 * 通过open_id获取user_id
	 * @param card
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageData selectUserId(PageData card) throws Exception {
		return (PageData) dao.findForObject("CancelMapper.selectUserId", card);
	}


}

