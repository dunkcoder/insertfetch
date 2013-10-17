package com.dunkcoder.insertfetch.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class JdbcUtil {

    public static Connection getConnection(String driver_class, String db_url, String db_userid, String db_password) {
        Connection conn = null;
        boolean blnContinue = true;
        try {
            Class.forName(driver_class);
        } catch (ClassNotFoundException cnfe) {
            println("Exception: JdbcUtil.getConnection driver_class not found");
            blnContinue = false;
        }
        if (blnContinue) {
            try {
                conn = DriverManager.getConnection(db_url, db_userid, db_password);
            } catch (SQLException sqle) {
                println("Exception: JdbcUtil.getConnection get connection failed");
            }
        }
        return conn;
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    public static long insertThenFetch(Connection conn, String insertSql) throws SQLException {
        long id = 0L;
        try {
            PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException sqle) {
            println("Exception insertThenFetch failed");
            throw sqle;
        }
        return id;
    }

    public static void free(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqle) {
                println("Exception: JdbcUtil.free rs");
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        println("Exception: JdbcUtil.free stmt");
                    }
                }
            }
        }
    }

    public static void free(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException sqle) {
                println("Exception: JdbcUtil.free conn");
            }
        }
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }
}
