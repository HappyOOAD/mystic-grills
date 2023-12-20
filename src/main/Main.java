package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LandingPanel;

public class Main extends Application
{
    public static void main(String[] args)
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage = new LandingPanel();
        primaryStage.show();
	}
}
