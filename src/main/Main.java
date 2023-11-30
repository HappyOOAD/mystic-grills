package main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MenuItem;
import model.User;



// --- JavaFX Main Class ---

//public class Main extends Application
//{
//	private VBox selectorParent = new VBox(25);
//	private Scene selectorScene = new Scene(selectorParent, 300, 150);
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//	
//	public void createSelectorScreen() {
//		Button newClientBtn = new Button("New JNPay Client");
//		newClientBtn.setOnAction(e -> {
//		});
//		
//		Button openControlsBtn = new Button("Open JNPay Operations Control Panel");
//		openControlsBtn.setOnAction(e -> {
//		});
//		
//		selectorParent.setAlignment(Pos.CENTER);
//		selectorParent.getChildren().addAll(newClientBtn, openControlsBtn);
//	}
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		createSelectorScreen();
//		primaryStage.setScene(selectorScene);
//		primaryStage.show();
//	}
//}



// --- Testing Java Main Class ---

public class Main
{
	
	public static void main(String[] args)
	{
//		// --- Create User Test ---
//		User.createUser(" ", "Maverick", "mv@gmail.com", "mv1234");
//		System.out.println("[LOG]: Success Insert User");

//		// --- Update User Test ---
//		User.updateUser(2, "Customer", "Christiansen", "c@gmail.com", "c1234");
//		System.out.println("[LOG]: Success Update User");

//		// --- Delete User Test ---
//		User.deleteUser(1);
//		System.out.println("[LOG]: Success Delete User");

//		// --- Get All Users Test ---
//		ArrayList<User> users = User.getAllUsers();
//		
//		for (User user : users)
//		{
//			System.out.println(user.getUserName());
//		}
//		System.out.println("[LOG]: Success Get All Users");

//		// --- Get User By Id Test ---
//		User user = User.getUserById(4);
//		
//		if(user != null) System.out.println(user.getUserName());
//		else System.out.println("User not found");
//		System.out.println("[LOG]: Success Get User By Id");

		// --- Authenticate User Test ---
		User auth = User.authenticateUser("mv@gmail.com", "mv1234");
		
		if(auth != null)
		{
			System.out.println("Success");
		}
		else
		{
			System.out.println("Failed");
		}
		System.out.println("[LOG]: Success Authenticate User");

		
		
//		// --- Create MenuItem Test ---
//		MenuItem.createMenuItem("Spageti", "Welp", 25000);
//		System.out.println("[LOG]: Success Create New MenuItem");
		
//		// --- Update MenuItem Test ---
//		MenuItem.updateMenuItem(2, "Fried Noddle", "Sedap", 24000);
//		System.out.println("[LOG]: Success Update New MenuItem");
		
//		// --- Get All MenuItem Test ---
//		ArrayList<MenuItem> menus = MenuItem.getAllMenuItems();
//		for (MenuItem menuItem : menus)
//		{
//			System.out.println(menuItem.getMenuItemName());
//		}
//		System.out.println("[LOG]: Success Get All MenuItem");
		
//		// --- Get MenuItem By Id ---
//		MenuItem menu = MenuItem.getMenuItemById(1);
//		System.out.println(menu.getMenuItemName());
//		System.out.println("[LOG]: Success get MenuItem By Id");
		
//		// --- Delete MenuItem Test ---
//		MenuItem.deleteMenuItem(4);
//		System.out.println("[LOG]: Success Delete menuItem");
				
	}
}