package star.webtool.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.ImmutableList;

import star.constant.SystemConstants;
import star.facade.ecmanager.menu.MenuFacade;
import star.facade.ecmanager.purview.PurviewFacade;
import star.facade.ecmanager.purview.vo.SysPermissionVo;

/**
 * 菜单拦截器
 * 
 */
public class MenuInterceptor extends HandlerInterceptorAdapter{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MenuFacade menuFacade;
	@Autowired
	private PurviewFacade purviewFacade;
	
	private final static ImmutableList<String> catagoryList=ImmutableList.of("menu","page");//需要定位的权限类型
	
	private final static String PERMISSION_SHOW_CATEGORY="page";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				
		return super.preHandle(request, response, handler);
	}

	/**
	 * 根据url定位到具体的菜单id
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {			
		String url = request.getRequestURI();

		//根据url查询权限信息
		SysPermissionVo permissionVo=purviewFacade.getPermissionByUrl(url);
		if(permissionVo!=null && isContain(permissionVo.getCategory())){
			Long locateId = menuFacade.getMenuIdByUrl(url,SystemConstants.SYSTEM_PLATFORM);
			modelAndView.addObject("locateId",locateId);
			logger.info("menuInterceptor locateId={},url={},SysPermissionVo={}",locateId,url,permissionVo);
			//如果为页面，返回权限名称
			if(PERMISSION_SHOW_CATEGORY.equals(permissionVo.getCategory())){
				modelAndView.addObject("pageName",permissionVo.getName());
			}
			
		}
	}
	
	/**
	 * 判断权限类别是否是菜单或页面
	 */
	private boolean isContain(String category){
		
		return catagoryList.contains(category);
	}
	
}
