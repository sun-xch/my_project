package com.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 测试拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    //进入Handler方法之前执行
    //用于身份认证、身份授权
    //比如身份认证，如果认证没有通过表示当前用户没有登录，需要此方法拦截不在向下执行
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //获取请求url
        String url = httpServletRequest.getRequestURI();
        //判断url是否是公开地址（实际使用时将公开地址配置在配置文件中）
        if(url.indexOf("login") >=0 ){
            return true;
        }
        //判断session
        HttpSession session = httpServletRequest.getSession();
        //从session中取出用户身份信息
        String username = (String) session.getAttribute("username");
        if(username != null){
            return true;
        }
        //否则需要身份认证 跳转到登录页
        httpServletRequest.getRequestDispatcher("/WEB-INF/view/jsp/login.jsp").forward(httpServletRequest,httpServletResponse);
        //return false 表示拦截，不向下执行
        //return true 表示放行
        return false;
    }

    //进入Handler方法之后，反回modelAndView之前执行
    //应用场景从modelAndView出发：将公用的模型数据（比如菜单导航）在这里传到视图，也可以在这里统一指定视图
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("HandlerInterceptor1...postHandle");
    }

    //执行Handler方法完成后执行此方法
    //统一的异常处理，统一的日志处理
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("HandlerInterceptor1...afterCompletion");
    }
}
