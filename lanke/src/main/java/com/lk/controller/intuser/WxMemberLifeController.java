package com.lk.controller.intuser;

import com.lk.controller.base.BaseController;
import com.lk.entity.Message;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.intuser.myMember.impl.MyMemberService;
import com.lk.service.intuser.wxML.WxMLService;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.client.impl.ClientServiceImpl;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.service.tb.TemplateRecord.TemplateRecordService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * @author myq
 * @description 会员生活——控制层
 * @create 2019-06-13 19:26
 */
@Controller
@RequestMapping(value = "/wxML")
public class WxMemberLifeController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(WxMemberLifeController.class);
    @Resource(name = "internetMemberService")
    private InternetMemberManager internetMemberService;
    @Autowired
    private WxMLService wxMLService;

    @Resource(name = "memberMarkeService")
    private MemberMarkeManager memberMarkeService;

    @Resource(name = "cardService")
    private CardManager cardService;

    @Resource(name = "indexMemberService")
    private IndexMemberManager indexMemberService;

    @Autowired
    private TemplateRecordService templateRecordService;

    @Resource(name = "intenetService")
    private IntenetManager intenetService;

    /******************************************* 请求页面 ********************************/


    /**
     * 会员生活
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/memberLife")
    public ModelAndView memberLife() throws Exception{
        ModelAndView mv = this.getModelAndView();
        //微信菜单传入code
        String code = this.getRequest().getParameter("code");
        PageData pdUser = this.getPageData();
        pdUser.put("code", code);
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }


        //更新顺网用户数据
        new Thread(new MyMemberService.updateSWUser(user.getUSER_ID())).start();

        mv.setViewName("intenetmumber/memberLife/memberLife");
        return mv;
    }


    /**
     * 去卡券抵用券——订单页面
     * @return
     */
    @RequestMapping(value = "/goCardRushOrder")
    public ModelAndView goCardRushOrder() throws Exception{
        ModelAndView mv = this.getModelAndView();
        // 测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        mv.addObject("pd", pdUser);
        mv.setViewName("intenetmumber/card/cardRushOrder");
        return mv;
    }


    /**
     * 点击模板消息领取卡券（抵用券的模板）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goLqCard")
    public ModelAndView goLqCard() throws Exception{
        ModelAndView mv = this.getModelAndView();
        // 传入openid和CARD_ID
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        PageData pd = this.getPageData();//传入CARD_ID和open_id，order_id, sequence
        pd.put("object_id", pd.getString("CARD_ID"));
        pd.put("card_id", pd.getString("CARD_ID"));
        pd.put("open_id", pd.getString("openid"));
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("url", this.getUrl());
        //判断卡券是否已领取
        PageData pdCard = cardService.findByOrderIdAndCardId(pd);
        if(pdCard.getString("card_state").equals("1")){
            mv.addObject("status", "1");//已领取

        }else{
            // 获取js-sdk 卡券接口的相关信息
            JSONObject wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
            mv.addObject("wx", wx);
            mv.addObject("status", "0");//未领取

        }


        mv.addObject("pd", pd);
        mv.setViewName("intenetmumber/card/lq_card");
        return mv;
    }


    /**
     * 满时长券的模板消息，领取卡券
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goLqTermCard")
    public ModelAndView goLqTermCard() throws Exception {
        ModelAndView mv = this.getModelAndView();
        // 传入openid和CARD_ID,和主键id
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        PageData pd = this.getPageData();//传入CARD_ID和openid，和主键id（card_open的）
        pd.put("object_id", pd.getString("CARD_ID"));
        pd.put("card_id", pd.getString("CARD_ID"));
        pd.put("open_id", pd.getString("openid"));
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("url", this.getUrl());
        PageData pdCardOpen = cardService.findCardOpenById(pd.getString("id"));

        if(pdCardOpen.getString("card_state").equals("1")){
            mv.addObject("status", "1");//已领取

        }else{
            // 获取js-sdk 卡券接口的相关信息
            JSONObject wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
            mv.addObject("wx", wx);
            mv.addObject("status", "0");//未领取

        }
        pd.put("order_id", pd.getString("id"));
        mv.addObject("pd", pd);
        mv.setViewName("intenetmumber/card/lq_card");
        return mv;
    }


    /**
     * 点击会员生活中的消息领取卡券
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goMLLqCard")
    public ModelAndView goMLLqCard() throws Exception{
        ModelAndView mv = this.getModelAndView();
        // 传入CARD_ID
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }


        PageData pd = this.getPageData();//传入CARD_ID和open_id，order_id, sequence
        pd.put("user_id", user.getUSER_ID());
        pd.put("mayId", pd.getString("CARD_ID"));
        pd.put("internet_id", user.getINTENET_ID());
        JSONObject result = indexMemberService.receiveCard(pd);

        if(result.getString("card_result").equals("true")){
            // 获取js-sdk 卡券接口的相关信息
            pd.put("object_id", pd.getString("CARD_ID"));
            pd.put("open_id", user.getOPEN_ID());
            pd.put("url", this.getUrl());
            JSONObject wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
            mv.addObject("wx", wx);
            mv.addObject("status", 1);
        }else{
            mv.addObject("status", 0);
        }

        mv.addObject("result", result);
        mv.setViewName("intenetmumber/memberLife/lq_card");
        return mv;
    }


    /**
     * 查看模板详情信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goTempContent")
    public ModelAndView goTempContent() throws Exception{
        ModelAndView mv = this.getModelAndView();

        //传入参数 temp_id，openid
        PageData pd = this.getPageData();
        PageData pdTemp = templateRecordService.findById(pd.getString("temp_id"));

        if(pdTemp != null){
            PageData org = intenetService.findByInternetId(pdTemp.getString("internet_id"), false);
            pdTemp.put("author", org.getString("INTENET_NAME"));
            pdTemp.put("create_time", pdTemp.get("create_time").toString());
            pdTemp.put("update_time", pdTemp.get("update_time").toString());
            mv.addObject("status", 1);
        }else{
            mv.addObject("status", 0);//找不到内容
        }


        mv.addObject("pd", pdTemp);
        mv.setViewName("intenetmumber/memberLife/tempContent");
        return mv;
    }

    /******************************************* 请求方法 ********************************/


    /**
     * 加载会员列表页面
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadML")
    public Message loadML() throws Exception{

        //传入参数：暂无
        PageData pd = this.getPageData();


        return wxMLService.loadML(pd);
    }


    /**
     * 已阅读
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/readAct")
    public Message readAct() throws Exception{

        //传入参数：暂无
        PageData pd = this.getPageData();

        return wxMLService.readAct(pd);
    }


    /**
     * 加载抵用券，支付详情信息
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadRushOrder")
    public Message loadRushOrder() throws Exception{

        //c传入参数CARD_ID
        PageData pd = this.getPageData();

        return wxMLService.loadRushOrder(pd);
    }


    /**
     * 支付购买抵用券
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/rushPay")
    public Message rushPay() throws Exception{

        //c传入参数CARD_ID
        PageData pd = this.getPageData();

        return wxMLService.rushPay(pd);
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
        return "redirect:/wxML/goCardRushOrder.do?jl_status=rt_success";
    }


    /**
     * 嘉联——回调通知地址
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/jLCallBack")
    public void jLCallBack(PrintWriter writer) {
        //接收响应参数
        PageData pd = this.getPageData();
        log.info("嘉联——异步接收-jLCallBack:::" + pd.toString());


        Message m = wxMLService.handleJLCallback(pd);

        writer.write("success");
    }




}
