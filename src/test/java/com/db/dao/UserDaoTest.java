package com.db.dao;

import com.db.connection.AwsConnectionMaker;
import com.db.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao userDao;
    private User user1;
    private User user2;

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(UserDaoFactory.class);
    @BeforeEach
    void setUp(){
        userDao = ac.getBean("awsUserDao", UserDao.class);
        user1 = new User("3", "홍길동", "1234");
        user2 = new User("4", "홍길수", "12346");
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

}