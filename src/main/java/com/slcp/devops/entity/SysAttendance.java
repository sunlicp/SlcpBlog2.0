package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * (SysAttendance)实体类
 *
 * @author makejava
 * @since 2022-06-28 14:32:14
 */
@Data
@EqualsAndHashCode
@TableName(value = "sys_attendance")
public class SysAttendance implements Serializable {
    private static final long serialVersionUID = 752992629859751842L;
    /**
     * 业务主表主键ID
     */
    @Schema(description = "业务主表主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "uid")
    private Long uid;
    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @TableField(value = "name")
    private String name;
    /**
     * 类型
     */
    @Schema(description = "类型")
    @TableField(value = "type")
    private String type;
    /**
     * 时长
     */
    @Schema(description = "时长")
    @TableField(value = "length")
    private String length;
    /**
     * 迟到时间
     */
    @Schema(description = "迟到时间")
    @TableField(value = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    /**
     * 原因
     */
    @Schema(description = "原因")
    @TableField(value = "remark")
    private String remark;

}

