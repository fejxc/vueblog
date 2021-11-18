package com.suny.common.lang.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: suny
 * @Date: 2021/11/18 下午7:25
 * @Description:
 */
@Data
public class LoginTDO implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
