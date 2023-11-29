package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	private VBox selectorParent = new VBox(25);
	private Scene selectorScene = new Scene(selectorParent, 300, 150);
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void createSelectorScreen() {
		Button newClientBtn = new Button("New JNPay Client");
		newClientBtn.setOnAction(e -> {
		});
		
		Button openControlsBtn = new Button("Open JNPay Operations Control Panel");
		openControlsBtn.setOnAction(e -> {
		});
		
		selectorParent.setAlignment(Pos.CENTER);
		selectorParent.getChildren().addAll(newClientBtn, openControlsBtn);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		createSelectorScreen();
		primaryStage.setScene(selectorScene);
		primaryStage.show();
	}

}
