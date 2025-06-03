package com.ccnuai.panlong.mapper;

import com.ccnuai.panlong.pojo.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {


    @Select("select * from student where student_number = #{studentNumber}")
    Student selectByStudentNumber(String studentNumber);

    @Insert("insert into student (student_number, name) values (#{studentNumber}, #{name})")
    void insert(Student newStudent);
}
