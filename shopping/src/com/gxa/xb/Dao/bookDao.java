package com.gxa.xb.Dao;

import java.util.List;

import com.gxa.xb.pojo.Book;


public interface bookDao {
	/**
	 * 查找所有书籍
	 * @return
	 */
	public List<Book> findAllBooks();
	
	/**
	 * 根据ID查找书籍
	 */
	public Book findBookByBookId(int bookId);
	
	/**
	 * 增加书籍信息
	 * @param book
	 * @return
	 */
	public int addBooks(Book book);
	
	/**
	 * 删除书籍信息
	 * @param bookId
	 * @return
	 */
	public int deleteBooks(String bookname);
	
	/**
	 * 修改图书信息
	 * @param book
	 * @param bookId
	 * @return
	 */
	public int modifyBooks(Book book,String name);
	
	/**
	 * 根据类型名称查找书籍
	 * @param typeName
	 * @return
	 */
	public List<Book> findBooksByBookType(String typeName);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Book findBooksByName(String bookname);
	

}
