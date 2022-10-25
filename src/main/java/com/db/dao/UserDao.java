package com.db.dao;

import com.db.connection.ConnectionMaker;
import com.db.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = null;

        c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();

    }

    public User findById(String id) throws ClassNotFoundException, SQLException {
        Connection c = null;

        c = connectionMaker.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public List<User> findAll() {
        Connection c = null;
        Statement statement = null;
        ResultSet rs = null;

        List<User> userList = new ArrayList<>();
        try {
            c = connectionMaker.getConnection();

            statement = c.createStatement();

            rs = statement.executeQuery("select * from users");

            while (rs.next()) {
                User user = new User(rs.getString("id"),
                        rs.getString("name"), rs.getString("password"));
                userList.add(user);
            }

            rs.close();
            statement.close();
            c.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        c = connectionMaker.getConnection();
        pstmt = c.prepareStatement("select count(*) from users");
        rs = pstmt.executeQuery();
        rs.next();

        c.close();
        pstmt.close();
        rs.close();

        return rs.getInt(1);
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement pstmt = null;

        c = connectionMaker.getConnection();
        pstmt = c.prepareStatement("delete from users");
        pstmt.executeUpdate();

        c.close();
        pstmt.close();
    }

}
