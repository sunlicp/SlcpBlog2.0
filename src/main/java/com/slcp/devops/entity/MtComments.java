package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BlogComments)实体类
 *
 * @author makejava
 * @since 2023-03-06 09:36:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "mt_comments")
public class MtComments extends BaseEntity<MtComments> implements Serializable {
    private static final long serialVersionUID = -61783493810760968L;

    /**
     * 墙留言ID
     */
    @Schema(description = "墙留言ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "wall_id")
    private Long wallId;
    /**
     * 评论者ID
     */
    @Schema(description = "评论者ID")
    @TableField(value = "user_id")
    private String userId;
    /**
     * 头像路径
     */
    @Schema(description = "头像路径")
    @TableField(value = "img_url")
    private String imgUrl;
    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    @TableField(value = "comment")
    private String comment;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @TableField(value = "name")
    private String name;
    /**
     * 时间
     */
    @Schema(description = "时间")
    @TableField(value = "moment")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime moment;

}

