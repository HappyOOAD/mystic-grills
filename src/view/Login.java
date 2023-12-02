package view;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

public class Login extends Application
{
	UserController controller = new UserController();
	
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Mystic Grills - Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Email
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 0);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 0);

        // Password
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);
        
        // Action Text
        Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        loginButton.setOnAction(e ->
        {
        	String email = emailField.getText();
            String password = passwordField.getText();
            
            String res = controller.authenticateUser(email, password);
            actionTarget.setText(res);
        });

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private boolean validateInput(String email, String password) {
        // Perform input validation based on the specified rules
        // In a real application, you would check against a database
        // For simplicity, here we assume a hardcoded email and password
        return !email.isEmpty() && email.equals("user@example.com") &&
               !password.isEmpty() && password.equals("password123");
    }

    public Login(Stage args)
    {
        start(args);
    }
}
