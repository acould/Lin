package com.lk.controller.unionPay;


/**
 * Created by faliny on 2017/8/31.
 */

import static com.lk.util.unionPay.Util.makeSign;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lk.entity.unionPay.Goods;
import com.lk.util.unionPay.NotifyUtilTest;
import com.lk.util.unionPay.Util;
//@Controller
//@RequestMapping(value = "/publicpay")
public class PublicPayDemo {

//    //读取资源配置参数
//	    private static ResourceBundle emayConfig = ResourceBundle.getBundle("publicpayParams");
//	    public static String APIurl = emayConfig.getString("shurl"); // 软件序列号,请通过亿美销售人员获取
//	    public static String mid = emayConfig.getString("mid");// 密码,请通过亿美销售人员获取
//	    public static String tid = emayConfig.getString("tid"); // 序列号首次激活时自己设定
//	    public static String instMid = emayConfig.getString("instMid"); // 软件序列号,请通过亿美销售人员获取
//	    public static String msgSrc = emayConfig.getString("msgSrc");// 密码,请通过亿美销售人员获取
//	    public static String md5Key = emayConfig.getString("key"); // 序列号首次激活时自己设定
//	    public static String msgType_refund = emayConfig.getString("msgType_refund"); // 软件序列号,请通过亿美销售人员获取
//	    public static String msgType_secureCancel = emayConfig.getString("msgType_secureCancel");// 密码,请通过亿美销售人员获取
//	    public static String msgType_secureComplete = emayConfig.getString("msgType_secureComplete"); // 序列号首次激活时自己设定
//	    
//	    public static String msgType_close = emayConfig.getString("msgType_close"); // 软件序列号,请通过亿美销售人员获取
//	    public static String msgType_query = emayConfig.getString("msgType_query");// 密码,请通过亿美销售人员获取
//   /* @Value("${shurl}")
//    private String APIurl;
//    @Value("${mid}")
//    private String mid;
//    @Value("${tid}")
//    private String tid;
//    @Value("${instMid}")
//    private String instMid;
//    @Value("${msgSrc}")
//    private String msgSrc;
//    @Value("${key}")
//    private String md5Key;
//
//    @Value("${msgType_refund}")
//    private String msgType_refund;
//
//    @Value("${msgType_secureCancel}")
//    private String msgType_secureCancel;
//
//    @Value("${msgType_secureComplete}")
//    private String msgType_secureComplete;
//
//    @Value("${msgType_close}")
//    private String msgType_close;
//
//    @Value("${msgType_query}")
//    private String msgType_query;
//*/
//    final private static String msgType = "WXPay.jsPay";
//    final private static String msgSrcId = "3194";
//    final private static String apiUrl_makeOrder = "https://qr-test2.chinaums.com/netpay-portal/webpay/pay.do";
//    final private static String notifyUrl = "http://172.27.49.240:8080/publicpay/notifyUrl.do";
//    final private static String returnUrl = "http://172.27.49.240:8080/publicpay/returnUrl.do";
//
//
//    //跳转交易查询页面
//    @RequestMapping(value = "/toOrderQueryPage.do",method = RequestMethod.GET)
//    public String toBillQuery(){
//        return "/WEB-INF/views/jsp/orderQuery.jsp";
//    }
//
//    //跳转交易退款页面
//    @RequestMapping(value = "/toRefundPage.do",method = RequestMethod.GET)
//    public String toRefund(){
//        return "/WEB-INF/views/jsp/refund.jsp";
//    }
//
//    //跳转担保撤销页面
//    @RequestMapping(value = "/toCancelPage.do",method = RequestMethod.GET)
//    public String toCancel(){
//        return "/WEB-INF/views/jsp/secureCancel.jsp";
//    }
//
//    //跳转担保完成页面
//    @RequestMapping(value = "/toSecureCompletePage.do",method = RequestMethod.GET)
//    public String toSecureCompletePage(){
//        return "/WEB-INF/views/jsp/secureComplete.jsp";
//    }
//
//    //跳转担保完成页面
//    @RequestMapping(value = "/toClosePage.do",method = RequestMethod.GET)
//    public String toClosePage(){
//        return "/WEB-INF/views/jsp/close.jsp";
//    }
//
//    /**
//     * 接收并处理下单请求
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping(value = "/publicpay.do", method = RequestMethod.GET)
//    public String publicPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // 准备商品信息
//        List<Goods> goodsList = new ArrayList<Goods>();
//        goodsList.add(new Goods("0001", "Goods Name", 1L, 100L, "Auto", "goods body",null,null));
//        goodsList.add(new Goods("0002", "Goods Name", 2L, 200L, "Auto", "goods body",null,null));
//
//        // 组织请求报文，具体参数请参照接口文档，以下仅作模拟
//        JSONObject json = new JSONObject();
//        json.put("mid", mid);
//        json.put("tid", tid);
//        json.put("msgType", msgType);
//        json.put("msgSrc", msgSrc);
//        json.put("instMid", instMid);
//
//        json.put("merOrderId", Util.genMerOrderId(msgSrcId));
////        json.put("sceneType", "AND_WAP");
////        json.put("merAppName", "全民付");
////        json.put("merAppId", "http://www.chinaums.com");
//        json.put("totalAmount", 1);
//        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        json.put("goods", goodsList);
//        json.put("notifyUrl", notifyUrl);
//        json.put("returnUrl", returnUrl);
//
//        json.put("secureTransaction", true);
//
//        String url = Util.makeOrderRequest(json, md5Key, apiUrl_makeOrder);
//        response.sendRedirect(url);
//        return null;
//    }
//
//
//    /**
//     * 接收通知的方法
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping(value = "/notifyUrl.do", method = RequestMethod.POST)
//    public void notifyHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        Map<String, String> params = Util.getRequestParams(request);
//        System.out.println("params："+params);
//
//        // 验签
//        boolean checkRet = NotifyUtilTest.checkSign2(md5Key, params);
//
//        if (checkRet) {
//            //更新你们的订单
//        }
//
//        // 收到通知后记得返回SUCCESS
//        PrintWriter writer = response.getWriter();
//        writer.print("SUCCESS");
//        writer.flush();
//    }
//
//    /**
//     * 回跳地址
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping(value = "/returnUrl.do", method = RequestMethod.GET)
//    public String goReturnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        Map<String, String> params = Util.getRequestParams(request);
//        System.out.println("params:"+params);
//        // 验签
//
//        boolean checkRet = NotifyUtilTest.checkSign2(md5Key, params);
//
//        if (checkRet) {
//            //跳转到你们自己的商城页面
//            return "/WEB-INF/views/yourself.jsp";
//        }
//        return "/WEB-INF/views/yourselfErrorSign.jsp";
//    }
//
//
//
//    /**
//     * 查询模块
//     */
//    //账单查询
//    @ResponseBody
//    @RequestMapping(value = "/orderQuery.do")
//    public String orderQuery(HttpServletResponse response, @RequestParam(value = "merOrderId",defaultValue = "0") String merOrderId){
//        System.out.println("请求参数对象："+merOrderId);
//        //组织请求报文
//        JSONObject json = new JSONObject();
//        json.put("mid", mid);
//        json.put("tid", tid);
//        json.put("msgType", msgType_query);
//        json.put("msgSrc", msgSrc);
//        json.put("instMid", instMid);
//        json.put("merOrderId", merOrderId);
//
//        //是否要在商户系统下单，看商户需求  createBill()
//        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        Map<String, String> paramsMap = Util.jsonToMap(json);
//        paramsMap.put("sign", makeSign(md5Key, paramsMap));
//        System.out.println("paramsMap："+paramsMap);
//        String strReqJsonStr = JSON.toJSONString(paramsMap);
//        System.out.println("strReqJsonStr:"+strReqJsonStr);
//
//        //调用银商平台获取二维码接口
//        HttpURLConnection httpURLConnection = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
////        OutputStreamWriter out = null;
//        String resultStr = null;
//        Map<String,String> resultMap = new HashMap<String,String>();
//        if (!StringUtils.isNotBlank(APIurl)) {
//            resultMap.put("errCode","URLFailed");
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }
//
//        try {
//            URL url = new URL(APIurl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestProperty("Content_Type","application/json");
//            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
//            httpURLConnection.setRequestProperty("contentType","UTF-8");
//            //发送POST请求参数
//            out = new PrintWriter(httpURLConnection.getOutputStream());
////            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
//            out.write(strReqJsonStr);
////            out.println(strReqJsonStr);
//            out.flush();
//
//            //读取响应
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                StringBuffer content = new StringBuffer();
//                String tempStr = null;
//                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                while ((tempStr=in.readLine()) != null){
//                    content.append(tempStr);
//                }
//                System.out.println("content:"+content.toString());
//
//                //转换成json对象
//                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
//                String resultCode = respJson.getString("errCode");
//                resultMap.put("errCode",resultCode);
//                resultMap.put("respStr",respJson.toString());
//                resultStr = JSONObject.fromObject(resultMap).toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultMap.put("errCode","HttpURLException");
//            resultMap.put("msg","调用银商接口出现异常："+e.toString());
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }finally {
//            if (out != null) {
//                out.close();
//            }
//            httpURLConnection.disconnect();
//        }
//
//        System.out.println("resultStr:"+resultStr);
//        return resultStr;
//    }
//
//    /**
//     * 交易退款模块
//     * */
//
//    @ResponseBody
//    @RequestMapping(value = "/refund.do",method = RequestMethod.POST)
//    public String refund(HttpServletResponse response, String merOrderId, String refundAmount){
//        System.out.println("请求参数对象："+merOrderId);
//        //组织请求报文
//        JSONObject json = new JSONObject();
//        json.put("mid", mid);
//        json.put("tid", tid);
//        json.put("msgType", msgType_refund);
//        json.put("msgSrc", msgSrc);
//        json.put("instMid", instMid);
//        json.put("merOrderId", merOrderId);
//
//        //是否要在商户系统下单，看商户需求  createBill()
//
//        json.put("refundAmount",refundAmount);
//        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        Map<String, String> paramsMap = Util.jsonToMap(json);
//        paramsMap.put("sign", makeSign(md5Key, paramsMap));
//        System.out.println("paramsMap："+paramsMap);
//
//        String strReqJsonStr = JSON.toJSONString(paramsMap);
//        System.out.println("strReqJsonStr:"+strReqJsonStr);
//
//        //调用银商平台获取二维码接口
//        HttpURLConnection httpURLConnection = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
////        OutputStreamWriter out = null;
//        String resultStr = null;
//        Map<String,String> resultMap = new HashMap<String,String>();
//        if (!StringUtils.isNotBlank(APIurl)) {
//            resultMap.put("errCode","URLFailed");
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }
//
//        try {
//            URL url = new URL(APIurl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestProperty("Content_Type","application/json");
//            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
//            httpURLConnection.setRequestProperty("contentType","UTF-8");
//            //发送POST请求参数
//            out = new PrintWriter(httpURLConnection.getOutputStream());
////            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
//            out.write(strReqJsonStr);
////            out.println(strReqJsonStr);
//            out.flush();
//
//            //读取响应
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                StringBuffer content = new StringBuffer();
//                String tempStr = null;
//                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                while ((tempStr=in.readLine()) != null){
//                    content.append(tempStr);
//                }
//                System.out.println("content:"+content.toString());
//
//                //转换成json对象
//                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
//                String resultCode = respJson.getString("errCode");
//                resultMap.put("errCode",resultCode);
//                resultMap.put("respStr",respJson.toString());
//                resultStr = JSONObject.fromObject(resultMap).toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultMap.put("errCode","HttpURLException");
//            resultMap.put("msg","调用银商接口出现异常："+e.toString());
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }finally {
//            if (out != null) {
//                out.close();
//            }
//            httpURLConnection.disconnect();
//        }
//
//        System.out.println("resultStr:"+resultStr);
//        return resultStr;
//    }
//
//
//    /**
//     * 担保撤销模块
//     * */
//
//    @ResponseBody
//    @RequestMapping(value = "/secureCancel.do",method = RequestMethod.POST)
//    public String cancel(HttpServletResponse response, String merOrderId){
//        System.out.println("请求参数对象："+merOrderId);
//        //组织请求报文
//        JSONObject json = new JSONObject();
//        json.put("mid", mid);
//        json.put("tid", tid);
//        json.put("msgType", msgType_secureCancel);
//        json.put("msgSrc", msgSrc);
////        json.put("instMid", instMid);
//        json.put("merOrderId", merOrderId);
//
//        //是否要在商户系统下单，看商户需求  createBill()
//
//        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        Map<String, String> paramsMap = Util.jsonToMap(json);
//        paramsMap.put("sign", makeSign(md5Key, paramsMap));
//        System.out.println("paramsMap："+paramsMap);
//
//        String strReqJsonStr = JSON.toJSONString(paramsMap);
//        System.out.println("strReqJsonStr:"+strReqJsonStr);
//
//        //调用银商平台获取二维码接口
//        HttpURLConnection httpURLConnection = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
////        OutputStreamWriter out = null;
//        String resultStr = null;
//        Map<String,String> resultMap = new HashMap<String,String>();
//        if (!StringUtils.isNotBlank(APIurl)) {
//            resultMap.put("errCode","URLFailed");
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }
//
//        try {
//            URL url = new URL(APIurl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestProperty("Content_Type","application/json");
//            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
//            httpURLConnection.setRequestProperty("contentType","UTF-8");
//            //发送POST请求参数
//            out = new PrintWriter(httpURLConnection.getOutputStream());
////            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
//            out.write(strReqJsonStr);
////            out.println(strReqJsonStr);
//            out.flush();
//
//            //读取响应
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                StringBuffer content = new StringBuffer();
//                String tempStr = null;
//                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                while ((tempStr=in.readLine()) != null){
//                    content.append(tempStr);
//                }
//                System.out.println("content:"+content.toString());
//
//                //转换成json对象
//                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
//                String resultCode = respJson.getString("errCode");
//                resultMap.put("errCode",resultCode);
//                resultMap.put("respStr",respJson.toString());
//                resultStr = JSONObject.fromObject(resultMap).toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultMap.put("errCode","HttpURLException");
//            resultMap.put("msg","调用银商接口出现异常："+e.toString());
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }finally {
//            if (out != null) {
//                out.close();
//            }
//            httpURLConnection.disconnect();
//        }
//
//        System.out.println("resultStr:"+resultStr);
//        return resultStr;
//    }
//
//
//
//    /**
//     * 担保完成模块
//     * */
//
//    @ResponseBody
//    @RequestMapping(value = "/secureComplete.do",method = RequestMethod.POST)
//    public String secureComplete(HttpServletResponse response, String merOrderId, String completedAmount){
//        System.out.println("请求参数对象："+merOrderId);
//        //组织请求报文
//        JSONObject json = new JSONObject();
//        json.put("mid", mid);
//        json.put("tid", tid);
//        json.put("msgType", msgType_secureComplete);
//        json.put("msgSrc", msgSrc);
//        json.put("instMid", instMid);
//        json.put("merOrderId", merOrderId);
//        json.put("completedAmount", completedAmount);
//
//        //是否要在商户系统下单，看商户需求  createBill()
//
//        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        Map<String, String> paramsMap = Util.jsonToMap(json);
//        paramsMap.put("sign", makeSign(md5Key, paramsMap));
//        System.out.println("paramsMap："+paramsMap);
//
//        String strReqJsonStr = JSON.toJSONString(paramsMap);
//        System.out.println("strReqJsonStr:"+strReqJsonStr);
//
//        //调用银商平台获取二维码接口
//        HttpURLConnection httpURLConnection = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
////        OutputStreamWriter out = null;
//        String resultStr = null;
//        Map<String,String> resultMap = new HashMap<String,String>();
//        if (!StringUtils.isNotBlank(APIurl)) {
//            resultMap.put("errCode","URLFailed");
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }
//
//        try {
//            URL url = new URL(APIurl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestProperty("Content_Type","application/json");
//            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
//            httpURLConnection.setRequestProperty("contentType","UTF-8");
//            //发送POST请求参数
//            out = new PrintWriter(httpURLConnection.getOutputStream());
////            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
//            out.write(strReqJsonStr);
////            out.println(strReqJsonStr);
//            out.flush();
//
//            //读取响应
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                StringBuffer content = new StringBuffer();
//                String tempStr = null;
//                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                while ((tempStr=in.readLine()) != null){
//                    content.append(tempStr);
//                }
//                System.out.println("content:"+content.toString());
//
//                //转换成json对象
//                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
//                String resultCode = respJson.getString("errCode");
//                resultMap.put("errCode",resultCode);
//                resultMap.put("respStr",respJson.toString());
//                resultStr = JSONObject.fromObject(resultMap).toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultMap.put("errCode","HttpURLException");
//            resultMap.put("msg","调用银商接口出现异常："+e.toString());
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }finally {
//            if (out != null) {
//                out.close();
//            }
//            httpURLConnection.disconnect();
//        }
//
//        System.out.println("resultStr:"+resultStr);
//        return resultStr;
//    }
//
//    /**
//     * 订单关闭模块
//     * */
//    @ResponseBody
//    @RequestMapping(value = "/close.do",method = RequestMethod.POST)
//    public String close(HttpServletResponse response, String merOrderId){
//        System.out.println("请求参数对象："+merOrderId);
//        //组织请求报文
//        JSONObject json = new JSONObject();
//        json.put("mid", mid);
//        json.put("tid", tid);
//        json.put("msgType", msgType_close);
//        json.put("msgSrc", msgSrc);
////        json.put("instMid", instMid);
//        json.put("merOrderId", merOrderId);
//
//        //是否要在商户系统下单，看商户需求  createBill()
//
//        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        Map<String, String> paramsMap = Util.jsonToMap(json);
//        paramsMap.put("sign", makeSign(md5Key, paramsMap));
//        System.out.println("paramsMap："+paramsMap);
//
//        String strReqJsonStr = JSON.toJSONString(paramsMap);
//        System.out.println("strReqJsonStr:"+strReqJsonStr);
//
//        //调用银商平台获取二维码接口
//        HttpURLConnection httpURLConnection = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
////        OutputStreamWriter out = null;
//        String resultStr = null;
//        Map<String,String> resultMap = new HashMap<String,String>();
//        if (!StringUtils.isNotBlank(APIurl)) {
//            resultMap.put("errCode","URLFailed");
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }
//
//        try {
//            URL url = new URL(APIurl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestProperty("Content_Type","application/json");
//            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
//            httpURLConnection.setRequestProperty("contentType","UTF-8");
//            //发送POST请求参数
//            out = new PrintWriter(httpURLConnection.getOutputStream());
////            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
//            out.write(strReqJsonStr);
////            out.println(strReqJsonStr);
//            out.flush();
//
//            //读取响应
//            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                StringBuffer content = new StringBuffer();
//                String tempStr = null;
//                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                while ((tempStr=in.readLine()) != null){
//                    content.append(tempStr);
//                }
//                System.out.println("content:"+content.toString());
//
//                //转换成json对象
//                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
//                String resultCode = respJson.getString("errCode");
//                resultMap.put("errCode",resultCode);
//                resultMap.put("respStr",respJson.toString());
//                resultStr = JSONObject.fromObject(resultMap).toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultMap.put("errCode","HttpURLException");
//            resultMap.put("msg","调用银商接口出现异常："+e.toString());
//            resultStr = JSONObject.fromObject(resultMap).toString();
//            return resultStr;
//        }finally {
//            if (out != null) {
//                out.close();
//            }
//            httpURLConnection.disconnect();
//        }
//
//        System.out.println("resultStr:"+resultStr);
//        return resultStr;
//    }

}
