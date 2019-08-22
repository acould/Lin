package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description
 * @create 2019-04-16 17:09
 */
@Data
public class SWQuery {

    //通用时
    private Integer field_type;//字段类型（0卡号；1证件号；2电脑名称；3上机唯一标识guid）
    private String field_value;//搜索字段值


}
