package com.lk.service.weixin.autoReply.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.textmsg.TextmsgService;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;
import com.lk.wechat.util.WechatMaterialUtil;
import com.lk.wechat.util.WechatMessageUtil;

/**
 * 微信--自动回复实现类
 * @author Administrator
 *
 */
@Service("autoReplyService")
public class AutoReplyImpl implements AutoReplyService {
	// FILE(文件类型)
	public static final String FILE1 = "data:image/jpeg;base64,";
	public static final String FILE2 = "data:image/png;base64,";
	// SUFFIXNAME(图片尾缀)
	public static final String SUFFIXNAME1 = ".jpg";
	public static final String SUFFIXNAME2 = ".png";
	//URL(上传下载)
	public static final String URL = PublicManagerUtil.URL1+"uploadFiles/uploadImgs/";
	

	String menuUrl = "wxAutoReply/list.do"; //菜单地址(权限用)
	@Resource(name = "daoSupport")
	private DaoSupport baseDao;
	@Resource(name="terraceService")
	private TerraceManager terraceService;
	@Resource(name="textmsgService")
	private TextmsgService textmsgService;
	@Resource(name="internetPicturesService")
	private InternetPicturesManager internetPicturesService;
	
	
	/**
	 * 分页查询列表
	 * page中设置pd
	 * pd中可有INTERNET_ID，EVENT，KEYWORDS（只有EVENT=keyword时才有）
	 * 网吧id，事件类型，
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) baseDao.findForList("WechatAutoReplyMapper.datalistPage", page);
	}


	/**
	 * 新增操作
	 */
	public JSONObject save(PageData pd) throws Exception {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", PublicManagerUtil.TRUE);
		jsonResult.put("message", "保存成功！");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "没有保存权限！");
			return jsonResult;
		}
		
		//检验字段
		if(pd.getString("OBJECT_ID").equals("")){
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "回复内容不能为空！");
			return jsonResult;
		}
		if(pd.getString("EVENT").equals("keyword")){
			if(pd.getString("NAME").equals("")){
				jsonResult.put("result", PublicManagerUtil.FALSE);
				jsonResult.put("message", "请输入规则名称");
				return jsonResult;
			}
			if(pd.getString("KEYWORD").equals("")){
				jsonResult.put("result", PublicManagerUtil.FALSE);
				jsonResult.put("message", "请输入关键词！");
				return jsonResult;
			}
		}
		
		//新增操作
		if(pd.getString("TYPE").equals("text")){
			PageData pdText = new PageData();
			pdText.put("TEXTMSG_ID", StringUtil.get32UUID());
			pdText.put("INTERNET_ID", pd.getString("INTERNET_ID"));
			pdText.put("CONTENT", pd.getString("OBJECT_ID"));
			pdText.put("CREATE_TIME", Tools.date2Str(new Date()));
			textmsgService.save(pdText);
			pd.put("OBJECT_ID", pdText.getString("TEXTMSG_ID"));
		}
		
		baseDao.save("WechatAutoReplyMapper.save", pd);
		
		return jsonResult;
	}

	/**
	 * 修改操作
	 */
	public JSONObject edit(PageData pd) throws Exception {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", PublicManagerUtil.TRUE);
		jsonResult.put("message", "保存成功！");
		//校验权限
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "没有修改权限！");
			return jsonResult;
		}
		
		//检验字段
		if(pd.getString("OBJECT_ID").equals("")){
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "回复内容不能为空！");
			return jsonResult;
		}
		PageData pdd = new PageData();
		if(pd.getString("EVENT").equals("keyword")){
			if(pd.getString("NAME").equals("")){
				jsonResult.put("result", PublicManagerUtil.FALSE);
				jsonResult.put("message", "请输入规则名称");
				return jsonResult;
			}
			if(pd.getString("KEYWORD").equals("")){
				jsonResult.put("result", PublicManagerUtil.FALSE);
				jsonResult.put("message", "请输入关键词！");
				return jsonResult;
			}
			pdd = findById(pd);
			
		}else{
			List<PageData> replyList = findByEvent(pd);
			pdd = replyList.get(0);
		}
		//编辑的是文字的时候，
		if(pd.getString("TYPE").equals("text")){
			//原本保存的是文字，就修改，否则新增
			if(pdd.getString("TYPE").equals("text")){
				PageData pdText = new PageData();
				pdText.put("TEXTMSG_ID", pdd.getString("OBJECT_ID"));
				pdText.put("CONTENT", pd.getString("OBJECT_ID"));
				textmsgService.edit(pdText);
				pd.put("OBJECT_ID", pdText.getString("TEXTMSG_ID"));
			}else{
				PageData pdText = new PageData();
				pdText.put("TEXTMSG_ID", StringUtil.get32UUID());
				pdText.put("INTERNET_ID", pd.getString("INTERNET_ID"));
				pdText.put("CONTENT", pd.getString("OBJECT_ID"));
				pdText.put("CREATE_TIME", Tools.date2Str(new Date()));
				textmsgService.save(pdText);
				pd.put("OBJECT_ID", pdText.getString("TEXTMSG_ID"));
			}
		}else{
			//原本保存的是文字，需要删除文字
			if(pdd.getString("TYPE").equals("text")){
				PageData pdText = new PageData();
				pdText.put("TEXTMSG_ID", pdd.getString("OBJECT_ID"));
				textmsgService.delete(pdText);
			}
		}
		//修改操作
		
		baseDao.save("WechatAutoReplyMapper.edit", pd);
		
		return jsonResult;
	}


	/**
	 * 删除
	 */
	public JSONObject delete(PageData pd) throws Exception {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", PublicManagerUtil.TRUE);
		jsonResult.put("message", "删除成功！");
		
		pd = findById(pd);
		//如果是文字，先删除文字，再删除规则
		if(pd.getString("TYPE").equals("text")){
			PageData pdText = new PageData();
			pdText.put("TEXTMSG_ID", pd.getString("OBJECT_ID"));
			textmsgService.delete(pdText);
		}
		
		
		Integer i = (Integer) baseDao.delete("WechatAutoReplyMapper.delete", pd);
		if(i == 0){
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "删除失败！");
		}
		return jsonResult;
	}


	/**
	 * 根据主键id查找
	 * pd中包含AUTOREPLY_ID
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) baseDao.findForObject("WechatAutoReplyMapper.findById", pd);
	}


	/**
	 * 批量删除
	 * 参数为主键id的数组
	 */
	public JSONObject deleteAll(String[] arrayDATA_IDS) throws Exception {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", PublicManagerUtil.TRUE);
		jsonResult.put("message", "删除成功！");
		
		for(int j=0;j<arrayDATA_IDS.length;j++){
			PageData pd = new PageData();
			pd.put("AUTOREPLY_ID", arrayDATA_IDS[j]);
			pd = findById(pd);
			//如果是文字，先删除文字，再删除规则
			if(pd.getString("TYPE").equals("text")){
				PageData pdText = new PageData();
				pdText.put("TEXTMSG_ID", pd.getString("OBJECT_ID"));
				textmsgService.delete(pdText);
			}
		}
		
		Integer i = (Integer) baseDao.delete("WechatAutoReplyMapper.deleteAll", arrayDATA_IDS);
		if(i == 0){
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "删除失败！");
		}
		return jsonResult;
	}


	/**
	 * 根据事件类型查询
	 * pd中有EVENT，INTERNET_ID
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByEvent(PageData pd) throws Exception {
		return (List<PageData>) baseDao.findForList("WechatAutoReplyMapper.findByEvent", pd);
	}


	/**
	 * 上传图文到微信服务器
	 * pd中有upFile，INTERNET_ID
	 * base64格式的图片，网吧id
	 */
	public JSONObject uploadImg(PageData pd) throws Exception {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", PublicManagerUtil.TRUE);
		jsonResult.put("message", "上传成功！");
		
		String file = pd.getString("upFile");
		String internetId = pd.getString("INTERNET_ID");
		if(StringUtil.isNotEmpty(file)){
			String suffixName = "";
			if(file.contains(FILE1)){
				suffixName = SUFFIXNAME1;
			}else if(file.contains(FILE2)){
				suffixName = SUFFIXNAME2;
			}else{
				suffixName = SUFFIXNAME1;
			}
			String fileName = new Date().getTime() + suffixName;
			String path = internetId + "/" + fileName ;
			String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
			String showPath = URL + path;
//			String showPath = "http://192.168.2.216/lanker/uploadFiles/uploadImgs/" + path;
			
			//上传到本地
			Tools.pic(file,filePath);
			//上传到微信
			String token = getAuthToken(internetId);
			JSONObject result = WechatMaterialUtil.addMaterialEver(token, filePath, "image");
			if(StringUtil.isNotEmpty(result.getString("media_id"))){
				String mediaId = result.getString("media_id");
				String url = result.getString("url");
				
				PageData pdPicture = new PageData();
				pdPicture.put("PICTURE_ID", StringUtil.get32UUID());
				pdPicture.put("NAME", fileName);
				pdPicture.put("PATH", path);
				pdPicture.put("CREATE_TIME", Tools.date2Str(new Date()));
				pdPicture.put("INTERNET_ID", internetId);
				pdPicture.put("MEDIA_ID", mediaId);
				pdPicture.put("URL", url);
				internetPicturesService.save(pdPicture);
				jsonResult.put("PICTURE_ID", pdPicture.getString("PICTURE_ID"));
				jsonResult.put("PICTURE_ID", mediaId);
				jsonResult.put("PATH", showPath);
				jsonResult.put("URL", url);
			}else{
				jsonResult.put("result", PublicManagerUtil.FALSE);
				jsonResult.put("message", "上传失败！");
			}
		}else{
			jsonResult.put("result", PublicManagerUtil.FALSE);
			jsonResult.put("message", "上传失败！");
		}
		return jsonResult;
	}
	
	
	/**
	 * 获取微信凭证token
	 * @param internetId为网吧id
	 * @throws Exception
	 */
	public String getAuthToken(String internetId) throws Exception{
		PageData pdmyop = new PageData();
		pdmyop.put("INTENET_ID", internetId);	//网吧id
		pdmyop = terraceService.findByInternetId(pdmyop); //通过网吧id去获取授权信息
		pdmyop.put("APP_ID", pdmyop.getString("APP_ID"));
		Date dateTime=Tools.sAddHours(new Date(), -1);
		pdmyop.put("INSERT_TIME", Tools.date2Str(dateTime));
		pdmyop = terraceService.findByAppId(pdmyop); //通过appid和新增时间去获取AUTHORIZER_ACCESS_TOKEN(通过缓存读取数据AUTHORIZER_ACCESS_TOKEN)
		return pdmyop.getString("AUTHORIZER_ACCESS_TOKEN");
	}


	/**
	 * 微信--关键词回复，收到消息回复，关注回复
	 * 
	 */
	public JSONObject wxReply(PageData pd)throws Exception {
		
		if(pd.getString("EVENT").equals("subscribe")){
			//关注回复
			List<PageData> autoReplyList = findByEvent(pd);
			if(autoReplyList.size() > 0){
				PageData pdReply = new PageData();
				pdReply = autoReplyList.get(0);
				sendMessage(pdReply,pd);
			}
		}else{
			//关键词回复
			List<PageData> autoReplyList = findByEvent(pd);
			if(autoReplyList.size() == 0){
				//关键词不匹配，则用收到消息回复
				pd.put("EVENT", "message");
				autoReplyList = findByEvent(pd);
			}
			if(autoReplyList.size() > 0){
				for(int i=0;i<autoReplyList.size();i++){
					PageData pdReply = new PageData();
					pdReply = autoReplyList.get(i);
					sendMessage(pdReply,pd);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 调用微信客服接口，发送消息
	 * @param pdReply
	 * @param pd
	 * @throws Exception
	 */
	public void sendMessage(PageData pdReply,PageData pd) throws Exception{
		JSONObject jsonType = new JSONObject();
		if(pdReply.getString("TYPE").equals("text")){
			PageData pdText = new PageData();
			pdText.put("TEXTMSG_ID", pdReply.getString("OBJECT_ID"));
			pdText = textmsgService.findById(pdText);
			if(StringUtil.isNotEmpty(pdText)){
				jsonType.put("content", pdText.getString("CONTENT"));
				WechatMessageUtil.sendMessageByCustom(pd.getString("ACCESS_TOKEN"), pd.getString("OPEN_ID"), "text", jsonType);
			}
		}else if(pdReply.getString("TYPE").equals("image")){
			jsonType.put("media_id", pdReply.getString("OBJECT_ID"));
			WechatMessageUtil.sendMessageByCustom(pd.getString("ACCESS_TOKEN"), pd.getString("OPEN_ID"), "image", jsonType);
		}else if(pdReply.getString("TYPE").equals("card")){
			jsonType.put("card_id", pdReply.getString("OBJECT_ID"));
			WechatMessageUtil.sendMessageByCustom(pd.getString("ACCESS_TOKEN"), pd.getString("OPEN_ID"), "wxcard", jsonType);
		}else if(pdReply.getString("TYPE").equals("material")){
			jsonType.put("media_id", pdReply.getString("OBJECT_ID"));
			WechatMessageUtil.sendMessageByCustom(pd.getString("ACCESS_TOKEN"), pd.getString("OPEN_ID"), "mpnews", jsonType);
		}
	}
	
	
	
	
}
