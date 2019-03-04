package com.gxa.xb.pojo;
/*
 * 书籍实体类
 */
public class Book {
	private int bookId;
	
	private String bookName;   //书名
	
	private String bookAuthor;  //作者
	
	private String bookImage;   //图片
	
	private int bookPrice;     //价格
	
	private String bookPub;    //出版社
	
	private String bookDesc;   //简介
	
	private Type type;         //类型


	public int getBookId() {
		return bookId;
	}


	public void setBookId(int bookId) {
		this.bookId = bookId;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getBookAuthor() {
		return bookAuthor;
	}


	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}


	public String getBookImage() {
		return bookImage;
	}


	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}


	public int getBookPrice() {
		return bookPrice;
	}


	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}


	public String getBookPub() {
		return bookPub;
	}


	public void setBookPub(String bookPub) {
		this.bookPub = bookPub;
	}


	public String getBookDesc() {
		return bookDesc;
	}


	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public Book() {
		super();
	}


	public Book(int bookId, String bookName, String bookAuthor,
			String bookImage, int bookPrice, String bookPub, String bookDesc,
			Type type) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookImage = bookImage;
		this.bookPrice = bookPrice;
		this.bookPub = bookPub;
		this.bookDesc = bookDesc;
		this.type = type;
	}
	
	public Book(String bookName,Type type){
		super();
		this.bookName = bookName;
		this.type = type;
	}
	
	public Book(int bookId, String bookName,Type type){
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.type = type;
	}
	
	public Book(String bookName, String bookAuthor,
			String bookImage, int bookPrice, String bookPub, String bookDesc,
			Type type) {
		super();
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookImage = bookImage;
		this.bookPrice = bookPrice;
		this.bookPub = bookPub;
		this.bookDesc = bookDesc;
		this.type = type;
	}
}
