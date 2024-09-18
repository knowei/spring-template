package com.liwen.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liwen.project.common.response.PageResponse;
import com.liwen.project.model.entity.User;
import com.liwen.project.model.request.UserPagingDto;

public interface UserService extends IService<User> {
    PageResponse<User> getList(UserPagingDto dto);
}
