package com.gxa.xb.servlet;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.bookDao;
import com.gxa.xb.Dao.typeDao;
import com.gxa.xb.Dao.Impl.bookDaoImpl;
 
import com.gxa.xb.Dao.Impl.typeDaoImpl;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.Type;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.sendRedirect("/booksshop/findAllBooks");
	    List<Book> booklist  = new ArrayList<Book>();
	    bookDao bookdao = new bookDaoImpl();
		booklist = (ArrayList<Book>) bookdao.findAllBooks();
		request.getSession().setAttribute("booklist", booklist);
		
		ArrayList<Type> typelist  = new ArrayList<Type>();
	    typeDao typedao = new typeDaoImpl();
	    typelist = (ArrayList<Type>) typedao.findAllTypes();
		request.getSession().setAttribute("typelist", typelist);
		
		response.sendRedirect("./loginsuccessindex.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
