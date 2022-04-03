package com.whx.myspringboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whx.myspringboot.common.Constants;
import com.whx.myspringboot.common.Result;
import com.whx.myspringboot.controller.dto.UserDTO;
import com.whx.myspringboot.entity.SysUser;
import com.whx.myspringboot.exception.ServiceException;
import com.whx.myspringboot.service.SysUserService;
import com.whx.myspringboot.mapper.SysUserMapper;
import com.whx.myspringboot.utils.TokenUtils;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
private static final Log LOG=Log.get();

    @Resource
    SysUserMapper sysUserMapper;


    @Override
    public Page<SysUser> pageTest(Page<SysUser> page, Integer beginId, Integer endId) {
        Page<SysUser> getPage=sysUserMapper.pageTest(page,beginId,endId);
        return getPage;
    }

    @Override
    public UserDTO login(SysUser user) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,user.getUsername()).eq(SysUser::getPassword,user.getPassword());
        SysUser one=null;
        try {
            one = sysUserMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            System.out.println("log输出异常");
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"其他错误");
        }

        if(one!=null){
            UserDTO userDTO=new UserDTO();
            BeanUtil.copyProperties(one,userDTO,true);
            String token = TokenUtils.genToken(userDTO.getId().toString(), userDTO.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }


    }

    @Override
    public Result register(SysUser user) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,user.getUsername()).eq(SysUser::getPassword,user.getPassword());
        SysUser one=null;
        try {
            one=sysUserMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500,"其他错误");
        }
        if(one!=null){
            throw new ServiceException(Constants.CODE_600,"用户存在");
        }
        sysUserMapper.insert(user);
        return Result.success();
    }
}




