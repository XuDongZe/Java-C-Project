package com.gxa.xb.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Dao.Impl.userDaoImpl;
import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.Order;
import com.gxa.xb.Dao.userDao;

public class orderMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		Order order = new Order();
		userDao userdao = new userDaoImpl();
		try {
			order.setOrderId(rs.getInt("orderId"));
			order.setUser(userdao.findUserByUserId(rs.getInt("userId")));
			order.setOrderDate(rs.getString("orderDate"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}

}
