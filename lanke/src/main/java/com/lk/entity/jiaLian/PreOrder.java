package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 预下单
 * @create 2019-04-26 16:17
 */
@Data
public class PreOrder {

    private String shop_no;//商户号
    private String shop_order_no;//接入方订单编号，长度100

    private Integer transaction_amount;//支付金额单位分
    private String order_remark;//订单备注
    private String return_url;//支付成功后跳转地址
    private String callback_url;//回调地址
    private String timestamp;//时间戳
    private String order_name;//订单名称
    private String sign;//签名
    private String client_ip;//接入系统终端ip地址（不参与签名）


}
