package view.Customer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
import model.MenuItems;
import model.Order;
import model.OrderItem;
import model.User;

public class CustomerPanel extends Stage
{
	// bisa view menu dan add order
	// bisa view menu yang uda di-order dan bisa update menu yang uda di-order
	
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;

	public CustomerPanel()
	{
		super(StageStyle.DECORATED);

        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

        menuBar = new MenuBar();
        Menu addMenu = new Menu("Add order");
        MenuItem addMenuItem= new MenuItem("Show");

        addMenu.getItems().addAll(addMenuItem);
        menuBar.getMenus().addAll(addMenu);
        
        Menu historyMenu = new Menu("View History");
        MenuItem historyMenuItem= new MenuItem("Show");

        historyMenu.getItems().addAll(historyMenuItem);
        menuBar.getMenus().addAll(historyMenu);
        
        root.setTop(menuBar);
        
        addMenuItem.setOnAction(e -> {
        	addItem();
        });
        historyMenuItem.setOnAction(e -> {        
        	history();
        });
        
        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
	}
	
	TableView<MenuItems> MenuTable;

	private void addItem() {
		// TODO Auto-generated method stub
		contentArea.getChildren().clear();
    	
		// buat bikin table
    	MenuTable = createMMenuItemTable();
		contentArea.getChildren().add(MenuTable);
		
		// biar item bisa di-select
		setupTableSelectionListener1();
		
		// buat masukkin quantity item yang di-select
        GridPane form = createOrderform(MenuTable);
        
        
        
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
		
	}
	
	private TableView<MenuItems> createMMenuItemTable() {
    	TableView<MenuItems> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	    
        TableColumn<MenuItems, String> menuItemName = new TableColumn<>("Name");
        menuItemName.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        menuItemName.setPrefWidth(150);
        
        TableColumn<MenuItems, String> menuItemDescription = new TableColumn<>("Description");
        menuItemDescription.setCellValueFactory(new PropertyValueFactory<>("menuItemDescription"));
        menuItemDescription.setPrefWidth(150);
        
        TableColumn<MenuItems, Integer> menuItemPrice = new TableColumn<>("Price");
        menuItemPrice.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));
        menuItemPrice.setPrefWidth(150);
       
        tableView.getColumns().addAll(menuItemName, menuItemDescription, menuItemPrice);
        tableView.setItems(FXCollections.observableArrayList(MenuItems.getAllMenuItems()));
        
        return tableView;
    }
	
	private void setupTableSelectionListener1() {
	    MenuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            idInput_menu.setText(newSelection.getMenuItemId() + "");
	            nameInput_menu.setText(newSelection.getMenuItemName());
	            itemDesc_menu.setText(newSelection.getMenuItemDescription());
	            itemPrice_menu.setText(String.valueOf(newSelection.getMenuItemPrice()));
	            quantity_menu.setText("0");
	        }
	    });
	}
	
	private MenuItems getSelectedMenuItem() {
	    return MenuTable.getSelectionModel().getSelectedItem();
	}
	
	private TextField idInput_menu;
    private TextField nameInput_menu;
    private TextField itemDesc_menu;
    private TextField itemPrice_menu;
    private TextField quantity_menu;
    
    private ArrayList<OrderItem> keranjang;
	
	private GridPane createOrderform(TableView<MenuItems> menuTable2) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idInput_menu = new TextField();
        nameInput_menu = new TextField();
        itemDesc_menu = new TextField();
        itemPrice_menu = new TextField();
        quantity_menu = new TextField();
        
        
        Button addButton = new Button("Add");
        Button finalizeButton = new Button("Finalize");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameInput_menu, 1, 0);
        form.add(new Label("Desc:"), 0, 1);
        form.add(itemDesc_menu, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(itemPrice_menu, 1, 2);
        form.add(new Label("Quantity:"), 0, 3);
        form.add(quantity_menu, 1, 3);
        form.add(addButton, 0, 4);
        form.add(finalizeButton, 1, 4);
        nameInput_menu.setDisable(true);
        itemDesc_menu.setDisable(true);
        itemPrice_menu.setDisable(true);
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MenuItems selectedMenuItem = getSelectedMenuItem();
				if (selectedMenuItem != null && quantity_menu.equals("0")==false) {
					// di sini masih gabisa masuk
					OrderItem order = new OrderItem(0, selectedMenuItem.getMenuItemId(), Integer.parseInt(quantity_menu.getText()));
					order.createOrderitem(0, selectedMenuItem, Integer.parseInt(quantity_menu.getText()));
					keranjang.add(order);
	            }
			}
		});
        
        finalizeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(keranjang!=null) {
					LocalDate currentDate = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					String formattedDate = currentDate.format(formatter);
					Date sqlDate = Date.valueOf(formattedDate);
					
					Order.createOrder(User.getUserById(0), keranjang, sqlDate);
				}
			}
		});
        
        return form;
    }

	TableView<MenuItems> OrderTable;
	
	private void history() {
		// TODO Auto-generated method stub
		contentArea.getChildren().clear();
    	
		// buat bikin table
    	OrderTable = createOrderItemTable();
		contentArea.getChildren().add(OrderTable);
		
		// biar item bisa di-select
		setupTableSelectionListener();
		
		// buat masukkin quantity item yang di-select
        GridPane form = createUpdateOrderform(OrderTable);
        
        
        
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
		
	}

	private GridPane createUpdateOrderform(TableView<Order, OrderItem> orderTable2) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setupTableSelectionListener() {
		// TODO Auto-generated method stub
		
	}

	private TableView<MenuItems> createOrderItemTable() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
