package controller;

import java.util.ArrayList;

import model.MenuItems;
import model.OrderItem;

public class OrderItemController
{

	public OrderItemController()
	{
		// TODO Auto-generated constructor stub
	}

	
	public String createOrderItem(int orderId, MenuItems menuItem, int quantity)
	{
		if(menuItem1 == null) return "Menu Item must be chosen";
		if(quantity < 1) return "Quantity cannot below 1";
		
		OrderItem.createOrderitem(orderId, menuItem1, quantity);
		return "Successfully Create an Order Item";
	}
	
	public String updateOrderItem(int orderId, MenuItems menuItem, int quantity)
	{
		if(menuItem1 == null) return "Menu Item must be chosen";
		if(quantity < 1) return "Quantity cannot below 1";
		
		OrderItem.updateOrderItem(orderId, menuItem1, quantity);
		return "Successfully Update an Order Item";
	}
	
	public void deleteOrderItem(int orderId, MenuItems menuItem, int quantity)
	{
		OrderItem.deleteOrderItem(orderId, quantity);
	}
	
	public ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId)
	{
		return OrderItem.getAllOrderItemsByOrderId(orderId);
	}
}
