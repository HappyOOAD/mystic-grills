package controller;

import java.util.ArrayList;

import model.MenuItem;

public class MenuItemController {

	public MenuItemController() {
		// TODO Auto-generated constructor stub
	}

	public String createMenuItem(String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		if(menuItemName.isBlank()) return "Menu Item Name Empty"; // CANNOT EMPTY
		
		if(menuItemDescription.length() <= 10) return "Menu Item Description Length must be more than 10 characters"; // Must be more than 10 characters
				
		if(menuItemPrice < 2.5) return "Menu Item Price must a number that is greater than or equal to 2.5"; // -	Must be a number that is greater than or equal to (>=) 2.5
		
		String res = MenuItem.createMenuItem(menuItemName, menuItemDescription, menuItemPrice);
		if(res.equals("success")) return "Success Create A New Menu Item";
		else if(res.equals("exist")) return "Menu Item Already Exist"; // MUST UNIQUE
		else return "Failed Create A New Menu Item";
	}
	
	public String updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription, int menuItemPrice)
	{
		if(menuItemName.isBlank()) return "Menu Item Name Empty"; // CANNOT EMPTY
		
		if(menuItemDescription.length() <= 10) return "Menu Item Description Length must be more than 10 characters"; // Must be more than 10 characters
				
		if(menuItemPrice < 2.5) return "Menu Item Price must a number that is greater than or equal to 2.5"; // -	Must be a number that is greater than or equal to (>=) 2.5
		
		String res = MenuItem.updateMenuItem(menuItemId, menuItemName, menuItemDescription, menuItemPrice);
		if(res.equals("success")) return "Success Update A Menu Item";
		else if(res.equals("exist")) return "Menu Item Already Exist"; // MUST UNIQUE
		else return "Failed Update A Menu Item";
	}
	
	public void deleteMenuItem(int menuItemId)
	{
		MenuItem.deleteMenuItem(menuItemId);
	}
	
	public MenuItem getMenuItemById(int menuItemId)
	{
		return MenuItem.getMenuItemById(menuItemId);
	}
	
	public ArrayList<MenuItem> getAllMenuItem()
	{
		return MenuItem.getAllMenuItems();
	}
	
	
}
