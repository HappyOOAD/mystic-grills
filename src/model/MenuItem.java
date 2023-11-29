package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Connect;

public class MenuItem
{
	private int menuItemId;
	private String menuItemName;
	private String menuItemDescription;
	private int menuItemPrice;
	
	// CONSTRUCTOR
	public MenuItem(int menuItemId, String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		super();
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.menuItemDescription = menuItemDescription;
		this.menuItemPrice = menuItemPrice;
	}
	
	
	// CRUD
	
	public static void createMenuItem(String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		String query = "INSERT INTO menuitem(menuItemId, menuItemName, menuItemDescription, menuItemPrice) VALUES (0,'"
			+ menuItemName 			+ "', '"
			+ menuItemDescription 	+ "', '"
			+ menuItemPrice 		+ "')";
				  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static MenuItem getMenuItemById(int menuItemId)
	{
		MenuItem menuItem = null;
		String query = "SELECT * FROM menuitem WHERE menuItemId = " + menuItemId + ";";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next())
			{
				int id = resultSet.getInt("menuItemId");
				String name = resultSet.getString("menuItemName");
				String description = resultSet.getString("menuItemDescription");
				int price = resultSet.getInt("menuItemPrice");
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
		String query = "SELECT * FROM menuitem";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("menuItemId");
				String name = resultSet.getString("menuItemName");
				String description = resultSet.getString("menuItemDescription");
				int price = resultSet.getInt("menuItemPrice");
				menuItems.add(new MenuItem(id, name, description, price));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return menuItems;
	}
	
	public static void updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		String query = "UPDATE menuitem SET "
				+ "menuItemName = '" + menuItemName + "', "
				+ "menuItemDescription = '" + menuItemDescription + "', "
				+ "menuItemPrice = " + menuItemPrice + " "
				+ "WHERE "
				+ "menuItemId = " + menuItemId + ";";
				
		System.out.println(query);
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void deleteMenuItem(int menuItemId)
	{
		String query = "DELETE FROM menuitem WHERE menuItemId = " + menuItemId + ";";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
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

	public int getMenuItemPrice()
	{
		return menuItemPrice;
	}

	public void setMenuItemPrice(int menuItemPrice)
	{
		this.menuItemPrice = menuItemPrice;
	}
}