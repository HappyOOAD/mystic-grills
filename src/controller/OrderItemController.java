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
		// Add Order Sequence Diagram
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		// Update Order Sequence Diagram
		// RULES: (From Sequence)
		// - No Validation
		// - When Add New Order Item to Order
		
		if(menuItem == null) return "ERROR: Menu Item must be chosen";
		if(quantity < 1) return "ERROR: Quantity cannot below 1";
		
		OrderItem.createOrderitem(orderId, menuItem, quantity);
		return "SUCCESS: Successfully Create an Order Item";
	}
	
	public String updateOrderItem(int orderId, MenuItems menuItem, int quantity)
	{
		// Update Order Sequence Diagram
		// RULES: (From Sequence)
		// - No Validation
		// - When Update Existing Order Item

		if(menuItem == null) return "ERROR: Menu Item must be chosen";
		if(quantity < 1) return "ERROR: Quantity cannot below 1";
		
		OrderItem.updateOrderItem(orderId, menuItem, quantity);
		return "SUCCESS: Successfully Update an Order Item";
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
