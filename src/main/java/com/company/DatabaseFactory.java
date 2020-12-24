package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseFactory {
    private String driverClass;
    private String databaseUrl;
    private String username;
    private String password;

    public DatabaseFactory(String driverClass, String databaseUrl, String username, String password) {
        this.driverClass = driverClass;
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
    }

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        return DriverManager.getConnection(databaseUrl, username, password);
    }
}
