package com.ccnuai.panlong.service;

import com.ccnuai.panlong.pojo.entity.Book;
import com.ccnuai.panlong.pojo.entity.Chapter;

import java.util.List;

public interface BookService {
    List<Book> query();

    List<Chapter> queryChapterById(String bookId);
}
