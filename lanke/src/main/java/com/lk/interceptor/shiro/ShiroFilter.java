//package com.lk.interceptor.shiro;
//
//import com.lk.entity.system.User;
//import com.lk.util.Const;
//import com.lk.util.Jurisdiction;
//import org.apache.shiro.web.servlet.AdviceFilter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author myq
// * @description 自定义shiroFilter
// * @create 2019-05-28 12:57
// */
//public class ShiroFilter extends AdviceFilter {
//
//
//    /**
//     * 请求拦截处理
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        String path = httpServletRequest.getServletPath();
//
//        if(path.contains("weiindex.do/")) {
//            System.out.println("拿到的url信息：" + path);
//            String[] aa = path.split("/");
//            String abc = "/" + aa[1] + "/" + aa[2] + "?appid=" + aa[3];
//            System.out.println("拿到的url转换的信息：" + abc);
//            request.getRequestDispatcher(abc).forward(request, response);
//            return true;
//        }else{
//            User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
//
//            if(user == null){
//                if(path.contains("/wx") || path.contains("/intenetmumber") || path.contains("/myMember") || path.contains("/indexMember")){
//                    //微信端过滤
//                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/indexMember/userNull.do");
//                    return false;
//                }else{
//                    //后台登陆过滤
//                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + Const.LOGIN);
//                    return false;
//                }
//            }else{
//                //权限校验
//                path = path.substring(1, path.length());
//                boolean b = Jurisdiction.hasJurisdiction(path);
//                if(!b){
//                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + Const.LOGIN);
//                    return false;
//                }
//                return b;
//            }
//
//        }
//
////        return super.preHandle(request, response);
//    }
//}
