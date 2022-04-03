package com.whx.myspringboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whx.myspringboot.common.Result;
import com.whx.myspringboot.controller.dto.UserDTO;
import com.whx.myspringboot.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SysUserService extends IService<SysUser> {


    Page<SysUser> pageTest(Page<SysUser> page, Integer beginId, Integer endId);

    UserDTO login(SysUser user);

    Result register(SysUser user);
}
