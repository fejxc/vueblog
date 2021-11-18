package com.suny.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suny.common.lang.Result;
import com.suny.entity.Blog;
import com.suny.entity.User;
import com.suny.service.BlogService;
import com.suny.unti.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2021-11-17
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result list(@PathVariable(name= "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客被删除");
        return Result.succ(blog);
    }
    //编辑，添加
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result list(@Validated @RequestBody Blog blog){

        Blog temp =null;
        if(blog.getId() != null){
            //编辑
            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()),"没有权限编辑");

        } else {
            //添加
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        //只复制3个字段属性
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        //更新或者保存
        blogService.saveOrUpdate(temp);
        return Result.succ(temp);
    }
}
