package main;


import java.util.ArrayList;
import java.util.Date;

import controller.OrderController;
import controller.ReceiptController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Order;
import model.Receipt;
import view.Login;


public class Main extends Application
{
    public static void main(String[] args)
    {
// 	Testing
//    	Order o = Order.getOrderById(1);
//    	System.out.println(o.getOrderTotal());
//    	ArrayList<OrderItem> o = OrderItem.getAllOrderItemsByOrderId(1);
//    	for (OrderItem orderItem : o) {
//			System.out.println(orderItem.getMenuItem().getMenuItemName());
//		}
//    	Receipt.getAllReceipts();
//    	ArrayList<User> x = User.getAllUsers();
//    	for (User temp : x) {
//			System.out.println(temp.getUserName());
//		}
//    	ArrayList<MenuItem> x = MenuItem.getAllMenuItems();
//    	for (MenuItem temp : x) {
//			System.out.println(temp.getMenuItemName());
//		}
    	
//    	MenuItems.updateMenuItem(1, "bebek goreng ayam sedap", "rasa pedas dan gurih", 29.10);
//    	ArrayList<Receipt> x = Receipt.getAllReceipts();
//    	for (Receipt temp : x) {
//			System.out.println(temp.getReceiptId());
//		}
    	Order x = OrderController.getOrderByOrderId(7);
		System.out.println(x.getOrderId());
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("Mystic Grills");

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e ->
        {
            new Login().show();
            primaryStage.close();
        });

        registerButton.setOnAction(e ->
        {
            new Login().show();
            primaryStage.close();
        });

        vbox.getChildren().addAll(loginButton, registerButton);

        Scene scene = new Scene(vbox, 300, 100);
        primaryStage.setScene(scene);

        primaryStage.show();
	}
}
