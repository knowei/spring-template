/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liwen.project.controller;

import com.liwen.project.common.response.PageResponse;
import com.liwen.project.model.entity.User;
import com.liwen.project.exception.BusinessException;
import com.liwen.project.model.request.UserPagingDto;
import com.liwen.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    @Operation(summary = "用户管理相关接口")
    public PageResponse<User> list(@RequestBody UserPagingDto dto) {
        return userService.getList(dto);
    }

    @GetMapping("/error")
    @Operation(summary = "错误接口")
    public void error() {
        try {
            List<String> list = new ArrayList<String>();
            String o = list.get(1);
        } catch (Exception e) {
            throw BusinessException.fail(e.getMessage());
        }
    }
}
