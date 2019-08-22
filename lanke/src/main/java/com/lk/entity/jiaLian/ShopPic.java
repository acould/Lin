package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 嘉联商户向支付平台进件商户信息--请求图片
 * @create 2019-04-26 14:56
 */
@Data
public class ShopPic {

    private String picType;//照片类型
    private String picPath;//照片路径

    public ShopPic (String picType, String picPath){
        this.picType = picType;
        this.picPath = picPath;
    }
}
