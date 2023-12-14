package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import model.Order;
import model.OrderItem;
import model.User;

public class OrderController
{

	public OrderController()
	{
		// TODO Auto-generated constructor stub
	}
	
	public int createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
	{
		// Add Order Sequence Diagram [CUSTOMER]
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		// Additional Rules:
		// - Need To Return OrderId
		
		return Order.createOrder(orderUser, orderItems, new Date(System.currentTimeMillis())); // Date Now
	}
	
	public String updateOrder(int orderId, ArrayList<OrderItem> orderItems, String orderStatus)
	{ 
		// Prepare Order Sequence Sequence Diagram [CHEF]
		// RULES: (From Sequence)
		// - No Validation
		// - Display Updated Order Status / Display Error Message
		
		// Serve Order Sequence Sequence Diagram [WAITER]
		// RULES: (From Sequence)
		// - No Validation
		// - Display Updated Order Status / Display Error Message
				
		// Process Order Payment Sequence Diagram [CASHIER]
		// RULES: (From Sequence)
		// - No Validation
		// - Display Updated Order Status / Display Error Message
		
		String res = Order.updateOrder(orderId, orderItems, orderStatus);
		if(res.equals("not exist")) return "ERROR: Order doesn't exist";
		else if(res.equals("success")) return "SUCCESS: Success Update Order";
		else return "FAILED: Failed Update Order";
	}
	
	public ArrayList<Order> getOrderByCustomerId(int customerId)
	{
		// Update Order Sequence Diagram [KITCHEN]
		// RULES: (From Sequence)
		// - No Validation
		// - Return Order
		
		return Order.getOrdersByCustomerId(customerId);
	}
	
	public String deleteOrder(int orderId)
	{
		// Remove Order Sequence Diagram [KITCHEN]
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		String res = Order.deleteOrder(orderId);
		if(res.equals("success")) return "SUCCESS: Delete Order";
		else return "FAILED: Delete order";
	}
	
	public ArrayList<Order> getAllOrders()
	{
		// View Orders Sequence Diagram [KITCHEN, CASHIER]
		// RULES: (From Sequence)
		// - No Validation
		// - Return List<Order>
		
		return Order.getAllOrders();
	}
	
	public Order getOrderByOrderId(int orderId)
	{
		// View Order Details Sequence Diagram [KITCHEN]
		// RULES: (From Sequence)
		// - No Validation
		// - Return Order
		
		return Order.getOrderById(orderId);
	}
	
	
	// ADITIONAL
	
	public ArrayList<Order> getAllOrdersByOrderStatus(String orderStatus)
	{
		ArrayList<Order> allOrders = getAllOrders();
		return (ArrayList<Order>) allOrders.stream()
			.filter(order -> orderStatus.equalsIgnoreCase(order.getOrderStatus()))
			.collect(Collectors.toList());
	}
}
