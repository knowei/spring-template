package com.liwen.project.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.liwen.project.common.password.PasswordUtils;
import com.liwen.project.common.redis.RedisUtils;
import com.liwen.project.exception.BusinessException;
import com.liwen.project.mapper.UserMapper;
import com.liwen.project.model.entity.User;
import com.liwen.project.model.request.LoginDto;
import com.liwen.project.model.request.LoginRegisterDto;
import com.liwen.project.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void captcha(String uuid, HttpServletResponse response) {

        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = lineCaptcha.getCode();

        redisUtils.set(uuid, code, RedisUtils.MIN_SIX_EXPIRE);

        try {
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void login(LoginDto dto) {
        String code = (String) redisUtils.get(dto.getUuid());

        if (!StringUtils.equals(code, dto.getCode())) {
            throw BusinessException.fail("验证码错误");
        }
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw BusinessException.fail("用户不存在");
        }
        if (!PasswordUtils.matches(dto.getPassword(), user.getPassword())) {
            throw BusinessException.fail("密码错误");
        }
    }

    @Override
    public String register(LoginRegisterDto dto) {
        User user = userMapper.selectByUsername(dto.getUsername());
        if (ObjectUtils.isNotNull(user)) {
            throw BusinessException.fail("用户已存在");
        }

        String uuid = dto.getUuid();
        String code = (String) redisUtils.get(uuid);
        if (!StringUtils.equals(code, dto.getCode())) {
            throw BusinessException.fail("验证码错误");
        }

        user = new User();
        user.setUsername(dto.getUsername());
        String password = dto.getPassword();
        String newPassword = PasswordUtils.encode(password);
        user.setPassword(newPassword);
        userMapper.insert(user);


        redisUtils.delete(uuid);
        return "success";
    }
}
