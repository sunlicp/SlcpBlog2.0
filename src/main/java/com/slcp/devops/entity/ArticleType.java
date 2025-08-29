package com.slcp.devops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * (ArticleType)实体类
 *
 * @author makejava
 * @since 2022-07-09 23:53:22
 */
@Data
@AllArgsConstructor
@TableName(value = "t_article_type")
public class ArticleType implements Serializable {
    private static final long serialVersionUID = 124395818890214477L;
    /**
     * 业务主表主键ID
     */
    @ApiModelProperty(value = "业务主表主键ID")
    @TableId(value = "article_id")
    private Integer articleId;

    /**
     * 文章类型
     */
    @ApiModelProperty(value = "文章类型")
    @TableField(value = "article_type")
    private String articleType;

}

