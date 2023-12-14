package view.UpdateOrder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import view.Customer.CustomerPanel;



public class UpdateOrderPanel extends Stage{
	private BorderPane root;
    private VBox contentArea = new VBox();
    
    Stage addItemStage;
	
	public UpdateOrderPanel(int orderId) {
		super(StageStyle.DECORATED);
		root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);
        
        // buat nambahin item, ngarah ke panel baru
        Button addButton = new Button("Add New Item");
        contentArea.getChildren().add(addButton);
        addButton.setOnAction(event -> showAddNewItemForm(orderId));
		
        // show tabel isinya ordered item
		OrderDetailTable = createEditOrderItemTable(orderId);
		contentArea.getChildren().add(OrderDetailTable);
		
		root.setCenter(contentArea); 

        setScene(scene);
        setTitle("Order Items Table View");
        
        setupEditItemTableSelectionListener();
        
        GridPane form = editOrderform(OrderDetailTable, orderId);
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
    }
	
	TableView<MenuItems> MenuTable;
	
	private void showAddNewItemForm(int orderId) {
        // Create a new stage for the form
		contentArea.getChildren().clear();
    	
		// buat bikin table
    	MenuTable = createMMenuItemTable();
		contentArea.getChildren().add(MenuTable);
		
		// biar item bisa di-select
		setupAddNewItemTableSelectionListener();
		
		// buat masukkin quantity item yang di-select
        GridPane form = createOrderform(MenuTable, orderId);
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
    }
	
	private void setupAddNewItemTableSelectionListener() {
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
	
	private GridPane createOrderform(TableView<MenuItems> menuTable2, int orderId) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        idInput_menu = new TextField();
        nameInput_menu = new TextField();
        itemDesc_menu = new TextField();
        itemPrice_menu = new TextField();
        quantity_menu = new TextField();
        
        Button addButton = new Button("Add");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameInput_menu, 1, 0);
        form.add(new Label("Desc:"), 0, 1);
        form.add(itemDesc_menu, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(itemPrice_menu, 1, 2);
        form.add(new Label("Quantity:"), 0, 3);
        form.add(quantity_menu, 1, 3);
        form.add(addButton, 0, 4);        
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
					OrderItem order = new OrderItem(orderId, selectedMenuItem.getMenuItemId(), Integer.parseInt(quantity_menu.getText()));
					order.createOrderitem(orderId, selectedMenuItem, Integer.parseInt(quantity_menu.getText()));
	            }
				addItemStage.close();
			}
		});
        
        return form;
    }
	
	private MenuItems getSelectedMenuItem() {
	    return MenuTable.getSelectionModel().getSelectedItem();
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
	
	private GridPane createAddItemForm(int orderId) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);

        TextField newItemName = new TextField();
        TextField newItemDesc = new TextField();
        TextField newItemPrice = new TextField();
        TextField newItemQuantity = new TextField();

        Button addButton = new Button("Add Item to Order");

        form.add(new Label("Name:"), 0, 0);
        form.add(newItemName, 1, 0);
        form.add(new Label("Desc:"), 0, 1);
        form.add(newItemDesc, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(newItemPrice, 1, 2);
        form.add(new Label("Quantity:"), 0, 3);
        form.add(newItemQuantity, 1, 3);
        form.add(addButton, 0, 4);

        addButton.setOnAction(event -> {
            // Create a new OrderItem and add it to the order
            if (!newItemName.getText().isEmpty() && !newItemQuantity.getText().isEmpty()) {
                
                // Refresh the table and close the add item form
                refreshTable(orderId);
                addItemStage.close();
            }
        });

        return form;
    }

	private TableView<OrderItem> createEditOrderItemTable(int orderId) {
		TableView<OrderItem> tableView = new TableView<>();
        TableColumn<OrderItem, Integer> menuItemIdColumn = new TableColumn<>("Menu Item ID");
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");       

        // Set cell value factories to extract data from OrderItem properties
        menuItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableView.getColumns().addAll(menuItemIdColumn, quantityColumn);

        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList(OrderItem.getAllOrderItemsByOrderId(orderId));
        tableView.setItems(orderItems);
        
        return tableView;
	}

	TableView<OrderItem> OrderDetailTable;
	
	private TextField idInput_menu;
    private TextField nameInput_menu;
    private TextField itemDesc_menu;
    private TextField itemPrice_menu;
    private TextField quantity_menu;
	
	private void setupEditItemTableSelectionListener() {
		OrderDetailTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	OrderItem selectedOrderItem = (OrderItem) newSelection;
	        	int menuItemId = selectedOrderItem.getMenuItemId();
	        	
	        	MenuItems menuSelected = MenuItems.getMenuItemById(menuItemId);
	            idInput_menu.setText(menuSelected.getMenuItemId() + "");
	            nameInput_menu.setText(menuSelected.getMenuItemName());
	            itemDesc_menu.setText(menuSelected.getMenuItemDescription());
	            itemPrice_menu.setText(String.valueOf(menuSelected.getMenuItemPrice()));
	        }
	    });
	}
	
	private GridPane editOrderform(TableView<OrderItem> menuTable2, int orderId) {
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);

        idInput_menu = new TextField();
        nameInput_menu = new TextField();
        itemDesc_menu = new TextField();
        itemPrice_menu = new TextField();
        quantity_menu = new TextField();
        
        
        Button editButton = new Button("Edit menu");
        
        form.add(new Label("Name:"), 0, 0);
        form.add(nameInput_menu, 1, 0);
        form.add(new Label("Desc:"), 0, 1);
        form.add(itemDesc_menu, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(itemPrice_menu, 1, 2);
        form.add(new Label("Quantity:"), 0, 3);
        form.add(quantity_menu, 1, 3);
        form.add(editButton, 0, 4);
        nameInput_menu.setDisable(true);
        itemDesc_menu.setDisable(true);
        itemPrice_menu.setDisable(true);
        
        editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				OrderItem selectedOrderItem = getSelectedOrderItem();
				if (selectedOrderItem != null) {
					// edit quantity
					if (quantity_menu.equals("0")){
		            	// hapus menu
		            	selectedOrderItem.deleteOrderItem(selectedOrderItem.getOrderId(), selectedOrderItem.getMenuItemId());
		            }
					//edit quantity
					// masih belom masuk
					selectedOrderItem.setQuantity(Integer.parseInt(quantity_menu.getText()));
				}
				refreshTable(orderId);
			}
		});

        
        return form;
    }
	
	private OrderItem getSelectedOrderItem() {
	    return OrderDetailTable.getSelectionModel().getSelectedItem();
	}
	
	private void refreshTable(int orderId) {
		// TODO Auto-generated method stub
		ArrayList<OrderItem> orderItem = OrderItem.getAllOrderItemsByOrderId(orderId);
		OrderDetailTable.getItems().setAll(orderItem);
	}
	
}
