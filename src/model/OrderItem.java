package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.Connect;

public class OrderItem
{
	private int orderId;
	private MenuItem menuItem;
	private int quantity;
	
	// CONSTRUCTOR
	public OrderItem(int orderId, MenuItem menuItem, int quantity)
	{
		super();
		this.orderId = orderId;
		this.menuItem = menuItem;
		this.quantity = quantity;
	}
	
	
	// CRUD
	
	public static void createOrderitem(int orderId, MenuItem menuItem, int quantity)
	{
		String query = "INSERT INTO orderitem (orderItemId, orderId, menuItemId, quantity) VALUES (? ,? ,? ,? );";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, 0);
			prep.setInt(1, orderId);
			prep.setInt(2, menuItem.getMenuItemId());
			prep.setInt(3, quantity);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId)
	{
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		String query = "SELECT * FROM orderitem WHERE orderId = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			ResultSet resultSet = prep.executeQuery(query);
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("orderId");
				int userId = resultSet.getInt("userId");
				String orderStatus = resultSet.getString("orderStatus");
				Date orderDate = resultSet.getDate("orderDate");
				User user = User.getUserById(userId);
				int total = 0;
//				ArrayList<OrderItem> orderItems = OrderItem.getAllOrderItemsByOrderId(id);
//				order.add(new Order(id, user, orderItems, orderStatus, orderDate, total));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return orderItems;
	}
	
	public static void updateOrderItem(int orderId, MenuItem menuItem, int quantity)
	{
		
	}
	
	public static void deleteOrderItem(int orderId, int menuItemId)
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

	public MenuItem getMenuItem()
	{
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem)
	{
		this.menuItem = menuItem;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}	
}
