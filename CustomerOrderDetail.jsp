<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="product.bk.com.Product"%>
<%@ page import="product.bk.com.OrderItem"%>
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
<title>Order Detail</title>
</head>
<body>
	<%
		@SuppressWarnings ("unchecked")
		List<OrderItem> orderItems =  (ArrayList<OrderItem>) request.getAttribute("orderDetail");
		int receiptNo =  (Integer)request.getAttribute("receiptNo");
		DecimalFormat df = new DecimalFormat("#.00");
	%>
	<h4><% out.print(result); %></h4>
	<h3>Your Order Detail</h3>
	<h3>Receipt No: <% out.print(receiptNo); %></h3>
	
	<%
		double total=0.0;
		out.print("<table><tr><th>Product Code</th><th>Product Name</th><th>Price (MYR)</th><th>Quantity</th><th>Subtotal (MYR)</th></tr>");	
		for (OrderItem orderItem : orderItems) {
			out.print("<tr><td style = 'text-align:center'>"+orderItem.getProduct().getCode()+"</td><td>"+orderItem.getProduct().getName()+"</td><td style = 'text-align:center'>"+orderItem.getProduct().getPrice()+"</td><td style = 'text-align:center'>"+orderItem.getQuantity()+"</td><td style = 'text-align:center'>"+df.format(orderItem.getSubTotal())+"</td></tr>");
			total+=orderItem.getSubTotal();
		}
		out.print("</table>");
		out.print("<br>Total = RM "+df.format(total));
	%>
	<p></p>
	<a href="index.jsp">Confirm</a>

</body>
</html>