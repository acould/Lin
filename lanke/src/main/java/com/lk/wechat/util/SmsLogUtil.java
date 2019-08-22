package com.lk.wechat.util;


import java.util.ResourceBundle;

import com.lk.util.PageData;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.Data;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * 短信工具类
 */
public class SmsLogUtil {

    private static ResourceBundle emayConfig = ResourceBundle.getBundle("sms");
    public static String appkey = emayConfig.getString("appkey"); // 软件序列号,请通过亿美销售人员获取
    public static String secret = emayConfig.getString("secret");// 密码,请通过亿美销售人员获取
    public static String url = emayConfig.getString("url"); // 序列号首次激活时自己设定


    @Data
    public static class SMSBase {
        String temp_id;
        String temp_info;
        String json;
    }


    @Data
    public static class SMSYzm extends SMSBase{
        String code;
        String internet_name;
        public SMSYzm(String code, String internet_name){
            this.temp_id = "SMS_66915242";
            this.temp_info = "短信验证码";
            this.json = "{\"code\":\"" + code + "\",\"product\":\"" + internet_name + "\"}";
            this.code = code;
            this.internet_name = internet_name;
        }
    }

    @Data
    public static class SMSWarn extends SMSBase{
        String store_id;
        String percentage;
        String count;
        SMSWarn(String store_id, String percentage, String count){
            this.temp_id = "SMS_141200209";
            this.temp_info = "预警通知";
            this.json = "{\"store_id\":\"" + store_id + "\",\"percentage\":\"" + percentage + "\",\"count\":\"" + count + "\"}";
            this.store_id = store_id;
            this.percentage = percentage;
            this.count = count;
        }
    }

    @Data
    public static class SMSUpPsd extends SMSBase{
        String code;
        SMSUpPsd(String code){
            this.temp_id = "SMS_135525120";
            this.temp_info = "修改密码短信验证";
            this.json = "{\"code\":\"" + code + "\"}";
            this.code = code;
        }
    }

    @Data
    public static class SMSUpPhone extends SMSBase{
        String code;
        SMSUpPhone(String code){
            this.temp_id = "SMS_135350138";
            this.temp_info = "换绑手机短信验证";
            this.json = "{\"code\":\"" + code + "\"}";
            this.code = code;
        }
    }

    @Data
    public static class SMSStoreVPass extends SMSBase{
        String store_name;
        String expiration_time;
        SMSStoreVPass(String store_name, String expiration_time){
            this.temp_id = "SMS_135345112";
            this.temp_info = "加V成功";
            this.json = "{\"STORE_NAME\":\"" + store_name + "\",\"EXPIRATION_TIME\":\"" + expiration_time + "\"}";
            this.store_name = store_name;
            this.expiration_time = expiration_time;
        }
    }

    @Data
    public static class SMSStoreVFail extends SMSBase{
        String store_name;
        SMSStoreVFail(String store_name){
            this.temp_id = "SMS_131920027";
            this.temp_info = "加V失败";
            this.json = "{\"STORE_NAME\":\"" + store_name + "\"}";
            this.store_name = store_name;
        }
    }

    @Data
    public static class SMSWXAppAuth extends SMSBase{
        String nick_name;
        SMSWXAppAuth(String nick_name){
            this.temp_id = "SMS_135350034";
            this.temp_info = "SMS微信公众号授权";
            this.json = "{\"nick_name\":\"" + nick_name + "\"}";
            this.nick_name = nick_name;
        }
    }

    @Data
    public static class SMSAgentCreate extends SMSBase{
        SMSAgentCreate(){
            this.temp_id = "SMS_139972174";
            this.temp_info = "代理商账户创建完成";
        }
    }

    @Data
    public static class SMSStorePay extends SMSBase{
        String enterprise_name;
        SMSStorePay(String enterprise_name, String type){
            this.enterprise_name = enterprise_name;
            this.json = "{\"enterprise_name\":\"" + enterprise_name + "\"}";
            if(type.equals("material_success")){
                this.temp_id = "SMS_139987004";
                this.temp_info = "在线支付--资料审核开通成功";
            }else if(type.equals("open_success")){
                this.temp_id = "SMS_139987005";
                this.temp_info = "在线支付开通成功";
            }else if(type.equals("material_false")) {
                this.temp_id = "SMS_146805906";
                this.temp_info = "在线支付--资料审核开通失败";
            }
        }
    }

    @Data
    public static class SMSStoreExpiration extends SMSBase{
        String store_name;
        String expiration_time;
        SMSStoreExpiration(String store_name, String expiration_time){
            this.temp_id = "SMS_141581908";
            this.temp_info = "加V过期";
            this.json = "{\"store_name\":\"" + store_name + "\",\"EXPIRATION_TIME\":\"" + expiration_time + "\"}";
            this.store_name = store_name;
            this.expiration_time = expiration_time;
        }
    }


    public static Boolean sendSMS(String phone, JSONObject param){
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");//公共回传参数(可选)
        req.setSmsType("normal");//短信类型(必须)
        req.setSmsFreeSignName("网晶揽客");//短信签名（必须）
        req.setSmsParamString(param.getString("json"));//短信模板变量(可选)
        req.setRecNum(phone);//短信接收号码（必须）
        req.setSmsTemplateCode(param.getString("temp_id"));//短信模板ID（必须）
        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());
        } catch (Exception e) {
            System.out.print("测试发送短信失败emayConfig===" + appkey + "抛出异常为" + e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 阿里大鱼短信发送
     * @param code         验证码
     * @param phone        手机号
     * @param internetName 网吧名称
     * @return
     */
    public static Boolean sendMessage(String code, String phone, String internetName) {

        JSONObject param = JSONObject.fromObject(new SMSYzm(code, internetName));
        return sendSMS(phone, param);
    }


    
    /**
     * 预警通知
     * @return
     */
    public static Boolean warningMessage(String store_id, String percentage, String count , String phone) {
        JSONObject param = JSONObject.fromObject(new SMSWarn(store_id, percentage, count));
        return sendSMS(phone, param);
    }
    
    
    /**
     * 修改密码短信验证
     * @param code         验证码
     * @param phone        手机号
     * @return
     */
    public static Boolean uodatePassword(String code, String phone) {
        JSONObject param = JSONObject.fromObject(new SMSUpPsd(code));
        return sendSMS(phone, param);

    }
    
    /**
     * 换绑手机短信验证
     * @param code         验证码
     * @param phone        手机号
     * @return
     */
    public static Boolean uodatePhone(String code, String phone) {
        JSONObject param = JSONObject.fromObject(new SMSUpPhone(code));
        return sendSMS(phone, param);
    }
    
    /**
     * 阿里大鱼短信发送(加V成功反馈)
     * @param phone      手机号
     * @param STORE_NAME 网吧名称
     * @param EXPIRATION_TIME 到期日期
     * @return
     */
    public static Boolean sendMessagePass(String STORE_NAME, String phone, String EXPIRATION_TIME) {
        JSONObject param = JSONObject.fromObject(new SMSStoreVPass(STORE_NAME, EXPIRATION_TIME));
        return sendSMS(phone, param);

    }
    
    /**
     * 阿里大鱼短信发送(加V失败反馈)
     * @param phone      手机号
     * @param STORE_NAME 网吧名称
     * @return
     */
    public static Boolean sendMessageFail( String STORE_NAME,String phone) {
        JSONObject param = JSONObject.fromObject(new SMSStoreVFail(STORE_NAME));
        return sendSMS(phone, param);

    }
    
    /**
     * 阿里大鱼短信发送(微信公众号授权)
     * @param nick_name  公众号名称
     * @return
     */
    public static Boolean sendNotification(String phone,String nick_name) {
        JSONObject param = JSONObject.fromObject(new SMSWXAppAuth(nick_name));
        return sendSMS(phone, param);

    }

    /**
     * 阿里大鱼短信发送(代理商账户创建完成)
     * @return
     */
	public static Boolean sendMessagePass(String phone) {
        JSONObject param = JSONObject.fromObject(new SMSAgentCreate());
        return sendSMS(phone, param);

	}
	
	/**
     * 发送短信提醒（在线支付开通--资料审核通过/开通成功）
     * @param phone
     * @param enterprise_name 企业名称
     * @return
     */
    public static Boolean sendOpenCondition(String phone,String enterprise_name,String type) {
        JSONObject param = JSONObject.fromObject(new SMSStorePay(enterprise_name, type));
        return sendSMS(phone, param);

    }
    
    /**
     * 阿里大鱼短信发送(加V过期预警)
     * @param phone      手机号
     * @param store_name 网吧名称
     * @param EXPIRATION_TIME 到期日期
     * @return
     */
    public static Boolean sendNotice(String store_name, String phone, String EXPIRATION_TIME) {
        JSONObject param = JSONObject.fromObject(new SMSStoreExpiration(store_name, EXPIRATION_TIME));
        return sendSMS(phone, param);
    }


    /**
     * 发送门店计费申请的通知
     *  ${store_name}—正在申请开通计费服务， 请及时核实。
     * @param phone
     * @param store_name
     * @return
     */
    public static Boolean storeVApply(String phone, String store_name){
        String json = "{\"store_name\":\"" + store_name + "\"}";
        JSONObject param = new JSONObject();
        param.put("temp_id", "SMS_171113178");
        param.put("json", json);
        return sendSMS(phone, param);
    }

    public static void main(String[] args) {
        storeVApply("18222956710", "sdfasdf");
    }


}
