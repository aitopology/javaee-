package com.example.dao;

import com.example.model.Teacher;

import java.util.List;

public interface TeacherDao {
    boolean existsByName(String name);

    //查询所有的学生信息
    List<Teacher> queryAll();
    //根据学号查询对应的学生信息
    Teacher queryByName(String name);
    //添加学生
    int insert(Teacher teacher);
    //根据学号更新学生信息
    int updateByName(Teacher teacher);
    //根据学号删除对应的学生信息
    int deleteByName(String name);

    // 分页查询学生
    List<Teacher> queryByPage(int offset, int pageSize);

    // 查询总记录数
    int getTotalCount();

}


/*
package com.example.dao;

import com.example.model.Teacher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeacherDao {
    List<Teacher> findAll(int currentPage, int pageSize);
    int getTotalCount();
    int insert(Teacher teacher);
    void update(Teacher teacher);
    void delete(int id);
    Teacher findById(int id);


}*/