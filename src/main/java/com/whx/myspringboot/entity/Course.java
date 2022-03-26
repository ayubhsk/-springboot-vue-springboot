package com.whx.myspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 学分
     */
    private Integer score;

    /**
     * 上课时间
     */
    private String times;

    /**
     * 是否开课
     */
    private Boolean state;

    /**
     * 授课老师id
     */
    private Integer teacherId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}