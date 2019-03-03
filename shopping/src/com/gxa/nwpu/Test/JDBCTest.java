package com.gxa.nwpu.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import com.gxa.nwpu.DBBean.mapping.UserInfoMapping;
import com.gxa.nwpu.Util.JDBCUtil;

/*
 * 测试JDBC连接
 * */
public class JDBCTest {
	
	public static void main(String args[]) {
		Connection connection = JDBCUtil.getConnection();
		
		String sql = "insert into userInfo(userName, userPwd)";
		
		JDBCUtil.updateQuery(sql, "xudongze", "123456");
		
	}
	//1. 创建连接池
		
	//3. 执行mySQL
	//4. 关闭连接
	
}
