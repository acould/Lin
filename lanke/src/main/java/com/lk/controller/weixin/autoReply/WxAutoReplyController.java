package com.lk.controller.weixin.autoReply;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.store.StoreManager;
import com.lk.service.system.welcome.WelcomeManager;
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

/** 
 * 说明：(微信)自动回复
 */
@Controller
@RequestMapping(value = "/wxAutoReply")
public class WxAutoReplyController extends BaseController {
	//回复内容类型--TYPE
	public static final String TYPE1 = "material"; //图文消息
	public static final String TYPE2 = "image";    //图片
	public static final String TYPE3 = "text";     //文字
	public static final String TYPE4 = "card";     //卡劵
	public static final String TYPE5 = "DATE_TYPE_FIX_TIME_RANGE";
	public static final String TYPE6 = "DATE_TYPE_FIX_TERM";
	//回复类型--EVENT
	public static final String EVENT1  = "message";   //收到消息回复
	public static final String EVENT2  = "subscribe"; //被关注回复
	public static final String EVENT3  = "keyword";   //关键词自动回复
	//OPEN_ID
	public static final String OPEN_ID  = "otlSPvysBUvymy5QFva9oIEgoH6k";

	String menuUrl = "wxAutoReply/list.do"; // 菜单地址(权限用)
	@Resource(name = "autoReplyService")
	private AutoReplyService autoReplyService;
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
	@Resource(name = "textmsgService")
	private TextmsgService textmsgService;
	@Resource(name = "storeService")
	private StoreManager storeService;
	@Resource(name = "welcomeService")
	private WelcomeManager welcomeService;

	/**
	 * 自动回复列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---自动回复功能列表");
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		// 默认展现关键词自动回复(可切换message，subscribe)，获取回复列表
		pd.remove("TYPE");
		pd.remove("AUTOREPLY_ID");
		pd.put("EVENT", EVENT3);
		String event = this.getRequest().getParameter("EVENT");
		if (StringUtil.isNotEmpty(event)) {
			pd.put("EVENT", event);
		}
		page.setPd(pd);
		if (pd.getString("EVENT").equals(EVENT1) || pd.getString("EVENT").equals(EVENT2)) {
			List<PageData> replyList = autoReplyService.findByEvent(pd);
			if (replyList.size() == 0) {
				pd.put("TYPE", TYPE1);
			} else {
				PageData pdd = new PageData();
				pdd = replyList.get(0);
				pd.put("AUTOREPLY_ID", pdd.getString("AUTOREPLY_ID"));
				pd.put("TYPE", pdd.getString("TYPE"));
				// 收到消息回复或者关注回复的内容
				if (pdd.getString("TYPE").equals(TYPE1)) {
					mv.addObject(TYPE1, getObject(pdd));
				} else if (pdd.getString("TYPE").equals(TYPE2)) {
					mv.addObject(TYPE2, getObject(pdd));
				} else if (pdd.getString("TYPE").equals(TYPE3)) {
					mv.addObject(TYPE3, getObject(pdd));
				} else if (pdd.getString("TYPE").equals(TYPE4)) {
					mv.addObject(TYPE4, getObject(pdd));
				}
			}
		} else {
			List<PageData> keywordList = autoReplyService.list(page);
			mv.addObject("keywordList", keywordList);
		}
		mv.setViewName("weixin/autoReply/autoReply_list");
		mv.addObject("pd", pd);
		Jurisdiction.buttonJurisdiction(menuUrl, "cha");
		mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAddKeyword")
	public ModelAndView goAddKeyword() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---增加关键词自动回复");
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("weixin/autoReply/keyword_edit");
		PageData pd = new PageData();
		pd.put("TYPE", TYPE1);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去修改页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEditKeyword")
	public ModelAndView goEditKeyword() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---修改关键词自动回复");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd.put("AUTOREPLY_ID", this.getRequest().getParameter("AUTOREPLY_ID"));
		pd = autoReplyService.findById(pd);
		if (pd.getString("EVENT").equals(EVENT3)) {
			if (pd.getString("TYPE").equals(TYPE1)) {
				mv.addObject("material", getObject(pd));
			} else if (pd.getString("TYPE").equals(TYPE2)) {
				mv.addObject("image", getObject(pd));
			} else if (pd.getString("TYPE").equals(TYPE3)) {
				mv.addObject("text", getObject(pd));
			} else if (pd.getString("TYPE").equals(TYPE4)) {
				mv.addObject("card", getObject(pd));
			}
		}
		mv.addObject("pd", pd);
		mv.setViewName("weixin/autoReply/keyword_edit");
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveKeyword")
	@ResponseBody
	public JSONObject saveKeyword() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---保存关键词自动回复");
		User user = this.getUser();//得到用户

		JSONObject jsonResult = new JSONObject();
		PageData pd = this.getPageData();
		if (pd.getString("AUTOREPLY_ID").equals("")) {
			// 新增
			pd.put("AUTOREPLY_ID", this.get32UUID());
			pd.put("CREATE_TIME", Tools.date2Str(new Date()));
			pd.put("STATE", "1");
			pd.put("INTERNET_ID", user.getINTENET_ID());
			jsonResult = autoReplyService.save(pd);
		} else {
			// 修改
			pd.put("INTERNET_ID", user.getINTENET_ID());
			jsonResult = autoReplyService.edit(pd);
		}
		if (jsonResult.getString("result").equals(PublicManagerUtil.TRUE)) {
			// 如果有原来的欢迎语就删除原来的
			PageData pdWelcome = new PageData();
			pdWelcome.put("INTENET_ID", user.getINTENET_ID());
			pdWelcome = welcomeService.findById(pdWelcome);
			if (StringUtil.isNotEmpty(pdWelcome)) {
				welcomeService.delete(pdWelcome);
			}
		}
		return jsonResult;
	}

	/**
	 * 删除关键词自动回复
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteKeyword")
	@ResponseBody
	public JSONObject deleteKeyword() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---删除关键词自动回复");
		JSONObject jsonResult = new JSONObject();
		PageData pd = new PageData();
		pd.put("AUTOREPLY_ID", this.getRequest().getParameter("AUTOREPLY_ID"));
		jsonResult = autoReplyService.delete(pd);
		return jsonResult;
	}

	/**
	 * 批量删除关键词自动回复
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAllKeyword")
	@ResponseBody
	public JSONObject deleteAllKeyword() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---多删除关键词自动回复");
		JSONObject jsonResult = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		String ArrayDATA_IDS[] = DATA_IDS.split(",");
		jsonResult = autoReplyService.deleteAll(ArrayDATA_IDS);
		return jsonResult;
	}

	/**
	 * 上传图片（base64的格式图片方式）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public JSONObject uploadImg() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "---上传图片");
		User user = this.getUser();//得到用户

		PageData pd = this.getPageData();
		String file = this.getRequest().getParameter("upFile");// base64图片
		pd.put("upFile", file);
		pd.put("INTERNET_ID", user.getINTENET_ID());
		JSONObject jsonResult = autoReplyService.uploadImg(pd);
		return jsonResult;
	}

	/**
	 * 获取某个消息类型的对象内容
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getObject(PageData pd) throws Exception {
		if (pd.getString("TYPE").equals(TYPE1)) {
			PageData pdMaterial = new PageData();
			pdMaterial.put("MEDIA_ID", pd.getString("OBJECT_ID"));
			pdMaterial = sendRecordService.findByMediaId(pdMaterial);
			if (StringUtil.isNotEmpty(pdMaterial)) {
				List<PageData> mList = recordMaterialService.findBySendRecordId(pdMaterial);
				pdMaterial.put("mList", mList);
			}
			return pdMaterial;
		} else if (pd.getString("TYPE").equals(TYPE2)) {
			PageData pdImg = new PageData();
			pdImg.put("MEDIA_ID", pd.getString("OBJECT_ID"));
			pdImg = internetPicturesService.findByMediaId(pdImg);
			if (StringUtil.isNotEmpty(pdImg)) {
				return pdImg;
			}
		} else if (pd.getString("TYPE").equals(TYPE3)) {
			PageData pdText = new PageData();
			pdText.put("TEXTMSG_ID", pd.getString("OBJECT_ID"));
			pdText = textmsgService.findById(pdText);
			if (StringUtil.isNotEmpty(pdText)) {
				return pdText;
			}
		} else if (pd.getString("TYPE").equals(TYPE4)) {
			PageData pdCard = new PageData();
			pdCard.put("CARD_ID", pd.getString("OBJECT_ID"));
			pdCard = cardService.findById(pdCard);
			if (StringUtil.isNotEmpty(pdCard)) {
				pdCard.put("SUB_TITLE", pdCard.getString("SUB_TITLE"));
				pdCard.put("DESCRIPTION", "使用说明：" + pdCard.getString("DESCRIPTION"));
				if (pdCard.getString("TYPE").equals(TYPE5)) {
					pdCard.put("AVAILABLE_TIME",pdCard.getString("BEGIN_TIMESTAMP") + "至" + pdCard.getString("END_TIMESTAMP"));
				} else if (pdCard.getString("TYPE").equals(TYPE6)) {
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
		}
		return null;
	}

	/**
	 * 测试为微信发送自动回复消息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toSend")
	@ResponseBody
	public JSONObject toSend() throws Exception {
		PageData pdReply = new PageData();
		String token = this.getRequest().getParameter("TOKEN");
		pdReply.put("ACCESS_TOKEN", token);
		pdReply.put("OPEN_ID", OPEN_ID);
		pdReply.put("EVENT", this.getRequest().getParameter("EVENT"));
		pdReply.put("KEYWORD", "11");
		pdReply.put("INTERNET_ID", PublicManagerUtil.INTENET_ID1);
		autoReplyService.wxReply(pdReply);
		return null;
	}


}
