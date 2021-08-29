<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="product.bk.com.Product"%>
<%@ page import="product.bk.com.OrderItem"%>
<%@ page import="product.bk.com.Receipt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DecimalFormat"%>

<!DOCTYPE html>
<html>

<style>
	td, th { border:1px solid black; padding:5px; }
	table { border-collapse:collapse; }
</style>

<head>
<meta charset="ISO-8859-1">
<title>History</title>
</head>
<body>
	<%
		// Receive List from servlet
		@SuppressWarnings ("unchecked")
		List<Receipt> receipts =  (ArrayList<Receipt>) request.getAttribute("receipts");
		DecimalFormat df = new DecimalFormat("#.00");
		
		// Check is there any history for today and output according to the condition
		if(receipts.size()>0){
			out.println("<h3>Your Order History on "+receipts.get(0).getDate()+"</h3>");
		}
		else{
			out.println("<h3>No history yet</h3>");
		}
		
	%>
	
	<%
		// Loop the receipt
		for(Receipt receipt:receipts){
			out.println("Receipt No: "+receipt.getReceiptNo()+"<br> Date: "+receipt.getDate()+"<br> Total Price : RM "+df.format(receipt.getTotalPrice()));
			out.print("<table><tr><th>Product Code</th><th>Product Name</th><th>Price (MYR)</th><th>Quantity</th><th>Subtotal (MYR)</th></tr>");	
			for(OrderItem orderItem:receipt.getOrderItems()){
				out.print("<tr><td style = 'text-align:center'>"+orderItem.getProduct().getCode()+"</td><td>"+orderItem.getProduct().getName()+"</td><td style = 'text-align:center'>"+orderItem.getProduct().getPrice()+"</td><td style = 'text-align:center'>"+orderItem.getQuantity()+"</td><td style = 'text-align:center'>"+df.format(orderItem.getSubTotal())+"</td></tr>");
			}
			out.print("</table><br><br>");
		}
		
	%>

<a href="index.jsp">Return</a>

</body>
</html>