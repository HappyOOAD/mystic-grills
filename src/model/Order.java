package model;

import java.util.ArrayList;
import java.util.Date;

public class Order {

	private int orderId;
	private User orderUser;
	private ArrayList<OrderItem> orderItems;
	private String orderStatus;
	private Date orderDate;
	private int orderTotal;
	
	public Order(int orderId, User orderUser, ArrayList<OrderItem> orderItems, String orderStatus, Date orderDate,
			int orderTotal) {
		super();
		this.orderId = orderId;
		this.orderUser = orderUser;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
	}
	
	public static void createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
	{
		
	}
	
	public static void updateOrder(int orderId, ArrayList<OrderItem> orderItems, String orderStatus)
	{
		
	}
	
	public static void deleteOrder(int orderId)
	{
		
	}
	
	public static ArrayList<Order> getOrdersByCustomerId(int customerId)
	{
		return null;
	}
	
	public static ArrayList<Order> getAllOrders()
	{
		return null;
	}
	
	public static Order getOrderById(int orderId)
	{
		return null;
	}
	
}
