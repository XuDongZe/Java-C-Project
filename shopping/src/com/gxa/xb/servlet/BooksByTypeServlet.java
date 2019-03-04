package com.gxa.xb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.bookDao;
import com.gxa.xb.Dao.Impl.bookDaoImpl;
import com.gxa.xb.pojo.Book;

/**
 * Servlet implementation class BooksByTypeServlet
 */
public class BooksByTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksByTypeServlet() {
     
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String typeName = request.getParameter("typeName");
		bookDao bookdao = new bookDaoImpl();
		List<Book> booklist = new ArrayList<Book>();
		
		booklist = bookdao.findBooksByBookType(typeName);
		
		if(booklist != null) {
			request.getSession().setAttribute("typeBooks", booklist);
			response.sendRedirect("./booklist.jsp");
		}else {
			System.out.println("得到的booklist为空");
			//response.sendRedirect("/booksshop/index.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
