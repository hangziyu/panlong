package com.ccnuai.panlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccnuai.panlong.pojo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 根据id查询题目
     * @param questionList
     * @return
     */
    List<Question> findQuestionByIds(@Param("ids") List<String> questionList);

    /**
     * 随机获取题目
     * @param chapterId
     * @return
     */
    List<Question> random(String chapterId);
}
