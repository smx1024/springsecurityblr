package com.sx.demo2.filter;

import com.sx.demo2.exception.KaptchaNotMatchException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KaptchaFilter extends UsernamePasswordAuthenticationFilter {
    public static final String KAPTCHA_KEY = "kaptcha";//默认值
    private String kaptcha = KAPTCHA_KEY;

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")){
            throw  new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //不设置就是默认值，可以避免写死，代码更规范
        String kaptcha = request.getParameter(getKaptcha());
        String sessionKaptcha = (String) request.getSession().getAttribute("kaptcha");
        if (!ObjectUtils.isEmpty(kaptcha) && !ObjectUtils.isEmpty(sessionKaptcha) &&
                kaptcha.equalsIgnoreCase(sessionKaptcha)) {
            return super.attemptAuthentication(request, response);
        }
        throw new KaptchaNotMatchException("验证码输入错误!");
    }

}
