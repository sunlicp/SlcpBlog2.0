package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 博文(Blog)表实体类
 *
 * @author makejava
 * @since 2022-07-05 10:24:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_blog")
public class Blog extends BaseEntity<Blog> implements Serializable {
    private static final long serialVersionUID = -2309782512312943999L;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "type_id")
    private Long typeId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @TableField(value = "title")
    private String title;

    /**
     * 内容
     */
    @Schema(description = "内容")
    @TableField(value = "content")
    private String content;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @TableField(value = "description")
    private String description;

    /**
     * 图片路径
     */
    @Schema(description = "图片路径")
    @TableField(value = "first_picture")
    private String firstPicture;

    /**
     * 赞赏
     */
    @Schema(description = "赞赏")
    @TableField(value = "appreciation")
    private Boolean appreciation;

    /**
     * 标记
     */
    @Schema(description = "标记")
    @TableField(value = "flag")
    private Integer flag;

    /**
     * 公开
     */
    @Schema(description = "公开")
    @TableField(value = "published")
    private Boolean published;

    /**
     * 推荐
     */
    @Schema(description = "推荐")
    @TableField(value = "recommend")
    private Boolean recommend;

    /**
     * 转载声明
     */
    @Schema(description = "转载声明")
    @TableField(value = "share_statement")
    private Boolean shareStatement;

    /**
     * 置顶
     */
    @Schema(description = "置顶")
    @TableField(value = "top")
    private Boolean top;
    //评论

    /**
     * 评论
     */
    @Schema(description = "评论")
    @TableField(value = "comment")
    private Boolean comment;

    /**
     * 访问次数
     */
    @Schema(description = "访问次数")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "views")
    private Long views;

    /**
     * 评论次数
     */
    @Schema(description = "评论次数")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "comment_count")
    private Long commentCount;

    /**
     * 特殊操作
     */
    @Schema(description = "特殊操作")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "special")
    private Integer special;

    /**
     * 特殊内容
     */
    @Schema(description = "特殊内容")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "special_content")
    private String specialContent;
}