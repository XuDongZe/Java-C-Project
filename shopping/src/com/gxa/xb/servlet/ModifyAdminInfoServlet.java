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
 * Servlet implementation class ModifyAdminInfoServlet
 */
@WebServlet("/ModifyAdminInfoServlet")
public class ModifyAdminInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       adminDaoImpl dao = new adminDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String origName = new String(request.getParameter("adminOrigname").getBytes("ISO8859-1"),"UTF-8");
		if(dao.findAdminByAdminName(origName)==null)
		{
			JOptionPane.showMessageDialog(null,"查无此人哦www","修改错误", JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("/booksshop/admin_modify.jsp");
		}
		else{
		String name = new String(request.getParameter("adminname").getBytes("ISO8859-1"),"UTF-8");
		String password = new String(request.getParameter("adminpas").getBytes("ISO8859-1"),"UTF-8");
		Admin admin = new Admin(name,password);
		dao.modifyAdmins(admin,origName);
		JOptionPane.showMessageDialog(null,"修改成功哦www","修改成功", JOptionPane.DEFAULT_OPTION);
		response.sendRedirect("/booksshop/admin_modify.jsp");// TODO Auto-generated method stub
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
