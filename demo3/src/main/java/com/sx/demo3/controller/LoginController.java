package com.sx.demo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
    @RequestMapping("/login.html")
    public String login(){
        return  "login";
    }
    @RequestMapping("/logout.html")
    public String logout(){
        return  "logout";
    }
}
