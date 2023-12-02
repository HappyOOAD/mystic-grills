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

public class Register extends Application{
	
	UserController controller = new UserController();
	
	public void start(Stage primaryStage) {
        primaryStage.setTitle("Mystic Grills - User Registration");

        GridPane grid = new GridPane();
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
        grid.add(actionTarget, 1, 6);
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
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private boolean validateInput(String username, String email, String password, String confirmPassword) {
        // Perform input validation based on the specified rules
        return !username.isEmpty() && !email.isEmpty() && email.matches("^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$")
                && !password.isEmpty() && password.length() >= 6 && password.equals(confirmPassword);
    }

    public Register(Stage args)
    {
        start(args);
    }
}
