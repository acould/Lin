package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 请求订单退款类
 * @create 2019-04-26 16:40
 */
@Data
public class RefundOrder {

    private String shop_no;//商户号
    private String timestamp;//请求时间戳
    private String order_no;//订单编号，根据type类型决定
    private String refund_amount;//退款金额
    private String order_remark;//退款原因
    private String type;//ono:应用支付系统订单编号  sno：应用商户订单编号
    private String sign;//签名

}
