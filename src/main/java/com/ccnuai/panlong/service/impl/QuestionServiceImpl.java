package com.ccnuai.panlong.service.impl;

import com.ccnuai.panlong.mapper.QuestionMapper;
import com.ccnuai.panlong.pojo.entity.Question;
import com.ccnuai.panlong.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;


    /**
     * 随机获取题目
     * @return
     */
    @Override
    public List<Question> random(String chapterId) {
        return questionMapper.random(chapterId);
    }
}
