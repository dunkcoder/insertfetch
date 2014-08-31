package com.dunkcoder.insertfetch.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class JdbcUtilTest {

    private static Connection conn = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String drive_class = "com.mysql.jdbc.Driver";
        String db_url = "jdbc:mysql://localhost:3306/test";
        String db_userid = "root";
        String db_password = "pa55w0rd";
        conn = JdbcUtil.getConnection(drive_class, db_url, db_userid, db_password);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        JdbcUtil.free(conn);
    }

    @Test
    public void testInsertThenFetch() {
        String sql = "insert into t_user (username) values ('san.zhang')";
        try {
            long id = JdbcUtil.insertThenFetch(conn, sql);
            JdbcUtil.println(String.format("AUTO_INCREMENT id is %sL", id));
            Assert.assertTrue("Insert then fetch succeed", id > 0);
        } catch (SQLException sqle) {
            JdbcUtil.println("Exception main insertThenFetch failed");
        } finally {
            // nothing
        }
    }
}