package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.pojo.entity.Question;
import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    /**
     * 随机获取题目
     * @return
     */
    @PostMapping("/random")
    public Result random(@RequestParam String chapterId){
        log.info("随机获取题目");
        //TODO 随机获取题目(rpc调用)

        //从习题数据库中随机获取题目
        List<Question> question = questionService.random(chapterId);
        log.info("随机获取题目成功");
        return Result.ok(question);
    }

    /**
     * 保存答题记录
     * @param question
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody Question question){
        log.info("保存题目");
        //TODO 保存题目(rpc调用)
        log.info("保存题目成功");
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
