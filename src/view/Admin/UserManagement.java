package view.Admin;

import java.util.ArrayList;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;

public class UserManagement extends Stage {
	
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    
    public UserManagement() {
    	super(StageStyle.DECORATED);

        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

        menuBar = new MenuBar();
        Menu UserManagement = new Menu("User Management");
        MenuItem UserManagementItem= new MenuItem("Show");

        UserManagement.getItems().addAll(UserManagementItem);
        menuBar.getMenus().addAll(UserManagement);
        
        Menu MenuItemManagement = new Menu("Menu Item Management");
        MenuItem MenuItemManagementItem = new MenuItem("Show");

        MenuItemManagement.getItems().addAll(MenuItemManagementItem);
        menuBar.getMenus().addAll(MenuItemManagement);
        
        root.setTop(menuBar);
        
        UserManagementItem.setOnAction(e -> {
        	System.out.println("Clicked");
        	openNewPage();
        });
        MenuItemManagementItem.setOnAction(e -> {
        	System.out.println("Clicked");
        	openNewPage();
        });

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
    }
    
    
    private void openNewPage() {
    	contentArea.getChildren().clear();
    	TableView<User>accountsTable = createAccountsTableView();
		contentArea.getChildren().add(accountsTable);
        
    }
    
    private TableView<User> createAccountsTableView() {
    	TableView<User> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	    
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        usernameColumn.setPrefWidth(150);
        
        TableColumn<User, String> userroleColumn = new TableColumn<>("Role");
        userroleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        userroleColumn.setPrefWidth(150);
        
        TableColumn<User, Integer> userId = new TableColumn<>("ID");
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userId.setPrefWidth(150);
        
        TableColumn<User, String> userEmailColumn = new TableColumn<>("Email");
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        userEmailColumn.setPrefWidth(150);
        
        TableColumn<User, String> userPasswordColumn = new TableColumn<>("Password");
        userPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
        userPasswordColumn.setPrefWidth(150);
            
        
        tableView.getColumns().addAll(usernameColumn, userroleColumn, userId, userEmailColumn, userPasswordColumn);
        tableView.setItems(FXCollections.observableArrayList(User.getAllUsers()));
        
        return tableView;
    }
}
