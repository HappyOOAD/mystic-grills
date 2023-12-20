package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LandingPanel extends Stage
{
	public LandingPanel()
	{
		setTitle("Mystic Grills");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text title = new Text("Mystic Grills");
        title.setFont(Font.font(null, FontWeight.BOLD, 18));
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e ->
        {
            new LoginPanel().show();
            this.close();
        });

        registerButton.setOnAction(e ->
        {
            new RegisterPanel().show();
            this.close();
        });

        vbox.getChildren().addAll(title, loginButton, registerButton);

        Scene scene = new Scene(vbox, 324, 182);
        setScene(scene);

	}
}
