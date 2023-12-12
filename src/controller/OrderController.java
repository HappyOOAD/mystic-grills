package controller;

import java.util.ArrayList;
import java.util.Date;

import model.Order;
import model.OrderItem;
import model.User;

public class OrderController
{

	public OrderController()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static String createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
	{
		if(orderItems.size() == 0) return "Order Items Empty"; // Menu Item must be chosen
		return "Success Create An Order";
	}
	
	public static String updateOrder(int orderId, ArrayList<OrderItem> orderItems, String orderStatus)
	{ 
		String res = Order.updateOrder(orderId, orderItems, orderStatus);
		if(res.equals("success")) return "Success Update Order";
		else return "Failed Update Order";
	}
	
	public static ArrayList<Order> getOrderByCustomerId(int customerId)
	{
		return Order.getOrdersByCustomerId(customerId);
	}
	
	public static String deleteOrder(int orderId)
	{
		String res = Order.deleteOrder(orderId);
		
		if(res.equals("Success")) return "Sucess Delete Order";
		
		return "Failed Delete Order";
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
