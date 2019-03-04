package com.gxa.xb.pojo;

import java.util.List;

public class CartItem {
	private Book book;
	private int bookNum;
	private double sumCount;
	private String date;
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
	public double getSumCount() {
		return sumCount;
	}
	public void setSumCount(double sumCount) {
		this.sumCount = sumCount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public CartItem(){}
	
	public CartItem(Book book, int bookNum) {
		this.book = book;
		this.bookNum = bookNum;
		this.date = String.valueOf(new java.util.Date().getTime());
		this.sumCount = book.getBookPrice() * 1.0 * bookNum;
	}
	
}
