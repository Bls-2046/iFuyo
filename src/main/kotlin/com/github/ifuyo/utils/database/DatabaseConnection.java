package com.github.ifuyo.utils.database;

import java.sql.Connection;

public interface DatabaseConnection {
    /**
     * 获取数据库连接
     *
     * @return 数据库连接，如果连接失败则返回 null
     */
    Connection getConnection();

    /**
     * 关闭数据库连接
     */
    void closeConnection();
}