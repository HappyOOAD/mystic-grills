package controller;

import java.util.ArrayList;

import model.MenuItems;

public class MenuItemController
{

	public MenuItemController()
	{
		// TODO Auto-generated constructor stub
	}

	public static String createMenuItem(String menuItemName, String menuItemDescription, double menuItemPrice)
	{
		if(menuItemName.isBlank()) return "Menu Item Name Empty"; // CANNOT EMPTY
		if(MenuItems.nameIsExist(menuItemName)) return "Menu Item Name Exist"; // MUST BE UNIQUE
		
		if(menuItemDescription.length() <= 10) return "Menu Item Description Length must be more than 10 characters"; // Must be more than 10 characters
				
		if(menuItemPrice < 2.5) return "Menu Item Price must a number that is greater than or equal to 2.5"; // -	Must be a number that is greater than or equal to (>=) 2.5
		
		String res = MenuItems.createMenuItem(menuItemName, menuItemDescription, menuItemPrice);
		if(res.equals("success")) return "Success Create A New Menu Item";
		else if(res.equals("exist")) return "Menu Item Already Exist"; // MUST UNIQUE
		else return "Failed Create A New Menu Item";
	}
	
	public static String updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, double menuItemPrice)
	{
		if(menuItemName.isBlank()) return "Menu Item Name Empty"; // CANNOT EMPTY
		if(MenuItems.nameIsExist(menuItemName)) return "Menu Item Name Exist"; // MUST BE UNIQUE
		
		if(menuItemDescription.length() <= 10) return "Menu Item Description Length must be more than 10 characters"; // Must be more than 10 characters
				
		if(menuItemPrice < 2.5) return "Menu Item Price must a number that is greater than or equal to 2.5"; // -	Must be a number that is greater than or equal to (>=) 2.5
		
		String res = MenuItems.updateMenuItem(menuItemId, menuItemName, menuItemDescription, menuItemPrice);
		if(res.equals("success")) return "Success Update A Menu Item";
		else if(res.equals("exist")) return "Menu Item Already Exist"; // MUST UNIQUE
		else return "Failed Update A Menu Item";
	}
	
	public static void deleteMenuItem(int menuItemId)
	{
		MenuItems.deleteMenuItem(menuItemId);
	}
	
	public MenuItems getMenuItemById(int menuItemId)
	{
		return MenuItems.getMenuItemById(menuItemId);
	}
	
	public static ArrayList<MenuItems> getAllMenuItem()
	{
		return MenuItems.getAllMenuItems();
	}
	
	
}
