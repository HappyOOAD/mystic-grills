package view.Admin;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

public class UserManagement extends Stage {
//public class UserManagement extends Application {
	
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
        	openNewPage1();
        });

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
    }
    
    private TextField idInput;
    private TextField nameInput;
    private TextField roleInput;
    private TextField emailInput;
    private TextField passwordInput;
    
    private void openNewPage() {
    	contentArea.getChildren().clear();
        
        TableView<User> accountsTable = createAccountsTableView();
        contentArea.getChildren().add(accountsTable);
        
        setupTableSelectionListener();
        GridPane form = createUserForm(accountsTable);
        
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
        
    }
    
    private void setupTableSelectionListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idInput.setText(newSelection.getUserId()+"");
                nameInput.setText(newSelection.getUserName());
                roleInput.setText(newSelection.getUserRole());
                emailInput.setText(newSelection.getUserEmail());
                passwordInput.setText(newSelection.getUserPassword());
                
            }
        });
    }
    
    private void setupTableSelectionListener1() {
        MenuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idInput_menu.setText(newSelection.getMenuItemId()+"");
                nameInput_menu.setText(newSelection.getMenuItemName());
                itemDesc_menu.setText(newSelection.getMenuItemDescription());
                itemPrice_menu.setText(String.valueOf(newSelection.getMenuItemPrice()));
                
            }
        });
    }
    
    private void openNewPage1() {
    	contentArea.getChildren().clear();
    	
    	MenuTable = createAccountsTableView1();
		contentArea.getChildren().add(MenuTable);
		
		setupTableSelectionListener1();
        GridPane form = createUserForm1(MenuTable);
        
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
        
    }
    
    TableView<User> tableView;
    TableView<model.MenuItem> MenuTable;
    
    private TableView<User> createAccountsTableView() {
    	tableView = new TableView<>();
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
    
    
	private void loadProductData() {

		// TODO Auto-generated method stub
		ArrayList<User> users = User.getAllUsers();
		tableView.getItems().setAll(users);
		
	}
	
	private void loadProductData1() {

		// TODO Auto-generated method stub
		ArrayList<model.MenuItem> menu = model.MenuItem.getAllMenuItems();
		MenuTable.getItems().setAll(menu);
		
	}
	
    
    private GridPane createUserForm(TableView<User> table) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idInput = new TextField();
	    nameInput = new TextField();
	    roleInput = new TextField();
	    emailInput = new TextField();
	    passwordInput = new TextField();
        
        
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");

        form.add(new Label("User ID:"), 0, 0);
        idInput.setDisable(true);
        form.add(idInput, 1, 0);
        form.add(new Label("Username:"), 0, 1);
        form.add(nameInput, 1, 1);
        form.add(new Label("Role:"), 0, 2);
        form.add(roleInput, 1, 2);
        form.add(new Label("Email:"), 0, 3);
        form.add(emailInput, 1, 3);
        form.add(new Label("Password:"), 0, 4);
        form.add(passwordInput, 1, 4);
        form.add(addButton, 0, 5);
        form.add(deleteButton, 1, 5);
        form.add(updateButton, 2, 5);
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String username = nameInput.getText();
				String role= roleInput.getText();
				String email= emailInput.getText();
				String password= passwordInput.getText();
				User.createUser(role, username, email, password);
				loadProductData();
			}
		});
        
        updateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int id = Integer.parseInt(idInput.getText());
				String username = nameInput.getText();
				String role= roleInput.getText();
				String email= emailInput.getText();
				String password= passwordInput.getText();
				User.updateUser(id, role, username, email, password);
				loadProductData1();
			}
		});
        
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				User selectedProduct = table.getSelectionModel().getSelectedItem();
				if(selectedProduct!=null) {
					User.deleteUser(selectedProduct.getUserId());
					loadProductData();
				}
			}
		});
        
        
        return form;
    }
    
    
    private TextField idInput_menu;
    private TextField nameInput_menu;
    private TextField itemDesc_menu;
    private TextField itemPrice_menu;
    
    private GridPane createUserForm1(TableView<model.MenuItem> menuTable2) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idInput_menu = new TextField();
        nameInput_menu = new TextField();
        itemDesc_menu = new TextField();
        itemPrice_menu = new TextField();
        
        
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");

        form.add(new Label("ID:"), 0, 0);
        idInput_menu.setDisable(true);
        form.add(idInput_menu, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameInput_menu, 1, 1);
        form.add(new Label("Desc:"), 0, 2);
        form.add(itemDesc_menu, 1, 2);
        form.add(new Label("Price:"), 0, 3);
        form.add(itemPrice_menu, 1, 3);
        form.add(addButton, 0, 5);
        form.add(deleteButton, 1, 5);
        form.add(updateButton, 2, 5);
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				String name = nameInput_menu.getText();
				String desc= itemDesc_menu.getText();
				double price= Double.parseDouble(itemPrice_menu.getText());
				model.MenuItem.createMenuItem(name, desc, price);
				loadProductData1();
			}
		});
        
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int id = Integer.parseInt(idInput_menu.getText());
//				int id = 2;
				String name = nameInput_menu.getText();
				String desc= itemDesc_menu.getText();
				double price= Double.parseDouble(itemPrice_menu.getText());
				model.MenuItem.updateMenuItem(id, name, desc, price);
				loadProductData1();
			}
		});
        
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				model.MenuItem selectedProduct = menuTable2.getSelectionModel().getSelectedItem();
				if(selectedProduct!=null) {
					model.MenuItem.deleteMenuItem(selectedProduct.getMenuItemId());
					loadProductData1();
				}
			}
		});
        
        
        return form;
    }
    
    private TableView<model.MenuItem> createAccountsTableView1() {
    	TableView<model.MenuItem> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	    
        TableColumn<model.MenuItem, String> menuItemName = new TableColumn<>("Name");
        menuItemName.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        menuItemName.setPrefWidth(150);
        
        TableColumn<model.MenuItem, String> menuItemDescription = new TableColumn<>("Description");
        menuItemDescription.setCellValueFactory(new PropertyValueFactory<>("menuItemDescription"));
        menuItemDescription.setPrefWidth(150);
        
        TableColumn<model.MenuItem, Integer> menuItemPrice = new TableColumn<>("Price");
        menuItemPrice.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));
        menuItemPrice.setPrefWidth(150);
       
            
        
        tableView.getColumns().addAll(menuItemName, menuItemDescription, menuItemPrice);
        tableView.setItems(FXCollections.observableArrayList(model.MenuItem.getAllMenuItems()));
        
        return tableView;
    }
    
//    public static void main(String[] args) {
//        // Create an instance of your custom stage
//    	launch(args);
//    }
}
