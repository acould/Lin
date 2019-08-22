package com.lk.controller.system.wechatuser;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.entity.LayMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.userDown.UserDownService;
import com.lk.service.billiCenter.userFlow.UserFlowService;
import com.lk.service.system.bunduser.BundUserManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.AppUtil;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.ObjectExcelView;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.response.WechatUser;
import com.lk.wechat.util.WechatMessageUtil;
import com.lk.wechat.util.WeixinUtil;

/**
 * 说明：微信用户 创建人：洪智鹏 创建时间：2016-10-31
 */
@Controller
@RequestMapping(value = "/wechatuser")
public class WeChatUserController extends BaseController {

	String menuUrl = "wechatuser/list.do"; // 菜单地址(权限用)
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "bunduserService")
	private BundUserManager bunduserService;
	@Resource(name = "userDownService")
	private UserDownService userDownService;
	@Resource(name = "userFlowService")
	private UserFlowService userFlowService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;

	/**
	 * 保存
	 * 
	 * @param
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "新增WeChatUser");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("WECHAT_ID", this.get32UUID()); // 主键
		pd.put("USER_ID", ""); // 用户id
		pd.put("OPEN_ID", ""); // 公众号唯一id
		pd.put("INTENET_ID", ""); // 网吧id
		pd.put("NECK_NAME", ""); // 昵称
		pd.put("SEX", ""); // 性别
		pd.put("PROVICNE", ""); // 省
		pd.put("CITY", ""); // 城市
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 关注时间
		pd.put("STATE", ""); // 状态
		wechatuserService.save(pd);
		mv.addObject("msg", PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 *
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除WeChatUser");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		wechatuserService.delete(pd);
		out.write(PublicManagerUtil.SUCCESS);
		out.close();
	}

	/**
	 * 修改
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "修改WeChatUser");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		wechatuserService.edit(pd);
		mv.addObject("msg", PublicManagerUtil.SUCCESS);
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表 通过条件/关键词和网吧id/角色id去查询会员信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "列表WeChatUser");
		ModelAndView mv = this.getModelAndView();

		User user = this.getUser();//得到用户

		PageData pdStore = new PageData();
		pdStore.put("internetId", user.getINTENET_ID());
		List<PageData> sList = storeService.listByIntenet(pdStore);// 通过网吧id查询现有门店(没有被禁用的)

		mv.setViewName("system/wechatuser/wechat_user_list");
		mv.addObject("sList", sList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}


    /**
     * 加载信息
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getList")
    public LayMessage getList(Page page) throws Exception{


        //传入参数
        PageData pd = this.getPageData();
        return wechatuserService.getWechatUserList(pd, page);
    }



	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/wechatuser/wechatuser_edit");
		mv.addObject("msg", PublicManagerUtil.SAVE);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = wechatuserService.findById(pd); // 根据ID读取
		mv.setViewName("system/wechatuser/wechatuser_edit");
		mv.addObject("msg", PublicManagerUtil.EDIT);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去查看页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goView")
	public ModelAndView goView(HttpServletRequest request) throws Exception {
		User user = this.getUser();//得到用户

		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("INTENET_ID", user.getINTENET_ID());
		PageData pdStore = storeService.findByVId(pd);// 获取绑定的门店信息

		if (StringUtil.isEmpty(pdStore) || (Integer.parseInt(pdStore.get("v_state") + "") != 1)) {// 判断其门店是否加v
			PageData pdInternet = intenetService.findById(pd); // 获取网吧名称
			pd.put("INTENET_NAME", pdInternet.getString("INTENET_NAME"));
			pd.put("STORE_NAME", pdStore.getString("STORE_NAME"));
			mv.setViewName("system/wechatuser/tips");
			mv.addObject("pd", pd);
			return mv;
		}

		PageData pdUser = wechatuserService.findById(pd);// 获取微信用户
		pd.put("NECK_NAME", URLDecoder.decode(pdUser.getString("NECK_NAME"), "utf-8"));
		pd.put("USER_ID", pdUser.getString("USER_ID"));
		pd.put("userId", pdUser.getString("USER_ID"));

		PageData pdBind = bunduserService.findByUser(pd);// 获取绑定的微信用户信息

		pdStore.put("INTENET_ID", pdStore.getString("INTERNET_ID"));
		PageData pdInternet = intenetService.findById(pdStore); // 获取网吧名称
		pd.put("INTENET_NAME", pdInternet.getString("INTENET_NAME"));

		mv.setViewName("system/wechatuser/wechatuser_view");
		mv.addObject("pd", pd);
		mv.addObject("user", pdBind);
		mv.addObject("live", request.getParameter("live"));
		mv.addObject("consume", request.getParameter("consume"));
		mv.addObject("balance", request.getParameter("balance"));
		return mv;
	}

	/**
	 * 获取流水列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userFlow")
	@ResponseBody
	public JSONObject userFlow() throws Exception {
		JSONObject result = new JSONObject();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		String userId = pd.getString("USER_ID");
		// 筛选流水类型
		String type = pd.getString("flow_type");
		// 筛选时间
		String time = pd.getString("flow_time");
		// 筛选订单号
		String order_id = pd.getString("order_id");

		PageData pdBind = new PageData();
		pdBind.put("userId", userId);
		pdBind = bunduserService.findByUser(pdBind); // 获取该会员在该网吧的所有流水信息（半年）
		if (StringUtil.isNotEmpty(pdBind)) {
			JSONObject jsonuserFlow = new JSONObject();
			String STORE_ID = "";
			jsonuserFlow.put("filter_type", "0"); // 0表示全部类型的流水
            String card_id = StringUtil.isNotEmpty(pdBind.get("CARDID")) ? pdBind.getString("CARDID") : pdBind.getString("CARDED");
			jsonuserFlow.put("card_id", card_id);
			jsonuserFlow.put("msg_from", "lanker");
			jsonuserFlow.put("flag", "sel"); // sel表示只查询；timer表示查询并保存流水
			jsonuserFlow.put("filter_type2", type);
			jsonuserFlow.put("flow_time2", time);
			jsonuserFlow.put("order_id2", order_id);

			pd.put("internet_id", user.getINTENET_ID());
			pd.put("state", PublicManagerUtil.INTERNET_STORE_STATE_V1); // 审核通过的
			List<PageData> storeVList = storeService.findStoreV(pd); // 获取网吧内的所有加v门店

			List<JSONObject> flowList = new ArrayList<JSONObject>();// 半年的流水信息
			double consume = 0;// 半年的总消费
			double reward = 0;// 半年的总奖励
			for (int i = 0; i < storeVList.size(); i++) {
				STORE_ID = storeVList.get(i).getString("STORE_ID");

                jsonuserFlow.put("begin_time", Tools.dateAddDay2(new Date(), -183));
                jsonuserFlow.put("end_time", Tools.date2Str(new Date()));
				JSONObject userFlow = userFlowService.userFlowLocalToLanker(STORE_ID, jsonuserFlow);

				if (StringUtil.isNotEmpty(userFlow) && userFlow.getString("errcode").equals("0")
						&& StringUtil.isNotEmpty(userFlow.getJSONArray("body"))) {
					JSONArray body = userFlow.getJSONArray("body");
					for (int j = 0; j < body.size(); j++) {
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
				} else {
					continue;
				}
			}

			// 统计消费和奖励
			for (JSONObject jj : flowList) {
				if (Double.parseDouble(jj.get("amount") + "") < 0) {
					consume += Double.parseDouble(jj.get("amount") + "");
				}
				if (Double.parseDouble(jj.get("reward") + "") < 0) {
					consume += Double.parseDouble(jj.get("reward") + "");
				}
				reward += Double.parseDouble(jj.get("reward") + "");
			}

			result.put("amount", consume);
			result.put("reward", reward);
			result.put("flowList", flowList);
			result.put("data", flowList);
			result.put("count", flowList.size());
		}

		result.put("msg", "查询成功");
		result.put("code", "0");
		return result;
	}

	/**
	 * 获取上网信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userOnline")
	@ResponseBody
	public JSONObject userOnline() throws Exception {
		JSONObject result = new JSONObject();
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();// card_id,up_time
		pd.put("internet_id", user.getINTENET_ID());

		String up_time = pd.getString("up_time");
		if (up_time.equals("undefined-undefined")) {
			up_time = "";
		}
		if (StringUtil.isNotEmpty(up_time)) {
			pd.put("up_time", Tools.date2Str(Tools.str2Date(up_time, "yyyy-MM"), "yyyy-MM"));
		} else {
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

		}
		result.put("money", money);
		result.put("time", time);
		result.put("times", list.size());
		result.put("list", list);
		result.put("data", list);

		result.put("count", list.size());

		result.put("msg", "查询成功");
		result.put("code", "0");
		return result;
	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}


    private static Map<String, String> includeMap = new HashMap<>();

	@ResponseBody
	@RequestMapping(value = "/includeOld")
	public JSONObject includeOld() throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "wechatUser -- 导入老会员-- includeOld");
		JSONObject result = new JSONObject();

		User user = this.getUser();//得到用户

		// 获取公众号凭证
		String accessToken = autoReplyService.getAuthToken(user.getINTENET_ID());

		JSONArray array = WechatMessageUtil.getOpenids(accessToken);

        String flag = includeMap.get(user.getINTENET_ID());
        if(StringUtil.isEmpty(flag)){
            //可导入
            includeMap.put(user.getINTENET_ID(), "1");//1表示正在导入
            GroupInclude thread = new GroupInclude(array, accessToken);
            new Thread(thread).start();
        }

		result.put("result", "true");
		return result;
	}

	public class GroupInclude implements Runnable {
		private JSONArray array;
		private String accessToken;
		public GroupInclude(JSONArray array, String accessToken) {
			this.array = array;
			this.accessToken = accessToken;
		}
		public void run() {
			try {
                User user = Jurisdiction.getSessionUser().getUser();//得到用户

				for (int i = 0; i < array.size(); i++) {
					String openId = array.getString(i);
					PageData pdUser = new PageData();
					pdUser.put("OPEN_ID", openId);
					pdUser = wechatuserService.findByOpenId(pdUser);
					if (pdUser == null) {// 数据库中没有该关注的人时，往数据库中添加数据
						WechatUser person = WeixinUtil.getUserInfo(accessToken, openId);
						String userName = WeixinUtil.generateSequenceNo();
						pdUser = new PageData();
						pdUser.put("USER_ID", StringUtil.get32UUID());
						pdUser.put("LAST_LOGIN", ""); // 最后登录时间
						pdUser.put("IP", ""); // IP
						pdUser.put("STATUS", "0"); // 状态
						pdUser.put("SKIN", "default");
						pdUser.put("RIGHTS", "");
						pdUser.put("PASSWORD", new SimpleHash("SHA-1", "123456").toString()); // 密码加密
						pdUser.put("USERNAME", userName);
						pdUser.put("ANGET_ID", "");
						pdUser.put("INTENET_ID", user.getINTENET_ID()); // 更改为appId
						pdUser.put("EMAIL", "");
						pdUser.put("NAME", URLEncoder.encode(person.getNECK_NAME(), "utf-8"));
						pdUser.put("ROLE_ID", PublicManagerUtil.PROMOTERROLEID);// 推广员
						pdUser.put("INTEGRAL", 0);
						userService.saveU(pdUser);

						PageData wechatUser = new PageData();
						wechatUser.put("WECHAT_ID", StringUtil.get32UUID()); // 主键
						wechatUser.put("USER_ID", pdUser.get("USER_ID")); // 用户id
						wechatUser.put("OPEN_ID", person.getOPEN_ID()); // 公众号唯一id
						wechatUser.put("INTENET_ID", user.getINTENET_ID()); // 网吧id
						wechatUser.put("NECK_NAME", URLEncoder.encode(person.getNECK_NAME(), "utf-8")); // 昵称
						wechatUser.put("SEX", person.getSEX()); // 性别
						wechatUser.put("PROVICNE", person.getPROVICNE()); // 省
						wechatUser.put("CITY", person.getCITY()); // 城市
						wechatUser.put("CREATE_TIME", Tools.formatTime(person.getCREATE_TIME())); // 关注时间
						wechatUser.put("STATE", "1"); // 状态1关注 0取消关注
						wechatUser.put("IMGURL", person.getIMGURL());
						wechatuserService.save(wechatUser);
					}
				}
                includeMap.remove(user.getINTENET_ID());//移除标记
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
