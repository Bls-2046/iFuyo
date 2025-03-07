package com.github.ifuyo.utils.database.SQLite;

import com.github.ifuyo.error.database.DatabaseException;
import com.github.ifuyo.utils.EnvConfig;
import com.github.ifuyo.utils.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection implements DatabaseConnection {
    private final String dbUrl;
    private java.sql.Connection connection;

    /**
     * 构造函数
     */
    public SQLiteConnection() {
        this.dbUrl = EnvConfig.getEnvParameter("SQLITE_URL", "jdbc:sqlite:mail.db");
    }

    @Override
    public synchronized Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                // 加载 SQLite 驱动
                Class.forName("org.sqlite.JDBC");
                // 创建连接
                connection = DriverManager.getConnection(dbUrl);
                System.out.println("Connected to SQLite database: " + dbUrl);
            } catch (ClassNotFoundException e) {
                // 抛出自定义异常，类型为 OTHER_ERROR
                throw new DatabaseException("SQLite driver not found.", DatabaseException.ErrorType.OTHER_ERROR, e);
            } catch (SQLException e) {
                // 抛出自定义异常，类型为 CONNECTION_ERROR
                throw new DatabaseException("Failed to connect to SQLite database: " + dbUrl, DatabaseException.ErrorType.CONNECTION_ERROR, e);
            }
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null && !isConnectionClosed()) {
            try {
                connection.close();
                System.out.println("SQLite connection closed.");
            } catch (SQLException e) {
                // 抛出自定义异常，类型为 OTHER_ERROR
                throw new DatabaseException("Failed to close SQLite connection", DatabaseException.ErrorType.OTHER_ERROR, e);
            }
        }
    }

    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            // 抛出自定义异常，类型为 OTHER_ERROR
            throw new DatabaseException("Failed to check SQLite connection status", DatabaseException.ErrorType.OTHER_ERROR, e);
        }
    }
}