package com.blog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt) {
        this.token = jwt;
    }

    @Override//返回对象
    public Object getPrincipal() {
        return token;
    }

    @Override//返回秘钥
    public Object getCredentials() {
        return token;
    }
}
