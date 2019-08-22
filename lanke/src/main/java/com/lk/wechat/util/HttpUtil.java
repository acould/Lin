package com.lk.wechat.util;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lk.util.StringUtil;


public class HttpUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	
	/**
	 * 向微信服务器发起请求
	 * @param url：接口地址
	 * @param json：请求参数
	 * @param message：请求说明
	 * @return
	 */
	public static JSONObject wechatRequest(String url,String method,JSONObject json,String message){
 		JSONObject result = new JSONObject();
 		try {
            URL httpclient =new URL(url);
            HttpURLConnection conn =(HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
//            conn.setReadTimeout(3500);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            conn.setDoOutput(true);        
            conn.setDoInput(true);
            conn.connect();
            OutputStream os= conn.getOutputStream();    
            if(StringUtil.isNotEmpty(json)){
            	os.write(json.toString().getBytes("UTF-8"));//传入参数    
            }
            os.flush();
            os.close();
            
            InputStream is =conn.getInputStream();
            //防止返回数据的数据过长，导致接收不全
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
        	while ((line = reader.readLine()) != null) {    
        		sb.append(line + "\n");
        	}
//            int size =is.available();
//            byte[] jsonBytes =new byte[size];
//            is.read(jsonBytes);
//            String message1 = new String(jsonBytes,"UTF-8");
//            log.info(message+"返回码:"+message1);
//            result = JSONObject.fromObject(message1);
        	log.info(message+"返回码:"+sb.toString());
        	result = JSONObject.fromObject(sb.toString());
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
 	}

	
	public static JSONObject wechatRequest2(String url,String method,JSONObject json,String message){
 		JSONObject result = new JSONObject();
 		try {
            URL httpclient =new URL(url);
            HttpURLConnection conn =(HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setDoOutput(true);        
            conn.setDoInput(true);
            conn.connect();
            OutputStream os= conn.getOutputStream();    
            if(StringUtil.isNotEmpty(json)){
            	os.write(json.toString().getBytes("UTF-8"));//传入参数    
            }
            os.flush();
            os.close();
            
            InputStream is =conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
        	while ((line = reader.readLine()) != null) {    
        		sb.append(line + "\n");
        	}
        	
            System.out.println("调用接口信息----"+message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
 	}
	
	/**
	 * 上传素材请求
	 * @param URL 接口地址
	 * @param filePath 素材文件路径
	 * @param message 请求说明
	 * @return
	 */
	public static JSONObject wechatUpload(String URL,String filePath,JSONObject videoJSON,String message){
		JSONObject result = new JSONObject();
		File file = new File(filePath);
		
		try{
			URL url = new URL(URL);
			HttpURLConnection conn =(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(5000);
	        conn.setReadTimeout(2000);
			//请求头信息
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
			conn.setRequestProperty("Charset", "UTF-8");
			String boundary = "----------" + System.currentTimeMillis();
			conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ boundary);
			//请求正文信息
			// 第一部分
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filelength=\""+file.length()+"\";filename=\""  + file.getName() + "\"\r\n"); 
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			
			//获取输出流
			OutputStream out= conn.getOutputStream();
			// 输出表头
			out.write(head);
			// 文件正文部分
	        // 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(filePath));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			//结尾部分
			byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线 
			out.write(foot);
			
			if(StringUtil.isNotEmpty(videoJSON)){
				StringBuilder sb2 = new StringBuilder();
				sb2.append("--"); // 必须多两道线
				sb2.append(boundary);
				sb2.append("\r\n");
				sb2.append("Content-Disposition: form-data;name=\"description\";");
				sb2.append("Content-Type:application/octet-stream\r\n\r\n");
	            out.write(sb2.toString().getBytes());
	            out.write(videoJSON.toString().getBytes());
	            out.write(("\r\n--" + boundary + "--\r\n").getBytes("utf-8"));// 定义最后数据分隔线
			}
			
	        out.flush();
	        out.close();
	        in.close();
	        
	        StringBuffer buffer = new StringBuffer();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        String message1 = buffer.toString();
	        log.info(message+"返回码:"+message1);
	        result = JSONObject.fromObject(message1);
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * http请求
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public static String httpRequest(String requestUrl, String requestMethod,String outputStr) {
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}


}
