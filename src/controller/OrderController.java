package controller;

import java.util.ArrayList;
import java.util.Date;

import model.OrderItem;
import model.User;

public class OrderController
{

	public OrderController()
	{
		// TODO Auto-generated constructor stub
	}
	
	public String createOrder(User orderUser, ArrayList<OrderItem> orderItems, Date orderDate)
	{
		if(orderItems.size() == 0) return "Order Items Empty"; // Menu Item must be chosen
		
		
		return "Success Create An Order";
	}

}
