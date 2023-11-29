package model;

import java.util.ArrayList;
import java.util.Date;

public class Receipt
{
	private int receiptId;
	private Order receiptOrder;
	private int receiptPaymentAmount;
	private Date receiptPaymentDate;
	private String receiptPaymentType;
	
	// CONSTRUCTOR
	public Receipt(int receiptId, Order receiptOrder, int receiptPaymentAmount, Date receiptPaymentDate,
			String receiptPaymentType)
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