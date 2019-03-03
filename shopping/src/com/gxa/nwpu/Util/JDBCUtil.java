package com.gxa.nwpu.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * 	JDBC工具包
 * */
public class JDBCUtil {
	private static final String USER = "root";
	private static final String PWD = "xdz1120jc87";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/shopping";
	/*
	 * 	获取连接池
	 * 	返回连接池
	 * */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PWD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeAll(Connection conn, PreparedStatement preStmt, ResultSet rs) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(preStmt != null) {
			try {
				preStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 	增删改
	 * */
	public static void updateQuery(String sql, Object... objects) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		conn = getConnection();
		int count = 0;
		try {
			preStmt = conn.prepareStatement(sql);
			//获取条件
			if( objects != null ) {
				for(int i=0; i<objects.length; i++) {
					preStmt.setObject(i+1, objects[i]);
				}
				count = preStmt.executeUpdate();
			}
			closeAll(conn, preStmt, null);
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	
	/*
	 * 	查询返回多条语句
	 * */
	public static List<Object> selectAllQuery(String sql, Object...objects) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<Object>();
		conn = JDBCUtil.getConnection();
		//预处理sql语句
		try {
			preStmt = conn.prepareStatement(sql);
			if(objects != null) {
				for(int i=0; i<objects.length; i++) {
					preStmt.setObject(i+1, objects[i]);
				}
			}
			//返回结果集, 已经预处理了参数, 不需要传参数
			rs = preStmt.executeQuery();
			//转换结果集到java对象集合
			while(rs.next()) {
				
			}
			JDBCUtil.closeAll(conn, preStmt, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(rs);
		return null;
 	}
	
}
