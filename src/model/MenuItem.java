package model;

import java.util.ArrayList;

public class MenuItem
{
	private int menuItemId;
	private String menuItemName;
	private String menuItemDescription;
	private int menuItemPrice;
	
	public MenuItem(int menuItemId, String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		super();
		this.menuItemId = menuItemId;
		this.menuItemName = menuItemName;
		this.menuItemDescription = menuItemDescription;
		this.menuItemPrice = menuItemPrice;
	}
	
	public void createMenuItem(String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		
	}
	
	public void updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		
	}
	
	public void deleteMenuItem(int menuItemId)
	{
		
	}
	
	public MenuItem getMenuItemById(int MenuItemId)
	{
		return null;
	}
	
	public ArrayList<MenuItem> getAllMenuItems()
	{
		return null;
	}
	
}
