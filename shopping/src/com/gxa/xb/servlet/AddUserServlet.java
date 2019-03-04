package com.gxa.xb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.gxa.xb.Dao.Impl.bookDaoImpl;
import com.gxa.xb.Dao.Impl.typeDaoImpl;
import com.gxa.xb.Dao.Impl.userDaoImpl;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.Type;
import com.gxa.xb.pojo.User;

/**
 * Servlet implementation class AddUserServlet
 */
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	userDaoImpl dao = new userDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
     *

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = new String(request.getParameter("userName").getBytes("ISO8859-1"),"UTF-8");
		if(dao.findUserByUserName(name)!=null)
		{
			JOptionPane.showMessageDialog(null,"此人已存在哦www","添加错误", JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("/booksshop/add_users.jsp");
		}
		else{
		String password = new String(request.getParameter("userpass").getBytes("ISO8859-1"),"UTF-8");
		String realname = new String(request.getParameter("userRealName").getBytes("ISO8859-1"),"UTF-8");
		String address = new String(request.getParameter("useradress").getBytes("ISO8859-1"),"UTF-8");
		String tel = new String(request.getParameter("usertel").getBytes("ISO8859-1"),"UTF-8");
		String email = new String(request.getParameter("useremail").getBytes("ISO8859-1"),"UTF-8");

		User user  = new User(name,password,realname,address,tel,email);
		dao.userReg(user);
		JOptionPane.showMessageDialog(null,"添加成功哦www","添加成功", JOptionPane.DEFAULT_OPTION);
		response.sendRedirect("/booksshop/add_users.jsp");
		}
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
