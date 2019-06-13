package star.webtool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import star.fw.web.util.RequestUtil;
import star.util.StringUtil;
import star.webtool.component.LoginComponent;

/**
 * 
 * 
 * 
 * Title: 登录拦截器
 * 
 * Description:
 * 
 * Copyright: (c) 2014
 * 
 * @author haoxz11
 * @created 上午9:02:07
 * @version $Id: LoginInterceptor.java 89113 2015-06-13 10:17:14Z zhjy $
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private LoginComponent loginComponent;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (loginComponent.isLogin(request, response)) {
			return super.preHandle(request, response, handler);
		} else {
			RequestUtil.send302(response, "/login.html?url=" + StringUtil.urlencoder(RequestUtil.getURL(request)));
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}
}
