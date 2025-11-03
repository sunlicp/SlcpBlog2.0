package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (TagBlogRel)实体类
 *
 * @author makejava
 * @since 2022-07-09 23:53:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tag_blog_rel")
public class TagBlogRel implements Serializable {
    private static final long serialVersionUID = 384395818890264477L;
    /**
     * 业务主表主键ID
     */
    @Schema(description = "业务主表主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;

    /**
     * 标签id
     */
    @Schema(description = "标签id")
    @TableField(value = "tid")
    private Long tagId;

    /**
     * 博客id
     */
    @Schema(description = "博客id")
    @TableField(value = "bid")
    private Long blogId;


}

