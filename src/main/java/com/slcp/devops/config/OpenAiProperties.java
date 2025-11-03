package com.slcp.devops.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author slcp
 * @date 2023-01-10 22:10:43
 * @deprecated 已废弃，请使用新的 Spring AI 配置
 * 该类仅保留用于向后兼容，将在未来版本中移除
 */
@Deprecated
@Data
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {
    /**
     * 秘钥
     */
    List<String> token;
    /**
     * 超时时间
     */
    Integer timeout;
}