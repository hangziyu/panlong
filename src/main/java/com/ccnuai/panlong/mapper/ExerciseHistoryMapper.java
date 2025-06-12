package com.ccnuai.panlong.mapper;

import com.ccnuai.panlong.pojo.entity.ExerciseHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExerciseHistoryMapper {
    /**
     * 保存题目历史
     * @param exerciseHistory
     */
    @Insert("insert into exercise_history (user_id, session_id, exercise_id, user_answer,correct_answer,is_correct,mastery_level,question_content,timestamp) " +
            "values (#{userId}, #{sessionId}, #{exerciseId}, #{userAnswer},#{correctAnswer},#{isCorrect},#{masteryLevel},#{questionContent},#{timestamp}) ")
    void save(ExerciseHistory exerciseHistory);

    /**
     * 根据用户ID获取题目历史
     * @param userId
     * @return
     */
    @Select("select * from exercise_history where user_id = #{userId}")
    List<ExerciseHistory> findByUserId(String userId);
}
