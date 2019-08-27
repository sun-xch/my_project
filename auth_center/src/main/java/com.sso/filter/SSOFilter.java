package com.sso.filter;

import com.sso.commons.utils.SSOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SSOFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        //1.判断是否有局部会话
        Boolean isLogin = (Boolean)session.getAttribute("isLogin");
        if(isLogin != null && isLogin){
            //有局部会话，直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //没有局部会话，重定向到统一认证中心，检查是否有其他的系统已经登录过
        SSOUtils.redirectToSSOURL(request, response);
    }

    @Override
    public void destroy() {

    }
}
