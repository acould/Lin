package com.lk.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lk.entity.system.Intenet;
import com.lk.util.PublicManagerUtil;


/**
 * 公众平台通用接口工具类
 *
 * @author lm
 */
public class WechatMenuUtil {
    private static Logger log = LoggerFactory.getLogger(WechatMenuUtil.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 公众号菜单接口
     *
     * @param org
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createMenu(Intenet org, String token) throws UnsupportedEncodingException {
        String urlStr = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"; //菜单创建
        //String urlstr ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //发送客服图文消息
        urlStr = urlStr.replace("ACCESS_TOKEN", token);
        StringBuffer sb = new StringBuffer();
        JSONObject MenuButton = new JSONObject();
        JSONArray MenuList = new JSONArray();

        //第一个菜单
        JSONObject firstButton = new JSONObject();
        JSONArray firstMenuList = new JSONArray();
        /* JSONObject listFmOne =new JSONObject();
         listFmOne.put("type","view"); //标题
         listFmOne.put("name","优惠充值"); //描述
         listFmOne.put("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+org.getWECHAT_ID()+"&redirect_uri="+PublicManagerUtil.URL1+"intenetmumber/recharge.do?intenetId="+org.getINTENET_ID()+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect"); //点击图文链接跳转的地址
         firstMenuList.add(listFmOne);*/
        //1-1
        JSONObject listFmTow = new JSONObject();
        listFmTow.put("type", "view"); //标题
        listFmTow.put("name", "签到有礼"); //描述
        String url = PublicManagerUtil.URL1+"intenetmumber/singIn.do?intenetId=" + org.getINTENET_ID() + "";
        url = URLEncoder.encode(url, "utf-8");
        listFmTow.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + org.getWECHAT_ID() + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=1&component_appid="+PublicManagerUtil.APPID+"#wechat_redirect"); //点击图文链接跳转的地址
        firstMenuList.add(listFmTow);
         
       /*  JSONObject listFmThree =new JSONObject();
         listFmThree.put("type","view"); //标题
         listFmThree.put("name","抢先订座"); //描述
         listFmThree.put("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+org.getWECHAT_ID()+"&redirect_uri=PublicManagerUtil.URL1+"intenetmumber/book.do?intenetId="+org.getINTENET_ID()+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect"); //点击图文链接跳转的地址
         firstMenuList.add(listFmThree);*/


        firstButton.put("name", "赢积分");
        firstButton.put("sub_button", firstMenuList);
        MenuList.add(firstButton);

        //第二个菜单
        JSONObject secondButton = new JSONObject();
        secondButton.put("type", "view"); //标题
        secondButton.put("name", "抢福利");
        url = PublicManagerUtil.URL1+"intenetmumber/index.do?intenetId=" + org.getINTENET_ID() + "";
        url = URLEncoder.encode(url, "utf-8");
        secondButton.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + org.getWECHAT_ID() + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=1&component_appid="+PublicManagerUtil.APPID+"#wechat_redirect");
        MenuList.add(secondButton);

        //第三个菜单
        JSONObject thirdButton = new JSONObject();
        JSONArray thirdMenuList = new JSONArray();
        //3-1
        JSONObject third3_1 = new JSONObject();
        third3_1.put("type", "view");
        third3_1.put("name", "微官网");
        url = PublicManagerUtil.URL1+"intenetmumber/goToMiniWeb.do?intenetId=" + org.getINTENET_ID() + "";
        url = URLEncoder.encode(url, "utf-8");
        third3_1.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + org.getWECHAT_ID() + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=1&component_appid="+PublicManagerUtil.APPID+"#wechat_redirect");
        thirdMenuList.add(third3_1);
        //3-2
        JSONObject third3_2 = new JSONObject();
        third3_2.put("type", "view");
        third3_2.put("name", "找门店");
        url = PublicManagerUtil.URL1+"intenetmumber/introduction.do?intenetId=" + org.getINTENET_ID() + "";
        url = URLEncoder.encode(url, "utf-8");
        third3_2.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + org.getWECHAT_ID() + "&redirect_uri=" + url + "&response_type=code&scope=snsapi_base&state=1&component_appid="+PublicManagerUtil.APPID+"#wechat_redirect");
        thirdMenuList.add(third3_2);

        thirdButton.put("name", "看网吧");
        thirdButton.put("sub_button", thirdMenuList);
        MenuList.add(thirdButton);

        MenuButton.put("button", MenuList);
        sb.append(MenuButton);
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
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            log.info("createMenu创建菜单接口的返回码:" + message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    /**
     * 微信公众号菜单删除
     * @param org
     * @param token
     * @return
     */
    public static String deleteMenu(Intenet org, String token) {
        String urlStr = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN"; //菜单删除
        urlStr = urlStr.replace("ACCESS_TOKEN", token);
        StringBuffer sb = new StringBuffer();
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
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            log.info("deleteMenu删除菜单接口的返回码:" + message1);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

}