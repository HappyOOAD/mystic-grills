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

public class RegisterPanel extends Stage
{	
	UserController controller = new UserController();
	VBox container= new VBox(20);
	GridPane grid = new GridPane();
	
	public RegisterPanel() 
	{
        setTitle("Mystic Grills - Register");
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        container.setAlignment(Pos.CENTER);

        // Title Text
        Text title = new Text("Register");
        title.setFont(Font.font(null, FontWeight.BOLD, 18));
        
        // Username Field
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        // Email Field
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 1);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 1);

        // Password Field
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Confirmation Password Field
        Label confirmPasswordLabel = new Label("Confirm Password:");
        grid.add(confirmPasswordLabel, 0, 3);
        PasswordField confirmPasswordField = new PasswordField();
        grid.add(confirmPasswordField, 1, 3);

        // Register Button
        Button registerButton = new Button("Register");
        
        // Action Text
        Text actionTarget = new Text();
        actionTarget.setFill(Color.RED);
        
        //Login Button
        Text loginRedirect = new Text("Already have an account? Login here");
        loginRedirect.setFill(Color.BLUE);
        
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
            		new LoginPanel().show();
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
		
        loginRedirect.setOnMouseClicked(e ->
        {
            new LoginPanel().show();
            this.close();
        });

        container.getChildren().addAll(title, grid, registerButton, actionTarget, loginRedirect);
        Scene scene = new Scene( container, 576, 324);
        setScene(scene);
    }
}
