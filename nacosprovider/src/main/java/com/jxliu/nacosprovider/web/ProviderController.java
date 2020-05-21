package com.jxliu.nacosprovider.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Provider")
public class ProviderController {
    Logger logger= LoggerFactory.getLogger(ProviderController.class);

    @GetMapping("hi")
    public String hi(@RequestParam(value = "name",defaultValue = "forezp",required = false)String name){
        System.out.println("come in ProviderController...");
        return "hi "+name;
    }
}
