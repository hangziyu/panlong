package com.ccnuai.panlong.service;

import com.ccnuai.panlong.pojo.entity.Chapter;

import java.util.List;

public interface ChapterService {
    List<Chapter> findChapter(Integer bookId);
}
