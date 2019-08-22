package com.lk.controller.intuser;

import java.util.List;

import javax.annotation.Resource;

import com.lk.service.intuser.myMember.impl.MyMemberService;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.LeaveMessage;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.intuser.myMember.MyMemberManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.leaveMessage.LeaveMessageService;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.util.PageData;
import com.lk.util.StringUtil;

/**
 * 微信我的--Controller处理器
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/myMember")
public class MyMemberController extends BaseController {

    @Resource(name = "intenetService")
    private IntenetManager intenetService;
    @Resource(name = "bunduserService")
    private BundUserManager bunduserService;
    @Resource(name = "LeaveMessageService")
    private LeaveMessageService lmService;
    @Resource(name = "userFlowService")
	private UserFlowService userFlowService;
    
    @Resource(name = "myMemberService")
	private MyMemberManager myMemberService;
    @Resource(name = "indexMemberService")
	private IndexMemberManager indexMemberService;
	@Resource(name = "internetMemberService")
	private InternetMemberManager internetMemberService;
	@Resource(name="generateOrderService")
	private GenerateOrderService generateOrderService;
    /**
     * 我的页面
     * @return 正常返回微信用户的基本信息（微信昵称，卡号，绑定的门店，卡内余额，奖励金额，揽客积分，网咖积分）
     * @throws Exception
     */
    @RequestMapping(value = "/gotoUserCenter")
    public ModelAndView gotoUserCenter() throws Exception {
        ModelAndView mv = this.getModelAndView();
		//测试传入test_user_id，正式不用传
		PageData pdUser2 = this.getPageData();
		User user = internetMemberService.getUser(pdUser2);
		
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        
        PageData pdUser = new PageData();
        pdUser.put("user_id", user.getUSER_ID());
        pdUser = myMemberService.getWechatUserInfo(pdUser);


        mv.addObject("user", pdUser);
        mv.setViewName("intenetmumber/userCenter");
        return mv;
    }

    
    /**
     * 个人中心
     * @return 正常返回微信用户的基本信息（微信昵称，卡号，绑定的门店，卡内余额，奖励金额，揽客积分，网咖积分）
     * @throws Exception
     */
    @RequestMapping(value = "/userCenter")
    public ModelAndView userCenter() throws Exception {
        ModelAndView mv = this.getModelAndView();
		//测试传入test_user_id，正式不用传
		PageData pdUser2 = this.getPageData();
		User user = internetMemberService.getUser(pdUser2);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        PageData pdUser = new PageData();
        pdUser.put("user_id", user.getUSER_ID());
        pdUser = myMemberService.getWechatUserInfo(pdUser);
        
        //网吧名称
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());

        
        mv.addObject("user", pdUser);
        mv.setViewName("intenetmumber/userCenter");
        return mv;
    }

    /**
     * 异步加载我的数据
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/loadUserCenter")
    public JSONObject loadUserCenter() throws Exception{
        JSONObject result = new JSONObject();

        User user = this.getUser();//获取用户

        PageData pdUser = new PageData();
        pdUser.put("user_id", user.getUSER_ID());
        pdUser = myMemberService.getWechatUserInfo(pdUser);
        //判断是否绑定用户
        if(StringUtil.isNotEmpty(pdUser) && StringUtil.isNotEmpty(pdUser.getString("CARDED"))){
            //已绑定的用户实时更新顺网数据
            pdUser = myMemberService.updateUserInfo(pdUser);
        }

        //加载我的数据
        JSONObject data = myMemberService.loadMyData(pdUser, user);

        result.put("data", data);
        result.put("pdUser", pdUser);
        result.put("result", "true");
        return result;
    }

    /**
     * 查看我的明细
     * @throws Exception
     */
    @RequestMapping(value = "/myStatements")
    public ModelAndView myStatements() throws Exception {
        ModelAndView mv = this.getModelAndView();
        String user_id = this.getRequest().getParameter("user_id");
        User user = myMemberService.getUser(user_id);
        if(StringUtil.isEmpty(user)){
			mv.setViewName("userNull");
			return mv;
		}
        
        //判断用户是否绑定
        PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/myMember/myStatements.do");
		mv = indexMemberService.judgeBind(pd);
		if(mv.getModel().get("result").toString().equals("false")){
			//门店被禁用时，去换绑页面
			if(StringUtil.isNotEmpty(mv.getModel().get("store_state")) && mv.getModel().get("store_state").toString().equals("1")){
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}
		
		mv.setViewName("intenetmumber/money");
        return mv;
    }


    /**
	 * 加载我的明细
	 * @return 如果绑定的门店已开通加v，则返回其在门店的流水信息
	 * @throws Exception
	 */
    @RequestMapping(value = "/myStatementsJson")
    @ResponseBody
    public JSONObject myStatementsJson() throws Exception {
    	
        //测试传入test_user_id，正式不用传
  		PageData pdUser = this.getPageData();
  		User user = internetMemberService.getUser(pdUser);
		//清理垃圾订单(去除)
//		generateOrderService.deleteFailOrder(user.getUSER_ID());
        JSONObject result = new JSONObject();
        PageData pd = this.getPageData();
        pd.put("internet_id", user.getINTENET_ID());
        PageData pdBind = new PageData();
        pdBind.put("userId", user.getUSER_ID());
        pdBind = bunduserService.findByUser(pdBind);
        //判断微信用户是否绑定
        if(StringUtil.isNotEmpty(pdBind)){
        	result = myMemberService.loadMyStateMents(pd,pdBind);
        }
        return result;
    }
    
    
    /**
     * 查看明细详情（流水详情信息）
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "/statementDetail")
    public ModelAndView statementDetail() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
  		PageData pdUser = this.getPageData();
  		User user = internetMemberService.getUser(pdUser);
        if(StringUtil.isEmpty(user)){
			mv.setViewName("userNull");
			return mv;
		}
        
        PageData pd = this.getPageData();
        String flow_id = pd.getString("flowId");
        String desc = pd.getString("desc");
        pd.put("flow_id", flow_id);
        if("b".equals(desc)){
        	//处理中的订单详情查询
        	PageData recharge = generateOrderService.findByIdorder(flow_id);
        	mv.addObject("recharge",recharge);
        }else{
        	pd = userFlowService.findById(pd);
        }
        if(StringUtil.isNotEmpty(pd)){
        	mv.addObject("result",pd);
        }
        mv.setViewName("intenetmumber/moneyDetails");
        return mv;
    }

    
    /**
     * 查看上网记录
     * @return 如果绑定的门店已开通加v，则返回其在门店的流水信息
     * @throws Exception
     */
    @RequestMapping(value = "/internetRecord")
    public ModelAndView internetRecord() throws Exception {
        ModelAndView mv = this.getModelAndView();
        String user_id = this.getRequest().getParameter("user_id");
        User user = myMemberService.getUser(user_id);
        
        if(StringUtil.isEmpty(user)){
			mv.setViewName("userNull");
			return mv;
		}
		
        //判断用户是否绑定
        PageData pd = new PageData();
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("backurl", "/myMember/internetRecord.do");
		mv = indexMemberService.judgeBind(pd);
		if(mv.getModel().get("result").toString().equals("false")){
			//门店被禁用时，去换绑页面
			if(StringUtil.isNotEmpty(mv.getModel().get("store_state")) && mv.getModel().get("store_state").toString().equals("1")){
				return new ModelAndView(mv.getModel().get("url").toString());
			}
			return mv;
		}

		mv.addObject("card_id", ((PageData) mv.getModel().get("pdBind")).get("CARDED"));
        mv.setViewName("intenetmumber/record");
        return mv;
    }

    
	/**
	 * 获取上网信息列表
	 * @return 正常返回上网基本信息
	 * @throws Exception
	 */
	@RequestMapping("/userOnline")
	@ResponseBody
	public JSONObject userOnline() throws Exception {
		JSONObject result = new JSONObject();
		//测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
		
		//会接收到card_id,up_time
		PageData pd = this.getPageData();
		pd.put("internet_id", user.getINTENET_ID());

		result = myMemberService.loadMyUserOnLine(pd);
		return result;
	}

	
    /**
     * 我的抽奖
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/mySpoil")
    public ModelAndView mySpoil() throws Exception {
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
        mv.setViewName("intenetmumber/mySpoil");
        return mv;
    }

    
    /**
     * 我的抽奖---根据状态切换我的抽奖
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ckcj")
    @ResponseBody
    public JSONObject ckcj() throws Exception {
        JSONObject result = new JSONObject();
        //测试传入test_user_id，正式不用传
  		PageData pdUser = this.getPageData();
  		User user = internetMemberService.getUser(pdUser);
  		
        String typeId = this.getRequest().getParameter("type");
        PageData pd = new PageData();
        if (typeId != null && typeId != "") {
            //1.未兑奖；2兑奖中；3已兑奖；4已失效
            pd.put("STATE", typeId);
        } else {
            pd.put("STATE", "1");
        }
        pd.put("userId", user.getUSER_ID());

        result = myMemberService.loadMySpoil(pd);
        return result;
    }

    /**
     * 我的抽奖---申请兑换中奖奖品
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sqdj")
    @ResponseBody
    public JSONObject sqdj() throws Exception {
        JSONObject result = new JSONObject();
        //测试传入test_user_id，正式不用传
  		PageData pdUser = this.getPageData();
  		User user = internetMemberService.getUser(pdUser);
  		if(StringUtil.isEmpty(user)){
  			result.put("message", "无效的用户");
  			return result;
  		}
        //包含参数mayId
        PageData pd = this.getPageData();
        
        result = myMemberService.applySpoil(pd);
        
        return result;
    }

    
    /**
     * 我的优惠券
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/myCard")
    public ModelAndView myCard() throws Exception {
        ModelAndView mv = this.getModelAndView();
        //测试传入test_user_id，正式不用传
  		PageData pdUser = this.getPageData();
  		User user = internetMemberService.getUser(pdUser);
        //判断用户是否为空
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }

        //更新顺网用户数据
        new Thread(new MyMemberService.updateSWUser(user.getUSER_ID())).start();

        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());
        mv.setViewName("intenetmumber/myCard");
        return mv;
    }

    
    /**
     * 我的优惠券---根据状态切换我的优惠券
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/myfuli")
    @ResponseBody
    public JSONObject myfuli() throws Exception {
        JSONObject result = new JSONObject();
		//测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
        //返回type
        PageData pd = this.getPageData();
        String typeId = pd.getString("type");
		if (typeId != null && typeId != "") {
            pd.put("STATE", typeId);
        } else {
            pd.put("STATE", "1");
        }
		pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        //加载我的卡券信息
        result = myMemberService.loadMyCard(pd);
        return result;
    }

    /**
     * 我的订单
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myDingDan")
    public ModelAndView myDingDan() throws Exception {
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

        mv.setViewName("intenetmumber/myDingDan");
        return mv;
    }

    
    /**
     * 我的订单---根据状态切换我的订单
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dingdan")
    @ResponseBody
    public JSONObject dingdan() throws Exception {
        JSONObject result = new JSONObject();
		//测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
        //返回栏目类型type
        PageData pd = this.getPageData();
        String typeId = pd.getString("type");
        if (typeId != null && typeId != "") {
            pd.put("typeId", typeId);
        } else {
            pd.put("typeId", "1");
        }
        
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        result = myMemberService.loadMyOrder(pd);
        return result;
    }
    

    /**
     * 我的订单---申请提货
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sqth")
    @ResponseBody
    public JSONObject sqth() throws Exception {
        JSONObject result = new JSONObject();

        //传入ORDER_ID订单主键id
        PageData pd = this.getPageData();
        
        //申请提货
        result = myMemberService.applyOrder(pd);
        return result;
    }



    /**
     * 我的投诉
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myLm")
    public ModelAndView myLm() throws Exception {
        ModelAndView mv = this.getModelAndView();
		//测试传入test_user_id，正式不用传
		PageData pdUser = this.getPageData();
		User user = internetMemberService.getUser(pdUser);
        if (StringUtil.isEmpty(user)) {
            mv.setViewName("userNull");
            return mv;
        }
        Intenet org = intenetService.getIntenetById(user.getINTENET_ID());
        mv.addObject("intenetName", org.getINTENET_NAME());
        
        //加载投诉列表
        PageData pd = new PageData();
        pd.put("user_id", user.getUSER_ID());
        pd.put("internet_id", user.getINTENET_ID());
        JSONObject result = myMemberService.loadMyLm(pd);
        
        List<LeaveMessage> varList = (List<LeaveMessage>) result.get("varList");
//        this.getRequest().getSession().setAttribute("listp", varList);//疑似无用
        
        mv.addObject("varList", varList);
        mv.setViewName("intenetmumber/myLeaveMessage");
        return mv;
    }

    
    /**
     * 查看投诉回复
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ckhf")
    @ResponseBody
    public JSONObject ckhf() throws Exception {
        JSONObject gjson = new JSONObject();
        //传入LM_ID主键id
        PageData pd = this.getPageData();
        String lm_id = pd.getString("LM_ID");
        LeaveMessage lm = lmService.getOneByLMID(lm_id);

        //已读
        PageData pdLm = new PageData();
        pdLm.put("LM_ID", lm_id);
        pdLm.put("IS_VIEW", "1");//已回复
        lmService.updateLm(pdLm);

        gjson.put("message", lm.getBACKCONTENT()+"");
        return gjson;
    }


   
    

}
