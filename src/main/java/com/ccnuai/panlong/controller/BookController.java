package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.pojo.entity.Book;
import com.ccnuai.panlong.pojo.entity.Chapter;
import com.ccnuai.panlong.result.Result;
import com.ccnuai.panlong.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 查询书籍
     * @return
     */
    @GetMapping("/query")
    public Result query(){
        log.info("查询书籍");
        List<Book> list = bookService.query();
        log.info("查询书籍成功");
        return Result.ok(list);
    }

}
