package com.ccnuai.panlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccnuai.panlong.pojo.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("select * from book")
    List<Book> query();


}
