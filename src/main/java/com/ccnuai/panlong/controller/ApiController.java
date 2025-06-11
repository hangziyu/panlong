package com.ccnuai.panlong.controller;

import com.alibaba.fastjson.JSON;
import com.ccnuai.panlong.listener.SSEListener;
import com.ccnuai.panlong.pojo.DTO.ChatGlmDto;
import com.ccnuai.panlong.utils.ExecuteSSEUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {
    private static final String URL = "";


    @PostMapping(value = "/sse-invoke", produces = "text/event-stream;charset=UTF-8" )
    public void sseInvoke(@RequestBody ChatGlmDto chatGlmDto, HttpServletResponse response) {
        try {
            SSEListener sseListener = new SSEListener(chatGlmDto,response);
            ExecuteSSEUtil.executeSSE(URL, "", sseListener, JSON.toJSONString(chatGlmDto));
        }catch (Exception e){
            log.error("请求错误",e);
        }
    }


}
