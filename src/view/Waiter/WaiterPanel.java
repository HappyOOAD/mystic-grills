package view.Waiter;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import view.AddOrderItemPanel.AddOrderItemPanel;
import view.AddOrderItemPanel.IAddOrderItemParentPanel;

import java.util.ArrayList;
import java.util.Date;

import controller.OrderController;
import controller.OrderItemController;

public class WaiterPanel extends Stage implements IAddOrderItemParentPanel
{
	// CONTROLLERS
	private OrderController orderController = new OrderController();
	private OrderItemController orderItemController = new OrderItemController();
	
	// GLOBAL DATA
	private Order selectedOrder = null;
	private OrderItem selectedMenuItem = null;

	// GUI COMPONENTS
	private BorderPane root;
    private TableView<Order> ordersTable;
    private TableView<OrderItem> orderItemsTable;
    private Button serveOrderButton, deleteOrderButton, addMenuButton, updateQuantityButton, deleteOrderItemButton;
    private TextField quantityField;
    
	public WaiterPanel()
	{
		// Setup Scene
		super(StageStyle.DECORATED);
		setTitle("Mystic Grills - Waiter Panel");
        root = new BorderPane();
        Scene scene = new Scene(root, 1280, 720);
        setScene(scene);
        initializeOrderPage();
	}
	
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
		GridPane orderItemActionGrid = createOrderItemActionGrid();

		// Combine Action Grid
		VBox actionGrid = new VBox(30);
		actionGrid.getChildren().addAll(orderActionGrid, orderItemActionGrid);

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
		GridPane actionGrid = new GridPane();
        actionGrid.setVgap(10);
        actionGrid.setHgap(10);
		
        serveOrderButton = new Button("Serve Order");
        serveOrderButton.setDisable(true);
        deleteOrderButton = new Button("Delete Order");
        deleteOrderButton.setDisable(true);
        
        
        actionGrid.add(serveOrderButton, 0, 0);
        actionGrid.add(deleteOrderButton, 1, 0);
        
        serveOrderButton.setOnAction(e ->
        {
        	ArrayList<OrderItem> orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
        	
        	String res = orderController.updateOrder(selectedOrder.getOrderId(), orderItems, "Served");
        	
        	if (res.contains("SUCCESS")) showDialog("Success", "Serve success");
        	else  showDialog("Failed", res);
        	selectedOrder = null;
        	loadOrdersTableData();
        	loadOrderItemsTableData();
        	serveOrderButton.setDisable(true);
        	deleteOrderButton.setDisable(true);
        	addMenuButton.setDisable(true);
        	updateQuantityButton.setDisable(true);
        	quantityField.setDisable(true);
        	deleteOrderItemButton.setDisable(true);
        });

        deleteOrderButton.setOnAction(e ->
        {
        	//Delete Order by Chosen OrderID
        	String res = orderController.deleteOrder(selectedOrder.getOrderId());
        	
        	if (res.contains("SUCCESS")) showDialog("Success", "Delete success");
            else showDialog("Failed", res);
        	selectedOrder = null;
        	loadOrdersTableData();
        	loadOrderItemsTableData();
        	serveOrderButton.setDisable(true);
        	deleteOrderButton.setDisable(true);
        	addMenuButton.setDisable(true);
        	updateQuantityButton.setDisable(true);
        	quantityField.setDisable(true);
        	deleteOrderItemButton.setDisable(true);
        });
        
        return actionGrid;
	}
	
	// Method to generate Order Table
    private TableView<Order> createOrderTableView()
    {
    	TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
    	// Order ID Column
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tableView.getColumns().add(orderIdColumn);
    	    
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
            	serveOrderButton.setDisable(false);
            	deleteOrderButton.setDisable(false);
            	addMenuButton.setDisable(false);
            	quantityField.setDisable(true);
            	updateQuantityButton.setDisable(true);
            	deleteOrderItemButton.setDisable(true);
            }
        });
        
        return tableView;
    }
    
    // Method to reload Order Table data
    @Override
	public void loadOrdersTableData()
	{
	    ArrayList<Order> preparedOrders = orderController.getAllOrdersByOrderStatus("Prepared");
	    ordersTable.getItems().setAll(preparedOrders);
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------//
	//																																				//
	// ORDER ITEM SECTION																															//
	//																																				//
	// ---------------------------------------------------------------------------------------------------------------------------------------------//
	
    // Method to generate Order Items Action Grid
    private GridPane createOrderItemActionGrid()
	{
		quantityField = new TextField();
    	quantityField.setDisable(true);
    	
    	addMenuButton = new Button("Add New Menu Item");
    	addMenuButton.setDisable(true);
    	updateQuantityButton = new Button("Update Quantity");
    	updateQuantityButton.setDisable(true);
    	deleteOrderItemButton = new Button("Delete Order Item");
    	deleteOrderItemButton.setDisable(true);
    	
		GridPane actionGrid = new GridPane();
        actionGrid.setVgap(10);
        actionGrid.setHgap(10);
        
        actionGrid.add(addMenuButton, 0, 0);
        actionGrid.add(new Label("Quantity"), 0, 1);
        actionGrid.add(quantityField, 1, 1);
        actionGrid.add(updateQuantityButton, 0, 2);
        actionGrid.add(deleteOrderItemButton, 0, 3);
        
        addMenuButton.setOnAction(e -> // --- createOrderItem() ---
        {
        	// Add Menu Item Here
        	new AddOrderItemPanel(this, selectedOrder.getOrderId()).show();
        	reloadPageData();
        	serveOrderButton.setDisable(true);
        	deleteOrderButton.setDisable(true);
        	addMenuButton.setDisable(true);
        	updateQuantityButton.setDisable(true);
        	quantityField.setDisable(true);
        	deleteOrderItemButton.setDisable(true);
        });
        
        updateQuantityButton.setOnAction(e ->
        {
        	//Get the value quantity
        	int quantity = Integer.parseInt(quantityField.getText());
			String res = orderItemController.updateOrderItem(selectedOrder.getOrderId(), selectedMenuItem.getMenuItem(), quantity); // --- updateOrderItem() ---
            if (res.contains("SUCCESS"))
            {
                showDialog("Success","Update Quantity success");
            } else {
                showDialog("Failed", res);
            }
            reloadPageData();
        	serveOrderButton.setDisable(true);
        	deleteOrderButton.setDisable(true);
        	addMenuButton.setDisable(true);
        	updateQuantityButton.setDisable(true);
        	quantityField.setDisable(true);
        	deleteOrderItemButton.setDisable(true);
        });

        deleteOrderItemButton.setOnAction(e ->
        {
        	//Get the value quantity
        	int quantity = Integer.parseInt(quantityField.getText());
			String res = orderItemController.deleteOrderItem(selectedOrder.getOrderId(), selectedMenuItem.getMenuItem(), quantity); // --- updateOrderItem() ---
            if (res.contains("SUCCESS"))
            {
                showDialog("Success","Delete Order Item success");
            } else {
                showDialog("Failed", res);
            }
            reloadPageData();
            quantityField.setText("");
            selectedMenuItem = null;
        	serveOrderButton.setDisable(true);
        	deleteOrderButton.setDisable(true);
        	addMenuButton.setDisable(true);
        	updateQuantityButton.setDisable(true);
        	quantityField.setDisable(true);
        	deleteOrderItemButton.setDisable(true);
        });
        
        return actionGrid;
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
        	return Bindings.createObjectBinding(() -> menuItem.getMenuItemPrice() * order.getQuantity());
        });
        totalColumn.setPrefWidth(120);
        tableView.getColumns().add(totalColumn);
        
        // Set On Click Listeners
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedMenuItem = newSelection;
            	quantityField.setText(String.valueOf(newSelection.getQuantity()));
            	quantityField.setDisable(false);
            	updateQuantityButton.setDisable(false);
            	deleteOrderItemButton.setDisable(false);
            }
        });
    	
        return tableView;
	}

    // Method to reload Order Items Table data
	@Override
	public void loadOrderItemsTableData() 
	{
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		if(selectedOrder != null)
		{
			orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
		}
		orderItemsTable.getItems().setAll(orderItems);
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
