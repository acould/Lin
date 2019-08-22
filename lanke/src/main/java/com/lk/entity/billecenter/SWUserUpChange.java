package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 请求换机
 * @create 2019-04-18 16:19
 */
@Data
public class SWUserUpChange {

    private String card_id;//卡号
    private String from_computer;//源机器
    private String to_computer;//目标机器

}
