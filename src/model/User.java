package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	// CONSTRUCTOR
	public User(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		super();
		this.userId = userId;
		this.userRole = userRole;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
		
	
	// CRUD & Authenticate
	
	public static void createUser(String userRole, String userName, String userEmail, String userPassword)
	{
		String query = "INSERT INTO users(userId, userRole, userName, userEmail, userPassword) VALUES (0,'Customer','" +
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
	
	public static User getUserById(int userId)
	{
		User user = null;
		String query = "SELECT * FROM users WHERE userId = " + userId + ";";
		
		try (Connection connection = Connect.getInstance().getConnection())
		{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next())
			{
				
				int id = resultSet.getInt("userId");
				String role = resultSet.getString("UserRole");
				String name = resultSet.getString("UserName");
				String email = resultSet.getString("UserEmail");
				String password = resultSet.getString("UserPassword");
				user = new User(id, role, name, email, password);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		return user;
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
	
	public static void updateUser(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		String query = "UPDATE users SET "
				+ "userRole = '" + userRole + "', "
				+ "userName = '" + userName + "', "
				+ "userEmail = '" + userEmail + "', "
				+ "userPassword = '" + userPassword + "' "
				+ "WHERE "
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
	
	public static User authenticateUser(String userEmail, String userPassword)
	{
		User user = null;
		
		String query = "SELECT * FROM users WHERE userEmail = ? AND userPassword = ?;";
		
		try
		{
			Connection connection = Connect.getInstance().getConnection();
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setString(1, userEmail);
			prep.setString(2, userPassword);
			ResultSet resultSet = prep.executeQuery();
			
			if(resultSet.next())
			{
				
				int id = resultSet.getInt("userId");
				String role = resultSet.getString("UserRole");
				String name = resultSet.getString("UserName");
				String email = resultSet.getString("UserEmail");
				String password = resultSet.getString("UserPassword");
				user = new User(id, role, name, email, password);
			}
			
		} catch (SQLException e)
    	{
			// TODO: handle exception
			e.printStackTrace();
		}
		return user;
	}
	
	
	// GETTERS & SETTERS

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getUserRole()
	{
		return userRole;
	}

	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}

	public String getUserPassword()
	{
		return userPassword;
	}

	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}
}