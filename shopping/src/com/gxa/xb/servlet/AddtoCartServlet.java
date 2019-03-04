package com.gxa.xb.servlet;

import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.CartItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.gxa.xb.Dao.bookDao;
import com.gxa.xb.Dao.Impl.bookDaoImpl;

/**
 * Servlet implementation class AddtoCartServlet
 */
public class AddtoCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddtoCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookId = request.getParameter("bookId");
		String bookNum = request.getParameter("bookNum");
		
		bookDao bookdao = new bookDaoImpl();
		System.out.println("bookId=" + bookId);
		Book book = bookdao.findBookByBookId( Integer.parseInt(bookId) );
		
		Object obj = request.getSession().getAttribute("cartList");
		
		List <CartItem> cartList = null;
		if( obj == null ) {
			cartList = new ArrayList<CartItem>();
		}else {
			cartList = (List<CartItem>)obj;
			System.out.println("> list.size()=" + cartList.size());
		}
		
		if(book != null) {
			CartItem cartItem = new CartItem(book, Integer.parseInt(bookNum) );
			System.out.println(cartItem.getBook().getBookName());
			cartList.add(cartItem);
			request.getSession().setAttribute("cartList", cartList);
			//JOptionPane.showMessageDialog(null, "添加成功!", "提示",JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("./bookdetails.jsp");
		}else {
			System.out.println("null");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
