package com.ccnuai.panlong.service;

import com.ccnuai.panlong.pojo.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> random(String chapterId);
}
