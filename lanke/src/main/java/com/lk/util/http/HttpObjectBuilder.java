package com.lk.util.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author： <a href="mailto:wangfei@tianyancha.com">wangfei</a>
 * @date: 2019/2/21
 * @version: 1.0.0
 */
public class HttpObjectBuilder {//构建HttpRequestBase工具类 HttpRequestBase = HttpGet + HttpPost +httpDelete + httpPut + 、、、


    //一、构建一个HttpGet对象
    public static HttpGet getHttpGet(String uri) {
        HttpGet httpGet = new HttpGet(uri);
        return httpGet;
    }

    public static HttpGet getHttpGet(String uri, Map<String, String> headersMap) {
        HttpGet httpGet = new HttpGet(uri);
        HttpHeadersUtil.setHeaders(httpGet, headersMap);
        return httpGet;
    }


    //二、构建一个HttpPost对象
    public static HttpPost getHttpPost(String uri) {
        HttpPost httpPost = new HttpPost(uri);
        return httpPost;
    }


    //带File文件的post表单
    public static HttpPost getHttpPostUsingFormFile(String uri, Map<String, String> headersMap){
        HttpPost httpPost = new HttpPost(uri);
        HttpHeadersUtil.setHeaders(httpPost, headersMap);

        return httpPost;
    }

    //post表单格式传参
    public static HttpPost getHttpPostUsingForm(String uri, Map<String, String> headersMap, Map<String, Object> paramsMap) {
        HttpPost httpPost = new HttpPost(uri);
        HttpHeadersUtil.setHeaders(httpPost, headersMap);
        setPostParams(httpPost, paramsMap);
        return httpPost;
    }

    //辅助方法  设置表单格式传参post参数
    private static void setPostParams(HttpPost httpPost, Map<String, Object> paramsMap) {
        if (httpPost != null && paramsMap != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            Set<Entry<String, Object>> paramsEntrySet = paramsMap.entrySet();
            for (Entry<String, Object> paramEntry : paramsEntrySet) {
                nameValuePairList.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue().toString()));
            }
            try {
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nameValuePairList,"utf-8");

                httpPost.setEntity(uefEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    //post-json格式格式传参
    public static HttpPost getHttpPostUsingJson(String uri, Map<String, String> headersMap, Map<String, Object> paramsMap) {
        HttpPost httpPost = new HttpPost(uri);
        String jsonBody = JSON.toJSONString(paramsMap);
        HttpHeadersUtil.setHeaders(httpPost, headersMap);
        try {
            httpPost.setEntity(new StringEntity(jsonBody));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return httpPost;
    }

    public static HttpPost getHttpPostUsingJson(String uri, Map<String, String> headersMap, String jsonBody) {
        HttpPost httpPost = new HttpPost(uri);
        HttpHeadersUtil.setHeaders(httpPost, headersMap);
        try {
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON.getCharset()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpPost;
    }


    //三、构建一个带body的httpGet
    public static HttpGetWithEntity getHttpGetWithEntity(String uri, String jsonBody) {
        HttpGetWithEntity httpGet = new HttpGetWithEntity(uri);
        httpGet.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        return httpGet;
    }

    public static HttpGetWithEntity getHttpGetWithEntity(String uri, Map<String, String> headersMap, String jsonBody) {
        HttpGetWithEntity httpGet = new HttpGetWithEntity(uri);
        httpGet.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        HttpHeadersUtil.setHeaders(httpGet, headersMap);
        return httpGet;
    }

}

