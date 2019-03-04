package com.gxa.xb.Dao;

import java.util.List;

import com.gxa.xb.pojo.Order;

public interface orderDao {
	/**
	 * 查询所有订单
	 * @return
	 */
	public List<Order> findAllOrders();
	
	/**
	 * 根据用户Id查询订单
	 * @param userId
	 * @return
	 */
	public List<Order> findOrderByUser(int userId);
	
	/**
	 * 根据订单Id查询订单
	 * @param orderId
	 * @return
	 */
	public Order findOrderByOrderId(int orderId);
	
	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	public int addOrders(Order order);
	
	/**
	 * 删除订单
	 * @param order
	 * @return
	 */
	public int deleteOrders(int orderId);

	/**
	 * 获取最后一个订单id
	 * @return
	 */
	public int getLastOrderId() ;
}
