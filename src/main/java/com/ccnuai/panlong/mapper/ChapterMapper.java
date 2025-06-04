package com.ccnuai.panlong.mapper;

import com.ccnuai.panlong.pojo.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterMapper {
    /**
     * 查询章节
     * @return
     */
    List<Chapter> queryChapterById(String bookId);
}
