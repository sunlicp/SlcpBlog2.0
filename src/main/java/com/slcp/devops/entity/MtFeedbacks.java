package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (BlogFeedbacks)实体类
 *
 * @author makejava
 * @since 2023-03-06 09:36:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "mt_feedbacks")
public class MtFeedbacks extends BaseEntity<MtFeedbacks> implements Serializable {
    private static final long serialVersionUID = -75254041451243191L;

    /**
     * 墙留言ID
     */
    @ApiModelProperty(value = "墙留言ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "wall_id")
    private Long wallId;
    /**
     * 反馈者ID
     */
    @ApiModelProperty(value = "评论者ID")
    @TableField(value = "user_id")
    private String userId;
    /**
     * 反馈类型0喜欢1举报2撤销
     */
    @ApiModelProperty(value = "反馈类型")
    @TableField(value = "type")
    private Integer type;
    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    @TableField(value = "moment")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime moment;

}

