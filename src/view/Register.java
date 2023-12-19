package view;

import controller.UserController;
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
        grid.add(registerButton, 0, 4);
		
        // Action Text
        Text actionTarget = new Text();
        grid.add(actionTarget, 0, 5, 2, 1);
        
        //Login Button
        Button loginButton = new Button("Go To Login Screen");
        grid.add(loginButton, 0, 6, 2, 1);
        
        registerButton.setOnAction(e ->
        {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            
            //Checking password equals to confirmPassword
            if(password.equals(confirmPassword))
            {            	
            	
            	//Checking to UserController
            	String res = controller.createUser("Customer", username, email, password);  
            	if(res.contains("SUCCESS"))
    			{
            		new Login().show();
            		this.close();
    			}
            	else 
        		{
        			actionTarget.setText(res);
        		}
            }
            else
            {            	
            	actionTarget.setText("Password don't Match");
            }
            
        });
		
        loginButton.setOnAction(e ->
        {
            new Login().show();
            this.close();
        });

        Scene scene = new Scene(grid, 576, 324);
        setScene(scene);
    }
}
