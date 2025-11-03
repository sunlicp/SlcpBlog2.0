package com.slcp.devops.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AI 配置属性类
 * 统一管理 Ollama 的配置
 * 
 * @author slcp
 * @date 2025-10-27
 */
@Data
@ConfigurationProperties(prefix = "spring.ai")
public class AiProperties {

    private OllamaConfig ollama = new OllamaConfig();
    
    /**
     * Ollama 配置
     */
    @Data
    public static class OllamaConfig {
        /**
         * Ollama 服务地址
         */
        private String baseUrl = "http://127.0.0.1:11434";
        
        /**
         * 模型名称
         */
        private String model = "qwen2.5:0.5b";
        
        /**
         * 温度参数 (0.0-2.0)
         */
        private Double temperature = 0.7;
        
        /**
         * 最大 Token 数
         */
        private Integer maxTokens = 2000;
    }
}


