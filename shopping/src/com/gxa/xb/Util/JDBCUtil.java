package com.gxa.xb.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC工具包
 * @author root
 *
 */
public class JDBCUtil {
	private static final String DRIVE = "com.mysql.jdbc.Driver";
	private static final String ROOT = "root";
	private static final String PWD  = "123456";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/shopping";
	
	/**
	 * 获取连接池
	 * @return 连接池
	 * @throws SQLException 
	 */
	public Connection getConnection() throws SQLException{
			Connection connection = null ;
			try {
				Class.forName(DRIVE);
				connection = DriverManager.getConnection(URL, ROOT, PWD);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return connection;
		
	}
	/**
	 * 关闭
	 * @param con
	 * @param pstm
	 * @param rs
	 * @throws SQLException
	 */
	public void closeAll(Connection con ,PreparedStatement pstm , ResultSet rs) { 
		if(rs!=null){
		
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
		if(pstm!=null){
		
			try {
				pstm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstm = null;
		}
		
		if(con!=null){
			
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = null;
		}
	}
	/**
	 * 执行增删改
	 * @param sql SQL语句
	 * @param objects 可变参数
	 * @return
	 */
	public static int updateQuery(String sql , Object... objects ){
		Connection con = null;
		PreparedStatement pstm = null ; 
		int count = 0;
		
		try {
			con = new JDBCUtil().getConnection();
			pstm = con.prepareStatement(sql);
			//获取条件参数
			if(objects != null){
				for(int i = 0 ; i<objects.length; i++){
					pstm.setObject(i+1, objects[i]);
				}
			}
			count = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			new JDBCUtil().closeAll(con, pstm, null);
		}
		
		return count;
	}
	
	/**
	 * 查询返回多条记录
	 * @param <T>
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static List selectAllQuery(String sql,RowToObject rowToObject,Object...objects){
		Connection con = null;
		PreparedStatement pstm = null ; 
		ResultSet rs = null;//结果集
		List list = new ArrayList();
		try {
			//1.//获取连接池
			con = new JDBCUtil().getConnection();
			//2.预处理SQL语句
			pstm = con.prepareStatement(sql);
			//3.拼接参数
			if(objects != null){
				for(int i = 0 ; i<objects.length; i++){
					pstm.setObject(i+1, objects[i]);
				}
			}
			
			//执行sql 获取返回结果集
			rs = pstm.executeQuery();
			
		//new JDBCUtil().closeAll(con, pstm, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				Object object = rowToObject.makeRowToObject(rs);
				list.add(object);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			new JDBCUtil().closeAll(con, pstm, null);
		}
	
		return list;
	}
	/**
	 * 返回单条记录
	 * @param sql
	 * @param rowToObject
	 * @param objects
	 * @return
	 */
	public static Object selectOneQuery(String sql,RowToObject rowToObject,Object...objects){
		
		List list = selectAllQuery(sql, rowToObject, objects);
		if(list != null){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 返回单个值
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static Object selectValueQuery(String sql,Object...objects){
		Connection con = null;
		PreparedStatement pstm = null ; 
		ResultSet rs = null;//结果集
		Object obj = null;
		try {
			//1.//获取连接池
			con = new JDBCUtil().getConnection();
			//2.预处理SQL语句
			pstm = con.prepareStatement(sql);
			//3.拼接参数
			if(objects != null){
				for(int i = 0 ; i<objects.length; i++){
					pstm.setObject(i+1, objects[i]);
				}
			}
			
			//执行sql 获取返回结果集
			rs = pstm.executeQuery();
			while(rs.next()){
				obj = rs.getObject(1);
			}
		//new JDBCUtil().closeAll(con, pstm, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			new JDBCUtil().closeAll(con, pstm, null);
		}
		
		return obj;
	}
} 
