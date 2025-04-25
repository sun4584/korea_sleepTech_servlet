package com.study.korea_sleeptech_servlet.order_app.dao.impl;

import com.study.korea_sleeptech_servlet.order_app.dao.OrderDao;
import com.study.korea_sleeptech_servlet.order_app.db.DBConnection;
import com.study.korea_sleeptech_servlet.order_app.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private final Connection conn = DBConnection.getInstance().getConnection();

    private final String INSERT = "INSERT INTO `order` (user_id, product_name, amount) VALUES (?, ?, ?)";
    private final String FIND_BY_USER_ID = "SELECT * FROM `order` WHERE user_id = ?";
    private final String FIND_ALL = "SELECT * FROM `order`";

    @Override
    public boolean save(Order order) {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            pstmt.setInt(1, order.getUserId());
            pstmt.setString(2, order.getProductName());
            pstmt.setInt(3, order.getAmount());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Order> findByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql;

        if (userId == 0) {
            sql = FIND_ALL;
        } else {
            sql = FIND_BY_USER_ID;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (userId != 0) {
                pstmt.setInt(1, userId);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("user_id")
                        , rs.getString("product_name")
                        , rs.getInt("amount"));
                order.setId(rs.getInt("id"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> findAll() {
        return findByUserId(0); // 내부적으로 userId가 0이면 전체 조회
    }
}