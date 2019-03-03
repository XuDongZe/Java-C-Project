package com.gxa.xb.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.pojo.*;
import com.gxa.xb.Util.JDBCUtil;
import com.gxa.xb.pojo.mapping.UserInfoMapping;


public class JDBCTest {
	public static void main(String[] args) throws SQLException {
	/*	Connection connection = null ;
		//1.创建连接池
			//1.打开连接
			try{
				 connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shopping", "root", "123456");
				 //2.加载驱动
				Class.forName("com.mysql.jdbc.Driver");
			}catch(Exception e){
				e.printStackTrace();
			}
			if(connection==null){
				System.out.println("连接失败");
			}else{
				System.out.println("连接成功！");
				
			}
		
			
		//2.写SQL语句
			String sql = "insert into userInfo(userName,userPwd) values('zhangsan','789456')";
		//3.执行sql
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		//4.关闭连接
			statement.close();
			connection.close();*/
			
			
			/*String sql1 = "delete from userInfo";
			
			System.out.println(JDBCUtil.updateQuery(sql1));*/
		
			String sql2 = "select * from userinfo";
			String sql3 = "select count(userId) from userinfo";
			List<UserInfo> list = new ArrayList<UserInfo>();
			list = JDBCUtil.selectAllQuery(sql2, new UserInfoMapping());
				for (UserInfo userInfo : list) {
					System.out.println(userInfo.getUserPwd());
				}
		/*	while(rs.next()){

				System.out.println(rs.getString("userName"));
			}*/
				
			System.out.println(JDBCUtil.selectValueQuery(sql3));
	}
}
