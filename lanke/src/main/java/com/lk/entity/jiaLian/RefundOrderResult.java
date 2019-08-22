package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 发起订单退款——结果类
 * @create 2019-04-26 16:42
 */
@Data
public class RefundOrderResult {

    private String code;//返回状态码
    private String result;//返回结果说明
    private String orderstatus;//8:退款成功   9:退款失败
    private String order_no;//退款订单产生的业务编号
    private String shop_order_no;//对应退款的订单的商户系统的订单编号
    private String data;//异常类别码，退款成功，此内容为null

}
