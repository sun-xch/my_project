package com.sso.controller;

import com.sso.commons.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class SSOServerController {

    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model){
        //判断是否有全局的会话
        String token = (String) session.getAttribute("token");
        if(StringUtils.isEmpty(token)){
            //表示没有全局会话 跳转到统一认证中心的登录页面
            model.addAttribute("redirectUrl", redirectUrl);
            return "/index";
        }else{
            //重定向到redirectUrl 并把token带上
            model.addAttribute("token",token);
            return "redirect:" + redirectUrl;
        }
    }

    @RequestMapping("/login")
    public String login(String username, String password, String redirectUrl, HttpSession session, Model model){
        Boolean flag = UserInfo.isLogin(username, password);
        if(flag){
            //创建全局的会话，把令牌放入会话中
            String token = "1234567890";
            session.setAttribute("token",token);
            //令牌放入 数据库 或 Redis等
            //。。。
            //重定向到redirectUrl 并把token带上
            model.addAttribute("token",token);
            return "redirect:" + redirectUrl;
        }
        //若账号密码错误，重定向到登录页面  并把 redirectUrl 放入到request域中
        model.addAttribute("redirectUrl", redirectUrl);
        return "/index";
    }
}
