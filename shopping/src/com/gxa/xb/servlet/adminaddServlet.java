package com.gxa.xb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.gxa.xb.Dao.Impl.adminDaoImpl;
import com.gxa.xb.pojo.Admin;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.Type;

/**
 * Servlet implementation class adminaddServlet
 */
public class adminaddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    adminDaoImpl dao = new adminDaoImpl();   
    /**
     * @see HttpServlet#HttpServlet()
     */
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = new String(request.getParameter("adminname").getBytes("ISO8859-1"),"UTF-8");
		if(dao.findAdminByAdminName(name)!=null)
		{
			JOptionPane.showMessageDialog(null,"此人已存在哦www","添加错误", JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("/booksshop/adminadd.jsp");
		}
		else{
		String password = new String(request.getParameter("adminpassword").getBytes("ISO8859-1"),"UTF-8");
		Admin admin = new Admin(name,password);
		dao.addAdmins(admin);
		JOptionPane.showMessageDialog(null,"添加成功哦www","插入成功", JOptionPane.DEFAULT_OPTION);
		response.sendRedirect("/booksshop/adminadd.jsp");
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
