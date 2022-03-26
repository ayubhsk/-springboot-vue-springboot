package com.whx.myspringboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whx.myspringboot.entity.SysUser;
import com.whx.myspringboot.service.SysUserService;
import com.whx.myspringboot.mapper.SysUserMapper;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Resource
    SysUserMapper sysUserMapper;



    @Override
    public Page<SysUser> pageTest(Page<SysUser> page, Integer beginId, Integer endId) {
        Page<SysUser> getPage=sysUserMapper.pageTest(page,beginId,endId);
        return getPage;
    }
}




