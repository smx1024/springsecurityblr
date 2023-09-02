package com.sx.demo2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //失败同理
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg","登录成功");
        hashMap.put("status",200);
        hashMap.put("authentication",authentication);
        response.setContentType("application/json;charset=UTF-8");
        String s=new ObjectMapper().writeValueAsString(hashMap);
        response.getWriter().println(s);
    }
}
