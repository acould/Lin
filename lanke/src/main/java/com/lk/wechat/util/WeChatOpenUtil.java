package com.lk.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 公众平台通用接口工具类
 *
 * @author lm
 */
public class WeChatOpenUtil {
    private static Logger log = LoggerFactory.getLogger(WechatMenuUtil.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /**
     * 1，获取第三方平台access_token   https://api.weixin.qq.com/cgi-bin/component/api_component_token
     * 2，获取预授权码
     *
     */


    /**
     * 1.获取第三方平台component_access_token
     *
     * @param appId     开发者ID
     * @param appSecret 开发者密码
     * @param ticket    微信后台推送的ticket
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)  component_access_token && expires_in
     */
    public static JSONObject getComponent(String appId, String appSecret, String ticket) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", appId);
        gjson.put("component_appsecret", appSecret);
        gjson.put("component_verify_ticket", ticket);
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
            message1 = message1.replaceAll("[\\\\]", "");
            log.info("获取第三方平台component_access_token的返回码:" + message1);
            resultJSON = JSONObject.fromObject(message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 2.获取预授权码pre_auth_code
     *
     * @param componentAppId       第三方平台方appid
     * @param componentAccessToken 第三方平台access_token
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)   pre_auth_code(预授权码) &&  expires_in(有效期，为10分钟)
     */
    public static JSONObject getPreAuthCode(String componentAppId, String componentAccessToken) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=" + componentAccessToken;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppId);
        try {
            URL httpclient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
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
            message1 = message1.replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            System.out.println("获取预授权返回码=="+resultJSON);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 3.使用授权码换取公众号或小程序的接口调用凭据和授权信息
     *
     * @param componentAppid         第三方平台appid
     * @param authorization_code     授权code,会在授权成功时返回给第三方平台
     * @param component_access_token 第三方平台access_token
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject getApiQueryAuth(String componentAppid, String authorization_code, String component_access_token) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=" + component_access_token;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppid);
        gjson.put("authorization_code", authorization_code);
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
            message1 = message1.replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            System.out.println("使用预授权码，获取微信令牌等信息==="+resultJSON);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 4、获取（刷新）授权公众号或小程序的接口调用凭据（令牌）
     *
     * @param componentAppid         第三方平台appid
     * @param authorizerAppid        授权方appid
     * @param componentAccessToken   第三方平台access_token
     * @param authorizerRefreshToken 授权方的刷新令牌
     * @return
     */
    public static JSONObject getAuthorizeRefresh(String componentAppid, String authorizerAppid, String componentAccessToken, String authorizerRefreshToken) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=" + componentAccessToken;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppid);
        gjson.put("authorizer_appid", authorizerAppid);
        gjson.put("authorizer_refresh_token", authorizerRefreshToken);
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
            message1 = message1.replaceAll("[\\\\]", "");
            log.info("获取第三方平台authorizer_access_token的返回码:" + message1);
            resultJSON = JSONObject.fromObject(message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 获取授权方的帐号基本信息 微信公众号的
     *
     * @param componentAppid       第三方平台appid
     * @param authorizerAppid      授权方appid
     * @param componentAccessToken 第三方平台access_token
     * @return
     */
    public static JSONObject getAuthorizerInfo(String componentAppid, String authorizerAppid, String componentAccessToken) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=" + componentAccessToken;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppid);
        gjson.put("authorizer_appid", authorizerAppid);
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
            message1 = message1.replaceAll("[\\\\]", "");
            log.info("getAuthorizerInfo--获取授权方 的基本信息:" + message1);
            resultJSON = JSONObject.fromObject(message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 获取授权方的帐号基本信息 小程序
     *
     * @param componentAppid       第三方平台appid
     * @param authorizerAppid      授权方appid
     * @param componentAccessToken 第三方平台access_token
     * @return
     */
    public static JSONObject getAuthorizerXcxInfo(String componentAppid, String authorizerAppid, String componentAccessToken) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=" + componentAccessToken;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppid);
        gjson.put("authorizer_appid", authorizerAppid);
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
            message1 = message1.replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 获取授权方的选项设置信息
     *
     * @param componentAppid       第三方平台appid
     * @param authorizerAppid      授权方appid
     * @param optionName           选项名称
     * @param componentAccessToken 第三方平台access_token
     * @return
     */
    public static JSONObject getAuthorizerOption(String componentAppid, String authorizerAppid, String optionName, String componentAccessToken) {
        JSONObject resultJSON = null;
        String urlstr = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_option?component_access_token=" + componentAccessToken;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppid);
        gjson.put("authorizer_appid", authorizerAppid);
        gjson.put("option_name", optionName);
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
            os.write(gjson.toString().getBytes("UTF-8"));//传入参数    
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            message1 = message1.replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 设置授权方的选项信息
     *
     * @param componentAppid            第三方平台appid
     * @param authorizerAppid           授权方appid
     * @param optionName                选项名称
     * @param optionValue               设置的选项值
     * @param componentAccessToken      第三方平台access_token
     * @return
     */
    public static JSONObject getSetAuthorizerOption(String componentAppid, String authorizerAppid, String optionName, String optionValue, String componentAccessToken) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/cgi-bin/component/api_set_authorizer_option?component_access_token=" + componentAccessToken;
        JSONObject gjson = new JSONObject();
        gjson.put("component_appid", componentAppid);
        gjson.put("authorizer_appid", authorizerAppid);
        gjson.put("option_name", optionName);
        gjson.put("option_value", optionValue);
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
            message1 = message1.replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 使用新浪短链接接口
     */
    public static JSONObject getShotUrl(String url) {
        JSONObject resultJSON = null;
        String urlStr = "http://api.t.sina.com.cn/short_url/shorten.json?source=3271760578&url_long=" + url;
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
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            String mess = message1.substring(1);
            String msg = mess.substring(0, mess.length() - 1);
            resultJSON = JSONObject.fromObject(msg);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 通过网页授权，获取用户的open_id和公众号的token
     * @param appid 公众号的id
     * @param code 用户的网页授权码
     * @param componentAppid 开放平台的id
     * @param componentAccessToken 开放平台的token
     * @return access_token，expires_in，refresh_token，openid，scope（json对象）
     */
    public static JSONObject getOpenAccessToken(String appid, String code, String componentAppid, String componentAccessToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=" + appid + ""
                + "&code=" + code + ""
                + "&grant_type=authorization_code"
                + "&component_appid=" + componentAppid + ""
                + "&component_access_token=" + componentAccessToken + "";

        String resultValue = HttpUrlUtil.openUrl(url, "UTF-8");
        if (StringUtils.isNotBlank(resultValue)) {
            log.info("获取用户的open_id和公众号的token:" + resultValue);
            JSONObject jsonObject = JSONObject.fromObject(resultValue);
            return jsonObject;
        } else {
            return null;
        }
    }

}