package com.slcp.devops.entity.openai;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author slcp
 * @date 2023-01-10 22:10:43
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class OpenAi {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 内容
     */
    private String desc;

    /**
     * code
     */
    private String model;

    /**
     * 提示模板
     */
    private String prompt;

    /**
     * 创新采样
     */
    private Double temperature;

    /**
     * 情绪采样
     */
    private Double topP;

    /**
     * 结果条数
     */
    private Double n;

    /**
     * 频率处罚系数
     */
    private Double frequencyPenalty;

    /**
     * 重复处罚系数
     */
    private Double presencePenalty;

    /**
     * 停用词
     */
    private String stop;
 
}