package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description
 * @create 2019-04-17 14:24
 */
@Data
public class SWQueryFlow {

    //查询流水时需用以下
    private String card_id;//卡号
    private Integer filter_type;//操作类型
    private Integer begin_time;//开始时间
    private Integer end_time;//结束时间
}
