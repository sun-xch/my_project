package com.sso.commons.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SSOUtils {

    /*@Value("${server-url-ssoTest}")
    private String SERVER_URL_SSOTEST;  //作为统一认证中心

    @Value("${server-url-ssmTest}")
    private String SERVER_URL_SSMTEST;*/

    @Value("${server-url-ssoTest}")
    private String SERVER_URL_SSOTEST1;

    @Value("${server-url-ssmTest}")
    private String SERVER_URL_SSMTEST1;

    private static String SERVER_URL_SSOTEST = "http://localhost:8802/sso_test_war_exploded/";

    private static String SERVER_URL_SSMTEST = "http://localhost:8802/ssm_test_war_exploded/";

    /**
     * 获取当前请求地址
     * @param request
     * @return
     */
    public static String getRedirectUrl(HttpServletRequest request){
        //获取请求URL
        return SERVER_URL_SSOTEST + request.getServletPath();
    }

    /**
     * 根据request获取跳转到统一认证中心的地址 http://localhost:8802/ssm_test_war_exploded//chectLogin?redirectUrl=http://localhost:8802/sso_test_war_exploded/
     * 通过response跳转到指定的地址
     * @param request
     * @param response
     * @throws IOException
     */
    public static void redirectToSSOURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = getRedirectUrl(request);
        StringBuilder url = new StringBuilder(50).append(SERVER_URL_SSOTEST).append("/chectLogin?redirectUrl=").append(redirectUrl);
        response.sendRedirect(url.toString());
    }
}
