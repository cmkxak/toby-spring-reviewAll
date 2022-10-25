package com.db.dao;

import com.db.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao userDao;
    private User user1;
    private User user2;

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(UserDaoFactory.class);

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userDao = ac.getBean("awsUserDao", UserDao.class);
        user1 = new User("16", "홍길동", "1234");
        user2 = new User("17", "홍길수", "12346");
    }

    @AfterEach
    void afterSetUp(){
        userDao.deleteAll();
    }

    @Test
    @DisplayName("add 테스트")
    void add_Test() throws SQLException, ClassNotFoundException {
        userDao.add(user1);
        userDao.add(user2);

        User findUser1 = userDao.findById(user1.getId());
        User findUser2 = userDao.findById(user2.getId());

        assertEquals(user1.getName(), findUser1.getName());
        assertEquals(user2.getName(), findUser2.getName());
    }

    @Test
    @DisplayName("delete 테스트")
    void delete_test() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(0).isEqualTo(userDao.getCount());
    }

    @Test
    @DisplayName("getCount 테스트")
    void getCount_test() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        userDao.add(user1);
        assertThat(1).isEqualTo(userDao.getCount());

        userDao.add(user2);
        assertThat(2).isEqualTo(userDao.getCount());
    }

    @Test
    @DisplayName("getAll 테스트")
    void getAll_test() throws SQLException {
        userDao.add(user1);
        userDao.add(user2);

        userDao.getAll();
        assertThat(2).isEqualTo(userDao.getAll().size());
    }

}