package com.slcp.devops.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索封装类
 *
 * @author devops
 */
@Data
@Schema(description = "搜索条件")
public class Search implements Serializable {

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Integer pageNum = 1;

    /**
     * 每页的数量
     */
    @Schema(description = "每页的数量")
    private Integer pageSize = 10;

    /**
     * 升序 字段数组
     */
    @Schema(hidden = true)
    private String ascs;

    /**
     * 降序 字段数组
     */
    @Schema(hidden = true)
    private String descs;
}