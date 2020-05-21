package com.jxliu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@SpringBootApplication
@MapperScan("com.jxliu.demo.mapper")
public class ServiceHiApplication {

    private static final Logger logger = LoggerFactory.getLogger(ServiceHiApplication.class);
    public static void main(String[] args) {
        logger.warn("启动ServiceHiServer服务。。");
        SpringApplication.run(ServiceHiApplication.class, args);
        logger.warn("启动ServiceHiServer服务完毕。。");
    }

}
