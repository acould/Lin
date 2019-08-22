package com.lk.wechat.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lk.entity.system.Intenet;
import com.lk.service.information.pictures.PicturesManager;
import com.lk.service.information.pictures.impl.PicturesService;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.system.intenet.impl.IntenetService;
import com.lk.service.system.wechat.WechatManager;
import com.lk.service.system.wechat.impl.WechatService;
import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.SrcUtils;
import com.lk.util.Tools;
import com.lk.util.UuidUtil;

public class WechatUtil{

	private static Logger log = LoggerFactory.getLogger(WechatMenuUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	@Resource(name="intenetService")
	private static IntenetService orgService;
	@Resource(name="terraceService")
	private TerraceManager terraceService;

	/***
	 * 保存编辑器中的图片和视频到本地
	 * @param pdMaterial
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static String saveImgOrVideoToBenDi(PageData pdMaterial,String accessToken) throws Exception{
		PicturesManager picturesService = new PicturesService();
		String content = pdMaterial.getString("CONTENT");
		List<String> imgSrt = new ArrayList<String>();
		imgSrt=SrcUtils.ImgSrc(pdMaterial.getString("CONTENT"));
		if(imgSrt.size()>0){
			for(int i=0;i<imgSrt.size();i++){
				String filePath = imgSrt.get(i);
					if(filePath.contains("/uploadFiles/uploadImgs/")){
						if(filePath.indexOf("image")>0){
							String[] file1 = imgSrt.get(i).split("/");
							JSONObject resultJSON = WechatMaterialUtil.addMaterialImg(accessToken,  filePath);
							PageData pdPic = new PageData();
							pdPic.put("PICTURES_ID", UuidUtil.get32UUID());			//主键
							pdPic.put("TITLE", "图文--内容图片");						//标题
							pdPic.put("NAME", file1[file1.length-1]);				//文件名
							pdPic.put("PATH",filePath);								//路径
							pdPic.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
							pdPic.put("MASTER_ID", "1");							//附属与
							pdPic.put("INTERNET_ID", pdMaterial.getString("INTERNET_ID"));
							pdPic.put("URL", resultJSON.get("url"));	
							picturesService.save(pdPic);
							content = content.replace(filePath,resultJSON.get("url").toString());
						}
					}
			}
			
		}
		List<String> vedioSrt = new ArrayList<String>();
		vedioSrt=SrcUtils.ImgVedio(content);
		if(vedioSrt.size()>0){
			for(int i=0;i<vedioSrt.size();i++){
				 String filePath = vedioSrt.get(i);	
				 	if(filePath.contains("/uploadFiles/uploadVideos/")){
				 		if(filePath.indexOf("video")>0){
					    	 String[] file1 = vedioSrt.get(i).split("/");
					    	 JSONObject resultJSON = WechatMaterialUtil.postVideo(filePath,pdMaterial.getString("TITLE"),pdMaterial.getString("DIGEST"),accessToken  );
					    	 JSONObject resultJSONVdedio = WechatMaterialUtil.getVedioMaterial(resultJSON.getString("media_id"),accessToken);
							 PageData pdPic = new PageData();
							 pdPic.put("PICTURES_ID", UuidUtil.get32UUID());			//主键
							 pdPic.put("TITLE","图文--内容视频");							//标题
							 pdPic.put("NAME", file1[file1.length-1]);					//文件名
							 pdPic.put("PATH",filePath);								//路径
							 pdPic.put("CREATETIME", Tools.date2Str(new Date()));		//创建时间
							 pdPic.put("MASTER_ID", "1");								//附属与
							 pdPic.put("INTERNET_ID", pdMaterial.getString("INTERNET_ID"));
							 pdPic.put("MATERIAL_ID", resultJSON.get("media_id"));
							 pdPic.put("URL", resultJSONVdedio.get("down_url"));	
							 picturesService.save(pdPic);
							 content = content.replace(filePath,resultJSONVdedio.get("down_url").toString());
					    }
				 	}
			}
		}
		return content;
		
	}
	
	/***
	 * 保存编辑器中的图片和视频到微信
	 * @param pdMaterial
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static String saveImgOrVideoToWx(PageData pdMaterial,String accessToken,String pre) throws Exception{
		String content = pdMaterial.getString("CONTENT");
		List<String> imgSrt = new ArrayList<String>();
		imgSrt=SrcUtils.ImgSrc(pdMaterial.getString("CONTENT"));
		if(imgSrt.size()>0){
			for(int i=0;i<imgSrt.size();i++){
				String pre2 = pre;
				String filePath = imgSrt.get(i);
				String filePath2 = imgSrt.get(i);
					if(filePath.contains("/uploadFiles/uploadImgs/") || filePath.contains("/uploadFiles/uploadCatchImgs/")){
						String aa = "/uploadFiles/uploadImgs/";
						String bb = "/uploadFiles/uploadCatchImgs/";
						if(filePath.contains(aa)){
							filePath = filePath.substring(filePath.indexOf(aa) + 1);
							pre2 += filePath;
							filePath = PathUtil.getClasspath() + filePath;
						}else if(filePath.contains(bb)){
							filePath = filePath.substring(filePath.indexOf(bb) + 1);
							pre2 += filePath;
							filePath = PathUtil.getClasspath() + filePath;
						}
//						System.out.println("本地上的路径-----"+ filePath);
//						System.out.println("显示的路径====="+pre2);
						JSONObject resultJSON = uploadNewsImg(accessToken,  filePath);
//						System.out.println("上次后的路径==="+ resultJSON.get("url"));
						content = content.replace(filePath2,resultJSON.get("url").toString());
					}
			}
			
		}
		List<String> vedioSrt = new ArrayList<String>();
		vedioSrt=SrcUtils.ImgVedio(content);
		if(vedioSrt.size()>0){
			for(int i=0;i<vedioSrt.size();i++){
				String pre2 = pre;
				String filePath = vedioSrt.get(i);	
				String filePath2 = vedioSrt.get(i);	
			 	if(filePath.contains("/uploadFiles/uploadVideos/")){
			 		String aa = "/uploadFiles/uploadVideos/";
					if(filePath.contains(aa)){
						filePath = filePath.substring(filePath.indexOf(aa) + 1);
						pre2 += filePath;
						filePath = PathUtil.getClasspath() + filePath;
					}
					
		    	 JSONObject resultJSON = WechatMaterialUtil.postVideo(filePath,pdMaterial.getString("TITLE"),pdMaterial.getString("DIGEST"),accessToken  );
		    	 JSONObject resultJSONVdedio = WechatMaterialUtil.getVedioMaterial(resultJSON.getString("media_id"),accessToken);
				 
				 content = content.replace(filePath2,resultJSONVdedio.get("down_url").toString());
			 	}
			}
		}
		return content;
		
	}
	
	
	/**
	 * 上传图文消息内的图片（不受数量限制，仅支持jpg/png，<=1M）
	 * @param token
	 * @param filePath（媒体文件的完整路径）
	 * @return
	 */
	public static JSONObject uploadNewsImg(String token,String filePath){
		String URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
		URL = URL.replace("ACCESS_TOKEN", token);
		
		JSONObject result = HttpUtil.wechatUpload(URL, filePath, null, "上传图文消息内的图片");
        return result;
	}
	
	
	/**
	 * 将网络上的图片保存到本地
	 * @param content
	 * @param domain
	 * @param internetId
	 * @return
	 * @throws Exception
	 */
	public static String saveImgToLocal(String content, String domain, String internetId) throws Exception{
		List<String> imgList = SrcUtils.ImgSrc(content);
		for(int i=0;i<imgList.size();i++){
			String filePath = imgList.get(i);
			System.out.println("网络图片路径==="+filePath);
			if(!filePath.contains(domain)){
				//非本地图片时（本地图片：域名路径下的，或者包含/uploadFiles/）
		    	String fileName = new Date().getTime() +".jpg";//图片名称
				String localPath = PathUtil.getClasspath() + Const.CACHE_IMGS + internetId + "/";//图片路径
				Tools.download(filePath, fileName,localPath); 
				
		        String path = domain + "/" + Const.CACHE_IMGS + internetId + "/" + fileName;
				content = content.replace(filePath, path);
			}
		}
		
		return content;
	}


	/**
	 * 微信接口--生成带参数二维码
	 * @param type
	 * @param token
	 * @param expire_seconds
	 * @return
	 * @throws Exception
	 */
	public static JSONObject generateQCode(String type, String token, int expire_seconds, JSONObject scene) throws Exception{

		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
		JSONObject param = new JSONObject();

		if(type.equals("temp")){
			param.put("action_name", "QR_STR_SCENE");//QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值
			param.put("expire_seconds", expire_seconds);//有效时间，秒为单位，最大2592000（即30天）
				JSONObject actionInfo = new JSONObject();
				actionInfo.put("scene", scene);
			param.put("action_info", actionInfo);

		}else if(type.equals("forever")){
			param.put("action_name", "QR_LIMIT_STR_SCENE");//QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
				JSONObject actionInfo = new JSONObject();
				actionInfo.put("scene", scene);
			param.put("action_info", actionInfo);

		}

		return HttpUtil.wechatRequest(url, "POST", param, "生成二维码");

	}

	/**
	 * 发送预览图文
	 * @param token
	 * @param openId
	 * @param media_id
	 * @return
	 */
	public static JSONObject previewNews(String token, String openId, String media_id){

		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="+token;

		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", media_id);//图文的媒体id

		JSONObject param = new JSONObject();
		param.put("touser", openId);
		param.put("mpnews", mpnews);
		param.put("msgtype", "mpnews");

		return HttpUtil.wechatRequest(url, "POST", param, "发送预览图文");
	}























}
