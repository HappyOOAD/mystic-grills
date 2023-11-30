package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		String query = "INSERT INTO order (orderId, userId, orderStatus, orderDate) VALUES (? ,? ,? ,? );";
		  
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
			OrderItem.createOrderitem(orderItem.getOrderId(), orderItem.getMenuItem(), orderItem.getQuantity());
		}
	}
	
	public static ArrayList<Order> getOrdersByCustomerId(int customerId)
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		String query = "SELECT * FROM order WHERE userId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt   (1, customerId);
			ResultSet resultSet = prep.executeQuery();
			while(resultSet.next())
			{
				int id = resultSet.getInt("orderId");
				int userId = resultSet.getInt("userId");
				User user = User.getUserById(userId);
				ArrayList<OrderItem> orderItems = OrderItem.getAllOrderItemsByOrderId(id);
				String status = resultSet.getString("orderStatus");
				Date date = resultSet.getDate("orderDate");
				int total = 
				orders.add(new Order(id, user, orderItems, status, 0));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return orders;
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