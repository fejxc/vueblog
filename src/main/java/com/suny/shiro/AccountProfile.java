package com.suny.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: suny
 * @Date: 2021/11/18 上午12:29
 * @Description:
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;
}
