package com.study.korea_sleeptech_servlet.dao;

// JDBC에서 쓰일 직접적인 SQL문을 분리
public class UserSql {
    public static final String INSERT = "INSERT INTO user (name, email, country) VALUES (?, ?, ?)";
    public static final String SELECT_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String SELECT_ALL = "SELECT * FROM user";
    public static final String UPDATE = "UPDATE user SET name = ?, email = ?, country = ? WHERE id = ?";
    public static final String DELETE = "DELETE FROM user WHERE id = ?";
}