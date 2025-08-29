package com.slcp.devops.config;

import com.slcp.devops.utils.OpenAiUtils;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author slcp
 * @date 2023-01-10 22:10:43
 */

@Data
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties implements InitializingBean {
    /**
     * 秘钥
     */
    List<String> token;
    /**
     * 超时时间
     */
    Integer timeout;

    /**
     * 设置属性时同时设置给OpenAiUtils
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        OpenAiUtils.OPENAPI_TOKEN = token;
        OpenAiUtils.TIMEOUT = timeout;
    }
}