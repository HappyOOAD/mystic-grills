package model;

import java.util.ArrayList;

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
	
	public void createOrderitem(int orderId, MenuItem menuItem, int quantity)
	{
		
	}
	
	public ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId)
	{
		return null;
	}
	
	public void updateOrderItem(int orderId, MenuItem menuItem, int quantity)
	{
		
	}
	
	public void deleteOrderItem(int orderId, int menuItemId)
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