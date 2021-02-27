package com.blog.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.Result;
import com.blog.common.UserDto;
import com.blog.entity.User;
import com.blog.service.UserService;
import com.blog.shiro.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody UserDto userDto, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDto.getUsername()));
        if ( user == null || !user.getPassword().equals(SecureUtil.md5(userDto.getPassword()))) {
            throw new IllegalStateException("用户名或密码错误");
        }
        String jwt = jwtUtil.generateToken(user.getId());
        response.setHeader("jwt", jwt);
        response.setHeader("Access-control-Expose-Headers", "jwt");

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());

        return Result.succ(map);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
