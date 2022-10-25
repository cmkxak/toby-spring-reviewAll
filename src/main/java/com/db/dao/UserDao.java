package com.db.dao;

import com.db.context.JdbcContext;
import com.db.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    private JdbcContext jdbcContext;

    public UserDao(DataSource datasource) {
        this.jdbcContext = new JdbcContext(datasource);
    }

    public void add(User user) throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("INSERT INTO users values (?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });
    }

    public void deleteAll(){
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
    }
}
