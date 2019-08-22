package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——会员充值 --返回字段
 * @create 2019-04-16 18:12
 */
@Data
public class SWRechargeReturn {

    private Double principal;//增加的本金
    private Double reward;//增加的奖励
    private Double installment_amount;//分期奖励金额
    private Integer installment_month;//分期奖励月数
    private String order_id;//订单号
}
