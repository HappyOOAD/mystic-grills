package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.Connect;

public class Receipt
{
	private int receiptId;
	private Order receiptOrder;
	private double receiptPaymentAmount;
	private Date receiptPaymentDate;
	private String receiptPaymentType;
	
	// CONSTRUCTOR
	public Receipt(int receiptId, Order receiptOrder, double receiptPaymentAmount, Date receiptPaymentDate, String receiptPaymentType)
	{
		super();
		this.receiptId = receiptId;
		this.receiptOrder = receiptOrder;
		this.receiptPaymentAmount = receiptPaymentAmount;
		this.receiptPaymentDate = receiptPaymentDate;
		this.receiptPaymentType = receiptPaymentType;
	}
	
	
	// CRUD
	
	public static void createReceipt(Order order, String receiptPaymentType, double receiptPaymentAmount, Date receiptPaymentDate)
	{
		Date date = new Date();
		String query = "INSERT INTO receipt (receiptId, orderId, receiptPaymentAmount, receiptPaymentDate, receiptPaymentType) VALUES (? ,? ,? ,? ,? );";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt   (1, 0);
			prep.setInt	  (2, order.getOrderId());
			prep.setDouble(3, receiptPaymentAmount);
			prep.setDate  (4, (java.sql.Date) date);
			prep.setString(5, receiptPaymentType);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static Receipt getReceiptById(int receiptId) // NEED JOIN??
	{
		Receipt receipt = null;
		String query = "SELECT * FROM receipts WHERE receiptId = ?;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, receiptId);
			ResultSet resultSet = prep.executeQuery();
			
			if(resultSet.next()) 
			{
				int id = resultSet.getInt("receiptId");
				int orderId = resultSet.getInt("orderId");
				Order order = Order.getOrderById(orderId);
				double amount = resultSet.getDouble("receiptPaymentAmount");
				Date date = resultSet.getDate("receiptPaymentDate");
				String type = resultSet.getString("receiptPaymentType");
				receipt = new Receipt(id, order, amount, date, type);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return receipt;
	}
	
	public static ArrayList<Receipt> getAllReceipts() // NEED JOIN
	{
		ArrayList<Receipt> receipts = new ArrayList<>();
		String query = "SELECT * FROM receipts;";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet resultSet = prep.executeQuery();
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("receiptId");
				int orderId = resultSet.getInt("orderId");
				Order order = Order.getOrderById(orderId);
				double amount = resultSet.getDouble("receiptPaymentAmount");
				Date date = resultSet.getDate("receiptPaymentDate");
				String type = resultSet.getString("receiptPaymentType");
				receipts.add(new Receipt(id, order, amount, date, type));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return receipts;
	}
	
	public static void updateReceipt(int orderId, String receiptPaymentType, double receiptPaymentAmount,  Date receiptPaymentDate)
	{
		String query = "UPDATE receipt SET receiptPaymentType = ?, receiptPaymentAmount = ?, receiptPaymentDate = ? WHERE orderId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setString(1, receiptPaymentType);
			prep.setDouble(2, receiptPaymentAmount);
			prep.setDate  (4, (java.sql.Date) receiptPaymentDate);
			prep.setInt   (5, orderId);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void deleteReceipt(int orderId)
	{
		String query = "DELETE FROM receipt WHERE orderId = ?;";
		  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, orderId);
			prep.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
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

	public double getReceiptPaymentAmount()
	{
		return receiptPaymentAmount;
	}

	public void setReceiptPaymentAmount(double receiptPaymentAmount)
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