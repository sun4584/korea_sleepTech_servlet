package com.study.korea_sleeptech_servlet.dao;

import com.study.korea_sleeptech_servlet.db.DBConnection;
import com.study.korea_sleeptech_servlet.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// DAO: DATA ACCESS OBJECT (DB의 데이터에 접근하기 위한 객체)
public class UserDao {

    // 1. CREATE
    public void insertUser(User user) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UserSql.INSERT)
        ) {
            pstmt.setString(1, user.getName()); // parameterIndex: 동적 파라미터 순서 (1부터 시작)
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getCountry());

            pstmt.executeUpdate();
        }
    }

    // 2. READ - 단일
    public User selectUser(int id) throws SQLException {
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UserSql.SELECT_BY_ID)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        id,
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                );
            }
        }
        return user;
    }

    // 3. READ - 전체
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UserSql.SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                ));
            }
        }
        return users;
    }

    // 4. UPDATE - 수정
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UserSql.UPDATE)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getCountry());
            pstmt.setInt(4, user.getId());

            rowUpdated = pstmt.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // 5. DELETE - 삭제
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UserSql.DELETE)) {
            pstmt.setInt(1, id);
            rowDeleted = pstmt.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}