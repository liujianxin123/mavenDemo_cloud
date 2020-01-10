package com.jxliu.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EuerkaServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(EuerkaServerApplication.class);

    public static void main(String[] args) {
        logger.warn("启动EuerkaServer服务");
        SpringApplication.run(EuerkaServerApplication.class, args);
        logger.warn("启动EuerkaServer服务完毕");
    }

}
