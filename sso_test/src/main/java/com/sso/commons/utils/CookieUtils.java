package com.sso.commons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtils {

    /**
     * 获取cookie的值
     * @param request
     * @param cookieName
     * @param encodeString
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString){
        Cookie[] cookiesList = request.getCookies();
        if(cookiesList == null || cookieName == null){
            return null;
        }
        String retValue = null;
        try {
            for(int i=0; i<cookiesList.length; i++){
                if(cookiesList[i].getName().equals(cookieName)){
                    retValue = URLDecoder.decode(cookiesList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }


    /**
     * 获取 cookie的域名 例 http:// www.test.com:8080 转换为 test.com
     * @param request
     * @return
     */
    public static final String getDomainName(HttpServletRequest request){
        String domainName = null;
        //获取完整的请求URL地址
        String serverName = request.getRequestURL().toString();
        if(serverName == null || serverName.equals("")){
            serverName = "";
        }else{
            serverName = serverName.toLowerCase();//全部转小写（域名大小写不敏感）
            if(serverName.startsWith("http://")){
                serverName = serverName.substring(7);
            }else if(serverName.startsWith("https://")){
                serverName = serverName.substring(8);
            }
            final int end = serverName.indexOf("/");
            //剩余www.test.com
            serverName = serverName.substring(0,end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if(len > 3){
                domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            }else if (len <= 3 && len > 1){
                domainName = domains[len - 2] + "." + domains[len - 1];
            }else{
                domainName = serverName;
            }
        }

        if(domainName != null && domainName.indexOf(":") > 0){
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }

    /**
     * 设置cookie的值，并使其在指定时间内生效
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage 生效最大秒数
     * @param isEncode
     */
    public static final void doSetCookies(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, boolean isEncode){
        try {
            if(cookieValue == null){
                cookieValue = "";
            }else if(isEncode){
                cookieValue = URLEncoder.encode(cookieValue,"utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if(cookieMaxage >0){
                cookie.setMaxAge(cookieMaxage);
            }
            if(null != request){
                String domainName = getDomainName(request);
                if(!"localhost".equals(domainName)){
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置cookie的值，并使其在指定时间内生效
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage 生效最大秒数
     * @param encodeString
     */
    public static final void doSetCookies(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxage, String encodeString){
        try {
            if(cookieValue == null){
                cookieValue = "";
            }else{
                cookieValue = URLEncoder.encode(cookieValue,encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if(cookieMaxage >0){
                cookie.setMaxAge(cookieMaxage);
            }
            if(null != request){//设置域名的cookie
                String domainName = getDomainName(request);
                if(!"localhost".equals(domainName)){
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
