package star.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.gson.Gson;

import star.facade.ecmanager.manager.vo.ManagerVo;
import star.fw.web.support.IntegerPropertiesSupport;
import star.fw.web.support.LongPropertiesSupport;
import star.fw.web.support.StringPropertiesSupport;
import star.fw.web.util.RequestUtil;
import star.fw.web.util.ServletAttributes;
import star.util.StringUtil;
import star.vo.result.ResultVo;

/**
 * 
 * 
 * 
 * Title: 基础Controller
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午10:19:10
 * @version $Id: BasicController.java 123184 2015-12-17 07:25:17Z xuxh $
 */
public abstract class BasicController {

	public static final Gson gson = new Gson();
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 获取登陆用户的信息
	 * @return
	 */
	protected ManagerVo getSysUser(){
		return (ManagerVo) ServletAttributes.getRequest().getAttribute("sysUser");
	}

	/**
	 * 文本直接输出
	 * 
	 * @param s
	 * @param response
	 */
	protected void out(String s) {
		try {
			PrintWriter out = ServletAttributes.getResponse().getWriter();
			out.print(s);
			out.close();
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * 判断请求是否为ajax
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isAjax() {
		return RequestUtil.isAjax(ServletAttributes.getRequest());
	}

	/**
	 * 判断是否为爬虫
	 * 
	 * @param request
	 * @return
	 */
	public boolean isRobot() {
		return RequestUtil.isRobot(ServletAttributes.getRequest());
	}

	/**
	 * 判断是否为手机访问
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isMobile() {
		return RequestUtil.isMobile(ServletAttributes.getRequest());
	}

	/**
	 * 判断是否为ipad访问
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isIpad() {
		return RequestUtil.isIpad(ServletAttributes.getRequest());
	}

	/**
	 * 是否为UC浏览器
	 * 
	 * @param request
	 * @return
	 */
	public boolean checkUCJisu() {
		return RequestUtil.checkUCJisu(ServletAttributes.getRequest());
	}

	// /**
	// * 302跳转
	// *
	// * @param response
	// * @param redirectUrl
	// */
	// protected String send302(String redirectUrl) {
	// // RequestUtil.send302(response, redirectUrl);
	//
	// StringBuilder sb=new
	// StringBuilder().append("redirect:").append(redirectUrl);
	// return sb.toString();
	// }

	/**
	 * 302跳转
	 * 
	 * @param response
	 * @param redirectUrl
	 */
	protected void send302(HttpServletResponse response, String redirectUrl) {
		RequestUtil.send302(response, redirectUrl);
	}

	/**
	 * 301跳转
	 * 
	 * @param response
	 * @param redirectUrl
	 */
	protected void send301(HttpServletResponse response, String redirectUrl) {
		RequestUtil.send301(response, redirectUrl);
	}

	/**
	 * 修改response的状态码
	 * 
	 * @param response
	 * @param code
	 */
	protected void sendError(HttpServletResponse response, int code) {
		try {
			response.sendError(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到来源
	 * 
	 * @param request
	 * @return
	 */
	protected String getReferer() {
		return ServletAttributes.getRequest().getHeader("Referer");
	}

	/**
	 * 得到来源
	 * 
	 * @param request
	 * @return
	 */
	protected String getIp() {
		return RequestUtil.getRealIp(ServletAttributes.getRequest());
	}
	/**
	 * 获取当前登陆人信息
	 * @return
	 */
	protected  ManagerVo getManageVo(HttpServletRequest request){
		return (ManagerVo) request.getAttribute("sysUser");
	}

	@InitBinder
	// 必须有一个参数WebDataBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(int.class, new IntegerPropertiesSupport());
		binder.registerCustomEditor(Integer.class, new IntegerPropertiesSupport());
		binder.registerCustomEditor(Long.class, new LongPropertiesSupport());
		binder.registerCustomEditor(String.class, new StringPropertiesSupport());
	}
	
	/**
	 * 将获取的时间字符串转化成date对象
	 * @param dateString
	 * @return Date
	 */
	public Date parseDate(String dateString){
	    try {
	        if(StringUtil.isEmpty(dateString)){
	            return null;
	        }
           return simpleDateFormat.parse(dateString); 
        } catch (Exception e) {
            throw new RuntimeException("时间转换异常");
        }
	}
	
	/**
	 * 校验参数中inputMerchantId 是否等于当前用户的企业merchantid。
	 * 注意：同一企业，只能 操作自己企业内部的功能
	 * @param inputMerchantId
	 * @return
	 * @Author:chistar
	 * @Since : 2018年11月13日下午11:21:45
	 */
	public ResultVo<Integer> checkManagerMerchantId(Long inputMerchantId) {
		ManagerVo muser = this.getSysUser();
		ResultVo<Integer> ret = new ResultVo<Integer>();
		ret.setSuccess(false);
		 if(inputMerchantId==null ||  !inputMerchantId.equals(muser.getMerchantId()) ) {
			ret.setResultDes("企业参数异常");
			ret.setSuccess(false);
			return ret;
		 }
		ret.setSuccess(true);
		return ret;
	}

}
