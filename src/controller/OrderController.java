package controller;

import java.sql.Date;
import java.util.ArrayList;

import model.Order;
import model.OrderItem;
import model.User;

public class OrderController
{

	public OrderController()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
	{
		// Add Order Sequence Diagram
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		Order.createOrder(orderUser, orderItems, new Date(System.currentTimeMillis())); // Date Now
	}
	
	public static String updateOrder(int orderId, ArrayList<OrderItem> orderItems, String orderStatus)
	{ 
		// Prepare Order Sequence Diagram
		// Serve Order Sequence Diagram
		// Process Order Payment Diagram
		// RULES : [From Sequence]
		// - No Validation
		// - Return Update [Success / Failed] Response
		
		String res = Order.updateOrder(orderId, orderItems, orderStatus);
		if(res.equals("not exist")) return "ERROR: Order doesn't exist";
		else if(res.equals("success")) return "SUCCESS: Success Update Order";
		else return "FAILED: Failed Update Order";
	}
	
	public static ArrayList<Order> getOrderByCustomerId(int customerId)
	{
		return Order.getOrdersByCustomerId(customerId);
	}
	
	public static void deleteOrder(int orderId)
	{
		// Remove Order Sequence Diagram
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		Order.deleteOrder(orderId);
	}
	
	public static ArrayList<Order> getAllOrders()
	{
		return Order.getAllOrders();
	}
	
	public static Order getOrderByOrderId(int orderId)
	{
		return Order.getOrderById(orderId);
	}

}
