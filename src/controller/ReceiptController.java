package controller;

import java.util.Date;

import model.Order;
import model.Receipt;

public class ReceiptController
{

	public ReceiptController()
	{
		// TODO Auto-generated constructor stub
	}

	public void createReceipt(Order order, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		if(order == null) return;
		if(receiptPaymentType.isBlank()) return;
		Receipt.createReceipt(order, receiptPaymentType, receiptPaymentAmount, receiptPaymentDate);
	}
}
