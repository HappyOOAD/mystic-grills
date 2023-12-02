package view;

import controller.UserController;
import javafx.geometry.Insets;
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
        grid.add(actionTarget, 1, 3);

        loginButton.setOnAction(e ->
        {
        	String email = emailField.getText();
            String password = passwordField.getText();
            
            String res = controller.authenticateUser(email, password);
            if(res.equals("Login Success")) this.close();
            else actionTarget.setText(res);
        });

        Scene scene = new Scene(grid, 400, 200);
        setScene(scene);
    }
}
