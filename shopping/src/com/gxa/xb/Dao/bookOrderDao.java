package com.gxa.xb.Dao;

import java.util.List;

import com.gxa.xb.pojo.Bookorder;



public interface bookOrderDao {
	/**
	 * 根据订单号查询图书订单
	 * @param orderId
	 * @return
	 */
	public List<Bookorder> findBookorderByorderId(int orderId);
	
	/**
	 * 根据图书订单号查询图书订单
	 * @param bookorderId
	 * @return
	 */
	public List<Bookorder> findBookorderBybookorderId(int bookorderId);
	
	/**
	 * 根据用户Id查询图书订单
	 * @param userId
	 * @return
	 */
	public List<Bookorder> findBookorderByuserId(int userId);

	/**
	 * 添加图书订单
	 * @param bookorder
	 * @return
	 */
	public int addBookorder(Bookorder bookorder);
	
	/**
	 * 删除图书订单
	 * @param bookorder
	 * @return
	 */
	public int deleteBookorder(Bookorder bookorder);
}
