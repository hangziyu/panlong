package com.ccnuai.panlong.service;

import com.ccnuai.panlong.pojo.VO.QuestionHistoryVO;
import com.ccnuai.panlong.pojo.entity.Question;

import java.net.MalformedURLException;
import java.util.List;

public interface QuestionService {

    List<Question> random(String chapterId);


    void saveQuestionHistory(String sessionId);

    QuestionHistoryVO questionHistory();
}
