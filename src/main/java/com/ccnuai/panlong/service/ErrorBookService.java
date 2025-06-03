package com.ccnuai.panlong.service;


import com.ccnuai.panlong.pojo.entity.Question;

import java.util.List;

public interface ErrorBookService {

    List<Question> findErrorQuestion(String studentId);
}
