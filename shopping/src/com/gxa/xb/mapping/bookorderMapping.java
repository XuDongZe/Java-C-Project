package com.gxa.xb.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Dao.Impl.bookDaoImpl;
import com.gxa.xb.Dao.Impl.orderDaoImpl;
import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.Bookorder;
import com.gxa.xb.Dao.bookDao;
import com.gxa.xb.Dao.orderDao;

public class bookorderMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		Bookorder bookorder = new Bookorder();
		orderDao orderdao = new orderDaoImpl();
		bookDao bookdao = new bookDaoImpl();
		try {
			bookorder.setBookorderId(rs.getInt("bookorderId"));
			bookorder.setBook(bookdao.findBookByBookId(rs.getInt("bookId")));
			bookorder.setOrder(orderdao.findOrderByOrderId(rs.getInt("orderId")));
			bookorder.setBookNum(rs.getInt("bookNum"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookorder;
		
	}

}
