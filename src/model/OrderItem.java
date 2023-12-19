package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class OrderItem
{
	private int orderId;
	private int menuItemId;
	private MenuItems menuItem;
	private int quantity;
	
	// CONSTRUCTOR
	public OrderItem(int orderId, int menuItemId, int quantity)
	{
		super();
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
	}
	
	
	// CRUD
	
	public static void createOrderitem(int orderId, MenuItems menuItem, int quantity)
	{
		String query = "INSERT INTO orderitems (orderId, menuItemId, quantity) VALUES (? ,? ,?)";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
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
		String query = "SELECT * FROM orderitems WHERE orderId = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			ResultSet resultSet = prep.executeQuery();
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("orderId");
				int menuItemId = resultSet.getInt("menuItemId");
				int quantity = resultSet.getInt("quantity");
				orderItems.add(new OrderItem(id, menuItemId, quantity));
			}
			resultSet.close();
			
			for (OrderItem orderItem : orderItems)
			{
				orderItem.setMenuItem();
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return orderItems;
	}
	
	public static void updateOrderItem(int orderId, MenuItems menuItem, int quantity)
	{
		String query = "UPDATE orderitems SET quantity = ? WHERE orderId = ? AND menuItemId = ?;";
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, quantity);
			prep.setInt(2, orderId);
			prep.setInt(3, menuItem.getMenuItemId());
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteOrderItem(int orderId, int menuItemId)
	{
		String query = "DELETE FROM orderitems WHERE orderId = ? AND menuItemId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			prep.setInt(2, menuItemId);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	// ADDITIONAL METHODS
	
	public static OrderItem getOrderItemByOrderIdAndMenuItemId(int orderId, int menuItemId)
	{
		OrderItem orderItem = null;
		String query = "SELECT * FROM orderitems WHERE orderId = ? AND menuItemId = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			prep.setInt(2, menuItemId);
			ResultSet resultSet = prep.executeQuery();
			
			if(resultSet.next()) 
			{
				int quantity = resultSet.getInt("quantity");
				orderItem = new OrderItem(orderId, menuItemId, quantity);
			}
			resultSet.close();
			
			orderItem.setMenuItem();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return orderItem;
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
	
	public int getMenuItemId()
	{
		return menuItemId;
	}
	
	public void getMenuItemId(int menuItemId)
	{
		this.menuItemId = menuItemId;
	}

	public MenuItems getMenuItem()
	{
		return menuItem;
	}

	public void setMenuItem()
	{
		this.menuItem =  MenuItems.getMenuItemById(menuItemId);
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
