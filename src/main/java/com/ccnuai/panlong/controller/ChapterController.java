package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.pojo.entity.Chapter;
import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/findChapter")
    public Result findChapter(@RequestParam Integer bookId){
        log.info("查询章节");
        List<Chapter> chapterList = chapterService.findChapter(bookId);
        return Result.ok(chapterList);
    }

}
