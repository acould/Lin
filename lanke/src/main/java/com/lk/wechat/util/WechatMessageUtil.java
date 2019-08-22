package com.lk.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.functions.FuncQname;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lk.util.PageData;
import com.lk.wechat.aes.AesException;


/**
 * 公众平台通用接口工具类
 *
 * @author lm
 */
public class WechatMessageUtil {
    private static Logger log = LoggerFactory.getLogger(WechatMenuUtil.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 公众号菜单接口
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     * @throws AesException
     */
    public static String SendMessage(String access_token, String openId, String content) throws AesException {

        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //菜单创建
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("msgtype", "text");
        JSONObject news = new JSONObject();
        news.put("content", content);
        gjson.put("text", news);

        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(gjson.toString().getBytes("UTF-8"));//传入参数    
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            log.info("客服接口发送文本信息返回码:" + message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    
    public static String SendMessage( String access_token,String angetId,String timestamp,String nonce,String openId,String content) throws AesException{
   	 
 	   String urlstr ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //菜单创建
//        String urlstr ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //发送客服图文消息
          urlstr =urlstr.replace("ACCESS_TOKEN", access_token);
        /*  JSONObject gjson =new JSONObject();
          //JSONObject json = getUserOpenids(array.get(3).toString()); //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
          gjson.put("touser", openId);
          gjson.put("msgtype", "text");
          JSONObject news =new JSONObject();
          news.put("content", content);
          gjson.put("text", news);*/
          
        
         	/*String xml1= generate( openId,  angetId,  timestamp,  "text", content);
         	System.out.print(xml1);*/
         try {
         	
             URL httpclient =new URL(urlstr);
             HttpURLConnection conn =(HttpURLConnection) httpclient.openConnection();
             conn.setConnectTimeout(5000);
             conn.setReadTimeout(2000);
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
             conn.setDoOutput(true);        
             conn.setDoInput(true);
             conn.connect();
             OutputStream os= conn.getOutputStream();    
             os.write(content.getBytes("UTF-8"));//传入参数    
             os.flush();
             os.close();
             
             InputStream is =conn.getInputStream();
             int size =is.available();
             byte[] jsonBytes =new byte[size];
             is.read(jsonBytes);
             String message1=new String(jsonBytes,"UTF-8");
             log.info("客服接口发送文本信息返回码:"+message1);
             conn.disconnect();
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
 		return ""; 
      
    }
    /**
     * 发送图文
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject sendTwByOpenIds(String token, String menuId) {
        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; //群发图文消息
        JSONObject resultJSON = null;
        urlStr = urlStr.replace("ACCESS_TOKEN", token);
        StringBuffer sb = new StringBuffer();
        String mes = createGroup(getOpenids(token), menuId);
        sb.append(mes);
        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            log.info("发送图文返回码:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 拼装视频
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static String createGroup(JSONArray array, String meauId) {
        JSONObject gjson = new JSONObject();
        //JSONObject json = getUserOpenids(array.get(3).toString()); //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
        gjson.put("touser", array);
        gjson.put("msgtype", "mpnews");
        gjson.put("send_ignore_reprint", 0);
	    /*  JSONObject news =new JSONObject();
	      JSONArray articles =new JSONArray();
	      JSONObject list =new JSONObject();
	      list.put("title","title"); //标题
	      list.put("description",message); //描述
	      list.put("url","http://"); //点击图文链接跳转的地址
	      list.put("picurl","http://"); //图文链接的图片
	      articles.add(list);
	      news.put("articles", articles);*/
        JSONObject mpnews = new JSONObject();
        mpnews.put("media_id", meauId);
        gjson.put("mpnews", mpnews);

        return gjson.toString();
    }

    /**
     * @param token
     * @return
     */
    public static JSONArray getOpenids(String token) {
        JSONArray array = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        urlStr = urlStr.replace("ACCESS_TOKEN", token);
        urlStr = urlStr.replace("NEXT_OPENID", "");
        URL url;
        try {
            url = new URL(urlStr);
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
//	        int size =is.available();
//	        byte[] buf=new byte[size];
//	        is.read(buf);
//	        String resp =new String(buf,"UTF-8");
            log.info("获取关注用户列表返回码:" + sb.toString());
            JSONObject jsonObject = JSONObject.fromObject(sb.toString());
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
     * @param access_token
     * @param openid
     * @param list
     * @return
     */
    public static JSONObject sendArticles(String access_token, String openid, List<PageData> list) {
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; // 发送客服图文消息
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        JSONObject gjson = new JSONObject();
        gjson.put("touser", openid);
        gjson.put("msgtype", "news");

        List<JSONObject> articleList = new ArrayList<JSONObject>();
        for (int i = 0; i < list.size(); i++) {
            JSONObject articleObj = new JSONObject();
            articleObj.put("title", list.get(i).getString("TITLE"));
            articleObj.put("description", list.get(i).getString("DESCRIPTION"));
            articleObj.put("url", list.get(i).getString("URL"));
            articleObj.put("picurl", list.get(i).getString("PIC_URL"));
            articleList.add(articleObj);
        }
        JSONObject mpnews = new JSONObject();
        mpnews.put("articles", articleList);
        gjson.put("news", mpnews);

        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(gjson.toString().getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            resultJSON = JSONObject.fromObject(message1);
            log.info("客服接口发送多条永久图文素材返回码:" + message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     * 客服接口发送消息
     *
     * @param access_token
     * @param openid
     * @param type
     * @param jsonType
     * @return
     */
    public static JSONObject sendMessageByCustom(String access_token, String openid, String type, JSONObject jsonType) {
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        JSONObject gjson = new JSONObject();
        gjson.put("touser", openid);
        gjson.put("msgtype", type);
        gjson.put(type, jsonType);
        resultJSON = HttpUtil.wechatRequest(urlStr, "POST", gjson, "客服接口发送" + type + "类型返回码:");
        return resultJSON;
    }


    /**
     * 模板信息
     * 获取公众号所属行业
     */
    public static JSONObject getIndustry(String access_token) {
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN"; // 获取设置的行业信息
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            resultJSON = JSONObject.fromObject(message1);
            log.info("获取已设置的行业信息---返回码:" + message1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     * 模板信息
     * 设置公众号所属行业
     */
    public static JSONObject setIndustry(String access_token, String industryId1, String industryId2) {
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        JSONObject jsonPram = new JSONObject();
        jsonPram.put("industry_id1", industryId1);
        jsonPram.put("industry_id2", industryId2);

        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonPram.toString().getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            resultJSON = JSONObject.fromObject(message1);
            log.info("设置行业信息---返回码:" + message1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     * 模板信息
     * 获取所有已添加到公众号的模板列表
     */
    public static JSONObject getAllTemplateMsg(String access_token) {
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
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
//				int size = is.available();
//				byte[] jsonBytes = new byte[size];
//				is.read(jsonBytes);
//				String message1 = new String(jsonBytes, "UTF-8");
            resultJSON = JSONObject.fromObject(sb.toString());
            log.info("获取模板列表---返回码:" + sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;

    }


    /**
     * 模板信息
     * 发送模板消息
     */
    public static JSONObject sendTemplateMsg(String access_token, String openId, String templateId, String url, JSONObject miniProgram, JSONObject data) {
        JSONObject resultJSON = new JSONObject();
        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        JSONObject jsonPram = new JSONObject();
        jsonPram.put("touser", openId);
        jsonPram.put("template_id", templateId);
        if (url != null) {
            jsonPram.put("url", url);
        }
        if (miniProgram != null) {
            jsonPram.put("miniprogram", miniProgram);
        }
        jsonPram.put("data", data);

        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonPram.toString().getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            resultJSON = JSONObject.fromObject(message1);
            log.info("发送模板消息---返回码:" + message1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    public static String json2XML(String json) {
        JSONObject jobj = JSONObject.fromObject(json);
        String xml = new XMLSerializer().write(jobj);
        return xml;
    }

    public static String generate(String openId, String appId, long timestamp, String type, String content) {

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[" + openId + "]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[" + appId + "]]></FromUserName>");
        sb.append("<CreateTime>" + timestamp + "</CreateTime>");
        sb.append("<MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[" + content + "]]></Content>");
        sb.append("</xml>");
        String replyMsg = sb.toString();
			/* String  xml = "<xml>\n"+
	    	 "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"+
	    	 "<FromUserName><![CDATA[%2$s]]</FromUserName>\n"+
	    	 "<CreateTime>%3$s</CreateTime>\n"+
	    	 "<MsgType><![CDATA[%4$s]]</MsgType>\n"+
	    	 "<Content><![CDATA[%5$s]]</Content>\n"+
	    	 "<FuncFlag>1</FuncFlag>\n"+
	    	 "</xml>";*/
        return replyMsg;

    }

    /**
     * 预览接口:
     * 图文：mpnews；	文本：text；	语音：voice	图片：image	视频：mpvideo	卡券：wxcard
     * "card_id":"123dsdajkasd231jhksad",
     * "card_ext": "{\"code\":\"\",\"openid\":\"\",\"timestamp\":\"1402057159\",\"signature\":\"017bb17407c8e0058a66d72dcc61632b70f511ad\"}"
     *
     * @param access_token
     * @return
     */
    public static JSONObject previewNews(String access_token, String openId, String msgType, String param) {
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", access_token);

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("touser", openId);
        jsonParam.put("msgtype", msgType);
        JSONObject json = new JSONObject();
        if (msgType.equals("mpnews") || msgType.equals("voice") || msgType.equals("image") || msgType.equals("mpvideo")) {
            json.put("media_id", param);
        } else if (msgType.equals("text")) {
            json.put("content", param);
        } else if (msgType.equals("wxcard")) {
            JSONObject obj = new JSONObject();
            String p[] = param.split(";");
            obj.put("card_id", p[0]);
            obj.put("card_ext", p[1]);
        }

        jsonParam.put(msgType, json);
        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            resultJSON = JSONObject.fromObject(message1);
            log.info("预览接口---返回码:" + message1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     * 根据openId数组来群发图文
     *
     * @param token
     * @param mediaId
     * @return
     */
    public static JSONObject sendNewsByOpenIdArray(String token, String mediaId, JSONArray array) {
        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", token);
        StringBuffer sb = new StringBuffer();
        JSONObject resultJSON = new JSONObject();

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("touser", array);
        jsonParam.put("msgtype", "mpnews");
        jsonParam.put("send_ignore_reprint", 1);
        JSONObject mpnews = new JSONObject();
        mpnews.put("media_id", mediaId);
        jsonParam.put("mpnews", mpnews);
        sb.append(jsonParam.toString());
        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            log.info("发送图文返回码:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }


    public static JSONObject sendNewsByTag(String token, String type, PageData pd){
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", token);

        JSONObject param = new JSONObject();
            JSONObject filter = new JSONObject();
            filter.put("is_to_all", true);//默认发给所有人
//            filter.put("is_to_all", false);//发给带标签的人
//            filter.put("tag_id", 100);//

        param.put("filter", filter);
            JSONObject typeJSON = new JSONObject();
            if(type.equals("mpnews")){
                typeJSON.put("media_id", pd.getString("media_id"));//图文
            }else if(type.equals("text")){
                typeJSON.put("media_id", pd.getString("content"));//文本
            }else if(type.equals("voice")){
                typeJSON.put("media_id", pd.getString("media_id"));//语音/音频
            }else if(type.equals("voice")){
                typeJSON.put("media_id", pd.getString("media_id"));//语音/音频
            }else if(type.equals("mpvideo")){
                typeJSON.put("media_id", pd.getString("media_id"));//视频（注意上传视频接口时特殊的）
            }else if(type.equals("wxcard")){
                typeJSON.put("card_id", pd.getString("card_id"));//视频
            }
        param.put(type, typeJSON);
        param.put("msgtype", type);

        resultJSON = HttpUtil.wechatRequest(urlStr, "POST", param, "根据标签进行群发，群发类型==="+type);
        return resultJSON;
    }



    /**
     * 将长连接转为短连接
     * @param token
     * @param longUrl
     * @return
     */
    public static JSONObject longToShort(String token, String longUrl) {
        String urlStr = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", token);
        StringBuffer sb = new StringBuffer();
        JSONObject resultJSON = new JSONObject();

        JSONObject jsonParam = new JSONObject();

        jsonParam.put("action", "long2short");
        jsonParam.put("long_url", longUrl);

        sb.append(jsonParam.toString());
        try {
            URL httpClient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            log.info("长连接转短连接的返回码===:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }


    /**
     * 创建标签
     * @return
     */
    public static JSONObject createTag(String token, String name){
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", token);

        JSONObject param = new JSONObject();
        JSONObject tag = new JSONObject();
        tag.put("name", name);
        param.put("tag", tag);

        resultJSON = HttpUtil.wechatRequest(urlStr, "POST", param, "创建标签===");
        return resultJSON;
    }


    /**
     * 给用户打标签
     * @param token
     * @param tagid 标签id
     * @param list openid列表
     * @return
     */
    public static JSONObject setTag(String token, String tagid, List<String> list){
        JSONObject resultJSON = new JSONObject();

        String urlStr = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
        urlStr = urlStr.replace("ACCESS_TOKEN", token);

        JSONObject param = new JSONObject();
        param.put("openid_list", list);
        param.put("tagid", tagid);

        resultJSON = HttpUtil.wechatRequest(urlStr, "POST", param, "批量为用户打标签===");
        return resultJSON;
    }


}