package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 查询订单--请求类
 * @create 2019-04-26 16:21
 */
@Data
public class QueryOrder {

    private String shop_no;//商户号
    private String sign;//签名
    private String order_no;//支付订单编号
    private String timestamp;//当前查询时间戳

    //通过接入方订单接口时，需要这个字段
    private String type;//查询类型：ono:支付系统订单编号  sno：商户系统订单编号
}
