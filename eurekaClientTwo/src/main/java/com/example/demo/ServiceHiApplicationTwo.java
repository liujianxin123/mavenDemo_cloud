package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@SpringBootApplication
public class ServiceHiApplicationTwo {
    private static final Logger logger = LoggerFactory.getLogger(ServiceHiApplicationTwo.class);
    public static void main(String[] args) {
        logger.warn("ServiceHiApplicationTwo项目启动。。");
        SpringApplication.run(ServiceHiApplicationTwo.class, args);
        logger.warn("ServiceHiApplicationTwo项目启动成功。。");
    }

}
