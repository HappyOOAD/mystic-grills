package model;

import java.util.ArrayList;

public class User {

	private int userId;
	private String userRole;
	private String userName;
	private String userEmail;
	private String userPassword;

	public User(int userId, String userRole, String userName, String userEmail, String userPassword) {
		super();
		this.userId = userId;
		this.userRole = userRole;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	
	public static void deleteUser(int userId)
	{
		
	}
	
	
	public static User getUserById(int userId)
	{
		return null;
	}
	
	public static void createUser(String userRole, String userName, String userEmail, String userPassword)
	{
		
	}
	
	public static void updateUser(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		
	}
	
	public static ArrayList<User> getAllUsers()
	{
		return null;
	}
	
	public boolean authenticateUser(String userName, String userEmail)
	{
		return false;
	}
	
	
}
