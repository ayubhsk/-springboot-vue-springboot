package com.whx.myspringboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whx.myspringboot.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.whx.myspringboot.entity.SysUser
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<SysUser> pageTest(Page<SysUser> page, @Param("beginId") Integer beginId, @Param("endId") Integer endId);
}




