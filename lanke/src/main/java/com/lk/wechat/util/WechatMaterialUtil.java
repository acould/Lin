package com.lk.wechat.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lk.util.PageData;
import com.lk.util.PathUtil;


/**
 * 公众平台通用接口工具类
 *
 * @author lm
 */
public class WechatMaterialUtil {
    private static Logger log = LoggerFactory.getLogger(WechatMaterialUtil.class);

    /**
     * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
     *
     * @param accessToken
     * @param filePath
     * @param type
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterialEver(String accessToken, String filePath, String type) throws Exception {
        JSONObject resultJSON = null;
        String filePath1 = null;
        if (filePath.indexOf("ueditor") > 0) {
            int a = filePath.indexOf("ueditor");
            filePath1 = PathUtil.getClasspath() + filePath.substring(a - 1);
        } else {
            filePath1 = filePath;
        }

        File file = new File(filePath);
        try {
            System.out.println("开始上传" + type + "永久素 材---------------------");
            //开始获取证书
            if (!StringUtils.isNotBlank(accessToken)) {
                System.out.println("accessToken is null");
                return resultJSON;
            }

            //上传素材
            String path = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + accessToken + "&type=" + type;
            String result = null;
            URL httpClient = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\"" + file.getName() + "\"\r\n");

            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");

            OutputStream out = conn.getOutputStream();
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(filePath1));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            try {
                // 定义BufferedReader输入流来读取URL的响应

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                if (result == null) {

                    result = buffer.toString();

                }

            } catch (IOException e) {

                System.out.println("发送POST请求出现异常！" + e);

                e.printStackTrace();

                throw new IOException("数据读取异常");

            } finally {

                if (reader != null) {

                    reader.close();

                }

            }
            result = result.replaceAll("[\\\\]", "");
            System.out.println("上传图文中图片的返回码" + result);
            resultJSON = JSONObject.fromObject(result);
            if (resultJSON != null) {
                if (resultJSON.get("media_id") != null) {
                    System.out.println("上传" + type + "永久素材成功");
                    return resultJSON;
                } else {
                    System.out.println("上传" + type + "永久素材失败");
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("结束上传" + type + "永久素材---------------------");
        }
        return resultJSON;
    }

    /**
     * 上传图片到微信服务器(本接口所上传的图片不占用公众号的素材库中图片数
     * <p>
     * 量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下)
     *
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterialImg(String accessToken, String filePath) throws Exception {
        String filePath1 = null;
        if (filePath.indexOf("ueditor") > 0) {
            int a = filePath.indexOf("ueditor");
            filePath1 = PathUtil.getClasspath() + filePath.substring(a - 1);
        } else {
            filePath1 = filePath;
        }
        JSONObject resultJSON = null;
        File file = new File(filePath);
        try {
            System.out.println("开始上传图文消息内的图片---------------------");

            //开始获取证书
            if (!StringUtils.isNotBlank(accessToken)) {
                System.out.println("accessToken is null");
                return null;
            }

            //上传图片素材
            String path = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + accessToken;

            String result = null;
            URL httpclient = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //conn.connect();
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");

            OutputStream out = conn.getOutputStream();
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            filePath = filePath.replaceAll("\\\\", "/");
            DataInputStream in = new DataInputStream(new FileInputStream(filePath1));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            try {
                // 定义BufferedReader输入流来读取URL的响应

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                if (result == null) {

                    result = buffer.toString();

                }

            } catch (IOException e) {

                System.out.println("发送POST请求出现异常！" + e);

                e.printStackTrace();

                throw new IOException("数据读取异常");

            } finally {

                if (reader != null) {
                    reader.close();
                }
            }
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            resultJSON = JSONObject.fromObject(result);

            if (resultJSON != null) {
                if (resultJSON.get("url") != null) {
                    System.out.println("上传图文消息内的图片成功");
                    return resultJSON;
                } else {
                    System.out.println("上传图文消息内的图片失败");
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("结束上传图文消息内的图 片---------------------");
        }
        return resultJSON;
    }


    /**
     * 上传永久视频素材接口
     *
     * @return
     * @throws Exception
     */
    public static JSONObject postVideo(String filePath,
                                       String title, String introduction, String accessToken) {
        String filePath1 = null;
        if (filePath.indexOf("ueditor") > 0) {
            int a = filePath.indexOf("ueditor");
            filePath1 = PathUtil.getClasspath() + filePath.substring(a - 1);
        } else {
            filePath1 = filePath;
        }

        File file = new File(filePath);
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
                + accessToken + "&type=video";
        String result = null;
        try {

            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName()).getBytes());
            output.write("Content-Type: video/mp4 \r\n\r\n".getBytes());
            byte[] data = new byte[1024];
            int len = 0;
            FileInputStream input = new FileInputStream(filePath1);
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
            output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", title, introduction).getBytes());
            output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();
            InputStream resp = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((len = resp.read(data)) > -1)
                sb.append(new String(data, 0, len, "utf-8"));
            resp.close();
            result = sb.toString();
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            resultJSON = JSONObject.fromObject(result);

        } catch (IOException e) {
            log.error("postFile数据传输失败", e);
        }
        log.info("{}: result={}", url, result);
        return resultJSON;
    }

    /**
     * 上传临时图片素材接口
     *
     * @return
     * @throws Exception
     */

    public static JSONObject postImg(String filePath,
                                     String title, String introduction, String accessToken, String sercet) {
        JSONObject resultJSON = null;
        File file = new File(filePath);
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
                + accessToken + "&type=image";
        if (!file.exists())
            return null;
        String result = null;
        try {

            URL httpclient = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //conn.connect();
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");

            OutputStream out = conn.getOutputStream();
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中

            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            try {
                // 定义BufferedReader输入流来读取URL的响应

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                if (result == null) {

                    result = buffer.toString();

                }

            } catch (IOException e) {

                System.out.println("发送POST请求出现异常！" + e);

                e.printStackTrace();

                throw new IOException("数据读取异常");

            } finally {

                if (reader != null) {

                    reader.close();

                }

            }
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            resultJSON = JSONObject.fromObject(result);

            if (resultJSON != null) {
                if (resultJSON.get("media_id") != null) {
                    System.out.println("上传图文消息内的图片成功");
                    return resultJSON;
                } else {
                    System.out.println("上传图文消息内的图片失败");
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("结束上传图文消息内的图 片---------------------");
        }
        return resultJSON;
    }


    /**
     * 上传图文信息
     *
     * @return
     * @throws Exception
     */

    public static JSONObject postTw(String mes,
                                    String accessToken) {
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="
                + accessToken;
        try {
            URL httpClient = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpClient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            System.out.println("ccccc:" + mes);
            os.write(mes.getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8").replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }


    /**
     * 新增永久图文素材
     *
     * @return
     * @throws Exception
     */
    public static JSONObject postNew(PageData pd, String accessToken) {
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="
                + accessToken;
        StringBuffer sb = new StringBuffer();
        JSONObject MenuButton = new JSONObject();
        JSONArray articles = new JSONArray();
            JSONObject firstButton = new JSONObject();
            firstButton.put("title", pd.get("TITLE"));
            firstButton.put("thumb_media_id", pd.get("THUMB_MEDIA_ID"));
            firstButton.put("author", pd.get("CREATE_USER"));
            firstButton.put("digest", pd.get("DIGEST"));
            firstButton.put("show_cover_pic", 0);
            firstButton.put("content", pd.get("CONTENT"));
            firstButton.put("content_source_url", pd.get("CONTENT_SOURCE_URL"));
        articles.add(firstButton);
        MenuButton.put("articles", articles);

        resultJSON = HttpUtil.wechatRequest(url, "POST", MenuButton, "新增永久图文素材返回码");

        return resultJSON;
    }

    public static JSONObject postNews(List<PageData> pdList, String accessToken) {
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + accessToken;
        StringBuffer sb = new StringBuffer();
        JSONObject MenuButton = new JSONObject();
        JSONArray articles = new JSONArray();

        for (int i = 0; i < pdList.size(); i++) {
            JSONObject firstButton = new JSONObject();
            PageData pd = pdList.get(i);
            firstButton.put("title", pd.getString("TITLE"));
            firstButton.put("thumb_media_id", pd.getString("THUMB_MEDIA_ID"));
            firstButton.put("author", pd.getString("CREATE_USER"));
            firstButton.put("digest", pd.getString("DIGEST"));
            firstButton.put("show_cover_pic", 0);
            firstButton.put("content", pd.getString("CONTENT"));
            firstButton.put("content_source_url", pd.getString("CONTENT_SOURCE_URL"));
//            firstButton.put("need_open_comment", 1);//是否打开评论，0不打开，1打开
//            firstButton.put("only_fans_can_comment", 0);//Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论

            articles.add(firstButton);
        }
        MenuButton.put("articles", articles);
        sb.append(MenuButton);
        try {
            URL httpclient = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(sb.toString().getBytes("UTF-8"));// 传入参数
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8").replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            log.info("新增多个永久图文素材返回码:" + message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    /**
     * 修改永久图文素材
     *
     * @return
     * @throws Exception
     */
    public static JSONObject updateNew(PageData pd, String accessToken, String index) {
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=" + accessToken;
        StringBuffer sb = new StringBuffer();

        JSONObject MenuButton = new JSONObject();
        MenuButton.put("media_id", pd.getString("INTERNETMATERIAL_ID"));
        MenuButton.put("index", index);
        JSONObject firstButton = new JSONObject();
        firstButton.put("title", pd.getString("TITLE"));
        firstButton.put("thumb_media_id", pd.getString("THUMB_MEDIA_ID"));
        firstButton.put("author", pd.getString("CREATE_USER"));
        firstButton.put("digest", pd.getString("DIGEST"));
        firstButton.put("show_cover_pic", 0);
        firstButton.put("content", pd.getString("CONTENT"));
        firstButton.put("content_source_url", pd.getString("CONTENT_SOURCE_URL"));
        MenuButton.put("articles", firstButton);

        resultJSON = HttpUtil.wechatRequest(url, "POST", MenuButton, "修改永久图文素材返回码");

        return resultJSON;
    }


    /**
     * 获取永久图文素材
     *
     * @return
     * @throws Exception
     */

    public static JSONObject getVedioMaterial(String mediaId, String accessToken) {
        JSONObject resultJSON = null;
        String url = "	 https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="
                + accessToken;
        StringBuffer sb = new StringBuffer();
        JSONObject MenuButton = new JSONObject();
        MenuButton.put("media_id", mediaId);

        resultJSON = HttpUtil.wechatRequest(url, "POST", MenuButton, "查询永久图文素材返回码");

        return resultJSON;
    }


    /**
     * 删除图文素材
     *
     * @return
     * @throws Exception
     */

    public static JSONObject deleteMaterial(String mediaId, String accessToken) {
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token="
                + accessToken;
        StringBuffer sb = new StringBuffer();
        JSONObject MenuButton = new JSONObject();
        MenuButton.put("media_id", mediaId);
        sb.append(MenuButton);
        try {
            URL httpclient = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8").replaceAll("[\\\\]", "");
            resultJSON = JSONObject.fromObject(message1);
            log.info("删除永久图文素材返回码:" + message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    public static JSONObject getMaterialList(String accessToken, String type, String offset, String count) {
        JSONObject resultJSON = null;
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="
                + accessToken;
        StringBuffer sb = new StringBuffer();
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("offset", offset);
        json.put("count", count);
        sb.append(json);
        try {
            URL httpclient = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(sb.toString().getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();
            InputStream is = conn.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message1 = new String(jsonBytes, "UTF-8").replaceAll("[\\\\]", "");
            log.info("获取永久图文素材列表返回码:" + message1);
            resultJSON = JSONObject.fromObject(message1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

}


