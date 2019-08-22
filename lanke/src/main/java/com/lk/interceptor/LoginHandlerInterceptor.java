package com.lk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.lk.entity.system.User;
import com.lk.util.Const;
import com.lk.util.Jurisdiction;
/**
 *
* 类名称：登录过滤，权限验证
* 类描述：
*
* 作者单位：
* 联系方式：
* 创建时间：2015年11月2日
* @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
		String path = httpServletRequest.getServletPath();

//        System.out.println("path"+path);
        if(path.matches(Const.NO_INTERCEPTOR_PATH)){
            if(path.contains("weiindex.do/")){
                System.out.println("拿到的url信息：" + path);
                String[] aa=path.split("/");
                String abc="/"+aa[1]+"/"+aa[2]+"?appid="+aa[3];
                System.out.println("拿到的url转换的信息：" + abc);
                httpServletRequest.getRequestDispatcher(abc).forward(httpServletRequest, httpServletResponse);
                return true;
            }
            return true;
        }else{
            User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();

            if(user == null){
                if(path.contains("/wx") || path.contains("/intenetmumber") || path.contains("/myMember") || path.contains("/indexMember")){
                    //微信端过滤
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/indexMember/userNull.do");
                    return false;
                }else{
                    //后台登陆过滤
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + Const.LOGIN);
                    return false;
                }
            }else{
                if(path.contains("/wx") || path.contains("/intenetmumber") || path.contains("/myMember") || path.contains("/indexMember")){
                    //微信端过滤
                    return true;
                }else{
                    //权限校验
                    path = path.substring(1, path.length());
                    boolean b = Jurisdiction.hasJurisdiction(path);
                    if(!b){
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + Const.LOGIN);
                        return false;
                    }
                    return b;
                }
            }

        }

	}

}
