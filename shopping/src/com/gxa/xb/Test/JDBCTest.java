package com.gxa.xb.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.Dao.bookDao;
import com.gxa.xb.Dao.bookOrderDao;
import com.gxa.xb.Dao.orderDao;
import com.gxa.xb.Dao.typeDao;
import com.gxa.xb.Dao.userDao;
import com.gxa.xb.Dao.Impl.bookDaoImpl;
import com.gxa.xb.Dao.Impl.bookOrderDaoImpl;
import com.gxa.xb.Dao.Impl.orderDaoImpl;
import com.gxa.xb.Dao.Impl.typeDaoImpl;
import com.gxa.xb.Dao.Impl.userDaoImpl;
import com.gxa.xb.Util.JDBCUtil;
import com.gxa.xb.mapping.userMapping;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.Bookorder;
import com.gxa.xb.pojo.Order;
import com.gxa.xb.pojo.Type;
import com.gxa.xb.pojo.User;


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
			
			
			/*String sql1 = "delete from user";
			
			System.out.println(JDBCUtil.executeUpdate(sql1));*/
			/*List<User> list = new ArrayList<User>();
			String sql2 = "select * from user";
			list = JDBCUtil.executeQuery(sql2,new userMapping());
			for (User user : list) {
				System.out.println(user.getUserName());
			}*/
			
			/*String sql3 = "select count(userId) from user";
			List<User> list = new ArrayList<User>();
			list = JDBCUtil.executeQuery(sql2, new userMapping());
			for (User user : list) {
				System.out.println(user.getUesrPwd());
			}*/
		/*	while(rs.next()){

				System.out.println(rs.getString("userName"));
			}*/
				
			//System.out.println(JDBCUtil.executeQueryValue(sql3));
			
			/*userDao userdao = new userDaoImpl();
			//System.out.println(userdao.findUserByUserId(3).getUserName());
			/*List<User> list = new ArrayList<User>();
			list = userdao.findAllUser();
			for(User user : list)
				System.out.println(user.getUserName());*/
			/*String sql="select * from user where userName=?";
			User user=(User) JDBCUtil.executeQueryOne(sql, new userMapping(), "小红");
			System.out.println(userdao.userLogin(user).getUserPwd());*/
			//User user  = new User(7,"小","123456");
			//System.out.println(userdao.userReg(user));
			/*String sql4 = "insert into user (userId,userName,userPwd,userRealName) values(?,?,?,?)";
			JDBCUtil.executeUpdate(sql4,"5","小红","123456","红");*/
			
		/*	String selectSql = "select * from book where bookId = ?";
			String insertSql = "insert into book(bookId, bookName, typeId) value(?, ?, ?)";
			
			
			JDBCUtil.executeUpdate(insertSql, 1, "中文", 1);*/
		
			//User user = new User(3,"小张","123456","张","成都","1774666666","123@qq.com");
			//userDao userdao = new userDaoImpl();
			//System.out.println(userdao.userReg(user));
			//System.out.println(userdao.modifyUser(user));
			//User user = userdao.findUserByUserId(3);
			//System.out.println(userdao.findUserByUserId(3).getUserAddress());
			//User user = new User("小红","123456");
			//userdao.userReg(user);
			/*List<User> userlist = new ArrayList<User>();
			userlist=userdao.findAllUser();
			for (User user : userlist) {
				System.out.println(user.getUserName());
			}*/
			//System.out.println(userdao.userLogin(user).getUserName());
			//typeDao typedao = new typeDaoImpl();
			//System.out.println(typedao.addType("科幻作品"));
			//System.out.println(typedao.findTypeByTypeId(2).getTypeName());
			/*List<Type> typelist = new ArrayList<Type>();
			typelist=typedao.findAllTypes();
			for (Type type : typelist) {
				System.out.println(type.getTypeName());
			}*/
			//typedao.deleteType(2);
			//bookDao bookdao = new bookDaoImpl();
			//Type type = typedao.findTypeByTypeId(1);
			//Book book = new Book(2,"百年孤独","书店","image.img",100,"成都出版社","这时一本书",type);
			//System.out.println(bookdao.addBooks(book));
			/*List<Book> booklist = new ArrayList<Book>();
			booklist = bookdao.findAllBooks();
			for (Book book : booklist) {
				System.out.println(book.getBookName());
			}*/
			//bookdao.deleteBooks(1);
			//bookdao.modifyBooks(book);
			//bookdao.deleteBooks(3);
			/*orderDao orderdao = new orderDaoImpl();
			userDao userdao = new userDaoImpl();
			User user = userdao.findUserByUserId(1);
			Order order = new Order(user,"20180703");*/
			//orderdao.addOrders(order);
			//System.out.println(orderdao.findOrderByOrderId(1).getOrderDate());
			/*List<Order> orderlist = new ArrayList<Order>();
			orderlist = orderdao.findOrderByUser(1);
			for (Order order2 : orderlist) {
				System.out.println(order2.getOrderDate());
			}*/
			//orderdao.deleteOrders(1);
	}
}
