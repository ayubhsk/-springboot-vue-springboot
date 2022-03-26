package com.whx.myspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName student_course
 */
@TableName(value ="student_course")
@Data
public class StudentCourse implements Serializable {
    /**
     * 
     */
    private Integer studentId;

    /**
     * 
     */
    private Integer courseId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}