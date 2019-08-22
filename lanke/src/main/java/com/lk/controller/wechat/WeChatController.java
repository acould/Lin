package com.lk.controller.wechat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lk.controller.system.wechatuser.WeChatUserController;
import com.lk.service.wechat.WeixinService;
import com.lk.wechat.util.WechatMessageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.controller.base.BaseController;
import com.lk.service.internet.permission.PermissionManager;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.system.welcome.WelcomeManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.menu.WxMenuService;
import com.lk.service.weixin.textmsg.TextmsgService;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.aes.WXBizMsgCrypt;
import com.lk.wechat.util.SmsLogUtil;
import com.lk.wechat.util.WeChatOpenUtil;


/**
 * 核心请求处理类
 */
@Controller
@RequestMapping(value = "/wechat")
public class WeChatController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(WeChatController.class);

	@Autowired
	private WeixinService weixinService;

	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "myopService")
	private MyopManager myopService;
	@Resource(name = "wxMenuService")
	private WxMenuService menuService;
	@Resource(name = "permissionService")
	private PermissionManager permissionService;
	@Resource(name = "terraceService")
	private TerraceManager terraceService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/weiindex")
	public void weiIndex(HttpServletRequest request, HttpServletResponse response){
		log.info("微信服务器入口----"+request.getMethod());
		try {
			if (request.getMethod().equals("GET")) {
				this.doGet(request, response);
			} else if (request.getMethod().equals("POST")) {
				this.doPost(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ;
	}

	private void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String signature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		//验证签名是否正确
		String result = weixinService.checkSignature(signature, echostr, timestamp, nonce);

		response.getWriter().print(result);
		return ;
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		String nonce = request.getParameter("nonce");
		String angetId = request.getParameter("appid");
		String signature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");

		//获取（加密）信息参数
		String decryptXML = weixinService.getecryptXML(request.getInputStream());

		//处理微信推送的消息，事件

		String result = weixinService.handleEvent(decryptXML, angetId, timestamp, signature, nonce);

		return ;
	}


	/********************************* 以下为开放平台授权 ************************************/

	/**
	 * 授权事件接收URL
	 */
	@RequestMapping(value = "/authoriz")
	public void authoriz(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		this.doSqPost(request, response, out);
	}

	private void doSqPost(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		in.close();
		this.XMLSqConvertToModel(sb.toString());
	}

	public String XMLSqConvertToModel(String sb) throws Exception {
		Document doc = DocumentHelper.parseText(sb);

		Element rootElt = doc.getRootElement(); // 获取根节点
		String openid = rootElt.elementTextTrim("AppId");
		String msgType = rootElt.elementTextTrim("Encrypt");
		WXBizMsgCrypt pc = new WXBizMsgCrypt(PublicManagerUtil.TOKEN, PublicManagerUtil.key, openid);
		String aa = pc.decrypt(msgType);

		log.info("授权获取的解密xml信息：：" + aa);
		Document docNew = DocumentHelper.parseText(aa);
		Element rootEltNew = docNew.getRootElement(); // 获取根节点
		String AppId = rootEltNew.elementTextTrim("AppId");//开放平台appid
        String AuthorizerAppid = rootEltNew.elementTextTrim("AuthorizerAppid");//授权方的appid
		String InfoType = rootEltNew.elementTextTrim("InfoType");

        log.info("授权公众号appid：：" + AuthorizerAppid + "，授权类型：：" + InfoType);
		if (InfoType.equals("unauthorized")) {
			// 取消授权通知
			PageData pd = new PageData();
			pd.put("APPID", AuthorizerAppid);
			intenetService.updateState(pd);
		} else if (InfoType.equals("authorized")) {
			// 授权成功通知
		} else if (InfoType.equals("updateauthorized")) {
			// 授权更新通知
		} else if (InfoType.equals("component_verify_ticket")) {
			String component_verify_ticket = rootEltNew.elementTextTrim("ComponentVerifyTicket");
			PageData pdmyop = new PageData();
			pdmyop.put("APPID", AppId);
			pdmyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
			pdmyop.put("COMPONENT_VERIFY_TICKET", component_verify_ticket);
			pdmyop = myopService.findByAppId(pdmyop);

			return PublicManagerUtil.SUCCESS;
		}
		return "";
	}
	
	/**
	 * 授权回调URL
	 */
	@RequestMapping(value = "/authorization")
	public String authorization(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logBefore(logger, "开始授权：");
		PageData pdmyop = new PageData();
		pdmyop.put("APPID", PublicManagerUtil.APPID);
		Date dateTime = Tools.sAddHours(new Date(), -1);// 超
		pdmyop.put("TOKEN_TIME", dateTime); // 更新时间
		pdmyop = myopService.findByAppId(pdmyop);

		String authorization_code = request.getParameter("auth_code");// 授权码
		String INTENET_ID = request.getParameter("intenetId");
		// JSONObject component_access_token=WeChatOpenUtil.getComponent(PublicManagerUtil.APPID,"575cb861312fccef78abc6c3423ace25",pdmyop.getString("COMPONENT_VERIFY_TICKET"));
		// String aaa=component_access_token.getString("component_access_token");
		JSONObject function = WeChatOpenUtil.getApiQueryAuth(PublicManagerUtil.APPID, authorization_code,pdmyop.getString("COMPONENT_ACCESS_TOKEN"));
		System.out.print(function);
		JSONObject authorization_info = function.getJSONObject("authorization_info"); // 授权信息
		String authorizer_appid = authorization_info.getString("authorizer_appid"); // 授权方appid
		String authorizer_access_token = authorization_info.getString("authorizer_access_token"); // 接口调用凭据(令牌)
		String authorizer_refresh_token = authorization_info.getString("authorizer_refresh_token"); // 接口调用凭据刷新令牌

		/*-----将AUTHORIZER_ACCESS_TOKEN，AUTHORIZER_REFRESH_TOKEN加入缓存-----*/
		PageData pdpd = new PageData();
		pdpd.put("AUTHORIZER_ACCESS_TOKEN", authorizer_access_token);
		pdpd.put("AUTHORIZER_REFRESH_TOKEN", authorizer_refresh_token);
		Cache cache = CacheManager.myGzhCahce();// 缓存对象
		cache.insertObject(authorizer_appid, pdpd); // 把AUTHORIZER_ACCESS_TOKEN(令牌和刷新令牌)加入缓存
		
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "开始授权：/授权方接口调用凭据");
		pd.put("TERRACE_ID", this.get32UUID()); // 主键
		pd.put("AUTH_CODE", authorization_code); // 授权码
		pd.put("APP_ID", authorizer_appid); // 公众号appid
		pd.put("AUTHORIZER_ACCESS_TOKEN", authorizer_access_token); // 授权方接口调用凭据
		pd.put("AUTHORIZER_REFRESH_TOKEN", authorizer_refresh_token); // 接口调用凭据刷新令牌
		pd.put("INTENET_ID", INTENET_ID); // 所属网吧
		pd.put("INSERT_TIME", Tools.date2Str(new Date())); // 授权时间
		logBefore(logger, "验证新的权限集是否为满权限");

		//先去判断权限集
		JSONArray array = authorization_info.getJSONArray("func_info"); // 授权给开发者的权限集列表
		ArrayList<String> arrayList = new ArrayList<String>(); // 新建一个arrayList,用来存取新权限集
		for (int i = 0; i < array.size(); i++) {
			/*
			 * [{ "funcscope_category": { "id": 17 } }, { "funcscope_category": { "id": 18 }
			 * }, { "funcscope_category": { "id": 19 } }]
			 */
			JSONObject funcscope_category = array.getJSONObject(i).getJSONObject("funcscope_category"); // 获取json对象funcscope_category
			String id = funcscope_category.get("id").toString(); // 获取funcscope_category对象的id的值
			arrayList.add(id); // 循环将id的值放入list
		}
		//对比规定满权限集,看是否为满权限
		String[] sz= {"1","2","3","4","5","6","7","8","10","11","13","15","23"};
		ArrayList<String> arrayList1 = new ArrayList<String>(); // 新建一个arrayList1,用来存取规定满权限集
		for (int i = 0; i < sz.length; i++) {
			arrayList1.add(sz[i]);
		}
		//首先判断两个权限集长度是否相同
		if(arrayList.size()!=arrayList1.size() && arrayList.size()!=arrayList1.size()+1) {//如果不同,直接返回授权失败页面
			System.out.println("长度不同");
			return "redirect:/account/goWarn.do"; //并警告请授予满权限
		}
		else {//便利规定权限集,看看新权限集是否缺失规定权限集权限
			for (int i = 0; i < arrayList1.size(); i++) {
				if(!arrayList.contains(arrayList1.get(i))) {//规定权限集有,新权限集没,直接返回授权失败页面
					System.out.println("权限不同");
					return "redirect:/account/goWarn.do"; //并警告请授予满权限
				}
			}
		}
		
		logBefore(logger, "判断授权的几种情况");
		//权限集没有问题再去分情况判断
		List<PageData> list = intenetService.findById1(pd); // 通过网吧id查询网吧状态和微信id(STATE,WECHAT_ID)
		String INTERNET_STATE = ""; // 网吧状态
		String WECHAT_ID = ""; // 微信id
		if (StringUtil.isNotEmpty(list)) {
			String a = list.get(0).get("STATE").toString();
			if (StringUtil.isNotEmpty(a)) {
				INTERNET_STATE = a;
			}
			if (StringUtil.isNotEmpty(list.get(0).get("WECHAT_ID"))) {
				WECHAT_ID = list.get(0).get("WECHAT_ID").toString();
			}
		}

		List<PageData> appid_list = terraceService.findByappId(pd); // 通过appid去查询数据(appid)
		String APP_ID = "";
		if (StringUtil.isNotEmpty(appid_list)) {
			APP_ID = appid_list.get(0).get("APP_ID").toString(); // 原授权微信appid
		}
		
		// 调用授权方的帐号基本信息接口，拿到基本信息
		JSONObject authorizer_info = WeChatOpenUtil.getAuthorizerInfo(PublicManagerUtil.APPID, authorizer_appid,
				pdmyop.getString("COMPONENT_ACCESS_TOKEN"));
		JSONObject authorizer_infoA = authorizer_info.getJSONObject("authorizer_info"); // 得到authorizer_info(授权信息)
		String nick_name = authorizer_infoA.getString("nick_name"); // 公众号名称（INTENTE_NAME）

		List<PageData> CATEGORY_LIST = permissionService.findByAppId(pd); // 去查询之前appid的权限集(CATEGORY集合)
		if (INTERNET_STATE.equals("0")) { // 状态为0(注册用户,未授权)
			if (StringUtil.isEmpty(WECHAT_ID)) { // WECHAT_ID为空
				if (StringUtil.isEmpty(APP_ID)) { // (情况1--从未授过权,新用户)
					terraceService.save(pd); // 保存数据
				} else { // (情况2--新用户,公众号却已经授过权(不管是否解绑))
					if (CATEGORY_LIST.size() != arrayList.size()) { // 两次权限集长度不同,证明权限不同
						System.out.println("不相同");
						List<PageData> phone_list = intenetService.findByappid(pd); // 通过appid去获取之前授权的揽客用户手机号
						if (StringUtil.isNotEmpty(phone_list)) {
							String phone = phone_list.get(0).getString("PHONE"); // 获取该公众号之前授权用户的手机号
							SmsLogUtil.sendNotification(phone, nick_name); // 然后发送短信进行提醒(授权失败)
						}
						permissionService.deleteApp(pd); // 删除以前授予的权限
						terraceService.update(pd);       // 更新授权表信息
						System.out.print(array);
						Iterator<Object> it = array.iterator();
						while (it.hasNext()) { // 保存新权限集
							JSONObject funcscope_category = (JSONObject) it.next();
							JSONObject funcscope_categoryNew = funcscope_category.getJSONObject("funcscope_category");
							PageData sq = new PageData();
							sq.put("PERMISSION_ID", this.get32UUID()); // 主键
							sq.put("APP_ID", authorizer_appid); // 微信公众号appid
							sq.put("CATEGORY", funcscope_categoryNew.get("id")); // 授权项
							permissionService.save(sq); // 同意赋予权限
						}
					} else { // 长度相同则去比对两个权限集内具体内容
						System.out.println("相同");
						outer: for (int i = 0; i < CATEGORY_LIST.size(); i++) {
							if (!arrayList.contains(CATEGORY_LIST.get(i).get("CATEGORY"))) {// 不包含
								List<PageData> phone_list = intenetService.findByappid(pd); // 通过appid去获取之前授权的揽客用户手机号
								if (StringUtil.isNotEmpty(phone_list)) {
									String phone = phone_list.get(0).getString("PHONE"); // 获取该公众号之前授权用户的手机号
									SmsLogUtil.sendNotification(phone, nick_name); // 然后发送短信进行提醒(加V成功)
								}
								break outer;
							}
						}
						permissionService.deleteApp(pd); // 删除以前授予的权限
						System.out.print(array);
						Iterator<Object> it = array.iterator();
						while (it.hasNext()) { // 保存新权限集
							JSONObject funcscope_category = (JSONObject) it.next();
							JSONObject funcscope_categoryNew = funcscope_category.getJSONObject("funcscope_category");
							PageData sq = new PageData();
							sq.put("PERMISSION_ID", this.get32UUID()); // 主键
							sq.put("APP_ID", authorizer_appid); // 微信公众号appid
							sq.put("CATEGORY", funcscope_categoryNew.get("id")); // 授权项
							permissionService.save(sq); // 同意赋予权限
						}
					}
					        terraceService.update1(pd);       // 更新授权表信息
					return "redirect:/account/goToWarn.do"; // 最后弹出授权失败页面,并警告其该公众号已授权其他揽客用户
				}
			}
		} else if (INTERNET_STATE.equals("4")) {
			if (StringUtil.isNotEmpty(WECHAT_ID)) { // WECHAT_ID不为空
				if (StringUtil.isNotEmpty(APP_ID)) { // APP_ID不为空
					if (WECHAT_ID.equals(APP_ID)) { // (情况3--流失用户,原公众号取消授权，原公众号再次授权给原揽客账户)
						permissionService.deleteApp(pd); // 删除以前授予的权限
						terraceService.update(pd); // 更新授权表信息
					}
				} else { // (情况4--原公众号取消授权，换了一个新的公众号授权给原揽客账户)
					PageData pd1 = new PageData();
					pd1.put("APP_ID", WECHAT_ID); // 放入原appid
					permissionService.deleteApp(pd1); // 删除原appid以前授予的权限
					menuService.deleteByAppId(pd1); // 通过原appid删除微信菜单
					terraceService.update(pd); // 更新授权表信息
				}
			}
		}
		System.out.print(array);
		Iterator<Object> it = array.iterator();
		while (it.hasNext()) { // 保存新权限集信息
			JSONObject funcscope_category = (JSONObject) it.next();
			JSONObject funcscope_categoryNew = funcscope_category.getJSONObject("funcscope_category");
			PageData sq = new PageData();
			sq.put("PERMISSION_ID", this.get32UUID()); // 主键
			sq.put("APP_ID", authorizer_appid); // 微信公众号appid
			sq.put("CATEGORY", funcscope_categoryNew.get("id")); // 授权项
			permissionService.save(sq); // 同意赋予权限
		}

		// 调用授权方的帐号基本信息接口，拿到基本信息
		JSONObject service_type_info = authorizer_infoA.getJSONObject("service_type_info"); // 授权方公众号类型(0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号)
		String STATE = service_type_info.getString("id"); // (本系统只能用服务号,订阅号很多功能不能使用)
		if (StringUtil.isNotEmpty(STATE)) {
			if (!STATE.equals("2")) { // 当不为2(服务号)时,赋值为5(非法用户)
				STATE = "5";
			}
		}
		String user_name = authorizer_infoA.getString("user_name"); // 授权方公众号的原始ID(WECHAT_KET)
		String head_img = "";
		if (authorizer_infoA.containsKey("head_img")) { // 头像可能为空
			head_img = authorizer_infoA.getString("head_img"); // 获取头像
		}

		String qrcode_url = authorizer_infoA.getString("qrcode_url"); // 获取二维码图片的url
		PageData pdd = new PageData();
		pdd.put("INTENET_ID", INTENET_ID);
		pdd.put("INTENET_NAME", nick_name); // 公众号名称（INTENTE_NAME）
		pdd.put("WECHAT_KET", user_name); // 授权方公众号的原始ID(WECHAT_KET)
		pdd.put("STATE", STATE); // 授权方公众号类型(STATE,0为注册用户,2为正式用户,3为停用用户,4为流失用户,5为非法用户)
		pdd.put("WECHAT_ID", authorizer_appid); // 公众号appid
		pdd.put("HEAD_IMG", head_img); // 头像
		pdd.put("QRCODE_URL", qrcode_url); // 二维码图片url
		pdd.put("auth_time", Tools.date2Str(new Date()));//授权时间

		intenetService.update(pdd); // 用公众号信息修改sys_intenet表(修改用户信息)
		PageData intenetpd = intenetService.findById(pdd); // 通过网吧id获取信息
		userService.editUPname(intenetpd); // 修改name(修改user表名字为网吧名称)

		/*
		 * //主要将QRCODE_URL保存 或 修改 用于分享设置二维码 PageData ppd = new PageData(); ppd =
		 * inviteService.findByInternetId(pdd); PageData pddpd = new PageData(); if(ppd
		 * == null){//执行保存 pddpd.put("INTIVE_ID", this.get32UUID());
		 * pddpd.put("BARCODE",qrcode_url);//二维码图片链接 pddpd.put("PATH",
		 * head_img);//商户logo图片链接 pddpd.put("TITLE", "上网也能领券！！");
		 * pddpd.put("DESCRIPTION",
		 * "大量卡券，花式玩法！新手券、男神女神券、生日券、老带新......惊喜不断，只有你想不到，点击立即进入。"); pddpd.put("WELCOME",
		 * ""); pddpd.put("INTENET_ID", INTENET_ID); inviteService.save(pddpd);
		 * }else{//执行修改 pddpd.put("BARCODE",qrcode_url);//二维码图片链接 pddpd.put("PATH",
		 * head_img);//商户logo图片链接 pddpd.put("TITLE", "上网也能领券！！");
		 * pddpd.put("DESCRIPTION",
		 * "大量卡券，花式玩法！新手券、男神女神券、生日券、老带新......惊喜不断，只有你想不到，点击立即进入。");
		 * inviteService.editIntenet_id(pddpd); }
		 */

		PageData pdMenu = new PageData();
		pdMenu.put("INTERNET_ID", INTENET_ID);
		pdMenu.put("APP_ID", authorizer_appid);
		menuService.defaultMenu(pdMenu); // 调用微信菜单接口，生成默认菜单，并将数据保存

		//导入微信用户
		JSONArray userArray = WechatMessageUtil.getOpenids(authorizer_access_token);
		new WeChatUserController().new GroupInclude(userArray, authorizer_access_token);

		return "redirect:/login_toLogin.do";

	}
}
