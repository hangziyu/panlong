package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.UserService;
import com.ccnuai.panlong.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     */
    @PostMapping("/register")
    public Result register(@RequestParam String username, String password) {
        log.info("注册");
        userService.register(username, password);
        log.info("注册成功");
        return Result.ok();
    }

    @GetMapping("/login")
    public Result login(@RequestParam String username, String password) {
        log.info("登录");
        String userId = userService.login(username, password);
        if (userId == null) {
            return Result.fail("登录失败");
        }
        //生成jwt令牌

        String token = JwtUtil.sign(username,userId);
        System.out.println("token:" + token);

        log.info("登录成功");
        return Result.ok(token);
    }

    @PutMapping("/bind")
    public Result bind(@RequestParam String studentNumber,@RequestParam String userId,@RequestParam String name){
        log.info("绑定");
        //绑定
        try {
            userService.bind(studentNumber,userId,name);
        }catch (Exception e){
            return Result.fail("绑定失败");
        }
        log.info("绑定成功");
        return Result.ok();
    }

}
