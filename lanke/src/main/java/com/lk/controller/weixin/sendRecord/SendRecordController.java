package com.lk.controller.weixin.sendRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.entity.Message;
import com.lk.service.article.ArticleService;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.wechat.util.WeChatOpenUtil;
import com.lk.wechat.util.WechatUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lk.controller.base.BaseController;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.service.weixin.sendRecord.SendRecordManager;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

/**
 * 说明：新建图文
 */
@Controller
@RequestMapping(value = "/sendRecord")
public class SendRecordController extends BaseController {

	//菜单连接
	String menuUrl = "sendRecord/list.do";

	@Resource(name = "sendRecordService")
	private SendRecordManager sendRecordService;


	@Resource(name = "recordMaterialService")
	private RecordMaterialManager recordMaterialService;
	@Resource(name = "wechatuserService")
	private WeChatUserManager wechatuserService;



	@Autowired
	private ArticleService articleService;



	/******************************************* 请求页面 ********************************/
	/**
	 * 进入列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		//传入参数
		PageData pd = this.getPageData();

		mv.addObject("pd", pd);
		mv.setViewName("weixin/sendRecord/sendRecord_list2");
		return mv;
	}

	/**
	 * 进入新增/编辑图文页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() throws Exception {
		ModelAndView mv = this.getModelAndView();
		//传入参数
		PageData pd = this.getPageData();
		User user = this.getUser();
		pd.put("NAME", user.getNAME());

		//pd 若存在 synthesizer 表示从揽客文章库跳转过来
		mv.addObject("pd", pd);
		mv.setViewName("weixin/sendRecord/imgText_add3");
		return mv;
	}


	/**
	 * 进入上传微信图片页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goUploadImg")
	public ModelAndView goUploadImg() {
		ModelAndView mv = this.getModelAndView();
		//传入参数
		PageData pd = this.getPageData();

		mv.addObject("pd", pd);
		mv.setViewName("weixin/sendRecord/uploadImg");
		return mv;
	}


	/**
	 * 进入群发设置页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goGroupSet")
	public ModelAndView goGroupSet() throws Exception {
		ModelAndView mv = this.getModelAndView();
		User user = this.getUser();//得到用户

		PageData pd = new PageData();
		pd.put("INTERNET_ID", user.getINTENET_ID());
		pd.put("area", "province");
		// 查找关注的人所有的地区
		List<PageData> proList = wechatuserService.findArea(pd);


		mv.setViewName("weixin/sendRecord/groupSet");
		mv.addObject("proList", proList);
		return mv;
	}


	/**
	 * 扫描预览二维码，跳转到公众号页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPreviewNews")
	public ModelAndView sendPreviewNews() throws Exception{
		logBefore(logger, "---发送预览图文");
		//传入参数 media_id
		PageData pd = this.getPageData();
		pd.put("pre", this.getPre());

		Message m = sendRecordService.sendPreviewNews(pd);
		return new ModelAndView("redirect:" + m.getData().get("url"));
	}


	/******************************************* 请求方法 ********************************/

	/**
	 * 加载图文列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/loadRecordNews")
	public Message loadRecordNews(Page page) throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "加载图文列表");
		//传入参数 state
		PageData pd = this.getPageData();
		User user = this.getUser();
		pd.put("internet_id", user.getINTENET_ID());

		return sendRecordService.loadRecordNews(pd, page);
	}


	/**
	 * 加载图文记录
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/loadRecord")
	public Message loadRecord() throws Exception{

		//传入参数 sendrecord_id
		PageData pd = this.getPageData();
		pd.put("pre", this.getPre());
		return sendRecordService.loadRecord(pd);
	}


	/**
	 * 从揽客文章库获取图文
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadFromArticle")
	public Message loadFromArticle() throws Exception {
		//传入参数 flag : synthesizer
		PageData pd = this.getPageData();
		pd.put("pre", this.getPre());
		JSONObject result = articleService.loadSynthesizer(pd);

		return Message.ok().addData("mList", result.get("list"));
	}

	/**
	 * 加载公众号的图片列表
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/loadWxImgs")
	public Message loadWxImgs() throws Exception{

		User user = this.getUser();
		//传入参数
		PageData pd = this.getPageData();
		pd.put("internet_id", user.getINTENET_ID());
		return sendRecordService.loadWxImgs(pd);
	}


	/**
	 * 保存图文
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRecord")
	public Message saveRecord() throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "---保存图文");
		User user = this.getUser();
		//传入参数 sendrecord_id total_num 图文列表值
		PageData pd = this.getPageData();
		pd.put("pre", this.getPre());
		pd.put("internet_id", user.getINTENET_ID());

		return sendRecordService.saveRecord(pd);
	}

	/**
	 * 群发图文
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/groupSend")
	public Message groupSend() throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "---群发图文");
		User user = this.getUser();
		//传入参数 sendrecord_id
		PageData pd = this.getPageData();
		pd.put("pre", this.getPre());
		pd.put("internet_id", user.getINTENET_ID());

		return sendRecordService.sendNews(pd);
	}

	/**
	 * 删除图文
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delRecord")
	public Message delRecord() throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "---删除图文");
		//传入参数 sendrecord_id
		PageData pd = this.getPageData();
		return sendRecordService.delRecord(pd);
	}


	/**
	 * 预览图文
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getPreviewQr")
	public Message getPreviewQr() throws Exception{
		logBefore(logger, Jurisdiction.getUsername() + "---预览图文");
		User user = this.getUser();
		//传入参数 sendrecord_id curr_sequence
		PageData pd = this.getPageData();
		pd.put("pre", this.getPre());
		pd.put("internet_id", user.getINTENET_ID());
		pd.put("user_id", user.getUSER_ID());

		return sendRecordService.getPreviewQr(pd);
	}





	/**
	 * 预览(以前的)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/preview")
	public ModelAndView preview(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		String title = request.getParameter("TITLE");
		String author = request.getParameter("AUTHOR");
		String content = request.getParameter("CONTENT");

		String tempId = this.get32UUID();
		PageData temp = new PageData();
		temp.put("TEMP_ID", tempId);
		temp.put("TITLE", title);
		temp.put("AUTHOR", author);
		temp.put("CONTENT", content);
		recordMaterialService.saveTemp(temp);
		String url = PublicManagerUtil.wxmenuUrl + tempId;

		String pre = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		//微信预览接口
		temp.put("send_sequence", request.getParameter("send_sequence"));
		temp.put("pre", pre);
		mv.addObject("temp", temp);
		JSONObject result = sendRecordService.preview(temp);
		mv.addObject("result", result);

		mv.setViewName("weixin/sendRecord/showImgText");

		if(result.getString("result").equals("true")){
			mv.addObject("title", result.getJSONObject("material").get("title"));
			mv.addObject("time", result.get("create_time"));
			mv.addObject("author", result.getJSONObject("material").get("author"));
//			mv.addObject("content", result.getJSONObject("material").get("content"));
			mv.addObject("content", content);

			System.out.println(result.getJSONObject("material").getString("url"));
			mv.addObject("url", result.getJSONObject("material").getString("url"));
		}

		return mv;
	}

	/**
	 * 手机预览(以前的)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/phonePreview")
	public ModelAndView phonePreview(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();

		PageData pd = new PageData();
		pd = recordMaterialService.getYuLanTemp(request.getParameter("mm"));

		String title = pd.getString("TITLE");
		String author = pd.getString("AUTHOR");
		String content = pd.getString("CONTENT");

		// recordMaterialService.deleteTemp(request.getParameter("mm"));

		mv.setViewName("weixin/sendRecord/phonePreview");
		mv.addObject("title", title);
		mv.addObject("time", Tools.date2Str(new Date(), "yyyy-MM-dd"));
		mv.addObject("author", author);
		mv.addObject("content", content);
		return mv;
	}
}
