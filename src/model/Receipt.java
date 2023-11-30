package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.Connect;

public class Receipt
{
	private int receiptId;
	private Order receiptOrder;
	private int receiptPaymentAmount;
	private Date receiptPaymentDate;
	private String receiptPaymentType;
	
	// CONSTRUCTOR
	public Receipt(int receiptId, Order receiptOrder, int receiptPaymentAmount, Date receiptPaymentDate, String receiptPaymentType)
	{
		super();
		this.receiptId = receiptId;
		this.receiptOrder = receiptOrder;
		this.receiptPaymentAmount = receiptPaymentAmount;
		this.receiptPaymentDate = receiptPaymentDate;
		this.receiptPaymentType = receiptPaymentType;
	}
	
	
	// CRUD
	
	public static void createReceipt(Order order, String receiptPaymentType, int receiptPaymentAmount, Date receiptPaymentDate)
	{
		Date date = new Date();
		String query = "INSERT INTO receipt (receiptId, receiptOrderId, receiptPaymentAmount, receiptPaymentDate, receiptPaymentType) VALUES (? ,? ,? ,? ,? );";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt    (1, 0);
			prep.setInt	   (2, order.getOrderId());
			prep.setInt	   (3, receiptPaymentAmount);
			prep.setDate   (4, (java.sql.Date) date);
			prep.setString (5, receiptPaymentType);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static Receipt getReceiptById(int orderId)
	{
		return null;
	}
	
	public static ArrayList<Receipt> getAllReceipts()
	{
		return null;
	}
	
	public static void updateReceipt(int orderId, String receiptPaymentType, Date receiptPaymentDate)
	{
		
	}

	public static void deleteReceipt(int orderId)
	{
		
	}
	
	
	// GETTERS & SETTERS
	
	public int getReceiptId()
	{
		return receiptId;
	}

	public void setReceiptId(int receiptId)
	{
		this.receiptId = receiptId;
	}

	public Order getReceiptOrder()
	{
		return receiptOrder;
	}

	public void setReceiptOrder(Order receiptOrder)
	{
		this.receiptOrder = receiptOrder;
	}

	public int getReceiptPaymentAmount()
	{
		return receiptPaymentAmount;
	}

	public void setReceiptPaymentAmount(int receiptPaymentAmount)
	{
		this.receiptPaymentAmount = receiptPaymentAmount;
	}

	public Date getReceiptPaymentDate()
	{
		return receiptPaymentDate;
	}

	public void setReceiptPaymentDate(Date receiptPaymentDate)
	{
		this.receiptPaymentDate = receiptPaymentDate;
	}

	public String getReceiptPaymentType()
	{
		return receiptPaymentType;
	}

	public void setReceiptPaymentType(String receiptPaymentType)
	{
		this.receiptPaymentType = receiptPaymentType;
	}
}