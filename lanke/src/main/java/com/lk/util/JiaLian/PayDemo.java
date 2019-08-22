package com.lk.util.JiaLian;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lk.entity.jiaLian.PreOrder;

import java.util.*;

public class PayDemo {

    public static void main(String[] args) throws Exception{
        PreOrder preOrder = new PreOrder();
        preOrder.setShop_no("123456");
        preOrder.setShop_order_no("111111");

        preOrder.setTransaction_amount(10);
        preOrder.setOrder_remark("揽客充值：充"+10+"元，送"+0+"元。");
        preOrder.setReturn_url(YikeConfig.order_return_url);//支付成功后跳转地址
        preOrder.setCallback_url(YikeConfig.order_callback_url);//回调地址
        preOrder.setTimestamp(String.valueOf(new Date().getTime()));//时间戳
        preOrder.setOrder_name("揽客充值");//订单名称
        preOrder.setSign(PayDemo.getSign(preOrder,null));//签名
        preOrder.setClient_ip(YikeConfig.client_ip);//接入系统终端ip地址（不参与签名）

        System.out.println(preOrder);
    }

    public static String getSign(PreOrder preOrder, String secret) throws Exception{

        Map<String,Object> map = JSON.parseObject(JSON.toJSONString(preOrder),new TypeReference<Map<String,Object>>(){});

        String res = getSign(map, secret);
        System.out.println("签名为：：：：" + res);
        return res;
    }

    //获取签名
    public static String getSign(Map<String ,Object> map, String secret){

        try {
            String keyStr = getMapToKeyValueStringSortAsc(map);
            System.out.println("查看签名字符串");
            System.out.println("待验签字符串："+keyStr);//046799  克龙 855663  品遥：041778

            System.out.println("shop_secret:::" + secret);
            String en = SecurityUtil.sha1X16(keyStr + secret, "UTF-8");
            return en;
        } catch (Exception e) {
            System.out.println("签名生成异常");
            return "";
        }
    }

    public static String getMapToKeyValueStringSortAsc(Map<String,Object> map){
        //将map按照字母顺序排序
        String keyStr = key_sort(map);

        return keyStr;
    }

    /**
     * 按照key字母排序
     *
     * @param map
     * @return
     */
    public static String key_sort(Map<String, Object> map) {

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());

        //排序方法
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                //return (o2.getValue() - o1.getValue());
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
        });



        //Map<String, Object> resultMap = new HashMap<String, Object>();



        String keyStr = "";

        //排序后
        for (Map.Entry<String, Object> m : infoIds) {
            keyStr = keyStr + m.getKey() + m.getValue();
            // resultMap.put(m.getKey(), m.getValue());
        }

        return keyStr;
    }

}
