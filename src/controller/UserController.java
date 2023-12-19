package controller;

import java.util.ArrayList;

import model.User;
import view.Admin.AdminPanel;
import view.Cashier.CashierPanel;
import view.Chef.ChefPanel;
import view.Customer.CustomerPanel;
import view.Waiter.WaiterPanel;

public class UserController
{
	private User currentUser;
	
	public UserController()
	{
		super();
	}
	
	public String createUser(String userRole, String userName, String userEmail, String userPassword)
	{	
		// Create Account Sequence Diagram [GUEST]
		// RULES: (From Sequence)
		// - Return Validation Error
		// - Return [Success / Failed / Error] Message
		
		// USERNAME
		if(userName.isBlank()) return "ERROR: Username cannot be empty";
		
		// EMAIL
		if(userEmail.isBlank()) return "ERROR: Email cannot be empty";
		if(User.emailIsExist(userEmail)) return "ERROR: Email already taken";
		
		// PASSWORD
		if(userPassword.isBlank()) return "ERROR: Password cannot be empty";
		if(userPassword.length() < 6) return "ERROR: Password must at least be 6 characters long";
		
		//CONFIRM PASSWORD???
		
		String res = User.createUser(userRole, userName, userEmail, userPassword);
		if(res.equals("success")) return "SUCCESS: Success Create A New User";
		else return "FAILED: Failed create a new user";
	}
	
	public static void updateUser(int userId, String userRole, String userName, String userEmail, String userPassword)
	{
		// Update Role Sequence Diagram [ADMIN]
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
		
		User.updateUser(userId, userRole, userName, userEmail, userPassword);
	}
	
	public static void deleteUser(int userId)
	{
		// Delete User Sequence Diagram [ADMIN]
		// RULES: (From Sequence)
		// - No Validation
		// - No Return Response
				
		User.deleteUser(userId);
	}
	
	public User getUserById(int userId)
	{
		//Get User by ID
		
		return User.getUserById(userId);
	}
	
	public String authenticateUser(String userEmail, String userPassword)
	{
		if(userEmail.equalsIgnoreCase("")) return "ERROR: Email cannot be empty";
		if(User.emailIsExist(userEmail) == false) return "ERROR: Email not found";
		
		if(userPassword.equalsIgnoreCase("")) return "ERROR: Password cannot be empty";
		
		User user = User.authenticateUser(userEmail, userPassword);
		if(user != null)
		{
			String role = user.getUserRole();
			currentUser = user;
			switch (role)
			{
				case "Admin": new AdminPanel().show(); break; //If user Role is Admin, go to admin Panel
				case "Cashier": new CashierPanel().show(); break; //If user Role is Cashier, go to Cashier Panel
				case "Chef": new ChefPanel().show(); break; //If user Role is Chef, go to Chef Panel
				case "Waiter": new WaiterPanel().show(); break; //If user Role is Waiter, go to Waiter Panel
				case "Customer": new CustomerPanel(user).show(); break; //If user Role is Customer, go to Customer Panel
				default: return "FAILED: Role Undefined";
			}
			return "SUCCESS: Login Success";
		}
		else
		{
			return "FAILED: Login Failed";
		}
	}
	
	public static ArrayList<User> getAllUsers()
	{
		// View User Sequence Diagram [ADMIN]
		// RULES: (From Sequence)
		// - Return List<User>
		
		return User.getAllUsers();
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
