package com.jxliu.demo.web;

import com.jxliu.demo.entity.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class KafkaController {
    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping(value = "/test")
    public void test(){

        //kafkaSender.sendChannelMess("testTopic","caonima");


        //kafkaSender.sendChannelMap("test2","");

    }
}
