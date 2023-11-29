package model;

import java.util.ArrayList;

public class OrderItem
{
	private int orderId;
	private MenuItem menuItem;
	private int quantity;
	
	public OrderItem(int orderId, MenuItem menuItem, int quantity)
	{
		super();
		this.orderId = orderId;
		this.menuItem = menuItem;
		this.quantity = quantity;
	}
	
	public void createOrderitem(int orderId, MenuItem menuItem, int quantity)
	{
		
	}
	
	public void updateOrderItem(int orderId, MenuItem menuItem, int quantity)
	{
		
	}
	
	public void deleteOrderItem(int orderId, int menuItemId)
	{
		
	}
	
	public ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId)
	{
		return null;
	}

}
