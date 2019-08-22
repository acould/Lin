package com.lk.entity;

import lombok.Data;

/**
 * @author myq
 * @description 图片返回
 * @create 2019-06-20 15:31
 */
@Data
public class LayImgMessage {

    private Integer code;
    private String msg;
    private LayImg data;

    @Data
    public static class LayImg{
        private String src;
        private String title;

        public LayImg(){super();}

        public LayImg(String src, String title){
            super();
            this.src = src;
            this.title = title;
        }
        public static LayImg ok(String src, String title){
            LayImg layImg = new LayImg(src, title);
            return layImg;
        }

    }


    public static LayImgMessage ok(String src, String title){
        LayImgMessage lm = new LayImgMessage();
        lm.setCode(0);
        lm.setMsg("上传成功");
        lm.setData(LayImg.ok(src,title));
        return lm;
    }

    public static LayImgMessage fail(String msg, String src, String title){
        LayImgMessage lm = new LayImgMessage();
        lm.setCode(-1);
        lm.setMsg(msg);
        lm.setData(LayImg.ok(src,title));
        return lm;
    }
}
