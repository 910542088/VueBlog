package com.blog.controller;


import com.blog.common.Result;
import com.blog.common.UserDto;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Simon
 * @since 2021-02-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @RequiresAuthentication
    @GetMapping("get")
    public User get(){
        return userMapper.selectById(1L);
    }

    @GetMapping("hello")
    public Result hello(@Validated UserDto userDto){
        return Result.succ("hello");
    }

}
