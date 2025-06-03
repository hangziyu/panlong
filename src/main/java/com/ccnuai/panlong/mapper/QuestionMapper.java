package com.ccnuai.panlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccnuai.panlong.pojo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> findQuestionByIds(@Param("ids") List<String> questionList);

}
