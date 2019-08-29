package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    //登录
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password){
        //调用service进行用户身份验证
        //...
        //在session 中保存用户身份信息
        session.setAttribute("username",username);
        //重定向到商品列表页
        return "redirect:/items/queryItems";
     }

    //退出
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页
        return "redirect:/login";
    }
}
