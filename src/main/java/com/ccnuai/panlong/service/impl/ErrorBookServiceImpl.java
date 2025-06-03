package com.ccnuai.panlong.service.impl;

import com.ccnuai.panlong.mapper.QuestionMapper;
import com.ccnuai.panlong.mapper.StudentWrongQuestionMapper;
import com.ccnuai.panlong.pojo.entity.Question;
import com.ccnuai.panlong.service.ErrorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorBookServiceImpl implements ErrorBookService {

    @Autowired
    private StudentWrongQuestionMapper studentWrongQuestionMapper;

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 查询错题
     * @return
     */
    @Override
    public List<Question> findErrorQuestion(String studentId) {
        List<String> questionList = studentWrongQuestionMapper.findErrorQuestion(studentId);
        if (questionList == null) {
            return null;
        }else {
            List<Question> list = questionMapper.findQuestionByIds(questionList);
            return list;
        }

    }



}
