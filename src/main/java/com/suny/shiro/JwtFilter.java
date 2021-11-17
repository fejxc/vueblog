package com.suny.shiro;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.json.JSONUtil;
import com.suny.common.lang.Result;
import com.suny.unti.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: suny
 * @Date: 2021/11/17 下午11:49
 * @Description:
 */
@Component
public class JwtFilter extends AuthenticatingFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
          HttpServletRequest request = (HttpServletRequest) servletRequest;
          String jwt = request.getHeader("Authorazation");
          if(StringUtils.isEmpty(jwt)) {
              return null;
          }
        return new JwtToken(jwt);
    }

    //校验判断
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorazation");
        if(StringUtils.isEmpty(jwt)) {
            //交给注解拦截
            return true;
        }else {
            //校验jwt
            Claims claimByToken = jwtUtils.getClaimByToken(jwt);
            if(claimByToken == null || jwtUtils.isTokenExpired(claimByToken.getExpiration())) {
                throw new ExpiredCredentialsException("token已经失效，请重新登陆！");

            }
            //执行登陆
            return executeLogin(servletRequest,servletResponse);

        }

    }

    //登陆异常处理 json返回前端
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Throwable throwable  = e.getCause() == null ? e : e.getCause();
        Result result = Result.fail(throwable.getMessage());
        String json = JSONUtil.toJsonStr(result);
        try {
            httpServletResponse.getWriter().print(json);
        } catch (IOException ex) {

        }
        return false;
    }
}
