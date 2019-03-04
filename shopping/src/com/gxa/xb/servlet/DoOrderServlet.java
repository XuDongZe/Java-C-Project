package com.gxa.xb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gxa.xb.Dao.bookOrderDao;
import com.gxa.xb.Dao.orderDao;
import com.gxa.xb.Dao.Impl.bookOrderDaoImpl;
import com.gxa.xb.Dao.Impl.orderDaoImpl;
import com.gxa.xb.Util.DateUtil;
import com.gxa.xb.pojo.Bookorder;
import com.gxa.xb.pojo.CartItem;
import com.gxa.xb.pojo.Order;
import com.gxa.xb.pojo.User;

/**
 * Servlet implementation class DoOrderServlet
 */
@WebServlet("/DoOrderServlet")
public class DoOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Object  obj_user = request.getSession().getAttribute("user");
		Object  obj_cartList = request.getSession().getAttribute("cartList");
		
		if( obj_user!=null && obj_cartList!=null ) {
			User user = (User)obj_user;
			@SuppressWarnings("unchecked")
			List<CartItem> cartList = (List<CartItem>)obj_cartList;
			
			orderDao orderdao = new orderDaoImpl();
			bookOrderDao bookorderdao = new bookOrderDaoImpl();
			
			boolean first = true;
			Order newOrder = new Order(user, null);
			for(CartItem cartItem : cartList ) {
				if( first ) {
					newOrder.setOrderDate(DateUtil.getDateStr());
					int lastOrderId = orderdao.getLastOrderId();
					newOrder.setOrderId(lastOrderId + 1);
					orderdao.addOrders(newOrder);
					first = false;
				}

				Bookorder bookorder = new Bookorder(newOrder, cartItem.getBook(), cartItem.getBookNum());
				bookorderdao.addBookorder(bookorder);
			}
			
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
