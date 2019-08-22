package com.lk.util.http;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author： <a href="mailto:wangfei@tianyancha.com">wangfei</a>
 * @date: 2019/2/21
 * @version: 1.0.0
 */
public final class HttpClientFactory {//构建heepClient操作工具类
	private static final int DEFAULT_CONN_REQUEST_TIMEOUT = 50000;
	private static final int DEFAULT_SOCKET_TIMEOUT = 150000;
	private static final int DEFAULT_CONN_TIMEOUT = 50000;

	//n个参数不同的重载方法
	private HttpClientFactory() {
	}
	public static CloseableHttpClient getHttpClient(){
		return getHttpClient(null, null, null, DEFAULT_CONN_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONN_TIMEOUT);
	}
	public static CloseableHttpClient getHttpClient(HttpHost proxy) {
		return getHttpClient(proxy, null, null, DEFAULT_CONN_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONN_TIMEOUT);
	}
	
	public static CloseableHttpClient getHttpClient(RedirectStrategy redirectStratery) {
		return getHttpClient(null, redirectStratery, null, DEFAULT_CONN_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONN_TIMEOUT);
	}
	
	public static CloseableHttpClient getHttpClient(HttpRequestRetryHandler retryHandler) {
		return getHttpClient(null, null, retryHandler, DEFAULT_CONN_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CONN_TIMEOUT);
	}
	
	public static CloseableHttpClient getHttpClient(int connectRequestTimeout, int socketTimeout, int connectTimeout) {
		return getHttpClient(null, null, null, connectRequestTimeout, socketTimeout, connectTimeout);
	}

	//其实调的都是这个
	public static CloseableHttpClient getHttpClient(HttpHost proxy, RedirectStrategy redirectStratery, HttpRequestRetryHandler retryHandler, int connectRequestTimeout, int socketTimeout, int connectTimeout) {
		RequestConfig requestConfig = RequestConfig.custom()
												   .setConnectionRequestTimeout(connectTimeout)
												   .setSocketTimeout(socketTimeout)
												   .setConnectTimeout(connectTimeout)
												   .build();
		HttpClientBuilder builder = HttpClientBuilder.create();

		//如果需要，设置fiddler代理 proxy = new HttpHost("127.0.0.1", 8888, "http");
		if(null != proxy) {
			builder.setProxy(proxy);
		}
		if(null != redirectStratery) {
			builder.setRedirectStrategy(redirectStratery);
		}
		if(null != retryHandler) {
			builder.setRetryHandler(retryHandler);
		}
		builder.setDefaultRequestConfig(requestConfig);
		builder.setDefaultCookieStore(new BasicCookieStore());
		return builder.build();
	}

}

