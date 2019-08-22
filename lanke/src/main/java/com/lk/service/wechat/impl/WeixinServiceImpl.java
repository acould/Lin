package com.lk.service.wechat.impl;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.benefitrecord.BenefitRecordManager;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.system.welcome.WelcomeManager;
import com.lk.service.tb.TemplateRecord.TemplateRecordService;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.menu.WxMenuService;
import com.lk.service.weixin.sendRecord.SendRecordManager;
import com.lk.service.weixin.template.TemplateManager;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.aes.WXBizMsgCrypt;
import com.lk.wechat.response.WechatUser;
import com.lk.wechat.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author shkstart
 * @create 2018-10-17 11:38
 */
@Service("weixinService")
public class WeixinServiceImpl implements WeixinService {

    private static final Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);

    @Resource(name = "daoSupport")
    private DaoSupport baseDao;

    @Autowired
    private UserManager userService;
    @Autowired
    private WxMenuService menuService;
    @Autowired
    private MyopManager myopService;
    @Autowired
    private CardManager cardService;
    @Autowired
    private WeChatUserManager wechatuserService;
    @Autowired
    private CancelManager cancelService;
    @Autowired
    private WelcomeManager welcomeService;
    @Autowired
    private TerraceManager terraceService;
    @Autowired
    private IntenetManager intenetService;
    @Autowired
    private AutoReplyService autoReplyService;
    @Autowired
   	private BenefitRecordManager benefitrecordService;

    @Autowired
    private TemplateManager templateService;


    @Autowired
    private TemplateRecordService templateRecordService;
    @Resource(name = "sendRecordService")
    private SendRecordManager sendRecordService;

    @Override
    public String checkSignature(String signature, String echostr, String timestamp, String nonce) throws Exception{

        // 字典序排序
        String[] str = { PublicManagerUtil.TOKEN, timestamp, nonce };
        Arrays.sort(str);
        //加密
        String bigStr = str[0] + str[1] + str[2];
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.update(bigStr.getBytes());
        byte messageDigest[] = crypt.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        //验证签名是否正确，成功返回echostr
        String digest = hexString.toString();
        if (digest.equals(signature)) {
            log.info("签名验证正确");
            return echostr;
        }
        return "";
    }

    @Override
    public String getecryptXML(InputStream inputStream) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader( inputStream, "UTF-8"));

        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();

        Document doc = DocumentHelper.parseText(sb.toString());// 获取doc对象
        Element rootElt = doc.getRootElement(); // 获取根节点
        String Encrypt = rootElt.elementTextTrim("Encrypt");
        WXBizMsgCrypt pc = new WXBizMsgCrypt("jswechat", PublicManagerUtil.key, PublicManagerUtil.APPID);

        return pc.decrypt(Encrypt);
    }

    @Override
    public String handleEvent(String decryptXML, String angetId, String timestamp,
                              String signature, String nonce) throws Exception{


        log.info("微信解密后的xml===" + decryptXML);
        Document docNew = DocumentHelper.parseText(decryptXML);
        Element rootEltNew = docNew.getRootElement(); // 获取根节点

        String msgType = rootEltNew.elementTextTrim("MsgType");
        String openid = rootEltNew.elementTextTrim("FromUserName");
        log.info("微信用户id=="+openid);
        log.info("拿到的信息===网吧id：" + angetId); // 拿到根节点的名称

        //获取第三方最新的token
        Date dateTime = Tools.sAddHours(new Date(), -1);
        PageData myop = new PageData();
        myop.put("APPID", PublicManagerUtil.APPID);
        myop.put("TOKEN_TIME", dateTime);
        myop = myopService.findByAppId(myop);

        //保存公众号的token等信息
        String keyword = rootEltNew.elementTextTrim("Content");
        if (StringUtil.isNotEmpty(keyword) && keyword.contains("QUERY_AUTH_CODE")) {
            String ArrayDATA_IDS1[] = keyword.split(":");
            JSONObject component_access_token = WeChatOpenUtil.getApiQueryAuth(PublicManagerUtil.APPID,
                    ArrayDATA_IDS1[1], myop.getString("COMPONENT_ACCESS_TOKEN"));
            JSONObject authorization_info = component_access_token.getJSONObject("authorization_info");
            String authorizer_access_token = authorization_info.getString("authorizer_access_token");
            String authorizer_refresh_token = authorization_info.getString("authorizer_refresh_token");
            PageData myopTRA = new PageData();
            myopTRA.put("TERRACE_ID", StringUtil.get32UUID()); // 主键
            myopTRA.put("AUTH_CODE", ArrayDATA_IDS1[1]);
            myopTRA.put("INTENET_ID", angetId);// 所属网吧
            myopTRA.put("APP_ID", angetId); // 公众号appid
            myopTRA.put("AUTHORIZER_ACCESS_TOKEN", authorizer_access_token); // 授权方接口调用凭据
            myopTRA.put("AUTHORIZER_REFRESH_TOKEN", authorizer_refresh_token); // 接口调用凭据刷新令牌
            myopTRA.put("INSERT_TIME", Tools.date2Str(new Date())); // 授权时间
            log.info("开始授权插入数据库");
            terraceService.save(myopTRA);

        }


        WechatUser person = new WechatUser();
        Intenet internet = new Intenet();
        String INTENET_ID = angetId;
        internet = intenetService.getIntenetByWeiXinId(INTENET_ID);

        // 获取公众号的凭证
        String AUTHORIZER_ACCESS_TOKEN = autoReplyService.getAuthToken(internet.getINTENET_ID());

        if (msgType.equals("text")) {
            // 接收到文本消息

            // 开发平台测试
			 /* if(keyword.contains("TESTCOMPONENT_MSG_TYPE_TEXT")){
				  logBefore(logger,"拿到分数发送的消息：" + keyword+"开始发送消息");
				  JSONObject gjsonAip =new JSONObject();
				  gjsonAip.put("touser", openid);
				  gjsonAip.put("msgtype", "text");
				  JSONObject news =new JSONObject();
				  news.put("content", "TESTCOMPONENT_MSG_TYPE_TEXT_callback");
				  gjsonAip.put("text", news);
				  WechatMessageUtil.SendMessage(AUTHORIZER_ACCESS_TOKEN,openid, gjsonAip.toString());
			  }
			  if(keyword.contains("QUERY_AUTH_CODE")){
	      		  out.write("");//传入参数
	      		  out.close();
	      		  String ArrayDATA_IDS[] =  keyword.split(":");
	      		  JSONObject gjson =new JSONObject();
	      		  //JSONObject json = getUserOpenids(array.get(3).toString()); //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
	      		  gjson.put("touser", openid);
      	          gjson.put("msgtype", "text");
      	          JSONObject news =new JSONObject();
      	          news.put("content", ArrayDATA_IDS[1]+"_from_api");
      	          gjson.put("text", news);
      	          WechatMessageUtil.SendMessage(AUTHORIZER_ACCESS_TOKEN,"wx7d71780213f70683",nonce,time, toUserName,gjson.toString());
			  }*/

            log.info("接收到文本消息=="+keyword);
            // 文本回复（客服接口）
            PageData pdReply = new PageData();
            pdReply.put("ACCESS_TOKEN", AUTHORIZER_ACCESS_TOKEN);
            pdReply.put("OPEN_ID", openid);
            pdReply.put("EVENT", "keyword");
            pdReply.put("KEYWORD", keyword);
            pdReply.put("INTERNET_ID", internet.getINTENET_ID());
            autoReplyService.wxReply(pdReply);

        } else if (msgType.equals("image")) {
            // 图片
        } else if (msgType.equals("voice")) {
            // 语音
        } else if (msgType.equals("video")) {
            // 视频
        } else if (msgType.equals("shortvideo")) {
            // 小视频
        } else if (msgType.equals("location")) {
            // 地理位置
        } else if (msgType.equals("link")) {
            // 链接
        } else if (msgType.equals("event")) {
            //事件通知
            String event = rootEltNew.elementTextTrim("Event");
            if (event.equals("subscribe")) {
                //关注事件
                person = WeixinUtil.getUserInfo(AUTHORIZER_ACCESS_TOKEN, openid);
                if (StringUtil.isNotEmpty(openid)) {
                    User user = userService.getUserByOpenId(openid);

                    String state = "0";
                    if (user == null) {
                        String userName = WeixinUtil.generateSequenceNo();
                        PageData pdUser = new PageData();
                        pdUser.put("USER_ID", StringUtil.get32UUID());
                        pdUser.put("LAST_LOGIN", ""); // 最后登录时间
                        pdUser.put("IP", ""); // IP
                        pdUser.put("STATUS", "0"); // 状态
                        pdUser.put("SKIN", "default");
                        pdUser.put("RIGHTS", "");
                        pdUser.put("PASSWORD", new SimpleHash("SHA-1", PublicManagerUtil.PASSWORD).toString()); // 密码加密
                        pdUser.put("USERNAME", userName);
                        pdUser.put("ANGET_ID", internet.getANGET_ID());
                        pdUser.put("INTENET_ID", internet.getINTENET_ID()); // 更改为appId
                        pdUser.put("EMAIL", "");
                        pdUser.put("NAME", URLEncoder.encode(person.getNECK_NAME(), "utf-8"));
                        pdUser.put("ROLE_ID", PublicManagerUtil.PROMOTERROLEID);
                        pdUser.put("INTEGRAL", 0);
                        userService.saveU(pdUser);
                        PageData wechatUser = new PageData();
                        wechatUser.put("WECHAT_ID", StringUtil.get32UUID()); // 主键
                        wechatUser.put("USER_ID", pdUser.get("USER_ID")); // 用户id
                        wechatUser.put("OPEN_ID", person.getOPEN_ID()); // 公众号唯一id
                        wechatUser.put("INTENET_ID", internet.getINTENET_ID()); // 网吧id
                        wechatUser.put("NECK_NAME", URLEncoder.encode(person.getNECK_NAME(), "utf-8")); // 昵称
                        wechatUser.put("SEX", person.getSEX()); // 性别
                        wechatUser.put("PROVICNE", person.getPROVICNE()); // 省
                        wechatUser.put("CITY", person.getCITY()); // 城市
                        wechatUser.put("CREATE_TIME", Tools.formatTime(person.getCREATE_TIME())); // 关注时间
                        wechatUser.put("STATE", "1"); // 状态1关注 0取消关注
                        wechatUser.put("IMGURL", person.getIMGURL());
                        wechatuserService.save(wechatUser);

                        user = userService.getUserByOpenId(openid);
                        state = "1";
                    }

                    //新用户关注或者用户状态为2的时候，发送新手券
                    if (state.equals("1") || user.getSTATUS().equals("2")) {
                        PageData pdCard = new PageData();
                        pdCard.put("intenetId", internet.getINTENET_ID());
                        pdCard.put("cardType", "NEW");
                        pdCard = cardService.findByCenece(pdCard);

                        if (pdCard != null) {
                            WechatCardUtil.putGzCard(AUTHORIZER_ACCESS_TOKEN, myop.getString("COMPONENT_VERIFY_TICKET"),
                                    openid, timestamp, signature, pdCard.getString("CARD_ID"));
                        }
                        PageData userPd = new PageData();
                        userPd.put("USER_ID", user.getUSER_ID());
                        userPd.put("STATUS", "0");
                        userService.editUserStatus(userPd);
                    }


                    // 是否是重新关注的
                    PageData wuPd = new PageData();
                    wuPd.put("OPEN_ID", openid);
                    wuPd = wechatuserService.findByOpenId(wuPd);
                    if (wuPd != null) {
                        wuPd.put("STATE", "1");// 重新关注
                        wuPd.put("CREATE_TIME", Tools.date2Str(new Date()));// 重新关注时间
                        wechatuserService.edit(wuPd);
                    }

                }
                // 发送欢迎语（旧版本的欢迎语）
                PageData pdWelcome = new PageData();
                pdWelcome.put("INTENET_ID", internet.getINTENET_ID());
                pdWelcome = welcomeService.findById(pdWelcome);
                if (pdWelcome != null) {
                    WechatMessageUtil.SendMessage(AUTHORIZER_ACCESS_TOKEN, openid, pdWelcome.getString("WELCOME"));
                }
                // 关注自动回复
                PageData pdReply = new PageData();
                pdReply.put("ACCESS_TOKEN", AUTHORIZER_ACCESS_TOKEN);
                pdReply.put("OPEN_ID", openid);
                pdReply.put("EVENT", "subscribe");
                pdReply.put("INTERNET_ID", internet.getINTENET_ID());
                autoReplyService.wxReply(pdReply);

                //带场景值的关注，回复
                String scene = rootEltNew.elementTextTrim("EventKey");
                log.info("带场景的二维码关注事件===qrscene_user_up_qrcode"+scene);
                if("qrscene_user_up_qrcode".equals(scene)){
                    String log_url_1 = PublicManagerUtil.URL1 + "intenetmumber/Membership0.do?openid="+openid;
                    String log_url_2 = PublicManagerUtil.URL1 + "intenetmumber/bindCard.do?openid="+openid;
                    log_url_1 = WeChatOpenUtil.getShotUrl(log_url_1).getString("url_short");
                    log_url_2 = WeChatOpenUtil.getShotUrl(log_url_2).getString("url_short");


                    WechatMessageUtil.SendMessage(AUTHORIZER_ACCESS_TOKEN, openid,
                            "欢迎关注"+internet.getINTENET_NAME()+"！\n" +
                            "快速办理会员，绑定微信号\n" +
                            "点击下面链接：\n" +log_url_1+
                            "\n\n已有会员，绑定门店\n" +
                            "点击下面链接：\n" + log_url_2+"\n");
                }





            } else if (event.equals("unsubscribe")) {
                // 取消关注事件
                PageData wuPd = new PageData();
                wuPd.put("OPEN_ID", openid);
                wuPd = wechatuserService.findByOpenId(wuPd);
                if (wuPd != null) {
                    wuPd.put("STATE", "0");// 取消关注
                    wuPd.put("CREATE_TIME", Tools.date2Str(new Date()));// 取消关注时间
                    wechatuserService.edit(wuPd);
                }
            } else if (event.equals("card_pass_check")) {
                // 卡券通过审核
                String CardId = rootEltNew.elementTextTrim("CardId");
                PageData card = new PageData();
                card.put("CARD_ID", CardId);
                card.put("STATE", event);
                cardService.updateState(card);
            } else if (event.equals("card_not_pass_check")) {
                // 卡券未通过审核
                String CardId = rootEltNew.elementTextTrim("CardId");
                PageData card = new PageData();
                card.put("CARD_ID", CardId);
                card.put("STATE", event);
            } else if (event.equals("user_get_card")) {
                // 用户领取卡券
                String CardId = rootEltNew.elementTextTrim("CardId");
                String FromUserName = rootEltNew.elementTextTrim("FromUserName");
                String CreateTime = rootEltNew.elementTextTrim("CreateTime");
                String UserCardCode = rootEltNew.elementTextTrim("UserCardCode");

                PageData pdHasCard = new PageData();
                pdHasCard.put("CARD", UserCardCode);
                pdHasCard = cancelService.findByCode(pdHasCard);
                if(StringUtil.isEmpty(pdHasCard)){
                    //当前卡券码不存在时，添加卡券
                    //卡券核销表
                    PageData card = new PageData();
                    card.put("CARD_ID", CardId);// 微信生成的优惠券ID
                    card.put("STATE", 1);
                    card.put("CANCEL_ID", StringUtil.get32UUID()); // 主键
                    card.put("CREAT_TIME", CreateTime); // 创建时间
                    card.put("OPEN_ID", FromUserName); // 领取人openId
                    card.put("CARD", UserCardCode); // 微信生成的优惠券码
                    card.put("USER_ID", "1"); // 卡券核销时的操作用户
                    card.put("INTERNET_ID", internet.getINTENET_ID());
                    card.put("UPDATE_TIME", Tools.date2Str(new Date()));
                    cancelService.save(card);// 用户领取优惠券后 保存到核销表

                    PageData pdU = new PageData();
                    pdU.put("OPEN_ID", FromUserName);
                    pdU = wechatuserService.findByOpenId(pdU);

                    //福利领取记录
                    PageData pdFl = new PageData();
                    pdFl.put("BENEFITRECORD_ID", StringUtil.get32UUID()); // 主键
                    pdFl.put("INTENET_ID", internet.getINTENET_ID()); // 网吧id
                    pdFl.put("USER_ID", pdU.getString("USER_ID")); // 用户id
                    pdFl.put("BENEFITS_ID", CardId); // 福利id
                    pdFl.put("CREATE_TIME", new Date()); // 福利id
                    benefitrecordService.save(pdFl);

                    // 把该卡券的库存对应减去1
                    PageData pdd = new PageData();
                    pdd.put("CARD_ID", CardId);
                    pdd = cardService.findByCardId(pdd);
                    int oldQUANTITY = Integer.parseInt(String.valueOf(pdd.get("QUANTITY")));
                    int Oldreceive = Integer.parseInt(String.valueOf(pdd.get("cardSum")));//已领取卡券数量
                    pdd.put("CARD_ID", CardId);
                    pdd.put("QUANTITY", (oldQUANTITY - 1) +"");//库存减1
                    pdd.put("cardSum", (Oldreceive+1) +"");//已领取加1
                    cardService.edit(pdd);

                    //处理满时长券 或 抵用券，附带订单id
                    String OuterStr = rootEltNew.elementTextTrim("OuterStr");
                    cardService.rushReceived(CardId, FromUserName, OuterStr, card.getString("CANCEL_ID"));
                }



            }else if(event.equals("user_consume_card")){
                //卡券核销事件推送
                String cardId = rootEltNew.elementTextTrim("CardId");//卡券id
                String userCardCode = rootEltNew.elementTextTrim("UserCardCode");//卡券code吗
                String consumeSource = rootEltNew.elementTextTrim("ConsumeSource");//核销来源（支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号））
                String staffOpenId = rootEltNew.elementTextTrim("StaffOpenId");//核销员的openid
                String outerStr = rootEltNew.elementTextTrim("OuterStr");//开发者发起核销时传入的自定义参数，用于进行核销渠道统计

                PageData pdCancel = new PageData();
                pdCancel.put("CARD_ID", cardId);
                pdCancel.put("USER_CARD", userCardCode);
                pdCancel.put("CONSUME_SOURCE", consumeSource);
                pdCancel.put("STAFF_OPEN_ID", staffOpenId);
                pdCancel.put("OUTER_STR", outerStr);

                if(!consumeSource.equals("FROM_API")){
                    //不是通过接口调用时，同步揽客数据
                    cancelService.updateCancel(pdCancel, openid);
                }


            }else if (event.equals("CLICK")) {
                // 菜单点击事件
                String key = rootEltNew.elementTextTrim("EventKey");
                PageData pdMenu = new PageData();
                pdMenu.put("VALUE", key);
                pdMenu.put("APP_ID", angetId);
                pdMenu = menuService.findByKey(pdMenu);
                JSONObject jsonType = new JSONObject();
                if (StringUtil.isNotEmpty(pdMenu) && StringUtil.isNotEmpty(pdMenu.getString("CONTENT"))) {
                    // 发送文字信息
                    jsonType.put("content", pdMenu.getString("CONTENT"));
                    WechatMessageUtil.sendMessageByCustom(AUTHORIZER_ACCESS_TOKEN, openid, "text", jsonType);
                } else if (StringUtil.isNotEmpty(pdMenu) && StringUtil.isNotEmpty(pdMenu.getString("CARD_ID"))) {
                    // 发送卡券
                    jsonType.put("card_id", pdMenu.getString("CARD_ID"));
                    WechatMessageUtil.sendMessageByCustom(AUTHORIZER_ACCESS_TOKEN, openid, "wxcard", jsonType);
                }
            }else if(event.equals("SCAN")){
                String key = rootEltNew.elementTextTrim("EventKey");
                //带场景值，预览图文
                String a = "preview_news" + internet.getUSER_ID();
                if(a.equals(key)){
                    Cache cache = CacheManager.getWxQrCache();
                    String media_id = (String) cache.getObject(a);
//                    cache.clearObject(a);

                    PageData pdPreview = new PageData();
                    pdPreview.put("internet_id", internet.getINTENET_ID());
                    pdPreview.put("openid", openid);
                    pdPreview.put("media_id", media_id);
                    sendRecordService.sendPreviewNews(pdPreview);
                }
            }
        }
		String eventKey = rootEltNew.elementTextTrim("EventKey");
		return eventKey + "?openId=" + openid;
    }


    @Override
    public JSONObject preHandleTemplate(String internet_id, String type, String template_id_short) throws Exception{
        JSONObject result = new JSONObject();

        //获取token
        String token = autoReplyService.getAuthToken(internet_id);

        //检查网吧是否有该模板id
        PageData newPd = new PageData();
        newPd.put("internet_id", internet_id);
        newPd.put("type", type);
        newPd = templateService.findById(newPd);
        if (StringUtil.isEmpty(newPd)) {
            newPd = new PageData();
            newPd.put("internet_id", internet_id);
            List<PageData> list = templateService.findByInternetId(newPd);
            if(list.size() == 0){
                //没有，先设置行业属性，再增加模板id，设置行业属性一个月设置一次
                TemplateMsgUtil.setIndustry(token, "39", "2");
            }
            //新增模板
            JSONObject addTemplate = TemplateMsgUtil.addTemplate(token, template_id_short);
            //保存消息模板
            newPd = new PageData();
            newPd.put("id", StringUtil.get32UUID());
            newPd.put("internet_id", internet_id);
            newPd.put("type", type);
            newPd.put("create_time", Tools2.date2Str(new Date()));
            newPd.put("template_id", addTemplate.getString("template_id"));
            templateService.save(newPd);
        }

        result.put("result", "true");
        result.put("template_id", newPd.getString("template_id"));
        result.put("token", token);
        return result;
    }


    @Override
    public JSONObject sendTamplate(String internet_id, String open_id, String type, PageData pd) throws Exception{
        JSONObject result = new JSONObject();

        //发送模板消息前，检查模板设置
        String shot = TemplateMsgUtil.getTypeShort(type);
        if(shot.equals("")){
            result.put("result", "false");
            result.put("message", "type类型错误");
            return result;
        }

        //预处理模板
        JSONObject checkTemp = preHandleTemplate(internet_id, type, shot);

        PageData pdTemplate = new PageData();
        pdTemplate.put("first_data", pd.getString("first_data"));
        pdTemplate.put("remark_data", pd.getString("remark_data"));

        List<String> keywordList = new ArrayList<>();
        JSONObject keywordJSON = JSONObject.fromObject(pd.getString("keyword_data"));
        for (int i = 1; i < 6; i++) {
            if (keywordJSON.containsKey("keyword"+i)) {
                keywordList.add(keywordJSON.getString("keyword" + i));
            }
        }

        //封装微信模板消息内容
        JSONObject templateJSON = TemplateMsgUtil.commonTemplate(pdTemplate, keywordList);


        String url = "";
        if(StringUtil.isNotEmpty(pd.getString("url"))){
            url += pd.getString("url") + "&open_id=" + open_id;
        }
        //发送模板消息
        String record_id = StringUtil.get32UUID();
        url += "&record_id=" + record_id;
        JSONObject sendResult = TemplateMsgUtil.sendTemplate(checkTemp.getString("token"), open_id,
                checkTemp.getString("template_id"), url, null,
                templateJSON);// 发送模板消息


        //发送成功，保存发送记录
        PageData pdRecord = new PageData();
        pdRecord.put("id", record_id);
        pdRecord.put("create_time", new Date());
        pdRecord.put("update_time", new Date());
        pdRecord.put("send_time", new Date());

        pdRecord.put("open_id", open_id);
        pdRecord.put("internet_id", internet_id);
        pdRecord.put("template_id", checkTemp.getString("template_id"));

        pdRecord.put("first_data", pd.getString("first_data"));
        pdRecord.put("keyword_data", pd.getString("keyword_data"));
        pdRecord.put("remark_data", pd.getString("remark_data"));
        pdRecord.put("url", pd.getString("url"));

        pdRecord.put("errmsg", sendResult.toString());
        pdRecord.put("h_state", "2");//未处理
        templateRecordService.save(pdRecord);
        return sendResult;
    }


}
