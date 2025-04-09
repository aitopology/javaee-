package com.example.dao.impl;

import com.example.dao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM udata WHERE username=? AND password=?";
        try {
            Map<String, Object> user = jdbcTemplate.queryForMap(sql, username, password);
            return user != null;
        } catch (Exception e) {
            return false;
        }
    }
}