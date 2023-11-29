package main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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



// --- Common Java Main Class ---

public class Main
{
	
	public static void main(String[] args)
	{
//		// --- Create User Test ---
//		User.createUser(" ", "Patrick", "p@gmail.com", "p1234");
//		System.out.println("[LOG]: Success Insert User");
//
//		// --- Update User Test ---
//		User.updateUser(1, "Customer", "Christiansen", "c@gmail.com", "c1234");
//		System.out.println("[LOG]: Success Update User");
//	
//		// --- Delete User Test ---
//		User.deleteUser(2);
//		System.out.println("[LOG]: Success Delete User");
//	
		// --- Get All Users Test ---
		ArrayList<User> users = User.getAllUsers();
		
		for (User user : users)
		{
			System.out.println(user.getUserName());
		}
		System.out.println("[LOG]: Success Get All Users");
//
//		User user = User.getUserById(1);
//		
//		if(user != null) System.out.println(user.getUserName());
//		else System.out.println("not found");
		
//		User auth = User.authenticateUser("p@gmail.com", "p234");
//		
//		if(auth != null)
//		{
//			System.out.println("Success");
//		}
//		else
//		{
//			System.out.println("Failed");
//		}
	}
}