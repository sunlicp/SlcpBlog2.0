package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * (SysRights)实体类
 *
 * @author makejava
 * @since 2022-06-28 14:32:14
 */
@Data
@TableName(value = "sys_rights")
public class SysRights implements Serializable {
    private static final long serialVersionUID = -36791769757807077L;
    /**
     * 业务主表主键ID
     */
    @Schema(description = "业务主表主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;
    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "rid")
    private Long rid;
    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    @TableField(value = "auth_name")
    private String authName;
    /**
     * 级别
     */
    @Schema(description = "级别")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "level")
    private Long level;
    /**
     * 路径
     */
    @Schema(description = "路径")
    @TableField(value = "path")
    private String path;

}

