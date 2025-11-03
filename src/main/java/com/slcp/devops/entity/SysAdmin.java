package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * (SysAdmin)实体类
 *
 * @author makejava
 * @since 2022-06-28 14:24:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_admin")
public class SysAdmin extends BaseEntity<SysAdmin> implements Serializable {
    private static final long serialVersionUID = -99322056007168973L;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @TableField(value = "password", select = false)
    private String password;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "rid")
    private Long rid;

    /**
     * 状态
     *
     */
    @Schema(description = "状态")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "is_status")
    private boolean status;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @TableField(value = "email")
    private String email;

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    @TableField(value = "token", fill = FieldFill.INSERT)
    private String token;

    /**
     * 头像
     */
    @Schema(description = "头像")
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 上次登录地址
     */
    @Schema(description = "上次登录地址")
    @TableField(value = "last_login_address")
    private String lastLoginAddress;

    /**
     * 上次登录时间
     */
    @Schema(description = "上次登录时间")
    @TableField(value = "last_login_time")
    @JsonSerialize
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;


}