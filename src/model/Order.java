package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.Connect;

public class Order
{
	private int orderId;
	private User orderUser;
	private ArrayList<OrderItem> orderItems;
	private String orderStatus;
	private Date orderDate;
	private int orderTotal;
	
	// CONSTRUCTOR [With Order Date]
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

	// CONSTRUCTOR [Without Order Date]
	public Order(int orderId, User orderUser, ArrayList<OrderItem> orderItems, String orderStatus, int orderTotal)
	{
		super();
		this.orderId = orderId;
		this.orderUser = orderUser;
		this.orderItems = orderItems;
		this.orderStatus = orderStatus;
		this.orderTotal = orderTotal;
	}
	
	
	// CRUD
	
	public static void createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
	{
		Date date = new Date();
		String query = "INSERT INTO order(orderId, userId, orderStatus, orderDate) VALUES (? ,? ,? ,? );";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt   (1, 0);
			prep.setInt	  (2, orderUser.getUserId());
			prep.setString(3, "Pending");
			prep.setDate  (4, (java.sql.Date) orderDate);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		// Insert orderItem
		
		for (OrderItem orderItem : orderItems)
		{
			orderItem.createOrderitem(orderItem.getOrderId(), orderItem.getMenuItem(), orderItem.getQuantity());
//			String queryItem = "INSERT INTO orderitem(orderItemId, orderId, menuItemId, quantity) VALUES (? ,? ,? ,? );";
//			  
//			try (Connection connection = Connect.getInstance().getConnection())
//			{
//				PreparedStatement prep = connection.prepareStatement(queryItem);
//				prep.setInt(1, 0);
//				prep.setInt(2, orderItem.getOrderId());
//				prep.setInt(3, orderItem.getMenuItem().getMenuItemId());
//				prep.setInt(4, orderItem.getQuantity());
//				prep.executeUpdate();
//			}
//			catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
		}
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