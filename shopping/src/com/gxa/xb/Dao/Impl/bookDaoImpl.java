package com.gxa.xb.Dao.Impl;

import java.util.List;
import java.util.ArrayList;

import com.gxa.xb.mapping.bookMapping;
import com.gxa.xb.mapping.orderMapping;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.Order;
import com.gxa.xb.Dao.bookDao;
import com.gxa.xb.Util.JDBCUtil;

public class bookDaoImpl implements bookDao {

	@Override
	public List<Book> findAllBooks() {
		// TODO Auto-generated method stub
		String sql="select * from book";
		List<Book> booklist = new ArrayList<Book>();
		booklist=JDBCUtil.executeQuery(sql,new bookMapping());
		return booklist;
	}

	@Override
	public Book findBookByBookId(int bookId) {
		// TODO Auto-generated method stub
		String sql="select * from book where bookId = ?";
		return (Book)JDBCUtil.executeQueryOne(sql, new bookMapping(), bookId);
	}

	@Override
	public int addBooks(Book book) {
		// TODO Auto-generated method stub
		String sql="insert into book(bookName,bookAuthor,bookImage,bookPrice,bookPub,bookDesc,typeId) values(?,?,?,?,?,?,?)";
		return JDBCUtil.executeUpdate(sql, book.getBookName(), book.getBookAuthor(), book.getBookImage(), book.getBookPrice(), book.getBookPub(), book.getBookDesc(), book.getType().getTypeId());
	}

	@Override
	public int deleteBooks(String bookname) {
		// TODO Auto-generated method stub
		String sql="delete from book where bookName=?";
		return JDBCUtil.executeUpdate(sql, bookname);
	}

	@Override
	public int modifyBooks(Book book,String name) {
		// TODO Auto-generated method stub
		String sql="update book set bookName=?,bookAuthor=?,bookImage=?,bookPrice=?,bookPub=?,bookDesc=?,typeId=? where bookName=?";
		return JDBCUtil.executeUpdate(sql, book.getBookName(),book.getBookAuthor(),book.getBookImage(),book.getBookPrice(),book.getBookPub(),book.getBookDesc(),book.getType().getTypeId(),name);
	}

	@Override
	public List<Book> findBooksByBookType(String typeName) {
		// TODO Auto-generated method stub
		String sql="select * from book where typeId in"
				+ " (select typeId from type where typeName = ?)";
		List<Book> booklist = new ArrayList<Book>();
		booklist = JDBCUtil.executeQuery(sql, new bookMapping(), typeName);
		return booklist;
	}

	@Override
	public Book findBooksByName(String name){
		String sql = "select * from book where bookName = ?";
		return (Book)JDBCUtil.executeQueryOne(sql, new bookMapping(), name);
		
	}
	

}
