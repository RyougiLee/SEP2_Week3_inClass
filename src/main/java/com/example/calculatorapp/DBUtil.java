package com.example.calculatorapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBUtil {
    // 加载 .env 文件
    private static final Dotenv dotenv = Dotenv.load();

    private static final String DB_HOST = dotenv.get("DB_HOST", "localhost");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");
    private static final String URL = "jdbc:mariadb://" + DB_HOST + ":3306/shopping_cart_localization";
    private static final String USER = "root";

    public static Connection getConnection() throws SQLException {
        if (PASSWORD == null) {
            throw new SQLException("database password are not included in .env");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
