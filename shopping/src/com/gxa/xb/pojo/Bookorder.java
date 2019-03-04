package com.gxa.xb.pojo;
/*
 * 图书订单实体类
 */
public class Bookorder {
	private int bookorderId;
	
	private Order order;  //该图书订单对应的订单号
	
	private Book book;   //包含的图书
	
	private int bookNum;  //订单中图书数量


	public int getBookorderId() {
		return bookorderId;
	}


	public void setBookorderId(int bookorderId) {
		this.bookorderId = bookorderId;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}


	public int getBookNum() {
		return bookNum;
	}


	public void setBookNum(int bookNum) {
		this.bookNum = bookNum;
	}


	public Bookorder() {
		super();
	}


	public Bookorder(int bookorderId, Order order, Book book, int bookNum) {
		super();
		this.bookorderId = bookorderId;
		this.order = order;
		this.book = book;
		this.bookNum = bookNum;
	}
	
	public Bookorder(Order order, Book book, int bookNum) {
		super();
		this.order = order;
		this.book = book;
		this.bookNum = bookNum;
	}
	
}
