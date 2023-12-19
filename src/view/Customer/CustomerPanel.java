package view.Customer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import controller.MenuItemController;
import controller.OrderController;
import controller.OrderItemController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MenuItems;
import model.Order;
import model.OrderItem;
import model.User;
import view.UpdateOrder.UpdateOrderPanel;

public class CustomerPanel extends Stage
{
	// bisa view menu dan add order
	// bisa view menu yang uda di-order dan bisa update menu yang uda di-order
	
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    private OrderController orderController = new OrderController();
    private OrderItemController orderItemController = new OrderItemController();
    private MenuItemController menuItemController = new MenuItemController();
    private User customer;

	public CustomerPanel(User customer)
	{
		super(StageStyle.DECORATED);

		setTitle("Mystic Grills - Customer Panel");
		this.customer = customer;
        root = new BorderPane();
        Scene scene = new Scene(root, 1280, 720);
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
        	history(customer);
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
	TableView<OrderItem> keranjangTB;

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
        
        keranjangTB = createkeranjangtable();
        
     // Create an HBox to arrange forms horizontally
        HBox formsContainer = new HBox(20); // Set spacing between forms
        formsContainer.getChildren().addAll(form, keranjangTB);
        
        setupTableSelectionkeranjangTB();

        contentArea.getChildren().add(formsContainer);
		
	}
	
	private void setupTableSelectionkeranjangTB() {
	    keranjangTB.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            Platform.runLater(() -> {
	                idInput_menu.setText(String.valueOf(newSelection.getMenuItemId()));
	                nameInput_menu.setText(newSelection.getMenuItem().getMenuItemName());
	                itemDesc_menu.setText(newSelection.getMenuItem().getMenuItemDescription());
	                itemPrice_menu.setText(String.valueOf(newSelection.getMenuItem().getMenuItemPrice()));
	                quantity_menu.setText(String.valueOf(newSelection.getQuantity()));
	            });
	        }
	    });
	}

	private TableView<OrderItem> createkeranjangtable() {
		TableView<OrderItem> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<OrderItem, String> menuItemName = new TableColumn<>("Name");
        menuItemName.setCellValueFactory(cellData ->
        {
        	OrderItem name = cellData.getValue();
            return new SimpleStringProperty(name.getMenuItem().getMenuItemName());
        });
        
        TableColumn<OrderItem, Integer> menuItemQtty = new TableColumn<>("Quantity");
        menuItemQtty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menuItemQtty.setPrefWidth(150);
       
        tableView.getColumns().addAll(menuItemName, menuItemQtty);
        tableView.setItems(FXCollections.observableArrayList(keranjang));
        
        return tableView;
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
        tableView.setItems(FXCollections.observableArrayList(menuItemController.getAllMenuItem()));
        
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
	
	private OrderItem getSelectedKeranjangMenuItem() {
	    return keranjangTB.getSelectionModel().getSelectedItem();
	}
	
	private void refreshTable() {
		// TODO Auto-generated method stub
		keranjangTB.getItems().setAll(keranjang);
	}
	
	private TextField idInput_menu;
    private TextField nameInput_menu;
    private TextField itemDesc_menu;
    private TextField itemPrice_menu;
    private TextField quantity_menu;
    
    private ArrayList<OrderItem> keranjang = new ArrayList<OrderItem>();
	
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
        Button editButton = new Button("Edit");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameInput_menu, 1, 0);
        form.add(new Label("Desc:"), 0, 1);
        form.add(itemDesc_menu, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(itemPrice_menu, 1, 2);
        form.add(new Label("Quantity:"), 0, 3);
        form.add(quantity_menu, 1, 3);
        form.add(addButton, 0, 4);
        form.add(finalizeButton, 2, 4);
        form.add(editButton, 1, 4);
        nameInput_menu.setDisable(true);
        itemDesc_menu.setDisable(true);
        itemPrice_menu.setDisable(true);
        
        addButton.setOnAction(new EventHandler<ActionEvent>()
        {
			@Override
			public void handle(ActionEvent event) 
			{
				MenuItems selectedMenuItem = getSelectedMenuItem();
				if (selectedMenuItem != null && Integer.parseInt(quantity_menu.getText())>0)
				{
					OrderItem order = new OrderItem(0, selectedMenuItem.getMenuItemId(), Integer.parseInt(quantity_menu.getText()));

					order.setMenuItem();
					System.out.println(order.getMenuItem().getMenuItemName());
					
					int flag=0;
					for(OrderItem o : keranjang) {
						if(o.getMenuItemId()==selectedMenuItem.getMenuItemId() ){
							o.setQuantity(o.getQuantity()+Integer.parseInt(quantity_menu.getText()));
							flag=1;
						}
					}
					if(flag==0) {
						keranjang.add(order);						
					}
					refreshTable();
					
					OpenDialog("Success", "Added "+Integer.parseInt(quantity_menu.getText())+" "+order.getMenuItem().getMenuItemName());
	            }else if (selectedMenuItem == null) {
	            	OpenDialog("Error", "Please select menu item");
	            }else if(Integer.parseInt(quantity_menu.getText())==0) {
	            	OpenDialog("Error", "Please input quantity");
	            }
			}
		});
        
        editButton.setOnAction(new EventHandler<ActionEvent>()
        {
			@Override
			public void handle(ActionEvent event) 
			{
				OrderItem selectedMenuItem = getSelectedKeranjangMenuItem();
				selectedMenuItem.setMenuItem();
				if (selectedMenuItem != null && Integer.parseInt(quantity_menu.getText())>0){
					selectedMenuItem.setQuantity(Integer.parseInt(quantity_menu.getText()));
					refreshTable();
					
					OpenDialog("Success", "Change "+selectedMenuItem.getMenuItem().getMenuItemName()+" quantity");
	            }else if (selectedMenuItem == null) {
	            	OpenDialog("Error", "Please select menu item on keranjang");
	            }else if (Integer.parseInt(quantity_menu.getText()) == 0) {
	                Iterator<OrderItem> iterator = keranjang.iterator();
	                while (iterator.hasNext()) {
	                    OrderItem o = iterator.next();
	                    if (o.getMenuItemId() == selectedMenuItem.getMenuItemId()) {
	                        iterator.remove();
	                        OpenDialog("Success", "Delete " + selectedMenuItem.getMenuItem().getMenuItemName());
	                    }
	                }
	            }
				refreshTable();
			}
		});
        
        finalizeButton.setOnAction(new EventHandler<ActionEvent>()
        {

			@Override
			public void handle(ActionEvent event)
			{
				// TODO Auto-generated method stub
				if(keranjang != null){
					Date date = new Date(System.currentTimeMillis());
					int orderId = orderController.createOrder(customer, keranjang, date);
					
					for (OrderItem orderItem : keranjang)
					{
						System.out.println(orderItem.getMenuItem().getMenuItemName());
						orderItemController.createOrderItem(orderId, orderItem.getMenuItem(), orderItem.getQuantity());
						OpenDialog("Success", "Order has been finalized");
					}
				}else if(keranjang==null){
					OpenDialog("Error", "Please input the order!");
				}
				keranjang.clear();
			}
		});
        
        return form;
    }

	TableView<Order> OrderTable;
	
	private void history(User user) {
		// TODO Auto-generated method stub
		contentArea.getChildren().clear();
    	
		// buat bikin table
    	OrderTable = createOrderItemTable();
		contentArea.getChildren().add(OrderTable);
		
		// biar item bisa di-select
		setupTableSelectionListener(user);
		
	}

	private void setupTableSelectionListener(User user)
	{
		OrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null)
	        {
	        	if(newSelection.getOrderStatus().equals("Pending")) {	        		
	        		int orderIdToOpenDetails = newSelection.getOrderId();
	        		new UpdateOrderPanel(orderIdToOpenDetails, user).show();
	        	}	
	        }
	    });
		
	}

	private TableView<Order> createOrderItemTable()
	{
		TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	    
        TableColumn<Order, Integer> orderID = new TableColumn<>("Order ID");
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderID.setPrefWidth(150);
        
        TableColumn<Order, String> orderStatus= new TableColumn<>("Status");
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderStatus.setPrefWidth(150);
        
        TableColumn<Order, Date> orderDate = new TableColumn<>("Date");
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderDate.setPrefWidth(150);
       
        tableView.getColumns().addAll(orderID, orderStatus, orderDate);
        //get user id nya
        tableView.setItems(FXCollections.observableArrayList(orderController.getOrderByCustomerId(customer.getUserId())));
        
        return tableView;
	}
	
    private void OpenDialog(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	

}
