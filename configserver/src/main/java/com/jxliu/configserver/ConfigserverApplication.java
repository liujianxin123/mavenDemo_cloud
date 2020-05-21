package com.jxliu.configserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigserverApplication {
    private static final Logger logger = LoggerFactory.getLogger(ConfigserverApplication.class);

    public static void main(String[] args) {
        logger.warn("ConfigserverApplication项目启动。。");
        SpringApplication.run(ConfigserverApplication.class, args);
        logger.warn("ConfigserverApplication项目启动完毕。。");
    }

}
