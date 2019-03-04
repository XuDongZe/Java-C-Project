package com.gxa.xb.Dao.Impl;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.pojo.Bookorder;
import com.gxa.xb.pojo.User;
import com.gxa.xb.Dao.bookOrderDao;
import com.gxa.xb.Util.JDBCUtil;
import com.gxa.xb.mapping.bookorderMapping;
import com.gxa.xb.mapping.userMapping;

public class bookOrderDaoImpl implements bookOrderDao {

	@Override
	public List<Bookorder> findBookorderByorderId(int orderId) {
		// TODO Auto-generated method stub
		String sql="select * from bookorder where orderId = ?";
		List<Bookorder> bookorderlist = new ArrayList<Bookorder>();
		bookorderlist = JDBCUtil.executeQuery(sql,new bookorderMapping(), orderId);
		return bookorderlist;
	}
	
	@Override
	public List<Bookorder> findBookorderBybookorderId(int bookorderId) {
		// TODO Auto-generated method stub
		String sql="select * from bookorder where bookorderId = ?";
		List<Bookorder> bookorderlist = new ArrayList<Bookorder>();
		bookorderlist = JDBCUtil.executeQuery(sql,new bookorderMapping(), bookorderId);
		return bookorderlist;
	}
	
	@Override
	public List<Bookorder> findBookorderByuserId(int userId) {
		// TODO Auto-generated method stub
		String sql="select * from bookorder where orderId in "
				+ "(select orderId from `order` where userId = ?)";
		List<Bookorder> bookorderlist = new ArrayList<Bookorder>();
		bookorderlist = JDBCUtil.executeQuery(sql,new bookorderMapping(), userId);
		return bookorderlist;
	}
	
	@Override
	public int addBookorder(Bookorder bookorder) {
		// TODO Auto-generated method stub
		String sql="insert into bookorder(bookorderId,orderId,bookId,bookNum) values(?,?,?,?)";
		return JDBCUtil.executeUpdate(
				sql, bookorder.getBookorderId(),bookorder.getOrder().getOrderId(), bookorder.getBook().getBookId(),bookorder.getBookNum());
	}
	

	@Override
	public int deleteBookorder(Bookorder bookorder) {
		// TODO Auto-generated method stub
		String sql="delete all from bookorder where bookorderId=?";
		return JDBCUtil.executeUpdate(sql,bookorder.getBookorderId());
	}

}
