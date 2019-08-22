package com.lk.cache;


/**
 * This document and its contents are protected by copyright 2012 and owned by Melot Inc.
 * The copying and reproduction of this document and/or its content (whether wholly or partly) or any
 * incorporation of the same into any other material in any media or format of any kind is strictly prohibited.
 * All rights are reserved.
 * <p>
 * Copyright (c) Melot Inc. 2015
 */


/**
 * Title: CacheManager
 * <p>
 * </p>
 * @author
 * @version V1.0
 * @since 2015-8-5 上午10:58:54 
 */
public class CacheManager {


    private static final int thirty_second = 30 * 1000;//30s

    private static final int ONEFIVE_HOUR = 110 * 60 * 1000;//110m
    private static final int two_hours = 2 * 60 * 60 * 1000;//2h
    private static final int ONE_DAY = 24 * 60 * 60 * 1000;//24h

    
    //api_ticket的缓存
    private static final Cache apiCache = new Cache(ONEFIVE_HOUR);
    public static Cache apiCache() {
        return apiCache;
    }


    //jsapi_ticket的缓存
    private static final Cache jsapiCache = new Cache(ONEFIVE_HOUR);
    public static Cache jsapiCache() {
        return jsapiCache;
    }


    //微信生成临时二维码的有效时间
    private static final Cache wx_qr_cache = new Cache(ONE_DAY);
    public static Cache getWxQrCache() {
        return wx_qr_cache;
    }


    //微信token缓存
    private static final Cache myGzhCahce = new Cache(ONEFIVE_HOUR);
    public static Cache myGzhCahce() {
        return myGzhCahce;
    }





    //顺网接口消息体 缓存30s有效
    private static final Cache msgCache = new Cache(thirty_second);
    public static Cache getMsgCache() {
        return msgCache;
    }


    //门店加v情况缓存
    private static final Cache storeVCache = new Cache(two_hours);
    public static Cache getStoreVCache() {
        return storeVCache;
    }








    


    public static String getCacheKey(Object... strs) {
        StringBuilder strBuffer = new StringBuilder();
        for (Object str : strs) {
            strBuffer.append(str).append("_");
        }
        return strBuffer.deleteCharAt(strBuffer.length() - 1).toString();
    }

}
