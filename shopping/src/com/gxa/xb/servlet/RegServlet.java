package com.gxa.xb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.userDao;
import com.gxa.xb.Dao.Impl.userDaoImpl;
import com.gxa.xb.pojo.User;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       userDao userdao = new userDaoImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegServlet() {
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
		String name = new String(request.getParameter("username").getBytes("ISO8859-1"),"UTF-8");//获取用户提交的账户
		String pwd =new String(request.getParameter("userpassword").getBytes("ISO8859-1"),"UTF-8");
		int flag=userdao.userReg(new User(name,pwd));
		if(flag!=0){
		//	request.getSession().setAttribute("user", user);
			response.sendRedirect("./index.jsp");
		}else{
			response.sendRedirect("./login.jsp");//重定向
			//request.getRequestDispatcher("/login.jsp").forward(request, response);//转发
		
		}
	}

}
