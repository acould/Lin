package com.lk.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lk.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**登录验证过滤器(废弃  com.lk.interceptor替代)
 * @author 洪智鹏
 *
 */
public class LoginFilter extends BaseController implements Filter {

    protected Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
	public void init(FilterConfig fc){
		//FileUtil.createDir("d:/FH/topic/");
	}

    @Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		chain.doFilter(req, res); // 调用下一过滤器
	}


}
