package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.pojo.DTO.*;
import com.ccnuai.panlong.pojo.VO.QuestionHistoryVO;
import com.ccnuai.panlong.pojo.entity.UserHolder;
import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.QuestionService;

import com.ccnuai.panlong.utils.CheckAnswerExerciseSSEClient;
import com.ccnuai.panlong.utils.RandomExerciseSSEClient;
import com.ccnuai.panlong.utils.TeachQuestionSSEClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.io.*;
import java.net.MalformedURLException;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 随机获取题目
     * @return
     */
    @PostMapping("/random")
    public SseEmitter generateExercise(@RequestBody RandomQuestionDTO request) {
        log.info("随机获取题目");
        SseEmitter emitter = new SseEmitter(0L); // 0 表示不超时
        new Thread(() -> {
            try {
                // 调用你的SSE客户端，传入emitter进行事件推送
                new RandomExerciseSSEClient().startSSE(request, emitter);
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data(e.getMessage()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    emitter.completeWithError(e);
                }
            }
        }).start();
        return emitter;
    }

    /**
     * 检查答案
     * @param checkAnswerDTO
     * @return
     * @throws IOException
     */
    //TODO 这个接口有误。
    @PostMapping("/check_answer")
    public SseEmitter checkAnswer(@RequestBody CheckAnswerDTO checkAnswerDTO) throws IOException {
        log.info("检查答案：{}", checkAnswerDTO);


        // 不超时，最大连接时间为无限
        SseEmitter emitter = new SseEmitter(0L);

        // 异步线程发起远程 SSE 请求
        new Thread(() -> {
            try {
                new CheckAnswerExerciseSSEClient().startSSE(checkAnswerDTO, emitter);
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data("内部错误: " + e.getMessage()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    emitter.completeWithError(e);
                }
            }
        }).start();

        return emitter;
    }


    /**
     * 保存题目历史
     * @param sessionId
     * @return
     */
    @GetMapping("/save/{sessionId}")
    public Result save(@PathVariable String sessionId){
        log.info("保存题目");
        //TODO 保存题目(rpc调用)
        questionService.saveQuestionHistory(sessionId);
        return Result.ok();
    }

    /**
     * 获取题目历史
     * @return
     */
    @GetMapping("/history")
    public Result history(){
        log.info("获取题目历史");
        QuestionHistoryVO history = questionService.questionHistory();
        log.info("获取题目历史成功");
        return Result.ok(history);
    }

    /**
     * 教学对话
     * @param request
     * @return
     */
    @PostMapping("/teach")
    public SseEmitter teach(@RequestBody TeachQuestionDTO request) {
        log.info("教学对话");
        SseEmitter emitter = new SseEmitter(0L); // 0 表示不超时
        new Thread(() -> {
            try {
                // 调用你的SSE客户端，传入emitter进行事件推送
                new TeachQuestionSSEClient().startSSE(request,emitter);
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data(e.getMessage()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    emitter.completeWithError(e);
                }
            }
        }).start();
        return emitter;
    }



}
