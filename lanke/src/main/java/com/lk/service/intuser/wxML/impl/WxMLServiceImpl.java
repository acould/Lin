package com.lk.service.intuser.wxML.impl;

import com.lk.communicate.server.tcp.ChannelCache;
import com.lk.controller.intuser.IndexMemberController;
import com.lk.entity.Message;
import com.lk.entity.jiaLian.PreOrder;
import com.lk.entity.system.BundUser;
import com.lk.entity.system.User;
import com.lk.service.internet.jiaLian.JiaLianService;
import com.lk.service.intuser.myMember.impl.MyMemberService;
import com.lk.service.intuser.wxML.WxMLService;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.service.user.notify.NotifyService;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.util.*;
import com.lk.util.JiaLian.JiaLianUtils;
import com.lk.util.JiaLian.PayDemo;
import com.lk.util.JiaLian.YikeConfig;
import com.lk.util.unionPay.PayConfig;
import com.lk.wechat.util.TemplateMsgUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description 会员生活——业务层
 * @create 2019-06-14 11:31
 */
@Service
public class WxMLServiceImpl implements WxMLService {


    public static final Logger log = LoggerFactory.getLogger(WxMLServiceImpl.class);

    @Resource(name = "cardService")
    private CardManager cardService;

    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;
    @Autowired
    private NotifyService notifyService;

    @Resource(name = "intenetService")
    private IntenetManager intenetService;

    @Resource(name = "weixinService")
    private WeixinService weixinService;

    @Autowired
    private JiaLianService jiaLianService;

    @Resource(name = "generateOrderService")
    private GenerateOrderService generateOrderService;

    /**
     * 加载会员生活页面内容
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message loadML(PageData pd) throws Exception{

        User user = Jurisdiction.getSessionUser().getUser();

        //查询抵用券(用户所在的门店是否有抵用券)
        BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());
        if(bundUser == null){
            return Message.error("请先绑定门店");
        }

        List<PageData> rushList = cardService.findByStoreIdAndFavType(bundUser.getStore_id(), "RUSH");
        PageData pdCard = new PageData();
        if(rushList.size() > 0){
            pdCard = rushList.get(0);
        }

        //查询消息列表
        List<PageData> notifyList = notifyService.findByOpenId(bundUser.getOpen_id());
        for (int i = 0; i < notifyList.size(); i++) {
            PageData pdd = notifyList.get(i);

            pdd.put("create_time", pdd.get("create_time").toString().substring(0,16));
            if(pdd.get("read_time")!= null){
                pdd.put("read_time", pdd.get("read_time").toString());
            }

            if(pdd.getString("type").equals("card")){
                if(StringUtil.isEmpty(pdd.get("card_time_type"))){
                    notifyList.remove(i);
                    continue;
                }
                pdd = comboCardTime(pdd);
            }else if(pdd.getString("type").equals("matches")){
                if(StringUtil.isEmpty(pdd.get("enroll_start_time"))){
                    notifyList.remove(i);
                    continue;
                }
                pdd.put("enroll_time", pdd.get("enroll_start_time").toString().substring(0,10) + " 至 " +pdd.get("enroll_end_time").toString().substring(0,10));
            }
            notifyList.set(i, pdd);
        }

        PageData pdInternet = new PageData();
        pdInternet.put("INTENET_ID", user.getINTENET_ID());
        pdInternet = intenetService.findById(pdInternet);


        return Message.ok()
                .addData("pdCard", pdCard)
                .addData("notifyList", notifyList)
                .addData("store_name", bundUser.getStore_name())
                .addData("pdInternet", pdInternet);
    }


    @Override
    public Message readAct(PageData pd) throws Exception{

        //已阅读
        pd.put("is_read", "1");
        pd.put("read_time", Tools.date2Str(new Date()));
        notifyService.edit(pd);

        return Message.ok();
    }

    /**
     * 组合卡券时间
     * @param pdd
     * @return
     */
    public static PageData comboCardTime(PageData pdd){
        if((StringUtil.isNotEmpty(pdd.getString("card_time_type")) && pdd.getString("card_time_type").equals("DATE_TYPE_FIX_TIME_RANGE"))
            || (StringUtil.isNotEmpty(pdd.getString("TYPE")) && pdd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE"))
            ){
            pdd.put("available_time", pdd.getString("BEGIN_TIMESTAMP") + " - " + pdd.getString("END_TIMESTAMP"));
        }else if((StringUtil.isNotEmpty(pdd.getString("card_time_type")) && pdd.getString("card_time_type").equals("DATE_TYPE_FIX_TERM"))
                || (StringUtil.isNotEmpty(pdd.getString("TYPE")) && pdd.getString("TYPE").equals("DATE_TYPE_FIX_TERM"))
            ){
            String tian = pdd.getString("FIXED_BEGIN_TERM");
            if(tian.equals("0")){
                tian = "当";
            }
            pdd.put("available_time", "领取后" + tian + "天生效，"+ pdd.getString("FIXED_TERM") +"天内有效");
        }
        return pdd;
    }

    /**
     * 加载抵用券支付详情页面
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Message loadRushOrder(PageData pd) throws Exception{

        User user = Jurisdiction.getSessionUser().getUser();
        BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());
        if(bundUser == null){
            return Message.error("请先绑定门店");
        }
        //所有的抵用券
        List<PageData> rushList = cardService.findByStoreIdAndFavType(bundUser.getStore_id(), "RUSH");
        for (int i = 0; i < rushList.size(); i++) {
            //选中卡券的 抵用券
            List<PageData> validRush = cardService.findValidRushByCardId(rushList.get(i).getString("CARD_ID"));
            rushList.get(i).put("validRush", validRush);
        }

        return Message.ok()
                .addData("bundUser", bundUser)
                .addData("rushList", rushList);

    }

    /**
     * 获取下一个间隔时间
     * @param handsel_time
     * @param handsel_times
     * @param date
     * @return
     */
    public Date getNextTime(int handsel_time, int handsel_times, Date date) {
        if(handsel_times == 0) {//天
            date = Tools.sAddDays(date, handsel_time);
        }else if(handsel_times == 1) {//周
            date = Tools.sAddDays(date, handsel_time * 7);
        }else if(handsel_times == 2) {//月
            date = Tools.sAddMonth(date, handsel_time);
        }else if(handsel_times == 3) {//年
            date = Tools.sAddYear(date, handsel_time);
        }
        return date;
    }

    public Date getLastTimeByStr(int handsel_time, String handsel_times, Date date) {
        if(handsel_times.equals("DAY")) {//天
            date = Tools.sAddDays(date, -handsel_time);
        }else if(handsel_times.equals("WEEK")) {//周
            date = Tools.sAddDays(date, -handsel_time * 7);
        }else if(handsel_times.equals("MON")) {//月
            date = Tools.sAddMonth(date, -handsel_time);
        }else if(handsel_times.equals("YEAR")) {//年
            date = Tools.sAddYear(date, -handsel_time);
        }
        return date;
    }

    /**
     * 抵用券 购买
     * @param pd CARD_ID
     * @return
     * @throws Exception
     */
    @Override
    public Message rushPay(PageData pd) throws Exception{

        User user = Jurisdiction.getSessionUser().getUser();
        PageData pdCard = cardService.findCardAndSceneById(pd.getString("CARD_ID"));
        if(pdCard == null){
            return Message.error("该卡券不存在");
        }
        if(!pdCard.getString("FAV_TYPE").equals("RUSH")){
            return Message.error("该卡券非抵用券，不能购买！");
        }
        //判断购买次数限制
        String RUSH_BUY_NUMBER = pdCard.getString("RUSH_BUY_NUMBER");//次数
        String RUSH_BUY_TIME = pdCard.getString("RUSH_BUY_TIME");//间隔
        String RUSH_BUY_TIME_UNITS = pdCard.getString("RUSH_BUY_TIME_UNITS");//间隔单位

        //获取当前一个间隔后的上一个时间
        Date lastDate = getLastTimeByStr(Integer.parseInt(RUSH_BUY_TIME), RUSH_BUY_TIME_UNITS, new Date());

        //查询购买次数
        List<PageData> orderList = generateOrderService.findByCardIdAndUserId(user.getUSER_ID(), pdCard.getString("CARD_ID"),
                Tools.date2Str(lastDate), Tools.date2Str(new Date()));

        log.info("判断订单数量：：：" + orderList.size());
        //间隔时间内，购买次数 》 限制次数
        if(orderList.size() >= Integer.parseInt(RUSH_BUY_NUMBER)){
            //现在时间 < 下一个可购买时间，不可购买
            return Message.error("您已达到该卡券的购买次数限制");
        }

        BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());
        if(!ChannelCache.channelMap.containsKey(bundUser.getStore_id())){
            return Message.error("揽客客户端已断开连接，请先连接");
        }

        //判断是否开通嘉联支付
        PageData pdJL = jiaLianService.findByStoreId(bundUser.getStore_id());
        if(!pdJL.get("status").equals("1") && StringUtil.isEmpty(pdJL.getString("shop_secret"))){
            return Message.error("该门店尚未通过商户审核，在线充值无法使用");
        }

        //保存预下单，创建订单
        PageData pdOrder = new PageData();
        String merOrderId = Tools.getCenterMsgId2(PayConfig.msgSrcId);
        pdOrder.put("id", StringUtil.get32UUID());
        pdOrder.put("merOrderId", merOrderId);
        pdOrder.put("user_id", user.getUSER_ID());
        pdOrder.put("carded", bundUser.getCarded());
        pdOrder.put("user_name", user.getNAME());
        pdOrder.put("USER_ID", user.getUSER_ID());
        pdOrder.put("openid", bundUser.getOpen_id());
        pdOrder.put("store_id", bundUser.getStore_id());
        pdOrder.put("store_name", bundUser.getStore_name());
        pdOrder.put("internet_id", user.getINTENET_ID());
        pdOrder.put("internetrule_id", pdCard.getString("CARD_ID"));//卡券id
        pdOrder.put("rincipal_balance", pdCard.get("recharge"));//充值金额
        pdOrder.put("reward_balance", pdCard.get("handsel"));//奖励金额
        pdOrder.put("createtime", Tools.date2Str(new Date()));
        pdOrder.put("pay_state", 0);//创建订单
        pdOrder.put("pay_actualbalance", pdCard.get("recharge"));//实际支付金额
        pdOrder.put("pay_type", 0);//支付类型(0.微信支付)
        pdOrder.put("third_payment", 2);//状态：1银联；2嘉联
        String memo = "揽客抵用券，付" + pdCard.get("recharge").toString() + "元，得"
                + pdCard.get("handsel").toString() + "元。分"
                + pdCard.get("handsel_sum").toString() + "次送。";
        pdOrder.put("memo", memo);//备注
        generateOrderService.addOrder(pdOrder);

        //调起嘉联支付
        PreOrder preOrder = new PreOrder();
        preOrder.setShop_no(pdJL.getString("shop_no"));
        preOrder.setShop_order_no(merOrderId);

        String recharge = pdCard.get("recharge").toString();
        int pay = (int) (Float.parseFloat(recharge) * 100);

        String order_return_url = PublicManagerUtil.URL2 + "wxML/jLReturn.do";
        String order_callback_url = PublicManagerUtil.URL2 + "wxML/jLCallBack.do";

        preOrder.setTransaction_amount(pay);
        preOrder.setOrder_remark(memo);
        preOrder.setReturn_url(order_return_url);//支付成功后跳转地址
        preOrder.setCallback_url(order_callback_url);//回调地址
        preOrder.setTimestamp(String.valueOf(new Date().getTime()));//时间戳
        preOrder.setOrder_name("揽客抵用券");//订单名称
        preOrder.setSign(PayDemo.getSign(preOrder, pdJL.getString("shop_secret")));//签名
        preOrder.setClient_ip(YikeConfig.client_ip);//接入系统终端ip地址（不参与签名）

        //下单
        Message m = JiaLianUtils.unifiedOrder(preOrder);

        //修改订单状态为支付中
        if(m.getErrcode() == 0){
            PageData pd1 = new PageData();
            pd1.put("merOrderId", merOrderId);
            pd1.put("pay_state", "3");//支付中
            pd1.put("pay_starttime",Tools.date2Str(new Date()));//开始支付时间
            pd.put("pd", pd1);
            generateOrderService.editOrderState(pd);
        }

        return m.addData("merOrderId", merOrderId);
    }


    /**
     * 处理嘉联支付回调
     * @param pd 嘉联支付回调参数
     * @return
     */
    @Override
    public Message handleJLCallback(PageData pd) {

        int order_status = Integer.parseInt(pd.getString("orderStatus"));//订单状态
        String shop_order_no = pd.getString("shopOrderNo");//商户订单号
        try{
            pd.put("merOrderId", shop_order_no);
            PageData pdOrder = generateOrderService.findByIdormerOrderId(pd);
            if(StringUtil.isEmpty(pdOrder)){
                log.info(shop_order_no + "————查找不到该订单");
                return Message.error("查找不到该订单");
            }else if(pdOrder.getString("pay_state").equals("2")){
                log.info(shop_order_no + "————该订单已支付成功");
                return Message.error("该订单已支付成功");
            }

            PageData pd1 = new PageData();
            pd1.put("merOrderId", shop_order_no);
            pd1.put("pat_endtime", Tools.date2Str(new Date()));
            if (order_status == 1){
                //对支付订单成功，且未完成支付的订单进行处理
                log.info(shop_order_no + "订单支付成功");

                //验证签名是否正确
                if(StringUtil.isNotEmpty(pd.get("sign"))){
                    PageData pdJl = jiaLianService.findByStoreId(pdOrder.getString("store_id"));
                    if(!IndexMemberController.checkSign(pd, pdJl.getString("shop_secret"))){
                        log.info(shop_order_no + "————签名错误");
                        return Message.error("签名错误");
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


                PageData pdOrder2 = generateOrderService.findByIdormerOrderId(pd);
                // 充值成功 删除 订单表 添加历史记录表
                System.out.println("删除订单"+ pdOrder2.toString());
                generateOrderService.deleteOrder(pdOrder2.getString("merOrderId"));

                pdOrder2.put("third_payment", "2");//嘉联
                System.out.println("添加订单"+ pdOrder2.toString());
                generateOrderService.addOrderHistory(pdOrder2);

                //处理业务
                handelOrderFinish(pd);

            }else{
                log.info(shop_order_no + "订单支付失败");
                // 修改订单状态
                pd1.put("pay_state", 1);//支付失败
                pd.put("pd", pd1);
                generateOrderService.editOrderState(pd);
            }
        }catch (Exception e){
            log.info(shop_order_no + "订单支付发生异常");
            log.info(e.getMessage());
        }

        return Message.ok("处理成功");
    }


    /**
     * 处理订单完成，发送第一张抵用券
     * @param pd
     * @return
     */
    private Message handelOrderFinish(PageData pd) throws Exception{

        String shop_order_no = pd.getString("shopOrderNo");//商户订单号
        pd.put("merOrderId", shop_order_no);
        PageData pdOrder = generateOrderService.findByIdormerOrderId(pd);
        BundUser bundUser = sysuserService.getBundUserByUserId(pdOrder.getString("user_id"));

        //订单相关的卡券表
        PageData pdCard = cardService.findCardAndSceneById(pdOrder.getString("internetrule_id"));


        //保存需要发送的卡券，以及当前发送第一张卡券
        String first_send_id = "";
        String first_card_id = "";
        List<PageData> validRush = cardService.findValidRushByCardId(pdCard.getString("CARD_ID"));

        Date endDate = getNextTime((int) validRush.get(0).get("handsel_time") * (validRush.size()-1),
                (int) validRush.get(0).get("handsel_times") , new Date());

        for (int i = 0; i < validRush.size(); i++) {
            PageData pdCardd = validRush.get(i);
            int handsel_time = (int) pdCardd.get("handsel_time");
            int handsel_times = (int) pdCardd.get("handsel_times");
            Date date = getNextTime(handsel_time * i, handsel_times, new Date());

            PageData pdSendCard = new PageData();
            pdSendCard.put("id", StringUtil.get32UUID());//主键Id
            pdSendCard.put("card_id", pdCardd.get("CARD_ID"));//发送卡券id
            pdSendCard.put("open_id", bundUser.getOpen_id());//关联用户id
            pdSendCard.put("need_time", Tools.dateStr(date));//需要发送日期
            if(i == 0){
                pdSendCard.put("need_time", Tools.dateStr(new Date()));//第一次发送为当前时间
            }
            pdSendCard.put("state", "0");//发送状态(0-未发送,1-已发送,2-发送失败)
            pdSendCard.put("internet_id", pdOrder.getString("internet_id"));//网吧id
            pdSendCard.put("card_state", "0");//领取状态(0--未领取,1--已领取)
            pdSendCard.put("type", "RUSH");//卡券类型
            pdSendCard.put("order_id", shop_order_no);//订单号
            pdSendCard.put("sequence", i);//订单号
            pdSendCard.put("full_term", Tools.dateStr(new Date()) + "至" +Tools.dateStr(endDate));//赠送的持续时间段
            //保存冲送券推送信息
            cardService.saveCardOpen(pdSendCard);

            if(i == 0){
                first_send_id = pdSendCard.getString("id");
                first_card_id = pdSendCard.getString("card_id");
            }
        }

        JSONObject obj = new JSONObject();
        obj.put("keyword1", shop_order_no);//订单号
        obj.put("keyword2", pdCard.getString("SUB_TITLE"));//票券名称
        obj.put("keyword3", validRush.size());//票券张数
        obj.put("keyword4", pdCard.get("recharge"));//订单金额(总金额)
        obj.put("keyword5", Tools.date2Str(new Date()));//下单时间

        PageData pdData = new PageData();
        pdData.put("first_data", "恭喜您，已成功购买！");//标题
        pdData.put("keyword_data", obj.toString());//内容格式：{keyword1:1,keyword2:2,keyword3:3}
        pdData.put("remark_data", "点击详情领取第1张抵用券");//备注
        pdData.put("url", PublicManagerUtil.URL1 + "wxML/goLqCard.do?CARD_ID="
                + first_card_id
                + "&openid=" + bundUser.getOpen_id()
                + "&order_id=" + shop_order_no
                + "&sequence=0");//跳转

        JSONObject sendResult = weixinService.sendTamplate(pdOrder.getString("internet_id"), bundUser.getOpen_id(), "card_buy_success", pdData);
        if (StringUtil.isNotEmpty(sendResult.getString("errcode"))) {
            if (sendResult.getString("errcode").equals("0")) {//发送成功
                PageData pdSendCard = new PageData();
                pdSendCard.put("id", first_send_id);//主键Id
                pdSendCard.put("real_time", Tools.date2Str(new Date()));//实际发送时间
                pdSendCard.put("state", "1");//发送成功
                //去改变冲送券发送状态
                cardService.editCardOpen(pdSendCard);
            }else{
                PageData pdSendCard = new PageData();
                pdSendCard.put("id", first_send_id);//主键Id
                pdSendCard.put("state", "2");//发送失败
                //去改变冲送券发送状态
                cardService.editCardOpen(pdSendCard);
            }
        }

        return Message.ok();
    }







}
