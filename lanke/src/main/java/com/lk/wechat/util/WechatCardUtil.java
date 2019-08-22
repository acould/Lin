package com.lk.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lk.entity.system.Intenet;
import com.lk.service.system.card.CardManager;
import com.lk.util.PageData;
import com.lk.util.UuidUtil;
import com.mysql.fabric.xmlrpc.base.Array;


/**
 * 公众平台通用接口工具类
 *
 * @author lm
 */
public class WechatCardUtil {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(WechatCardUtil.class);

    /**
     * 创建卡卷
     *
     * @param org
     * @param token
     * @param pd
     * @return
     */
    public static JSONObject createCard(Intenet org, String token, PageData pd) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN"; // 创建优惠券
        urlStr = urlStr.replace("ACCESS_TOKEN", token);

        StringBuffer sb = new StringBuffer();
        JSONObject cardFirst = new JSONObject();

        JSONObject card = new JSONObject();
        card.put("card_type", pd.getString("CARD_TYPE")); //卡券类型：gift为兑换券
        JSONObject giftJSON = new JSONObject();

        JSONObject baseInfoJSON = new JSONObject();//卡券基本信息
        baseInfoJSON.put("logo_url", pd.getString("LOGO_URL"));//商户logo
        baseInfoJSON.put("brand_name", pd.getString("BRAND_NAME"));//商户名字
        baseInfoJSON.put("code_type", pd.getString("CODE_TYPE"));//显示类型：CODE_TYPE_QRCODE二维码
        baseInfoJSON.put("title", pd.getString("SUB_TITLE"));//卡券名 pd.getString("TITLE")
        baseInfoJSON.put("color", pd.getString("COLOR"));//卡券颜色

        baseInfoJSON.put("notice", pd.getString("NOTICE"));//卡券使用提醒
        baseInfoJSON.put("service_phone", org.getPHONE());
        baseInfoJSON.put("description", pd.getString("DESCRIPTION"));//卡券使用说明
        JSONObject dateInfoJSON = new JSONObject();
        dateInfoJSON.put("type", pd.getString("TYPE"));//使用时间类型：DATE_TYPE_FIX_TIME_RANGE：固定日期区间；DATE_TYPE_FIX_TERM：固定时长（自领取后按天算）
        if (pd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
            String dstr = pd.getString("BEGIN_TIMESTAMP");
            Date date = new Date(System.currentTimeMillis());
            try {
                date = sdf.parse(dstr);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            dateInfoJSON.put("begin_timestamp", date.getTime() / 1000);//启用时间（第一种类型专用），从1970年1月1日00:00:00至起用时间的秒数

            String dstrEnd = pd.getString("END_TIMESTAMP") + " 23:59:59";
            SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateEnd = new Date(System.currentTimeMillis());
            try {
                dateEnd = sdfEnd.parse(dstrEnd);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            Long end = dateEnd.getTime();
            dateInfoJSON.put("end_timestamp", end / 1000);//结束时间（第一种类型专用），从1970年1月1日00:00:00至起用时间的秒数
        } else if (pd.getString("TYPE").equals("DATE_TYPE_FIX_TERM")) {
            dateInfoJSON.put("fixed_term", Integer.decode(pd.getString("FIXED_TERM")));//自领取后多少天内有效（第二种类型专用），不支持0
            dateInfoJSON.put("fixed_begin_term", Integer.decode(pd.getString("FIXED_BEGIN_TERM")));//自领取后多少天开始生效（第二种类型专用），写0表示当天生效
        }
        baseInfoJSON.put("date_info", dateInfoJSON);//使用日期，有效期的信息
        JSONObject skuJSON = new JSONObject();
        skuJSON.put("quantity", pd.getString("QUANTITY"));//卡券库存数量：上限1亿
        baseInfoJSON.put("sku", skuJSON);//商品信息
        
        baseInfoJSON.put("can_give_friend", false);//卡券不可转增
        baseInfoJSON.put("get_limit", "1000");//设置每人可领券的数量限制1000，不填默认为50
        giftJSON.put("base_info", baseInfoJSON);
		
        /*
        JSONObject advancedInfoJSON = new JSONObject();//卡券高级信息（非必填）
        giftJSON.put("advanced_info", advancedInfoJSON);
        */

        String gift = pd.getString("DEFAULT_DETAIL");//优惠券详情
        giftJSON.put("gift", gift);
        card.put("gift", giftJSON);//兑换券内容

        cardFirst.put("card", card);
        sb.append(cardFirst);
        try {
            URL httpclient = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            System.out.println("1111:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 图文信息投放卡卷
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject putCard(Intenet org, PageData pd) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/card/mpnews/gethtml?access_token=TOKEN"; //图文信息群发
        String access_token = WeixinUtil.getAccessToken(org).getToken();
        urlStr = urlStr.replace("TOKEN", access_token);
        StringBuffer sb = new StringBuffer();
        JSONObject cardFirst = new JSONObject();
        cardFirst.put("card_id", pd.getString("CARD_ID"));
        sb.append(cardFirst);
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
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            System.out.println("1111:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;

    }


    /**
     * 根据关注用户投放全部关注用户卡劵
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject putQfCard(String org, PageData pd) {
        JSONObject resultJSON = null;
        String urlstr = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; //根据openid群发
        urlstr = urlstr.replace("ACCESS_TOKEN", org);

        String mes = createGroupCard(getOpenids(org), pd);
        StringBuffer sb = new StringBuffer();
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
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            System.out.println("1111:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     *
     * @param token
     * @return
     */
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
//	            int size =is.available();
//	            byte[] buf=new byte[size];
//	            is.read(buf);
//	            String resp =new String(buf,"UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(sb.toString());
            System.out.println("getOpenids:" + jsonObject.toString());
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
     * 根据关注用户投放卡劵
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     * @throws NoSuchAlgorithmException
     */
    public static JSONObject putGzCard(String token, String ticket, String openId, String timestamp, String signature, String cardId) throws NoSuchAlgorithmException {
        JSONObject resultJSON = null;
        String urlstr = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //根据openid发送
        urlstr = urlstr.replace("ACCESS_TOKEN", token);
        JSONObject gjson = new JSONObject();
        gjson.put("touser", openId);
        gjson.put("msgtype", "wxcard");
        JSONObject wxcard = new JSONObject();
        wxcard.put("card_id", cardId);
        JSONObject cardExt = new JSONObject();
        cardExt.put("code", "");
        cardExt.put("openid", openId);
        cardExt.put("timestamp", timestamp);

        if (StringUtils.isBlank(signature)) {
            String nonce_str = UuidUtil.get32UUID();
            String[] str = new String[]{ticket, timestamp, cardId, "", openId, nonce_str};
            Arrays.sort(str); // 字典序排序
            String bigStr = str[0] + str[1] + str[2];
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.update(bigStr.getBytes());
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
            String digest = hexString.toString();
            cardExt.put("signature", digest);
        } else {
            cardExt.put("signature", signature);
        }

        wxcard.put("card_ext", cardExt);
        gjson.put("wxcard", wxcard);
        String mes = gjson.toString();
        StringBuffer sb = new StringBuffer();
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
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            System.out.println("1111:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     *
     * @param array
     * @param pd
     * @return
     */
    public static String createGroupCard(JSONArray array, PageData pd) {
        JSONObject gjson = new JSONObject();
        gjson.put("touser", array);
        gjson.put("msgtype", "wxcard");
        JSONObject wxcard = new JSONObject();
        wxcard.put("card_id", pd.getString("CARD_ID"));
        gjson.put("wxcard", wxcard);
        /* gjson.put("news", news);*/

        return gjson.toString();
    }

    /**
     * 查询卡卷code方便（核销之前确认写卡券状态是否正常）
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject queryCard(String token, String code, String cardId) {
        String urlstr = "https://api.weixin.qq.com/card/code/get?access_token="+token;

        JSONObject params = new JSONObject();
        params.put("code", code); //卡券码
        params.put("card_id", cardId); //卡券id（自定义卡券必填）
        params.put("check_consume", true); //是否校验code核销状态,填入true和false时的code异常状态返回数据不同。

        return HttpUtil.wechatRequest(urlstr, "POST", params, "查询该卡券是否正常");
    }

    /**
     * 核销卡卷code
     *
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject cancelCard(String access_token, PageData pd) {
        JSONObject resultJSON = null;
        String urlStr = "https://api.weixin.qq.com/card/code/consume?access_token=TOKEN"; //核销优惠券
        urlStr = urlStr.replace("TOKEN", access_token);
        StringBuffer sb = new StringBuffer();
        JSONObject cardFirst = new JSONObject();
        cardFirst.put("code", pd.getString("CARD")); //标题
        sb.append(cardFirst);
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
            System.out.println("ccccc:" + sb);
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            System.out.println("1111:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;

    }

    /**
     * 更改卡券信息
     *
     * @param org
     * @param access_token
     * @param pd
     * @param pdState
     * @return
     * @throws Exception
     */
    public static JSONObject updateCard(Intenet org, String access_token, PageData pd, PageData pdState) throws Exception {
        String urlStr = "https://api.weixin.qq.com/card/update?access_token=TOKEN";
        urlStr = urlStr.replace("TOKEN", access_token);
        JSONObject resultJSON = new JSONObject();
        StringBuffer sb = new StringBuffer();
        JSONObject base_info = new JSONObject();//基本信息
        base_info.put("service_phone", org.getPHONE());//服务电话
        base_info.put("code_type", pd.getString("CODE_TYPE"));//显示类型：CODE_TYPE_QRCODE二维码
        //判断这些数据有没有改动，如果改动只提交需要重新提审的数据
        if (!pd.getString("NOTICE").equals(pdState.getString("NOTICE"))) {
            base_info.put("notice", pd.getString("NOTICE"));//用时向服务员出示此券	（需要重新提审）
        }
        if (!pd.getString("DESCRIPTION").equals(pdState.getString("DESCRIPTION"))) {
            base_info.put("description", pd.getString("DESCRIPTION"));//卡券描述	（需要重新提审）
        }
        if (!pd.getString("COLOR").equals(pdState.getString("COLOR"))) {
            base_info.put("color", pd.getString("COLOR"));//卡券颜色	（需要重新提审）
        }
        JSONObject dateInfoJSON = new JSONObject();
        dateInfoJSON.put("type", pd.getString("TYPE"));//使用时间类型：DATE_TYPE_FIX_TIME_RANGE：固定日期区间；DATE_TYPE_FIX_TERM：固定时长（自领取后按天算）
        if (pd.getString("TYPE").equals("DATE_TYPE_FIX_TIME_RANGE")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
            String dstr = pd.getString("BEGIN_TIMESTAMP");
            Date date = new Date(System.currentTimeMillis());
            try {
                date = sdf.parse(dstr);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            dateInfoJSON.put("begin_timestamp", date.getTime() / 1000);//启用时间（第一种类型专用），从1970年1月1日00:00:00至起用时间的秒数
            String dstrEnd = pd.getString("END_TIMESTAMP") + " 23:59:59";
            SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateEnd = new Date(System.currentTimeMillis());
            try {
                dateEnd = sdfEnd.parse(dstrEnd);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            Long end = dateEnd.getTime();
            dateInfoJSON.put("end_timestamp", end / 1000);//结束时间（第一种类型专用），从1970年1月1日00:00:00至起用时间的秒数
        }
        //不支持DATE_TYPE_FIX_TERM类型的修改
			/*else if(pd.getString("TYPE").equals("DATE_TYPE_FIX_TERM")){
				dateInfoJSON.put("fixed_term", Integer.decode(pd.getString("FIXED_TERM")));//自领取后多少天内有效（第二种类型专用），不支持0
				dateInfoJSON.put("fixed_begin_term", Integer.decode(pd.getString("FIXED_BEGIN_TERM")));//自领取后多少天开始生效（第二种类型专用），写0表示当天生效
			}*/
        base_info.put("date_info", dateInfoJSON);//使用日期，有效期的信息
        JSONObject gift = new JSONObject();
        gift.put("base_info", base_info);
        JSONObject result = new JSONObject();
        result.put("card_id", pd.getString("CARD_ID"));
        result.put("gift", gift);
        sb.append(result);
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
            System.out.println("更改卡券信息" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 查看卡券详情
     *
     * @param access_token
     * @param card_id
     * @return
     */
    public static JSONObject getCardParticulars(String access_token, String card_id) {
        String urlstr = "https://api.weixin.qq.com/card/get?access_token=TOKEN";
        urlstr = urlstr.replace("TOKEN", access_token);
        StringBuffer sb = new StringBuffer();
        JSONObject resultJSON = null;
        JSONObject json = new JSONObject();
        json.put("card_id", card_id);
        sb.append(json);
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
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8");
            System.out.println("获取卡券详情的返回码：" + message1);
            resultJSON = JSONObject.fromObject(message1);
	            /*//查看提审状态
	            JSONObject card = resultJSON.getJSONObject("card");
	            JSONObject discount = card.getJSONObject("gift");
	            JSONObject base_info = discount.getJSONObject("base_info");
	            String status = base_info.getString("status");
	            //String status = resultJSON.getJSONObject("card").getJSONObject("gift").getJSONObject("base_info").getString("status");
	            System.out.println("状态："+status);
	            if(status.equals("CARD_STATUS_NOT_VERIFY")){
	            	System.out.println("待审核");
	            }else if(status.equals("CARD_STATUS_VERIFY_FAIL")){
	            	System.out.println("审核失败");
	            }else if(status.equals("CARD_STATUS_VERIFY_OK")){
	            	System.out.println("通过审核");
	            }else if(status.equals("CARD_STATUS_DELETE")){
	            	System.out.println("卡券被商户删除");
	            }else if(status.equals("CARD_STATUS_DISPATCH")){
	            	System.out.println("在公众平台投放过的卡券");
	            }*/
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 修改库存需重新调用接口
     *
     * @return
     */
    public static JSONObject modifystock(String access_token, String CARD_ID, int oldQUANTITY, int newQUANTITY) {
        String urlStr = "https://api.weixin.qq.com/card/modifystock?access_token=TOKEN";
        urlStr = urlStr.replace("TOKEN", access_token);
        StringBuffer sb = new StringBuffer();
        JSONObject resultJSON = null;
        JSONObject json = new JSONObject();
        json.put("card_id", CARD_ID);
        if (oldQUANTITY > newQUANTITY) {
            json.put("reduce_stock_value", oldQUANTITY - newQUANTITY);//int 减少多少库存，可以不填或填0。
        } else {
            json.put("increase_stock_value", newQUANTITY - oldQUANTITY);//int 增加多少库存，支持不填或填0。
        }
        sb.append(json);
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
            System.out.println("修改卡券库存详情码：" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

}