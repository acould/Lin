package star.webtool.external;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import star.config.Config;
import star.facade.ecmanager.vo.RequestAuthHeader;
import star.facade.ecmanager.vo.RequestResult;
import star.share.util.external.TimestampTypeAdapter;
import star.util.HttpUtil;

/**
 * 
 * 
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: (c) 2015
 * 
 * @author haoxz11
 * @created 上午10:26:32
 * @version $Id: ExternalHelper.java 90275 2015-06-27 06:46:14Z wangjh $
 */
public class ExternalHelper {

    /**
     * Logger for this class
     */
    private static final Logger logger           = LoggerFactory.getLogger(ExternalHelper.class);

    private static final String DefaultUserAgent = "GuangCPS ApiSdk Client v1.0";

    /**
     * key
     */
    private static final String token;

    private static final String url;

    public static final String  CHARSET          = "UTF-8";

    static
    {
        token = Config.getString("star.token");
        url = Config.getString("star.server.url");
    }
    
    private  final static  Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new TimestampTypeAdapter()).create();
    
    public static <T> T getResponse(String cmd, Object request, Type type)
    {
        RequestResult<Object> result = new RequestResult<Object>();
        result.setAuthHeader(new RequestAuthHeader(token, 1));
        result.setRequest(request);

        HttpPost post = HttpUtil.getPostRequest(new StringBuilder().append(url).append("/").append(cmd).toString());
        post.addHeader("User-Agent", DefaultUserAgent);
        post.addHeader("X-Requested-With", "XMLHttpRequest");

        try
        {
            StringEntity entity = new StringEntity(gson.toJson(result), "UTF-8");
            post.setEntity(entity);

            return gson.fromJson(HttpUtil.execute(post, CHARSET),type);

        }
        catch (Exception e)
        {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            logger.warn(out.toString());
        }
        return null;
    }
    
    public static String getJsonResponse(String cmd, Object request)
    {
        RequestResult<Object> result = new RequestResult<Object>();
        result.setAuthHeader(new RequestAuthHeader(token, 1));
        result.setRequest(request);

        HttpPost post = HttpUtil.getPostRequest(new StringBuilder().append(url).append("/").append(cmd).toString());
        post.addHeader("User-Agent", DefaultUserAgent);
        post.addHeader("X-Requested-With", "XMLHttpRequest");

        try
        {

        	logger.info("退款返回状态="+gson.toJson(result));
            StringEntity entity = new StringEntity(gson.toJson(result), "UTF-8");
            post.setEntity(entity);

            return HttpUtil.execute(post, CHARSET);

        }
        catch (Exception e)
        {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            logger.warn(out.toString());
        }
        return null;
    }
}
