package com.ccnuai.panlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccnuai.panlong.pojo.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {
    /**
     * 查询章节
     * @return
     */
    @Select("select * from chapter where book_id = #{bookId}")
    List<Chapter> queryChapterById(Integer bookId);


}
