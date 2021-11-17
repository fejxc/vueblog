package com.suny.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: suny
 * @Date: 2021/11/17 下午11:54
 * @Description:
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String jwt){
        this.token = jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
