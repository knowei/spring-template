package com.liwen.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwen.project.common.response.PageResponse;
import com.liwen.project.common.utils.MapUtils;
import com.liwen.project.common.utils.PageUtils;
import com.liwen.project.model.entity.User;
import com.liwen.project.mapper.UserMapper;
import com.liwen.project.model.request.UserPagingDto;
import com.liwen.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageResponse<User> getList(UserPagingDto dto) {

        Page<User> userPage = new Page<>(dto.getPageNo(), dto.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (dto.getName() != null){
            queryWrapper.like("name", dto.getName());
        }
        if (dto.getAge() != null){
            queryWrapper.like("age", dto.getAge());
        }
        Page<User> response = userMapper.selectPage(userPage, queryWrapper);
        PageResponse<User> result = PageUtils.convertPageResponse(response);
        return result;
    }
}
