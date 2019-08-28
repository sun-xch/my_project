package com.sso.controller;

import com.sso.commons.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class TestController {

    /**
     * host文件中设置两个域名    192.168.2.166 www.test.com
     *                           192.168.2.166 sso.test.com
     * 测试session跨域 代码实现
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response){
        String cookieName = "custom_global_session_id";
        String encodeString = "UTF-8";
        String cookieValue = CookieUtils.getCookieValue(request, cookieName, encodeString);
        if(null == cookieValue || "".equals(cookieValue.trim())){
            System.out.println("无cookie,生成新的cookie数据");
            cookieValue = UUID.randomUUID().toString();
        }

        //根据cookieValue 访问数据存储，获取客户端数据
        CookieUtils.doSetCookies(request, response, cookieName, cookieValue, 0, encodeString);
        return "ok";
    }

    //spring-session 解决同域名下的多服务器集群session共享问题，不能解决跨域session共享问题
    //spring-session表 保存客户端 session 对象的表
    //spring-session-attributes表 保存客户端session 中的 attributes 属性数据的表

    /**
     * 测试spring-session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/testSpringSession")
    public String testSpringSession(HttpServletRequest request, HttpServletResponse response){
        Object testAttrValue = request.getSession().getAttribute("testAttrName");
        if(null == testAttrValue){
            request.getSession().setAttribute("testAttrName","testAttrValue");
        }
        System.out.println("80:"+ testAttrValue);
        return "ok";
    }

}
