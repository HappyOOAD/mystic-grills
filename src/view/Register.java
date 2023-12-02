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

public class Register extends Stage
{	
	UserController controller = new UserController();
	
	public Register() 
	{
        setTitle("Mystic Grills - Register");
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Username
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        // Email
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 1);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 1);

        // Password
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Confirm Password
        Label confirmPasswordLabel = new Label("Confirm Password:");
        grid.add(confirmPasswordLabel, 0, 3);
        PasswordField confirmPasswordField = new PasswordField();
        grid.add(confirmPasswordField, 1, 3);

        // Register Button
        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 4);
        
        // Action Text
        Text actionTarget = new Text();
        grid.add(actionTarget, 1, 5);
        registerButton.setOnAction(e ->
        {
        	String username = usernameField.getText();
        	String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            if(password.equals(confirmPassword))
            {            	
            	String res = controller.createUser("Customer", username, email, password);  
            	actionTarget.setText(res);
            }
            else
            {            	
            	actionTarget.setText("Password don't Match");
            }
            
        });

        Scene scene = new Scene(grid, 400, 300);
        setScene(scene);
    }
}
