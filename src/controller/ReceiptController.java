package controller;

import java.util.ArrayList;
import java.sql.Date;

import model.Order;
import model.Receipt;

public class ReceiptController
{

	public ReceiptController()
	{
		// TODO Auto-generated constructor stub
	}

	public String createReceipt(Order order, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		// Process Order Payment Sequence Diagram [CASHIER]
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		if(order == null) return "Order is Empty";
		if(receiptPaymentType.isBlank()) return "Receipt Payment Type is Empty";
		if(receiptPaymentAmount <= 0.0) return "Receipt Payment Amount must greater than 0";
		if(receiptPaymentDate == null) return "Receipt Payment Date is Empty";
		Receipt.createReceipt(order, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
		return "Create Receipt Success";
	}
	
	public void updateReceipt(int orderId, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		if(orderId <= 0) return;
		if(receiptPaymentType.isBlank()) return;
		if(receiptPaymentAmount <= 0.0) return;
		if(receiptPaymentDate == null) return;
		Receipt.updateReceipt(orderId, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
	}
	
	public void deleteReceipt(int orderId)
	{
		if(orderId <= 0) return;
		Receipt.deleteReceipt(orderId);
	}
	
	public Receipt getReceiptById(int receiptId)
	{
		return Receipt.getReceiptById(receiptId);
	}
	
	public ArrayList<Receipt> getAllReceipts()
	{
		// View Receipt Sequence Diagram [CASHIER]
		// Rules: (From Sequence)
		// - Return List<Receipt>
		
		return Receipt.getAllReceipts();
	}
	
	
}
