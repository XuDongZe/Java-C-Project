package com.gxa.xb.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.adminDao;
import com.gxa.xb.Dao.userDao;
import com.gxa.xb.Dao.Impl.adminDaoImpl;
import com.gxa.xb.Dao.Impl.userDaoImpl;
import com.gxa.xb.pojo.Admin;
import com.gxa.xb.pojo.User;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	userDao userdao = new userDaoImpl();
    adminDao admindao = new adminDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int shenfen = Integer.parseInt(request.getParameter("shenfen"));
		
		String name = new String(request.getParameter("username").getBytes("ISO8859-1"),"UTF-8");//获取用户提交的账户
		String pwd = new String(request.getParameter("userpassword").getBytes("ISO8859-1"),"UTF-8");
		System.out.println("urlencodeString" + URLEncoder.encode(request.getParameter("username"), "utf-8"));
		System.out.println("rawString =" +  request.getParameter("username"));
		if(shenfen==0){
			User user = userdao.userLogin(new User(name,pwd));
			if(user!=null){
				request.getSession().setAttribute("user", user);
				
				response.sendRedirect("/booksshop/index");
			}
			else{
				request.getSession().setAttribute("error", "对hi白");
				response.sendRedirect("./login.jsp");//重定向
				//request.getRequestDispatcher("/login.jsp").forward(request, response);//转发
			}
		}
		else {
			Admin admin = admindao.adminLogin(new Admin(name,pwd));
			if(admin!=null){
					request.getSession().setAttribute("admin", admin);
					response.sendRedirect("./admin.jsp");
			}
			else{
				response.sendRedirect("./login.jsp");//重定向
				//request.getRequestDispatcher("/login.jsp").forward(request, response);//转发
			}
		}
	}
}
