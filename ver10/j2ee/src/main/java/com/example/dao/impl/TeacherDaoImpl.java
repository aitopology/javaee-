package com.example.dao.impl;

import com.example.dao.TeacherDao;
import com.example.model.Teacher;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM t_teacher WHERE name = ?";
        Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public List<Teacher> queryAll() {
        return queryByPage(0, Integer.MAX_VALUE);
    }

    @Override
    public List<Teacher> queryByPage(int offset, int pageSize) {
        String sql = "SELECT * FROM (SELECT t.*, ROWNUM rn FROM (SELECT name,phone,subject FROM t_teacher ORDER BY name) t WHERE ROWNUM <= ?) WHERE rn > ?";
        int endRow = offset + pageSize;
        int startRow = offset;
        return this.jdbcTemplate.query(sql, new RowMapperImpl(), endRow, startRow);
    }

    @Override
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM t_teacher";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Teacher queryByName(String name) {
        String sql = "SELECT name,phone,subject FROM t_teacher WHERE name = ?";
        List<Teacher> teachers = this.jdbcTemplate.query(sql, new RowMapperImpl(), name);
        if (teachers.isEmpty()) return null;
        if (teachers.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, teachers.size());
        }
        return teachers.get(0);
    }

    @Override
    public int insert(Teacher teacher) {
        String sql = "insert into t_teacher values(?,?,?)";
        int i = this.jdbcTemplate.update(sql,
                teacher.getName(),
                teacher.getSubject(),
                teacher.getPhone()
                );
        return i;
    }

    @Override
    @Transactional
    public int updateByName(Teacher teacher) {
        String sql = "UPDATE t_teacher SET phone=?, subject=? WHERE name=?";
        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: phone=" + teacher.getPhone() + ", subject=" + teacher.getSubject() + ", name=" + teacher.getName());
        return this.jdbcTemplate.update(sql,
                teacher.getPhone(),
                teacher.getSubject(),
                teacher.getName());
    }

    @Override
    public int deleteByName(String name) {
        String sql = "DELETE FROM t_teacher WHERE name = ?";
        return this.jdbcTemplate.update(sql, name);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * 内部类:自定义的获取结果集数据的实现类
     */
    class RowMapperImpl implements RowMapper<Teacher>{

        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            Teacher teacher = new Teacher();
            teacher.setName(rs.getString("name"));
            teacher.setPhone(rs.getString("phone"));
            teacher.setSubject(rs.getString("subject"));
            return teacher;
        }
    }
}