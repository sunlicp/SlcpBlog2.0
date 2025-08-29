package com.slcp.devops;

import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Slcp
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages="com.slcp.devops.mapper")
public class DevopsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(DevopsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
