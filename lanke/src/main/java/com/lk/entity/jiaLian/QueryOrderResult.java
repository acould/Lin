package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 查询订单--结果类
 * @create 2019-04-26 16:23
 */
@Data
public class QueryOrderResult {

    private String code;//返回状态码
    private String result;//返回结果说明
    private QueryOrderData data;//订单对象


    @Data
    public class QueryOrderData{
        private String shopNo;//商户号
        private String shopName;//商户名称
        private String orderNo;//订单编号
        private String shopOrderNo;//接入方传入本地生成的订单编号
        private String orderCompleteDatetime;//订单完成时间
        private Double orderMoney;//请求支付金额 单位：元
        private Double payMoney;//实际支付金额 单位：元
        private Integer orderStatus;//订单状态
        private String payWayCode;//01：支付
    }

}
