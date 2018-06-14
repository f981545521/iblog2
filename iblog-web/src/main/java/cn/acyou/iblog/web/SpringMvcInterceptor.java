package cn.acyou.iblog.web;

import cn.acyou.iblog.constant.AppConstant;
import cn.acyou.iblog.model.User;
import cn.acyou.iblog.utility.IbStatic;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static cn.acyou.iblog.utility.IbStatic.getRemoteIp;

/**
 * @author youfang
 * @date 2018-03-03 12:57
 **/
public class SpringMvcInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteIp = getRemoteIp(request);
        IbStatic.setIp(remoteIp);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        IbStatic.setAction(request.getRequestURI());
        if (user != null){
            IbStatic.setUser(user.getId());
        }else {
            IbStatic.setUser(AppConstant.DEFAULT_USER);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        IbStatic.clearThreadLocal();
    }
}
