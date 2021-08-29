<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "product.bk.com.Product" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<!DOCTYPE html>
<html>

<style>
	td, th { border:1px solid black; padding:5px; }
	table { border-collapse:collapse; }
</style>

<head>
<meta charset="ISO-8859-1">
<title>Menu</title>
</head>

<body>
<h2>**********Menu**********</h2>
<%
	@SuppressWarnings ("unchecked")
	List<Product> products = (ArrayList<Product>) request.getAttribute("products");
	out.print("<table id='t01'><tr><th>Code</th><th>Product</th><th>Price (MYR)</th></tr>");
	
	for (Product product:products){
		out.print("<tr><td style = 'text-align:center'>" + product.getCode() + "</td><td>" + product.getName() 
		+ "</td><td style = 'text-align:center'>" + product.getPrice() + "</td></tr>");
	}
	
	out.print("</table>");
%>

<h2>**********Order**********</h2>
<p>Please enter the quantity to place order.</p>
	<form method="post" action="CustomerConfirmOrderServlet">
		<table>
			<tr>
				<th>Code</th>
				<th>Quantity</th>
			</tr>
			<%
				// Get the quantity
				for(Product product:products){
					out.print("<tr><td style = 'text-align:center'><input type='hidden' name='code' value='"+product.getCode()+"'>" 
								+ product.getCode() + "</td>" +
								"<td><input type='number' name='quantity'></td></tr>");
				}
			%>
		</table>
		<br>
		<input type="submit" value="Confirm Order">
	</form>
	
<br><a href="index.jsp">Back</a>

</body>
</html>