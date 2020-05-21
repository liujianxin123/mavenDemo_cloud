package com.jxliu.demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "say")
public class SayHiController {

    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

    public void sss(){
    }


    public static void main(String[] args) {

        String str = "hello";
        test(str);
        System.out.println(str);
    }

    public static void test(String str){
        str = str + "world";
        System.out.println(str);
        int[] a = new int[1024];
    }
}
