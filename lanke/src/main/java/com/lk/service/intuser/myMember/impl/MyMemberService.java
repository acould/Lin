package com.lk.service.intuser.myMember.impl;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.lk.entity.Message2;
import com.lk.entity.billecenter.SWQrParam;
import com.lk.entity.billecenter.SWUser;
import com.lk.service.billiCenter.userBoard.UserBoardService;
import com.lk.service.system.sysuser.SysUserManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.LeaveMessage;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.userDown.UserDownService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.intuser.myMember.MyMemberManager;
import com.lk.service.system.auctionpictures.AuctionPicturesManager;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.cancel.CancelManager;
import com.lk.service.system.dictenty.DictEntyManager;
import com.lk.service.system.leaveMessage.LeaveMessageService;
import com.lk.service.system.lottery.LotteryManager;
import com.lk.service.system.memberlottery.MemberLotteryManager;
import com.lk.service.system.order.OrderManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.pay.GenerateOrderService;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import org.springframework.web.context.ContextLoader;

/**
 * 说明：微信端---我的页面---业务层
 *
 */
@Service("myMemberService")
public class MyMemberService implements MyMemberManager{

    private Logger logger = LoggerFactory.getLogger(MyMemberService.class);
	public static final String PICKUP    = "去提货";
	public static final String APPLYING  = "申请中";
	public static final String DELIVERY  = "提货成功";
	
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "userService")
	private UserManager userService;
    @Resource(name = "wechatuserService")
    private WeChatUserManager wechatuserService;
    @Resource(name = "bunduserService")
    private BundUserManager bunduserService;
    @Resource(name = "storeService")
    private StoreManager storeService;
    @Resource(name = "lotteryService")
    private LotteryManager lotteryService;
    @Resource(name = "memberlotteryService")
    private MemberLotteryManager memberlotteryService;
    @Resource(name = "cancelService")
    private CancelManager cancelService;
    @Resource(name = "auctionPicturesService")
    private AuctionPicturesManager auctionPicturesService;
    @Resource(name = "orderService")
    private OrderManager orderService;

    @Resource(name = "LeaveMessageService")
    private LeaveMessageService lmService;
    @Resource(name = "dictentyService")
  	private DictEntyManager dictentyService;
    
	@Resource(name="generateOrderService")
	private GenerateOrderService generateOrderService;
    @Resource(name = "userInfoService")
	private UserInfoService userInfoService;
    @Resource(name = "userFlowService")
	private UserFlowService userFlowService;
    @Resource(name = "userDownService")
    private UserDownService userDownService;



	@Resource(name = "sysuserService")
	private SysUserManager sysuserService;
    
    /**
	 * 获取User实体类
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
    public User getUser(String user_id) throws Exception{
    	User user = null;
    	if(StringUtil.isEmpty(user_id)){
			user = Jurisdiction.getSessionUser().getUser();
        }else{
        	//通过模板消息进入
			user = sysuserService.getByUserId(user_id);
        }
    	return user;
    }
    
    
    /**
	 * 获取微信用户的基本信息（包含用户昵称，图片，卡号等信息）
	 * @param pd（传USER_ID）
	 * @return
	 * @throws Exception
	 */
	public PageData getWechatUserInfo(PageData pd) throws Exception {
		
		String user_id = pd.getString("user_id");
		
        PageData pdUser = userService.findByUserId(user_id);
        if(StringUtil.isNotEmpty(pdUser)){
        	PageData pdWechatUser = wechatuserService.findByUserId(pdUser);
        	if(StringUtil.isNotEmpty(pdWechatUser)){
        		//微信的信息
        		pdUser.put("IMGURL", pdWechatUser.getString("IMGURL"));//头像图片
        		pdUser.put("NECK_NAME", URLDecoder.decode(pdWechatUser.getString("NECK_NAME"), "UTF-8"));//昵称
        		pdUser.put("OPEN_ID", pdWechatUser.getString("OPEN_ID"));//open_id
        	}
        	PageData pdBind = new PageData();
        	pdBind.put("userId", user_id);
            pdBind = bunduserService.findByUser(pdBind);
        	if(StringUtil.isNotEmpty(pdBind)){
        		//绑定的会员信息
        		pdUser.put("BUNDUSER_ID", pdBind.get("BUNDUSER_ID"));//绑定表的主键id
        		pdUser.put("REAL_NAME", pdBind.get("NAME"));//真实姓名
        		pdUser.put("STORE_ID", pdBind.get("STORE_ID"));//绑定的门店
        		pdUser.put("CARDED", pdBind.get("CARDED"));//会员卡号
        		pdUser.put("PHONE", pdBind.get("PHONE"));//绑定的号码
				pdUser.put("NAME", pdBind.get("NAME"));

				pdUser.put("NECK_NAME", pdBind.get("NAME"));

        		//绑定的门店的名称
        		PageData pdStore = new PageData();
                pdStore.put("STORE_ID", pdBind.get("STORE_ID"));
                pdStore = storeService.findById(pdStore);
                if(StringUtil.isNotEmpty(pdStore)){
                	pdUser.put("STORE_NAME", pdStore.get("STORE_NAME"));
                }
        		
        		//顺网提供的会员数据
        		pdUser.put("member_level", pdBind.get("MEMBER_LEVEL"));//会员级别
        		pdUser.put("usable_integral", pdBind.get("USABLE_INTEGRAL"));//可用积分
        		pdUser.put("overage", pdBind.get("OVERAGE"));//卡余额（包含奖励余额）
        		pdUser.put("reward", pdBind.get("REWARD"));//奖励余额
                
        	}
        }
		return pdUser;
	}
	
	
	/**
	 * 更新顺网的用户信息
	 * @param pdUser（传入STORE_ID，CARDED，BUNDUSER_ID）
	 * @return
	 * @throws Exception
	 */
	public PageData updateUserInfo(PageData pdUser) throws Exception {
		if (StringUtil.isNotEmpty(pdUser.getString("STORE_ID")) &&
				StringUtil.isNotEmpty(pdUser.getString("CARDED")) &&
				StringUtil.isNotEmpty(pdUser.getString("BUNDUSER_ID"))) {


			//调顺网接口获取用户数据
			Message2 m2 = userInfoService.getSWUser(pdUser.getString("STORE_ID"), pdUser.getString("CARDED"));

			if (m2.getErrcode() == 0) {
				SWUser swUser = (SWUser) m2.getData().get("SWUser");
				if (swUser != null) {
					if (swUser.getUser_name().equals(pdUser.getString("NAME"))) {
						PageData resPd = new PageData();
						resPd.put("BUNDUSER_ID", pdUser.getString("BUNDUSER_ID"));//用户绑定表主键
						resPd.put("MEMBER_LEVEL", swUser.getMember_level());//用户等级
						resPd.put("USABLE_INTEGRAL", swUser.getUsable_integral());//可用积分
						resPd.put("OVERAGE", swUser.getOverage());//本金
						resPd.put("REWARD", swUser.getReward());//奖励
						resPd.put("IS_SW", "1");//是否顺网对接数据1是0否

                        resPd.put("CARDED", swUser.getId_card());//证件号
                        resPd.put("CARDID", swUser.getCard_id());//卡号
						bunduserService.edit(resPd);

                        pdUser.put("CARDED", swUser.getId_card());//证件号
                        pdUser.put("CARDID", swUser.getCard_id());//卡号
						pdUser.put("member_level", swUser.getMember_level());//会员级别
						pdUser.put("usable_integral", swUser.getUsable_integral());//可用积分
						pdUser.put("overage", swUser.getOverage());//卡余额（包含奖励余额）
						pdUser.put("reward", swUser.getReward());//奖励余额
						pdUser.put("NECK_NAME", swUser.getUser_name());
					}
				}
			}
		}
		return pdUser;
	}
	
	/**
	 * 获取该网吧下所有城市，以及城市里的所有的区域，以及该区域内所有的门店
	 * @return
	 * @throws Exception
	 */
    public JSONObject getCityList(String internetId) throws Exception {

        PageData pdCity = new PageData();
        pdCity.put("INTERNET_ID", internetId);
        List<PageData> cityList = storeService.findCityByInternetId(pdCity);

        for (int i = 0; i < cityList.size(); i++) {
            String city = cityList.get(i).getString("CITY");
            PageData pdArea = new PageData();
            pdArea.put("CITY", city);
            pdArea.put("INTERNET_ID", internetId);
            List<PageData> areaList = storeService.findAreaByCity(pdArea);
            cityList.get(i).put("areaList", areaList);

            for (int j = 0; j < areaList.size(); j++) {
                String area = areaList.get(j).getString("COUNTY");
                PageData pd = new PageData();
                pd.put("CITY", city);
                pd.put("COUNTY", area);
                pd.put("INTERNET_ID", internetId);
                List<PageData> storeList = storeService.findStoreByArea(pd);
                areaList.get(j).put("storeList", storeList);
            }
        }
        JSONObject result = new JSONObject();
        result.put("cityList", cityList);
        
        String city = "";
		String area = "";
		if (cityList.size() > 0) {
			city = cityList.get(0).getString("CITY");
			List<PageData> areaList = (List<PageData>) cityList.get(0).get("areaList");
			area = areaList.get(0).getString("COUNTY");
		}
		result.put("city", city);
		result.put("area", area);
		
		
		
        return result;
    }

    
    /**
	 * 加载我的明细
	 * @return
	 * @throws Exception
	 */
    public JSONObject loadMyStateMents(PageData pd,PageData pdBind) throws Exception{
    	JSONObject result = new JSONObject();
    	
    	if(StringUtil.isNotEmpty(pdBind)){
            String card_id = StringUtil.isNotEmpty(pdBind.get("CARDID")) ? pdBind.getString("CARDID") : pdBind.getString("CARDED");

			String begin_time=Tools.dateAddDay2(new Date(),-183);
			String end_time=Tools.date2Str(new Date());
			JSONObject jsonUserFlow = new JSONObject();
			String STORE_ID="";
			//筛选时间
			String time = pd.getString("flow_time");
			String internet_id = pd.getString("internet_id");

			jsonUserFlow.put("card_id", card_id);
			jsonUserFlow.put("msg_from", "lanker");
			jsonUserFlow.put("filter_type", "0");
			jsonUserFlow.put("begin_time", begin_time);
			jsonUserFlow.put("end_time", end_time); 
			jsonUserFlow.put("flag", "sel");   //sel表示只查询；timer表示查询并保存流水
			jsonUserFlow.put("flow_time2", time);

	    	//获取网吧内的所有加v门店
	  		pd.put("internet_id", internet_id);
	  		pd.put("state", PublicManagerUtil.INTERNET_STORE_STATE_V1);//审核通过的
	  		List<PageData> storeVList = storeService.findStoreV(pd);
	  		
	  		List<JSONObject> flowList = new ArrayList<JSONObject>();//半年的流水信息
			double consume = 0;//一年的总消费
			double reward = 0;//一年的总奖励
			double recharge = 0;//一年的总充值
			for(int i=0; i<storeVList.size(); i++){
				STORE_ID = storeVList.get(i).getString("STORE_ID");

				jsonUserFlow.put("begin_time", begin_time);
				jsonUserFlow.put("end_time", end_time); 
				JSONObject userFlow = userFlowService.userFlowLocalToLanker(STORE_ID,jsonUserFlow);
				if(StringUtil.isNotEmpty(userFlow) && userFlow.getString("errcode").equals("0") && StringUtil.isNotEmpty(userFlow.getJSONArray("body"))){
					JSONArray body = userFlow.getJSONArray("body");
					for(int j=0;j<body.size();j++){
						JSONObject json = body.getJSONObject(j);
						if (json.get("pay_type").toString().equals("0")) {
							json.put("pay_desc", "卡余额支付");
						} else if (json.get("pay_type").toString().equals("1")) {
							json.put("pay_desc", "卡押金支付");
						} else if (json.get("pay_type").toString().equals("2")) {
							json.put("pay_desc", "揽客支付");
						} else if (json.get("pay_type").toString().equals("7")) {
							json.put("pay_desc", "现金支付");
						}
						json.put("store_name", storeVList.get(i).getString("STORE_NAME"));
						flowList.add(json);
					}
				}else{
					continue;
				}
			}
			//统计消费和奖励，充值
			for(JSONObject jj : flowList){
				if(Double.parseDouble(jj.get("amount")+"") < 0){
					consume += Double.parseDouble(jj.get("amount")+"");
				}else{
					recharge += Double.parseDouble(jj.get("amount")+"");
				}
				if(Double.parseDouble(jj.get("reward")+"") < 0){
					consume += Double.parseDouble(jj.get("reward")+"");
				}
				reward += Double.parseDouble(jj.get("reward")+"");
			}
			
			result.put("totalConsume", consume);//总消费
			result.put("totalReward", reward);//总奖励
			result.put("totalRechage", recharge);//总充值
			//查看失败订单 
			PageData pdsb=new PageData();
	    	pdsb.put("carded", pdBind.getString("CARDED"));
	    	pdsb.put("createtime", time);
	    	List<PageData> list = generateOrderService.listqb(pdsb);
	    	if(list!=null){
	    		System.out.println("=================================================================================="+pdsb);
		    	System.out.println("=================================================================================="+list);
		    	List<JSONObject> relist = new ArrayList<JSONObject>();
		    	for(PageData p:list){
		    		JSONObject json= new JSONObject();
		    		json.put("reward",p.get("reward_balance"));
                    json.put("memo","揽客充值");
		    		json.put("store_id",p.get("store_id"));
		    		json.put("flow_type",16);
		    		json.put("order_id",p.get("merOrderId"));
		    		json.put("card_id",p.get("carded"));
		    		json.put("amount",p.get("rincipal_balance"));
		    		json.put("flow_id",p.get("id"));
		    		json.put("flow_timestamp","0");
		    		json.put("pay_type",0);
		    		json.put("flow_desc","第三方");
		    		json.put("flow_time",p.get("createtime").toString());
		    		json.put("order_from","200010");
		    		json.put("pay_desc","b");
		        	json.put("store_name",p.get("store_name"));
		        	relist.add(json);
		    	}
		    	flowList.addAll(relist);
		    	result.put("flowList", flowList);
		    	System.out.println(flowList);
	    	}
    	}
    	return result;
    }
    
    /**
	 * 加载我的上网记录
	 * @param pd（internet_id，up_time筛选时间，card_id卡号）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMyUserOnLine(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		//根据时间筛选
		String up_time = pd.getString("up_time");
		if(up_time.equals("undefined-undefined")){
			up_time = "";
		}
		if(StringUtil.isNotEmpty(up_time)){
			pd.put("up_time", Tools.date2Str(Tools.str2Date(up_time, "yyyy-MM"), "yyyy-MM"));
		}else{
			pd.put("up_time", "");
		}
		
		Page page = new Page();
		page.setPd(pd);
		List<PageData> list = userDownService.list(page);
		int time = 0;
		double money = 0.00;
		for (int i = 0; i < list.size(); i++) {
			time += (int) list.get(i).get("up_duration");
			money += (double) list.get(i).get("consume_fee");
			
			String up_date = list.get(i).getString("up_time").split(" ")[0];
			if(up_date.equals(Tools.date2Str(Tools.str2Date2(Tools.dateAddDay(new Date(), -1)), "yyyy-MM-dd"))){
				//显示昨天
				String up_time2 = list.get(i).getString("up_time").split(" ")[1];
				list.get(i).put("up_time", "昨天 "+up_time2);
			}
			if(up_date.equals(Tools.date2Str(new Date(), "yyyy-MM-dd"))){
				//显示今天
				String up_time2 = list.get(i).getString("up_time").split(" ")[1];
				list.get(i).put("up_time", "今天 "+up_time2);
			}

			String down_date = list.get(i).getString("down_time").split(" ")[0];
			if(down_date.equals(Tools.date2Str(Tools.str2Date2(Tools.dateAddDay(new Date(), -1)), "yyyy-MM-dd"))){
				//显示昨天
				String down_time2 = list.get(i).getString("down_time").split(" ")[1];
				list.get(i).put("down_time", "昨天 "+down_time2);
			}
			if(down_date.equals(Tools.date2Str(new Date(), "yyyy-MM-dd"))){
				//显示今天
				String down_time2 = list.get(i).getString("down_time").split(" ")[1];
				list.get(i).put("down_time", "今天 "+down_time2);
			}
			
		}
		result.put("money", money);
		result.put("time", time);
		result.put("times", list.size());
		result.put("list", list);
		
		return result;
	}
	
	/**
	 * 加载我的抽奖
	 * @param pd（type状态）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loadMySpoil(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		Page page = new Page();
		
        page.setPd(pd);
        List<PageData> varList = memberlotteryService.wxlist(page);//暂时没有分页
        for (PageData pageData : varList) {
        	//计算中奖奖品的有效时间
            pd.put("LOTTERY_ID", pageData.get("LOTTERY_ID").toString());
            PageData pdg = lotteryService.findById(pd);
            Date zhongJiang = Tools.str2Date(pageData.get("LOTTERY_TIME").toString());
            int youXiao = Integer.parseInt(pdg.getString("EXPIRY_DATE"));
            String date = Tools.dateAddDay(zhongJiang, youXiao);
            pageData.put("youxiaoqi", date);

            //往pd中传入有效日期和现在时间
            if (pageData.containsKey("LOTTERY_TIME")) {
                String lotteryTime = pageData.getString("LOTTERY_TIME");
                String expiryDate = pageData.getString("EXPIRY_DATE");

                String availableTime = Tools.dateAddDay(Tools.str2Date(lotteryTime), Integer.parseInt(expiryDate)) + " " + lotteryTime.substring(11, 19);
                pageData.put("available_time", availableTime);
                pageData.put("now_date", Tools.date2Str(new Date()));
            }
        }
        JSONObject json = new JSONObject();
        json.put("subjectList", varList);
        
        result.put("data", json);
        
		return result;
	}
	
	
	/**
	 * 申请兑换奖品
	 * @param pd（mayId为奖表中的主键MEMBERLOTTERY_ID）
	 * @return 返回申请结果信息
	 * @throws Exception
	 */
	public JSONObject applySpoil(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		//获取中奖纪录，中奖时间
		PageData pdMemberLottery = new PageData();
		pdMemberLottery.put("MEMBERLOTTERY_ID", pd.getString("mayId"));
		pdMemberLottery = memberlotteryService.findById(pdMemberLottery);
		Date LOTTERY_TIME = Tools.str2Date(pdMemberLottery.get("LOTTERY_TIME").toString());
		
        //获取奖品表，奖品有效期
        PageData pdLottery = new PageData();
        pdLottery.put("LOTTERY_ID", pdMemberLottery.get("LOTTERY_ID"));
        pdLottery = lotteryService.findById(pdLottery);
        int EXPIRY_DATE = Integer.parseInt(pdLottery.getString("EXPIRY_DATE"));
        
        //奖品兑换的截止时间
        String available_time = Tools.dateAddDay(LOTTERY_TIME, EXPIRY_DATE) + " 23:59:59";
        
        Date date = new Date();
        //判断兑换时间是否过期，当前日期>截止日期
        if (!date.after(Tools.str2Date(available_time))) {
        	pdMemberLottery.put("STATE", "2");//申请兑换状态
            memberlotteryService.editSqdj(pdMemberLottery);
            result.put("message", "申请兑换成功");
        } else {
        	pdMemberLottery.put("STATE", "4");//已失效
            memberlotteryService.editSqdj(pdMemberLottery);
            result.put("message", "兑奖失败,超出有效期");
        }
		
		return result;
	}
	
	
	/**
	 * 加载我的卡券列表
	 * @param pd（STATE卡券状态，user_id用户id，internet_id用户所在网吧id）
	 * @return 返回我的卡券列表
	 * @throws Exception
	 */
	public JSONObject loadMyCard(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		String user_id = pd.getString("user_id");
		String internet_id = pd.getString("internet_id");
		pd.put("nowdate", Tools.date2Str(new Date()));
        
		//获取微信用户信息
        PageData wechatUser = new PageData();
        wechatUser.put("USER_ID", user_id);
        wechatUser = wechatuserService.findByUserId(wechatUser);
        
        //提取查询信息
        PageData pdCard = new PageData();
        pdCard.put("OPEN_ID", wechatUser.getString("OPEN_ID"));
        pdCard.put("INTENET_ID", internet_id);
        pdCard.put("STATE", pd.getString("STATE"));
        
        Page page = new Page();
        page.setPd(pdCard);
        List<PageData> varList = cancelService.wxlistPageCeshi(page);//暂时不要分页
        for (PageData pdInternet : varList) {
            String cardId = pdInternet.getString("CARD_ID");
            PageData pdStore = new PageData();
            pdStore.put("CARD_ID", cardId);
            List<PageData> sList = storeService.listByCardId(pdStore);
            //卡券核销所适用的门店
            String storeName = "";
            if (sList.size() > 0) {
                for (PageData pds : sList) {
                    storeName += pds.getString("STORE_NAME") + "，";
                }
                pdInternet.put("storeName", storeName.substring(0, storeName.length() - 1));
            }
            //加载卡券核销的有效期
            if (pdInternet.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")) {
                Date date = Tools.str2Date2(pdInternet.getString("END_TIMESTAMP"));
                Date nowdate = Tools.str2Date2(pd.getString("nowdate"));
                if (date.getTime() <= nowdate.getTime() && pdInternet.getString("STATE").equals("1")) {
					//到期
                    if (pd.getString("STATE").equals("1")) {
                        pdInternet.put("STATE", "shixiao");
                    } else if (pd.getString("STATE").equals("3")) {
                        pdInternet.put("STATE", "3");
                    }
                } else if (date.getTime() > nowdate.getTime() && pdInternet.getString("STATE").equals("1")) {
					//未到期
                    if (pd.getString("STATE").equals("3")) {
                        pdInternet.put("STATE", "shixiao");
                    } else if (pd.getString("STATE").equals("1")) {
                        pdInternet.put("STATE", "1");
                    }
                } else if(pdInternet.getString("STATE").equals("2")){
					//已核销
					pdInternet.put("STATE", "2");//已核销
				} else {
                    if (pd.getString("STATE").equals("3")) {
                        pdInternet.put("STATE", "shixiao");
                    } else if (pd.getString("STATE").equals("1")) {
                        pdInternet.put("STATE", "1");
                    }
                }
            } else if (pdInternet.getString("TYPE").equals("DATE_TYPE_FIX_TERM")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar ca = Calendar.getInstance();
                Date beginDate = new Date();
                ca.setTime(beginDate);
                int b = Integer.parseInt(pdInternet.getString("FIXED_TERM"));// 自领取后多少天内有效
                ca.set(Calendar.DATE, ca.get(Calendar.DATE) - b);//减去  自领取后多少天内有效
                Date endDate = format.parse(format.format(ca.getTime()));
                String A = format.format(endDate);//得到 : 减去  领取后多少天的 日期
                Long time = new Long(pdInternet.getString("CREAT_TIME"));
                String s = format.format(time * 1000);
                Date CREATE_TIME = Tools.str2Date(s);
                Date shengxiao = Tools.str2Date(A);
                String qizhi = Tools.dateAddDay(CREATE_TIME, b);
                String str = format.format(CREATE_TIME);
                pdInternet.put("BEGIN_TIMESTAMP", str);//起始日期
                pdInternet.put("END_TIMESTAMP", qizhi);//结束日期
                if (CREATE_TIME.getTime() <= shengxiao.getTime() && pdInternet.getString("STATE").equals("1")) {
					//到期
                    if (pd.getString("STATE").equals("1")) {
                        pdInternet.put("STATE", "shixiao");
                    } else if (pd.getString("STATE").equals("3")) {
                        pdInternet.put("STATE", "3");
                    }
                } else if (CREATE_TIME.getTime() > shengxiao.getTime() && pdInternet.getString("STATE").equals("1")) {
					//未到期
                    if (pd.getString("STATE").equals("3")) {
                        pdInternet.put("STATE", "shixiao");
                    } else if (pd.getString("STATE").equals("1")) {
                        pdInternet.put("STATE", "1");
                    }
                } else if(pdInternet.getString("STATE").equals("2")){
					pdInternet.put("STATE", "2");//已核销
				}
            }
        }

        JSONObject json = new JSONObject();
        json.put("subjectList", varList);
        
        result.put("data", json);
		return result;
	}
	
	
	/**
	 * 加载我的订单
	 * @param pd（typeId订单状态，user_id用户id，internet_id用户所在网吧id）
	 * @return 返回我的订单列表
	 * @throws Exception
	 */
	public JSONObject loadMyOrder(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		PageData pdOrder = new PageData();
		pdOrder.put("INTERNET_ID", pd.getString("internet_id"));
		pdOrder.put("USER_ID", pd.getString("user_id"));
		pdOrder.put("typeId", pd.getString("typeId"));
		
		Page page = new Page();
		page.setPd(pdOrder);
        List<PageData> varList = orderService.wxlist(page);//暂时不要分页
        for (PageData pageData : varList) {
            PageData pgp = auctionPicturesService.findByAuctionId(pageData.getString("Auction_ID"));
            pageData.put("PATH", pgp.get("PATH"));
            if (pageData.get("STATE").equals("1")) {
                pageData.put("zhuangtai", PICKUP);
            } else if (pageData.get("STATE").equals("2")) {
                pageData.put("zhuangtai", APPLYING);
            } else {
                pageData.put("zhuangtai", DELIVERY);
            }
        }
        JSONObject json = new JSONObject();
        json.put("subjectList", varList);
        
        result.put("data", json);
		return result;
	}
	
	
	/**
	 * 我的订单---申请提货
	 * @param pd（ORDER_ID订单主键id）
	 * @return 返回申请结果
	 * @throws Exception
	 */
	public JSONObject applyOrder(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		pd = orderService.findById(pd);
		if (StringUtil.isNotEmpty(pd)) {
            pd.put("STATE", "2"); // 1:未提货，2提货中；3提货成功
            orderService.edit(pd);
            result.put("result", PublicManagerUtil.SUCCESS);
        }else{
        	result.put("result", "false");
        }
		
		return result;
	}
	


	
	/**
	 * 我的投诉---加载我的投诉信息
	 * @param pd（user_id用户id，internet_id用户所在网吧id）
	 * @return 返回我的投诉信息
	 * @throws Exception
	 */
	public JSONObject loadMyLm(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
        pd.put("LM_USERID", pd.getString("user_id"));
        pd.put("internetId", pd.getString("internet_id"));
        List<LeaveMessage> varList = lmService.getlmByUid(pd);
        for (LeaveMessage leaveMessage : varList) {
        	//加载类型名称
        	PageData pdType = new PageData();
        	pdType.put("DICT_TYPE", "LM001");
        	pdType.put("DICT_CODE", leaveMessage.getLM_TYPEID());
        	pdType = dictentyService.findBySc(pdType);
        	if(StringUtil.isNotEmpty(pdType)){
        		leaveMessage.setLM_TYPENAME(pdType.getString("DICT_VALUE"));
        	}
        }
        result.put("varList", varList);
		return result;
	}


	@Override
	public JSONObject loadMyData(PageData pd, User user) throws Exception{
		JSONObject result = new JSONObject();

		if(user == null){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}

		//获取用户未查看的已回复投诉
		PageData pdData = new PageData();
		pdData.put("LM_USERID", user.getUSER_ID());
		pdData.put("LM_STATE", "1");//已回复
		pdData.put("IS_VIEW", "2");//未读
		List<PageData> lmList =  lmService.findLmByCondition(pdData);
		int lmSize = lmList.size();
		result.put("lmSize", lmSize);


		//获取用户未使用的卡券数量
		pd.put("STATE", "1");
		pd.put("user_id", user.getUSER_ID());
		pd.put("internet_id", user.getINTENET_ID());
		JSONObject cardResult = loadMyCard(pd);
		int cardSize = 0;
		if(StringUtil.isNotEmpty(cardResult)){
			List<JSONObject> varList = (List<JSONObject>) cardResult.getJSONObject("data").get("subjectList");
			//接下来去除state为失效的
			Iterator<JSONObject> it = varList.iterator();
			while(it.hasNext()){
				JSONObject x = it.next();
				if(x.getString("STATE").equals("shixiao")){
					it.remove();
				}
			}

			cardSize = varList.size();
		}

		result.put("cardSize", cardSize);
		result.put("result", "true");
		return result;
	}


    /**
     * 线程更新顺网用户数据
     */
    public static class updateSWUser implements Runnable {
        private String user_id;

        public updateSWUser(String user_id) {
            this.user_id = user_id;
        }
        @Override
        public void run() {
            try {
                MyMemberManager myMemberService = (MyMemberManager) ContextLoader.getCurrentWebApplicationContext().getBean("myMemberService");
                PageData pdUser = new PageData();
                pdUser.put("user_id", user_id);
                pdUser = myMemberService.getWechatUserInfo(pdUser);
                //判断是否绑定用户
                if (StringUtil.isNotEmpty(pdUser) && StringUtil.isNotEmpty(pdUser.getString("CARDED"))) {
                    //已绑定的用户实时更新顺网数据
                    myMemberService.updateUserInfo(pdUser);
                }
            }catch (Exception e){
                System.out.println("更新顺网用户数据出现异常");
            }
        }
    }











}
