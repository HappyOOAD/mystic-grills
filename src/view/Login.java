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

public class Login extends Stage
{
	UserController controller = new UserController();
	
	public Login() 
	{
		setTitle("Mystic Grills - Login");
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
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
        grid.add(loginButton, 0, 2);
		
        // Action Text
        Text actionTarget = new Text();
        grid.add(actionTarget, 0, 3, 2, 1);

        //Register Button
        Button RegisterButton = new Button("Go To Register Screen");
        grid.add(RegisterButton, 0, 6, 2, 1);
        

        loginButton.setOnAction(e ->
        {
            String email = emailField.getText();
            String password = passwordField.getText();
            
            String res = controller.authenticateUser(email, password);
            if(res.contains("SUCCESS")) this.close();
            else actionTarget.setText(res);
        });

        RegisterButton.setOnAction(e ->
        {
            new Register().show();
            this.close();
        });

        Scene scene = new Scene(grid, 576, 324);
        setScene(scene);
    }
}
