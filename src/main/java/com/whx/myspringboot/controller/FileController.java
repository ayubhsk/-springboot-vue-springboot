package com.whx.myspringboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whx.myspringboot.common.Result;
import com.whx.myspringboot.entity.SysFile;
import com.whx.myspringboot.service.SysFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

/*    @Value("${server.ip}")
    private String serverIp;*/

    @Resource
    private SysFileService sysFileService;

/*    @Autowired
    private StringRedisTemplate stringRedisTemplate;*/


    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        SysFile dbFiles = getSysFileByMD5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url ="http://localhost:8895/file/"+fileUUID;

        }

        SysFile sysFile=new SysFile();
        sysFile.setName(originalFilename);
        sysFile.setSize(size/1024);
        sysFile.setType(type);
        sysFile.setUrl(url);
        sysFile.setMd5(md5);
        boolean flag = sysFileService.save(sysFile);



        return url;
    }


    /**
     * 文件下载接口   http://localhost:8895/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //刷新页面操作
    @GetMapping("/page")
    public Result page(Integer pageNum,Integer pageSize,String name){
        LambdaQueryWrapper<SysFile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null && name!="",SysFile::getName,name);
        Page<SysFile> page=new Page<>(pageNum,pageSize);
        Page<SysFile> filePage = sysFileService.page(page, queryWrapper);
        Result result = Result.success(filePage);
        return result;

    }

    //删除单个记录
    @DeleteMapping("/{id}")
    public Result del(@PathVariable Integer id){
        boolean flag = sysFileService.removeById(id);
        return flag? Result.success():Result.error();
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result delBatch(@RequestBody Integer[] ids){
        LambdaQueryWrapper<SysFile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(SysFile::getId,ids);
        boolean flag = sysFileService.remove(queryWrapper);
        return flag? Result.success():Result.error();
    }
    //批量删除
    @PostMapping("/update")
    public Result update(@RequestBody SysFile file){
        boolean flag=sysFileService.saveOrUpdate(file);
        return flag? Result.success():Result.error();
    }


    private SysFile getSysFileByMD5(String md5) {
        LambdaQueryWrapper<SysFile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFile::getMd5,md5);
        return sysFileService.getOne(queryWrapper);
    }

}
