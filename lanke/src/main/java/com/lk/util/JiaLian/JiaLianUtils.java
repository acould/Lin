package com.lk.util.JiaLian;

import com.lk.entity.Message;
import com.lk.entity.jiaLian.PreOrder;
import com.lk.entity.jiaLian.ShopInfo;
import com.lk.entity.jiaLian.ShopPic;
import com.lk.service.internet.newPictures.NewPicturesService;
import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.http.HttpClientFactory;
import com.lk.util.http.HttpObjectBuilder;
import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.response.HttpResponse;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author myq
 * @description 嘉联接口
 * @create 2019-05-05 13:10
 */
public class JiaLianUtils {

    private static final String req_url = YikeConfig.jl_url;
    private static final Logger logger = LoggerFactory.getLogger(JiaLianUtils.class);



    public static void main(String[] args) throws Exception{
        String filePath = "C:/Users/myq/Desktop/myq/641.jpg";
//        String filePath = "http://myq.cross.echosite.cn:80/lanker/uploadFiles/uploadImgs/321dfm54lod846579985e1ss2135cvbn/7885adc0121a46a88e4116284b59e5ef/1557217870911.jpg";
//        String filePath = "C:/Users/myq/Desktop/myq/图片/1.jpg";
//        new JiaLianUtils().picReq("xwshscsfzzp", filePath);

//        Message m = selAudit("1557387824286");

        String shop_no = "1557741112148";
        String secret = "112148";
//        String order_id = "4432201905141656323170749729";//已退
        String order_id = "4432201905160436220852438142";

//        selOrder(shop_no, order_id, "sno", secret);

        refundOrder(shop_no, order_id, "100", "七天无理由退款", secret);


//        PreOrder preOrder = new PreOrder();
//        preOrder.setShop_no("12312831283123");
//        preOrder.setShop_order_no("34593485903804953409");
//        preOrder.setTransaction_amount(10);
//        preOrder.setOrder_remark("揽客充值：充"+0.1+"元，送"+0+"元。");
//        preOrder.setReturn_url(YikeConfig.order_return_url);//支付成功后跳转地址
//        preOrder.setCallback_url(YikeConfig.order_callback_url);//回调地址
//        preOrder.setTimestamp(String.valueOf(new Date().getTime()));//时间戳
//        preOrder.setOrder_name("揽客充值");//订单名称
//        preOrder.setSign(PayDemo.getSign(preOrder));//签名
//        preOrder.setClient_ip(YikeConfig.client_ip);//接入系统终端ip地址（不参与签名）
//
//        String requestUrl = req_url + YikeConfig.unified_order;
//        JSONObject param = JSONObject.fromObject(preOrder);
//        JSONObject param2 = JSONObject.fromObject(preOrder);
//        param = httpReq(requestUrl, param);
//        System.out.println("_____"+param);
//        System.out.println("_____"+param2);

    }

    /**
     * 嘉联——上传图片
     * @param sort
     * @param filePath
     * @return
     * @throws Exception
     */
    public Message picReq(String sort, String filePath){
        JSONObject result = new JSONObject();
        String requestUrl = req_url + YikeConfig.upload;
        String message = "嘉联——上传图片接口";

        String res = HttpClient.post(requestUrl)
                .param("picType", sort)
                .param("imageFile", new File(filePath))
                .execute()
                .asString();
        result = JSONObject.fromObject(res);

        logger.info(message + "返回码：：" + result.toString());
        if(result.getString("code").equals("R0001")){
            //返回嘉联的图片路径
            return Message.ok(result.getString("result")).addData("path", result.getString("data"));
        }
        return Message.error(result.getString("result"));
    }


    /**
     * 提交商户信息
     * @param shopInfo
     * @return
     * @throws Exception
     */
    public static Message shopReq(ShopInfo shopInfo){
        String requestUrl = req_url + YikeConfig.shop_info;
        String message = "嘉联——提交商户信息接口";

        shopInfo.setCall_back_url(YikeConfig.shop_call_back_url);

        List<JSONObject> listp = new ArrayList<>();
        for (int i = 0; i < shopInfo.getPicList().size(); i++) {
            JSONObject o = new JSONObject();
            o.put("picType", shopInfo.getPicList().get(i).getPicType());
            o.put("picPath", shopInfo.getPicList().get(i).getPicPath());
            listp.add(o);
        }


        JSONObject param = JSONObject.fromObject(shopInfo);
        param.remove("shop_no");
        param.remove("picList");
        param.put("picList", listp);

        System.out.println("param" + param);

        JSONObject result = httpReq(requestUrl, param);
        logger.info(message + "返回码：：" + result.toString());
        if(result.getString("code").equals("R0001")){
            //返回商户号
            return Message.ok(result.getString("result")).addData("shop_no", result.getString("data"));
        }
        return Message.error(result.getString("result"));
    }

    /**
     * 修改商户信息
     * @param shopInfo
     * @return
     * @throws Exception
     */
    public static Message updateShopReq(ShopInfo shopInfo) throws Exception{

        String requestUrl = req_url + YikeConfig.update_shop_info;
        String message = "嘉联——修改商户信息接口";

        shopInfo.setCall_back_url(YikeConfig.shop_call_back_url);
        JSONObject param = JSONObject.fromObject(shopInfo);
        System.out.println(param);
        param.remove("user_name");

        JSONObject result = httpReq(requestUrl, param);
        logger.info(message + "返回码：：" + result.toString());

        if(result.getString("code").equals("R0001")){
            //返回结果
            return Message.ok(result.getString("result"));
        }
        return Message.error(result.getString("result"));
    }

    /**
     * 查询商户审核状态
     * @param shop_no
     * @return
     * @throws Exception
     */
    public static Message selAudit(String shop_no) throws Exception{
        String requestUrl = req_url + YikeConfig.get_audit_status;
        String message = "嘉联——查询商户审核状态接口";

        JSONObject param = new JSONObject();
        param.put("shopNo", shop_no);

        logger.info("param::" + param.toString());
        JSONObject result = new JSONObject();
        try{
            result = formReq2(requestUrl, param);
        }catch (Exception e){
            result.put("code", "E0001");
            result.put("result", "请求异常" + e.getMessage());
        }

        logger.info(message + "返回码：：" + result.toString());
        //返回审核结果 0：待审核，1：通过，2：未通过
        if(result.getString("code").equals("R0001")){
            if(result.get("data").toString().equals("0")){
                return Message.ok("正在审核").addData("status",0);
            }else if(result.get("data").toString().equals("1")){
                return Message.ok("审核通过").addData("status",1);
            }else if(result.get("data").toString().equals("2")){
                return Message.ok("审核未通过").addData("status",2);
            }
        }
        return Message.error(result.getString("result"));
    }

    /**
     * 聚合支付——统一下单入口
     * @return
     * @throws Exception
     */
    public static Message unifiedOrder(PreOrder preOrder) throws Exception{
        String requestUrl = req_url + YikeConfig.unified_order;

        String message = "聚合支付——统一下单入口";
        logger.info(message);

        JSONObject param = JSONObject.fromObject(preOrder);


        JSONObject result = httpReq(requestUrl, param);
        if(result.getString("code").equals("R0001")){
            return Message.ok(result.getString("result")).addData("url", result.getString("trade_qrcode"));
        }

        return Message.error(result.getString("result"));
    }

    /**
     * 查询订单
     * @param shop_no 商户号
     * @param order_no 订单编号
     * @param type 查询类型：ono:支付系统订单编号  sno：商户系统订单编号
     * @param secret 商户密钥
     * @return
     * @throws Exception
     */
    public static Message selOrder(String shop_no, String order_no, String type, String secret){
        String requestUrl = "";
        JSONObject param = new JSONObject();
        if(type.equals("ono")){
            //根据嘉联订单号查询
            requestUrl = req_url + YikeConfig.sys_query_order;
            param.put("type", "ono");
        }else if(type.equals("sno")){
            //根据揽客订单号查询
            requestUrl = req_url + YikeConfig.lk_query_order;
            param.put("type", "sno");
        }

        String message = "嘉联——订单查询：：" + type;
        logger.info(message);

        param.put("shop_no", shop_no);
        param.put("order_no", order_no);
        param.put("timestamp", String.valueOf(new Date().getTime()));
        param.put("sign", PayDemo.getSign(param, secret));
        JSONObject result = httpReq(requestUrl, param);

        if(result.getString("code").equals("R0001")){
            return Message.ok(result.getString("result")).addData("url", result.getString("trade_qrcode"));
        }

        return Message.error(result.getString("result"));
    }

    /**
     * 发起退款
     * @param shop_no 商户号
     * @param order_no 订单编号
     * @param refund_amount 退款金额
     * @param order_remark 退款原因
     * @param secret 商户密钥
     * @return
     */
    public static Message refundOrder(String shop_no, String order_no, String refund_amount,
                                      String order_remark, String secret){
        String requestUrl = req_url + YikeConfig.refund_order;

        String message = "嘉联——退款：：" ;
        logger.info(message);

        JSONObject param = new JSONObject();
        param.put("type", "sno");
        param.put("shop_no", shop_no);
        param.put("order_no", order_no);
        param.put("refund_amount", refund_amount);
        param.put("order_remark", order_remark);
        param.put("timestamp", String.valueOf(new Date().getTime()));
        param.put("sign", PayDemo.getSign(param, secret));
        JSONObject result = httpReq(requestUrl, param);

        if(result.getString("code").equals("R0001")){
            return Message.ok(result.getString("result")).addData("url", result.getString("trade_qrcode"));
        }

        return Message.error(result.getString("result"));
    }







    public static JSONObject formReq2(String requestUrl, JSONObject param){
        JSONObject result = new JSONObject();
        HttpResponse response =
                HttpClient.Instance
                        .post(requestUrl)
                        .param(param)
                        .execute();
        try{
            String res = response.asString();
            result = JSONObject.fromObject(res);
        }catch (Exception e){
            result.put("code", "E0001");
            result.put("result", "请求异常" + e.getMessage());
        }
        return result;
    }

    public static JSONObject jsonReq2(String requestUrl, JSONObject param){
        JSONObject result = new JSONObject();

        HttpResponse response =
                HttpClient.Instance
                .textBody(requestUrl)
                .json(param.toString())
                .execute();
        System.out.println("111=================");

        try{
            String res = response.asString();
            result = JSONObject.fromObject(res);
        }catch (Exception e){
            System.out.println("请求异常：" + e.getMessage());
            result.put("code", "E0001");
            result.put("result", "请求异常" + e.getMessage());
        }
        return result;
    }

    public static JSONObject httpReq(String requestUrl, JSONObject param){
        logger.info("param：：：" + param.toString());

        JSONObject result = new JSONObject();
        //2、header信息
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", YikeConfig.head);

        //4、执行
        HttpPost httpPost = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String jsonResult = null;
        try {
            httpPost = HttpObjectBuilder.getHttpPostUsingJson(requestUrl, headerMap, param.toString());
            httpClient = HttpClientFactory.getHttpClient();
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {// 判断状态码是否为200
                jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");//得到响应体的内容
            }
            //不存在，新建，返回instanceId
            //已存在，保持存在，返回40902："Consumer with specified consumer ID already exists in the specified consumer group."
            //并不关注返回值，所以不需要返回结果
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //共用辅助方法 关流
            doCloseHttp(httpClient,response);
        }
        logger.info("返回码：：" + jsonResult.toString());

        return result.fromObject(jsonResult);
    }

    public static JSONObject formReq(String requestUrl, Map<String, Object> map) {
        JSONObject result = new JSONObject();

        //4、执行
        HttpPost httpPost = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String jsonResult = null;
        try {
            //2、header信息
            HashMap<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/x-www-form-urlencoded");

            httpPost = HttpObjectBuilder.getHttpPostUsingForm(requestUrl, headerMap, map);
            httpClient = HttpClientFactory.getHttpClient();
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {// 判断状态码是否为200
                jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");//得到响应体的内容
            }
            //不存在，新建，返回instanceId
            //已存在，保持存在，返回40902："Consumer with specified consumer ID already exists in the specified consumer group."
            //并不关注返回值，所以不需要返回结果
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //共用辅助方法 关流
            doCloseHttp(httpClient, response);
        }

        return result.fromObject(jsonResult);
    }



    //共用辅助方法 关流
    public static void doCloseHttp(CloseableHttpClient httpClient, CloseableHttpResponse response){
        if(httpClient!=null){
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(response!=null){
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
