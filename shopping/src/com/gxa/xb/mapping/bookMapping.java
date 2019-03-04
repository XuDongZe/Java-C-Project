package com.gxa.xb.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Dao.Impl.typeDaoImpl;
import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.Dao.typeDao;

public class bookMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		Book book = new Book();
		typeDao typedao = new typeDaoImpl();
		try {
			book.setBookId(rs.getInt("bookId"));
			book.setBookName(rs.getString("bookName"));
			book.setBookAuthor(rs.getString("bookAuthor"));
			book.setBookImage(rs.getString("bookImage"));
			book.setBookPrice(rs.getInt("bookPrice"));
			book.setBookPub(rs.getString("bookPub"));
			book.setBookDesc(rs.getString("bookDesc"));
			book.setType(typedao.findTypeByTypeId(rs.getInt("typeId")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}

}
