package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description 请求上机
 * @create 2019-04-18 16:12
 */
@Data
public class SWUserUp {

    private String card_id;//卡号
    private String computer_name;//电脑名称
    private String user_password;//明文密码
    private Integer up_type;//选填，上机类型（1、扫码上机、0、默认）

}
