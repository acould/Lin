package com.lk.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import Decoder.BASE64Decoder;

import com.lk.communicate.server.tcp.TcpConfig;
import com.lk.communicate.server.util.Md5Encoder;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 说明：常用工具
 * 创建人：洪智鹏
 * 修改时间：2015年11月24日
 */
public class Tools {

    /**
     * 随机生成六位数验证码
     *
     * @return
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;//(Math.random()*(999999-100000)+100000)
    }

    /**
     * 随机生成16位数订单号
     *
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    public static String replaceLanker(String pre){
        return pre.replaceAll("/lanker", "");
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s) {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     * @return
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateStr(Date date) {
        return date2Str(date, "yyyy-MM-dd");
    }
    
    public static Date str2Date(String date, String format) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 按照yyyy-MM-dd的格式，字符串转日期
     *
     * @param date
     * @return
     */
    public static Date str2Date2(String date) {
        if (notEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        } else {
            return null;
        }
    }

    /**
     * 按照参数增加天数yyyy-MM-dd
     *
     * @param date
     * @param i
     * @return
     */
    public static String dateAddDay(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return date2Str(date, "yyyy-MM-dd");
    }
    /**
     * 按照参数增加天数yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param i
     * @return
     */
    public static String dateAddDay2(Date date,int i){
	    Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动 
	    date=calendar.getTime();  
	    return date2Str( date,"yyyy-MM-dd HH:mm:ss" );
	}
    /**
     * 增减年份
     * @param date
     * @param i
     * @return
     */
    public static String dateAddYear(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, i);
        date = calendar.getTime();
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 按照参数增加小时
     *
     * @param date
     * @param i
     * @return
     */
    public static String dateAddHours(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.HOUR_OF_DAY, i);//把日期小时加i小时
        date = calendar.getTime();
        return date2Str(date, "yyyy-MM-dd");
    }
    public static String dateAddHours2(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.HOUR_OF_DAY, i);//把日期小时加i小时
        date = calendar.getTime();
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照参数增加年数
     *
     * @param date
     * @param i
     * @return
     */
    public static Date sAddYear(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, i);//把日期小时加i小时
        date = calendar.getTime();
        return date;
    }

    /**
     * 按照参数增加月数
     *
     * @param date
     * @param i
     * @return
     */
    public static Date sAddMonth(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, i);//把日期小时加i小时
        date = calendar.getTime();
        return date;
    }

    /**
     * 按照参数增加天数
     *
     * @param date
     * @param i
     * @return
     */
    public static Date sAddDays(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, i);//把日期小时加i小时
        date = calendar.getTime();
        return date;
    }

    /**
     * 按照参数增加小时
     *
     * @param date
     * @param i
     * @return
     */
    public static Date sAddHours(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.HOUR_OF_DAY, i);//把日期小时加i小时
        date = calendar.getTime();
        return date;
    }

    /**
     * 按照参数增加分钟
     *
     * @param date
     * @param i
     * @return
     */
    public static Date sAddMinute(Date date, int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MINUTE, i);//把日期小时加i小时
        date = calendar.getTime();
        return date;
    }

    /**
     * 按照参数format的格式，日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 把时间根据时、分、秒转换为时间段
     *
     * @param StrDate
     */
    public static String getTimes(String StrDate) {
        String resultTimes = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;

        try {
            now = new Date();
            java.util.Date date = df.parse(StrDate);
            long times = now.getTime() - date.getTime();
            long day = times / (24 * 60 * 60 * 1000);
            long hour = (times / (60 * 60 * 1000) - day * 24);
            long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            StringBuffer sb = new StringBuffer();
            //sb.append("发表于：");
            if (hour > 0) {
                sb.append(hour + "小时前");
            } else if (min > 0) {
                sb.append(min + "分钟前");
            } else {
                sb.append(sec + "秒前");
            }

            resultTimes = sb.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultTimes;
    }

    /**
     * 如果time中带上/下午的，转换为24小时的字符串
     * @param time
     * @return
     */
    public static String parseTime(String time){
        if(time.contains("上午")){
            time = time.replace("上午", " ");
        }else if(time.contains("下午")){
            time = time.replace("下午", " ");
            Calendar c = Calendar.getInstance();
            c.setTime(Tools.str2Date(time));
            c.add(c.HOUR_OF_DAY,12);
            time = Tools.date2Str(c.getTime());
        }
        String[] arr = time.split(" ");
        time = arr[0] + " " + arr[arr.length - 1];

        return time;
    }


    /**
     * 写txt里的单行内容
     *
     * @param fileP 文件路径
     * @param content  写入的内容
     */
    public static void writeFile(String fileP, String content) {
        String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
        filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
        if (filePath.indexOf(":") != 1) {
            filePath = File.separator + filePath;
        }
        try {
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检测KEY是否正确
     *
     * @param paraname 传入参数
     * @param FKEY     接收的 KEY
     * @return 为空则返回true，不否则返回false
     */
    public static boolean checkKey(String paraname, String FKEY) {
        paraname = (null == paraname) ? "" : paraname;
        return MD5.md5(paraname + DateUtil.getDays() + ",fh,").equals(FKEY);
    }

    /**
     * 读取txt里的单行内容
     *
     * @param fileP 文件路径
     */
    public static String readTxtFile(String fileP) {
        try {
            String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";    //项目路径
            filePath = filePath.replaceAll("file:/", "");
            filePath = filePath.replaceAll("%20", " ");
            filePath = filePath.trim() + fileP.trim();
            if (filePath.indexOf(":") != 1) {
                filePath = File.separator + filePath;
            }
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {        // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);    // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    return lineTxt;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
        }
        return "";
    }


    /**
     * 获取当前月份的第一天yyyy-MM-dd
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    /**
     * 获取当前月份的最后一天yyyy-MM-dd
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime());
    }
    /**
     * 获取月份的第一天yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFirstDay2(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);  
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return format.format(c.getTime())+" 00:00:00";
    }
    /**
     * 获取月份的最后一天yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getLastDay2(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);  
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(c.getTime())+" 23:59:59";
    }
    
    

    // 将微信传入的CreateTime转换成long类型，再乘以1000
    public static String formatTime(String createTime) {
        long msgCreateTime = Long.parseLong(createTime) * 1000L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(msgCreateTime));
    }


    public static String toPicture(String base64, String filePath) {
        try {
            base64.replace("data:image/jpeg;base64,", "");
            BASE64Decoder decoder = new BASE64Decoder();

            //Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpg图片
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {

        }
        return "";
    }


    public static void pic(String base64ImgData, String filePath) throws IOException {
        if (base64ImgData.contains("data:image/jpeg;base64")) {
            base64ImgData = base64ImgData.replace("data:image/jpeg;base64,", "");
        } else if (base64ImgData.contains("data:image/png;base64")) {
            base64ImgData = base64ImgData.replace("data:image/png;base64,", "");
        }
        BASE64Decoder d = new BASE64Decoder();
        byte[] bs = d.decodeBuffer(base64ImgData);

        File ff = new File(filePath);
        if (!ff.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录  
            if (!ff.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
            }
            ff.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(filePath);
        os.write(bs);
        os.close();
    }

    public static void download(String urlString, String filename,String savePath) throws Exception {    
        // 构造URL    
        URL url = new URL(urlString);    
        // 打开连接    
        URLConnection con = url.openConnection();    
        //设置请求超时为5s    
        con.setConnectTimeout(5*1000);    
        // 输入流    
        InputStream is = con.getInputStream();    
        
        // 1K的数据缓冲    
        byte[] bs = new byte[1024];    
        // 读取到的数据长度    
        int len;    
        // 输出的文件流    
       File sf=new File(savePath);    
       if(!sf.exists()){    
           sf.mkdirs();    
       }    
       OutputStream os = new FileOutputStream(savePath+filename);    
        // 开始读取    
        while ((len = is.read(bs)) != -1) {    
          os.write(bs, 0, len);    
        }    
        // 完毕，关闭所有链接    
        os.close();    
        is.close();    
        System.out.println("网络图片保存成功！");
    }


		
   
   public static String beforeDay() {
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
       Date date=new Date();  
       Calendar calendar = Calendar.getInstance();  
       calendar.setTime(date);  
       calendar.add(Calendar.DAY_OF_MONTH, -1);  
       date = calendar.getTime();  
       System.out.println(sdf.format(date)); 
       return sdf.format(date);
   }
   
   /** 
    * 设置时间 
    * @param year 
    * @param month 
    * @param date 
    * @return 
    */  
   public static Calendar setCalendar(int year,int month,int date){  
       Calendar cl = Calendar.getInstance();  
       cl.set(year, month-1, date);  
       return cl;  
   }  
     
   /** 
    * 获取当前时间的前一天时间 
    * @param cl 
    * @return 
    */  
   private static Calendar getBeforeDay(Calendar cl){  
       //使用roll方法进行向前回滚  
       //cl.roll(Calendar.DATE, -1);  
       //使用set方法直接进行设置  
       int day = cl.get(Calendar.DATE);  
       cl.set(Calendar.DATE, day-1);  
       return cl;  
   }  
     
   /** 
    * 获取当前时间的后一天时间 
    * @param cl 
    * @return 
    */  
   private static Calendar getAfterDay(Calendar cl){  
       //使用roll方法进行回滚到后一天的时间  
       //cl.roll(Calendar.DATE, 1);  
       //使用set方法直接设置时间值  
       int day = cl.get(Calendar.DATE);  
       cl.set(Calendar.DATE, day+1);  
       return cl;  
   }  
    
   
   public static String getCenterMsgId(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String time = formatter.format(new Date());
		return time.substring(0, time.length()-1);
	}
   public static String getCenterMsgId2(String msgId){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = formatter.format(new Date());
       String rand = RandomStringUtils.randomNumeric(7);
       return msgId + time + rand;
	}

   	public static String getKey(String barId, String timestamp){
		String reverse = new StringBuffer(barId  + timestamp + TcpConfig.public_key).reverse().toString();
		return Md5Encoder.encode(reverse);
	}
   
   /**
	 * 检查ip格式是否正确，true表示正确
	 * @param ip
	 * @return
	 */
	public static boolean isIpReg(String ip){
		String ipReg = "(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";
		if(ip.matches(ipReg)){
			return true;
		}
		return false;
	}

	/**
	 * 根据request获取ip
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		//X-Forwarded-For：Squid 服务代理
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//Proxy-Client-IP：apache 服务代理
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//WL-Proxy-Client-IP：weblogic 服务代理
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			//HTTP_CLIENT_IP：有些代理服务器
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        //X-Real-IP：nginx服务代理
			ip = request.getHeader("X-Real-IP");
	    }
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		
		//有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
	    if (ip != null && ip.length() != 0) {
	        ip = ip.split(",")[0];
	    }
	    //还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		System.out.println("request的ip地址=="+ip);
		return ip;
	}

    /**
     * 通过身份证号获取生日 (MM-dd)
     * @param cardEd
     * @return
     */
    public static String getBirthByCard(String cardEd) {
	    if(StringUtil.isEmpty(cardEd)){
            return "";
        }
	    if(cardEd.length() == 15){
           return cardEd.substring(8, 12);
        }else if(cardEd.length() == 18){
            return cardEd.substring(10, 14);
        }
        return "";
    }

    /**
     * 两个整数相除，返回两位小数的值，四舍五入
     * @param a
     * @param b
     * @return
     */
    public static String getDatalimit2(int a, int b){
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        return df.format((float)a/b);//返回的是String类型
    }

    public static void main(String[] args)throws Exception {

        String birth = getBirthByCard("330724199405075614");
        String now = Tools.dateStr(new Date()).substring(5).replace("-","");
        System.out.println(birth + "---"+ now);
        if(!birth.equals(now)){
            System.out.println("今天不是我生日");
        }

        String today = Tools.date2Str(Tools.sAddDays(new Date(), -1));

        System.out.println(today);

    }



}
