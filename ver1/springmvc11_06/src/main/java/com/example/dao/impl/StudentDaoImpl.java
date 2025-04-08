package com.example.dao.impl;

import com.example.dao.StudentDao;
import com.example.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Student> queryAll() {
        return queryByPage(0, Integer.MAX_VALUE);
    }

    @Override
    public List<Student> queryByPage(int offset, int pageSize) {
        String sql = "SELECT * FROM (SELECT t.*, ROWNUM rn FROM (SELECT no,name,age FROM t_student ORDER BY no) t WHERE ROWNUM <= ?) WHERE rn > ?";
        int endRow = offset + pageSize;
        int startRow = offset;
        return this.jdbcTemplate.query(sql, new RowMapperImpl(), endRow, startRow);
    }

    @Override
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM t_student";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Student queryByNo(String no) {
        String sql = "SELECT no,name,age FROM t_student WHERE no = ?";
        return this.jdbcTemplate.queryForObject(sql, new RowMapperImpl(), no);
    }

    @Override
    public int insert(Student student) {
        String sql = "insert into t_student values(?,?,?)";
        int i = this.jdbcTemplate.update(sql,
                student.getNo(),
                student.getName(),
                student.getAge());
        return i;
    }

    @Override
    public int updateByNo(Student student) {
        String sql = "UPDATE t_student SET name=?, age=? WHERE no=?";
        return this.jdbcTemplate.update(sql,
                student.getName(),
                student.getAge(),
                student.getNo());
    }

    @Override
    public int deleteByNo(String no) {
        String sql = "DELETE FROM t_student WHERE no = ?";
        return this.jdbcTemplate.update(sql, no);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * 内部类:自定义的获取结果集数据的实现类
     */
    class RowMapperImpl implements RowMapper<Student>{

        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setNo(rs.getString("no"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            return student;
        }
    }
}

