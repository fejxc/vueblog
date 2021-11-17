package com.suny.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.suny.entity.User;
import com.suny.service.UserService;
import com.suny.unti.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: suny
 * @Date: 2021/11/17 下午11:14
 * @Description:
 */

@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //登陆处理
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(Long.valueOf(userId));
        if(user == null) {
            throw new UnknownAccountException("账户不存在");
        }
        if(user.getStatus() == -1) {
            throw new LockedAccountException("账户锁定");
        }
        System.out.println("***********");
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user,profile);
        //交给Shiro处理
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}


