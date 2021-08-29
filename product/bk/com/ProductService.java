package product.bk.com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.bk.com.DBConnector;


public class ProductService {

	public List<Product> selectProduct() throws Exception{

		String sql = "Select p.ProductId, p.Code, p.Name, p.Price from Product p";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Product> products = new ArrayList<Product>();
		
		try {

			conn = DBConnector.getConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Product product = new Product();
				product.setCode(rs.getInt(2));
				product.setName(rs.getString(3));
				product.setPrice(rs.getDouble(4));

				products.add(product);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			// Close database-relates objects
			if(rs != null)
				rs.close();
			
			if(stmt != null)
				stmt.close();
			
			if(conn != null)
				conn.close();
		}
		
		return products;
	}
	
		public int insertOrderDetail(List<OrderItem> orderItems) {

			Connection conn = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			List<Double> subtotals = new ArrayList<Double>();
			double totalPrice = 0.0;
			int receiptNo = 0;
			
			int affectedRow = 0;
			try {
				conn = DBConnector.getConnection();
				
				for(int i=0; i<orderItems.size();i++) {
					preparedStatement = conn.prepareStatement("select price from product where code=?");
					preparedStatement.setInt (1, orderItems.get(i).getProduct().getCode());	
					rs = preparedStatement.executeQuery();
					
					while(rs.next()) {
						subtotals.add(orderItems.get(i).getQuantity()*rs.getDouble(1));
						totalPrice += subtotals.get(i);
					}	
				}
				
				preparedStatement = conn.prepareStatement("insert into receipt (TotalPrice, Date) VALUES (?,?)");
				preparedStatement.setDouble (1, totalPrice);
				long millis=System.currentTimeMillis();
				java.sql.Date date=new java.sql.Date(millis);				
				preparedStatement.setDate(2, date);
				affectedRow+=preparedStatement.executeUpdate();
				
				preparedStatement = conn.prepareStatement("select ReceiptNo from receipt order by ReceiptNo desc limit 1");
				rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					receiptNo=rs.getInt(1);
				}	
				
				for(int i = 0; i < orderItems.size(); i++) {
					preparedStatement = conn.prepareStatement("insert into orderdetail (ReceiptNo, Code, Quantity, Subtotal) VALUES (?,?,?,?)");
					preparedStatement.setInt (1, receiptNo);
					preparedStatement.setInt (2, orderItems.get(i).getProduct().getCode());
					preparedStatement.setInt (3, orderItems.get(i).getQuantity());
					preparedStatement.setDouble (4, subtotals.get(i));
					affectedRow += preparedStatement.executeUpdate();
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return affectedRow;
			
		}
		
		public int getReceiptNo() {

			Connection conn = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			int receiptNo = 0;

			try {
				conn = DBConnector.getConnection();
				preparedStatement = conn.prepareStatement("select ReceiptNo from receipt order by ReceiptNo desc limit 1");
				rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					receiptNo=rs.getInt(1);
				}
				
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return receiptNo;
		}
		
		public List<OrderItem> viewOrderDetail(int receiptNo) {
			
			Connection conn = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			List<OrderItem> orderItems = new ArrayList<OrderItem>();

			try {
				conn = DBConnector.getConnection();
				preparedStatement = conn.prepareStatement("SELECT orderdetail.Code, orderdetail.Quantity, orderdetail.SubTotal, product.Name, product.Price from orderdetail inner join product ON orderdetail.code=product.code where receiptNo=?");
				preparedStatement.setInt (1, receiptNo);	
				rs = preparedStatement.executeQuery();
			
				while(rs.next()) {
					Product product = new Product();
					product.setCode(rs.getInt(1));
					product.setName(rs.getString(4));
					product.setPrice(rs.getDouble(5));
					
					OrderItem orderItem = new OrderItem();
					orderItem.setQuantity(rs.getInt(2));
					orderItem.setProduct(product);
					orderItem.setSubTotal(rs.getInt(3));
					
					orderItems.add(orderItem);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			return orderItems;
		}
		
		public List<Receipt> viewTodayHistoryAsc() {
			Connection conn = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			List<Receipt> receipts = new ArrayList<Receipt>();
			
			long millis=System.currentTimeMillis();
			java.sql.Date date=new java.sql.Date(millis);
			
			try {
				conn = DBConnector.getConnection();
				preparedStatement = conn.prepareStatement("SELECT orderdetail.Code, orderdetail.Quantity, orderdetail.SubTotal, receipt.receiptNo, receipt.TotalPrice, receipt.Date, product.Name, product.Price from orderdetail inner join receipt ON orderdetail.receiptNo=receipt.receiptNo inner join product on orderdetail.Code=product.Code where receipt.Date=? order by receipt.receiptNo");
				preparedStatement.setDate(1, date);
				rs = preparedStatement.executeQuery();
				int receiptNo = 0;
				Receipt receipt = null;

				while(rs.next()) {
					
					Product product = new Product();
					product.setCode(rs.getInt(1));
					product.setName(rs.getString(7));
					product.setPrice(rs.getDouble(8));
					
					OrderItem orderItem = new OrderItem();
					orderItem.setQuantity(rs.getInt(2));
					orderItem.setProduct(product);
					orderItem.setSubTotal(rs.getDouble(3));
					
					if(receiptNo != rs.getInt(4)) {
						receipt = new Receipt();
						receiptNo = rs.getInt(4);
						receipt.setReceiptNo(receiptNo);
						receipt.setTotalPrice(rs.getDouble(5));
						receipt.setDate(rs.getDate(6));
						receipt.addOrderItem(orderItem);
						receipts.add(receipt);
					}
					else if(receiptNo == rs.getInt(4)) {
						receipt.addOrderItem(orderItem);
					}
					
					
				}
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return receipts;
			
		}	
	
}
