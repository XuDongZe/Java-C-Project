package com.gxa.xb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.gxa.xb.Dao.userDao;
import com.gxa.xb.Dao.Impl.userDaoImpl;
import com.gxa.xb.pojo.User;

/**
 * Servlet implementation class ModifyUserInfoServlet
 */
public class ModifyUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	userDao userdao = new userDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pwd = request.getParameter("userpassword");
		String realname = request.getParameter("userrealname");
		String addr = request.getParameter("useraddress");
		String tel = request.getParameter("usertel");
		String email = request.getParameter("useremail");
		
		String name = request.getParameter("userName");
		//int id = Integer.parseInt(request.getParameter("userID"));
		//System.out.println(request.getParameter("op"));
		int id = Integer.parseInt(request.getParameter("op"));
		User user = new User(id,name,pwd,realname,addr,tel,email);
		userdao.modifyUser(user);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pwd = new String(request.getParameter("newuserpass").getBytes("ISO8859-1"),"UTF-8");
		String realname = new String(request.getParameter("newuserrealname").getBytes("ISO8859-1"),"UTF-8");
		String addr = new String(request.getParameter("newuseraddress").getBytes("ISO8859-1"),"UTF-8");
		String tel = new String(request.getParameter("newusertel").getBytes("ISO8859-1"),"UTF-8");
		String email = new String(request.getParameter("newuseremail").getBytes("ISO8859-1"),"UTF-8");
		
		String name = new String(request.getParameter("username").getBytes("ISO8859-1"),"UTF-8");
		//int id = Integer.parseInt(request.getParameter("userID"));
		//System.out.println(request.getParameter("op"));
		int id = Integer.parseInt(request.getParameter("userid"));
		User user = new User(id,name,pwd,realname,addr,tel,email);
		userdao.modifyUser(user);
		//JOptionPane.showMessageDialog(null,"修改成功!","提示", JOptionPane.ERROR_MESSAGE);
		response.sendRedirect("./user_update.jsp");
	}

}
