package controller;

import model.User;

public class UserController
{
	private User currentUser;
	
	public UserController()
	{
		super();
	}
	
	public String createUser(String userRole, String userName, String userEmail, String userPassword)
	{
		if(userRole.equals("")) return "userRole empty";
		if(userName.equals("")) return "userName empty";
		if(userEmail.equals("")) return "userEmail empty";
		if(userPassword.equals("")) return "userPassword empty";
		
		User.createUser(userRole, userName, userEmail, userPassword);
		return "Success";
	}
	
	public User getUserById(int userId)
	{
		return User.getUserById(userId);
	}
	
	public String authenticateUser(String userEmail, String userPassword)
	{
		User user = User.authenticateUser(userEmail, userPassword);
		if(user != null)
		{
			currentUser = user;
			return "Login Success";
		}
		else
		{
			return "Invalid Credentials";
		}
	}
	
	// GETTERS SETTERS
	
	public User getCurrentUser()
	{
		return currentUser;
	}

	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}
		
}
