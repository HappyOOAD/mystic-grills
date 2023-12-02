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
	private double orderTotal;
	
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
				int total = 0;
				orders.add(new Order(id, user, orderItems, status, date, total));
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
		ArrayList<Order> order = new ArrayList<>();
		String query = "SELECT * FROM orders";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet resultSet = prep.executeQuery(query);
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("orderId");
				int userId = resultSet.getInt("userId");
				String orderStatus = resultSet.getString("orderStatus");
				Date orderDate = resultSet.getDate("orderDate");
				User user = User.getUserById(userId);
				int total = 0;
				ArrayList<OrderItem> orderItems = OrderItem.getAllOrderItemsByOrderId(id);
				order.add(new Order(id, user, orderItems, orderStatus, orderDate, total));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return order;
	}
	
	public static Order getOrderById(int orderId)
	{
		Order order = null;
		String query = "SELECT * FROM orders WHERE orderId = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			ResultSet resultSet = prep.executeQuery();

			if(resultSet.next())
			{
				
				int id = resultSet.getInt("orderId");
				int userId = resultSet.getInt("userId");
				String orderStatus = resultSet.getString("orderStatus");
				Date orderDate = resultSet.getDate("orderDate");
				User user = User.getUserById(userId);
				int total = 0;
				ArrayList<OrderItem> orderItems = OrderItem.getAllOrderItemsByOrderId(id);
				order = new Order(id, user, orderItems, orderStatus, orderDate, total);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return order;
	}
	
	public static void updateOrder(int orderId, ArrayList<OrderItem> orderItems, String orderStatus)
	{
		
	}
	
	public static void deleteOrder(int orderId)
	{
		String query = "DELETE FROM orders WHERE orderId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
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

	public double getOrderTotal()
	{
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal)
	{
		this.orderTotal = orderTotal;
	}		
}
