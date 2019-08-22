package com.lk.entity.billecenter;

import lombok.Data;

/**
 * @author myq
 * @description
 * @create 2019-04-19 14:41
 */
@Data
public class SWQrParam {

    private String parameters;//客户端生成二维码需要的参数json串
    private Integer company_type;//厂商类型（1、杰拉 2、网晶）
}
