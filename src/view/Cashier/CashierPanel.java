package view.Cashier;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import model.Receipt;
import model.User;

import java.util.ArrayList;
import java.sql.Date;

import controller.OrderController;
import controller.OrderItemController;
import controller.ReceiptController;

public class CashierPanel extends Stage
{
	// CONTROLLERS
	private OrderController orderController = new OrderController();
	private OrderItemController orderItemController = new OrderItemController();
	private ReceiptController receiptController = new ReceiptController();
	
	// SCENES
	private BorderPane root;
    private MenuBar menus;
    Node orderView, receiptView;
    
    // NODES
    Button processButton;
    
    // GLOBAL DATA
    private Order selectedOrder = null;
    
	public CashierPanel()
	{
		// Setup Scene
		super(StageStyle.DECORATED);
		setTitle("Mystic Grills - Chef Panel");
        root = new BorderPane();
        Scene scene = new Scene(root, 1280, 720);
        setScene(scene);
        initializeCashierPanel();
	}
	
	private void initializeCashierPanel()
	{
		createMenuBar();
		root.setCenter(orderView);
	}
	
	//Creating A MenuBar
	private MenuBar createMenuBar()
	{
		menus = new MenuBar();
        Menu orderMenu = new Menu("Order");
        MenuItem orderMenuItem= new MenuItem("Show");
        
        Menu ReceiptMenu = new Menu("Receipt");
        MenuItem ReceiptMenuItem= new MenuItem("Show");

        orderMenu.getItems().addAll(orderMenuItem);
        menus.getMenus().addAll(orderMenu);
        
        ReceiptMenu.getItems().addAll(ReceiptMenuItem);
        menus.getMenus().addAll(ReceiptMenu);
        
        root.setTop(menus);
        
        orderMenuItem.setOnAction(e -> {
        	selectedOrder = null;
        	initializeOrderPage();
        });
        
        ReceiptMenuItem.setOnAction(e -> {
        	selectedReceipt = null;
        	initializeReceiptPage();
        });

        return menus;
    }

	// ---------------------------------------------------------------------------------------------------------------------------------------------//
	//																																				//
	// CASHIER - ORDER VIEW																															//
	//																																				//
	// ---------------------------------------------------------------------------------------------------------------------------------------------//


	// GUI COMPONENTS
    private TableView<Order> ordersTable;
    private TableView<OrderItem> orderItemsTable;
    private Button processPaymentOrderButton;
    private ComboBox<String> paymentTypeField;
	
	// Method to initialize page
	public void initializeOrderPage()
	{
		// Generate Orders Table
    	ordersTable = createOrderTableView();
    	loadOrdersTableData();
        
    	// Generate Order Item Table
    	orderItemsTable = createOrderItemsTableView();
    	loadOrderItemsTableData();

    	// Generate Action Grid
        GridPane orderActionGrid = createOrderActionGrid();

		// Combine Action Grid
		VBox actionGrid = new VBox(30);
		actionGrid.getChildren().addAll(orderActionGrid);

		// Generate Horizontal Bottom Container
		HBox bottomContainer = new HBox(10);
		bottomContainer.getChildren().addAll(orderItemsTable, actionGrid);
        
        // Assign to Root Container
        VBox rootContainer = new VBox(10);
        rootContainer.setPadding(new Insets(20));
        rootContainer.getChildren().addAll(ordersTable, bottomContainer);
        root.setCenter(rootContainer);
	}
	
	// Method to refresh all data
	public void reloadPageData()
	{
		loadOrdersTableData();
		loadOrderItemsTableData();
	}

	// Method to generate Order Action Grid
	private GridPane createOrderActionGrid()
	{
    	ObservableList<String> options = FXCollections.observableArrayList("Cash", "Debit", "Credit");
    	paymentTypeField = new ComboBox<String>(options);
    	paymentTypeField.setValue("Cash");
    
		GridPane actionGrid = new GridPane();
        actionGrid.setVgap(10);
        actionGrid.setHgap(10);
		
        processPaymentOrderButton = new Button("Serve Order");
        processPaymentOrderButton.setDisable(true);
        
        actionGrid.add(new Label("Payment Type: "), 0, 0);
        paymentTypeField.setDisable(true);
        actionGrid.add(paymentTypeField, 1, 0);
        actionGrid.add(processPaymentOrderButton, 0, 1);
        
        processPaymentOrderButton.setOnAction(e ->
        {
        	ArrayList<OrderItem> orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
        	
        	orderController.updateOrder(selectedOrder.getOrderId(), orderItems, "Paid");
        	
        	Date date = new Date(System.currentTimeMillis());
        	String res = receiptController.createReceipt(selectedOrder, paymentTypeField.getValue(), selectedOrder.getOrderTotal(), date);
        	if (res.contains("SUCCESS")) showDialog("Success", "Process Payment success");
        	else  showDialog("Failed", res);
        	loadOrdersTableData();
        	selectedOrder = null;
        	loadOrderItemsTableData();
        	paymentTypeField.setDisable(true);
        	processPaymentOrderButton.setDisable(true);
        });
        
        return actionGrid;
	}
	
	// Method to generate Order Table
    private TableView<Order> createOrderTableView()
    {
    	TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
    	// Order ID Column
        TableColumn<Order, Integer> receiptIdColumn = new TableColumn<>("Order ID");
        receiptIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tableView.getColumns().add(receiptIdColumn);
    	    
        // Order Customer Name Column
        TableColumn<Order, String> customerNameColumn = new TableColumn<>("Customer Name");
        customerNameColumn.setCellValueFactory(cellData ->
        {
            Order order = cellData.getValue();
            User orderUser = order.getOrderUser();
            return Bindings.createObjectBinding(() -> orderUser.getUserName());
        });
        tableView.getColumns().add(customerNameColumn);
        
        // Order Date Column
        TableColumn<Order, Date> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tableView.getColumns().add(orderDateColumn);
        
        // Order Total Price Column
        TableColumn<Order, Double> orderTotalColumn = new TableColumn<>("Order Total");
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
        tableView.getColumns().add(orderTotalColumn);
        
        // Set On Click Listeners
        tableView.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedOrder = newSelection;
            	loadOrderItemsTableData();
            	paymentTypeField.setDisable(false);
            	processPaymentOrderButton.setDisable(false);
            }
        });
        
        return tableView;
    }
    
    // Method to reload Order Table data
	public void loadOrdersTableData()
	{
	    ArrayList<Order> preparedOrders = orderController.getAllOrdersByOrderStatus("Served");
	    ordersTable.getItems().setAll(preparedOrders);
	}
	
    // Method to generate Order Items Table
	private TableView<OrderItem> createOrderItemsTableView()
	{
		TableView<OrderItem> tableView = new TableView<>();
    	
    	// Menu Item NameColumn
        TableColumn<OrderItem, String> menuItemNameColumn = new TableColumn<>("Menu Item");
        menuItemNameColumn.setCellValueFactory(cellData ->
        {
            OrderItem orderItem = cellData.getValue();
            MenuItems menuItem = orderItem.getMenuItem();
            return new SimpleStringProperty(menuItem.getMenuItemName());
        });
        menuItemNameColumn.setPrefWidth(180);
        tableView.getColumns().add(menuItemNameColumn);
        
        // Quantity Column
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(120);
        tableView.getColumns().add(quantityColumn);
        
        // Menu Item Price Column
        TableColumn<OrderItem, Double> menuItemPriceColumn = new TableColumn<>("Price");
        menuItemPriceColumn.setCellValueFactory(cellData ->
        {
        	OrderItem order = cellData.getValue();
        	MenuItems menuItem = order.getMenuItem();
        	return Bindings.createObjectBinding(() -> menuItem.getMenuItemPrice());
        });
        menuItemPriceColumn.setPrefWidth(120);
        tableView.getColumns().add(menuItemPriceColumn);
        
        // Menu Item Price Column
        TableColumn<OrderItem, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData ->
        {
        	OrderItem order = cellData.getValue();
        	MenuItems menuItem = order.getMenuItem();
        	return Bindings.createObjectBinding(() -> menuItem.getMenuItemPrice() * (double) order.getQuantity());
        });
        totalColumn.setPrefWidth(120);
        tableView.getColumns().add(totalColumn);
        
        return tableView;
	}

    // Method to reload Order Items Table data
	public void loadOrderItemsTableData() 
	{
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		if(selectedOrder != null)
		{
			orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
		}
		orderItemsTable.getItems().setAll(orderItems);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------//
	//																																				//
	// CASHIER - RECEIPT VIEW																															//
	//																																				//
	// ---------------------------------------------------------------------------------------------------------------------------------------------//
	
	// GLOBAL DATA
	private Receipt selectedReceipt = null;

	// GUI COMPONENTS
    private TableView<Receipt> receiptsTable;
    private TableView<OrderItem> receiptItemsTable;
    
	// Method to initialize page
	public void initializeReceiptPage()
	{
		// Generate Orders Table
    	receiptsTable = createReceiptsTableView();
    	loadReceiptsTableData();
        
    	// Generate Order Item Table
    	receiptItemsTable = createReceiptItemsTableView();
    	loadReceiptItemsTableData();

		// Generate Horizontal Bottom Container
		HBox bottomContainer = new HBox(10);
		bottomContainer.getChildren().addAll(receiptItemsTable);
        
        // Assign to Root Container
        VBox rootContainer = new VBox(10);
        rootContainer.setPadding(new Insets(20));
        rootContainer.getChildren().addAll(receiptsTable, bottomContainer);
        root.setCenter(rootContainer);
	}
	
	// Method to generate Receipt Table
    private TableView<Receipt> createReceiptsTableView()
    {
    	TableView<Receipt> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
    	// Order ID Column
        TableColumn<Receipt, Integer> receiptIdColumn = new TableColumn<>("Receipt ID");
        receiptIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        tableView.getColumns().add(receiptIdColumn);
    	    
        // Order Customer Name Column
        TableColumn<Receipt, String> customerNameColumn = new TableColumn<>("Customer Name");
        customerNameColumn.setCellValueFactory(cellData ->
        {
            Receipt receipt = cellData.getValue();
            User orderUser = receipt.getReceiptOrder().getOrderUser();
            return Bindings.createObjectBinding(() -> orderUser.getUserName());
        });
        tableView.getColumns().add(customerNameColumn);
        
        // Order Date Column
        TableColumn<Receipt, Date> receiptDateColumn = new TableColumn<>("Receipt Date");
        receiptDateColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentDate"));
        tableView.getColumns().add(receiptDateColumn);
        
        // Order Total Price Column
        TableColumn<Receipt, Double> receiptTotalColumn = new TableColumn<>("Receipt Total");
        receiptTotalColumn.setCellValueFactory(cellData ->
        {
            Receipt receipt = cellData.getValue();
            return Bindings.createObjectBinding(() -> receipt.getReceiptOrder().getOrderTotal());
        });
        tableView.getColumns().add(receiptTotalColumn);
        
        // Set On Click Listeners
        tableView.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedReceipt = newSelection;
            	loadReceiptItemsTableData();
            }
        });
        
        return tableView;
    }
    
    // Method to reload Receipt Table data
	public void loadReceiptsTableData()
	{
	    ArrayList<Receipt> receipts = receiptController.getAllReceipts();
	    receiptsTable.getItems().setAll(receipts);
	}

	// Method to generate Receipt Items Table
	private TableView<OrderItem> createReceiptItemsTableView()
	{
		TableView<OrderItem> tableView = new TableView<>();
    	
    	// Menu Item NameColumn
        TableColumn<OrderItem, String> menuItemNameColumn = new TableColumn<>("Menu Item");
        menuItemNameColumn.setCellValueFactory(cellData ->
        {
            OrderItem orderItem = cellData.getValue();
            MenuItems menuItem = orderItem.getMenuItem();
            return new SimpleStringProperty(menuItem.getMenuItemName());
        });
        menuItemNameColumn.setPrefWidth(180);
        tableView.getColumns().add(menuItemNameColumn);
        
        // Quantity Column
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(120);
        tableView.getColumns().add(quantityColumn);
        
        // Menu Item Price Column
        TableColumn<OrderItem, Double> menuItemPriceColumn = new TableColumn<>("Price");
        menuItemPriceColumn.setCellValueFactory(cellData ->
        {
        	OrderItem order = cellData.getValue();
        	MenuItems menuItem = order.getMenuItem();
        	return Bindings.createObjectBinding(() -> menuItem.getMenuItemPrice());
        });
        menuItemPriceColumn.setPrefWidth(120);
        tableView.getColumns().add(menuItemPriceColumn);
        
        // Menu Item Price Column
        TableColumn<OrderItem, Double> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData ->
        {
        	OrderItem order = cellData.getValue();
        	MenuItems menuItem = order.getMenuItem();
        	return Bindings.createObjectBinding(() -> menuItem.getMenuItemPrice() * (double) order.getQuantity());
        });
        totalColumn.setPrefWidth(120);
        tableView.getColumns().add(totalColumn);
    	
        return tableView;
	}

    // Method to reload Receipt Items Table data
	public void loadReceiptItemsTableData() 
	{
		ArrayList<OrderItem> receiptItems = new ArrayList<>();
		if(selectedReceipt != null)
		{
			receiptItems = orderItemController.getAllOrderItemsByOrderId(selectedReceipt.getReceiptOrderId());
		}
		receiptItemsTable.getItems().setAll(receiptItems);
	}

	
	// ALERT DIALOG
    private void showDialog(String title, String successMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }
}
