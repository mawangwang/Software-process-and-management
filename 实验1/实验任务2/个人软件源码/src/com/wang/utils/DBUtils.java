package com.wang.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
															  //数据库名
	public static String db_url = "jdbc:mysql://localhost:3306/wang?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    public static String db_user = "root";   //用户名
    public static String db_password = "123456"; //密码
     
    public static Connection getConn () {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
            conn = DriverManager.getConnection(db_url, db_user, db_password);//获取连接对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
     
    /**
     * 关闭连接
     * @param state
     * @param conn
     */
    public static void close (Statement state, Connection conn) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
     
    /**
     * 关闭连接
     * @param rs
     * @param state
     * @param conn
     */
    public static void close (ResultSet rs, Statement state, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 
}
