package com.lk.service.wechat;


import com.lk.util.PageData;
import net.sf.json.JSONObject;

import java.io.InputStream;

/**
 * @author shkstart
 * @create 2018-10-17 11:40
 */
public interface WeixinService {

    /**
     * 验证微信签名是否正确
     * @param signature
     * @param echostr
     * @param timestamp
     * @param nonce
     * @return
     * @throws Exception
     */
    public String checkSignature(String signature, String echostr, String timestamp, String nonce) throws Exception;

    /**
     * 获取微信inputStream里的参数，解密后的
     * @param inputStream
     * @return
     * @throws Exception
     */
    public String getecryptXML(InputStream inputStream) throws Exception;

    /**
     * 处理微信推送的消息和事件
     * @param decryptXML
     * @param angetId
     * @param timestamp
     * @param signature
     * @param nonce
     * @return
     * @throws Exception
     */
    public String handleEvent(String decryptXML, String angetId, String timestamp, String signature,
                              String nonce) throws Exception;



    /**
     * 发送模板消息前，检查模板设置
     * @param internet_id 网吧id
     * @param template_id_short 微信模板短号id
     * @return
     * @throws Exception
     */
    public JSONObject preHandleTemplate(String internet_id, String type, String template_id_short) throws Exception;


    /**
     * 发送 type类型的模板消息 给对应的open_id
     * @param internet_id 网吧id
     * @param open_id 微信用户id
     * @param type 自定义的模板类型
     * @param pd (标题first_data, 备注remark_data, 内容keyword_data, 跳转url)
     * @return
     * @throws Exception
     */
    public JSONObject sendTamplate(String internet_id, String open_id, String type, PageData pd)throws Exception;




}
