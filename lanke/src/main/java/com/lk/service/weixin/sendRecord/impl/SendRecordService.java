package com.lk.service.weixin.sendRecord.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lk.cache.Cache;
import com.lk.cache.CacheManager;
import com.lk.entity.Message;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.util.*;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.service.weixin.sendRecord.SendRecordManager;
import com.lk.wechat.util.WechatMaterialUtil;
import com.lk.wechat.util.WechatMessageUtil;
import com.lk.wechat.util.WechatUtil;



@Service("sendRecordService")
public class SendRecordService implements SendRecordManager{

	private static Logger logger = LoggerFactory.getLogger(SendRecordService.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name="internetmaterialService")
	private InternetMaterialManager internetmaterialService;
	@Resource(name="recordMaterialService")
	private RecordMaterialManager recordMaterialService;
	@Resource(name="internetPicturesService")
	private InternetPicturesManager internetPicturesService;

	@Resource(name="wechatuserService")
	private WeChatUserManager wechatuserService;
	@Resource(name="autoReplyService")
	private AutoReplyService autoReplyService;
	@Resource(name = "myopService")
	private MyopManager myopService;

	@Resource(name = "intenetService")
	private IntenetManager intenetService;
	/**
	 * 新增
	 */
	public void save(PageData pd) throws Exception {
		dao.save("SendRecordMapper.save", pd);
	}

	/**
	 * 删除
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("SendRecordMapper.delete", pd);
	}

	/**
	 * 修改
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("SendRecordMapper.edit", pd);
	}

	
	/**
	 * 查询dataListPage
	 */
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>)dao.findForList("SendRecordMapper.datalist", page);
	}

	@Override
	public List<PageData> listByInternetId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SendRecordMapper.listByInternetId", pd);
	}

	/**
	 * 根据主键id查
	 * pd中有SENDRECORD_ID
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("SendRecordMapper.findById", pd);
	}
	/**
	 * 根据media_id查
	 * pd中有MEDIA_ID
	 */
	public PageData findByMediaId(PageData pdMaterial) throws Exception {
		return (PageData)dao.findForObject("SendRecordMapper.findByMediaId", pdMaterial);
	}

	/**
	 * 批量删除
	 */
	public void deleteAll(String[] ids) throws Exception {
		dao.delete("SendRecordMapper.deleteAll", ids);
	}

	/**
	 * 根据网吧id查
	 * pd中有INTERNET_ID
	 */
	public List<PageData> findByInternetId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SendRecordMapper.findByInternetId", pd);
	}

	/**
	 * 查询某个时间段的发送的数量
	 * pd中有INTERNET_ID，BEFORE，AFTER
	 */
	public List<PageData> isOutSend(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("SendRecordMapper.isOutSend", pd);
	}



	/******************************************* 业务逻辑 ********************************/
	/**
	 * 加载图文列表
	 * @param pd : state
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message loadRecordNews(PageData pd, Page page) throws Exception {

		page.setPd(pd);
		List<PageData> varList = this.listByInternetId(pd);

		//加载每条记录的图文值
		for (int i = 0; i < varList.size(); i++) {
			PageData pdRecord = varList.get(i);
			if(pdRecord.getString("STATE").equals("1")){
				pdRecord.put("STATE_INFO", "未群发");
			}
			if(pdRecord.getString("STATE").equals("2")){
				pdRecord.put("STATE_INFO", "已群发");
			}

			PageData pdMaterial = new PageData();
			pdMaterial.put("SENDRECORD_ID", pdRecord.getString("SENDRECORD_ID"));
			List<PageData> materialList = recordMaterialService.findBySendRecordId(pdMaterial);
			pdRecord.put("mList", materialList);
			varList.set(i, pdRecord);
		}

		return Message.ok().addData("list", varList);
	}


	/**
	 * 加载图文记录
	 * @param pd : pre sendrecord_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message loadRecord(PageData pd) throws Exception {

		pd.put("SENDRECORD_ID", pd.getString("sendrecord_id"));
		PageData pdRecord = this.findById(pd);
		if(pdRecord == null){
			Message.error("找不到数据");
		}
		List<PageData> mList = recordMaterialService.findBySendRecordId(pd);
		for (int i = 0; i < mList.size(); i++) {
			PageData pdd = mList.get(i);
			pdd.put("URL", pd.getString("pre") + Const.FILEPATHIMG  + pdd.getString("PATH"));
			mList.set(i, pdd);
		}
		Message message = Message.ok().addData("mList", mList).addData("pdRecord", pdRecord);

		return message;
	}


	/**
	 * 加载公众号的图片列表
	 * @param pd : internet_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message loadWxImgs(PageData pd) throws Exception {

        pd.put("INTERNET_ID", pd.getString("internet_id"));
		List<PageData> imgList = internetPicturesService.findByInternetId(pd);

		return Message.ok().addData("imgList", imgList);
	}


	/**
	 * 删除图文
	 * @param pd : sendrecord_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message delRecord(PageData pd) throws Exception {

		pd.put("SENDRECORD_ID", pd.getString("sendrecord_id"));
		PageData pdRecord = this.findById(pd);
		if(pdRecord == null){
			Message.error("找不到数据");
		}

		//做逻辑删除
		pdRecord.put("D_STATE", "1");
		this.edit(pdRecord);

		return Message.ok("删除成功");
	}


	/**
	 * 保存图文
	 * @param pd ：pre internet_id sendrecord_id total_num
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message saveRecord(PageData pd) throws Exception {

		logger.debug(pd.toString());

		if(StringUtil.isEmpty(pd.getString("total_num"))){
			return Message.error("缺少参数total_num");
		}
		String sendrecord_id = pd.getString("sendrecord_id");
		String internet_id = pd.getString("internet_id");
		PageData pdRecord = new PageData();
		if(StringUtil.isEmpty(sendrecord_id)){
			//新增图文
			sendrecord_id = StringUtil.get32UUID();

			pdRecord = new PageData();
			pdRecord.put("SENDRECORD_ID", sendrecord_id);//之前去新增页面的时候就默认创建一个数值，因此返回的也是新建的数值
			pdRecord.put("INTERNET_ID", internet_id);
			pdRecord.put("CREATE_TIME", Tools.date2Str(new Date()));
			pdRecord.put("STATE", "1");//未发（草稿）
			pdRecord.put("D_STATE", "0");//未删除
			pdRecord.put("GROUP_ID", pd.getString("group_id"));//群发组对象：0是全部；1是会员；2是非会员
			pdRecord.put("GROUP_SEX", pd.getString("group_sex"));//群发性别：0是全部；1是男；2是女
			pdRecord.put("GROUP_PROVINCE", pd.getString("group_province"));//群发省份
			pdRecord.put("GROUP_CITY", pd.getString("group_city"));//群发城市
			this.save(pdRecord);

		}else{
			//修改图文
			pd.put("SENDRECORD_ID", sendrecord_id);
			pdRecord = this.findById(pd);
			if(pdRecord == null){
				Message.error("找不到数据");
			}
			pdRecord.put("CREATE_TIME", Tools.date2Str(new Date()));
			pdRecord.put("GROUP_ID", pd.getString("group_id"));//群发组对象：0是全部；1是会员；2是非会员
			pdRecord.put("GROUP_SEX", pd.getString("group_sex"));//群发性别：0是全部；1是男；2是女
			pdRecord.put("GROUP_PROVINCE", pd.getString("group_province"));//群发省份
			pdRecord.put("GROUP_CITY", pd.getString("group_city"));//群发城市
			this.edit(pdRecord);

		}

		//保存图文素材
		int num = Integer.parseInt(pd.getString("total_num"));//图文数量
		List<PageData> list = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Message m = getMaterial(pd, i);
			if(m.getErrcode() != 0){
				return m;
			}
			PageData pdMaterial = (PageData) m.getData().get("pdMaterial");
			list.add(pdMaterial);
		}
		// 查询原有的图文素材
		pd.put("SENDRECORD_ID", sendrecord_id);
		List<PageData> recordMaterialList = recordMaterialService.findBySendRecordId(pd);


		//现删除素材和关系表
		internetmaterialService.delBySendRecordId(sendrecord_id);
		recordMaterialService.delBySendRecordId(sendrecord_id);
		//保存图文素材与群发记录关系表
		for (int i = 0; i < list.size(); i++) {
			PageData pdMaterial = list.get(i);
			//保存素材
			String media_id = "";
			String internetMaterialId = StringUtil.get32UUID();
			if(recordMaterialList.size() > 0
					&& recordMaterialList.size() >= list.size()
					&& StringUtil.isNotEmpty(recordMaterialList.get(i))
					&& StringUtil.isNotEmpty(recordMaterialList.get(i).getString("MEDIA_ID"))){
				media_id = recordMaterialList.get(i).getString("MEDIA_ID");
			}
			pdMaterial.put("INTERNETMATERIAL_ID", internetMaterialId);
			pdMaterial.put("INTERNET_ID", internet_id);
			pdMaterial.put("CREATE_TIME", Tools.date2Str(new Date()));
			pdMaterial.put("MEDIA_ID", media_id);
			internetmaterialService.save(pdMaterial);


			//保存发送，图文，图片之间的关系表
			PageData pdRecordMaterial = new PageData();
			pdRecordMaterial.put("RECORDMATERIAL_ID", StringUtil.get32UUID());
			pdRecordMaterial.put("SENDRECORD_ID", sendrecord_id);
			pdRecordMaterial.put("INTERNETMATERIAL_ID", internetMaterialId);
			pdRecordMaterial.put("SEQUENCE", i);//序号
			pdRecordMaterial.put("PICTURE_ID", pdMaterial.getString("picture_id"));
			recordMaterialService.save(pdRecordMaterial);
		}


		return Message.ok("保存成功").addData("sendrecord_id", sendrecord_id);
	}


	private Message getMaterial (PageData pd, int i){
		PageData pdMaterial = new PageData();
		String title = pd.getString("title"+i);
		String author = pd.getString("author"+i);
		String description = pd.getString("description"+i);
		String sourceUrl = pd.getString("source_url"+i);
		String content = pd.getString("content"+i);
		String picture_id = pd.getString("picture_id"+i);
		String media_id = pd.getString("media_id"+i);


		if(StringUtil.isEmpty(title)){
			return Message.error("第" + (i + 1) + "篇的标题不能为空！");
		}
		if(StringUtil.isEmpty(author)){
			return Message.error("第" + (i + 1) + "篇的作者不能为空！");
		}
		if(StringUtil.isEmpty(picture_id)){
			return Message.error("第" + (i + 1) + "篇的封面图片不能为空！");
		}
		if(StringUtil.isEmpty(content)){
			return Message.error("第" + (i + 1) + "篇的正文内容不能为空！");
		}

		pdMaterial.put("TITLE", title);
		pdMaterial.put("picture_id", picture_id);
		pdMaterial.put("THUMB_MEDIA_ID", media_id);
		pdMaterial.put("CREATE_USER", author);
		pdMaterial.put("DIGEST", description);
		pdMaterial.put("CONTENT", content);
		pdMaterial.put("CONTENT_SOURCE_URL", sourceUrl);
		return Message.ok().addData("pdMaterial", pdMaterial);
	}


	/**
	 * 群发图文
	 * @param pd pre internet_id sendrecord_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message sendNews(PageData pd) throws Exception {

		String sendrecord_id = pd.getString("sendrecord_id");
		String internet_id = pd.getString("internet_id");
		String pre = pd.getString("pre");

		//判断当前月份是否已发满4条
		PageData isPd = new PageData();
		isPd.put("INTERNET_ID", internet_id);
		isPd.put("BEFORE", Tools.getFirstDay() + " 00:00:00");
		isPd.put("AFTER", Tools.getLastDay() + " 23:59:59");
		List<PageData> list = this.isOutSend(isPd);
		if(list.size() > 3){
			return Message.error("当月已经群发4条图文了！不能再发送");
		}

		//1.获取群发对象
		pd.put("SENDRECORD_ID", sendrecord_id);
		PageData pdRecord = this.findById(pd);
		if(pdRecord == null){
			return Message.error("找不到数据");
		}else if(pdRecord.getString("STATE").equals("2")){
			return Message.error("该图文记录已群发");

		}
		List<PageData> openIdList = wechatuserService.findBySend(pdRecord);
		if(openIdList.size() < 2){
			return Message.error("群发对象至少两人！");
		}
		JSONArray openIdArray = new JSONArray();
//		openIdArray.add("ofavOwDC6PgSVmN8SP2j4jxNiDZE");
//		openIdArray.add("ofavOwDC6PgSVmN8SP2j4jxNiDZE");
		for(int i=0;i<openIdList.size();i++){
			openIdArray.add(openIdList.get(i).getString("OPEN_ID"));
		}

		//获取token
		String accessToken = autoReplyService.getAuthToken(internet_id);

		//2.上传图文素材
		String media_id = "";
		List<PageData> recordMaterialList = recordMaterialService.findBySendRecordId(pdRecord);
		List<PageData> pdList = new ArrayList<PageData>();
		//2.1 上传图文内的图片
		for (int i = 0; i < recordMaterialList.size(); i++) {
			PageData pdMaterial = recordMaterialList.get(i);
			//上传图文中的图片和视频，保存返回值
			String content = WechatUtil.saveImgOrVideoToWx(pdMaterial, accessToken, pre);
//			System.out.println("content===" + content);

			pdMaterial.put("CONTENT", content);
			pdList.add(pdMaterial);
		}
		//2.2 上传多图文
		JSONObject upResult = WechatMaterialUtil.postNews(pdList, accessToken);
		if(!upResult.containsKey("media_id")){
			return Message.error("上传图文素材失败，请稍后再试！");
		}
		media_id = upResult.getString("media_id");


		//3.发送图文
		JSONObject result = WechatMessageUtil.sendNewsByOpenIdArray(accessToken, media_id, openIdArray);
		if(!result.getString("errcode").equals("0")){
			return Message.error("发送失败，请稍后再试！");
		}
		//发送成功，保存media_id等信息
		pdRecord.put("MEDIA_ID", media_id);
		pdRecord.put("STATE", "2");//已发送
		pdRecord.put("SEND_TIME", Tools.date2Str(new Date()));
		pdRecord.put("CREATE_TIME", Tools.date2Str(new Date()));
		this.edit(pdRecord);

		return Message.ok("群发成功");
	}


	/**
	 * 预览图文
	 * @param pd pre internet_id curr_sequence sendrecord_id user_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message getPreviewQr(PageData pd) throws Exception {

		String sendrecord_id = pd.getString("sendrecord_id");
		String curr_sequence = pd.getString("curr_sequence");
		String internet_id = pd.getString("internet_id");
		String pre = pd.getString("pre");
		String user_id = pd.getString("user_id");

		//获取token
		String token = autoReplyService.getAuthToken(internet_id);

		pd.put("SENDRECORD_ID", sendrecord_id);
		pd.put("SEQUENCE", curr_sequence);
		PageData pdMaterial = recordMaterialService.findByMediaIdAndSequence(pd);
		//1 上传图文内的图片
		String content = WechatUtil.saveImgOrVideoToWx(pdMaterial, token ,pre);

		//2 上传/修改图文素材
		String media_id = "";
		JSONObject result = new JSONObject();
		String INTERNETMATERIAL_ID = pdMaterial.getString("INTERNETMATERIAL_ID");
		if(StringUtil.isNotEmpty(pdMaterial.get("MEDIA_ID"))){
			media_id = pdMaterial.get("MEDIA_ID").toString();
			//修改
			pdMaterial.put("INTERNETMATERIAL_ID", media_id);
			pdMaterial.put("CONTENT", content);
			result = WechatMaterialUtil.updateNew(pdMaterial, token, "0");

		}else{
			//新增
			pdMaterial.put("CONTENT", content);
			result = WechatMaterialUtil.postNew(pdMaterial, token);
			media_id = result.getString("media_id");
		}
		//微信接口返回
		if(result.containsKey("media_id") || result.getString("errcode").equals("0")){
			pdMaterial.put("INTERNETMATERIAL_ID", INTERNETMATERIAL_ID);
			pdMaterial.put("MEDIA_ID", media_id);
			pdMaterial.remove("CONTENT");
			internetmaterialService.edit(pdMaterial);
		}else{
			return Message.error("上传预览图文失败");
		}

		//生成二维码连接
		JSONObject scene = new JSONObject();
		scene.put("scene_str", "preview_news" + user_id);//场景值
		JSONObject qr_json = WechatUtil.generateQCode("temp", token, 60 * 60, scene);//1天有效
		String url = qr_json.getString("url");
		Cache cache = CacheManager.getWxQrCache();
		cache.insertObject("preview_news" + user_id, media_id);


		return Message.ok().addData("media_id", media_id).addData("url", url);
	}


	/**
	 * 发送预览图文
	 * @param pd openid  media_id internet_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Message sendPreviewNews(PageData pd) throws Exception {

		String openId = pd.getString("openid");
		String media_id = pd.getString("media_id");
		String internet_id = pd.getString("internet_id");


		//获取token
		String token = autoReplyService.getAuthToken(internet_id);
		//发送预览图文
		WechatUtil.previewNews(token, openId, media_id);

		return Message.ok();
	}

	@Override
	public JSONObject preview(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		result.put("result", "false");

		User user = Jurisdiction.getSessionUser().getUser();//得到用户
		if(user == null){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}

		String token = autoReplyService.getAuthToken(user.getINTENET_ID());

		String[] arr = pd.getString("send_sequence").split(";");
		pd.put("CREATE_USER", pd.getString("AUTHOR"));
		pd.put("THUMB_MEDIA_ID", arr[2]);


		//修改图文表的media_id
		PageData pdm = new PageData();
		pdm.put("SENDRECORD_ID", arr[0]);
		pdm.put("SEQUENCE", arr[1]);
		pdm = recordMaterialService.findByMediaIdAndSequence(pdm);

		//上传图文中的图片和视频
		String pre = pd.getString("pre");
		String content = WechatUtil.saveImgOrVideoToWx(pdm, token ,pre);
		pd.put("CONTENT", content);

		JSONObject postResult = new JSONObject();
		String media_id = "";
		if(pdm.get("MEDIA_ID") != null){
			media_id = pdm.get("MEDIA_ID").toString();
			//修改
			pd.put("INTERNETMATERIAL_ID", pdm.get("MEDIA_ID"));
			postResult = WechatMaterialUtil.updateNew(pd,token,"0");

		}else{
			//新增
			postResult =WechatMaterialUtil.postNew(pd, token);

		}

		if((postResult.containsKey("errcode") &&
				postResult.getString("errcode").equals("0")) || postResult.containsKey("media_id")){

			if (postResult.containsKey("media_id")){
				media_id = postResult.getString("media_id");

				pdm.put("MEDIA_ID", media_id);
				internetmaterialService.edit(pdm);
			}

			JSONObject materialResult = WechatMaterialUtil.getVedioMaterial(media_id, token);
			if(materialResult.containsKey("news_item")){
				JSONArray array = materialResult.getJSONArray("news_item");
				JSONObject material = array.getJSONObject(0);
				String time = Tools.formatTime(materialResult.getString("create_time"));
				if(StringUtil.isNotEmpty("update_time")){
					time = Tools.formatTime(materialResult.getString("update_time"));
				}
				result.put("create_time", time.substring(0,10));
				result.put("result", "true");
				result.put("material", material);

			}
		}




		return result;
	}
}
