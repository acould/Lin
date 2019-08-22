package com.lk.controller.system.memberMarke;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.service.system.sysuser.SysUserManager;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.Intenet;
import com.lk.entity.system.User;
import com.lk.service.intuser.indexMember.IndexMemberManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.system.memberMarke.MemberMarkeManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.storeUserTips.StoreUserTipsManager;
import com.lk.service.system.storeuser.StoreUserManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.service.weixin.sendRecord.SendRecordManager;
import com.lk.service.weixin.textmsg.TextmsgService;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import net.sf.json.JSONObject;

/**
 * 会员营销
 * 
 * @author lzh
 */
@Controller
@RequestMapping(value = "/memberMarke")
public class MemberMarkeController extends BaseController {
	String menuUrl = "memberMarke/list.do"; // 菜单地址(权限用)

	@Resource(name = "memberMarkeService")
	private MemberMarkeManager memberMarkeService;
	@Resource(name = "storeUserService")
	private StoreUserManager storeUserService;
	@Resource(name = "sendRecordService")
	private SendRecordManager sendRecordService;
	@Resource(name = "recordMaterialService")
	private RecordMaterialManager recordMaterialService;
	@Resource(name = "internetPicturesService")
	private InternetPicturesManager internetPicturesService;
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "textmsgService")
	private TextmsgService textmsgService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "storeUserTipsService")
	private StoreUserTipsManager storeUserTipsService;
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name = "indexMemberService")
	private IndexMemberManager indexMemberService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "internetMemberService")
	private InternetMemberManager internetMemberService;


	@Resource(name = "sysuserService")
	private SysUserManager sysuserService;

	// 回复内容类型--TYPE
	public static final String TYPE1 = "material"; // 图文消息
	public static final String TYPE2 = "image"; // 图片
	public static final String TYPE3 = "text"; // 文字
	public static final String TYPE4 = "card"; // 卡劵
	public static final String TYPE5 = "link"; // 链接
	public static final String TYPE6 = "notice"; // 消息通知
	public static final String TYPE7 = "DATE_TYPE_FIX_TIME_RANGE";
	public static final String TYPE8 = "DATE_TYPE_FIX_TERM";

	/**
	 * 列表
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "会员营销");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> storeList = null;
        User user = this.getUser();//得到用户

		String ROLE_ID = user.getROLE_ID();
		pd.put("internet_id", user.getINTENET_ID());
		if (ROLE_ID.equals(PublicManagerUtil.INTERNETROLEID)) {// 网吧老板
			// 去查看所有门店(没被禁用的)
			pd.put("storeIds", "");
			pd.put("role", "boss");
			page.setPd(pd);
			storeList = memberMarkeService.storeList(page);
		} else {
			// 通过user_id去找关联门店id和name的集合
			storeList = storeService.getStoreList1(user.getUSER_ID()); 
			String storeIds = "";
			for (int i = 0; i < storeList.size(); i++) {
				if (i == storeList.size() - 1) {
					storeIds = storeIds + storeList.get(i).get("store_id").toString();
				} else {
					storeIds = storeIds + storeList.get(i).get("store_id").toString() + ",";
				}
			}
			pd.put("storeIds", storeIds); // 店长相关门店id集合
			pd.put("role", "staff");
			page.setPd(pd);
		}

		// 去查询会员营销列表
		List<PageData> list = memberMarkeService.list(page, pd);
		if (StringUtil.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {// 遍历list去过去门店名称信息
				PageData pd1 = list.get(i);
				String store_ids[] = pd1.getString("store_id").split(",");
				List<PageData> storeNames = memberMarkeService.storeName(store_ids);// 获取门店名称
				pd1.put("sList", storeNames);
			}
		}
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.addObject("storeList", storeList);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		mv.setViewName("system/memberMarke/memberMarke_list");
		return mv;
	}

	/**
	 * 选择群发对象
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/toSelect")
	public ModelAndView toSelect(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "选择群发对象");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> list = null;
        User user = this.getUser();//得到用户

		String ROLE_ID = user.getROLE_ID();
		pd.put("internet_id", user.getINTENET_ID());
		if (ROLE_ID.equals(PublicManagerUtil.INTERNETROLEID)) {// 网吧老板
			// 去查看所有门店(没被禁用的)
			pd.put("storeIds", "");
			page.setPd(pd);
			list = memberMarkeService.storeList(page);// 去查看该用户所有门店(没被禁用的)
		} else {
			list = storeService.getStoreList1(user.getUSER_ID()); // 通过user_id去找关联门店id和name的集合
			page.setPd(pd);
		}
		mv.setViewName("system/memberMarke/memberMarke_select");
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 新增
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toAdd")
	public JSONObject toAdd() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "会员营销前往群发页面");
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
        User user = this.getUser();//得到用户

		String ROLE_ID = user.getROLE_ID();
		if (!ROLE_ID.equals(PublicManagerUtil.INTERNETROLEID)) {// 不是网吧老板
			if (pd.get("mass_object").toString().equals("fans")) {// 选择了全部粉丝
				json.put("result", PublicManagerUtil.FAIL);
				return json;
			}
		}
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("url", "/memberMarke/toGroup.do");
		return json;
	}

	/**
	 * 前往群发消息页面
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/toGroup")
	public ModelAndView toGroup() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "前往群发消息页面");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
        User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		if (pd.getString("mass_object").equals("member")) {// 为会员时,进行处理
			String store_ids[] = pd.getString("storeList").split(",");
			List<PageData> storeNames = memberMarkeService.storeName(store_ids);// 获取门店名称
			mv.addObject("list", storeNames);
		}
		pd.put("user_name", user.getNAME());
		pd.put("type", TYPE1);
		pd.put("state", "add");// 新增
		mv.addObject("pd", pd);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		mv.setViewName("system/memberMarke/memberMarke_edit");
		return mv;
	}

	/**
	 * 保存群发模板,群发消息
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toMarke")
	public JSONObject toMarke() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "保存群发模板,群发消息");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		PageData pd = this.getPageData();
		JSONObject json = new JSONObject();


		//如果勾选下机推送，检查时间段是否符合
		JSONObject result = memberMarkeService.checkTypeSend(pd);
		if(result.getString("result").equals("false")){
			return result;
		}

		// 保存群发模板并群发消息
		json = memberMarkeService.sendMessage(pd);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/updateSendType")
	public JSONObject updateSendType() throws Exception{

		//传入参数
		PageData pd = this.getPageData();

		//如果勾选下机推送，检查时间段是否符合
		JSONObject result = memberMarkeService.checkTypeSend(pd);
		if(result.getString("result").equals("false")){
			return result;
		}

		return memberMarkeService.updateSendType(pd);
	}

	

	/**
	 * 查询指定营销信息
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toView")
	public ModelAndView toView() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查询指定营销信息");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageData pd1 = new PageData();
		pd1 = memberMarkeService.view(pd);// 去查询指定营销信息
		if (pd1.getString("type").equals(TYPE6)) {// 为消息通知时
			pd1.put("type", "material");// 消息通知默认展示图文消息
		}
		if (pd1.getString("type").equals(TYPE1)) {
			mv.addObject("material", getObject(pd1));
		} else if (pd1.getString("type").equals(TYPE2)) {
			mv.addObject("image", getObject(pd1));
		} else if (pd1.getString("type").equals(TYPE3)) {
			mv.addObject("text", getObject(pd1));
		} else if (pd1.getString("type").equals(TYPE4)) {
			mv.addObject("card", getObject(pd1));
		} else if (pd1.getString("type").equals(TYPE5)) {
			mv.addObject("link", getObject(pd1));
		}
		if (pd1.getString("mass_object").equals("member")) {// 为会员时,进行处理
			List<PageData> list = new ArrayList<PageData>();
			String[] storeList = pd1.getString("store_id").split(",");
			for (int i = 0; i < storeList.length; i++) {
				PageData pd2 = new PageData();
				pd2.put("STORE_ID", storeList[i]);
				PageData pd3 = memberMarkeService.selectName(pd2);// 通过门店id获取门店名称
				pd2.put("STORE_NAME", pd3.getString("store_name"));
				list.add(pd2);
			}
			mv.addObject("list", list);
			// 同时去查询第一家门店的标签
			pd.put("store_id", storeList[0]);
			pd = storeUserTipsService.findById(pd); // 根据ID查询标签信息
			pd1.put("consume_high", pd.get("consume_high"));
			pd1.put("consume_low", pd.get("consume_low"));
			pd1.put("live_high", pd.get("live_high"));
			pd1.put("live_low", pd.get("live_low"));
			pd1.put("balance_high", pd.get("balance_high"));
			pd1.put("balance_low", pd.get("balance_low"));
		}
		pd1.put("state", "view");// 查看
		mv.setViewName("system/memberMarke/memberMarke_edit");
		mv.addObject("pd", pd1);
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 删除指定营销信息
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JSONObject delete() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Card");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		json = memberMarkeService.delete(pd); // 删除指定营销信息
		return json;
	}

	/**
	 * 批量删除指定营销信息
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@ResponseBody
	public JSONObject deleteAll() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "删除Card");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return null;
		} // 校验权限
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (null != DATA_IDS && !"".equals(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			json = memberMarkeService.deleteAll(ArrayDATA_IDS); // 批量删除指定营销信息
		}
		return json;
	}

	/**
	 * 查询指定门店的会员标签
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTips")
	@ResponseBody
	public JSONObject selectTips() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "查询指定门店的会员标签");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		json = memberMarkeService.selectTips(pd); // 查询指定营销信息
		return json;
	}

	/**
	 * 获取某个消息类型的对象内容
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getObject(PageData pd) throws Exception {
		if (pd.getString("type").equals(TYPE1)) {
			PageData pdMaterial = new PageData();
			pdMaterial.put("MEDIA_ID", pd.getString("object_id"));
			pdMaterial = sendRecordService.findByMediaId(pdMaterial);
			if (StringUtil.isNotEmpty(pdMaterial)) {
				List<PageData> mList = recordMaterialService.findBySendRecordId(pdMaterial);
				pdMaterial.put("mList", mList);
			}
			return pdMaterial;
		} else if (pd.getString("type").equals(TYPE2)) {
			PageData pdImg = new PageData();
			pdImg.put("MEDIA_ID", pd.getString("object_id"));
			pdImg = internetPicturesService.findByMediaId(pdImg);
			if (StringUtil.isNotEmpty(pdImg)) {
				return pdImg;
			}
		} else if (pd.getString("type").equals(TYPE3)) {
			PageData pdText = new PageData();
			pdText.put("TEXTMSG_ID", pd.getString("object_id"));
			pdText = textmsgService.findById(pdText);
			if (StringUtil.isNotEmpty(pdText)) {
				return pdText;
			}
		} else if (pd.getString("type").equals(TYPE4)) {
			PageData pdCard = new PageData();
			pdCard.put("CARD_ID", pd.getString("object_id"));
			pdCard = cardService.findById(pdCard);
			if (StringUtil.isNotEmpty(pdCard)) {
				pdCard.put("SUB_TITLE", pdCard.getString("SUB_TITLE"));
				pdCard.put("DESCRIPTION", "使用说明：" + pdCard.getString("DESCRIPTION"));
				if (pdCard.getString("TYPE").equals(TYPE7)) {
					pdCard.put("AVAILABLE_TIME",
							pdCard.getString("BEGIN_TIMESTAMP") + "至" + pdCard.getString("END_TIMESTAMP"));
				} else if (pdCard.getString("TYPE").equals(TYPE8)) {
					String name = "";
					if (pdCard.getString("FIXED_BEGIN_TERM").equals("0")) {
						name = "当天";
					} else {
						name = pdCard.getString("FIXED_BEGIN_TERM") + "天";
					}
					pdCard.put("AVAILABLE_TIME", "有效期：自领取后" + name + "生效，" + pdCard.getString("FIXED_TERM") + "天内有效");
				}
				PageData pdStore = new PageData();
				pdStore.put("CARD_ID", pdCard.getString("CARD_ID"));
				List<PageData> sList = storeService.listByCardId(pdStore);
				String storeName = "";
				if (sList.size() > 0) {
					for (PageData pds : sList) {
						storeName += pds.getString("STORE_NAME") + "，";
					}
					pdCard.put("STORE_NAME", "使用门店：" + storeName.substring(0, storeName.length() - 1));
				}
				return pdCard;
			}
		} else if (pd.getString("type").equals(TYPE5)) {
			PageData pdLink = new PageData();
			pdLink.put("id", pd.getString("object_id"));
			pdLink = memberMarkeService.findLink(pdLink);
			if (StringUtil.isNotEmpty(pdLink)) {
				return pdLink;
			}
		}
		return null;
	}

	/**
	 * 加载卡券列表
	 * 
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cardList")
	@ResponseBody
	public ModelAndView cardList(HttpServletRequest request, Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "获取符合要求的卡券");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
        User user = this.getUser();//得到用户

		// 为粉丝时,只能用通用券(这里的通用---指适用所有门店)
		if (pd.getString("type").equals("fans")) {
			pd.put("IS_ALL", "1");// 1--适用所有门店
		} // 为会员时,要求:门店加v
		if (pd.getString("type").equals("member")) {
			pd.put("state", "1");// 1--加V状态
		}
		if (StringUtil.isNotEmpty(pd.getString("store_id"))) {
			String store_ids[] = pd.getString("store_id").split(",");
			List<String> list = new ArrayList<>();
			for (int i = 0; i < store_ids.length; i++) {
				list.add(i, store_ids[i]);
			}
			pd.put("list", Joiner.on("','").join(list)); // 门店id集合
		}
		pd.put("internet_id", user.getINTENET_ID());
		//获取当前时间time1和time2
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String time1 = sdf1.format(new Date());
		pd.put("time1", time1);
		pd.put("NEW1", "NEW1");
		page.setPd(pd);
		// 列出Card列表(条件:1.FLQ--福利券  2,卡券未失效  3.门店没有禁用  4.门店加v(选择对象为会员时))
		List<PageData> cardList = memberMarkeService.selectCard(page);
		if (StringUtil.isNotEmpty(cardList)) {
			// 处理卡券展示数据
			for (PageData pdInternet : cardList) {

				String cardId = pdInternet.getString("CARD_ID");
				PageData pdStore = new PageData();
				pdStore.put("CARD_ID", cardId);
				if (pd.getString("type").equals("member")) {
					pdStore.put("state", "1");// 1--加V状态
				}
				List<PageData> sList = memberMarkeService.listByCardId(pdStore);// 通过卡券id获取门店信息
				String storeName = "";
				if (sList.size() > 0) {
					for (PageData pds : sList) {
						storeName += pds.getString("STORE_NAME") + "，";
					}
					pdInternet.put("STORE_NAME", storeName.substring(0, storeName.length() - 1));
				}
				if (pdInternet.getString("TYPE").equals(TYPE7)) {
					pdInternet.put("AVAILABLE_TIME",
							pdInternet.getString("BEGIN_TIMESTAMP") + "至" + pdInternet.getString("END_TIMESTAMP"));
				} else if (pdInternet.getString("TYPE").equals(TYPE8)) {
					String name = "";
					if (pdInternet.getString("FIXED_BEGIN_TERM").equals("0")) {
						name = "当天";
					} else {
						name = pdInternet.getString("FIXED_BEGIN_TERM") + "天";
					}
					pdInternet.put("AVAILABLE_TIME",
							"自领取后" + name + "生效，" + pdInternet.getString("FIXED_TERM") + "天内有效");
				}
			}
		}
		mv.setViewName("weixin/wxMenu/card");
		mv.addObject("cardList", cardList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 通过卡券id获取信息后前往群发页面
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/cardView")
	public ModelAndView cardView() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "通过卡券id获取信息后前往群发页面");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		} // 校验权限
        User user = this.getUser();//得到用户

		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 为会员时,要求:门店加v
		if (pd.getString("mass_object").equals("member")) {
			pd.put("state", "1");// 1--加V状态
		}
		// 先通过卡券id获取卡券信息和适用门店的信息
		List<PageData> cardList = memberMarkeService.cardView(pd); // 列出Card列表(获取到门店id和门店名称,门店没被禁用)
		String storeList = "";
		for (int i = 0; i < cardList.size(); i++) {
			if (i != cardList.size() - 1) {
				storeList = storeList + cardList.get(i).getString("STORE_ID") + ",";
			} else {
				storeList = storeList + cardList.get(i).getString("STORE_ID");
			}
		}
		pd.put("storeList", storeList);

		if (StringUtil.isNotEmpty(pd.getString("store_id"))) {// 如果是店长
			String store_ids[] = pd.getString("store_id").split(",");
			List<String> list = new ArrayList<>();
			for (int i = 0; i < store_ids.length; i++) {
				list.add(i, store_ids[i]);
			}
			pd.put("list", Joiner.on("','").join(list)); // 门店id集合
			List<PageData> list1 = memberMarkeService.findByStoreId(pd);// 通过门店id获取门店名称(加V的)
			if (StringUtil.isNotEmpty(list1)) {
				String storeList1 = "";
				for (int i = 0; i < list1.size(); i++) {
					if (i != list1.size() - 1) {
						storeList1 = storeList1 + list1.get(i).getString("STORE_ID") + ",";
					} else {
						storeList1 = storeList1 + list1.get(i).getString("STORE_ID");
					}
				}
			}
			pd.put("storeList", pd.getString("store_id"));
			pd.put("mass_object", "member");// 对象默认为会员
			mv.addObject("list", list1);
		} else {// 网吧老板
			if (pd.getString("mass_object").equals("member")) {// 为会员时,进行处理
				mv.addObject("list", cardList);
			}
		}
		pd.put("type", TYPE4);
		pd.put("object_id", pd.getString("cardId"));
		pd.put("user_name", user.getNAME());
		pd.put("state", "add");// 新增
		mv.addObject("pd", pd);
		mv.setViewName("system/memberMarke/memberMarke_edit");
		mv.addObject("card", getObject(pd));
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 查看详情跳转页面
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDetails")
	public ModelAndView toDetails() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		//更新用户信息
		User user = sysuserService.getUserByOpenId(pd.getString("open_id"));

		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}
		PageData record = memberMarkeService.findRecord(pd);// 通过记录表id去查询查看详情状态(view_state)
		String view_state = record.get("view_state").toString();// 查看详情状态(0--未查看,1--已查看)


		if (pd.getString("type").equals(TYPE1)) {// 图文
			PageData pdMaterial = new PageData();
			pdMaterial.put("MEDIA_ID", pd.getString("object_id"));
			pdMaterial = sendRecordService.findByMediaId(pdMaterial);// 获取SENDRECORD_ID
			if (StringUtil.isNotEmpty(pdMaterial)) {
				List<PageData> mList = recordMaterialService.findBySendRecordId(pdMaterial);// 获取图文信息
				if (StringUtil.isNotEmpty(mList)) {
					mv.addObject("title", mList.get(0).getString("TITLE"));
					mv.addObject("time", Tools.date2Str(new Date(), "yyyy-MM-dd"));
					mv.addObject("author", mList.get(0).getString("CREATE_USER"));
					mv.addObject("content", mList.get(0).getString("CONTENT"));
					mv.setViewName("weixin/sendRecord/phonePreview");
				}
			}
		} else if (pd.getString("type").equals(TYPE2)) {// 图片
			PageData pd1 = memberMarkeService.findContent(pd);// 通过mass_id获取信息
			JSONObject pd2 = JSONObject.fromObject(pd1.getString("mass_context"));
			pd.put("title", pd2.get("title"));
			pd.put("activtyType", pd2.get("activtyType"));
			pd.put("activtyContent", pd2.get("activtyContent"));
			pd.put("createtime", pd2.get("createtime"));
			pd.put("remark", pd2.get("remark"));
			pd.put("path", getObject(pd).getString("PATH"));
			mv.addObject("pd", pd);
			mv.setViewName("system/memberMarke/memberMarke_textImg");
		} else if (pd.getString("type").equals(TYPE3)) {// 文字
			PageData pd1 = memberMarkeService.findContent(pd);// 通过mass_id获取信息
			JSONObject pd2 = JSONObject.fromObject(pd1.getString("mass_context"));
			pd.put("title", pd2.get("title"));
			pd.put("activtyType", pd2.get("activtyType"));
			pd.put("activtyContent", pd2.get("activtyContent"));
			pd.put("createtime", pd2.get("createtime"));
			pd.put("remark", pd2.get("remark"));
			pd.put("content", getObject(pd).getString("CONTENT"));
			mv.addObject("pd", pd);
			mv.setViewName("system/memberMarke/memberMarke_textImg");
		}
		if (view_state.equals("0")) {// 如果是第一次查看,则修改状态(0--1)
			memberMarkeService.editRecord(pd);// 通过记录表id去修改查看详情状态(view_state)
		}
		return mv;
	}

	/**
	 * 群发卡券,点击详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/receiveCard")
	public ModelAndView receiveCard() throws Exception {
		ModelAndView mv = this.getModelAndView();
		ModelAndView mv1 = this.getModelAndView();
		PageData pd = this.getPageData();// 传入object_id和open_id
		System.out.println("唯一id标识为:***************"+pd.getString("id"));

		//更新用户信息
		User user = sysuserService.getUserByOpenId(pd.getString("open_id"));

		// 判断用户是否为空
		if (StringUtil.isEmpty(user)) {
			mv.setViewName("userNull");
			return mv;
		}

		PageData pd1 = cardService.findId(pd.getString("id"));
		if(StringUtil.isNotEmpty(pd1)) {//冲送券
			//通过唯一id标识去查询该卡券是否已领取
			if(pd1.get("card_state").toString().equals("0")) {//未领取
				pd.put("internet_id", user.getINTENET_ID());
                String url = this.getUrl();
				pd.put("url", url);
				// 获取js-sdk 卡券接口的相关信息
				JSONObject wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
				mv.addObject("wx", wx);
				mv.setViewName("intenetmumber/benefit");
			}else {//已领取过,不让其再领取
				mv.addObject("state", "RUSH");
				mv.setViewName("intenetmumber/dissatisfy");
				return mv;
			}
		}else {
			pd.put("user_id", user.getUSER_ID());
			pd.put("card_id", pd.getString("object_id"));
			pd.put("CARD_ID", pd.getString("object_id"));
			//获取当前时间time1和time2
			pd.put("time", Tools.date2Str(new Date()));

			//通过卡券id和user_id去判断是否可领取
			JSONObject jsonState = cardService.getCard(pd);
			if(jsonState.getString("result").equals("flase")) {
				mv.addObject("state", jsonState.getString("state"));
				if(jsonState.getString("state").equals("TERM_2")) {
					mv.addObject("cardSum_type", jsonState.getString("cardSum_type"));
					mv.addObject("cardSum_time", jsonState.getString("cardSum_time"));
				}
				mv.setViewName("intenetmumber/dissatisfy");
				return mv;
			}
			//这里去加载卡券列表
			User user1 = internetMemberService.getUser(pd);
			Intenet org = this.intenetService.getIntenetById(user1.getINTENET_ID());
			mv.addObject("intenetName", org.getINTENET_NAME());
			// 判断用户是否绑定
			pd.put("user_id", user.getUSER_ID());
			pd.put("internet_id", user.getINTENET_ID());
			pd.put("backurl", "/indexMember/benefits.do");
			mv1 = indexMemberService.judgeBind(pd);
			if (mv1.getModel().get("result").toString().equals("false")) {
				// 门店被禁用时，去换绑页面
				if (StringUtil.isNotEmpty(mv1.getModel().get("store_state"))
						&& mv1.getModel().get("store_state").toString().equals("1")) {
					return new ModelAndView(mv1.getModel().get("url").toString());
				}
				return mv1;
			}

			JSONObject json = memberMarkeService.judgeUser(pd);// 这里通过user_id和卡券id去判断用户是否可以领取该卡券
			if (json.getString("result").equals("true")) {// 满足领取条件
                pd.put("internet_id", user.getINTENET_ID());
                String url = this.getUrl();

                pd.put("url", url);
                //获取js-sdk 卡券接口的相关信息
                JSONObject wx = memberMarkeService.getCard_Ext(pd);// 获取card_ext
                mv.addObject("wx", wx);
                mv.setViewName("intenetmumber/benefit");
			}
		}
		return mv;
	}
	
	@RequestMapping(value = "/dissatisfy")
	public ModelAndView dissatisfy() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("intenetmumber/dissatisfy");
		return mv;
	}
}
