package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnManager {
	private static final String db_name = "jdbc:mysql://localhost:3306/db_quickCalculation?useUnicode=true&characterEncoding=UTF-8";
	private static final String login_name = "root";
	private static final String login_key = "";
	
	private Connection conn;
	
	public ConnManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("数据库驱动加载成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//getConnection();
	}
	
	public Connection getConnection(){
		try {
			conn = DriverManager.getConnection(db_name, login_name, login_key);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭连接 
	public void close(Connection conn, PreparedStatement preStmt, ResultSet rs) {
		try {
			if( rs != null )	rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if( preStmt != null ) preStmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if( conn != null ) conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
