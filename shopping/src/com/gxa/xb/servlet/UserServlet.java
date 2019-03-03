package com.gxa.xb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.UserDao;
import com.gxa.xb.Dao.impl.UserDaoImpl;
import com.gxa.xb.pojo.UserInfo;


public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      UserDao userDao = new UserDaoImpl();
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doget");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("userName");//获取用户提交的账户
		String pwd = request.getParameter("userPwd");
		UserInfo user = userDao.userLogin(new UserInfo(name,pwd));
		if(user!=null){
			response.sendRedirect("./index.jsp");
		}else{
			response.sendRedirect("./login.jsp");
		}
	}

}
