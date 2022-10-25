package com.db.dao;

import com.db.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            return user;
        }
    };

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) throws SQLException {
        jdbcTemplate.update("INSERT INTO users values (?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public void deleteAll(){
        jdbcTemplate.update("delete from users");
    }

    public int getCount(){
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public User findById(String id){
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
    public List<User> getAll(){
        String sql = "select * from users order by id";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
