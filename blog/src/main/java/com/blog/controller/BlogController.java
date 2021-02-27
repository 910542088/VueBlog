package com.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.Result;
import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.service.BlogService;
import com.blog.shiro.Account;
import com.blog.shiro.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Simon
 * @since 2021-02-20
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/blogs")
    public Result blogs(@RequestParam(defaultValue = "1") Integer currentPage) {
        System.out.println("=============blogs===============");
        Page<Blog> page = new Page<>(currentPage, 5);
        IPage<Blog> data = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(data);
    }

    @GetMapping("/blog/{id}")
    public Result edit(@PathVariable String id) {
        Blog blog = blogService.getById(Long.valueOf(id));
        Assert.notNull(blog,"该博客已被删除");
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog swap = null;
        if (blog.getId() == null) {
            swap = new Blog();
            swap.setCreated(LocalDateTime.now())
                .setStatus(0)
                .setUserId(((Account) SecurityUtils.getSubject().getPrincipal()).getId());
        } else {
            swap = blogService.getById(blog.getId());
            Account principal = (Account) SecurityUtils.getSubject().getPrincipal();
            Assert.isTrue(swap.getUserId().longValue() == principal.getId().longValue(),"无权编辑");
        }

        swap.setContent(blog.getContent());
        swap.setTitle(blog.getTitle());
        swap.setDescription(blog.getDescription());

        blogService.saveOrUpdate(swap);
        return Result.succ(null);
    }

    @RequiresAuthentication
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        boolean b = blogService.removeById(id);
        Assert.isTrue(b,"删除失败");
        return Result.succ(null);
    }

}
