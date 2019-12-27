package com.example.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "say")
public class SayHiController {
    private final static Logger logger = LoggerFactory.getLogger(SayHiController.class);
    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

    @RequestMapping("/logTest")
    public String logTest() throws Exception {
        System.out.println("分割线-------");
        for(int i=0;i<10;i++) {
            logger.info("输出info日志:请求成功！");
            logger.warn("输出warn日志：请求成功！");
        }
        return "请求成功！";
    }
}
