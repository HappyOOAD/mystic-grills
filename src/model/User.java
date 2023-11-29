package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.Connect;

public class User
{
	private int userId;
	private String userRole;
	private String userName;
	private String userEmail;
	private String userPassword;

	public User(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		super();
		this.userId = userId;
		this.userRole = userRole;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	
	public static void deleteUser(int userId)
	{
		String query = "DELETE FROM users WHERE userId = " + userId + ";";
					  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static User getUserById(int userId)
	{
		User user = null;
		String query = "SELECT * FROM users WHERE userId = " + userId + ";";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
				int id = resultSet.getInt("userId");
				String role = resultSet.getString("UserRole");
				String name = resultSet.getString("UserName");
				String email = resultSet.getString("UserEmail");
				String password = resultSet.getString("UserPassword");
				user = new User(id, role, name, email, password);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return user;
	}
	
	public static void createUser(String userRole, String userName, String userEmail, String userPassword)
	{
		String query = "INSERT INTO users(userRole, userName, userEmail, userPassword) VALUES ('Customer','" +
			userName + "','" +
			userEmail + "','" +
			userPassword + "')";
				  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void updateUser(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		String query = "UPDATE users SET "
				+ "userRole = " + userRole + ", "
				+ "userName = " + userName + ", "
				+ "userEmail = " + userEmail + ", "
				+ "userPassword = " + userPassword + " "
				+ "WHERE"
				+ "userId = " + userId + ";";
				  
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<User> getAllUsers()
	{
		ArrayList<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("userId");
				String role = resultSet.getString("UserRole");
				String name = resultSet.getString("UserName");
				String email = resultSet.getString("UserEmail");
				String password = resultSet.getString("UserPassword");
				users.add(new User(id, role, name, email, password));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return users;
	}
	
	public User authenticateUser(String userEmail, String userPassword)
	{
		User user = null;
		String query = "SELECT * FROM users WHERE userEmail = " + userEmail + "AND userPassword " + userPassword + ";";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
				int id = resultSet.getInt("userId");
				String role = resultSet.getString("UserRole");
				String name = resultSet.getString("UserName");
				String email = resultSet.getString("UserEmail");
				String password = resultSet.getString("UserPassword");
				user = new User(id, role, name, email, password);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return user;
	}
	
	
}
