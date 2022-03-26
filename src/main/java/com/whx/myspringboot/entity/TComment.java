package com.whx.myspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_comment
 */
@TableName(value ="t_comment")
@Data
public class TComment implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论人id
     */
    private Integer userId;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 最上级评论id
     */
    private Integer originId;

    /**
     * 关联文章的id
     */
    private Integer articleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}