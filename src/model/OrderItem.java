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
	private int orderItemId; // not necessary
	private int orderId;
	private MenuItem menuItem;
	private int quantity;
	
	// CONSTRUCTOR
	public OrderItem(int orderItemId, int orderId, MenuItem menuItem, int quantity)
	{
		super();
		this.orderItemId = orderItemId;
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
				int id = resultSet.getInt("orderItemId");
				int menuItemId = resultSet.getInt("menuItemId");
				MenuItem menuItem = MenuItem.getMenuItemById(menuItemId);
				int quantity = resultSet.getInt("quantity");
				orderItems.add(new OrderItem(id, orderId, menuItem, quantity));
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
		String query = "UPDATE orderitem SET quantity = ? WHERE orderId = ? AND menuItemId = ?;";
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, quantity);
			prep.setInt(2, orderId);
			prep.setInt(2, menuItem.getMenuItemId());
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteOrderItem(int orderId, int menuItemId)
	{
		String query = "DELETE FROM orderitem WHERE orderId = ? AND menuItemId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			prep.setInt(1, menuItemId);
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
