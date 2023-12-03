package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class MenuItem
{
	private int menuItemId;
	private String menuItemName;
	private String menuItemDescription;
	private double menuItemPrice;
	
	// CONSTRUCTOR
	public MenuItem(int menuItemId, String menuItemName, String menuItemDescription, double menuItemPrice)
	{
		super();
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.menuItemDescription = menuItemDescription;
		this.menuItemPrice = menuItemPrice;
	}
	
	
	// CRUD
	
	public static String createMenuItem(String menuItemName, String menuItemDescription, double menuItemPrice)
	{
		String query = "INSERT INTO menuitem(menuItemId, menuItemName, menuItemDescription, menuItemPrice) VALUES (? ,? ,? ,? )";
				  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt   (1, 0);
			prep.setString(2, menuItemName);
			prep.setString(3, menuItemDescription);
			prep.setDouble(4, menuItemPrice);
			prep.executeUpdate();
			return "success";
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return "failed";
		}
	}
	
	public static MenuItem getMenuItemById(int menuItemId)
	{
		MenuItem menuItem = null;
		String query = "SELECT * FROM menuitem WHERE menuItemId = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, menuItemId);
			ResultSet resultSet = prep.executeQuery();
			
			if(resultSet.next())
			{
				int id = resultSet.getInt("menuItemId");
				String name = resultSet.getString("menuItemName");
				String description = resultSet.getString("menuItemDescription");
				double price = resultSet.getDouble("menuItemPrice");
				menuItem = new MenuItem(id, name, description, price);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return menuItem;
	}
	
	public static ArrayList<MenuItem> getAllMenuItems()
	{
		ArrayList<MenuItem> menuItems = new ArrayList<>();
		String query = "SELECT * FROM menuitem;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet resultSet = prep.executeQuery();
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("menuItemId");
				String name = resultSet.getString("menuItemName");
				String description = resultSet.getString("menuItemDescription");
				double price = resultSet.getDouble("menuItemPrice");
				menuItems.add(new MenuItem(id, name, description, price));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return menuItems;
	}
	
	public static String updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, double menuItemPrice)
	{
		// CHECK UNIQUE
		String checkQuery = "SELECT * FROM menuitem WHERE menuItemName = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(checkQuery);
			prep.setString(1, menuItemName);
			ResultSet resultSet = prep.executeQuery();
			
			if(resultSet.next()) return "exist";
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return "failed";
		}
		
		// INSERT
		String query = "UPDATE menuitem SET menuItemName = ?, menuItemDescription = ?, menuItemPrice = ? WHERE menuItemId = ?;";
			
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setString(1, menuItemName);
			prep.setString(2, menuItemDescription);
			prep.setDouble(3, menuItemPrice);
			prep.setInt(4, menuItemId);
			prep.executeUpdate();
			return "success";
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return "failed";
		}
	}
	
	public static void deleteMenuItem(int menuItemId)
	{
		String query = "DELETE FROM menuitem WHERE menuItemId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, menuItemId);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	// Validate Functions
	
	public static boolean nameIsExist(String menuItemName)
	{
		String checkQuery = "SELECT * FROM menuitem WHERE menuItemName = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(checkQuery);
			prep.setString(1, menuItemName);
			ResultSet resultSet = prep.executeQuery();
			
			if(resultSet.next()) return true;
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	// GETTERS & SETTERS

	public int getMenuItemId()
	{
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId)
	{
		this.menuItemId = menuItemId;
	}

	public String getMenuItemName()
	{
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName)
	{
		this.menuItemName = menuItemName;
	}

	public String getMenuItemDescription()
	{
		return menuItemDescription;
	}

	public void setMenuItemDescription(String menuItemDescription)
	{
		this.menuItemDescription = menuItemDescription;
	}

	public double getMenuItemPrice()
	{
		return menuItemPrice;
	}

	public void setMenuItemPrice(double menuItemPrice)
	{
		this.menuItemPrice = menuItemPrice;
	}
}