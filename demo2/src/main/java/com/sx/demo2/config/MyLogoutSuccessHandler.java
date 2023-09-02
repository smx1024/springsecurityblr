package com.sx.demo2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @ClassName MyLogoutSuccessHandler
 * @Description 自定义认证注销处理
 * @Author Jiangnan Cui
 * @Date 2022/7/23 20:30
 * @Version 1.0
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msg", "注销成功，当前认证对象为：" + authentication);//打印认证信息
        result.put("status", 200);//打印状态码
        response.setContentType("application/json;charset=UTF-8");//设置响应类型
        String s = new ObjectMapper().writeValueAsString(result);//json格式转字符串
        response.getWriter().println(s);//打印json格式数据
    }
}
