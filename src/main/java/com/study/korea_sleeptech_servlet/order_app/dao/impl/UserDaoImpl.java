package com.study.korea_sleeptech_servlet.order_app.dao.impl;

import com.study.korea_sleeptech_servlet.order_app.dao.UserDao;
import com.study.korea_sleeptech_servlet.order_app.db.DBConnection;
import com.study.korea_sleeptech_servlet.order_app.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Connection conn = DBConnection.getInstance().getConnection();

    private final String INSERT = "INSERT INTO `user` (name, email) VALUES (?, ?)";
    private final String FIND_BY_EMAIL = "SELECT * FROM `user` WHERE email = ?";
    private final String FIND_BY_ID = "SELECT * FROM `user` WHERE id = ?";
    private final String FIND_ALL = "SELECT * FROM `user`";

    @Override
    public boolean save(User user) {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());

            return pstmt.executeUpdate() > 0; // 저장된 경우 true, 그렇지 않은 경우 false 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL)
        ) {
            while(rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"));
                user.setId(rs.getInt("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        try (PreparedStatement pstmt = conn.prepareStatement(FIND_BY_EMAIL)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"));
                user.setId(rs.getInt("id"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement(FIND_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("email"));
                user.setId(rs.getInt("id"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}