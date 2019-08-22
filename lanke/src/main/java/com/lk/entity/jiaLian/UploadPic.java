package com.lk.entity.jiaLian;

import lombok.Data;

import java.io.File;

/**
 * @author myq
 * @description 嘉联上传图片类型
 * @create 2019-04-26 15:30
 */
@Data
public class UploadPic {

    private File imageFile;//图片信息
    private String picType;//照片类型

}
