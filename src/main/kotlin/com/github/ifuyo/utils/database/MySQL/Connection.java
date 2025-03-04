package com.github.ifuyo.utils.database.MySQL;

import com.github.ifuyo.utils.EnvConfig;
import com.github.ifuyo.error.ExceptionHandler;
import com.github.ifuyo.utils.database.DatabaseConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection implements DatabaseConnection {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private java.sql.Connection connection;

    /**
     * 构造函数
     */
    public Connection() {
        this.dbUrl = EnvConfig.getEnvParameter("MYSQL_URL", "jdbc:mysql://localhost:3306/default_db");
        this.dbUser = EnvConfig.getEnvParameter("MYSQL_USER", "root");
        this.dbPassword = EnvConfig.getEnvParameter("MYSQL_PASSWORD", "password");
    }

    @Override
    public java.sql.Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            connection = ExceptionHandler.handle(
                    () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword),
                    "Failed to connect to MySQL"
            );
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null && !isConnectionClosed()) {
            ExceptionHandler.handle(
                    () -> connection.close(),
                    "Failed to close MySQL connection"
            );
        }
    }

    /**
     * 检查连接是否已关闭
     */
    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Failed to check MySQL connection status: " + e.getMessage());
            return true; // 假设连接已关闭
        }
    }
}
