package servlet.bk.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import product.bk.com.OrderItem;
import product.bk.com.Product;
import product.bk.com.ProductService;

@WebServlet("/CustomerConfirmOrderServlet")
public class CustomerConfirmOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerConfirmOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String[] code = request.getParameterValues("code");
		String[] quantity = request.getParameterValues("quantity");
		
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
				
		for(int i = 0; i<code.length; i++) {
			if(quantity[i] != "" && Integer.parseInt(quantity[i])>0) {
				
				Product product = new Product();
				product.setCode(Integer.parseInt(code[i]));
				
				OrderItem orderItem = new OrderItem();
				
				orderItem.setProduct(product);
				orderItem.setQuantity(Integer.parseInt(quantity[i]));
				
				orderItems.add(orderItem);
			}
		}
		
		ProductService productService = new ProductService();
		
		if(productService.insertOrderDetail(orderItems)>0) {
			response.sendRedirect(request.getContextPath()+"/CustomerOrderDetailServlet");
		}
		else {
			request.setAttribute("result", "Order Fail");
			request.getRequestDispatcher("CustomerOrder.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
