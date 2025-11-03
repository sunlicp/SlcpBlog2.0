package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BlogWalls)实体类
 *
 * @author makejava
 * @since 2023-03-06 09:36:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "mt_walls")
public class MtWalls extends BaseEntity<MtWalls> implements Serializable {
    private static final long serialVersionUID = 950911289425265431L;

    /**
     * 类型0信息1图片
     */
    @Schema(description = "类型")
    @TableField(value = "type")
    private Integer type;
    /**
     * 留言
     */
    @Schema(description = "类型")
    @TableField(value = "message")
    private String message;
    /**
     * 用户名
     */
    @Schema(description = "类型")
    @TableField(value = "name")
    private String name;
    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    @TableField(value = "user_id")
    private String userId;
    /**
     * 时间
     */
    @Schema(description = "时间")
    @TableField(value = "moment")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime moment;
    /**
     * 标签
     */
    @Schema(description = "标签")
    @TableField(value = "label")
    private Integer label;
    /**
     * 颜色
     */
    @Schema(description = "颜色")
    @TableField(value = "color")
    private Integer color;
    /**
     * 图片路径
     */
    @Schema(description = "图片路径")
    @TableField(value = "img_url")
    private String imgUrl;

}

