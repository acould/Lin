package com.lk.controller.intuser;

import com.lk.controller.base.BaseController;
import com.lk.entity.Message;
import com.lk.entity.Message2;
import com.lk.entity.system.BundUser;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.SessionUser;
import com.lk.entity.system.User;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.intuser.myMember.MyMemberManager;
import com.lk.service.system.Membership.MembershipManager;
import com.lk.service.system.branch.BranchManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.service.system.miniWeb.MiniWebManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.util.*;
import com.lk.util.data.Operation;
import com.lk.wechat.util.TemplateMsgUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 核心请求处理类
 */
@Controller
@RequestMapping(value = "/intenetmumber")
public class IntenetMemberController extends BaseController {

    @Resource(name = "intenetService")
    private IntenetManager intenetService;
    @Resource(name = "bunduserService")
    private BundUserManager bunduserService;
    @Resource(name = "storeService")
    private StoreManager storeService;
    @Resource(name = "cardService")
    private CardManager cardService;
    @Resource(name = "branchService")
    private BranchManager branchService;
    @Resource(name = "miniWebService")
    private MiniWebManager miniWebService;
    @Resource(name = "indexMemberService")
    private IndexMemberManager indexMemberService;
    @Resource(name = "myMemberService")
    private MyMemberManager myMemberService;
    @Resource(name = "internetMemberService")
    private InternetMemberManager internetMemberService;
    @Resource(name = "membershipService")
    private MembershipManager membershipService;
    @Resource(name = "memberMarkeService")
    private MemberMarkeManager memberMarkeService;


    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;

    public static final String TYPE7 = "DATE_TYPE_FIX_TIME_RANGE";
    public static final String TYPE8 = "DATE_TYPE_FIX_TERM";

    /**
     * 微信菜单--签到有礼
     *
     * @throws Exception
     */
    @RequestMapping(value = "/singIn")
    public ModelAndView singIn() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //微信菜单传入code，intenetId,测试传入test_user_id
        String code = this.getRequest().getParameter("code");
        PageData pdUser = this.getPageData();
        pdUser.put("code", code);
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        // 通过网吧id去判断网吧用户状态
        PageData pd1 = new PageData();
        pd1.put("internetId", user.getINTENET_ID());
        List<PageData> list = storeService.usercheck(pd1);
        Integer STATE = Integer.parseInt(list.get(0).get("STATE").toString());
        if (STATE != PublicManagerUtil.SYS_INTENET_STATE2) {//0注册用户,2正式用户,3停用用户,4流失用户
            mv.addObject("PHONE", list.get(0).get("PHONE"));
            mv.setViewName("userStop");
            return mv;
        }

        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID()); // 用户id
        pd.put("internet_id", user.getINTENET_ID());
        //签到
        JSONObject result = indexMemberService.signIn(pd);

        mv.addObject("message", result.get("message"));
        mv.setViewName("intenetmumber/qiandao");
        return mv;
    }

    /**
     * 微信菜单--抢福利首页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //微信菜单传入code，intenetId,测试传入test_user_id
        String code = this.getRequest().getParameter("code");
        PageData pdUser2 = this.getPageData();
        pdUser2.put("code", code);
        User user = internetMemberService.getUser(pdUser2);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("internetName", org.getINTENET_NAME());

        PageData pd1 = new PageData();
        pd1.put("internet_id", user.getINTENET_ID());
        pd1.put("internetId", user.getINTENET_ID());
        pd1.put("userid", user.getUSER_ID());
        // 通过网吧id去判断网吧用户状态
        List<PageData> list = storeService.usercheck(pd1);
        Integer STATE = Integer.parseInt(list.get(0).get("STATE").toString());
        if (STATE != PublicManagerUtil.SYS_INTENET_STATE2) {//0注册用户,2正式用户,3停用用户,4流失用户
            mv.addObject("PHONE", list.get(0).get("PHONE"));
            mv.setViewName("userStop");
            return mv;
        }

        PageData pdUser = new PageData();
        pdUser.put("user_id", user.getUSER_ID());
        pdUser = myMemberService.getWechatUserInfo(pdUser);


        //去查询最近的秒抢券信息
        PageData cardGrab = cardService.cardGrab(pd1);
        mv.addObject("cardGrab", cardGrab);



        //修改微信用户信息(头像)
        if(StringUtil.isNotEmpty(code)){
            membershipService.information(pd1);
        }
        mv.addObject("user", pdUser);
        mv.setViewName("intenetmumber/index");
        return mv;
    }


    /**
     * 异步加载首页数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loadIndex")
    @ResponseBody
    public JSONObject loadIndex() throws Exception {
        JSONObject result = new JSONObject();

        User user = this.getUser();//获取用户

        PageData pdUser = new PageData();
        pdUser.put("user_id", user.getUSER_ID());
        pdUser = myMemberService.getWechatUserInfo(pdUser);
        //判断是否绑定用户
        if (StringUtil.isNotEmpty(pdUser) && StringUtil.isNotEmpty(pdUser.getString("CARDED"))) {
            //已绑定的用户实时更新顺网数据
            pdUser = myMemberService.updateUserInfo(pdUser);
        }

        BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());
        if(null != bundUser){
            //加载抵用券
            List<PageData> rushList = cardService.findByStoreIdAndFavType(bundUser.getStore_id(), "RUSH");
            if(rushList.size() > 0){
                PageData pdCard = rushList.get(0);
                result.put("pdCard", pdCard);
            }
        }


        //加载会员申请
        PageData pd1 = new PageData();
        pd1.put("internet_id", user.getINTENET_ID());
        pd1.put("internetId", user.getINTENET_ID());
        pd1.put("userid", user.getUSER_ID());
        PageData view = membershipService.view(pd1);

        //加载连续上网满时长券
        PageData cardTerm = cardService.cardTerm(pd1);


        result.put("cardTerm", cardTerm);
        result.put("view", view);
        result.put("pdUser", pdUser);
        result.put("result", "true");
        return result;
    }


    /**
     * 统一去判断是否有上网满时长券
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/judgeCard")
    @ResponseBody
    public JSONObject judgeCard() throws Exception {
        logBefore(logger, "统一去判断是否有上网满时长券");
        JSONObject json = new JSONObject();
        User user = this.getUser();//获取用户

        PageData pd = new PageData();
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("internetId", user.getINTENET_ID());//传入网吧id
        PageData view = membershipService.view(pd);
        if (StringUtil.isEmpty(view)) {
            json.put("falg", false);
        } else {
            json.put("falg", true);
            json.put("view", view);
        }
        //去查询是否有连续上网满时长券
        PageData cardTerm = cardService.cardTerm(pd);
        if (StringUtil.isEmpty(cardTerm)) {
            json.put("result", PublicManagerUtil.FALSE);
        } else {
            json.put("result", PublicManagerUtil.TRUE);
            json.put("cardTerm", cardTerm);
        }
        return json;
    }

    /**
     * 微信菜单---微官网
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goToMiniWeb")
    public String goToMiniWeb() throws Exception {
        //微信菜单传入code，intenetId,测试传入test_user_id
        String code = this.getRequest().getParameter("code");
        PageData pdUser = this.getPageData();
        pdUser.put("code", code);
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            return "userNull";
        }
        PageData pd1 = new PageData();
        pd1.put("internetId", user.getINTENET_ID());
        // 通过网吧id去判断网吧用户状态
        List<PageData> list = storeService.usercheck(pd1);
        Integer STATE = Integer.parseInt(list.get(0).get("STATE").toString());
        if (STATE != PublicManagerUtil.SYS_INTENET_STATE2) {//0注册用户,2正式用户,3停用用户,4流失用户
            return "userStop";
        }

        PageData pd = new PageData();
        pd.put("INTERNET_ID", user.getINTENET_ID());
        pd = miniWebService.findByInternetId(pd);
        if (StringUtil.isEmpty(pd)) {
            return "intenetmumber/miniWeb";
        } else {
            return "redirect:" + pd.getString("URL");
        }
    }


    /**
     * 微信菜单---找门店
     *
     * @throws Exception
     */
    @RequestMapping(value = "/introduction")
    public ModelAndView introduction() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //微信菜单传入code，intenetId,测试传入test_user_id
        String code = this.getRequest().getParameter("code");
        PageData pdUser = this.getPageData();
        pdUser.put("code", code);
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        PageData pd = new PageData();
        pd.put("internetId", user.getINTENET_ID());
        // 通过网吧id去判断网吧用户状态
        List<PageData> list = storeService.usercheck(pd);
        Integer STATE = Integer.parseInt(list.get(0).get("STATE").toString());
        if (STATE != PublicManagerUtil.SYS_INTENET_STATE2) {//0注册用户,2正式用户,3停用用户,4流失用户
            mv.addObject("PHONE", list.get(0).get("PHONE"));
            mv.setViewName("userStop");
            return mv;
        }

        mv.setViewName("intenetmumber/wangkaList");
        return mv;
    }



    @RequestMapping(value = "/loadInternetStore")
    @ResponseBody
    public JSONObject loadInternetStore() {
        JSONObject result = new JSONObject();

        //keywords
        PageData pd = this.getPageData();

        pd.put("pre", getPre());
        try {
            result = internetMemberService.loadInternetStore(pd);
        } catch (Exception e) {
            result.put("result", "false");
            result.put("message", "数据加载异常");
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 注册获取验证码/修改手机号，新手机号验证码
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getcode")
    @ResponseBody
    public JSONObject getcode(String phone) throws Exception {
        JSONObject result = new JSONObject();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            result.put("message", PublicManagerUtil.FALSE);
            result.put("result", "用户为空，请先关注微信公众号或者从微信公众号重新进入");
            return result;
        }

        //传入phone
        PageData pd = new PageData();
        pd.put("phone", phone);
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("ip", user.getIP());
        result = internetMemberService.getYZM(pd);

        return result;
    }

    /**
     * 去到绑定卡界面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/bindCard")
    public ModelAndView bindCard() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        String backurl = "/intenetmumber/index.do";//首页
        if (StringUtil.isNotEmpty(pdUser.getString("label")) && pdUser.getString("label").equals("user")) {
            backurl = "/myMember/userCenter.do";//我的
        } else if (StringUtil.isNotEmpty(pdUser.getString("label")) && pdUser.getString("label").equals("qr_bind")) {
            backurl = "/retrospectCenter/userIndex.do";//一码通首页
            String param = "";
            if (this.getRequest().getQueryString() != null) {
                param = "?" + this.getRequest().getQueryString();
                backurl += param;
            }
        }

        //判断用户是否绑定
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("backurl", backurl);
        mv = indexMemberService.judgeBind(pd);
        mv.addObject("backurl", backurl);//返回url
        PageData pd1 = new PageData();
        pd1.put("internet_id", user.getINTENET_ID());
        PageData view = membershipService.view(pd1);
        mv.addObject("view", view);
        if (mv.getModel().get("result").toString().equals("false")) {
            if (StringUtil.isEmpty(mv.getModel().get("store_state"))) {
                return mv;
            }
        }

        mv.setViewName("intenetmumber/bindCard");
        return mv;
    }


    /**
     * 加载网吧的城市，区域列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loadCityInternet")
    @ResponseBody
    public JSONObject loadCityInternet() throws Exception {
        // 获取该网吧下所有城市，以及城市里的所有的区域，以及该区域内所有的门店

        User user = this.getUser();//获取用户

        JSONObject json = myMemberService.getCityList(user.getINTENET_ID());
        List<PageData> cityList = (List<PageData>) json.get("cityList");

        JSONObject result = new JSONObject();
        result.put("cityList", cityList);
        return result;
    }

    /**
     * 申请会员加载网吧的城市，区域列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/memberInternet")
    @ResponseBody
    public JSONObject memberInternet() throws Exception {
        // 获取该网吧下所有城市，以及城市里的所有的区域，以及该区域内所有的门店

        User user = this.getUser();//获取用户

        JSONObject json = membershipService.getCityList(user.getINTENET_ID());
        List<PageData> cityList = (List<PageData>) json.get("cityList");

        JSONObject result = new JSONObject();
        result.put("cityList", cityList);
        return result;
    }

    /**
     * 保存绑定会员卡信息
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/bindings")
    @ResponseBody
    public JSONObject bindings() throws Exception {
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //传入身份证，门店id，手机号，姓名，验证码
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());

        pd.put("sfz", this.getRequest().getParameter("sfz"));
        pd.put("STORE_ID", this.getRequest().getParameter("STORE_ID"));
        pd.put("phone", this.getRequest().getParameter("phone"));
        pd.put("name", this.getRequest().getParameter("name"));
        pd.put("verificationCode", this.getRequest().getParameter("verificationCode"));
        JSONObject result = internetMemberService.saveBind(pd);

        return result;
    }


    /**
     * 去member页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/member")
    public ModelAndView member() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        //判断用户是否绑定
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("backurl", "/myMember/userCenter.do");
        mv = indexMemberService.judgeBind(pd);
        if (mv.getModel().get("result").toString().equals("false")) {
            if (StringUtil.isEmpty(mv.getModel().get("store_state"))) {
                return mv;
            }
        }

        pd.put("state", "1");
        List<PageData> storeList = storeService.findStoreByOthers(pd);//查询启用的门店数量
        mv.addObject("size", storeList.size());

        mv.addObject("backurl", "/myMember/userCenter.do");
        mv.setViewName("intenetmumber/member");
        return mv;
    }


    /**
     * 去换绑页面
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        //判断用户是否绑定
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("backurl", "/myMember/userCenter.do");
        mv = indexMemberService.judgeBind(pd);
        if (mv.getModel().get("result").toString().equals("false")) {
            if (StringUtil.isEmpty(mv.getModel().get("store_state"))) {
                return mv;
            }
        }
        PageData pd1 = new PageData();
        pd1.put("internet_id", user.getINTENET_ID());
        PageData view = membershipService.view(pd1);
        mv.addObject("view", view);
        if (view != null) {
            System.out.println("=========view============" + view.getString("typeo"));
        }
        mv.addObject("backurl", "/myMember/userCenter.do");
        mv.setViewName("intenetmumber/bindCard");
        return mv;
    }


    /**
     * 检查是否可更换门店
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/checkTime")
    @ResponseBody
    public JSONObject checkTime() throws Exception {
        JSONObject gjson = new JSONObject();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        PageData pdBind = new PageData();

        pdBind.put("userId", user.getUSER_ID());
        pdBind = bunduserService.findByUser(pdBind);

        //距离上次换绑需24小时后才能再试解绑
        //现取消绑定时间限制
//		Date dateTime = Tools.sAddDays(new Date(), -1);
//		Date dateTimeEnd = Tools.str2Date(pdBind.getString("CREATE_TIME"));
//		if (dateTime.getTime() < dateTimeEnd.getTime()) {
//			gjson.put("result", PublicManagerUtil.FALSE);
//			gjson.put("message", "24小时之后才能重新解绑");
//			return gjson;
//		} else {
//			gjson.put("result", PublicManagerUtil.SUCCESS);
//			return gjson;
//		}
        gjson.put("result", PublicManagerUtil.SUCCESS);
        return gjson;
    }


    /**
     * 跳转申请会员成功页
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/succeed")
    public ModelAndView succeed() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        if (StringUtil.isNotEmpty(pdUser.getString("falg"))) {
            if (!"CHENGGONG".equals(pdUser.getString("falg"))) {
                mv.setViewName("userNull");
                return mv;
            }
        }
        PageData pd1 = new PageData();
        pd1.put("internet_id", user.getINTENET_ID());
        PageData view = membershipService.view(pd1);
        System.out.println("============couponid===============" + view.getString("couponid"));
        if (StringUtil.isNotEmpty(view.getString("couponid"))) {
            PageData pdInternet = membershipService.selectCard(view.getString("couponid"));
            System.out.println("===pdInternet----------===" + pdInternet);
            if (StringUtil.isNotEmpty(pdInternet)) {
                String cardId = pdInternet.getString("CARD_ID");
                PageData pdStore = new PageData();
                pdStore.put("CARD_ID", cardId);
                pdStore.put("state", "1");// 1--加V状态
                List<PageData> sList = memberMarkeService.listByCardId(pdStore);// 通过卡券id获取门店信息
                String storeName = "";
                if (sList.size() > 0) {
                    for (PageData pds : sList) {
                        storeName += pds.getString("STORE_NAME") + "，";
                    }
                    pdInternet.put("STORE_NAME",
                            storeName.substring(0, storeName.length() - 1));
                }
                if (pdInternet.getString("TYPE").equals(TYPE7)) {
                    pdInternet.put("AVAILABLE_TIME",
                            pdInternet.getString("BEGIN_TIMESTAMP") + "至"
                                    + pdInternet.getString("END_TIMESTAMP"));
                } else if (pdInternet.getString("TYPE").equals(TYPE8)) {
                    String name = "";
                    if (pdInternet.getString("FIXED_BEGIN_TERM").equals("0")) {
                        name = "当天";
                    } else {
                        name = pdInternet.getString("FIXED_BEGIN_TERM") + "天";
                    }
                    pdInternet.put("AVAILABLE_TIME", "自领取后" + name + "生效，"
                            + pdInternet.getString("FIXED_TERM") + "天内有效");
                }
            }
            mv.addObject("pdInternet", pdInternet);
        }
        PageData pdS = new PageData();
        String falgcard = membershipService.CardIdandUserIdbycard(pdS);// 通过卡券id获取门店信息

        String advertisement = view.getString("advertisement");
        mv.addObject("advertisement", advertisement);
        mv.setViewName("intenetmumber/succeed");
        return mv;
    }

    /**
     * 跳转申请会员页
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/Membership0")
    public ModelAndView Membership0() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        PageData pd1 = new PageData();
        pd1.put("internet_id", user.getINTENET_ID());

        String advertisement = "";
        PageData pdView = membershipService.view(pd1);
        if (StringUtil.isNotEmpty(pdView)) {
            advertisement = membershipService.view(pd1).getString("advertisement");
        }
        PageData pdUser1 = new PageData();
        pdUser1.put("user_id", user.getUSER_ID());
        pdUser1 = myMemberService.getWechatUserInfo(pdUser1);

        if(StringUtil.isNotEmpty(pdUser1.getString("CARDED"))){
            //已经是会员不需要申请
            String url = "redirect:/intenetmumber/goCommonTips.do?" +
                    "title="+URLEncoder.encode(Operation.opera_apply_member.getTitle(), "utf-8")+
                    "&content="+URLEncoder.encode(Operation.opera_apply_member.getContent(), "utf-8");
            return new ModelAndView(url);
        }

        mv.addObject("user", pdUser1);
        mv.addObject("advertisement", advertisement);
        mv.setViewName("intenetmumber/Membership0");
        return mv;
    }

    /**
     * 0元申请会员处理
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/Membership1")
    @ResponseBody
    public JSONObject Membership1() throws Exception {
        // 测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        // 传入身份证，门店id，手机号，姓名，验证码
        //判断用户是否绑定
        PageData pdUser1 = new PageData();
        pdUser1.put("user_id", user.getUSER_ID());
        pdUser1 = myMemberService.getWechatUserInfo(pdUser1);
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("sfz", pdUser.getString("sfz"));
        pd.put("STORE_ID", pdUser.getString("STORE_ID"));
        pd.put("phone", pdUser.getString("phone"));
        pd.put("name", pdUser.getString("name"));
        pd.put("verificationCode",
                this.getRequest().getParameter("verificationCode"));
        PageData pdme = new PageData();
        pdme.put("user_id", user.getUSER_ID());
        pdme.put("card_id", pdUser.getString("sfz"));
        pdme.put("store_id", pdUser.getString("STORE_ID"));
        pdme.put("meid", this.get32UUID());
        PageData pdData = membershipService.selectfind(pdme);
        //判断是否绑定用户
        if (StringUtil.isNotEmpty(pdData)) {
            JSONObject result = new JSONObject();
            result.put("message", PublicManagerUtil.FALSE);
            result.put("result", "您已经是会员啦！");
            return result;
        }
        PageData storeObject = membershipService.StoreObject(pdUser.getString("STORE_ID"));
        JSONObject param = new JSONObject();
        param.put("card_id", pdUser.getString("sfz"));
        param.put("user_name", pdUser.getString("name"));
        param.put("user_password", "");
        param.put("id_card", pdUser.getString("sfz"));
        param.put("id_type", 1);
        param.put("phone_number", pdUser.getString("phone"));
        param.put("member_level", storeObject.getString("member_level"));
        param.put("confirm_type", 0);
        JSONObject rechargeResult = membershipService.level(this.getRequest()
                .getParameter("STORE_ID"), param, pd);
        if (!"0".equals(rechargeResult.getString("errcode"))) {
            JSONObject result = new JSONObject();
            result.put("message", PublicManagerUtil.FALSE);
            result.put("result", rechargeResult.getString("errmsg"));
            return result;
        }
        //添加会员申请记录
        membershipService.addmemberfind(pdme);

        JSONObject result = internetMemberService.saveBind(pd);
        result.put("falg", "CHENGGONG");
        return result;
    }


    /**
     * 更换绑定的门店
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/changeCard")
    @ResponseBody
    public JSONObject changeCard() throws Exception {
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //传入门店id，手机号，验证码
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());

        pd.put("STORE_ID", this.getRequest().getParameter("STORE_ID"));
        pd.put("phone", this.getRequest().getParameter("phone"));
        pd.put("verificationCode", this.getRequest().getParameter("verificationCode"));

        JSONObject result = internetMemberService.updateBind(pd);
        return result;
    }


    /**
     * 重新绑定会员信息（申请加v后，会员信息匹配不上pubwin的会员）
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/rebind")
    public ModelAndView rebind() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        String backurl = this.getRequest().getParameter("backurl");
        PageData pd = new PageData();
        pd.put("backurl", backurl);
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        mv = internetMemberService.rebind(pd);

        mv.addObject("label", "rebind");
        return mv;
    }


    /**
     * 网吧详细介绍
     *
     * @throws Exception
     */
    @RequestMapping(value = "/wangkaCenter")
    public ModelAndView wangkaCenter() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
        PageData pdUser = this.getPageData();
        User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        //获取门店信息
        String store = this.getRequest().getParameter("storeId");
        PageData pd = new PageData();
        pd.put("STORE_ID", store);
        pd = storeService.findById(pd);
        pd.put("HEAD_IMG", org.getHEAD_IMG());
        mv.addObject("pd", pd);

        //加载门店的图片信息
        PageData pdStore = new PageData();
        pdStore.put("STORE_ID", store);
        pdStore.put("pre", getPre());
        List<PageData> varList = branchService.listAll(pdStore);

        mv.addObject("varList", varList);
        mv.setViewName("intenetmumber/wangkaCenter");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/checkSWUser")
    public Message2 checkSWUser(String type) throws Exception{
        if(type != null){
            String backurl = getTypeShort(type);

            User user = this.getUser();//获取用户

            PageData pdBind = new PageData();
            pdBind.put("userId", user.getUSER_ID());
            pdBind = bunduserService.findByUser(pdBind);
            if(pdBind != null && pdBind.getString("IS_SW").equals("0")){
                //没有对接顺网数据的用户
                return indexMemberService.judgeUserInfo(pdBind.getString("STORE_ID"), pdBind.getString("CARDED"), pdBind.getString("NAME"))
                        .addData("backurl", backurl);
            }
        }
        return Message2.ok();
    }

    @RequestMapping(value = "/goBindingError")
    public ModelAndView goBindingError(String backurl){
        ModelAndView mv = this.getModelAndView();

        mv.addObject("backurl", backurl);
        mv.setViewName("intenetmumber/binding-error");
        return mv;
    }

    /**
     * 前往信息提示页面
     * @param
     * @return
     */
    @RequestMapping(value = "/goCommonTips")
    public ModelAndView goCommonTips(String title, String content)throws Exception{
        ModelAndView mv = this.getModelAndView();

        JSONObject result = this.getErrorJson();
        result.put("title", URLDecoder.decode(title, "utf-8"));
        result.put("content", URLDecoder.decode(content, "utf-8"));



        mv.addObject("result", result);
        mv.setViewName("intenetmumber/infoTip/common_tips");
        return mv;
    }


    public static String getTypeShort(String type){
        String backurl = "";
        switch (type){
            case "benefits" :
                backurl = "/indexMember/benefits.do";
                break;
            case "activityGame" :
                backurl = "/indexMember/activityGame.do";
                break;
            case "recharge" :
                backurl = "/indexMember/recharge.do";
                break;
            case "mall" :
                backurl = "/indexMember/mall.do";
                break;
            case "book" :
                backurl = "/indexMember/book.do";
                break;

            case "myStatements" :
                backurl = "/myMember/myStatements.do";
                break;
            case "internetRecord" :
                backurl = "/myMember/internetRecord.do";
                break;
        }
        return backurl;
    }


    /**
     * 解绑会员用户
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unbindCard")
    public Message unbindCard() throws Exception{
        User user = this.getUser();
        if(user == null){
            return Message.error("当前用户为空，请重新进入公众号");
        }

        BundUser bundUser = sysuserService.getBundUserByUserId(user.getUSER_ID());
        if(bundUser == null){
            return Message.error("请先绑定门店");
        }

        int num = bunduserService.delByUserId(user.getUSER_ID());
        if(num > 0){
            sysuserService.clearSessionUser(false, false, true);//清理session
            return Message.ok("解绑成功");
        }

        return Message.error("请先绑定门店");
    }

}
