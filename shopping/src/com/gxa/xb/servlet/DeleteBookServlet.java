package com.gxa.xb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.gxa.xb.Dao.Impl.bookDaoImpl;

/**
 * Servlet implementation class DeleteBookServlet
 */
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    bookDaoImpl dao = new bookDaoImpl();
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = new String(request.getParameter("bookName").getBytes("ISO8859-1"),"UTF-8");
		if(dao.findBooksByName(name)==null)
		{
			JOptionPane.showMessageDialog(null,"查无此书哦www","删除错误", JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("/booksshop/delete_books.jsp");
		}
		else
		{
			dao.deleteBooks(name);
			JOptionPane.showMessageDialog(null,"删除成功哦www","删除成功", JOptionPane.DEFAULT_OPTION);
			response.sendRedirect("/booksshop/delete_books.jsp");
		}// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
