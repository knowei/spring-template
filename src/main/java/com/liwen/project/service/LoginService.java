package com.liwen.project.service;

import com.liwen.project.model.request.LoginDto;
import com.liwen.project.model.request.LoginRegisterDto;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    void captcha(String uuid, HttpServletResponse response);

    void login(LoginDto dto);

    /**
     * 用户注册
     * @param dto
     * @return
     */
    String register(LoginRegisterDto dto);
}
