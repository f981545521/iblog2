package cn.acyou.iblog.web;

import cn.acyou.iblog.model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
 * 过滤器
 * @author youfang
 * @date 2018-02-28 20:11
 */
public class AccessFilter implements Filter {

    private final Logger log=Logger.getLogger(getClass());

    private String[] visit = new String[]{"/iblog/web/login.html", "/iblog/web/cover.html", "/iblog/web/index.html", "/iblog/web/register.html"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        String path = req.getRequestURI();
        log.warn("doFilter--->当前访问URL：" + path);
/*        //判断访问路径是否以login结尾
        if (Arrays.asList(visit).contains(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //获得session，从session中获取登录用户
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            //如果没有登录就重定向到登录页面
            //System.out.println(req.getContextPath());
            res.sendRedirect("/iblog/web/cover.html");
            return;
        }*/
        //如果登录过，就放过请求。注：Servlet/JSP之间是转发；HTML之间没有转发；必须重定向；
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
