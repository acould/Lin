package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 请求下机
 * @create 2019-04-22 15:25
 */
@Data
public class SWUserDown2 {

    private String card_id;//必填，卡号
    private Integer pay_type;//必填，支付类型
    private Double card_remain;//选填，余额
    private Double consume_fee;//选填，实收费用
    private Integer up_duration;//选填，上机时长
    private Integer up_timestamp;//选填，上机时间
    private Integer down_timestamp;//选填，下机时间
    private String memo;//选填，备注


}
