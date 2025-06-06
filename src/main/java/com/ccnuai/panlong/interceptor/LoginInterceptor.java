package com.ccnuai.panlong.interceptor;

import com.ccnuai.panlong.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            System.out.println("OPTIONS请求");
            return true;
        }
        //获取token
        String token = request.getHeader("token");
        //验证
        if (JwtUtil.verify(token)){
            return true;
        }else {
            response.setStatus(401);
            return false;
        }
    }
}
