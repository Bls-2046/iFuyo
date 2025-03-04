package com.github.ifuyo.utils.database.SQLite;

import com.github.ifuyo.utils.EnvConfig;
import com.github.ifuyo.error.ExceptionHandler;
import com.github.ifuyo.utils.database.DatabaseConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection implements DatabaseConnection {
    private final String dbUrl;
    private java.sql.Connection connection;

    /**
     * 构造函数
     */
    public Connection() {
        this.dbUrl = EnvConfig.getEnvParameter("SQLITE_URL", "jdbc:sqlite:mail.db");
    }

    @Override
    public java.sql.Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            connection = ExceptionHandler.handle(
                    () -> DriverManager.getConnection(dbUrl),
                    "Failed to connect to SQLite"
            );
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null && !isConnectionClosed()) {
            ExceptionHandler.handle(
                    () -> connection.close(),
                    "Failed to close SQLite connection"
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
            System.err.println("Failed to check SQLite connection status: " + e.getMessage());
            return true; // 假设连接已关闭
        }
    }
}