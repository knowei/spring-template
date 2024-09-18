package com.liwen.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwen.project.model.entity.User;

public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(String username);
}
