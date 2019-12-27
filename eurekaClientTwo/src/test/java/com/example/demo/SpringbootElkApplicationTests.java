package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElkApplicationTests {
    @Test
    public void contextLoads() {
    }

    private Logger logger = LoggerFactory.getLogger(SpringbootElkApplicationTests.class);

    @Test
    public void test() throws Exception {

        for(int i=0;i<100;i++) {
            logger.info("输出info日志....");
            logger.debug("输出debug日志...");
            logger.error("输出error日志.....");
            logger.warn("输出warn日志....");
        }
    }
}
