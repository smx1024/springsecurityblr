package com.sx.demo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public  String hello(){
        System.out.println("你好sx");
        return "hello";
    }
}
