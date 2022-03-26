package com.whx.myspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whx.myspringboot.entity.Article;
import com.whx.myspringboot.service.ArticleService;
import com.whx.myspringboot.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




