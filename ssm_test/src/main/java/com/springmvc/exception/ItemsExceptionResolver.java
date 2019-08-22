package com.springmvc.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 */
public class ItemsExceptionResolver implements HandlerExceptionResolver {

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e 系统抛出的异常
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //o 就是处理器适配器要执行handler对象（只有method）

        //解析出异常类型 若是系统自定的类型，直接抛出异常信息，在错误页面展示
        /*String message = null;
        if(e instanceof ItemsException){
            message = ((ItemsException)e).getMessage();
        }else{
            //若抛出的异常类型不是系统自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
            message = "未知错误";
        }*/

        //上边代码变为
        ItemsException itemsException = null;
        if(e instanceof ItemsException){
            itemsException = (ItemsException)e;
        }else{
            //若抛出的异常类型不是系统自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
            itemsException = new ItemsException("未知错误");
        }
        //错误信息
        String message = itemsException.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        //将错误信息传到页面
        modelAndView.addObject("message",message);
        //指向错误页面
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
