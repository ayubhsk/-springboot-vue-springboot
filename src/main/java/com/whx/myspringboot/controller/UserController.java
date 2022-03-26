package com.whx.myspringboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whx.myspringboot.entity.SysUser;
import com.whx.myspringboot.service.SysUserService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    SysUserService userService;


    @GetMapping("/page")
    public Page<SysUser> loadPage(Integer pageNum,Integer pageSize,
                                   String username,String email,String address){
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(!"".equals(username),SysUser::getUsername,username)
                .like(!"".equals(address),SysUser::getAddress,address)
                .like(!"".equals(email),SysUser::getEmail,email);
        Page<SysUser> page=new Page<>(pageNum,pageSize);
        Page<SysUser> getPage=userService.page(page,queryWrapper);
        return getPage;
    }

    //若传来的元素没有有id就更新，否则保存
    @PostMapping("/saveOrUpdate")
    public boolean save(SysUser user){
        System.out.println("执行save操作");
        boolean flag=false;
        flag=userService.saveOrUpdate(user);
        return flag;
    }

    //根据id删除元素
    @PostMapping("/delUser")
    public boolean delUser(String id){

        boolean flag=false;
        flag=userService.removeById(id);
        return flag;


    }

    @PostMapping("/batchDelUsers")
    public boolean batchDelUsers(Integer[] ids){
        boolean flag=false;
        List<Integer> list = Arrays.asList(ids);
         flag=userService.removeBatchByIds(list);
        return flag;
    }


}
