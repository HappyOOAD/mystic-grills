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
	
	public Receipt(int receiptId, Order receiptOrder, int receiptPaymentAmount, Date receiptPaymentDate,
			String receiptPaymentType) {
		super();
		this.receiptId = receiptId;
		this.receiptOrder = receiptOrder;
		this.receiptPaymentAmount = receiptPaymentAmount;
		this.receiptPaymentDate = receiptPaymentDate;
		this.receiptPaymentType = receiptPaymentType;
	}
	
	public static void createReceipt(Order order, String receiptPaymentType, int receiptPaymentAmount, Date receiptPaymentDate)
	{
		
	}
	
	public static void updateReceipt(int orderId, String receiptPaymentType, Date receiptPaymentDate)
	{
		
	}

	public static void deleteReceipt(int orderId)
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
}
