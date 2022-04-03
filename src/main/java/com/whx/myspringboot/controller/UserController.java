package com.whx.myspringboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whx.myspringboot.common.Constants;
import com.whx.myspringboot.common.Result;
import com.whx.myspringboot.controller.dto.UserDTO;
import com.whx.myspringboot.entity.SysUser;
import com.whx.myspringboot.service.SysUserService;
import com.whx.myspringboot.utils.TokenUtils;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
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
        SysUser user = TokenUtils.getCurrentUser();
        System.out.println(user );

        return getPage;
    }

    //若传来的元素没有有id就更新，否则保存
    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody SysUser user){
        System.out.println("执行save操作");
        boolean flag=false;
        flag=userService.saveOrUpdate(user);
        return flag?Result.success(user):Result.error();
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

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<SysUser> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");
        writer.addHeaderAlias("role", "权限");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }
    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
         //方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
/*        List<SysUser> list = reader.readAll(SysUser.class);
        list.forEach(System.out::println);*/
        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<SysUser> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            SysUser user = new SysUser();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);
        }

        boolean flag = userService.saveBatch(users);
        return flag;
    }

    @PostMapping("/login")
    public Result login(SysUser user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error(Constants.CODE_400,"参数错误");
        };

        UserDTO userDTO=userService.login(user);
        return Result.success(userDTO);
    }

    @PostMapping("/register")
    public Result register(SysUser user){
        Result result=userService.register(user);
        return result;
    }

    @GetMapping("/getUserByName")
    public Result getUserByName( String userName){
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,userName);
        SysUser one = userService.getOne(queryWrapper);
        return Result.success(one);
    }

}
