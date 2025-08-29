package com.slcp.devops.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sunlicp
 * @since 2023/03/06 15:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Count implements Serializable {
    private static final long serialVersionUID = -75252221451243291L;
    private Integer count;
}
