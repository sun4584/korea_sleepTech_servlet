package com.study.korea_sleeptech_servlet.order_app.dao;

import com.study.korea_sleeptech_servlet.order_app.entity.User;

import java.util.List;

// 사용자 DAO 인터페이스
public interface UserDao {
    boolean save(User user);

    List<User> findAll();
    User findByEmail(String email);
    User findById(int id);
}