package com.gxa.xb.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.pojo.*;
import com.gxa.xb.Dao.*;
import com.gxa.xb.Util.JDBCUtil;
import com.gxa.xb.mapping.*;

public class orderDaoImpl implements orderDao {
	@Override
	public List<Order> findAllOrders() {
		// TODO Auto-generated method stub
		String sql = "select * from `order`";
		List<Order> orderlist = new ArrayList<Order>();
		orderlist = JDBCUtil.executeQuery(sql, new orderMapping());
		return orderlist;
	}

	@Override
	public List<Order> findOrderByUser(int userId) {
		// TODO Auto-generated method stub
		String sql ="select * from `order` where userId = ?";	
		List<Order> orderlist = new ArrayList<Order>();
		orderlist = JDBCUtil.executeQuery(sql, new orderMapping(), userId);
		return orderlist;
	}

	@Override
	public Order findOrderByOrderId(int orderId) {
		// TODO Auto-generated method stub
		String sql ="select * from `order` where orderId = ?";	
		return (Order) JDBCUtil.executeQueryOne(sql, new orderMapping(), orderId);
	}

	@Override
	public int addOrders(Order order) {
		// TODO Auto-generated method stub
		String sql = "insert into `order`(userId,orderDate) values(?,?)";
		return JDBCUtil.executeUpdate(sql,order.getUser().getUserId(), order.getOrderDate());
	}

	@Override
	public int deleteOrders(int orderId) {
		// TODO Auto-generated method stub
		String sql = "delete from `order` where orderId = ?";
		JDBCUtil.executeUpdate(sql, orderId);
		return 0;
	}
	
	@Override
	public int getLastOrderId() {
		// TODO Auto-generated method stub
		String sql = "select * from `order` order by orderId desc limit 0, 1";
		Order order = (Order)JDBCUtil.executeQueryOne(sql, new orderMapping() );
		if(order == null) {
			return 1;
		}else {
			return order.getOrderId();
		}
	}

}
