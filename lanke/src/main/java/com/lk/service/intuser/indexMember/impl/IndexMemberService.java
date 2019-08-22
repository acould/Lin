package com.lk.service.intuser.indexMember.impl;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.dao.DaoSupport;
import com.lk.entity.Message;
import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWUser;
import com.lk.entity.system.BundUser;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.LeaveMessage;
import com.lk.entity.system.User;
import com.lk.entity.weixin.JSAPI;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.service.internet.internetStaff.InternetStaffService;
import com.lk.service.internet.scene.SceneManager;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.system.auction.AuctionManager;
import com.lk.service.system.auctionpictures.AuctionPicturesManager;
import com.lk.service.system.benefitrecord.BenefitRecordManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.cardStore.CardStoreManager;
import com.lk.service.system.consumption.ConsumptionManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.intintegral.IntIntegralManager;
import com.lk.service.system.invite.InviteManager;
import com.lk.service.system.leaveMessage.LeaveMessageService;
import com.lk.service.system.lottery.LotteryManager;
import com.lk.service.system.memberlottery.MemberLotteryManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.order.OrderManager;
import com.lk.service.system.signin.SignInManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.*;
import com.lk.util.qrcode.QrcodeUtil;
import com.lk.wechat.response.WechatUser;
import com.lk.wechat.util.WeChatOpenUtil;
import com.lk.wechat.util.WechatUtil;
import com.lk.wechat.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信端---首页---业务层
 */
@Service("indexMemberService")
public class IndexMemberService implements IndexMemberManager {

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

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "bunduserService")
    private BundUserManager bunduserService;
    @Resource(name = "storeService")
    private StoreManager storeService;
    @Resource(name = "userService")
    private UserManager userService;
    @Resource(name = "signinService")
    private SignInManager signinService;
    @Resource(name = "intintegralService")
    private IntIntegralManager intintegralService;
    @Resource(name = "cardService")
    private CardManager cardService;
    @Resource(name = "inviteService")
    private InviteManager inviteService;
    @Resource(name = "intenetService")
    private IntenetManager intenetService;
    @Resource(name = "cardStoreService")
    private CardStoreManager cardStoreService;
    @Resource(name = "wechatuserService")
    private WeChatUserManager wechatuserService;
    @Resource(name = "sceneService")
    private SceneManager sceneService;
    @Resource(name = "benefitrecordService")
    private BenefitRecordManager benefitrecordService;


    @Resource(name = "auctionService")
    private AuctionManager auctionService;
    @Resource(name = "auctionPicturesService")
    private AuctionPicturesManager auctionPicturesService;
    @Resource(name = "orderService")
    private OrderManager orderService;
    @Resource(name = "lotteryService")
    private LotteryManager lotteryService;
    @Resource(name = "consumptionService")
    private ConsumptionManager consumptionService;
    @Resource(name = "memberlotteryService")
    private MemberLotteryManager memberlotteryService;
    @Resource(name = "picturesService")
    private PicturesManager picturesService;
    @Resource(name = "LeaveMessageService")
    private LeaveMessageService lmService;


    @Resource(name = "autoReplyService")
    private AutoReplyService autoReplyService;

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;
    @Autowired
    private MyopManager myopService;

    @Resource(name = "weixinService")
    private WeixinService weixinService;
    @Autowired
    private InternetStaffService internetStaffService;

    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;
    @Resource(name = "terraceService")
    private TerraceManager terraceService;


    /**
     * 判断用户是否绑定会员
     *
     * @param pd（传入user_id和internet_id）
     * @return 返回的json中字段result=false时表示未绑定，否则已绑定
     * @throws Exception
     */
    public ModelAndView judgeBind(PageData pd) throws Exception {
        ModelAndView mv = new ModelAndView();

        String user_id = pd.getString("user_id");
        String backurl = pd.getString("backurl");

        //获取微信用户信息
        PageData pdWechatUser = new PageData();
        pdWechatUser.put("USER_ID", user_id);
        pdWechatUser = wechatuserService.findByUserId(pdWechatUser);
        pdWechatUser.put("NECK_NAME", URLDecoder.decode(pdWechatUser.getString("NECK_NAME"), "UTF-8"));


        //判断用户是否绑定
        PageData pdBind = new PageData();
        pdBind.put("userId", user_id);
        pdBind = bunduserService.findByUser(pdBind);
        if (StringUtil.isEmpty(pdBind)) {
            mv.addObject("user", pdWechatUser);
            mv.setViewName("intenetmumber/bindCard");
            mv.addObject("backurl", backurl);
            mv.addObject("result", "false");
            return mv;
        } else {
            //判断绑定的门店是否已被禁用
            PageData pdStore = new PageData();
            pdStore.put("STORE_ID", pdBind.getString("STORE_ID"));
            pdStore = storeService.findById(pdStore);
            pdBind.put("STORE_STATE", pdStore.get("STATE"));
            pdWechatUser.put("NECK_NAME", pdBind.getString("NAME"));
            mv.addObject("pdBind", pdBind);//STORE_NAME,STORE_ID,CARDED,PHONE,STORE_STATE
//			if(StringUtil.isNotEmpty(pdStore) && !pdStore.getString("STATE").equals("1")){
//				//门店已被禁用
//				mv.addObject("url", "redirect:/intenetmumber/member");
//				mv.addObject("store_state", "1");
//				mv.addObject("result", "false");
//				return mv;
//			}

            //判断用户信息是否匹配
            if(pdBind != null && pdBind.getString("IS_SW").equals("0")){
                //没有对接顺网数据的用户
                Message2 m2 = judgeUserInfo(pdBind.getString("STORE_ID"), pdBind.getString("CARDED"), pdBind.getString("NAME"))
                        .addData("backurl", backurl);
                if (m2.getErrcode() != 0) {
                    mv.addObject("url", "redirect:/intenetmumber/goBindingError");
                    mv.addObject("user_info_err", "2");
                    mv.addObject("store_state", "1");
                    mv.addObject("result", "false");
                    return mv;
                }
            }

            mv.addObject("result", "true");
        }
        mv.addObject("user", pdWechatUser);

        return mv;
    }


    @Override
    public Message2 judgeUserInfo(String store_id, String carded, String name) throws Exception{
        PageData pdStoreV = new PageData();
        pdStoreV.put("store_id", store_id);
        pdStoreV.put("state", "1");//加v状态
        pdStoreV = storeService.findByStoreIdAndState(pdStoreV);
        //已加v，且客户端连接已存在
        if (StringUtil.isNotEmpty(pdStoreV) && StringUtil.isNotEmpty(ChannelCache.channelMap.get(store_id))) {
            //调顺网接口获取用户数据
            Message2 m2 = userInfoService.getSWUser(store_id, carded);
            if(m2.getErrcode() == 0){
                SWUser swUser = (SWUser) m2.getData().get("SWUser");
                if(swUser != null){
                    if (!swUser.getUser_name().equals(name)) {
                        return Message2.error("身份证和姓名不匹配").addData("err_type","card_match_err")
                                .addData("url", "intenetmumber/binding-error");
                    }
                }
            }else{
                return m2;
            }
        }
        return Message2.ok();
    }

    /**
     * 签到得积分
     *
     * @param pd（传入user_id和internet_id）
     * @return 返回签到结果
     * @throws Exception
     */
    public JSONObject signIn(PageData pd) throws Exception {
        JSONObject result = new JSONObject();

        String user_id = pd.getString("user_id");
        String internet_id = pd.getString("internet_id");

        // 查询用户信息
        PageData pdUser = new PageData();
        pdUser.put("USER_ID", user_id);
        pdUser = userService.findById(pdUser);

        // 查询商户签到积分设置信息，判断是否设置签到积分
        PageData pdInteger = new PageData();
        pdInteger.put("CATEGRORY", "2");
        pdInteger.put("INTENET_ID", internet_id);
        pdInteger = intintegralService.findByCATEGRORY(pdInteger);
        if (pdInteger != null) {
            // 查询用户签到信息，判断当天有没有保存签到记录
            PageData pdFirst = new PageData();
            pdFirst.put("userId", user_id); // 用户id
            pdFirst.put("insertTime", Tools.dateStr(new Date())); // 签到时间
            pdFirst = signinService.findByUser(pdFirst);
            if (pdFirst == null) {
                pdFirst = new PageData();
                pdFirst.put("USER_ID", user_id); // 用户id
                pdFirst.put("INSER_TIME", Tools.dateStr(new Date())); // 签到时间
                pdFirst.put("INTENET_ID", internet_id);
                pdFirst.put("SIGNIN_ID", StringUtil.get32UUID()); // 主键
                signinService.save(pdFirst);

                Date bdate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(bdate);
                // 修改用户积分，如果是周末则加上周末积分
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                        || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    if (StringUtil.isNotEmpty(pdInteger.get("WEEKEND_SEND"))
                            && (Integer) pdInteger.get("WEEKEND_SEND") > 0) {
                        pdUser.put("INTEGRAL", (Integer) pdUser.get("INTEGRAL") + (Integer) pdInteger.get("WEEKEND_SEND"));
                        result.put("integral", pdInteger.get("WEEKEND_SEND"));
                        result.put("message", "签到成功,获得" + (Integer) pdInteger.get("WEEKEND_SEND") + "积分");
                        result.put("result", "true");
                        userService.editUserJf(pdUser);
                    } else {
                        pdUser.put("INTEGRAL", (Integer) pdUser.get("INTEGRAL") + (Integer) pdInteger.get("INTEGRAL_SEND"));
                        result.put("integral", pdInteger.get("INTEGRAL_SEND"));
                        result.put("message", "签到成功,获得" + pdInteger.get("INTEGRAL_SEND") + "积分");
                        result.put("result", "true");
                        userService.editUserJf(pdUser);
                    }
                } else {
                    pdUser.put("INTEGRAL", (Integer) pdUser.get("INTEGRAL") + (Integer) pdInteger.get("INTEGRAL_SEND"));
                    result.put("integral", pdInteger.get("INTEGRAL_SEND"));
                    result.put("message", "签到成功,获得" + pdInteger.get("INTEGRAL_SEND") + "积分");
                    result.put("result", "true");
                    userService.editUserJf(pdUser);
                }

            } else {
                result.put("result", "false");
                result.put("message", "已经签到了，不可再次签到");
            }
        } else {
            result.put("result", "false");
            result.put("message", "商家暂未设置签到积分");
        }

        return result;
    }


    /**
     * 检查后台是否设置了好友分享
     *
     * @param pd（传入internet_id）
     * @return
     * @throws Exception
     */
    public JSONObject canShare(PageData pd) throws Exception {
        JSONObject result = new JSONObject();

        String internet_id = pd.getString("internet_id");

        // 邀请好友之前，后台卡券设置中要有新手券和老带新券
        PageData pdCardNew = new PageData();
        pdCardNew.put("INTERNET_ID", internet_id);
        pdCardNew.put("FAV_TYPE", "NEW");
        pdCardNew = cardService.findByInternetId(pdCardNew);

        PageData pdCardOld = new PageData();
        pdCardOld.put("INTERNET_ID", internet_id);
        pdCardOld.put("FAV_TYPE", "OLD");
        pdCardOld = cardService.findByInternetId(pdCardOld);

        // 需要先设置好分享设置
        PageData pdInvite = new PageData();
        pdInvite.put("INTENET_ID", internet_id);
        pdInvite = inviteService.findByInternetId(pdInvite);

        if (pdCardNew == null || pdCardOld == null || pdInvite == null) {
            result.put("result", "false");
            result.put("message", "商家尚未开启该活动");
        } else {
            result.put("result", "true");
            result.put("backUrl", "/indexMember/share.do");
        }

        return result;
    }


    /**
     * 获取分享的设置数据
     *
     * @param pdd（传入user_id和internet_id，url）
     * @return 返回分享的设置数据
     * @throws Exception
     */
    public ModelAndView share(PageData pdd) throws Exception {
        ModelAndView mv = new ModelAndView();

        String internet_id = pdd.getString("internet_id");
        String user_id = pdd.getString("user_id");
        String url = pdd.getString("url");

        //获取网吧名称
        Intenet org = intenetService.getIntenetById(internet_id);
        mv.addObject("intenetName", org.getINTENET_NAME());

        //获取api
        JSAPI jsapi = this.getWXJSdata(url, internet_id);

        //传页面必备参数
        mv.addObject("url", url);
        mv.addObject("nonceStr", jsapi.getNonceStr());
        mv.addObject("timestamp", jsapi.getTimestamp());
        mv.addObject("signature", jsapi.getSignature());
        mv.addObject("appId", org.getWECHAT_ID());
        mv.addObject("jsapi_ticket", jsapi.getJsapi_ticket());
        mv.addObject("userId", user_id);

        //拉新表的数据
        PageData pdIn = new PageData();
        pdIn.put("INTENET_ID", org.getINTENET_ID());
        pdIn = inviteService.findByInternetId(pdIn);
        mv.addObject("pdIn", pdIn);

        //获取新手券的信息
        PageData pdCard = new PageData();
        pdCard.put("intenetId", org.getINTENET_ID());
        pdCard.put("cardType", "NEW");
        pdCard = cardService.findByCenece(pdCard);
        if (StringUtil.isNotEmpty(pdCard)) {
            mv.addObject("SUB_TITLE", pdCard.get("SUB_TITLE"));
        }

        return mv;
    }


    /**
     * 获取卡券列表
     *
     * @param type
     * @return
     * @throws Exception
     */
    public Message getCardList(String type) throws Exception{

        User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();

        BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());//获取绑定的用户信息

        PageData pd = new PageData();
        pd.put("cardType", "FLQ");  //FLQ--福利券
        pd.put("intenetId", user.getINTENET_ID());
        pd.put("STORE_ID", bundUser.getStore_id());
        pd.put("store_id", bundUser.getStore_id());
        pd.put("FAV_TYPE", type);
        //展示用户绑定的门店的卡券列表
        // 卡券过了有效期后仍要展示一天后不在展示(sql判断条件)
        // 会员福利页面秒抢券的三种情况：
        // ⑴.未到开抢时间，则按钮变为“即将开枪”，变灰色，不可点击
        // ⑵.抢券时间内，按钮为“抢券”，可以点击
        // ⑶.抢券时间内，券已经被抢完，是否变为“已抢光”，变灰色，不可点击
        // ⑷.抢券结束后，按钮为“已结束”，变灰色，不可点击
        List<PageData> varList = cardService.listFl(pd);
        for (PageData pdInternet : varList) {
            String cardId = pdInternet.getString("CARD_ID");

            //卡券所适用的们门店列表
            PageData pdStore = new PageData();
            pdStore.put("CARD_ID", cardId);
            List<PageData> sList = storeService.listByCardId(pdStore);
            String stores = "";
            for (int i = 0; i < sList.size(); i++) {
                stores += sList.get(i).getString("STORE_NAME");
                if (i != sList.size() - 1) {
                    stores += ",";
                }
            }
            pdInternet.put("stores", stores);
        }
        for (int i = 0; i < varList.size(); i++) {
            PageData pdd = varList.get(i);
            if (pdd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")) {// 固定日期区间时
                String endTime = pdd.getString("END_TIMESTAMP");
                if (new Date().getTime() > Tools.str2Date2(endTime).getTime()) {// 当前日期 > 卡券结束日期，不显示
                    varList.remove(i);
                    i--;
                }
            }
        }


        return Message.ok().addData("list", varList);
    }

    /**
     * 会员领取卡券
     *
     * @param pdd（传入user_id和internet_id，mayId为卡卷的主键id）
     * @return 返回领取结果
     * @throws Exception
     */
    public JSONObject receiveCard(PageData pdd) throws Exception {
        JSONObject result = new JSONObject();
        result.put("card_result", "false");

        String internet_id = pdd.getString("internet_id");
        String user_id = pdd.getString("user_id");
        String card_id = pdd.getString("mayId");//卡券表的主键id

        //判断是否存在微信用户信息
        PageData Wechatpd = new PageData();
        Wechatpd.put("USER_ID", user_id);
        Wechatpd = wechatuserService.findByUserId(Wechatpd);
        if (Wechatpd == null) {
            result.put("message", "无效的微信用户信息");
            return result;
        }

        //判断用户是否绑定
        PageData pdBind = new PageData();
        pdBind.put("userId", user_id);
        pdBind = bunduserService.findByUser(pdBind);
        if (StringUtil.isEmpty(pdBind)) {
            result.put("message", "用户没有绑定！");
            return result;
        }

        //判断现在用户绑定的门店在适用的门店列表中
        PageData pdCard = new PageData();
        pdCard.put("CARD_ID", card_id);
        pdCard.put("STORE_ID", pdBind.getString("STORE_ID"));
        pdCard = cardStoreService.findByCardIdAndStoreId(pdCard);
        if (StringUtil.isEmpty(pdCard)) {
            result.put("message", "用户没有绑定适用的门店！请前往会员中心绑定！");
            return result;
        }

        PageData pd = new PageData();
        pd.put("CARD_ID", card_id);
        pd = sceneService.findByCard(pd);

        if (pd.get("QUANTITY").toString().equals("0")) {
            result.put("message", "当前卡券已领取完！");
            return result;
        }

        if (pd.getString("FAV_TYPE").endsWith(FAV_TYPE4)) {//男神券
            //判断男神劵领取条件
            String cardEd = pdBind.getString("CARDED");
            String cardNew = cardEd.substring(cardEd.length() - 2, cardEd.length() - 1);
            int number = Integer.parseInt(cardNew);
            if (number % 2 == 0) {
                result.put("message", "不是男性身份证号条件不符要求。");
                return result;
            }
        } else if (pd.getString("FAV_TYPE").endsWith(FAV_TYPE5)) {//女神券
            //判断女神劵领取条件
            String cardEd = pdBind.getString("CARDED");
            String cardNew = cardEd.substring(cardEd.length() - 2, cardEd.length() - 1);
            int number = Integer.parseInt(cardNew);
            if (number % 2 != 0) {
                result.put("message", "不是女性身份证号条件不符要求。");
                return result;
            }
        }  else if (pd.getString("FAV_TYPE").endsWith("BIRTH")) {//生日券
            //判断生日券领取条件
            String cardEd = pdBind.getString("CARDED");
            String birth = Tools.getBirthByCard(cardEd);//MMdd
            String now = Tools.dateStr(new Date()).substring(5).replace("-","");
            if(!birth.equals(now)){
                result.put("message", "仅限生日当天可以领取哦");
                return result;
            }
        } else if (pd.getString("FAV_TYPE").endsWith(FAV_TYPE9)) {//连续上网满时常券（周/月）  这里看到的卡券已经根据门店id晒选过了
            //判断是否满足领取条件
            Calendar c = Calendar.getInstance();// 日历
            Calendar c1 = Calendar.getInstance();// 日历
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式工具
            //通过user_id去获取上网时长
            if (pd.get("cardSum_type").toString().equals("2")) {//连续一周
                c.add(Calendar.WEEK_OF_MONTH, -1);
            } else if (pd.get("cardSum_type").toString().equals("3")) {//连续一月
                c.add(Calendar.MONTH, -1);
            }
            pd.put("time1", sdf.format(c.getTime()));
            pd.put("time2", sdf.format(c1.getTime()));
            Double time3 = Double.parseDouble(pd.get("cardSum_time").toString());//要求上网时常(小时)
            Double times = time3 * 60;//要求上网时常(分钟)

            //通过user_id和time去获取时间区域内的上网总时长
            pd.put("user_id", user_id);
            PageData pd2 = (PageData) dao.findForObject("CardMapper.getCardState", pd);//获取规定时间内实际上网时常(分钟)
            Integer timeSum = Integer.parseInt(pd2.get("timeSum").toString());//实际时常
            System.out.println("要求上网分钟:" + times);
            System.out.println("实际上网分钟" + timeSum);
            if (timeSum - times < 0) {//未满足领取条件
                result.put("message", "您不满足该卡券领取条件");
                result.put("card_result", PublicManagerUtil.FALSE);
                return result;
            }
        }
        //判断领取时间间隔等限制条件
        result = judgeDateNumber(pd, card_id, user_id);
        if (result.containsKey("msg") && result.get("msg").toString().equals("false")) {
            result.put("card_result", "false");
            result.put("message", result.get("result").toString());
            return result;
        }


        result.put("message", "会员福利领取成功");
        result.put("card_result", "true");
        result.put("backurl", "indexMember/goWechatCard.do?card_id=" + card_id);
        return result;
    }


    /**
     * 判断领取时间间隔
     *
     * @param pdSce
     * @param code
     * @param user_id
     * @return
     * @throws Exception
     */
    public JSONObject judgeDateNumber(PageData pdSce, String code, String user_id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Date dateTimeUpdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(dateTimeUpdate);
        Date startTime = null;
        PageData record = new PageData();
        if (StringUtils.isNotEmpty(pdSce.getString("RECEIVE_DETIL")) && StringUtils.isNotEmpty(pdSce.getString("BLANK_NUMBER"))) {
            int a = Integer.parseInt(pdSce.getString("BLANK_NUMBER"));//时间间隔数量
            if (pdSce.getString("RECEIVE_DETIL").endsWith("WEEK")) {//周
                startTime = Tools.sAddDays(dateTimeUpdate, -a * 7);
            } else if (pdSce.getString("RECEIVE_DETIL").endsWith("YEAR")) {//年
                startTime = Tools.sAddYear(dateTimeUpdate, -a);
            } else if (pdSce.getString("RECEIVE_DETIL").endsWith("DAY")) {//天
                startTime = Tools.sAddDays(dateTimeUpdate, -a);
            } else if (pdSce.getString("RECEIVE_DETIL").endsWith("MON")) {//月
                startTime = Tools.sAddMonth(dateTimeUpdate, -a);
            }
            record.put("END_TIME", dateNowStr);
            String startTimeStr = sdf.format(startTime);
            record.put("START_TIME", startTimeStr);
        }
        record.put("USER_ID", user_id);
        record.put("BENEFITS_ID", code);
        /*
         * Date dateTime=new Date(); SimpleDateFormat sdf = new
         * SimpleDateFormat("yyyy-MM-dd"); String dateNowStr = sdf.format(dateTime);
         * String endTime=dateNowStr+" 23:59:59"; String
         * startTime=dateNowStr+" 00:00:00";
         */
        List<PageData> pdCancleList = benefitrecordService.findByUserList(record);
        if (StringUtils.isNotEmpty(pdSce.getString("RECEIVE_NUMBER"))) {
            String aa = pdSce.getString("RECEIVE_NUMBER");
            int bb = Integer.parseInt(aa);
            if (pdCancleList.size() >= bb) {
                PageData pdtime = new PageData();
                pdtime.put("USER_ID", user_id);
                pdtime.put("BENEFITS_ID", code);
                pdtime = benefitrecordService.findByTime(record);
                if (pdtime != null) {
                    Date nowdate = Tools.str2Date(pdtime.get("CREATE_TIME").toString());

                    long l = nowdate.getTime() - startTime.getTime();
                    long day = l / (24 * 60 * 60 * 1000);
                    long hour = (l / (60 * 60 * 1000) - day * 24);
                    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    StringBuffer sb = new StringBuffer();
                    if (day > 0) {
                        sb.append(day + "天");
                    }
                    if (hour > 0) {
                        sb.append(+hour + "小时");
                    }
                    if (min > 0) {
                        sb.append(+min + "分");
                    }
                    if (s > 0) {
                        sb.append(+s + "秒");
                    }

                    jsonObject.put("msg", PublicManagerUtil.FALSE);
                    jsonObject.put("result", "超过规定时间内领取数量," + sb + "后再来吧！");
                } else {
                    jsonObject.put("msg", PublicManagerUtil.FALSE);
                    jsonObject.put("result", "超过规定时间内领取数量");
                }
                return jsonObject;
            } else {
                jsonObject.put("msg", PublicManagerUtil.SUCCESS);
                return jsonObject;
            }
        } else {
            jsonObject.put("msg", PublicManagerUtil.SUCCESS);
            return jsonObject;
        }
    }




    /**
     * 加载积分商城
     *
     * @param pd（传入user_id和internet_id）
     * @return
     * @throws Exception
     */
    public JSONObject loadMall(PageData pd) throws Exception {
        JSONObject result = new JSONObject();

        String internet_id = pd.getString("internet_id");
        String user_id = pd.getString("user_id");

        //加载全部已上架的商品列表
        pd.put("internetId", internet_id);
        pd.put("STATE", 1);// 1表示已上架；2表示已下架
        List<PageData> varList = auctionService.listAll(pd);
        //加载商品的图片信息
        for (PageData pageData : varList) {
            PageData pdd = auctionPicturesService.findByAuctionId(pageData.getString("Auction_ID"));
            pageData.put("PATH", pdd.get("PATH"));
        }
        result.put("varList", varList);

        //查询用户（查找其已有积分）
        PageData pdUser = new PageData();
        pdUser.put("USER_ID", user_id);
        pdUser = userService.findById(pdUser);
        result.put("INTEGRAL", pdUser.get("INTEGRAL"));


        return result;
    }


    /**
     * 积分申请兑换商品
     *
     * @param pdd（传入user_id和internet_id，Auction_ID商品id）
     * @return 返回申请结果
     * @throws Exception
     */
    public JSONObject applyAuction(PageData pdd) throws Exception {
        JSONObject result = new JSONObject();

        //下单门店为当前绑定的门店

        String internet_id = pdd.getString("internet_id");
        String Auction_ID = pdd.getString("Auction_ID");
        String user_id = pdd.getString("user_id");
        String payInt = pdd.getString("payInt");
        String payCash = pdd.getString("payCash");
        System.out.println("积分购买" + payInt);
        System.out.println("现金购买" + payCash);
        int COUNT = Integer.parseInt(pdd.getString("COUNT"));

        System.out.println("来到了业务层==========");
        System.out.println("这是商品ID" + Auction_ID);
        System.out.println("这是商品数量" + COUNT);

        PageData pdBind = new PageData();
        pdBind.put("userId", user_id);
        pdBind = bunduserService.findByUser(pdBind);

        //查询用户（查找其已有积分）
        PageData pdUser = new PageData();
        pdUser.put("USER_ID", user_id);
        pdUser = userService.findById(pdUser);
        //查询商品（查找其需要的消耗积分）
        PageData pd = new PageData();
        pd.put("Auction_ID", Auction_ID);
        pd = auctionService.findById(pd);
        Integer integral = (Integer) pd.get("INTEGRAL");


//        设置订单基本信息
        PageData pdOrder = new PageData();
        pdOrder.put("ORDER_ID", StringUtil.get32UUID());
        pdOrder.put("ORDER_NUMBER", Tools.getOrderIdByUUId());
        pdOrder.put("USER_ID", user_id);
        pdOrder.put("Auction_ID", Auction_ID);
        pdOrder.put("STATE", "1"); // 1:未提货
        pdOrder.put("INTERNET_ID", internet_id);
        pdOrder.put("CREATE_TIME", Tools.date2Str(new Date()));
        pdOrder.put("DELIVERY", "1"); // 1:到店提
        pdOrder.put("COUNT", COUNT); // 购买数量
        //判断是否用积分购买
        if ("I".equals(payInt)) {
            Integer total = (Integer) pd.get("INTEGRAL") * COUNT;
            System.out.println("选择积分购买了！！！！");
            //判断用户积分是否足够
            if ((Integer) pdUser.get("INTEGRAL") >= total) {
                pdOrder.put("payInt", payInt);//购买方式
                pdOrder.put("intTotal", total);


                pdOrder.put("STORE_ID", pdBind.getString("STORE_ID"));// 获取当前绑定门店的id
                orderService.save(pdOrder);

                //减用户积分  2019/4/12 新增购买数量 COUNT 所以得在原来基础上乘
                pdUser.put("INTEGRAL", (Integer) pdUser.get("INTEGRAL") - total);
                userService.editUserJf(pdUser);
                result.put("success", true);
                result.put("isOk", true);
            } else {
                result.put("isOk", false);
                result.put("message", "积分不足无法购买");
            }
        } else {
            /*用现金购买*/
            pdOrder.put("payCash", payCash);//购买方式
//       商品价格
            double price = (double) pd.get("price");
//            总金额
            double cashTotal = (COUNT * 1.0) * price;
            pdOrder.put("cashTotal", cashTotal);


            pdOrder.put("STORE_ID", pdBind.getString("STORE_ID"));// 获取当前绑定门店的id
            orderService.save(pdOrder);
            result.put("isOk", true);
            result.put("payForCash", true);
        }


        //购买成功，发送模板消息给网管
        if (result.get("isOk").toString().equals("true")) {
            String username = pdBind.getString("NAME");

            String title = "";
            if (StringUtil.isNotEmpty(pdd.getString("computer_name"))) {
                title = pdd.getString("computer_name") + "购买商品，请及时处理";
            } else {
                title = username + "购买商品，请及时处理";
            }

            JSONObject obj = new JSONObject();
            obj.put("keyword1", pd.getString("ANAME") + "，数量" + COUNT);
            if ("I".equals(payInt)) {
                obj.put("keyword2", (Integer) pd.get("INTEGRAL") * COUNT + "积分");
            } else {
                obj.put("keyword2",Double.parseDouble(pd.get("price").toString()) * COUNT + "元");
            }
            obj.put("keyword3", username);
            obj.put("keyword4", Tools.date2Str(new Date()));
            obj.put("keyword5", pdOrder.getString("ORDER_NUMBER"));

            //定义线程发送投诉模板网吧服务人员
            String type = "user_order";//自定义类型：下单
            PageData pdData = new PageData();
            pdData.put("first_data", title);//标题
            pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
            pdData.put("remark_data", "点击详情处理");//备注
            pdData.put("url", PublicManagerUtil.URL1 + "indexMember/goHandleTamplete.do?type=user_order&id=" + pdOrder.getString("ORDER_ID"));//跳转
            SendTemplateThread thread = new SendTemplateThread(internet_id, type, pdData);
            new Thread(thread).start();
        }

        return result;

    }


    /**
     * 判断是否可以抽奖
     *
     * @param pdd（传入internet_id）
     * @return 返回判断结果
     * @throws Exception
     */
    public JSONObject canLottery(PageData pdd) throws Exception {
        JSONObject result = new JSONObject();

        String internet_id = pdd.getString("internet_id");

        //抽奖设置是否设置了奖品
        PageData pd = new PageData();
        pd.put("internetId", internet_id);
        List<PageData> varList = lotteryService.listByInternet(pd);

        //积分消耗是否设置了抽奖所需积分
        PageData pdCons = new PageData();
        pdCons.put("CONSUMPTION_TYPE", "1");
        pdCons.put("INTENET_ID", internet_id);
        pdCons = consumptionService.findByInetrnet(pdCons);

        //只有两个都设置了才能抽奖
        if (varList.size() > 0 && StringUtil.isNotEmpty(pdCons)) {
            result.put("result", PublicManagerUtil.SUCCESS);
            result.put("backUrl", "/indexMember/activityGame.do");
        } else {
            result.put("result", PublicManagerUtil.FALSE);
            result.put("message", "商家尚未开启该活动");
        }

        return result;
    }


    /**
     * 加载抽奖奖品列表
     *
     * @param pdd（传入internet_id）
     * @return
     * @throws Exception
     */
    public JSONObject loadLottery(PageData pdd) throws Exception {
        JSONObject result = new JSONObject();

        String internet_id = pdd.getString("internet_id");

        PageData pdLottery = new PageData();
        pdLottery.put("internetId", internet_id);
        //加载网吧的奖品列表
        List<PageData> varList = lotteryService.listByInternet(pdLottery);
        StringBuffer obj = new StringBuffer();
        for (int i = 0; i < varList.size(); i++) {
            obj.append(varList.get(i).getString("LOTTERY_NAME") + ",");
        }
        String cj = obj.substring(0, obj.length() - 1).toString();
        result.put("cj", cj);


        //加载抽奖所需积分
        PageData pdCons = new PageData();
        pdCons.put("CONSUMPTION_TYPE", "1");
        pdCons.put("INTENET_ID", internet_id);
        pdCons = consumptionService.findByInetrnet(pdCons);
        Integer number = 0;
        if (StringUtil.isNotEmpty(pdCons)) {
            if (pdCons.containsKey("NUMBER")) {
                number = (Integer) pdCons.get("NUMBER");
            }
        }
        result.put("number", number);

        return result;
    }


    /**
     * 申请抽奖
     *
     * @param pdd（传入user_id和internet_id）
     * @return 返回申请结果
     * @throws Exception
     */
    public JSONObject applyLottery(PageData pdd) throws Exception {
        JSONObject result = new JSONObject();

        String internet_id = pdd.getString("internet_id");
        String user_id = pdd.getString("user_id");

        //加载用户信息
        PageData pdUser = new PageData();
        pdUser.put("USER_ID", user_id);
        pdUser = userService.findById(pdUser);

        //加载抽奖积分
        PageData pdCons = new PageData();
        pdCons.put("CONSUMPTION_TYPE", "1");
        pdCons.put("INTENET_ID", internet_id);
        pdCons = consumptionService.findByInetrnet(pdCons);

        if (StringUtil.isNotEmpty(pdCons)) {
            Integer integral = (Integer) pdCons.get("NUMBER");
            //判断用户积分是否够抽奖
            if ((Integer) pdUser.get("INTEGRAL") >= integral) {
                pdd.put("internetId", internet_id);
                List<PageData> varList = lotteryService.listByInternet(pdd);
                Integer prizeIndex = 0;
                Integer obj[] = new Integer[varList.size()];
                for (int i = 0; i < varList.size(); i++) {
                    obj[i] = (Integer) varList.get(i).get("LOTTERY_NUMBER");
                }
                prizeIndex = getRandPrize(obj);// 索引
                PageData pdBind = new PageData();
                pdBind.put("userId", user_id);
                pdBind = bunduserService.findByUser(pdBind);

                //保存中奖纪录
                PageData pdMemberLottery = new PageData();
                pdMemberLottery.put("MEMBERLOTTERY_ID", StringUtil.get32UUID()); // 主键id
                pdMemberLottery.put("USER_ID", user_id); // 用户
                pdMemberLottery.put("INTENET_ID", internet_id); // 网吧id
                pdMemberLottery.put("LOTTERY_ID", varList.get(prizeIndex).getString("LOTTERY_ID")); // 中奖奖品id
                pdMemberLottery.put("STATE", "1"); // 中奖状态
                pdMemberLottery.put("LOTTERY_TIME", Tools.date2Str(new Date())); // 抽奖时间
                pdMemberLottery.put("STORE_ID", pdBind.get("STORE_ID"));//抽奖时当前绑定的门店
                memberlotteryService.save(pdMemberLottery);

                //修改用户积分
                pdUser.put("INTEGRAL", (Integer) pdUser.get("INTEGRAL") - integral);
                userService.editUserJf(pdUser);

                //返回数据
                result.put("message", PublicManagerUtil.SUCCESS);
                result.put("num", prizeIndex + 1);
                result.put("type", varList.get(prizeIndex).getString("STATE"));
                result.put("name", varList.get(prizeIndex).getString("LOTTERY_NAME"));
                return result;
            } else {
                result.put("message", PublicManagerUtil.FAIL);
                return result;
            }
        } else {
            result.put("message", PublicManagerUtil.FAIL);
            return result;
        }

    }


    private Integer getRandPrize(Integer[] obj) {
        Integer result = null;// 奖项id
        int sum = 0; // 总概率
        for (int i = 0; i < obj.length; i++) {
            sum += obj[i];
        }
        for (int i = 0; i < obj.length; i++) {
            int randomNum = new Random().nextInt(sum) + 1;// 随机生成1到sum的整数
            if (randomNum <= obj[i]) {
                result = i;
                break;
            } else {
                sum -= obj[i];
            }
        }
        return result;
    }

    /**
     * 保存新增投诉
     *
     * @param pdd（传入user_id和internet_id，name，投诉提交的LM_CONTENT，LM_TYPEID，STORE_ID，fileArr图片数组）
     * @return
     * @throws Exception
     */
    public JSONObject saveLm(PageData pdd) throws Exception {
        JSONObject result = new JSONObject();

        String internet_id = pdd.getString("internet_id");
        String user_id = pdd.getString("user_id");
        String name = pdd.getString("name");

        //获取投诉的门店的信息
        PageData pdStore = new PageData();
        pdStore.put("STORE_ID", pdd.getString("STORE_ID"));
        pdStore = storeService.findById(pdStore);

        // 填充lm信息
        LeaveMessage leam = new LeaveMessage();
        leam.setLM_DATE(Tools.date2Str(new Date()));
        leam.setLM_ID(StringUtil.get32UUID());
        leam.setLM_USERID(user_id);
        leam.setLM_USERNAME(name);
        leam.setLM_TYPEID(pdd.getString("LM_TYPEID"));
        Intenet intent = intenetService.getIntenetById(internet_id);//获取网吧名称
        leam.setLM_INTERNET_NAME(intent.getINTENET_NAME());
        leam.setLM_STATE("2");
        leam.setLM_BACKTIME("");
        leam.setUSER_ID("");
        leam.setBACKCONTENT("");
        if (StringUtil.isNotEmpty(pdStore)) {
            leam.setSTROE_ID(pdStore.getString("STORE_ID"));
            leam.setLM_STROE_NAME(pdStore.getString("STORE_NAME"));
        }
        leam.setLM_CONTENT(pdd.getString("LM_CONTENT"));
        leam.setINTERNET_ID(internet_id);
        leam.setIs_VIEW("2");//2未读
        // 执行添加lm
        lmService.addLm(leam);
        // 上传图片
        String[] file = (String[]) pdd.get("fileArr");
        String ffile = DateUtil.getDays(), fileName = "";

        if (file != null && file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                String time = new Date().getTime() + "";
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + time + ".jpg";

                Tools.pic(file[i], filePath);

                String path = time + ".jpg";
                PageData pdPic = new PageData();
                pdPic.put("PICTURES_ID", StringUtil.get32UUID()); // 主键
                pdPic.put("TITLE", "意见图片" + ffile); // 标题
                pdPic.put("NAME", fileName); // 文件名
                pdPic.put("PATH", path); // 路径
                pdPic.put("CREATETIME", Tools.date2Str(new Date())); // 创建时间
                pdPic.put("MASTER_ID", "1"); // 附属与
                pdPic.put("LM_ID", leam.getLM_ID());
                pdPic.put("INTERNET_ID", internet_id); // 网吧id
                picturesService.save(pdPic);
            }
        }

        //定义线程发送投诉模板网吧服务人员
        String title = "";
        if (StringUtil.isNotEmpty(pdd.getString("computer_name"))) {
            title += pdd.getString("computer_name") + "：";
        }
        title += pdd.getString("LM_TYPENAME") + "，请及时处理";

        String username = URLDecoder.decode(leam.getLM_USERNAME(), "utf-8");
        pdd.put("userId", user_id);
        PageData pdUser = bunduserService.findByUser(pdd);
        if (StringUtil.isNotEmpty(pdUser)) {
            username = pdUser.getString("NAME");
        }
        JSONObject obj = new JSONObject();
        obj.put("keyword1", username);
        obj.put("keyword2", leam.getLM_DATE());

        String type = "user_complain";//自定义类型：投诉
        PageData pdData = new PageData();
        pdData.put("first_data", title);//标题(机器名： + 投诉类型 ，请及时处理)
        pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2}
        pdData.put("remark_data", leam.getLM_CONTENT());//备注
        pdData.put("url", PublicManagerUtil.URL1 + "indexMember/goHandleTamplete.do?type=user_complain&id=" + leam.getLM_ID());//跳转
        SendTemplateThread thread = new SendTemplateThread(internet_id, type, pdData);
        new Thread(thread).start();

        result.put("result", "true");
        result.put("message", "保存成功");
        result.put("backUrl", "/myMember/myLm.do");
        return result;
    }

    /**
     * 接下来通过卡券id,用户id去判断,该卡券是否适用于该用户的门店
     *
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject judgeStore(PageData pd) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd1 = (PageData) dao.findForObject("StoreMapper.judgeStore", pd);
        if (StringUtil.isEmpty(pd1)) {
            json.put("card_result", PublicManagerUtil.FALSE);
            json.put("message", "该卡券不适用于您的门店");
        } else {
            json.put("card_result", PublicManagerUtil.TRUE);
        }
        return json;
    }


    /**
     * 呼叫网管
     *
     * @param user_id 用户id
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject callStoreStaff(String user_id, String internet_id, String computer_name, String username) throws Exception {
        JSONObject result = new JSONObject();

        if (StringUtil.isEmpty(user_id) || StringUtil.isEmpty(user_id)) {
            result.put("result", "false");
            result.put("message", "无效参数");
            return result;
        }
        List<PageData> pdList = internetStaffService.getInternetStaff(internet_id);
        if (pdList.size() == 0) {
            result.put("result", "false");
            result.put("message", "商家暂未设置网管");
            return result;
        }

        username = URLDecoder.decode(username, "utf-8");
        PageData pdUser = new PageData();
        pdUser.put("userId", user_id);
        pdUser = bunduserService.findByUser(pdUser);
        if (StringUtil.isNotEmpty(pdUser)) {
            username = pdUser.getString("NAME");
        }

        String title = computer_name + "呼叫网管，请及时处理";
        JSONObject obj = new JSONObject();
        obj.put("keyword1", Tools.date2Str(new Date()));
        obj.put("keyword2", "需要帮助");
        obj.put("keyword3", computer_name + "：" + username);


        //定义线程发送投诉模板网吧服务人员
        String type = "user_call";//自定义类型：呼叫提醒
        PageData pdData = new PageData();
        pdData.put("first_data", title);//标题
        pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
        pdData.put("remark_data", "点击详情处理");//备注
        pdData.put("url", PublicManagerUtil.URL1 + "indexMember/goHandleTamplete.do?type=user_call&id=");//跳转
        SendTemplateThread thread = new SendTemplateThread(internet_id, type, pdData);
        new Thread(thread).start();

        result.put("result", "true");
        result.put("message", "呼叫成功");
        return result;
    }


    @Override
    public JSONObject checkUserSubscribe(String appid, String code, String cacheName, String sceneStr) throws Exception {
        JSONObject result = new JSONObject();

        Session session = Jurisdiction.getSession();
        PageData pdUser = new PageData();

        //扫授权二维码后微信推送的code，用于获取token，openid
        PageData pdMyop = new PageData();
        pdMyop.put("APPID", PublicManagerUtil.APPID);
        pdMyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
        pdMyop = myopService.findByAppId(pdMyop);

        //通过网页授权获取微信用户信息
        JSONObject userInfo = WeChatOpenUtil.getOpenAccessToken(appid, code,
                PublicManagerUtil.APPID, pdMyop.getString("COMPONENT_ACCESS_TOKEN"));

        String open_id = "";
        if (userInfo.containsKey("errcode") && userInfo.getString("errcode").equals("40163")) {
            //又调一次或者刷新调用
            open_id = (String) session.getAttribute("user_open_id");
        } else {
            open_id = userInfo.getString("openid");
            session.setAttribute("user_open_id", open_id);
        }
        if (StringUtil.isEmpty(open_id)) {
            result.put("result", "false");
            result.put("message", "未获取到微信用户信息");
            result.put("viewName", "internet/qrCode/info_tips");
            return result;
        }

        pdUser.put("OPEN_ID", open_id);
        pdUser = wechatuserService.findByOpenId(pdUser);
        //未关注的，没有数据的
        if (StringUtil.isEmpty(pdUser) || pdUser.getString("STATE").equals("0")) {
            result.put("result", "false");
            result.put("message", "请先关注微信公众号");
            result.put("err_type", "subscribe_err");
            result.put("viewName", "internet/qrCode/user_subscribe");

            //带场景的二维码
            PageData pdInternet = new PageData();
            pdInternet.put("APP_ID", appid);
            pdInternet = intenetService.findByappid(pdInternet).get(0);
            String internet_id = pdInternet.getString("INTENET_ID");

            //二维码(从缓存中查找，有效期1天)
            Cache cache = CacheManager.getWxQrCache();//得到缓存对象(含有效时间110分钟)
            JSONObject qr_json = (JSONObject) cache.getObject(cacheName + internet_id);//qr_，qr_subscribe_
            if (qr_json == null) {
                String token = autoReplyService.getAuthToken(internet_id);
                JSONObject scene = new JSONObject();
                scene.put("scene_str", sceneStr);//场景值
                qr_json = WechatUtil.generateQCode("temp", token, 25 * 60 * 60, scene);//1天有效
                //生成图片
                cache.insertObject(cacheName + internet_id, qr_json);
            }

            result.put("qr_img", qr_json.getString("url"));
            result.put("qr_img_base64", QrcodeUtil.putBase64(qr_json.getString("url"), 218, 218));

            if(StringUtil.isEmpty(pdUser) && userInfo.containsKey("access_token")){
                WechatUser person = WeixinUtil.getUserInfoByBgz(userInfo.getString("access_token"), open_id);
                if(StringUtil.isNotEmpty(person)){
                    pdUser = new PageData();
                    pdUser.put("IMGURL", person.getIMGURL());
                    pdUser.put("NECK_NAME", person.getNECK_NAME());
                    pdUser.put("OPEN_ID", open_id);

                }
            }
            pdUser.put("NECK_NAME", URLDecoder.decode(pdUser.getString("NECK_NAME"), "UTF-8"));
            result.put("pdUser", pdUser);
            return result;
        }else{
            pdUser.put("NECK_NAME", URLDecoder.decode(pdUser.getString("NECK_NAME"), "UTF-8"));
            //查询user放入session
        }

        if(StringUtil.isNotEmpty(pdUser.getString("USER_ID"))){
            sysuserService.getByUserId(pdUser.getString("USER_ID"));
            sysuserService.getByOpenId(open_id);
        }

        result.put("result", "true");
        result.put("pdUser", pdUser);
        return result;
    }

    class SendTemplateThread implements Runnable {
        private String internet_id;
        private String type;
        private PageData pdData;

        public SendTemplateThread(String internet_id, String type, PageData pdData) {
            this.internet_id = internet_id;
            this.type = type;
            this.internet_id = internet_id;
            this.pdData = pdData;
        }

        public void run() {
            try {
                List<PageData> pdList = internetStaffService.getInternetStaff(internet_id);
                for (PageData pdd : pdList) {
                    if (pdd.getString("perms").contains(type)) {
                        //给有接收服务的人员发消息
                        String open_id = pdd.getString("open_id");//网吧设置的人员
                        weixinService.sendTamplate(internet_id, open_id, type, pdData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取微信js api 的数据
     * @param url
     * @throws Exception
     */
    @Override
    public JSAPI getWXJSdata(String url, String internet_id) throws Exception{

        if(StringUtil.isEmpty(internet_id)){
            User user = Jurisdiction.getSessionUser().getUser();
            internet_id = user.getINTENET_ID();
        }

        String token = autoReplyService.getAuthToken(internet_id);
        if(StringUtil.isEmpty(token)){
            return null;
        }

        PageData pd = new PageData();
        pd.put("internet_id", internet_id);
        pd.put("token", token);
        String jsapi_ticketNew = terraceService.getJsApiTicketByInternetId(pd);


        JSAPI jsapi = WeixinUtil.getSign(jsapi_ticketNew, url);
        Intenet org = intenetService.getIntenetById(internet_id);
        jsapi.setAppid(org.getWECHAT_ID());
        jsapi.setInternet_id(org.getINTENET_ID());

        return jsapi;
    }
}
