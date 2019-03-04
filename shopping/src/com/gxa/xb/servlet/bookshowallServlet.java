package com.gxa.xb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.Impl.bookDaoImpl;
import com.gxa.xb.pojo.Book;

/**
 * Servlet implementation class bookshowallServlet
 */
public class bookshowallServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
		bookDaoImpl book = new bookDaoImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> books = book.findAllBooks();
		request.getSession().setAttribute("books", books);
		response.sendRedirect("./book_showall.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}

}
