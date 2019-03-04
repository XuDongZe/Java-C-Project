package com.gxa.xb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.orderDao;
import com.gxa.xb.Dao.Impl.orderDaoImpl;
import com.gxa.xb.pojo.Order;

/**
 * Servlet implementation class HistoryOrderServlet
 */
public class HistoryOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String HistoryOrder = request.getParameter("HistoryOrder");
		
		orderDao orderdao = new orderDaoImpl();
		List<Order> orderlist = new ArrayList<Order>();
		
		int userid = Integer.parseInt(request.getParameter("op"));
		orderlist = orderdao.findOrderByUser(userid);
		
		if(orderlist != null) {
			request.getSession().setAttribute("HistoryOrder", orderlist);
			response.sendRedirect("./order.jsp");
		}else {
			System.out.println("得到的orderlist为空");
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
