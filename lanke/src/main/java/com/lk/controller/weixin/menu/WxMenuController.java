package com.lk.controller.weixin.menu;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.util.*;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.card.CardManager;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.weixin.menu.WxMenuService;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.service.weixin.sendRecord.SendRecordManager;

/**
 * 说明：自定义(微信)菜单
 */
@Controller
@RequestMapping(value = "/wxMenu")
public class WxMenuController extends BaseController {
	// TYPE使用时间的类型
	public static final String TYPE1 = "DATE_TYPE_FIX_TIME_RANGE";
	public static final String TYPE2 = "DATE_TYPE_FIX_TERM";
	public static final String TYPE3 = "click";
	public static final String TYPE4 = "media_id";
	// DETAIL
	public static final String DETAIL1 = "WENZI"; // 文字
	public static final String DETAIL2 = "KAQUAN"; // 卡劵
	public static final String DETAIL3 = "TUPIAN"; // 图片
	public static final String DETAIL4 = "TUWEN"; // 图文


	String menuUrl = "wxMenu/list.do"; // 菜单地址(权限用)
	@Resource(name = "wxMenuService")
	private WxMenuService menuService;
	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	@Resource(name = "sendRecordService")
	private SendRecordManager sendRecordService;
	@Resource(name = "recordMaterialService")
	private RecordMaterialManager recordMaterialService;
	@Resource(name = "internetPicturesService")
	private InternetPicturesManager internetPicturesService;
	@Resource(name = "internetmaterialService")
	private InternetMaterialManager internetmaterialService;
	@Resource(name = "cardService")
	private CardManager cardService;
	@Resource(name = "storeService")
	private StoreManager storeService;

	/**
	 * 保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(HttpServletRequest request) throws Exception {
		System.out.println("自定义菜单-------------保存save");
		User user = this.getUser();//得到用户

		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);

		pdInternet.put("APP_ID", pdInternet.getString("WECHAT_ID"));
		pdInternet.put("INTERNET_ID", pdInternet.getString("INTENET_ID"));
		JSONObject json = menuService.saveMenu(pdInternet, request);

		return json;
	}

	/**
	 * 发布菜单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/issue")
	@ResponseBody
	public Object issue(HttpServletRequest request) throws Exception {
		System.out.println("微信-----------------发布菜单！！！");

		User user = this.getUser();//得到用户
		// 查找该网吧下的所有公众号
		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);
		pdInternet.put("INTERNET_ID", user.getINTENET_ID());
		pdInternet.put("APP_ID", pdInternet.getString("WECHAT_ID"));

		JSONObject json = menuService.issue(pdInternet);

		return json;
	}

	/**
	 * 删除菜单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteByOrder")
	@ResponseBody
	public Object deleteByOrder(HttpServletRequest request) throws Exception {
		System.out.println("自定义菜单 ------------- 删除菜单deleteByOrder");
		PageData pd = new PageData();
		User user = this.getUser();//得到用户
		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);
		String appId = pdInternet.getString("WECHAT_ID");
		pd.put("FIRST", request.getParameter("LIE"));
		pd.put("SECOND", request.getParameter("HANG"));
		pd.put("APP_ID", appId);
		JSONObject json = menuService.deleteMenuByOrder(pd);
		return json;
	}

	/**
	 * 菜单列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		System.out.println("微信-----------------自定义菜单列表");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		// 查找该网吧下的所有公众号
		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);
		String appId = pdInternet.getString("WECHAT_ID");
		// 首次检查菜单数据有没有保存（以前有些网吧通过都是手动设置的，没保存进数据库，这时应该先检查下有没有保存到数据库，没有的话保存一遍）
		PageData pdMenu = new PageData();
		pdMenu.put("INTERNET_ID", user.getINTENET_ID());
		pdMenu.put("APP_ID", appId);
		List<PageData> menuList = menuService.findByAppId(pdMenu);
		if (menuList.size() <= 0) {
			menuService.defaultMenu(pdMenu);
		}
		// 查找第一个一级菜单的内容
		PageData pd = new PageData();
		pd.put("FIRST", "0");
		pd.put("APP_ID", appId);
		pd = menuService.findByOrder(pd);
		if (pd.getString("PARENT_ID").equals("0")) {
			pd.put("SELECT_MENU", "0");
		} else {
			pd.put("SELECT_MENU", "0_0");
		}
		// 查找一级菜单列表
		List<PageData> fuList = menuService.findByAppId(pd);

		// 查找第一个和第三个的二级菜单列表
		PageData pdSon = new PageData();
		pdSon.put("FIRST", 0);
		pdSon.put("APP_ID", appId);
		List<PageData> fList = menuService.findSonByLie(pdSon);
		pdSon.put("FIRST", 2);
		List<PageData> tList = menuService.findSonByLie(pdSon);

		mv.setViewName("weixin/wxMenu/wx_menu");
		mv.addObject("internet", pdInternet);// 首个公众号的信息
		mv.addObject("fuList", fuList);// 一级菜单列表
		mv.addObject("fList", fList);// 第一个的二级菜单列表
		mv.addObject("tList", tList);// 第三个的二级菜单列表
		mv.addObject("pd", pd);// 首个菜单的信息
		return mv;
	}

	/**
	 * 移动菜单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/move")
	@ResponseBody
	public Object move(HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		User user = this.getUser();//得到用户

		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);
		String appId = pdInternet.getString("WECHAT_ID");
		//列，行，标识，公众号id
		pd.put("LIE", request.getParameter("LIE"));
		pd.put("HANG", request.getParameter("HANG"));
		pd.put("FLAG", request.getParameter("FLAG"));
		pd.put("APP_ID", appId);
		JSONObject json = menuService.move(pd);
		return json;
	}

	/**
	 * 切换菜单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/change")
	@ResponseBody
	public Object change(HttpServletRequest request) throws Exception {
		System.out.println("自定义菜单---切换菜单change");

		User user = this.getUser();//得到用户
		PageData pdInternet = new PageData();
		pdInternet.put("INTENET_ID", user.getINTENET_ID());
		pdInternet = intenetService.findById(pdInternet);

		// 切换前，自动保存上一个菜单
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("APP_ID", pdInternet.getString("WECHAT_ID"));
		pd.put("INTERNET_ID", pdInternet.getString("INTENET_ID"));
		menuService.saveMenu(pd, request);

		// 切换到当前的菜单
		PageData pdMenu = new PageData();
		pdMenu.put("FIRST", request.getParameter("LIE2"));
		pdMenu.put("SECOND", request.getParameter("HANG2"));
		pdMenu.put("APP_ID", pdInternet.getString("WECHAT_ID"));
		pdMenu = menuService.findByOrder(pdMenu);

		if (StringUtil.isNotEmpty(pdMenu.getString("TYPE"))) {
			if (pdMenu.getString("TYPE").equals(TYPE3)) {
				if (StringUtil.isNotEmpty(pdMenu.getString("CONTENT"))) {
					pdMenu.put("DETAIL", DETAIL1);
				}
				if (StringUtil.isNotEmpty(pdMenu.getString("CARD_ID"))) {
					pdMenu.put("DETAIL", DETAIL2);
					PageData pdCard = new PageData();
					pdCard.put("CARD_ID", pdMenu.getString("CARD_ID"));
					pdCard = cardService.findById(pdCard);
					if (StringUtil.isNotEmpty(pdCard)) {
						// 提取卡券信息
						pdMenu.put("SUB_TITLE", pdCard.getString("SUB_TITLE"));
						pdMenu.put("DESCRIPTION", pdCard.getString("DESCRIPTION"));
						if (pdCard.getString("TYPE").equals(TYPE1)) {
							pdMenu.put("AVAILABLE_TIME",
									pdCard.getString("BEGIN_TIMESTAMP") + "至" + pdCard.getString("END_TIMESTAMP"));
						} else if (pdCard.getString("TYPE").equals(TYPE2)) {
							String name = "";
							if (pdCard.getString("FIXED_BEGIN_TERM").equals("0")) {
								name = "当天";
							} else {
								name = pdCard.getString("FIXED_BEGIN_TERM") + "天";
							}
							pdMenu.put("AVAILABLE_TIME",
									"自领取后" + name + "生效，" + pdCard.getString("FIXED_TERM") + "天内有效");
						}
						PageData pdStore = new PageData();
						pdStore.put("CARD_ID", pdMenu.getString("CARD_ID"));
						List<PageData> sList = storeService.listByCardId(pdStore);
						String storeName = "";
						if (sList.size() > 0) {
							for (PageData pds : sList) {
								storeName += pds.getString("STORE_NAME") + "，";
							}
							pdMenu.put("STORE_NAME", storeName.substring(0, storeName.length() - 1));
						}
					}
				}
			} else if (pdMenu.getString("TYPE").equals(TYPE4)) {
				if (StringUtil.isNotEmpty(pdMenu.getString("PATH"))) {
					pdMenu.put("DETAIL", DETAIL3);
				} else {
					PageData pdMaterial = new PageData();
					pdMaterial.put("MEDIA_ID", pdMenu.getString("MEDIA_ID"));
					pdMaterial = sendRecordService.findByMediaId(pdMaterial);
					if (StringUtil.isNotEmpty(pdMaterial)) {
						List<PageData> mList = recordMaterialService.findBySendRecordId(pdMaterial);
						pdMenu.put("DETAIL", DETAIL4);
						pdMenu.put("mList", mList);
						pdMenu.put("CREATE_TIME", pdMaterial.getString("CREATE_TIME"));
					}
					pdMenu.put("DETAIL", DETAIL4);
				}
			}
		}
		return pdMenu;
	}

	/**
	 * 加载图文列表
	 * 
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tuwenList")
	@ResponseBody
	public ModelAndView tuwenList(HttpServletRequest request, Page page) throws Exception {
		System.out.println("自定义菜单---展示图文列表tuwenList");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户


		//根据标题搜索
		String title = request.getParameter("TITLE");

		PageData pd = new PageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());// 以后根据appid来查
		pd.put("STATE", 2);
		pd.put("MENUTITLE", title);
		page.setPd(pd);
		List<PageData> tuwenList = sendRecordService.list(page);

		// 加载当前公众号的群发记录
		for (int i = 0; i < tuwenList.size(); i++) {
			PageData pdMaterial = new PageData();
			String sendRecordId = tuwenList.get(i).getString("SENDRECORD_ID");
			pdMaterial.put("SENDRECORD_ID", sendRecordId);
			List<PageData> materialList = recordMaterialService.findBySendRecordId(pdMaterial);
			tuwenList.get(i).put("mList", materialList);
		}

		mv.setViewName("weixin/wxMenu/tuwen");
		mv.addObject("tuwenList", tuwenList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 加载图片列表
	 * 
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tupianList")
	@ResponseBody
	public ModelAndView tupianList(HttpServletRequest request, Page page) throws Exception {
		System.out.println("自定义菜单---展示图片列表图片List");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		// 获取历史封面图片
		PageData pdPicture = new PageData();
		pdPicture.put("INTERNET_ID", user.getINTENET_ID());
		List<PageData> imgList = internetPicturesService.findByInternetId(pdPicture);

		mv.setViewName("weixin/wxMenu/beforeImg");
		mv.addObject("imgList", imgList);
		return mv;
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
		System.out.println("自定义菜单---展示卡券列表tuwenList");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pdCard = new PageData();
		pdCard.put("INTERNET_ID", user.getINTENET_ID());
		List<PageData> cardList = cardService.listByMenu(pdCard); // 列出Card列表

		//处理卡券展示数据
		for (PageData pdInternet : cardList) {
			String cardId = pdInternet.getString("CARD_ID");
			PageData pdStore = new PageData();
			pdStore.put("CARD_ID", cardId);
			List<PageData> sList = storeService.listByCardId(pdStore);
			String storeName = "";
			if (sList.size() > 0) {
				for (PageData pds : sList) {
					storeName += pds.getString("STORE_NAME") + "，";
				}
				pdInternet.put("STORE_NAME", storeName.substring(0, storeName.length() - 1));
			}
			if (pdInternet.getString("TYPE").equals(TYPE1)) {
				pdInternet.put("AVAILABLE_TIME",
						pdInternet.getString("BEGIN_TIMESTAMP") + "至" + pdInternet.getString("END_TIMESTAMP"));
			} else if (pdInternet.getString("TYPE").equals(TYPE2)) {
				String name = "";
				if (pdInternet.getString("FIXED_BEGIN_TERM").equals("0")) {
					name = "当天";
				} else {
					name = pdInternet.getString("FIXED_BEGIN_TERM") + "天";
				}
				pdInternet.put("AVAILABLE_TIME", "自领取后" + name + "生效，" + pdInternet.getString("FIXED_TERM") + "天内有效");
			}
		}

		mv.setViewName("weixin/wxMenu/card");
		mv.addObject("cardList", cardList);
		return mv;
	}

	/**
	 * 预览图文
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/preview")
	@ResponseBody
	public ModelAndView preview(HttpServletRequest request) throws Exception {
		System.out.println("自定义菜单---------------预览图文preview");
		ModelAndView mv = this.getModelAndView();

		PageData pd = new PageData();
		pd.put("SENDRECORD_ID", request.getParameter("SENDRECORD_ID"));
		pd.put("SEQUENCE", request.getParameter("SEQUENCE"));
		pd = recordMaterialService.findByMediaIdAndSequence(pd);

		String tempId = this.get32UUID();
		PageData temp = new PageData();
		temp.put("TEMP_ID", tempId);
		temp.put("TITLE", pd.getString("TITLE"));
		temp.put("AUTHOR", pd.getString("CREATE_USER"));
		temp.put("CONTENT", pd.getString("CONTENT"));
		recordMaterialService.saveTemp(temp);
		String url = PublicManagerUtil.wxmenuUrl + tempId;
		// String url = "http://192.168.2.216/lanker/sendRecord/phonePreview.do?mm="+tempId;

		mv.setViewName("weixin/sendRecord/showImgText");
		mv.addObject("title", pd.getString("TITLE"));
		mv.addObject("time", Tools.date2Str(new Date(), "yyyy-MM-dd"));
		mv.addObject("author", pd.getString("CREATE_USER"));
		mv.addObject("content", pd.getString("CONTENT"));
		mv.addObject("url", url);
		return mv;
	}

	
}
