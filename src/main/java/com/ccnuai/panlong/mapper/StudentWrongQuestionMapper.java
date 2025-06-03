package com.ccnuai.panlong.mapper;

import com.ccnuai.panlong.pojo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentWrongQuestionMapper {


    @Select("select question_id from student_wrong_question where student_id = #{studentId}")
    List<String> findErrorQuestion(String studentId);
}
