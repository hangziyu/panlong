package com.ccnuai.panlong.mapper;

import com.ccnuai.panlong.pojo.entity.TeachingDialog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeachingDialogMapper {

    /**
     * 保存教学对话
     * @param dialogEntity
     */
    @Insert("insert into teaching_dialog (user_id,session_id,exercise_id ,role, content) values" +
            " (#{userId},#{sessionId},#{exerciseId},#{role},#{content})")
    void save(TeachingDialog dialogEntity);

    /**
     * 根据用户ID获取教学对话
     * @param userId
     * @return
     */
    @Select("select * from teaching_dialog where user_id = #{userId}")
    List<TeachingDialog> findByUserId(String userId);
}
