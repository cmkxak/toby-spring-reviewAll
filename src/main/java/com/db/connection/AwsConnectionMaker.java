package com.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class AwsConnectionMaker {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_ROOT"), env.get("DB_PASSWORD"));
        return c;
    }
}
