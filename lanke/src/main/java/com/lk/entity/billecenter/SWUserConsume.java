package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 顺网——用户消费信息
 * @create 2019-04-16 17:01
 */
@Data
public class SWUserConsume {

    private String card_id;
    private String sale_name;
    private Integer pay_type;
    private String pay_from;
    private Double amount;
    private Double reward;
    private String order_id;
    private String order_from;
    private String memo;
    private String guid;

}
