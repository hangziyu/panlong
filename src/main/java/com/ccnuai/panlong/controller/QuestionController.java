package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.pojo.DTO.RandomRequest;
import com.ccnuai.panlong.pojo.entity.Question;
import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

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
    public Result random(@RequestParam String chapterId){
        log.info("随机获取题目");
        //TODO 随机获取题目(rpc调用)
        String url = "https://444d-223-76-127-34.ngrok-free.app/exercise/random";

//        String url = "https://localhost:8080/exercise/random";

        // 构建请求体
        RandomRequest request = new RandomRequest();
        request.setQuestionPath("/root/Dlx/HTTP/Exercise/导数.docx");
        request.setSession_id(chapterId);
        // 设置 headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RandomRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        // 发送 POST 请求
        return Result.ok(response.getBody());
//        //从习题数据库中随机获取题目
//        List<Question> question = questionService.random(chapterId);
//        log.info("随机获取题目成功");
//        return Result.ok(question);
    }

    /**
     * 保存答题记录
     * @param question
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody Question question){
        log.info("保存答题历史");
        //TODO 答题历史(rpc调用)

        log.info("保存答题历史");
        return Result.ok();
    }


    /**
     * 获取答题记录
     * @param userId
     * @return
     */
    @PostMapping("/answerRecord")
    public Result answerRecord(@RequestParam String userId){
        log.info("获取答题记录");
        //TODO 获取答题记录(rpc调用)

        log.info("获取答题记录成功");
        return Result.ok();
    }

}
