package com.sso.commons;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    private static final Map<String, String> USER = new HashMap<>(16);

    static {
        for(int i=0; i<10; i++){
            USER.put("admin"+i, "password"+i);
        }
    }

    //是否可登录
    public static boolean isLogin(String username, String password){
        if(null == username || username.trim().length() == 0){
            return false;
        }
        String obj = USER.get(username);
        if(null == obj || !obj.equals(password)){
            return false;
        }
        return true;
    }
}
