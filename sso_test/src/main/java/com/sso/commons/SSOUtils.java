package com.sso.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class SSOUtils {

    private static Properties ssoProperties = new Properties();

    public static String SERVER_AUTH_CENTER;    //统一认证中心 http://localhost:8802/auth_center/ 在sso.properties配置

    public static String SERVER_SSO_TEST;    //当前客户端地址 http://localhost:8802/sso_test/ 在sso.properties配置

    static {
        try{
            ssoProperties.load(SSOUtils.class.getClassLoader().getResourceAsStream("sso.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        SERVER_AUTH_CENTER = ssoProperties.getProperty("server-auth-center");
        SERVER_SSO_TEST = ssoProperties.getProperty("server-sso-test");
    }

    /**
     * 当前客户端请求呗拦截，跳往统一认证中心，需要带redirectUrl的参数，统一认证中心登录后回调的地址
     * 通过request 获取这次请求的地址 http://localhost:8802/sso_test/
     * @param request
     * @return
     */
    public static String getRedirectUrl(HttpServletRequest request){
        //获取请求URL
        return SERVER_SSO_TEST + request.getServletPath();
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
        StringBuilder url = new StringBuilder(50).append(SERVER_AUTH_CENTER).append("/checkLogin?redirectUrl=").append(redirectUrl);
        response.sendRedirect(url.toString());
    }
}
