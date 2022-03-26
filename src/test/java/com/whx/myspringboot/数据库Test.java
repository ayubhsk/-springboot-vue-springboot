package com.whx.myspringboot;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whx.myspringboot.entity.SysUser;
import com.whx.myspringboot.entity.TComment;
import com.whx.myspringboot.service.SysUserService;
import com.whx.myspringboot.service.TCommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class 数据库Test {
    @Resource
    SysUserService userService;

    @Test
    public void test1(){
        List<SysUser> list = userService.list();
        list.forEach(System.out::println);
    }



    @Test
    public void testTimeStamp(){
        SysUser user=new SysUser();
        user.setNickname("zs");
        userService.saveOrUpdate(user);
    }

    //测试yml文件设置的时间格式
    @Test
    public void testDateForm(){
        System.out.println(new Date().toString());
    }

    //测试分页
    @Test
    public void test分页(){
        Page<SysUser> page=new Page<>(2,5);
        Integer beginId=2;
        Integer endId=27;
        Page<SysUser> getPage=userService.pageTest(page,beginId,endId);
        getPage.getRecords().forEach(System.out::println);
    }

}
