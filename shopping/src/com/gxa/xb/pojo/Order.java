package com.gxa.xb.pojo;
/*
 * 订单实体类
 */
public class Order {
	private int orderId;
	
	private User user;// 创建该订单的用户_外键
	
	private String orderDate; //订单时间


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public Order() {
		super();
	}


	public Order(int orderId, User user, String orderDate) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.orderDate = orderDate;
	}
	
	public Order(User user,String orderDate)
	{
		super();
		this.user = user;
		this.orderDate=orderDate;
	}
	
}
