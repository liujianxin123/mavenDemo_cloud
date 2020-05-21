package com.jxliu.nacosconsumer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/customer")
public class ConsumerController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "hiResttemplate", method = RequestMethod.GET)
    public String hiResttemplate(){
        return restTemplate.getForObject("http://nacos-provider/Provider/hi?name=resttemplate",String.class);

    }
}
