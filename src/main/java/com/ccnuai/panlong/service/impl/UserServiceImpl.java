package com.ccnuai.panlong.service.impl;

import com.ccnuai.panlong.mapper.StudentMapper;
import com.ccnuai.panlong.mapper.UserMapper;
import com.ccnuai.panlong.pojo.DTO.UserDTO;
import com.ccnuai.panlong.pojo.entity.Student;
import com.ccnuai.panlong.pojo.entity.User;
import com.ccnuai.panlong.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 注册
     *
     */
    @Override
    public void register(String username, String password) {
        //
        String nameId = "0";
        // 注册逻辑
        UserDTO user = new UserDTO(username, password, nameId, null);
        // 保存用户信息到数据库
        userMapper.insert(user);

    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        // 登录逻辑
        // 根据用户名查询用户信息
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功，返回用户ID
            return user.getId();
        }
        else {
            // 登录失败，返回null
            return null;
        }

    }

    /**
     * 绑定学生号和用户id
     * @param studentNumber
     * @param userId
     */
    @Transactional
    @Override
    public void bind(String studentNumber, String userId, String name) {
        //查询学生表是否存在该学生号
        Student student = studentMapper.selectByStudentNumber(studentNumber);
        if (student == null) {
            //学生不存在，新加学生
            Student newStudent = new Student();
            newStudent.setStudentNumber(studentNumber);
            newStudent.setName(name);
            studentMapper.insert(newStudent);
            student = studentMapper.selectByStudentNumber(studentNumber);
        }
        //更新用户表的nameId
        String studentId = student.getId();
        //根据userid更新用户表的nameId
        userMapper.updateNameId(userId, studentId);
    }
}
