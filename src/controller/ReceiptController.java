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
		
		if(order == null) return "Order is Empty"; //Cannnot be empty
		if(receiptPaymentType.isBlank()) return "Receipt Payment Type is Empty"; //Cannnot be empty
		if(receiptPaymentAmount <= 0.0) return "Receipt Payment Amount must greater than 0"; //Must Be Greater than 0
		if(receiptPaymentDate == null) return "Receipt Payment Date is Empty"; //Cannnot be empty
		Receipt.createReceipt(order, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate); //Create Receipt
		return "Create Receipt Success";
	}
	
	public void updateReceipt(int orderId, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		if(orderId <= 0) return;  //Must Be Greater than 0
		if(receiptPaymentType.isBlank()) return; //Cannnot be empty
		if(receiptPaymentAmount <= 0.0) return; //Must Be Greater than 0
		if(receiptPaymentDate == null) return; //Cannnot be empty
		Receipt.updateReceipt(orderId, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate); //Updating Receipt
	}
	
	public void deleteReceipt(int orderId)
	{
		if(orderId <= 0) return; //Check if the ID below 0, then orderId is not valid
		Receipt.deleteReceipt(orderId); //Deleting Receipt
	}
	
	public Receipt getReceiptById(int receiptId)
	{
		//Getting Receipt by ID
		
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
