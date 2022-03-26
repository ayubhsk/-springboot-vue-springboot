package com.whx.myspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whx.myspringboot.entity.Course;
import com.whx.myspringboot.service.CourseService;
import com.whx.myspringboot.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}




