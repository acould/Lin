package com.lk.wechat.util;

import com.lk.entity.system.Intenet;
import com.lk.entity.weixin.JSAPI;
import com.lk.util.UuidUtil;
import com.lk.wechat.response.AccessToken;
import com.lk.wechat.response.WechatUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.util.*;

/**
 * @author
 */
public class WeixinUtil {

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 拉微信用户信息接口
	private final static String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESSTOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取access_token
	 *
	 * @return
	 */
	public static AccessToken getAccessToken(Intenet org) {
		AccessToken accessToken = new AccessToken();
		Map<?, ?> retMap = null;
		String jsonObject = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			String requestUrl = access_token_url.replace("APPID", org.getWECHAT_ID()).replace("APPSECRET",
					org.getWECHAT_KET());
			jsonObject = httpRequest(requestUrl, "GET", null);
			retMap = mapper.readValue(jsonObject, Map.class);
			if (null != retMap) {
				accessToken.setToken(retMap.get("access_token").toString());
				accessToken.setExpiresIn((Integer) retMap.get("expires_in"));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return accessToken;
	}

	/**
	 * 获取jsticket
	 *
	 * @return
	 */
	public static String getJsTicket(String token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
		String resultValue = HttpUrlUtil.openUrl(url, "UTF-8");
		if (StringUtils.isNotBlank(resultValue)) {
			JSONObject jsonObject = JSONObject.fromObject(resultValue);
			return jsonObject.getString("ticket");
		} else {
			return null;
		}
	}

	/**
	 * 获取api_ticket
	 *
	 * @return
	 */
	public static String getApiTicket(String token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=wx_card";
		String resultValue = HttpUrlUtil.openUrl(url, "UTF-8");
		if (StringUtils.isNotBlank(resultValue)) {
			JSONObject jsonObject = JSONObject.fromObject(resultValue);
			return jsonObject.getString("ticket");
		} else {
			return null;
		}
	}

	/**
	 * 获取二维码
	 *
	 * @return
	 */
	public static JSONObject getJsewm(String token) {
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
		StringBuffer sb = new StringBuffer();
		JSONObject resultJSON = null;
		JSONObject gjson = new JSONObject();
		gjson.put("expire_seconds", 604800);
		gjson.put("action_name", "QR_SCENE");
		JSONObject wxcard = new JSONObject();
		JSONObject cardExt = new JSONObject();
		cardExt.put("scene_id", "123");
		wxcard.put("scene", cardExt);
		gjson.put("action_info", wxcard);
		sb.append(gjson.toString());
		try {
			URL httpclient = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			OutputStream os = conn.getOutputStream();
			os.write(sb.toString().getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();
			InputStream is = conn.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message1 = new String(jsonBytes, "UTF-8");
			log.info("1111:" + message1);
			resultJSON = JSONObject.fromObject(message1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultJSON;

	}

	public static JSONObject getJstp(String TICKET) {
		String url = " https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket" + TICKET;
		StringBuffer sb = new StringBuffer();
		JSONObject resultJSON = null;
		try {
			URL httpclient = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "image/jpg");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			OutputStream os = conn.getOutputStream();
			os.write(sb.toString().getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();
			InputStream is = conn.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message1 = new String(jsonBytes, "UTF-8");
			log.info("1111:" + message1);
			resultJSON = JSONObject.fromObject(message1);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultJSON;

	}

	public static JSONObject getOpenId(String appid, String secret, String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code="
				+ code + "&grant_type=authorization_code";
		String resultValue = HttpUrlUtil.openUrl(url, "UTF-8");
		if (StringUtils.isNotBlank(resultValue)) {
			JSONObject jsonObject = JSONObject.fromObject(resultValue);
			return jsonObject;
		} else {
			return null;
		}
	}

    /**
     * 获取js api签名
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static JSAPI getSign(String jsapi_ticket, String url) {
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.update(string1.getBytes());
            byte messageDigest[] = crypt.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            signature = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        JSAPI jsapi = new JSAPI();
        jsapi.setUrl(url);
        jsapi.setJsapi_ticket(jsapi_ticket);
        jsapi.setNonceStr(nonce_str);
        jsapi.setTimestamp(timestamp);
        jsapi.setSignature(signature);
        return jsapi;
    }


	/**
	 * 获取js-sdk卡券签名card_ext
	 * 
	 * @param api_ticket
	 * @param card_id
	 * @return
	 */
	public static JSONObject cardExtSign(String api_ticket, String card_id, String code, String openid) {
		JSONObject json = new JSONObject();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		
//		string1 = timestamp + api_ticket + nonce_str + card_id;
//		string1 = timestamp + nonce_str + api_ticket + card_id;
		string1 = timestamp + api_ticket + card_id;
//		if(card_id.contains("_")) {
//			string1 = timestamp + nonce_str + api_ticket + card_id;
//		}
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.update(string1.getBytes());
			byte messageDigest[] = crypt.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			signature = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		json.put("timestamp", timestamp);
		json.put("nonce_str", nonce_str);
		json.put("signature", signature);
		return json;
	}
	
	/**
	 * 获取js-sdk卡券签名card_ext
	 * 
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static JSONObject cardExtSign1(String api_ticket, String app_id, String card_id) {
		JSONObject json = new JSONObject();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String signature = "";
		String card_type = "GIFT";//优惠券
		
		String string1 = api_ticket + app_id + timestamp + nonce_str + card_id + card_type;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.update(string1.getBytes());
			byte messageDigest[] = crypt.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			signature = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		json.put("timestamp", timestamp);
		json.put("nonce_str", nonce_str);
		json.put("signature", signature);
		return json;
	}

	public static JSONObject getOpenId(Intenet org, String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + org.getWECHAT_ID() + "&secret=" + org.getWECHAT_KET() + "&code=" + code + "&grant_type=authorization_code";
		String resultValue = HttpUrlUtil.openUrl(url, "UTF-8");
		if (StringUtils.isNotBlank(resultValue)) {
			JSONObject jsonObject = JSONObject.fromObject(resultValue);
			return jsonObject;
		} else {
			return null;
		}
	}

	public static WechatUser getUserInfo(String token, String openid) {
		WechatUser user = new WechatUser();
		try {
			Map<?, ?> retMap = null;
			ObjectMapper mapper = new ObjectMapper();
			log.info("getUserInfo start.{token:" + token + ",openid:" + openid + "}");
			String jsonObject = null;
			String requestUrl = getUserInfoUrl.replace("ACCESSTOKEN", token).replace("OPENID", openid);
			log.info("调用微信方法拿用户信息：");
			jsonObject = httpRequest(requestUrl, "GET", null);
			log.info("调用微信方法拿用户信息：" + jsonObject);
			retMap = mapper.readValue(jsonObject, Map.class);
			if (retMap.get("openid") != null) {
				user.setOPEN_ID(retMap.get("openid").toString());
				if (retMap.get("subscribe") != null) {
					user.setSTATE(retMap.get("subscribe").toString());
				} else {
					user.setSTATE("");
				}
				if (retMap.get("nickname") != null) {
					user.setNECK_NAME(retMap.get("nickname").toString());
				} else {
					user.setNECK_NAME("");
				}
				if (retMap.get("sex") != null) {
					user.setSEX(retMap.get("sex").toString());
				} else {
					user.setSEX("");
				}
				if (retMap.get("city") != null) {
					user.setCITY(retMap.get("city").toString());
				} else {
					user.setCITY("");
				}
				if (retMap.get("province") != null) {
					user.setPROVICNE(retMap.get("province").toString());
				} else {
					user.setPROVICNE("");
				}
				if (retMap.get("headimgurl") != null) {
					user.setIMGURL(retMap.get("headimgurl").toString());
				} else {
					user.setIMGURL("");
				}

				if (retMap.get("subscribe_time") == null) {
					user.setCREATE_TIME(String.valueOf(new Date().getTime()));
				} else {
					user.setCREATE_TIME(retMap.get("subscribe_time").toString());
				}
			}
		} catch (Exception e) {
			log.error("get user info exception", e);

		}
		return user;

	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 发送文字信息
	 *
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String testsendTextByOpenids(String token, String message) {
		String urlstr = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; // 群发图文消息
		// String urlstr
		// ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		// //发送客服图文消息
		urlstr = urlstr.replace("ACCESS_TOKEN", token);
		StringBuffer sb = new StringBuffer();
		// String reqjson =createGroupText(getOpenids(token),message);
		// sb.append(reqjson);
		String mes = createGroupImg(getOpenids(token));
		sb.append(mes);
		try {
			URL httpclient = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			OutputStream os = conn.getOutputStream();
			log.info("ccccc:" + sb);
			os.write(sb.toString().getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = conn.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message1 = new String(jsonBytes, "UTF-8");
			log.info("testsendTextByOpenids:" + message1);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 拼装文字
	 *
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String createGroupText(JSONArray array, String message) {
		JSONObject gjson = new JSONObject();
		// JSONObject json = getUserOpenids(array.get(3).toString());
		// //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
		gjson.put("touser", array);
		gjson.put("msgtype", "text");
		/*
		 * JSONObject news =new JSONObject(); JSONArray articles =new JSONArray();
		 * JSONObject list =new JSONObject(); list.put("title","title"); //标题
		 * list.put("description",message); //描述 list.put("url","http://");
		 * //点击图文链接跳转的地址 list.put("picurl","http://"); //图文链接的图片 articles.add(list);
		 * news.put("articles", articles);
		 */
		JSONObject text = new JSONObject();
		text.put("content", message);
		gjson.put("text", text);
		/* gjson.put("news", news); */

		return gjson.toString();
	}

	/**
	 * 拼装视频
	 *
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String createGroupImg(JSONArray array) {
		JSONObject gjson = new JSONObject();
		// JSONObject json = getUserOpenids(array.get(3).toString());
		// //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
		gjson.put("touser", array);
		gjson.put("msgtype", "mpvideo");
		/*
		 * JSONObject news =new JSONObject(); JSONArray articles =new JSONArray();
		 * JSONObject list =new JSONObject(); list.put("title","title"); //标题
		 * list.put("description",message); //描述 list.put("url","http://");
		 * //点击图文链接跳转的地址 list.put("picurl","http://"); //图文链接的图片 articles.add(list);
		 * news.put("articles", articles);
		 */
		JSONObject mpvideo = new JSONObject();
		mpvideo.put("media_id", "usZgXcTN3d1U5wIHjjclJVOp4PcuFPLbUkUTTmH5fKk");
		gjson.put("mpvideo", mpvideo);
		/* gjson.put("news", news); */

		return gjson.toString();
	}

	/**
	 * 拼装图文信息
	 *
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String createGroupTw() {
		JSONObject news = new JSONObject();
		JSONArray articles = new JSONArray();
		JSONObject list = new JSONObject();
		list.put("title", "title"); // 标题
		list.put("thumb_media_id", UuidUtil.get32UUID()); // 描述
		list.put("author", "张三"); // 点击图文链接跳转的地址
		list.put("content_source_url", "www.qq.com"); // 图文链接的图片
		list.put("content", "这是条金鱼是一个非常简单的小测试"); // 图文链接的图片
		articles.add(list);
		JSONObject list1 = new JSONObject();
		list1.put("title", "title"); // 标题
		list1.put("thumb_media_id", UuidUtil.get32UUID()); // 描述
		list1.put("author", "李四"); // 点击图文链接跳转的地址
		list1.put("content_source_url", "www.qq.com"); // 图文链接的图片
		list1.put("content", "这是条金鱼111，这也是个非常简单的小测试"); // 图文链接的图片
		articles.add(list1);
		news.put("articles", articles);
		/* gjson.put("news", news); */

		return news.toString();
	}

	public static JSONArray getOpenids(String token) {
		JSONArray array = null;
		String urlstr = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		urlstr = urlstr.replace("ACCESS_TOKEN", token);
		urlstr = urlstr.replace("NEXT_OPENID", "");
		URL url;
		try {
			url = new URL(urlstr);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoInput(true);
			InputStream is = http.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// int size =is.available();
			// byte[] buf=new byte[size];
			// is.read(buf);
			// String resp =new String(buf,"UTF-8");

			JSONObject jsonObject = JSONObject.fromObject(sb.toString());
			log.info("getOpenids:" + jsonObject.toString());
			array = jsonObject.getJSONObject("data").getJSONArray("openid");
			return array;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return array;

		} catch (IOException e) {
			e.printStackTrace();
			return array;
		}
	}

	/**
	 * 发送图文
	 *
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String sendTwByOpenids(String token, String meauId) {
		String urlstr = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; // 群发图文消息
		// String urlstr
		// ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		// //发送客服图文消息
		urlstr = urlstr.replace("ACCESS_TOKEN", token);
		StringBuffer sb = new StringBuffer();
		// String reqjson =createGroupText(getOpenids(token),message);
		// sb.append(reqjson);
		String mes = createGroup(getOpenids(token), meauId);
		sb.append(mes);
		try {
			URL httpclient = new URL(urlstr);
			HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			OutputStream os = conn.getOutputStream();
			log.info("ccccc:" + sb);
			os.write(sb.toString().getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = conn.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message1 = new String(jsonBytes, "UTF-8");
			log.info("testsendTextByOpenids:" + message1);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 拼装视频
	 *
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static String createGroup(JSONArray array, String meauId) {
		JSONObject gjson = new JSONObject();
		// JSONObject json = getUserOpenids(array.get(3).toString());
		// //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
		gjson.put("touser", array);
		gjson.put("msgtype", "mpnews");
		/*
		 * JSONObject news =new JSONObject(); JSONArray articles =new JSONArray();
		 * JSONObject list =new JSONObject(); list.put("title","title"); //标题
		 * list.put("description",message); //描述 list.put("url","http://");
		 * //点击图文链接跳转的地址 list.put("picurl","http://"); //图文链接的图片 articles.add(list);
		 * news.put("articles", articles);
		 */
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", meauId);
		gjson.put("mpnews", mpnews);
		/* gjson.put("news", news); */

		return gjson.toString();
	}

	private static String create_nonce_str() {
		char[] numbersAndLetters = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
		Random randGen = new Random();
		char[] randBuffer = new char[16];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(61)];
		}
		return new String(randBuffer);
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static WechatUser getUserInfoByBgz(String token, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		WechatUser user = new WechatUser();
		try {
			Map<?, ?> retMap = null;
			ObjectMapper mapper = new ObjectMapper();
			String jsonObject = null;
			String requestUrl = url.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
			jsonObject = httpRequest(requestUrl, "GET", null);
			log.info("调用微信方法拿用户信息：" + jsonObject);
			retMap = mapper.readValue(jsonObject, Map.class);
			if (retMap.get("openid") != null) {
				user.setOPEN_ID(retMap.get("openid").toString());
				if (retMap.get("subscribe") != null) {
					user.setSTATE(retMap.get("subscribe").toString());
				} else {
					user.setSTATE("");
				}
				if (retMap.get("nickname") != null) {
					user.setNECK_NAME(retMap.get("nickname").toString());
				} else {
					user.setNECK_NAME("");
				}
				if (retMap.get("sex") != null) {
					user.setSEX(retMap.get("sex").toString());
				} else {
					user.setSEX("");
				}
				if (retMap.get("city") != null) {
					user.setCITY(retMap.get("city").toString());
				} else {
					user.setCITY("");
				}
				if (retMap.get("province") != null) {
					user.setPROVICNE(retMap.get("province").toString());
				} else {
					user.setPROVICNE("");
				}
				if (retMap.get("headimgurl") != null) {
					user.setIMGURL(retMap.get("headimgurl").toString());
				} else {
					user.setIMGURL("");
				}

				if (retMap.get("subscribe_time") == null) {
					user.setCREATE_TIME(String.valueOf(new Date().getTime()));
				} else {
					user.setCREATE_TIME(retMap.get("subscribe_time").toString());
				}
			}

		} catch (Exception e) {

			log.error("get user info exception", e);

		}

		return user;

	}

	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");
	private final static NumberFormat numberFormat = new DecimalFormat("0000");
	private static int seq = 0;
	private static final int MAX = 9999;

	public static synchronized String generateSequenceNo() {
		Calendar rightNow = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
		numberFormat.format(seq, sb, HELPER_POSITION);
		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}
		return sb.toString();
	}

}