package com.lk.controller.intuser;

import com.alibaba.fastjson.JSON;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.controller.base.BaseController;
import com.lk.entity.Message;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWRecharge;
import com.lk.entity.billecenter.SWUser;
import com.lk.entity.jiaLian.PreOrder;
import com.lk.entity.orderVO;
import com.lk.entity.system.BundUser;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.LeaveMessage;
import com.lk.entity.system.User;
import com.lk.entity.unionPay.Goods;
import com.lk.entity.unionPay.SubOrders;
import com.lk.service.billiCenter.recharge.RechargeService;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.internet.internetStaff.InternetStaffService;
import com.lk.service.internet.jiaLian.JiaLianService;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.intuser.myMember.impl.MyMemberService;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.dictenty.DictEntyManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.invite.InviteManager;
import com.lk.service.system.leaveMessage.LeaveMessageService;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.oldwith.OldwithManager;
import com.lk.service.system.order.OrderManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.sysbook.SysBookManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.tb.TemplateRecord.TemplateRecordService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.util.*;
import com.lk.util.JiaLian.JiaLianUtils;
import com.lk.util.JiaLian.PayDemo;
import com.lk.util.JiaLian.YikeConfig;
import com.lk.util.unionPay.NotifyUtilTest;
import com.lk.util.unionPay.PayConfig;
import com.lk.util.unionPay.Util;
import com.lk.wechat.response.WechatUser;
import com.lk.wechat.util.WeChatOpenUtil;
import com.lk.wechat.util.WechatCardUtil;
import com.lk.wechat.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 微信首页--Controller处理器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/indexMember")
public class IndexMemberController extends BaseController {
	public static final Logger log = LoggerFactory.getLogger(IndexMemberController.class);
	// (in_scene表的FAV_TYPE优惠类型)FAV_TYPE
	public static final String FAV_TYPE1 = "NEW";
	public static final String FAV_TYPE2 = "OLD";
	public static final String FAV_TYPE3 = "FLQ";
	public static final String FAV_TYPE4 = "MAN";
	public static final String FAV_TYPE5 = "WEM";
	public static final String FAV_TYPE6 = "CURREN";
	public static final String FAV_TYPE7 = "GRAB";
	public static final String FAV_TYPE8 = "APPLY";
	public static final String FAV_TYPE9 = "TERM";

	// 战队类型--TEAMTYPE
	public static final String TEAMTYPE1 = "1";
	public static final String TEAMTYPE2 = "2";



	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "myopService")
	private MyopManager myopService;
	@Resource(name = "terraceService")
	private TerraceManager terraceService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "inviteService")
	private InviteManager inviteService;
	@Resource(name = "sysbookService")
	private SysBookManager sysbookService;
	@Resource(name = "oldwithService")
	private OldwithManager oldwithService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "dictentyService")
	private DictEntyManager dictentyService;

	@Resource(name = "indexMemberService")
	private IndexMemberManager indexMemberService;
	@Resource(name = "internetMemberService")
	private InternetMemberManager internetMemberService;

	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "generateOrderService")
	private GenerateOrderService generateOrderService;
	@Resource(name = "rechargeService")
	private RechargeService rechargeService;
	@Resource(name = "memberMarkeService")
	private MemberMarkeManager memberMarkeService;

	@Autowired
	private InternetStaffService internetStaffService;
    @Resource(name = "LeaveMessageService")
    private LeaveMessageService lmService;
    @Autowired
    private TemplateRecordService templateRecordService;
    @Resource(name = "orderService")
    private OrderManager orderService;
    @Autowired
    private JiaLianService jiaLianService;

    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;

	// ********************************************会员卡充值***************************************
	/**
	 * 充值说明界面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/rechargeInfo")
	public ModelAndView rechargeInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("intenetmumber/rechargeTreaty");
		return mv;
	}

	@RequestMapping(value = "/userNull")
	public ModelAndView userNull() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("userNull");
		return mv;
	}

	/**
	 * 跳转到购买页面
	 */
	@RequestMapping("/buyUI")
	public ModelAndView buyUI(orderVO orderVO,String Auction_ID){
		ModelAndView mv = this.getModelAndView();
		System.out.println("1111111111");

        String computer_name = this.getRequest().getParameter("computer_name");
		if(StringUtil.isNotEmpty(computer_name)){
            mv.addObject("computer_name", computer_name);
        }
		mv.addObject("orderVO", orderVO);
		mv.addObject("Auction_ID", Auction_ID);
		mv.setViewName("/intenetmumber/myBuyUI");
		return mv;
	}
	/**
	 * 跳转到购买成功后页面
	 */
	@RequestMapping("/successUI")
	public ModelAndView successUI( ) {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("intenetmumber/successUI");
		return mv;
	}

	// /**
	// * 验证是否开通在线支付功能
	// */
	// @ResponseBody
	// @RequestMapping(value = "/yzrecharge")
	// public JSONObject yzrecharge() throws Exception {
	// ModelAndView mv = this.getModelAndView();
	// logBefore(logger, " 验证是否开通在线支付功能");
	// JSONObject json = new JSONObject();
//    User user = this.getUser();//获取用户
	// PageData pds =new PageData();
	// //判断用户是否绑定
	// //获取绑定门店id
	// pds.put("userId", user.getUSER_ID());
	// pds.put("user_id", user.getUSER_ID());
	// pds.put("internet_id", user.getINTENET_ID());
	// pds.put("backurl", "/indexMember/recharge.do");
	// mv = indexMemberService.judgeBind(pds);
	// if(mv.getModel().get("result").toString().equals("false")){
	// //门店被禁用时，去换绑页面
	// if(StringUtil.isNotEmpty(mv.getModel().get("store_state")) &&
	// mv.getModel().get("store_state").toString().equals("1")){
	// json.put("message", "请先绑定门店！");
	// json.put("result", "T");
	// return json;
	// }
	// }
	// PageData pd =new PageData();
	// pd = bunduserService.findByUser(pds);
	// if(pd==null){
	// json.put("message", "未开通在线充值功能！");
	// json.put("result", "T");
	// return json;
	// }
	// PageData pd1 =new PageData();
	// pd1.put("store_id", pd.getString("STORE_ID"));
	// PageData findById = generateOrderService.findByStoreId(pd1);
	// System.out.println("====================================="+findById);
	// if(StringUtil.isNotEmpty(findById)){
	// if(StringUtil.isNotEmpty(findById.get("status")) &&
	// (int)findById.get("status")==1){
	// json.put("backUrl", "http://" + this.getRequest().getServerName() +
	// this.getRequest().getContextPath() +"/indexMember/recharge");
	// json.put("result", "F");
	// }else{
	// json.put("message", "未开通在线充值功能！");
	// json.put("result", "T");
	// }
	// }else{
	// json.put("message", "未开通在线充值功能！");
	// json.put("result", "T");
	// }
	// return json;
	// }

	/**
	 * 充值协议
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/agreement")
	public ModelAndView agreement() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("intenetmumber/agreement");
		return mv;
	}

	/**
	 * 充值界面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/recharge")
	public ModelAndView recharge() throws Exception {

		PageData pd = new PageData();
		ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//获取用户
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}
		// 判断用户是否绑定
		// 获取绑定门店id
		pd.put("userId", user.getUSER_ID());
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/indexMember/recharge.do");
		mv = indexMemberService.judgeBind(pd);
		if (mv.getModel().get("result").toString().equals("false")) {
			// 门店被禁用时，去换绑页面
			if (StringUtil.isNotEmpty(mv.getModel().get("store_state"))
					&& mv.getModel().get("store_state").toString().equals("1")) {
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}
		pd = bunduserService.findByUser(pd);


		//判断用户绑定的门店和上机的门店信息不一致时
		BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());
		JSONObject pdBoard = ChannelCache.userUpMap.get(bundUser.getStore_id() + "_" + bundUser.getCardid());
		if(pdBoard == null){
			List<PageData> storeList = storeService.listByInternetId(user.getINTENET_ID());
			for (PageData pdd : storeList) {
				PageData pdStore = pdd;
				pdBoard = ChannelCache.userUpMap.get(pdStore.getString("STORE_ID") + "_" + bundUser.getCardid());
				if(pdBoard != null){
					logger.info("pdBoard======"+pdBoard.toString());
					//在同一个公众号的其他网吧上网时，将充值页面的参数更换为其他网吧的
					pd.put("STORE_ID", pdStore.getString("STORE_ID"));
					pd.put("STORE_NAME", pdStore.getString("STORE_NAME"));
					Message2 m2 = userInfoService.getSWUser(pdStore.getString("STORE_ID"), pd.getString("CARDED"));
					if(m2.getErrcode() == 0){
						SWUser swUser = (SWUser) m2.getData().get("SWUser");
						if(swUser != null){
							pd.put("CARDED", swUser.getId_card());
							pd.put("CARDID", swUser.getCard_id());
						}
					}
					continue;
				}
			}
		}



		mv.setViewName("intenetmumber/recharge");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 查看充值规则
	 * 
	 * @param storeid
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/listRule")
	public JSONObject listRule(String storeid) throws Exception {
		List<PageData> listRule = generateOrderService.listRule(storeid);
		JSONObject json = new JSONObject();

		System.out.println(listRule);
		if (listRule.size() > 0) {
			json.put("result", true);
			json.put("indexList", listRule);
		} else {
			json.put("result", false);
		}
		return json;
	}
 
	/**
	 * 创建充值订单
	 */
	@ResponseBody
	@RequestMapping(value = "/generateOrder")
	public JSONObject generateOrder(String store_id, String internet_id,
			String internetrule_id, String store_name, String carded, String cardid,
			String reward_balance, String rincipal_balance) throws Exception {
		PageData pd = new PageData();
		JSONObject results = new JSONObject();
        User user = this.getUser();//获取用户

		if (StringUtil.isEmpty(user)) {
			results.put("url", "http://" + this.getRequest().getServerName()
					+ this.getRequest().getContextPath()
					+ "/indexMember/userNull");
			results.put("result", true);
			return results;
		}

		if(!ChannelCache.channelMap.containsKey(store_id)){
            results.put("message", "揽客客户端已断开连接，请先连接");
            results.put("result", false);
            return results;
        }


		// 创建订单
		pd.put("id", this.get32UUID());
		pd.put("merOrderId", Tools.getCenterMsgId2(PayConfig.msgSrcId));
		pd.put("user_id", user.getUSER_ID());
		pd.put("carded", carded);
		pd.put("cardid", cardid);
		pd.put("user_name", user.getNAME());
		pd.put("USER_ID", user.getUSER_ID());
		pd.put("openid", wechatuserService.findByUserId(pd).getString("OPEN_ID"));
		pd.put("store_id", store_id);
		pd.put("store_name", store_name);
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("rincipal_balance", rincipal_balance);
		pd.put("reward_balance", reward_balance);
		pd.put("internetrule_id", internetrule_id);
		pd.put("createtime", Tools.date2Str(new Date()));
		pd.put("return_code", null);
		pd.put("return_msg", null);
		pd.put("pay_state", 0);
		pd.put("pay_actualbalance", rincipal_balance);
		pd.put("pay_type", 0);
		pd.put("pay_starttime", null);
		pd.put("pat_endtime", null);
		pd.put("memo", "揽客充值");

		// 保存 支付连接状态
		pd.put("falg", true);
		generateOrderService.addwarning(pd);
		Boolean falg = generateOrderService.yzRule(internetrule_id,
				rincipal_balance, reward_balance);
		if (!falg) {
			results.put("message", "规则被更改请刷新重新后选择");
			results.put("result", false);
			return results;
		}

		//判断银联或者嘉联支付
        String thirdPayMent = getThirdPayment(store_id);
		if(thirdPayMent.equals("2")){
            PageData pdJL = jiaLianService.findByStoreId(store_id);
            if(!pdJL.get("status").equals("1") && StringUtil.isEmpty(pdJL.getString("shop_secret"))){
                results.put("message", "该门店尚未通过商户审核，在线充值无法使用");
                results.put("result", false);
                return results;
            }
        }
        pd.put("third_payment", thirdPayMent);

		generateOrderService.addOrder(pd);
		String url = "http://" + this.getRequest().getServerName()
				+ this.getRequest().getContextPath()
				+ "/indexMember/payDetails?id=" + pd.getString("id")
				+ "&store_name=" + store_name + "&pay_actualbalance="
				+ rincipal_balance + "&store_id="+store_id;
		results.put("url", url);
		results.put("result", true);
		return results;
	}
 
	/**
	 * 跳转订单详情页
	 * 
	 * @param id
	 *            订单主键id
	 * @param store_name
	 *            门店名称
	 * @param pay_actualbalance
	 *            实际支付金额
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/payDetails")
	public ModelAndView payDetails(String id, String store_name,
			String pay_actualbalance, String store_id) throws Exception{
		ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//获取用户

		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}

		//判断银联或者嘉联支付
        String thirdPayMent = getThirdPayment(store_id);

		mv.setViewName("intenetmumber/payDetails");
		mv.addObject("id", id);
		mv.addObject("store_name", store_name);
		mv.addObject("pay_actualbalance", pay_actualbalance);
		mv.addObject("third_payment", thirdPayMent);
		mv.addObject("status", null);
		return mv;
	}

	/**
	 * 接收并处理下单请求
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/publicpay", method = RequestMethod.GET)
	public ModelAndView publicPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		System.out.println(pdUser);
        User user = this.getUser();//获取用户

		logBefore(logger, "下单支付" + pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		} 
		PageData pd = new PageData();
		pd = generateOrderService.findByIdormerOrderId(pdUser);
		PageData findById = generateOrderService.findByStoreId(pd);
		// 准备商品信息
		List<Goods> goodsList = new ArrayList<>();
		goodsList.add(new Goods("0001", "充值", "1", pd.get("pay_actualbalance").toString(), "Auto", "充"
				+ pd.get("pay_actualbalance").toString() + "增"
				+ pd.get("reward_balance").toString(), findById.getString("business_number"), Long.parseLong(pd.get("pay_actualbalance").toString())*100));//
		List<SubOrders> subOrdersList = new ArrayList<>(); 
		subOrdersList.add(new SubOrders(findById.getString("business_number"), Long.parseLong(pd.get("pay_actualbalance").toString())*100));
		// 请求报文 商品 价格 = 平台商户分账金额+子商户分账金额 = 支付总金额
		JSONObject json = new JSONObject();
		json.put("mid", PayConfig.mid); // 平台商户号
		//json.put("mid", findById.getString("business_number"));
		json.put("tid", PayConfig.tid);// 终端号 
		json.put("msgType", PayConfig.msgType);// 消息类型
		json.put("msgSrc", PayConfig.msgSrc);// 消息来源
		json.put("instMid", PayConfig.instMid);// 业务类型
		json.put("subOpenId", pd.getString("openid"));// 用户子标识openid
		json.put("merOrderId", pd.getString("merOrderId"));// 商户订单号
		json.put("totalAmount", Long.parseLong(pd.get("pay_actualbalance").toString())*100);// 支付总金额，单位分*100
		json.put("requestTimestamp",Tools.date2Str(new Date()));
		json.put("divisionFlag",true);//分账标记 
		json.put("platformAmount",0);//平台商户分账金额
		json.put("subOrders", subOrdersList); //网吧 商户号和子商户分账金额
		json.put("goods", goodsList); // 商品信息 
		json.put("notifyUrl", PayConfig.notifyUrl); // 接收通知地址
		json.put("returnUrl", PayConfig.returnUrl); // 同步支付回跳地址
		//json.put("secureTransaction", true); //标识是否是担保交易
		System.out.println(json);
		String url = Util.makeOrderRequest(json, PayConfig.md5Key,
				PayConfig.apiUrl_makeOrder);
		System.out.println(url);
		// 告诉ajax我是重定向
		response.setHeader("REDIRECT", "REDIRECT");
		// 告诉ajax我重定向的路径
		response.setHeader("CONTEXTPATH", url);
		// 修改订单支付状态
		PageData pd1 = new PageData();
		pd1.put("merOrderId", pd.getString("merOrderId"));
		pd1.put("pay_state", 3);
		pd1.put("pay_starttime",Tools.date2Str(new Date()));
		pd.put("pd", pd1);
		generateOrderService.editOrderState(pd);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}

	/**
	 * 充值入口
	 * 
	 * @param merOrderId
	 *            订单号
	 * @throws Exception
	 */
	@RequestMapping(value = "/swrecharge")
	@ResponseBody
	public JSONObject swrecharge(String merOrderId) throws Exception {
		PageData tpd = new PageData();
		JSONObject json = new JSONObject();
		logBefore(logger, "充值入口"+merOrderId);
		json.put("result", "fail");
		PageData pd = new PageData();
		pd.put("merOrderId", merOrderId);
		PageData pd1 = new PageData();
		PageData findByIdormerOrderId = generateOrderService.findByIdormerOrderId(pd);
		if (findByIdormerOrderId == null) {
			return null;
		}

		System.out.println("=============="+findByIdormerOrderId.getString("pay_state"));
		if (!"b".equals(findByIdormerOrderId.getString("pay_state"))) {
			return null;
		}
		JSONObject param = new JSONObject();
        String carded = findByIdormerOrderId.getString("carded");
		String cardid = findByIdormerOrderId.getString("cardid");
		if(StringUtil.isEmpty(cardid)){
			Message2 m2 = userInfoService.getSWUser(findByIdormerOrderId.getString("store_id"), carded);
			if(m2.getErrcode() == 0){
				SWUser swUser = (SWUser) m2.getData().get("SWUser");
				if(swUser != null){
					cardid = swUser.getCard_id();
				}
			}
		}


		param.put("card_id", cardid);
		param.put("order_id", findByIdormerOrderId.getString("merOrderId"));
		param.put("pay_from", "lanker");
		param.put("memo", "揽客充值");
		param.put("amount", findByIdormerOrderId.get("rincipal_balance"));
		param.put("reward", findByIdormerOrderId.get("reward_balance"));
		param.put("allow_reward", 0);
		JSONObject rechargeResult = rechargeService.recharge(
				findByIdormerOrderId.getString("store_id"), param);
		pd.put("id", merOrderId);
		double totalAmount=Double.parseDouble(findByIdormerOrderId.get("rincipal_balance").toString())+Double.parseDouble(findByIdormerOrderId.get("reward_balance").toString());
		
		// 查询是否成功 0 成功
		if (rechargeResult.getString("errcode").equals("0")) {
			// 修改订单状态
			pd1.put("merOrderId", findByIdormerOrderId.getString("merOrderId"));
			pd1.put("return_code", rechargeResult.get("errmsg"));
			pd1.put("request_time", Tools.date2Str(new Date()));
			pd1.put("pay_state", 'a');
			pd.put("pd", pd1);
			generateOrderService.editOrderState(pd);
			System.out.println("充值成功");
			tpd.put("merOrderId", findByIdormerOrderId.getString("merOrderId"));
			System.out.println("查询 完成的订单");
			PageData findByIdormerOrderId2 = generateOrderService.findByIdormerOrderId(tpd);
			// 充值成功 删除 订单表 添加历史记录表
			System.out.println("删除订单"+ findByIdormerOrderId2.toString());
			generateOrderService.deleteOrder(findByIdormerOrderId2.getString("merOrderId"));
			System.out.println("添加订单"+ findByIdormerOrderId2.toString());
            findByIdormerOrderId2.put("third_payment", "1");//银联
			generateOrderService.addOrderHistory(findByIdormerOrderId2);
			System.out.println("推动消息"+ findByIdormerOrderId2.getString("openid"));
			tpd.put("open_id",findByIdormerOrderId2.getString("openid"));
            tpd.put("USER_ID", findByIdormerOrderId2.getString("user_id"));
			tpd.put("internet_id", findByIdormerOrderId2.getString("internet_id"));
			tpd.put("CARDED",findByIdormerOrderId2.getString("carded"));
			tpd.put("totalAmount", totalAmount);
			tpd.put("rewardAmount", Double.parseDouble(findByIdormerOrderId2.get("reward_balance").toString()));

			logBefore(logger, "充值推送" + tpd);
			generateOrderService.tuisong(tpd);
		}else {
			// 修改订单状态
			pd1.put("merOrderId", findByIdormerOrderId.getString("merOrderId"));
			pd1.put("return_code", rechargeResult.get("errmsg"));
			pd1.put("request_time", Tools.date2Str(new Date()));
			pd1.put("pay_state", 'b');
			pd.put("pd", pd1);
			generateOrderService.editOrderState(pd);
			System.out.println("修改订单状态  充值失败");
		}
		return null;
	}

	/**
	 * 接收通知的方法
	 *
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
	public void notifyHandle(HttpServletRequest request, PrintWriter writer)
			throws Exception {
		PageData tpd = new PageData();
		PageData pd = new PageData();
		PageData pd1 = new PageData();
		logBefore(logger, "接收通知");
		Map<String, String> params = Util.getRequestParams(request);
		System.out.println("接收通知返回参数：" + params);
		pd.put("merOrderId", params.get("merOrderId"));
		PageData findByIdormerOrderId = generateOrderService.findByIdormerOrderId(pd);

		// 查询订单状态
		JSONObject json = new JSONObject();
		json.put("mid", params.get("mid"));
		json.put("tid", params.get("tid"));
		json.put("msgType", PayConfig.msgType_query);
		json.put("msgSrc", PayConfig.msgSrc);
		json.put("instMid", params.get("instMid"));
		json.put("merOrderId", params.get("merOrderId"));
		JSONObject result = orderQuery(params.get("merOrderId"), json);
		JSONObject respStr = JSONObject.fromObject(result.get("respStr"));
		System.out.println(respStr.get("status"));
		boolean checkRet = NotifyUtilTest.checkSign2(PayConfig.md5Key, params);
		// 验证接收通知 是否是重复接收
		if (StringUtil.isEmpty(findByIdormerOrderId.get("pat_endtime"))) {
			if (checkRet) {
				System.out.println("验签成功");
				// 修改订单支付状态
				// 支付成功
				if ("TRADE_SUCCESS".equals(respStr.get("status"))) {
					System.out.println("支付成功");
					// 修改订单状态 充值成功
					pd1.put("merOrderId", params.get("merOrderId"));
					pd1.put("return_code", respStr.get("status"));
					pd1.put("pat_endtime", Tools.date2Str(new Date()));
					pd1.put("pay_state", 2);
					pd.put("pd", pd1);
					generateOrderService.editOrderState(pd);
					System.out.println("修改订单状态  支付成功");
					// 调取顺网接口充值
					JSONObject param = new JSONObject();
                    String carded = findByIdormerOrderId.getString("carded");
                    String cardid = findByIdormerOrderId.getString("cardid");
					if(StringUtil.isEmpty(cardid)){
						Message2 m2 = userInfoService.getSWUser(findByIdormerOrderId.getString("store_id"), carded);
						if(m2.getErrcode() == 0){
							SWUser swUser = (SWUser) m2.getData().get("SWUser");
							if(swUser != null){
								cardid = swUser.getCard_id();
							}
						}
					}

                    param.put("card_id", cardid);
					param.put("order_id",findByIdormerOrderId.getString("merOrderId"));
					param.put("pay_from", "lanker");
					param.put("memo", "揽客充值");
					param.put("amount",findByIdormerOrderId.get("rincipal_balance"));
					param.put("reward",findByIdormerOrderId.get("reward_balance"));
//					param.put("amount", 100);
//					param.put("reward", 3);
					param.put("allow_reward", 0);
					double totalAmount=Double.parseDouble(findByIdormerOrderId.get("rincipal_balance").toString())+Double.parseDouble(findByIdormerOrderId.get("reward_balance").toString());
					JSONObject rechargeResult = rechargeService.recharge(findByIdormerOrderId.getString("store_id"), param);
					System.out.println("充值  返回参数：" + rechargeResult.get("errcode").toString());
					System.out.println("调取充值接口");
					// 查询是否成功 0 成功
					if (rechargeResult.getString("errcode").equals("0")) {
							// 修改订单状态
							pd1.put("merOrderId", params.get("merOrderId"));
							pd1.put("return_code", rechargeResult.get("errmsg"));
							pd1.put("request_time", Tools.date2Str(new Date()));
							pd1.put("pay_state", 'a');
							pd.put("pd", pd1);
							generateOrderService.editOrderState(pd);
							System.out.println("充值成功");
							tpd.put("merOrderId", params.get("merOrderId"));
							System.out.println("查询 完成的订单");
							PageData findByIdormerOrderId2 = generateOrderService.findByIdormerOrderId(tpd);
							// 充值成功 删除 订单表 添加历史记录表
							System.out.println("删除订单"+ findByIdormerOrderId2.toString());
							generateOrderService.deleteOrder(findByIdormerOrderId2.getString("merOrderId"));
							System.out.println("添加订单"+ findByIdormerOrderId2.toString());

                            findByIdormerOrderId2.put("third_payment", "1");//银联
							generateOrderService.addOrderHistory(findByIdormerOrderId2);
							System.out.println("推动消息"+ findByIdormerOrderId2.getString("openid"));
							tpd.put("open_id",findByIdormerOrderId2.getString("openid"));
							tpd.put("internet_id", findByIdormerOrderId2.getString("internet_id"));
							tpd.put("card",findByIdormerOrderId2.getString("carded"));
							tpd.put("CARDED",findByIdormerOrderId2.getString("carded"));
							tpd.put("USER_ID",findByIdormerOrderId2.getString("user_id"));
							tpd.put("totalAmount", totalAmount);
							tpd.put("rewardAmount", Double.parseDouble(findByIdormerOrderId2.get("reward_balance").toString()));
							generateOrderService.tuisong(tpd);
						}else {
							// 修改订单状态
							pd1.put("merOrderId", params.get("merOrderId"));
							pd1.put("return_code", rechargeResult.get("errmsg"));
							pd1.put("request_time", Tools.date2Str(new Date()));
							pd1.put("pay_state", 'b');
							pd.put("pd", pd1);
							generateOrderService.editOrderState(pd);
							System.out.println("修改订单状态  充值失败");
						}
					System.out.println("查看充值是否成功");
				} else {
					// 修改订单状态
					pd1.put("merOrderId", params.get("merOrderId"));
					pd1.put("pat_endtime", Tools.date2Str(new Date()));
					pd1.put("pay_state", 1);
					pd.put("pd", pd1);
					generateOrderService.editOrderState(pd);
					System.out.println("修改订单状态  支付失败");
				}
			}
		}
		// 收到通知后记得返回SUCCESS
			writer.print("SUCCESS");
		writer.flush();
	}

	/**
	 * 回跳地址
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
	public ModelAndView goReturnUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("intenetmumber/payDetails");
		this.getRequest().setCharacterEncoding("ISO-8859-1");
		Map<String, String> params = Util.getRequestParams(request);
		// 查询订单状态
		JSONObject json = new JSONObject();
		json.put("mid", params.get("mid"));
		json.put("tid", params.get("tid"));
		json.put("msgType", PayConfig.msgType_query);
		json.put("msgSrc", PayConfig.msgSrc);
		json.put("instMid", params.get("instMid"));
		json.put("merOrderId", params.get("merOrderId"));
		JSONObject result = orderQuery(params.get("merOrderId"), json);
		JSONObject respStr = JSONObject.fromObject(result.get("respStr"));
		System.out.println(respStr.get("status"));
		// 查询订单状态
		pd.put("merOrderId", params.get("merOrderId"));
		pd = generateOrderService.findByIdormerOrderId(pd);
		System.out.println("pd===========" + pd);
		// 验签
		boolean checkRet = NotifyUtilTest.checkSign2(PayConfig.md5Key, params);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		if (checkRet) {
			// 跳转到你们自己的商城页面
			mv.addObject("errMsg", result.get("errMsg"));
			mv.addObject("status", params.get("status"));
			mv.addObject("store_name", pd.getString("store_name"));
			mv.addObject("pay_actualbalance", pd.get("pay_actualbalance"));
			mv.addObject("id", pd.get("id"));
			// 清理垃圾订单
			if ("WAIT_BUYER_PAY".equals(params.get("status"))) {
				PageData pd1 = new PageData();
				pd1.put("merOrderId", params.get("merOrderId"));
				pd1.put("return_code", params.get("status"));
				pd1.put("pay_state", 1);
				pd.put("pd", pd1);
				generateOrderService.editOrderState(pd);
			}
		}
		return mv;
	}

	/**
	 * 查询模块
	 */
	// 账单查询
	public JSONObject orderQuery(String merOrderId, JSONObject json) {
		System.out.println("请求参数对象：" + merOrderId);

		// 是否要在商户系统下单，看商户需求 createBill()
		json.put("requestTimestamp",Tools.date2Str(new Date()));

		Map<String, String> paramsMap = Util.jsonToMap(json);
		paramsMap.put("sign", Util.makeSign(PayConfig.md5Key, paramsMap));
		System.out.println("paramsMap：" + paramsMap);

		String strReqJsonStr = JSON.toJSONString(paramsMap);
		System.out.println("strReqJsonStr:" + strReqJsonStr);

		// 调用银商平台获取二维码接口
		HttpURLConnection httpURLConnection = null;
		BufferedReader in = null;
		PrintWriter out = null;
		// OutputStreamWriter out = null;
		JSONObject resultStr = null;
		Map<String, String> resultMap = new HashMap<String, String>();
		if (!StringUtils.isNotBlank(PayConfig.APIurl)) {
			resultMap.put("errCode", "URLFailed");
			resultStr = JSONObject.fromObject(resultMap);
			return resultStr;
		}

		try {
			URL url = new URL(PayConfig.APIurl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Content_Type",
					"application/json");
			httpURLConnection.setRequestProperty("Accept_Charset", "UTF-8");
			httpURLConnection.setRequestProperty("contentType", "UTF-8");
			// 发送POST请求参数
			out = new PrintWriter(httpURLConnection.getOutputStream());
			// out = new
			// OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
			out.write(strReqJsonStr);
			// out.println(strReqJsonStr);
			out.flush();

			// 读取响应
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = null;
				in = new BufferedReader(new InputStreamReader(
						httpURLConnection.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				System.out.println("content:" + content.toString());

				// 转换成json对象
				com.alibaba.fastjson.JSONObject respJson = JSON
						.parseObject(content.toString());
				String resultCode = respJson.getString("errCode");
				resultMap.put("errCode", resultCode);
				resultMap.put("respStr", respJson.toString());
				resultStr = JSONObject.fromObject(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errCode", "HttpURLException");
			resultMap.put("msg", "调用银商接口出现异常：" + e.toString());
			resultStr = JSONObject.fromObject(resultMap);
			return resultStr;
		} finally {
			if (out != null) {
				out.close();
			}
			httpURLConnection.disconnect();
		}

		System.out.println("resultStr:" + resultStr);
		return resultStr;
	}

	// /**
	// * 查看订单列表
	// * @return
	// * @throws Exception
	// */
	// @RequestMapping(value="/listOrder")
	// public ModelAndView listOrder(Page page) throws Exception{
	// ModelAndView mv = this.getModelAndView();
//    User user = this.getUser();//获取用户
	// PageData pd = new PageData();
	// //判断用户是否为空
	// if (StringUtil.isEmpty(user)) {
	// mv.setViewName("userNull");
	// return mv;
	// }
	//
	// pd.put("user_id", user.getUSER_ID());
	// //清理垃圾订单
	// generateOrderService.deleteFailOrder(user.getUSER_ID());
	// page.setPd(pd);
	// List<PageData> listPage = generateOrderService.list(page);
	// mv.setViewName("");
	// mv.addObject("listPage", listPage);
	// return mv;
	// }
	// /**
	// * 查看订单详情
	// * @param id 订单主键
	// * @return
	// * @throws Exception
	// */
	// @RequestMapping(value="/orderinfo")
	// public ModelAndView findByIdorder(String id) throws Exception{
	// ModelAndView mv = this.getModelAndView();
//    User user = this.getUser();//获取用户
	// //判断用户是否为空
	// if (StringUtil.isEmpty(user)) {
	// mv.setViewName("userNull");
	// return mv;
	// }
	// Recharge recharge = generateOrderService.findByIdorder(id);
	// mv.setViewName("");
	// mv.addObject("recharge", recharge);
	// return mv;
	// }
	// ********************************************会员卡充值***************************************

	/**
	 * 签到有礼
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/singInOne")
	@ResponseBody
	public JSONObject singInOne() throws Exception {
		JSONObject result = new JSONObject();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			result.put("message", "无效的用户");
			return result;
		}

		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID()); // 用户id
		pd.put("internet_id", user.getINTENET_ID());

		// 签到
		result = indexMemberService.signIn(pd);

		return result;
	}

	/**
	 * 是否可以邀请好友
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/canShare")
	@ResponseBody
	public JSONObject canShare() throws Exception {
		JSONObject result = new JSONObject();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}

		PageData pd = new PageData();
		pd.put("internet_id", user.getINTENET_ID());
		// 检查是否设置分享
		result = indexMemberService.canShare(pd);

		return result;
	}

	/**
	 * 邀请好友页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/share")
	public ModelAndView share() throws Exception {

		ModelAndView mv = this.getModelAndView();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}
		String url = this.getUrl();

		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("url", url);
		// 获取分享的设置数据
		mv = indexMemberService.share(pd);

		mv.addObject("domain", PublicManagerUtil.URL1);
		mv.setViewName("intenetmumber/share");
		return mv;
	}

	/**
	 * 好友点开分享链接，获取公众号二维码
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/laodaixin")
	public ModelAndView laodaixin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		String userId = this.getRequest().getParameter("userId");
		PageData pd = new PageData();
		pd.put("USER_ID", userId);
		pd = this.userService.findById(pd);

		Intenet org = intenetService.getIntenetById(pd.getString("INTENET_ID"));
		PageData person = new PageData();
		person = wechatuserService.findByUserId(pd);

        // 老带新分享链接码(公众号id)
        String shareUrl1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + org.getWECHAT_ID();
        // 老带新重定向链接
        String shareUrl2 = "&redirect_uri="+PublicManagerUtil.URL1+"indexMember/oldwith.do&response_type=code&scope=snsapi_userinfo&state=" + userId;
        String shareUrl3 = "&component_appid="+PublicManagerUtil.APPID+"#wechat_redirect";


		String url = shareUrl1 + shareUrl2 + shareUrl3;


		mv.addObject("userId", userId);
		mv.addObject("url", url);
		mv.addObject("nickname",
				URLDecoder.decode(person.getString("NECK_NAME"), "utf-8"));
		mv.addObject("urlImg", person.getString("IMGURL"));
		mv.addObject("intenetName", org.getINTENET_NAME());
		PageData pdCard = new PageData();
		pdCard.put("intenetId", org.getINTENET_ID());
		pdCard.put("cardType", FAV_TYPE1);
		pdCard = cardService.findByCenece(pdCard);
		mv.addObject("SUB_TITLE", pdCard.get("SUB_TITLE"));
		mv.setViewName("intenetmumber/laodaixin");
		return mv;
	}

	/**
	 * 获取公众号二维码之前，先保存微信用户信息，跳转到二维码页面，让用户关注
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/oldwith")
	public ModelAndView oldwith() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		String userId = this.getRequest().getParameter("state");
		PageData pd = new PageData();
		pd.put("USER_ID", userId);
		pd = this.userService.findById(pd);
		Intenet org = intenetService.getIntenetById(pd.getString("INTENET_ID"));

		/********* 获取授权的access_token ************/
		String code = this.getRequest().getParameter("code");
		System.out.println("code--------------" + code);
		PageData pdTerrace = new PageData();
		pdTerrace.put("INTENET_ID", org.getINTENET_ID());
		pdTerrace = terraceService.findByInternetId(pdTerrace);
		PageData pdMyop = new PageData();
		pdMyop.put("APPID", PublicManagerUtil.APPID);
		pdMyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
		pdMyop = this.myopService.findByAppId(pdMyop);
		JSONObject jsonObject = WeChatOpenUtil.getOpenAccessToken(
				pdTerrace.getString("APP_ID"), code, pdMyop.getString("APPID"),
				pdMyop.getString("COMPONENT_ACCESS_TOKEN"));

		String openId = jsonObject.getString("openid");
		System.out.println("拿到用户的openid------ " + openId);
		/**************************/
		String token = jsonObject.getString("access_token");

		User user = new User();
		user = sysuserService.getUserByOpenId(openId);
		if (user == null) {
			WechatUser person = WeixinUtil.getUserInfoByBgz(token, openId);
			String userName = WeixinUtil.generateSequenceNo();

			//系统用户
			PageData pdUser = new PageData();
			pdUser.put("USER_ID", this.get32UUID());
			pdUser.put("LAST_LOGIN", ""); // 最后登录时间
			pdUser.put("IP", ""); // IP
			pdUser.put("STATUS", "2"); // 状态
			pdUser.put("SKIN", "default");
			pdUser.put("RIGHTS", "");
			pdUser.put("PASSWORD", new SimpleHash("SHA-1",
					PublicManagerUtil.PASSWORD).toString()); // 密码加密
			pdUser.put("USERNAME", userName);
			pdUser.put("ANGET_ID", org.getANGET_ID());
			pdUser.put("INTENET_ID", org.getINTENET_ID());
			pdUser.put("EMAIL", "");
			pdUser.put("NAME",
					URLEncoder.encode(person.getNECK_NAME(), "utf-8"));
			pdUser.put("ROLE_ID", PublicManagerUtil.PROMOTERROLEID);
			pdUser.put("INTEGRAL", 0);
			userService.saveU(pdUser);
            sysuserService.getByUserId(pdUser.getString("USER_ID"));//放入缓存

            //微信用户
			PageData wechatUser = new PageData();
			wechatUser.put("WECHAT_ID", this.get32UUID()); // 主键
			wechatUser.put("USER_ID", pdUser.get("USER_ID")); // 用户id
			wechatUser.put("OPEN_ID", person.getOPEN_ID()); // 公众号唯一id
			wechatUser.put("INTENET_ID", org.getINTENET_ID()); // 网吧id
			wechatUser.put("NECK_NAME",
					URLEncoder.encode(person.getNECK_NAME(), "utf-8")); // 昵称
			wechatUser.put("SEX", person.getSEX()); // 性别
			wechatUser.put("PROVICNE", person.getPROVICNE()); // 省
			wechatUser.put("CITY", person.getCITY()); // 城市
			wechatUser.put("CREATE_TIME",
					Tools.formatTime(person.getCREATE_TIME())); // 关注时间
			wechatUser.put("STATE", "1"); // 状态1关注 0取消关注
			wechatUser.put("IMGURL", person.getIMGURL()); // 状态1关注 0取消关注
			wechatuserService.save(wechatUser);
            sysuserService.getByOpenId(person.getOPEN_ID());//放入缓存

            //老带新
			PageData oldwith = new PageData();
			Date date = new Date();
			oldwith.put("OLDWITH_ID", this.get32UUID()); // 主键
			oldwith.put("USER_ID", pdUser.get("USER_ID")); // 用户id
			oldwith.put("STATE", "0"); // 状态
			oldwith.put("OLD_ID", userId); // 老用户id
			oldwith.put("CARD_ID", userName); // 获取老带新码
			oldwith.put("CREATE_TIME", Tools.date2Str(new Date())); // 时间
			oldwithService.save(oldwith);
			long timestamp = date.getTime() / 1000;

			String auth_token = autoReplyService.getAuthToken(org.getINTENET_ID());
			String jsapi_ticketNew = WeixinUtil.getJsTicket(auth_token);

			PageData pdCard = new PageData();
			pdCard.put("intenetId", org.getINTENET_ID());
			pdCard.put("cardType", FAV_TYPE1);
			pdCard = cardService.findByCenece(pdCard);
			if (pdCard != null) {
				WechatCardUtil.putGzCard(auth_token, jsapi_ticketNew,person.getOPEN_ID(), String.valueOf(timestamp), "",pdCard.getString("CARD_ID"));
			}
			mv.addObject("nickname",
					URLDecoder.decode(person.getNECK_NAME(), "utf-8"));
			mv.addObject("url", person.getIMGURL());
		} else {

			PageData person = new PageData();
			person.put("USER_ID", user.getUSER_ID());
			person = wechatuserService.findByUserId(person);
			mv.addObject("nickname",
					URLDecoder.decode(person.getString("NECK_NAME"), "utf-8"));
			mv.addObject("url", person.getString("IMGURL"));
		}
		PageData pdInvite = new PageData();
		pdInvite.put("INTENET_ID", org.getINTENET_ID());
		pdInvite = inviteService.findByInternetId(pdInvite);
		mv.addObject("BARCODE", pdInvite.get("BARCODE"));
		/*
		 * pdInvite = intenetService.findById(pdInvite);
		 * mv.addObject("QRCODE_URL", pdInvite.get("QRCODE_URL"));
		 */
		mv.addObject("intenetName", org.getINTENET_NAME());
		mv.setViewName("intenetmumber/wechatGz");
		return mv;
	}

	/**
	 * 去分享的二维码页面（估计没用到）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wechatGz")
	public ModelAndView wechatGz() throws Exception {
		ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//获取用户

		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}

		Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
		mv.addObject("intenetName", org.getINTENET_NAME());
		PageData pdInvite = new PageData();
		pdInvite.put("INTENET_ID", user.getINTENET_ID());
		pdInvite = inviteService.findByInternetId(pdInvite);
		mv.addObject("BARCODE", pdInvite.get("BARCODE"));
		mv.setViewName("intenetmumber/wechatGz");

		return mv;
	}

	/**
	 * 抢先订座页面
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/book")
	public ModelAndView book() throws Exception {
		ModelAndView mv = this.getModelAndView();        
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}

		Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
		mv.addObject("intenetName", org.getINTENET_NAME());

		// 判断用户是否绑定
		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/indexMember/book.do");
		mv = indexMemberService.judgeBind(pd);
		if (mv.getModel().get("result").toString().equals("false")) {
			// 门店被禁用时，去换绑页面
			if (StringUtil.isNotEmpty(mv.getModel().get("store_state"))
					&& mv.getModel().get("store_state").toString().equals("1")) {
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}

		mv.setViewName("intenetmumber/schedule");
		return mv;
	}

	/**
	 * 客户订座
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/userbook")
	@ResponseBody
	public JSONObject userbook() throws Exception {
		JSONObject result = new JSONObject();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		if (StringUtil.isEmpty(user)) {
			result.put("message", "无效的用户");
			return result;
		}

		String num = this.getRequest().getParameter("num");
		String lian = this.getRequest().getParameter("lian");
		String rzsj = this.getRequest().getParameter("rzsj");
		String radio2 = this.getRequest().getParameter("radio2");
		PageData pd = new PageData();
		pd.put("SYSBOOK_ID", this.get32UUID()); // 主键
		pd.put("USER_ID", user.getUSER_ID()); // 用户id
		pd.put("INTEGER_ID", user.getINTENET_ID()); // 所属网吧
		pd.put("BOOK_NUMBER", num);
		pd.put("BOOK_STATE", lian);
		pd.put("BOOK_TIME", rzsj);
		pd.put("BOOK_TYPE", radio2);
		sysbookService.save(pd);
		result.put("message", PublicManagerUtil.SUCCESS);
		return result;
	}

	/**
	 * 会员福利领取界面
	 * 
	 * @param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/benefits")
	public ModelAndView benefits() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}


		// 判断用户是否绑定
		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/indexMember/benefits.do");
		mv = indexMemberService.judgeBind(pd);
		if (mv.getModel().get("result").toString().equals("false")) {
			// 门店被禁用时，去换绑页面
			if (StringUtil.isNotEmpty(mv.getModel().get("store_state"))
					&& mv.getModel().get("store_state").toString().equals("1")) {
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}


        //更新顺网用户数据
        new Thread(new MyMemberService.updateSWUser(user.getUSER_ID())).start();

		mv.addObject("pd", pd);
		mv.setViewName("intenetmumber/benefit");
		return mv;
	}


    /**
     * 加载卡券列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadCardList")
	public Message loadCardList(@RequestParam(value = "type") String type) throws Exception{

        // 获取卡券列表
        Message m  = indexMemberService.getCardList(type);

        return m ;
    }

	/**
	 * 领取福利(需要进行门店判断)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/receiveBenefits")
	@ResponseBody
	public JSONObject receiveBenefits() throws Exception {
		JSONObject json = new JSONObject();
		JSONObject result = new JSONObject();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		if (StringUtil.isEmpty(user)) {
			result.put("message", "无效的用户");
			return result;
		}

		// 传入mayId
		PageData pd = this.getPageData();
		pd.put("card_id", pdUser.getString("mayId"));
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		System.out.println("卡券领取---" + pd.toString());
		//接下来通过卡券id,用户id去判断,该卡券是否适用于该用户的门店
		if(StringUtil.isEmpty(pdUser.getString("falgs"))){
			json = indexMemberService.judgeStore(pd);
			if(json.getString("card_result").equals("false")) {//不适用
				return json;
			}
		}
		result = indexMemberService.receiveCard(pd);
		return result;
	}


	/**
	 * 会员点击领取，直接跳转微信sdk卡券页面领取
	 * @return
	 */
	@RequestMapping(value = "/goWechatCard")
	public ModelAndView goWechatCard() throws Exception{
		ModelAndView mv = this.getModelAndView();

		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}

		//传入card_id
		PageData pd = this.getPageData();
		pd.put("object_id", pd.getString("card_id"));
		pd.put("USER_ID", user.getUSER_ID());
		pd.put("open_id", wechatuserService.findByUserId(pd).getString("OPEN_ID"));


		// 获取js-sdk 卡券接口的相关信息
		pd.put("internet_id", user.getINTENET_ID());
        String url = this.getUrl();

		pd.put("url", url);
		JSONObject wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
		mv.addObject("wx", wx);
		mv.setViewName("intenetmumber/receiveCard");
		return mv;
	}
	
	/**
	 * 我的卡券点击使用
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/goWechatCard1")
	public JSONObject goWechatCard1() throws Exception{
		JSONObject result = new JSONObject();


		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}

		PageData pd = this.getPageData();
		pd.put("object_id", pd.getString("card_id"));
		pd.put("internet_id", user.getINTENET_ID());
		JSONObject wx = new JSONObject();
		if("myCard".equals(pd.getString("state"))){
			//传入url,card_id,code

			// 获取js-sdk 卡券接口的相关信息
			wx = memberMarkeService.getCard_Ext1(pd);// 获取card_ext
		}else if("benefit".equals(pd.getString("state"))){
			//传入url,card_id
			pd.put("USER_ID", user.getUSER_ID());
			pd.put("open_id", wechatuserService.findByUserId(pd).getString("OPEN_ID"));
			wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
		}


		//通过卡券id去获取卡券code
		result.put("pd", pd);
		result.put("wx", wx);
		result.put("result", "true");
		return result;
	}



	/**
	 * 积分商城页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/mall")
	public ModelAndView mall(String computer_name) throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}
		Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
		mv.addObject("intenetName", org.getINTENET_NAME());

		// 判断用户是否绑定
		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/indexMember/mall.do");
		mv = indexMemberService.judgeBind(pd);
		if (mv.getModel().get("result").toString().equals("false")) {
			// 门店被禁用时，去换绑页面
			if (StringUtil.isNotEmpty(mv.getModel().get("store_state"))
					&& mv.getModel().get("store_state").toString().equals("1")) {
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}

		if(StringUtil.isNotEmpty(computer_name)){
            mv.addObject("computer_name", computer_name);
        }
		// 加载积分商城列表
		JSONObject result2 = indexMemberService.loadMall(pd);

		mv.addObject("INTEGRAL", result2.get("INTEGRAL"));
		mv.addObject("varList", result2.get("varList"));
		mv.setViewName("intenetmumber/mall");
		return mv;
	}

	/**
	 * 积分兑换请求
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/sqdh")
	@ResponseBody
	public JSONObject sqdh() throws Exception {
		JSONObject result = new JSONObject();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		if (StringUtil.isEmpty(user)) {
			result.put("message", "无效的用户");
			return result;
		}

		// 传入Auction_ID
		PageData pd = this.getPageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		result = indexMemberService.applyAuction(pd);

		return result;
	}

	/**
	 * 是否可以进行抽奖
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/canChouJiang")
	@ResponseBody
	public JSONObject canChouJiang() throws Exception {
		JSONObject result = new JSONObject();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);

		PageData pd = new PageData();
		pd.put("internet_id", user.getINTENET_ID());
		result = indexMemberService.canLottery(pd);

		return result;
	}

	/**
	 * 手气抽奖界面
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/activityGame")
	public ModelAndView activityGame() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}
		Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
		mv.addObject("intenetName", org.getINTENET_NAME());

		// 判断用户是否绑定
		PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/indexMember/activityGame.do");
		mv = indexMemberService.judgeBind(pd);
		if (mv.getModel().get("result").toString().equals("false")) {
			// 门店被禁用时，去换绑页面
			if (StringUtil.isNotEmpty(mv.getModel().get("store_state"))
					&& mv.getModel().get("store_state").toString().equals("1")) {
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}

		// 加载抽奖奖品
		JSONObject result2 = indexMemberService.loadLottery(pd);

		mv.addObject("cj", result2.get("cj"));
		mv.addObject("number", result2.get("number"));
		mv.setViewName("intenetmumber/activityGame");
		return mv;
	}

	/**
	 * 客户抽奖
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/choujiang")
	@ResponseBody
	public JSONObject choujiang() throws Exception {
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);

		PageData pd = new PageData();
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("user_id", user.getUSER_ID());
		JSONObject result = indexMemberService.applyLottery(pd);
		return result;
	}

	/**
	 * 投诉建议页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/gotoLm")
	public ModelAndView gotoLm() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}
		Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
		mv.addObject("intenetName", org.getINTENET_NAME());

		PageData pd = this.getPageData();

		// 获取投诉类型
		PageData pdType = new PageData();
		pdType.put("dictType", "LM001");
		List<PageData> tlist = dictentyService.listAll(pdType);
		mv.addObject("tlist", tlist);

		// 获取门店列表
		pd.put("internetId", user.getINTENET_ID());
		List<PageData> storeList = storeService.listByIntenet(pd);
		if (storeList.size() > 0) {
			mv.addObject("storeList", storeList);
		}

        String computer_name = this.getRequest().getParameter("computer_name");
		if(StringUtil.isNotEmpty(computer_name)){
            mv.addObject("computer_name", computer_name);
        }

		mv.setViewName("intenetmumber/leaveMessageEdit");
		return mv;
	}

	/**
	 * 保存投诉和建议
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveLm")
	@ResponseBody
	public JSONObject saveLm(String STORE_ID, String LM_TYPEID, String LM_TYPENAME, String computer_name,
			String LM_CONTENT) throws Exception {
		// 测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);

		// 传入LM_CONTENT，LM_TYPEID，STORE_ID
		PageData pd = new PageData();
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("user_id", user.getUSER_ID());
		pd.put("name", user.getNAME());

		pd.put("STORE_ID", STORE_ID);
		pd.put("LM_TYPEID", LM_TYPEID);
		pd.put("LM_CONTENT", LM_CONTENT);
		pd.put("LM_TYPENAME", LM_TYPENAME);
		pd.put("computer_name", computer_name);
		String[] file = this.getRequest().getParameterValues("upimg");// 传入图片数组
		// String[] file = upimg;//传入图片数组
		pd.put("fileArr", file);

		// 保存投诉
		JSONObject result = indexMemberService.saveLm(pd);
		// return "redirect:/myMember/myLm";

		return result;
	}

	@RequestMapping(value = "callServer")
    @ResponseBody
	public JSONObject callServer(String computer_name) throws Exception{
        JSONObject result = this.getErrorJson();

        // 测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);

        result = indexMemberService.callStoreStaff(user.getUSER_ID(), user.getINTENET_ID(),computer_name, user.getNAME());
        return result;
    }


	/**
	 * 扫描二维码后，网吧服务人员绑定页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userStaffBind")
	public ModelAndView userStaffBind(Model model)throws Exception{
		ModelAndView mv = new ModelAndView();

		//传入参数（上机参数：appid，state，code,component_appid,uuid）
		PageData pd = this.getPageData();
		log.info("userStaffBind--pd的参数"+pd.toString());

		//uuid无效扫码

		//判断是否关注公众号
		JSONObject result = indexMemberService.checkUserSubscribe(pd.getString("appid"), pd.getString("code"), "qr_subscribe_", "");//无场景值
		mv.addObject("result", result);
//		mv.setViewName("internet/qrCode/user_subscribe");

		mv.setViewName("internet/internetStaff/staff_bind");
		return mv;
	}

	/**
	 * 编辑保存信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStaff")
	@ResponseBody
	public JSONObject saveStaff() throws Exception{
		JSONObject result = getErrorJson();

		Session session = Jurisdiction.getSession();
        User user = this.getUser();//获取用户
		if(user == null){
			result.put("message", "用户为空");
			return result;
		}

		//传入参数
		PageData pd = this.getPageData();
		String[] perms = this.getRequest().getParameterValues("perms");
		if(pd.getString("STORE_ID") == null){
			result.put("message", "请选择门店");
			return result;
		}
		pd.put("store_id", pd.getString("STORE_ID"));
		if(perms.length == 0){
			result.put("message", "请选择服务项");
			return result;
		}
		pd.put("open_id", (String) session.getAttribute("user_open_id"));

		pd.put("internet_id", user.getINTENET_ID());
		return internetStaffService.saveStaff(pd, perms);
	}


	/**
	 * 服务人员 -- 去处理模板消息页面
	 * @throws Exception
	 */
	@RequestMapping(value = "goHandleTamplete")
	public ModelAndView goHandleTamplete(String type, String open_id, String id, String record_id) throws Exception{
		ModelAndView mv = this.getModelAndView();

		//更新缓存用户信息
        sysuserService.getUserByOpenId(open_id);

        PageData pdRecord = templateRecordService.findById(record_id);
		if(type.equals("user_call")){

            mv.addObject("title", pdRecord.getString("first_data"));
            mv.addObject("pd", JSONObject.fromObject(pdRecord.getString("keyword_data")));
            mv.addObject("state", pdRecord.getString("h_state"));
			mv.setViewName("intenetmumber/handleTemp/user_call");
		}else if(type.equals("user_complain")){

            LeaveMessage lm = lmService.getOneByLMID(id);
            lm.setLM_USERNAME(URLDecoder.decode(lm.getLM_USERNAME(), "utf-8"));

            PageData pdUser = new PageData();
            pdUser.put("userId", lm.getLM_USERID());
            pdUser = bunduserService.findByUser(pdUser);
            if(StringUtil.isNotEmpty(pdUser)){
                lm.setLM_USERNAME(pdUser.getString("NAME"));
            }

            mv.addObject("title", pdRecord.getString("first_data"));
            mv.addObject("lm", lm);
			mv.setViewName("intenetmumber/handleTemp/user_complain");
		}else if(type.equals("user_order")){

            PageData pdOrder = new PageData();
            pdOrder.put("ORDER_ID", id);
            pdOrder = orderService.findById(pdOrder);
            mv.addObject("state", pdOrder.getString("STATE"));
            mv.addObject("id", pdOrder.getString("ORDER_ID"));

            JSONObject keywordData = JSONObject.fromObject(pdRecord.getString("keyword_data"));
            String keyword1 = keywordData.getString("keyword1");
            mv.addObject("name", keyword1.split("，")[0]);
            mv.addObject("count", keyword1.split("，")[1]);
            mv.addObject("title", pdRecord.getString("first_data"));

            pdOrder.put("userId", pdOrder.getString("USER_ID"));
            PageData pdBund = bunduserService.findByUser(pdOrder);
            if(pdBund != null){
                keywordData.put("phone", pdBund.getString("PHONE"));
            }

            mv.addObject("pd", keywordData);
			mv.setViewName("intenetmumber/handleTemp/user_order");
		}
        mv.addObject("record_id", record_id);
		return mv;
	}



	/**
	 * 服务人员 -- 处理模板消息
	 * @throws Exception
	 */
	@RequestMapping(value = "handleTamplete")
	@ResponseBody
	public JSONObject handleTamplete(String type) throws Exception{
        JSONObject result = this.getErrorJson();

        //record_id,type
        PageData pd = this.getPageData();
        if(StringUtil.isEmpty(pd.getString("record_id")) && StringUtil.isEmpty(pd.getString("type"))){
            result.put("message", "参数错误");
            return result;
        }

        User user = this.getUser();//获取用户
        if(type.equals("user_call")){

            PageData pdRecord = new PageData();
            pdRecord.put("h_state", "1");//已处理
            pdRecord.put("update_time", Tools.date2Str(new Date()));
            pdRecord.put("id", pd.getString("record_id"));
            templateRecordService.update(pdRecord);
        }else if(type.equals("user_complain")){
            //BACKCONTENT,
            if(StringUtil.isEmpty(pd.getString("BACKCONTENT"))){
                result.put("message", "请输入回复内容");
                return result;
            }
            //判断是否已处理
            LeaveMessage llm = lmService.getOneByLMID(pd.getString("id"));
            if(llm.getLM_STATE().equals("1")){
                result.put("message", "该投诉已处理");
                return result;
            }

            pd.put("LM_ID", pd.getString("id"));
            pd.put("LM_STATE", "1");//已处理
            pd.put("LM_BACKTIME", Tools.date2Str(new Date()));//已处理
            pd.put("USER_ID", user.getUSER_ID());//处理人
            lmService.updateLm(pd);

            PageData pdRecord = new PageData();
            pdRecord.put("h_state", "1");//已处理
            pdRecord.put("update_time", Tools.date2Str(new Date()));
            pdRecord.put("id", pd.getString("record_id"));
            templateRecordService.update(pdRecord);
        }else if(type.equals("user_order")){

            //判断是否已提货
            PageData pdOrder = new PageData();
            pdOrder.put("ORDER_ID", pd.getString("id"));
            pdOrder = orderService.findById(pdOrder);
            if(pdOrder.getString("STATE").equals("3")){
                result.put("message", "该商品已提货");
                return result;
            }

            pdOrder = new PageData();
            pdOrder.put("ORDER_ID", pd.getString("id"));
            pdOrder.put("SEND_TIME", Tools.date2Str(new Date()));
            pdOrder.put("STATE", "3"); // 1:未提货，2提货中；3提货成功
            orderService.edit(pdOrder);

            PageData pdRecord = new PageData();
            pdRecord.put("h_state", "1");//已处理
            pdRecord.put("update_time", Tools.date2Str(new Date()));
            pdRecord.put("id", pd.getString("record_id"));
            templateRecordService.update(pdRecord);
        }

        result.put("result", "true");
		return result;
	}


    /**
     * 发起嘉联支付
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value = "/jlPay")
	public Message jlPay() throws Exception {


		//传入参数(id)
		PageData pd = this.getPageData();

        //查询订单信息
        PageData pdOrder = generateOrderService.findByIdormerOrderId(pd);
        PageData pdJL = jiaLianService.findByStoreId(pdOrder.getString("store_id"));

        if(!pdJL.get("status").equals("1") && StringUtil.isEmpty(pdJL.getString("shop_secret"))){
            return Message.error("该门店尚未通过商户审核，在线充值无法使用");
        }

        PreOrder preOrder = new PreOrder();
        preOrder.setShop_no(pdJL.getString("shop_no"));
        preOrder.setShop_order_no(pdOrder.getString("merOrderId"));

        String pay_actualbalance = pdOrder.get("pay_actualbalance").toString();
        int pay = (int) (Float.parseFloat(pay_actualbalance) * 100);

        String song_mon = pdOrder.get("reward_balance").toString();
        preOrder.setTransaction_amount(pay);
        preOrder.setOrder_remark("揽客充值：充"+pay+"元，送"+song_mon+"元。");
        preOrder.setReturn_url(YikeConfig.order_return_url);//支付成功后跳转地址
        preOrder.setCallback_url(YikeConfig.order_callback_url);//回调地址
        preOrder.setTimestamp(String.valueOf(new Date().getTime()));//时间戳
        preOrder.setOrder_name("揽客充值");//订单名称
        preOrder.setSign(PayDemo.getSign(preOrder, pdJL.getString("shop_secret")));//签名
        preOrder.setClient_ip(YikeConfig.client_ip);//接入系统终端ip地址（不参与签名）

        System.out.println("******************************----------------");

        //下单
        Message m = JiaLianUtils.unifiedOrder(preOrder);

        //修改订单状态为支付中
        if(m.getErrcode() == 0){
            PageData pd1 = new PageData();
            pd1.put("merOrderId", pdOrder.getString("merOrderId"));
            pd1.put("pay_state", "3");//支付中
            pd1.put("pay_starttime",Tools.date2Str(new Date()));//开始支付时间
            pd.put("pd", pd1);
            generateOrderService.editOrderState(pd);
        }

        return m.addData("third_payment", "2");//嘉联
	}


    /**
     * 嘉联-支付成功跳转地址
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/jLReturn")
	public String jLReturn() throws Exception{

        PageData pd = this.getPageData();
        log.info("jLReturn：：：" + pd.toString());

        //支付成功，同步回调(嘉联回调功能还在开发)
        return "redirect:/indexMember/payDetails2?status=TRADE_SUCCESS";
    }

	@RequestMapping(value = "/payDetails2")
	public ModelAndView payDetails2(String status,
                                    String store_name, String pay_actualbalance) throws Exception{
		ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//获取用户

		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}

		mv.setViewName("intenetmumber/payDetails");
		mv.addObject("store_name", store_name);
		mv.addObject("pay_actualbalance", pay_actualbalance);
		mv.addObject("third_payment", "2");
		mv.addObject("status", status);
		return mv;
	}



    /**
     * 嘉联——回调通知地址
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/jLCallBack")
	public void jLCallBack(PrintWriter writer) {

        try{
            //接收响应参数
            PageData pd = this.getPageData();
            log.info("嘉联——异步接收-jLCallBack:::" + pd.toString());

            //返回封装类
            int order_status = Integer.parseInt(pd.getString("orderStatus"));
            String shop_order_no = pd.getString("shopOrderNo");

            pd.put("merOrderId", shop_order_no);
            PageData pdOrder = generateOrderService.findByIdormerOrderId(pd);
            if(StringUtil.isEmpty(pdOrder)){
                log.info(shop_order_no + "————查找不到该订单");
                writer.write("success");
                return;
            }

            PageData pd1 = new PageData();
            pd1.put("merOrderId", shop_order_no);
            pd1.put("pat_endtime", Tools.date2Str(new Date()));
            if (order_status == 1 && !pdOrder.getString("pay_state").equals("2")){
                //对支付订单成功，且未完成支付的订单进行处理
                log.info(shop_order_no + "订单支付成功");

                //验证签名是否正确
				if(StringUtil.isNotEmpty(pd.get("sign"))){
					PageData pdJl = new PageData();
					pdJl = jiaLianService.findByStoreId(pdOrder.getString("store_id"));
					if(!checkSign(pd, pdJl.getString("shop_secret"))){
						log.info(shop_order_no + "————签名错误");
						writer.write("fail");
						return ;
					}
				}
                String order_result = pd.getString("result");
                String complete_date = pd.getString("completeDate");

                //修改订单状态
                pd1.put("pat_endtime", complete_date);
                pd1.put("return_code", order_result);//返回状态码
                pd1.put("pay_state", 2);//支付成功
                pd.put("pd", pd1);
                generateOrderService.editOrderState(pd);

                //会员卡号
                String cardid = pdOrder.getString("cardid");
                if(StringUtil.isEmpty(cardid)){
					Message2 m2 = userInfoService.getSWUser(pdOrder.getString("store_id"), pdOrder.getString("carded"));
					if(m2.getErrcode() == 0){
						SWUser swUser = (SWUser) m2.getData().get("SWUser");
						if(swUser != null){
							cardid = swUser.getCard_id();
						}
					}
				}

                SWRecharge swRecharge = new SWRecharge();
                swRecharge.setCard_id(cardid);
                swRecharge.setOrder_id(shop_order_no);
                swRecharge.setPay_from("lanker");
                swRecharge.setMemo("揽客充值");
                swRecharge.setAmount(Double.parseDouble(pdOrder.get("rincipal_balance").toString()));
                swRecharge.setReward(Double.parseDouble(pdOrder.get("reward_balance").toString()));
                swRecharge.setAllow_reward(0);

                //调顺网充值接口充值
                Message2 m2 = rechargeService.newRecharge(pdOrder.getString("store_id"), swRecharge);
                if (m2.getErrcode() == 0) {
                    log.info(shop_order_no + "顺网充值成功");

                    // 修改订单状态
                    pd1.put("return_code", m2.getErrmsg());
                    pd1.put("request_time", Tools.date2Str(new Date()));//顺网充值时间
                    pd1.put("pay_state", 'a');
                    pd.put("pd", pd1);
                    generateOrderService.editOrderState(pd);


                    PageData pdOrder2 = generateOrderService.findByIdormerOrderId(pd);
                    // 充值成功 删除 订单表 添加历史记录表
                    System.out.println("删除订单"+ pdOrder2.toString());
                    generateOrderService.deleteOrder(pdOrder2.getString("merOrderId"));

                    pdOrder2.put("third_payment", "2");//嘉联
                    System.out.println("添加订单"+ pdOrder2.toString());
                    generateOrderService.addOrderHistory(pdOrder2);
                    System.out.println("推动消息"+ pdOrder2.getString("openid"));

                    PageData tpd = new PageData();
                    tpd.put("merOrderId", shop_order_no);
                    tpd.put("open_id",pdOrder2.getString("openid"));
                    tpd.put("internet_id", pdOrder2.getString("internet_id"));
                    tpd.put("card",pdOrder2.getString("carded"));
                    tpd.put("CARDED",pdOrder2.getString("carded"));
                    tpd.put("USER_ID",pdOrder2.getString("user_id"));
                    double totalAmount = Double.parseDouble(pdOrder.get("rincipal_balance").toString()) + Double.parseDouble(pdOrder.get("reward_balance").toString());
                    tpd.put("totalAmount", totalAmount);
                    tpd.put("rewardAmount", Double.parseDouble(pdOrder.get("reward_balance").toString()));
                    generateOrderService.tuisong(tpd);
                }else {
                    // 修改订单状态
                    pd1.put("return_code", m2.getErrmsg());
                    pd1.put("request_time", Tools.date2Str(new Date()));//顺网充值时间
                    pd1.put("pay_state", 'b');
                    pd.put("pd", pd1);
                    generateOrderService.editOrderState(pd);
                }
            }else if(order_status != 1){
                log.info(shop_order_no + "订单支付失败");
                // 修改订单状态
                pd1.put("pay_state", 1);//支付失败
                pd.put("pd", pd1);
                generateOrderService.editOrderState(pd);
            }

            writer.write("success");
        }catch (Exception e){

        }
    }


    private void handleSWRechargeErr(String errmsg, String internet_id){
    	log.info("顺网充值失败返回码：：：：" + errmsg);
    	if(errmsg.contains("该卡正在") && errmsg.contains("上机")){
			//原因：漫游网吧，绑定的网吧和上网网吧不一致
			//解决：充值到上网所在的网吧

		}
	}


    /**
     * 获取门店是否开通了银联或者嘉联
     * @param store_id
     * @return
     * @throws Exception
     */
    private String getThirdPayment(String store_id) throws Exception{
        String str = "1";//默认银联
        PageData pdStore = new PageData();
        pdStore.put("store_id", store_id);
        pdStore = jiaLianService.findPaymentByStoreId(store_id);
        if(StringUtil.isNotEmpty(pdStore)){
            if(StringUtil.isNotEmpty(pdStore.get("yl_status")) && pdStore.get("yl_status").toString().equals("1")){
                str = "1";//银联
            }
            if(StringUtil.isNotEmpty(pdStore.get("jl_status")) && pdStore.get("jl_status").toString().equals("1")){
                str = "2";//嘉联
            }
        }
        return str;
    }

    public static boolean checkSign(PageData pd, String secret) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("code", pd.getString("code"));
        map.put("result", pd.getString("result"));
        map.put("shopNo", pd.getString("shopNo"));
        map.put("orderStatus", pd.getString("orderStatus"));
        map.put("orderNo", pd.getString("orderNo"));
        map.put("shopOrderNo", pd.getString("shopOrderNo"));
        map.put("channelNo", pd.getString("channelNo"));
        map.put("payType", pd.getString("payType"));
        map.put("completeDate", pd.getString("completeDate"));
        map.put("payMoney", pd.getString("payMoney"));
        map.put("remark", pd.getString("remark"));

        if (PayDemo.getSign((TreeMap) map, secret).equals(pd.getString("sign"))) {
            return true;
        }
        return false;
    }





    @ResponseBody
    @RequestMapping(value = "/test")
    public JSONObject test() throws Exception{
        System.out.println("test==========");
        return this.getErrorJson();
    }
}
