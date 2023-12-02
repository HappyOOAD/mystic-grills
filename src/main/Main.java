package main;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import view.Login;
import view.Register;



// --- JavaFX Main Class ---
public class Main extends Application
{
    public static void main(String[] args)
    {
//    	User.createUser("customer", "chris", "christiansen@gmail.com", "mynameisc");
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Login/Register App");

        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(20, 20, 20, 20));

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e -> {
            new Login(primaryStage);
        });

        registerButton.setOnAction(e -> {
//            System.out.println("Register button clicked!");
            // Add your registration logic here
            new Register(primaryStage);
        });

        hbox.getChildren().addAll(loginButton, registerButton);

        Scene scene = new Scene(hbox, 300, 100);
        primaryStage.setScene(scene);

        primaryStage.show();
	}
}



// --- Testing Java Main Class ---

//public class Main
//{	
//	public static void main(String[] args)
//	{
////		// --- Create User Test ---
////		User.createUser(" ", "Maverick", "mv@gmail.com", "mv1234");
////		System.out.println("[LOG]: Success Insert User");
//
////		// --- Update User Test ---
////		User.updateUser(2, "Customer", "Christiansen", "c@gmail.com", "c1234");
////		System.out.println("[LOG]: Success Update User");
//
////		// --- Delete User Test ---
////		User.deleteUser(1);
////		System.out.println("[LOG]: Success Delete User");
//
////		// --- Get All Users Test ---
////		ArrayList<User> users = User.getAllUsers();
////		
////		for (User user : users)
////		{
////			System.out.println(user.getUserName());
////		}
////		System.out.println("[LOG]: Success Get All Users");
//
////		// --- Get User By Id Test ---
////		User user = User.getUserById(4);
////		
////		if(user != null) System.out.println(user.getUserName());
////		else System.out.println("User not found");
////		System.out.println("[LOG]: Success Get User By Id");
//
////		// --- Authenticate User Test ---
////		User auth = User.authenticateUser("mv@gmail.com", "mv1234");
////		
////		if(auth != null)
////		{
////			System.out.println("Success");
////		}
////		else
////		{
////			System.out.println("Failed");
////		}
////		System.out.println("[LOG]: Success Authenticate User");
//
//		
//		
////		// --- Create MenuItem Test ---
////		MenuItem.createMenuItem("Burger", "Bun", 25000);
////		System.out.println("[LOG]: Success Create New MenuItem");
//		
////		// --- Update MenuItem Test ---
////		MenuItem.updateMenuItem(2, "Fried Noodle", "Indomie", 24000);
////		System.out.println("[LOG]: Success Update New MenuItem");
//		
////		// --- Get All MenuItem Test ---
////		ArrayList<MenuItem> menus = MenuItem.getAllMenuItems();
////		for (MenuItem menuItem : menus)
////		{
////			System.out.println(menuItem.getMenuItemName());
////		}
////		System.out.println("[LOG]: Success Get All MenuItem");
//		
////		// --- Get MenuItem By Id ---
////		MenuItem menu = MenuItem.getMenuItemById(1);
////		System.out.println(menu.getMenuItemName());
////		System.out.println("[LOG]: Success get MenuItem By Id");
//		
////		// --- Delete MenuItem Test ---
////		MenuItem.deleteMenuItem(2);
////		System.out.println("[LOG]: Success Delete menuItem");
//				
//	}
//}