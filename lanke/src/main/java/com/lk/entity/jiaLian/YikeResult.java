package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 嘉联接口 -- 返回类
 * @create 2019-04-26 14:59
 */
@Data
public class YikeResult {

    private String code;//返回状态码 R0001：成功  E0001：失败
    private String result;//返回结果说明
    //(String,Integer)
    private Object data;//图片路径 || 商户号 || 审核结果 0：待审核，1：通过，2：未通过

    private String trade_qrcode;//统一下单返回聚合支付支付页面
}
