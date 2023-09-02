package com.sx.demo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/index")
    public String index(){
        System.out.println("index页面");
        return  "登录成功，这是首页";
    }
}
