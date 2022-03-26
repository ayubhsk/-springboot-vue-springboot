package com.whx.myspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whx.myspringboot.entity.TComment;
import com.whx.myspringboot.service.TCommentService;
import com.whx.myspringboot.mapper.TCommentMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TCommentServiceImpl extends ServiceImpl<TCommentMapper, TComment>
    implements TCommentService{

}




