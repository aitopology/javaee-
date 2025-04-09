package com.example.dao;

public interface UserDao {
    boolean validateUser(String username, String password);

    void createUser(String username, String encodedPassword);
}