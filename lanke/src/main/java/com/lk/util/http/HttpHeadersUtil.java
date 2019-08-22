package com.lk.util.http;

import com.google.common.base.Joiner;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * @author： <a href="mailto:wangfei@tianyancha.com">wangfei</a>
 * @date: 2019/2/21
 * @version: 1.0.0
 */
public class HttpHeadersUtil {//header各种操作工具类


    // 设置header  将参数二Map设置进参数一里
    public static void setHeaders(HttpRequestBase httpRequestBase, Map<String, String> headersMap) {
        if (httpRequestBase != null && headersMap != null) {
            Set<Map.Entry<String, String>> headersEntrySet = headersMap.entrySet();
            for (Map.Entry<String, String> headerEntry : headersEntrySet) {
                httpRequestBase.setHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
    }

    //设置get参数  设置httpGet消息键值对参数
    private static void setUrlParams(HttpRequestBase httpRequestBase, Map<String, String> urlParamsMap) {
        String urlParams = Joiner.on("&").withKeyValueSeparator("=").join(urlParamsMap);
        String uri = httpRequestBase.getURI().toString() + "?" + urlParams;
        httpRequestBase.setURI(URI.create(uri));
    }


}
