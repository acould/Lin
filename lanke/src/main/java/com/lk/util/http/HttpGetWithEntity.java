package com.lk.util.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @author： <a href="mailto:wangfei@tianyancha.com">wangfei</a>
 * @date: 2019/3/2
 * @version: 1.0.0
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {//自定义带Body的HttpGet对象

    private final static String METHOD_NAME = "GET";

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpGetWithEntity() {
        super();
    }

    public HttpGetWithEntity(URI uri) {
        super();
        setURI(uri);
    }

    HttpGetWithEntity(String uri) {
        super();
        setURI(URI.create(uri));
    }
}