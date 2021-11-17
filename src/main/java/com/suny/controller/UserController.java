package com.suny.controller;


import com.suny.common.lang.Result;
import com.suny.entity.User;
import com.suny.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-11-17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Result indx() {
        User u = userService.getById(1L);
        return Result.succ(200,"操作完成",u);
    }

}
