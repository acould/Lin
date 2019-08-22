package com.lk.filter;

import com.lk.util.PathUtil;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author myq
 * @description 静态资源过滤器
 * @create 2019-05-28 12:17
 */
public class StaticSourceFilter implements Filter {

    protected Logger log = LoggerFactory.getLogger(StaticSourceFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getServletPath();
        // 读取流文件渲染页面
        showPage(uri, request, response, filterChain);
    }

    @Override
    public void destroy() {

    }


    private void showPage(String uri, HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        //过滤规则，如果有图片则渲染，提高访问速度
        log.info("------------------"+uri);
        if(uri.contains("/uploadFiles/")){
            String mimeType = "";
            try {
                MagicMatch match = Magic.getMagicMatch(new File(PathUtil.getClasspath() + uri),true);
                mimeType = match.getMimeType() ;
            } catch (Exception e1) {
                log.error(e1.getClass().getName()+":"+e1.getMessage());
                e1.printStackTrace();
            }
            response.setContentType(mimeType);

        }else {
            chain.doFilter(request, response);
            return ;
        }
        ServletOutputStream out;
        // 通过文件路径获得File对象
        File html_file = new File(PathUtil.getClasspath() + uri);
        try {
            FileInputStream inputStream = new FileInputStream(html_file);
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, b);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            log.error(e.getClass().getName()+":"+e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.getClass().getName()+":"+e.getMessage());
            e.printStackTrace();
        }
    }
}
