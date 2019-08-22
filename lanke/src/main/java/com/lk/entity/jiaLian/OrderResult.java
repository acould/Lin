package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 异步接收的订单结果类
 * @create 2019-04-26 17:01
 */
@Data
public class OrderResult {

    private String code;//返回状态码
    private String result;//返回结果说明
    private Integer orderStatus;//订单状态 1:成功
    private String orderNo;//支付订单编号
    private String shopOrderNo;//商户订单编号
    private String completeDate;//订单完成时间
    private Double payMoney;//本次订单交易金额 单位分,不能出现小数
    private String channelNo;//微信/支付宝支付详情页上对应的商户/商家订单编号
    private String shopNo;//商户号
    private String payType;//支付类型：2是微信支付
    private String remark;//订单备注
    private Double sign;//签名（同支付签名规则）

}
