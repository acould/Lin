package com.lk.wechat.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUrlUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUrlUtil.class);

    public static String openUrl(String httpUrl) {
        return openUrl(httpUrl, "utf-8");
    }

    public static String openUrl(String httpUrl, String charsetName) {
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setRequestProperty("contentType", charsetName);
            httpURLConnection.setReadTimeout(3000);
            // connect & post data.
            httpURLConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    (InputStream) httpURLConnection.getInputStream(), charsetName));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            httpURLConnection.disconnect();// 断开连接

            if (LOG.isInfoEnabled())
                LOG.info("Http connection result value: " + sb.toString());

            return sb.toString();

        } catch (IOException e) {
            LOG.warn("Http connection IO error. " + e.getMessage() + ". url:" + httpUrl);
        } catch (Exception e) {
            LOG.warn("Open Http connection url error. " + e.getMessage() + ". url:" + httpUrl);
        }

        return StringUtils.EMPTY;
    }
}
