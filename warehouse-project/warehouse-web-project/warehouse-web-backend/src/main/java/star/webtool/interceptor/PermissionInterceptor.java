package star.webtool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import star.constant.SystemConstants;
import star.facade.ecmanager.purview.PurviewFacade;
import star.fw.web.exception.PermissionException;
import star.webtool.component.LoginComponent;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoginComponent loginComonent;
	
	@Autowired
	private PurviewFacade purviewFacade;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (uri.equals("/") || uri.equals("")) {
			request.getRequestDispatcher("/index.html").forward(request, response);
			return false;
		}
		
		long userId = loginComonent.getLoginUserId();
		boolean authByUrlret = purviewFacade.getAuthByUrl(userId, uri,SystemConstants.SYSTEM_PLATFORM);
//		logger.info("PermissionInterceptor userId={},uri={},authByUrlret={}",userId,uri,authByUrlret);
//		if (!purviewFacade.getAuthByUrl(userId, uri)) {
		if(!authByUrlret) {
			logger.info("PermissionInterceptor权限不足 userId={},uri={},authByUrlret={}",userId,uri,authByUrlret);
			throw new PermissionException("权限不足");
		}
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

}
