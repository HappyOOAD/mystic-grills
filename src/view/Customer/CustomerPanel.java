package view.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import controller.MenuItemController;
import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import model.MenuItems;
import model.Order;
import model.OrderItem;
import model.User;

public class CustomerPanel extends Stage
{
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    
    private Stage stage;
    private ArrayList<MenuItem> menuItems;
    private Order currentOrder;

	public CustomerPanel(){
		this.menuItems = menuItems;

		menuBar = new MenuBar();
        Menu addMenu = new Menu("Add");
        MenuItem addMenuItem= new MenuItem("Show");
        
        addMenu.getItems().addAll(addMenuItem);
        menuBar.getMenus().addAll(addMenu);
        
        addMenuItem.setOnAction(e -> createMenuItemsPane());    
        
        Menu updateMenu = new Menu("Update");
        MenuItem updateMenuItem= new MenuItem("Show");
        
        updateMenu.getItems().addAll(updateMenuItem);
        menuBar.getMenus().addAll(updateMenu);
        
        addMenuItem.setOnAction(e -> createMenuItemsPane());
        updateMenuItem.setOnAction(e -> createOrderedMenuItemsPane()); 
        
        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
		
	}

	private Object createOrderedMenuItemsPane() {
		// TODO Auto-generated method stub
		return null;
	}

	private GridPane createMenuItemsPane() {
		contentArea.getChildren().clear();
    	TableView<MenuItems>menuItemTable = createMenuItemTableView();
		contentArea.getChildren().add(menuItemTable);
		AtomicInteger idSelectedMenu = new AtomicInteger();
		MenuItems menu;
		User user;
		
		menuItemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	idSelectedMenu.set(newSelection.getMenuItemId());
            }
        });
		int selectedMenuId = idSelectedMenu.get();
		menu = MenuItems.getMenuItemById(selectedMenuId);
		
		GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label quantityLabel = new Label("Quantity:");
        TextField quantityTextField = new TextField();

        Button addToOrderButton = new Button("Add to Order");
        addToOrderButton.setOnAction(event -> addToOrder(menu, quantityTextField.getText()));

        Button submitOrderButton = new Button("Submit Order");
        submitOrderButton.setOnAction(event -> submitOrder());

        gridPane.add(quantityLabel, 0, 1);
        gridPane.add(quantityTextField, 1, 1);
        gridPane.add(addToOrderButton, 0, 2);
        gridPane.add(submitOrderButton, 1, 2);

        return gridPane;
	}
	
	private void addToOrder(MenuItems menuItem, String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);

            // Validation checks
            if (menuItem != null && quantity > 0) {
//            	menuItems.add() masukkin menu yang dipilih nya ke menuItems 
            } else {
                System.out.println("Invalid input. Please check your input values.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a valid number.");
        }
    }
	
	private void submitOrder() {
		//
		Date currentDate = new Date();
		// kenapa order item yang di masukkin ke arraylist?
		Order.createOrder(1, menuItems, currentDate);
    }

	private TableView<MenuItems> createMenuItemTableView() {
    	TableView<MenuItems> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<MenuItems, Integer> menuItemIdColumn = new TableColumn<>("ID");
        menuItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        menuItemIdColumn.setPrefWidth(150);
    	    
        TableColumn<MenuItems, String> menuItemNameColumn = new TableColumn<>("Name");
        menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        menuItemNameColumn.setPrefWidth(150);
        
        TableColumn<MenuItems, String> menuItemDescriptionColumn = new TableColumn<>("Description");
        menuItemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemDescription"));
        menuItemDescriptionColumn.setPrefWidth(150);
        
        TableColumn<MenuItems, Integer> menuItemPriceColumn = new TableColumn<>("Price");
        menuItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));
        menuItemPriceColumn.setPrefWidth(150);       
        
        tableView.getColumns().addAll(menuItemIdColumn, menuItemNameColumn, menuItemDescriptionColumn, menuItemPriceColumn);
        tableView.setItems(FXCollections.observableArrayList(MenuItemController.getAllMenuItem()));
        
        return tableView;
    }

}
