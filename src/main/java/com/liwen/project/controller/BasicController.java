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

import com.liwen.project.demos.User;
import com.liwen.project.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class BasicController {
    @GetMapping()
    @Operation(summary = "用户管理相关接口")
    public List<User> list() {
        List list = new ArrayList<User>();
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User();
            user.setName("man " + i);
            user.setAge(i);
            list.add(user);
        }
        return list;
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
