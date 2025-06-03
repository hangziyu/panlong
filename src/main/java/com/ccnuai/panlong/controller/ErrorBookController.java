package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.pojo.entity.Question;
import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.ErrorBookService;
import com.ccnuai.panlong.service.impl.ErrorBookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/errorBook")
public class ErrorBookController {

    @Autowired
    private ErrorBookService errorBookService;

    /**
     * 查询错题
     * @return
     */
    @GetMapping("/finderrorquetion")
    public Result errorQuestion(@RequestParam String studentId){
        log.info("查询错题");
        List<Question> questionList = errorBookService.findErrorQuestion(studentId);
        log.info("查询错题成功");
        return Result.ok(questionList);
    }
}
