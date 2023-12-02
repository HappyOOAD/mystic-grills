package controller;

import model.User;
import view.Admin.UserManagement;

public class UserController
{
	private User currentUser;
	
	public UserController()
	{
		super();
	}
	
	public String createUser(String userRole, String userName, String userEmail, String userPassword)
	{	
		if(userName.isBlank()) return "Username cannot be empty"; // Username cannot be empty
		
		if(userEmail.isBlank()) return "Email cannot be empty"; // Email cannot be empty
		if(User.emailIsUnique(userEmail) == false) return "Email already taken"; // Email must be unique
		
		if(userPassword.isBlank()) return "Password cannot be empty";
		if(userPassword.length() < 6) return "Password must at least be 6 characters long"; // Must at least be 6 characters long
		
		String res = User.createUser(userRole, userName, userEmail, userPassword);
		if(res.equals("success")) return "Success Create A New User";
		else return "Failed create a new user";
	}
	
	public String updateUser(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		if(userName.isBlank()) return "Username cannot be empty"; // Username cannot be empty
		
		if(userEmail.isBlank()) return "Email cannot be empty"; // Email cannot be empty
		
		if(userPassword.isBlank()) return "Password cannot be empty";
		if(userPassword.length() < 6) return "Password must at least be 6 characters long"; // Must at least be 6 characters long
		
		String res = User.updateUser(userId, userRole, userName, userEmail, userPassword);
		if(res.equals("success")) return "Success Create A New User";
		else return "Failed create a new user";
	}
	
	public void deleteUser(int userId)
	{
		User.deleteUser(userId);
	}
	
	public User getUserById(int userId)
	{
		return User.getUserById(userId);
	}
	
	public String authenticateUser(String userEmail, String userPassword)
	{
		if(userEmail.equalsIgnoreCase("")) return "Email Empty";
		if(User.emailIsExist(userEmail) == false) return "Email not found";
		
		if(userPassword.equalsIgnoreCase("")) return "Password Empty";
		
		User user = User.authenticateUser(userEmail, userPassword);
		if(user != null)
		{
			String role = user.getUserRole();
			currentUser = user;
			return "Login Success";
		}
		else
		{
			return "Login Failed";
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
