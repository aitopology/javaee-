package com.example.dao;

import com.example.model.Student;

import java.util.List;

public interface StudentDao {
    //查询所有的学生信息
    List<Student> queryAll();
    //根据学号查询对应的学生信息
    Student queryByNo(String no);
    //添加学生
    int insert(Student student);
    //根据学号更新学生信息
    int updateByNo(Student student);
    //根据学号删除对应的学生信息
    int deleteByNo(String no);

    // 分页查询学生
    List<Student> queryByPage(int offset, int pageSize);

    // 查询总记录数
    int getTotalCount();
}
