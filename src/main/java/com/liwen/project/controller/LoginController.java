package com.liwen.project.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.liwen.project.model.request.LoginDto;
import com.liwen.project.model.request.LoginRegisterDto;
import com.liwen.project.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("captcha")
    @Operation(summary = "获取验证码")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        loginService.captcha(uuid, response);

    }

    @PostMapping("/login")
    public void login(@RequestBody LoginDto dto, HttpServletResponse response) {
        loginService.login(dto);
    }

    @PostMapping("/register")
    public String register(@RequestBody LoginRegisterDto dto) {
        return loginService.register(dto);
    }
}
