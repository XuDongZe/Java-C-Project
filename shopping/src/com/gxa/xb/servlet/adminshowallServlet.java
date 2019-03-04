package com.gxa.xb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.Impl.adminDaoImpl;
import com.gxa.xb.Dao.Impl.bookDaoImpl;
import com.gxa.xb.pojo.Admin;
import com.gxa.xb.pojo.Book;

public class adminshowallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	adminDaoImpl admin = new adminDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
  
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Admin> admins = admin.findAllAdmins();
		request.getSession().setAttribute("admins", admins);
		response.sendRedirect("./admin_showall.jsp");
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
