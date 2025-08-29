package com.slcp.devops.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * algolia数据推送
 *
 * @author slcp
 * @since 2023-01-18 10:24:42
 */
@Data
@NoArgsConstructor
public class Contact implements Serializable {
  @JsonSerialize(using = ToStringSerializer.class)
  private Long objectID;
  private String path;
  private String title;
  private String contentStripTruncate;

}
