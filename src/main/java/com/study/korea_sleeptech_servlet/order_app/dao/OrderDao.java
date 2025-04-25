package com.study.korea_sleeptech_servlet.order_app.dao;

import com.study.korea_sleeptech_servlet.order_app.entity.Order;

import java.util.List;

// 주문 정보 DAO 인터페이스
public interface OrderDao {
    boolean save(Order order);
    List<Order> findByUserId(int userId);
    List<Order> findAll();
}