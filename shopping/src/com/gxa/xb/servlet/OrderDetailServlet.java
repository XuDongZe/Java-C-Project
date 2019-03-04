package com.gxa.xb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.*;
import com.gxa.xb.Dao.Impl.*;
import com.gxa.xb.pojo.*;

/**
 * Servlet implementation class OrderDetailServlet
 */
@WebServlet("/OrderDetailServlet")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op = request.getParameter("op");
		int orderId = Integer.parseInt(op);
				
		bookOrderDao bookorderdao = new bookOrderDaoImpl();
		List<Bookorder> bookorderlist = new ArrayList<Bookorder>();
		
		bookorderlist = bookorderdao.findBookorderByorderId(orderId);
		
		if(bookorderlist != null) {
			request.getSession().setAttribute("OrderDetail", bookorderlist);
			response.sendRedirect("./orderdetails.jsp");
		}else {
			System.out.println("得到的bookorderlist为空");
			//response.sendRedirect("/booksshop/index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
