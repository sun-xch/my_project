package com.sso.commons.jwt;

/**
 * 作为Subject数据使用。也就是payload中保存的public claims
 * 不包含敏感数据
 * 开发中使用 实体类型。
 */
public class JWTSubject {

    private String username;

    public JWTSubject() {
        super();
    }

    public JWTSubject(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
