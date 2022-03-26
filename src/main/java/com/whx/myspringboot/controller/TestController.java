package com.whx.myspringboot.controller;


import com.whx.myspringboot.entity.SysUser;
import com.whx.myspringboot.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {
    @Resource
    SysUserService userService;


    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    @GetMapping("/testJsonIgnore")
    public List<SysUser> testJsonIgnore(){

        List<SysUser> list = userService.list();
        return list;
    }
}
