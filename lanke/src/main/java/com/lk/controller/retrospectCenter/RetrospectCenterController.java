package com.lk.controller.retrospectCenter;


import com.lk.communicate.server.model.Message;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.communicate.server.util.Tools2;
import com.lk.controller.base.BaseController;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQuery;
import com.lk.entity.billecenter.SWUser;
import com.lk.entity.billecenter.SWUserBoard;
import com.lk.service.billiCenter.userBoard.UserBoardService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.hd.CallBack;
import com.lk.service.hd.impl.Business;
import com.lk.service.internet.QRcode.QrCodeService;
import com.lk.service.system.client.impl.ClientServiceImpl;
import com.lk.service.system.store.StoreManager;
import com.lk.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping(value = "/retrospectCenter")
public class RetrospectCenterController extends BaseController{

	public static final Logger log = LoggerFactory.getLogger(RetrospectCenterController.class);
	private static Map<String, String> map = new HashMap<>();
	
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;

	@Resource(name="callBackService")
	private CallBack callBackService;

	@Autowired
	private QrCodeService qrCodeService;

    @Resource(name = "userBoardService")
    private UserBoardService userBoardService;

    @Autowired
    private UserFlowService userFlowService;

	
	@RequestMapping(value = "/test")
	@ResponseBody
	public String test() throws Exception{
		
		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");
		String type = this.getRequest().getParameter("type");

		SWQuery query = new SWQuery();
		query.setField_type(Integer.parseInt(type));
		query.setField_value(card_id);
		Message2 m2 = userInfoService.sendSelUserInfo(store_id, query);

		Message2 m22 = userInfoService.getSelSWUserByFlag(m2.getData().get("msg_flag").toString());
		SWUser user = ((List<SWUser>) m22.getData().get("list")).get(0);

		return JSONObject.fromObject(user).toString();
	}

	@RequestMapping(value = "/testUser")
	@ResponseBody
	public Message2 testUser() throws Exception{

		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");

		Message2 m2 = userInfoService.getSWUser(store_id, card_id);

		return m2;
	}

	@RequestMapping(value = "/testAddMember")
	@ResponseBody
	public JSONObject testAddMember(){

		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");
		String user_name = this.getRequest().getParameter("user_name");
		String member_level = this.getRequest().getParameter("member_level");

		JSONObject param = new JSONObject();
		param.put("card_id", card_id);
		param.put("user_name", user_name);
		param.put("user_password", "111111");
		param.put("id_card", card_id);
		param.put("id_type", "1");//身份证
		param.put("phone_number", "");
		param.put("member_level", member_level);
		param.put("confirm_type", 0);

		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.ADD_MEMBER);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));

		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());

		return JSONObject.fromObject(result);

	}

	@RequestMapping(value = "/testMoney")
	@ResponseBody
	public JSONObject testMoney() throws Exception{

		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");

		//充值
		JSONObject param = new JSONObject();
    	param.put("card_id", card_id);
    	param.put("order_id", Tools.getCenterMsgId());
    	param.put("pay_from", "lanker");
    	param.put("memo", "揽客优惠券");
    	param.put("amount", "0");
    	param.put("reward", "0.01");
    	param.put("allow_reward", 0);
		JSONObject result = null;//rechargeService.recharge(store_id, param);

		return result;
	}

	@RequestMapping(value = "/testUp")
	@ResponseBody
	public JSONObject testUp() throws Exception{
		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");
		String computer_name = this.getRequest().getParameter("computer_name");
		String user_password = this.getRequest().getParameter("user_password");

		JSONObject param = new JSONObject();
		param.put("card_id", card_id);
		param.put("computer_name", computer_name);
		param.put("user_password", user_password);

		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType("0x00008046");
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));

		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());

		return JSONObject.fromObject(result);

	}

	@RequestMapping(value = "/testUp2")
	@ResponseBody
	public Message2 testUp2() throws Exception{
		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");
		String computer_name = this.getRequest().getParameter("computer_name");
		String user_password = this.getRequest().getParameter("user_password");

        Message2 m2 = userBoardService.userUp(store_id, card_id, computer_name, user_password);
		return m2;

	}

    @RequestMapping(value = "/testChangeUp2")
    @ResponseBody
    public Message2 testChangeUp2() throws Exception{
        String store_id = this.getRequest().getParameter("store_id");
        String card_id = this.getRequest().getParameter("card_id");
        String from_computer = this.getRequest().getParameter("from_computer");
        String to_computer = this.getRequest().getParameter("to_computer");

        Message2 m2 = userBoardService.userUpChange(store_id, card_id, from_computer, to_computer);
        return m2;

    }

    @RequestMapping(value = "/testLoadUserOnline")
    @ResponseBody
    public Message2 testLoadUserOnline() throws Exception{
        String store_id = this.getRequest().getParameter("store_id");
        String card_id = this.getRequest().getParameter("card_id");
        String computer_name = this.getRequest().getParameter("computer_name");
        String guid = this.getRequest().getParameter("guid");

        if(StringUtil.isNotEmpty(card_id)){
            Message2 m2 = userBoardService.getUserBoard(store_id, card_id, null);
            return m2;
        }

        if(StringUtil.isNotEmpty(computer_name)){
            Message2 m2 = userBoardService.getUserBoard(store_id, null, computer_name);
            return m2;
        }
        if(StringUtil.isNotEmpty(guid)){
            SWQuery query = new SWQuery();
            query.setField_type(3);//0卡号；1证件号;2电脑名称；3guid
            query.setField_value(guid);
            Message2 m2 = userBoardService.sendSelUserUP(store_id, query);
            if(m2.getErrcode() != 0){
                return m2;
            }

            Message2 m22 = userBoardService.getSelUserUPByFlag(m2.getData().get("msg_flag").toString());
            if(m22.getErrcode() != 0){
                return m22;
            }

            return m22;
        }
        return null;
    }


	@RequestMapping(value = "/testDown")
	@ResponseBody
	public JSONObject testDown() throws Exception{
		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");

		JSONObject param = new JSONObject();
		param.put("card_id", card_id);
		param.put("pay_type", 0);//卡余额支付

		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.USER_DOWN);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));

		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());

		return JSONObject.fromObject(result);

	}

	@RequestMapping(value = "/testChangeUp")
	@ResponseBody
	public JSONObject testChangeUp() throws Exception{
		String store_id = this.getRequest().getParameter("store_id");
		String card_id = this.getRequest().getParameter("card_id");
		String from_computer = this.getRequest().getParameter("from_computer");
		String to_computer = this.getRequest().getParameter("to_computer");

		JSONObject param = new JSONObject();
		param.put("card_id", card_id);
		param.put("from_computer", from_computer);
		param.put("to_computer", to_computer);

		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType("0x00008049");
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));

		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());

		return JSONObject.fromObject(result);

	}

	@ResponseBody
	@RequestMapping(value = "/testWakePower")
	public JSONObject testWakePower() throws Exception{

		//传入store_id,computer_name
		PageData pd = this.getPageData();
		System.out.println("testWakePower========"+pd.toString());

		qrCodeService.wakePower(pd);
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/testSWWakePower")
	public Message2 testSWWakePower() throws Exception{

		//传入store_id,computer_name
		PageData pd = this.getPageData();
		System.out.println("testSWWakePower========"+pd.toString());

		JSONObject param = new JSONObject();
		param.put("computer_name", pd.getString("computer_name"));


		return userBoardService.swWakeToPower(pd.getString("store_id"), param);
	}

	@RequestMapping(value = "/testSetQrParam")
	@ResponseBody
	public JSONObject testSetQrParam() throws Exception{
		String store_id = this.getRequest().getParameter("store_id");
		String appid = this.getRequest().getParameter("appid");
		String lanke_url = this.getRequest().getParameter("lanke_url");
		String component_appid = this.getRequest().getParameter("component_appid");

		JSONObject parameters = new JSONObject();
		parameters.put("appid", appid);
		parameters.put("lanke_url", lanke_url);
		parameters.put("component_appid", component_appid);

		JSONObject param = new JSONObject();
		param.put("parameters", parameters);
		param.put("company_type", 2);//网晶

		System.out.println(param.toString());
		Message msg = new Message();
		msg.setBarId(store_id);
		msg.setType(MsgUtil.SET_QR_PARAM);
		msg.setFlag(Tools2.getCenterMsgId2());
		msg.setData(Tools2.desEncoder(MsgUtil.ENCODE_KEY,param.toString()));

		// 通过store_id获取具体需要通讯的客户端连接
		ChannelCache.channelMap.get(store_id).writeAndFlush(msg);
		Business.exc(msg.getFlag(), MsgUtil.BASE_TIME);
		String result = callBackService.result(msg.getFlag());

		return JSONObject.fromObject(result);

	}


    /**
     * 测试查询流水
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/testUserFlow")
	public Message2 testUserFlow() throws Exception{

        String store_id = this.getRequest().getParameter("store_id");
        String card_id = this.getRequest().getParameter("card_id");
        String begin = this.getRequest().getParameter("begin_time");
        String end = this.getRequest().getParameter("end_time");

        int begin_time = Integer.parseInt((Tools.str2Date(begin).getTime() / 1000) + "");//开始时间
        int end_time = Integer.parseInt((Tools.str2Date(end).getTime() / 1000) + "");//结束时间

        return userFlowService.getUserFlow(store_id, card_id, 0, begin_time, end_time);
    }

	/************************************************************/










	/**
	 * 微信端--一码通首页
	 * @return
	 */
	@RequestMapping(value = "/userIndex")
	public ModelAndView userIndex() throws Exception{
		ModelAndView mv = new ModelAndView();
		JSONObject result = new JSONObject();

		//传入参数（上机参数：appid，computer_name，state，code,component_appid）
		PageData pd = this.getPageData();
		log.info("userIndex--pd的参数"+pd.toString());
		if(StringUtil.isEmpty(pd.getString("appid")) || StringUtil.isEmpty(pd.getString("computer_name"))
				|| StringUtil.isEmpty(pd.getString("state")) || StringUtil.isEmpty(pd.getString("code"))
		){

			result.put("result", "false");
			result.put("message", "无效的二维码");
			mv.setViewName("internet/qrCode/info_tips");
			return mv;
		}

		//判断用户信息(是否关注，是否绑定门店) 和二维码信息
		result = qrCodeService.userIndex(pd);
		mv.addObject("result", result);
		log.info("userIndex === 返回result===" + result.toString());

		if("false".equals(result.getString("result"))){
			mv.setViewName(result.getString("viewName"));
			return mv;
		}

		mv.addObject("pd", pd);
		mv.setViewName("internet/qrCode/user_index");
		return mv;
	}


    /**
     * 加载一码通首页数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loadUserIndex")
	public JSONObject loadUserIndex() throws Exception{
        JSONObject result = new JSONObject();

        PageData pd = this.getPageData();
        //获取二维码上下机情况
        result = qrCodeService.loadUserIndex(pd);

        result.put("result", "true");
        return result;
    }

    /**
     * 网吧客户机扫二维码上机
     * @return
     */
    @RequestMapping(value = "/userUp")
    public ModelAndView userUp() throws Exception{
        ModelAndView mv = this.getModelAndView();
        JSONObject result = new JSONObject();

        //传入参数（上机参数：appid，computer_name，state，code,component_appid）
        PageData pd = this.getPageData();
        log.info("userUp--pd的参数"+pd.toString());
        if(StringUtil.isEmpty(pd.getString("appid")) || StringUtil.isEmpty(pd.getString("computer_name"))
                || StringUtil.isEmpty(pd.getString("state")) || StringUtil.isEmpty(pd.getString("code")))
                {

            result.put("result", "false");
            result.put("message", "无效的二维码");
            mv.setViewName("internet/qrCode/info_tips");
            return mv;
        }

        //判断用户信息(是否关注，是否绑定门店) 和二维码信息
        result = qrCodeService.userIndex(pd);
        mv.addObject("result", result);
        log.info("userUp === 返回result===" + result.toString());


        if("false".equals(result.getString("result"))){
            mv.setViewName(result.getString("viewName"));
            return mv;
        }

        mv.setViewName("internet/qrCode/user_up");
        return mv;
    }





	/**
	 * 提供给客户端调用的，获取网吧的最新服务器地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/servers")
	@ResponseBody
	public JSONObject servers() throws Exception{
		JSONObject result = new JSONObject();
		
		String store_id = this.getRequest().getParameter("store_id");
		log.info(store_id+"客户端请求服务器列表------");
		//判断请求客户端的密钥是否正确
		String timestamp = this.getRequest().getParameter("timestamp");
		String key = this.getRequest().getParameter("key");
		if(!key.equals(Tools.getKey(store_id, timestamp))){
			result.put("result", "false");
			result.put("message", "不合法的验证密钥");
			log.info("不合法的验证密钥");
			return result;
		}
		
		
		//判断请求的store_id是否加v
		PageData pd = new PageData();
		pd.put("store_id", store_id);
		pd.put("state", "1");
		pd = storeService.findByStoreIdAndState(pd);
    	if(StringUtil.isEmpty(pd)){
    		result.put("result", "false");
			result.put("message", "您的网吧尚未开通计费系统功能，请联系揽客客服");
			log.info(store_id+"您的网吧尚未开通计费系统功能，请联系揽客客服");
			return result;
    	}else{

		}
		//设置扫码上机参数
		if(ChannelCache.channelMap.containsKey(store_id)){
			new Thread(new ClientServiceImpl.setQrParam(store_id)).start();
		}

		JSONObject json = new JSONObject();
		json.put("id", Tools.getCenterMsgId());
		json.put("address", PublicManagerUtil.domain_ip);//测试/正式服务器
		json.put("port", "8027");//服务端tcp端口
		List<JSONObject> list = new ArrayList<JSONObject>();
		list.add(json);
		
		result.put("servers", list);
		result.put("result", "true");
		return result;
	}


}
