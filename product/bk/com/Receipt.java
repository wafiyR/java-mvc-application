package product.bk.com;

import java.sql.Date;
import java.util.ArrayList;

public class Receipt {
	private int receiptNo;
	private ArrayList<OrderItem> orderItems=new ArrayList<OrderItem>();
	private double totalPrice;
	
	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(ArrayList<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(int receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	
}
