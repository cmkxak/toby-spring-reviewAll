package com.db.context;

import com.db.dao.StatementStrategy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy){
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            c = dataSource.getConnection();
            pstmt = statementStrategy.makeStatement(c);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void executeSql(final String sql){
        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection c) throws SQLException {
                return c.prepareStatement(sql);
            }
        });
    }
}
