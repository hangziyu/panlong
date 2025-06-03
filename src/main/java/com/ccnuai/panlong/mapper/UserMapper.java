package com.ccnuai.panlong.mapper;

import com.ccnuai.panlong.pojo.DTO.UserDTO;
import com.ccnuai.panlong.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("insert into user (username, password, name_id) values (#{username}, #{password}, #{nameId})")
    void insert(UserDTO user);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    @Update("update user set name_id = #{studentId} where id = #{userId}")
    void updateNameId(String userId, String studentId);
}
