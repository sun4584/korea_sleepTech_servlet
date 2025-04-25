package com.study.korea_sleeptech_servlet.order_app.db;

import java.sql.Connection;
import java.sql.DriverManager;

// 싱글톤 패턴 구현
public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/shop_db";
    private final String USER = "root";
    private final String PASSWORD = "root";

    private DBConnection() {
        try  {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}