package com.slcp.devops.dto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.slcp.devops.entity.Count;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author sunlicp
 * @since 2023/03/06 11:27
 */
@Data
public class MtWallsDTO implements Serializable {
    private static final long serialVersionUID = -1309782228272943999L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer type;
    private String message;
    private String name;
    private String userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime moment;
    private Integer label;
    private Integer color;
    private String imgUrl;
    private List<Count> report;
    private List<Count> revoke;
    private List<Count> like;
    private List<Count> comcount;
    private List<Count> isLike;

}
