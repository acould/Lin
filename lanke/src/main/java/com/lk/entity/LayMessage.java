package com.lk.entity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈layui格式的返回类〉
 *
 * @author myq
 * @create 2019/3/23
 * @since 1.0.0
 */
@Data
public class LayMessage {

    private Integer code;
    private String msg;
    private Integer count;
    private Integer showCount;
    private List<?> data = new ArrayList<>();


    public LayMessage(){super();}

    public LayMessage(Integer code, String msg, Integer count, List<?> list) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = list;
    }

    public static LayMessage ok(Integer count, List<?> list){
        LayMessage lm = new LayMessage();
        lm.setCode(0);
        lm.setMsg("查询成功");
        lm.setCount(count);
        lm.setData(list);
        return lm;
    }

    public static LayMessage ok(Integer count, List<?> list, Integer showCount){
        LayMessage lm = new LayMessage();
        lm.setCode(0);
        lm.setMsg("查询成功");
        lm.setCount(count);
        lm.setShowCount(showCount);
        lm.setData(list);
        return lm;
    }


}