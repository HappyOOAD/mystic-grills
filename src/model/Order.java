package model;

import java.util.ArrayList;
import java.util.Date;

public class Order
{
	private int orderId;
	private User orderUser;
	private ArrayList<OrderItem> orderItems;
	private String orderStatus;
	private Date orderDate;
	private int orderTotal;
	
	// CONSTRUCTOR
	public Order(int orderId, User orderUser, ArrayList<OrderItem> orderItems, String orderStatus, Date orderDate,
			int orderTotal)
	{
		super();
		this.orderId = orderId;
		this.orderUser = orderUser;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
	}
	
	
	// CRUD
	
	public static void createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
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
	
	public static void updateOrder(int orderId, ArrayList<OrderItem> orderItems, String orderStatus)
	{
		
	}
	
	public static void deleteOrder(int orderId)
	{
		
	}

	
	// GETTERS & SETTERS
	
	public int getOrderId()
	{
		return orderId;
	}


	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}


	public User getOrderUser() 
	{
		return orderUser;
	}


	public void setOrderUser(User orderUser)
	{
		this.orderUser = orderUser;
	}


	public ArrayList<OrderItem> getOrderItems()
	{
		return orderItems;
	}


	public void setOrderItems(ArrayList<OrderItem> orderItems)
	{
		this.orderItems = orderItems;
	}


	public String getOrderStatus()
	{
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus)
	{
		this.orderStatus = orderStatus;
	}


	public Date getOrderDate()
	{
		return orderDate;
	}


	public void setOrderDate(Date orderDate)
	{
		this.orderDate = orderDate;
	}


	public int getOrderTotal()
	{
		return orderTotal;
	}


	public void setOrderTotal(int orderTotal)
	{
		this.orderTotal = orderTotal;
	}		
}