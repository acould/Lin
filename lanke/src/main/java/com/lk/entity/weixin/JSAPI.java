package com.lk.entity.weixin;

import lombok.Data;

/**
 * @author myq
 * @description jsapi 签名类
 * @create 2019-06-03 18:16
 */
@Data
public class JSAPI {

    private String url;
    private String share_url;
    private String appid;
    private String signature;
    private String nonceStr;
    private String timestamp;
    private String jsapi_ticket;
    private String internet_id;

}
