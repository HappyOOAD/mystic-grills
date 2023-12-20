package view;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPanel extends Stage
{
	UserController controller = new UserController();
	VBox container = new VBox(20);
	GridPane grid = new GridPane();
	
	public LoginPanel() 
	{
		setTitle("Mystic Grills");
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        container.setAlignment(Pos.CENTER);
        
        
        // Title Text
        Text title = new Text("Login");
        title.setFont(Font.font(null, FontWeight.BOLD, 18));

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
		
        // Action Text
        Text actionTarget = new Text();
        actionTarget.setFill(Color.RED);

        //Register Button
        Text registerRedirect = new Text("Doesn't have account? Register here");
        registerRedirect.setFill(Color.BLUE);

        loginButton.setOnAction(e ->
        {
            String email = emailField.getText();
            String password = passwordField.getText();
            
            //Checking to UserController
            String res = controller.authenticateUser(email, password);
            if(res.contains("SUCCESS")) this.close();
            else actionTarget.setText(res);
        });

        registerRedirect.setOnMouseClicked(e ->
        {
            new RegisterPanel().show();
            this.close();
        });
        
        container.getChildren().addAll(title, grid, loginButton, actionTarget, registerRedirect);
        Scene scene = new Scene(container, 576, 324);
        setScene(scene);
    }
}
