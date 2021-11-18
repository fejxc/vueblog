package com.suny.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suny.common.lang.Result;
import com.suny.common.lang.dto.LoginTDO;
import com.suny.entity.User;
import com.suny.service.UserService;
import com.suny.unti.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: suny
 * @Date: 2021/11/18 下午7:23
 * @Description:
 */
@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping("login")
    public Result login(@Validated @RequestBody LoginTDO loginTDO, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginTDO.getUsername()));
        //断言
        Assert.notNull(user,"用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginTDO.getPassword()) )){
            return Result.fail("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        return  Result.succ(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map()
        );
    }

    //@RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
