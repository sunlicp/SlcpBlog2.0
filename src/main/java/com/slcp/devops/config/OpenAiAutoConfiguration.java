package com.slcp.devops.config;
 
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
 
/**
 * 自动配置类
 * @author slcp
 * @date 2023-01-10 22:10:43
 */
@Configuration
@EnableConfigurationProperties(OpenAiProperties.class)
public class OpenAiAutoConfiguration {
}