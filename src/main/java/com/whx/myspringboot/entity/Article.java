package com.whx.myspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布人
     */
    private String user;

    /**
     * 发布时间
     */
    private String time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}