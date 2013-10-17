package com.dunkcoder.insertfetch.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dunkcoder.insertfetch.domain.User;
import com.dunkcoder.insertfetch.util.JdbcUtil;

public class UserDaoImplTest {

    private static IUserDao dao;
    private static ApplicationContext ctx;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ctx = new ClassPathXmlApplicationContext("application-beans.xml");
        dao = (IUserDao) ctx.getBean("userDao");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testInsertUser() {
        User u = new User();
        dao.insertUser(u);
        u.setUsername("zhangsan");
        Assert.assertTrue("new user id: " + u.getId(), u.getId() > 0);
        JdbcUtil.println(String.format("new user: %sL", u.getId()));
    }
}