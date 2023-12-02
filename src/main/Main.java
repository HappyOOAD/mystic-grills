package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Order;
import model.OrderItem;
import view.Login;


public class Main extends Application
{
    public static void main(String[] args)
    {
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