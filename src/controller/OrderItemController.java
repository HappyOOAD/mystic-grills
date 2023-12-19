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
		// Add Order Sequence Diagram [CUSTOMER]
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		// Update Order Sequence Diagram [KITCHEN]
		// RULES: (From Sequence)
		// - No Validation
		// - When Add New Order Item to Order
		
		// Update Ordered Menu Item Sequence Diagram [CUSTOMER]
		// RULES: (From Sequence)
		// - No Validation
		// - When Add New Order Item to Order
		
		if(menuItem == null) return "ERROR: Menu Item must be chosen";
		if(quantity < 1) return "ERROR: Quantity cannot below 1";
		
		
		OrderItem existing = OrderItem.getOrderItemByOrderIdAndMenuItemId(orderId, menuItem.getMenuItemId());
		if(existing == null)
		{
			OrderItem.createOrderitem(orderId, menuItem, quantity);
			return "SUCCESS: Successfully Create an Order Item";			
		}
		else
		{
			OrderItem.updateOrderItem(orderId, menuItem, quantity + existing.getQuantity());
			return "SUCCESS: Successfully Add Quantity to the Order Item";
		}
		
	}
	
	public String updateOrderItem(int orderId, MenuItems menuItem, int quantity)
	{
		// Update Order Sequence Diagram [KITCHEN]
		// RULES: (From Sequence)
		// - No Validation
		// - When Update Existing Order Item

		// Update Ordered Sequence Diagram [CUSTOMER]
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
		//Delete OrderItem
		
		OrderItem.deleteOrderItem(orderId, quantity);
	}
	
	public ArrayList<OrderItem> getAllOrderItemsByOrderId(int orderId)
	{
		// View Ordered Menu Items Sequence Diagram [CUSTOMER]
		// RULES: (From Sequence)
		// - No Validation
		// - Return List<OrderItem>
		
		return OrderItem.getAllOrderItemsByOrderId(orderId);
	}
}
