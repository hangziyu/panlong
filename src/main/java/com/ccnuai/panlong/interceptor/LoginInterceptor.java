package com.ccnuai.panlong.interceptor;

import com.ccnuai.panlong.pojo.DTO.UserDTO;
import com.ccnuai.panlong.pojo.entity.UserHolder;
import com.ccnuai.panlong.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    //todo 登录拦截器(暂时关闭了)

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //1.获取session
//        HttpSession session = request.getSession();
//        //2.获取session中的user
//        Object user = session.getAttribute("user");
//        //3.判断user是否为空
//        if (user == null) {
//            //4.不存在，拦截，返回401状态码
//            response.setStatus(401);
//            return false;
//        }
//        //5.存在，保存用户信息到手动封装的Threadlocal中
//        UserHolder.saveUser((UserDTO)user);
//        //6.放行
//        return true;
//    }


//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
//            System.out.println("OPTIONS请求");
//            return true;
//        }
//        //获取token
//        String token = request.getHeader("token");
//        //验证
//        if (JwtUtil.verify(token)){
//            return true;
//        }else {
//            response.setStatus(401);
//            return false;
//        }
//    }
}
