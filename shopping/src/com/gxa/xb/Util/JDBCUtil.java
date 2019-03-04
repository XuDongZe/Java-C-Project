package com.gxa.xb.Util;
/**
 * 增删改查的工具包
 * JDBCBase
 * Details:
 * Author:WangLei
 * DateTime：2017年6月23日
 * Version：1.0
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public  class JDBCUtil {
	private static  final String DRIVE = "com.mysql.jdbc.Driver";
	
	private static  final String URL = "jdbc:mysql://localhost:3306/book?useUnicode=true&characterEncoding=utf8";
	
	private static  final String USER = "root";
	
	private static  final String PWD = "";
	
	//1.获取链接池
		/**
		 * 获取连接池
		 * @return 连接池
		 * Author:Wanglei
		 * DateTime:2017年6月23日
		 */
		public Connection getConnection(){
			
			Connection con = null;
			
			//1.加载驱动
			
				try {
					Class.forName(DRIVE);
					//2.获取链接
					try {
						con = DriverManager.getConnection(URL, USER, PWD);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			return con;
			
		}
		
	//2.释放资源
		/**
		 * 
		 * @param rs 结果集
		 * @param pstm 预编译的 SQL 语句的对象
		 * @param con 连接池
		 * Author:Wanglei
		 * DateTime:2017年6月23日
		 */
		public  void closeAll(ResultSet rs , PreparedStatement pstm , Connection con) {
		try {	
			if(rs != null){
				
				rs.close();
				
				rs = null ;
			}
			if(pstm != null){
				pstm.close();
				pstm = null;
			}
			
			if(con != null){
				con.close();
				con = null;
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 
	 * @param sql SQL语句
	 * @param object 可变参数，个数不定
	 * @return 受影响行数
	 * Author:Wanglei
	 * DateTime:2017年6月23日
	 */
	public static int executeUpdate(String sql , Object... objs ){
		int count = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		JDBCUtil base  = new JDBCUtil();	
	
		try {
			
			con = base.getConnection();//获取连接池
			//预编译SQL语句
			pstm = con.prepareStatement(sql);
			//获取条件 添加条件参数
			if(objs != null){
				for(int i = 0 ; i < objs.length ; i++ ){
					
					pstm.setObject(i + 1 , objs[i]);
				}
				
			}
			
			System.out.println(printSQL(sql, objs));
			count = pstm.executeUpdate();//执行预编译成功的SQL语句
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			base.closeAll(null, pstm, con);
		}
		
		return count;
	}
	/**
	 * 
	 * @param sql 要查询的SQL语句
	 * @param make 映射关系
	 * @param objs 条件
	 * @return 集合
	 * Author:Wanglei
	 * DateTime:2017年6月26日
	 */
	public static List executeQuery(String sql ,  RowToObject make ,Object... objs){
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstm = null;
		JDBCUtil base  = new JDBCUtil();	
		
		List list = new ArrayList();

		try {
			
			con = base.getConnection();//获取连接池
			//预编译SQL语句
			pstm = con.prepareStatement(sql);
			//获取条件 添加条件参数
			if(objs != null){
				for(int i = 0 ; i < objs.length ; i++ ){
					
					pstm.setObject(i + 1 , objs[i]);
				}
				
			}
			
			System.out.println(printSQL(sql, objs));
			rs = pstm.executeQuery();//执行查询
			while(rs.next()){
				Object obj = make.makeRowToObject(rs);
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			base.closeAll(rs, pstm, con);
		}
		
		return list;
	}
	/**
	 * 返回一条记录
	 * @param sql
	 * @param make
	 * @param objs
	 * @return
	 * Author:Wanglei
	 * DateTime:2017年6月26日
	 */
	public static Object executeQueryOne(String sql ,  RowToObject make ,Object... objs){
		List list = new ArrayList();
		list = executeQuery(sql, make, objs);
		if(list == null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
		
	}
	/**
	 * 查询返回一个指定的值
	 * @param sql
	 * @param objs
	 * @return 返回一个值
	 * Author:Wanglei
	 * DateTime:2017年6月26日
	 */
	public static Object executeQueryValue(String sql ,Object... objs){
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstm = null;
		JDBCUtil base  = new JDBCUtil();	
		Object obj = null;
		

		try {
			
			con = base.getConnection();//获取连接池
			//预编译SQL语句
			pstm = con.prepareStatement(sql);
			//获取条件 添加条件参数
			if(objs != null){
				for(int i = 0 ; i < objs.length ; i++ ){
					
					pstm.setObject(i + 1 , objs[i]);
				}
				
			}
			
			System.out.println(printSQL(sql, objs));
			rs = pstm.executeQuery();//执行查询
			while(rs.next()){
					obj = rs.getObject(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			base.closeAll(rs, pstm, con);
		}
		
		return obj;
	}
	/**
	 * 打印带参数的SQL语句
	 * @param sql 要格式化的SQL
	 * @param objs 参数
	 * @return 格式化后的SQL
	 * Author:Wanglei
	 * DateTime:2017年6月26日
	 */
	
	public static String printSQL(String sql , Object... objs){
		//1.如果没有参数,说明不是动态SQL----PLSQL
		if(null == objs){
			return sql;
		}
		
		//如果有参数,则是动态的SQL
		StringBuffer returnSql = new StringBuffer();
		//通过？分割字符串
		String[] subSQL = sql.split("\\?");
		for(int i = 0 ; i<objs.length; i++){
			
			//将SQL拼接起来
			
			returnSql.append(subSQL[i]).append("'").append(objs[i]).append("'");
			
		}
		if(subSQL.length > objs.length){
			returnSql.append(subSQL[subSQL.length-1]);
			
		}
		return returnSql.toString();//将StringBuffer 装换成string
	}
	
	
}
