package com.liwen.project.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginRegisterDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "验证码不能为空")
    private String code;
    @NotNull(message = "uuid不能为空")
    private String uuid;
}
