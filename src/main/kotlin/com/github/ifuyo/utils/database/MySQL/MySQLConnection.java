package com.github.ifuyo.utils.database.MySQL;

import com.github.ifuyo.error.database.DatabaseException;
import com.github.ifuyo.utils.EnvConfig;
import com.github.ifuyo.utils.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection implements DatabaseConnection {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private java.sql.Connection connection;

    /**
     * 构造函数
     */
    public MySQLConnection() {
        this.dbUrl = EnvConfig.getEnvParameter("MYSQL_URL", "jdbc:mysql://localhost:3306/default_db");
        this.dbUser = EnvConfig.getEnvParameter("MYSQL_USER", "root");
        this.dbPassword = EnvConfig.getEnvParameter("MYSQL_PASSWORD", "password");
    }

    @Override
    public synchronized Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                // 加载 MySQL 驱动
                Class.forName("com.mysql.cj.jdbc.Driver");
                // 创建连接
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                System.out.println("Connected to MySQL database: " + dbUrl);
            } catch (ClassNotFoundException e) {
                // 抛出自定义异常，类型为 OTHER_ERROR
                throw new DatabaseException("MySQL driver not found.", DatabaseException.ErrorType.OTHER_ERROR, e);
            } catch (SQLException e) {
                // 抛出自定义异常，类型为 CONNECTION_ERROR
                throw new DatabaseException("Failed to connect to MySQL database: " + dbUrl, DatabaseException.ErrorType.CONNECTION_ERROR, e);
            }
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null && !isConnectionClosed()) {
            try {
                connection.close();
                System.out.println("MySQL connection closed.");
            } catch (SQLException e) {
                // 抛出自定义异常，类型为 OTHER_ERROR
                throw new DatabaseException("Failed to close MySQL connection", DatabaseException.ErrorType.OTHER_ERROR, e);
            }
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
