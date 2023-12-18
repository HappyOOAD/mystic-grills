package view.Chef;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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

import java.util.ArrayList;
import java.util.Date;

import controller.OrderController;
import controller.OrderItemController;

public class ChefPanel extends Stage
{
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    TableView<Order>menuItemTable;
    private OrderController orderController = new OrderController();
    private OrderItemController orderItemController = new OrderItemController();
    private int selectedOrderId = 0;
    
	public ChefPanel()
	{
		// GENERALS
		super(StageStyle.DECORATED);
		setTitle("Mystic Grills - Chef Panel");
        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 800);
        this.setScene(scene);
        
        // MENUBARS
        menuBar = new MenuBar();
        Menu orderMenu = new Menu("Order");
        menuBar.getMenus().addAll(orderMenu);
        root.setTop(menuBar);
        orderMenu.setOnAction(e ->
        {
        	openOrderPage();
        });

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        openOrderPage();
        VBox topSection = new VBox(15);;
        topSection.setAlignment(Pos.CENTER);
        topSection.getChildren().addAll(contentArea);
        root.setTop(topSection);

        HBox bottomSection = new HBox(40);
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setPadding(new Insets(20));
        GridPane form = openOrderPage();
        HBox orderItemSection = openOrderItemPage();
        bottomSection.getChildren().addAll(form, orderItemSection);
        root.setBottom(bottomSection);
	}
	
	private GridPane openOrderPage() // Open Orders Page
	{
    	TextField orderId = new TextField();
    	TextField userId = new TextField();
    	TextField orderStatus = new TextField();
    	TextField orderDate = new TextField();
		
    	contentArea.getChildren().clear();
    	menuItemTable = createOrderTableView();
		contentArea.getChildren().add(menuItemTable);
	
		menuItemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedOrderId = newSelection.getOrderId();
            	loadOrderItemsData();
            	addMenuButton.setDisable(false);
            	updateQuantityButton.setDisable(true);
            	quantityField.setDisable(true);
            	
            	orderId.setText(newSelection.getOrderId()+"");
            	userId.setText(newSelection.getOrderUserId()+"");
            	orderStatus.setText(String.valueOf(newSelection.getOrderStatus()));
            	orderDate.setText(String.valueOf(newSelection.getOrderDate()));
            }
        });
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
		Button updateQuantityButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button prepareButton = new Button("Prepare");
        
        form.add(new Label("Order ID:"), 0, 0);
        orderId.setDisable(true);
        form.add(orderId, 1, 0);
        form.add(new Label("User ID:"), 0, 1);
        userId.setDisable(true);
        form.add(userId, 1, 1);
        form.add(new Label("Order Status:"), 0, 2);
        orderStatus.setDisable(true);
        form.add(orderStatus, 1, 2);
        form.add(new Label("Order Date:"), 0, 3);
        orderDate.setDisable(true);
        form.add(orderDate, 1, 3);
        
        form.add(updateQuantityButton, 0, 5);
        form.add(deleteButton, 1, 5);
        form.add(prepareButton, 2, 5);
        
        updateQuantityButton.setOnAction(e ->
        {
			int id= Integer.parseInt(orderId.getText());
			String status = orderStatus.getText();
			
			
			Order x = orderController.getOrderByOrderId(id); // --- getOrderByOrderId() ---
			
			ArrayList<OrderItem> orderItem = x.getOrderItems(); 
            String updatingOrder = orderController.updateOrder(id, orderItem, status);
            
            if (updatingOrder.contains("SUCCESS"))
            {
                showDialog("Success","Update success");
                loadOrdersData();
            } else {
                showDialog("Failed",updatingOrder);
                loadOrdersData();
            }
            
        });
        
        deleteButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(orderId.getText());
        	String deleteOrder = orderController.deleteOrder(id);
        	
        	if (deleteOrder.contains("SUCCESS"))
        	{
                showDialog("Success","Delete success");
                loadOrdersData();
            } 
        	else 
        	{
                showDialog("Failed",deleteOrder);
                loadOrdersData();
            }
        });
        
        prepareButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(orderId.getText());
			String status = "Prepared";
			
			Order x = Order.getOrderById(id);
			ArrayList<OrderItem> orderItem = x.getOrderItems(); 
            String updatingOrder = orderController.updateOrder(id, orderItem, status);
            
            if (updatingOrder.contains("SUCCESS"))
            {
                showDialog("Success","Update success");
                loadOrdersData();
            } 
            else 
            {
                showDialog("Failed",updatingOrder);
                loadOrdersData();
            }
        });
        return form;
	}
	
    private TableView<Order> createOrderTableView()
    {
    	TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    	    
        TableColumn<Order, Integer> orderUserIdColumn = new TableColumn<>("Order User ID");
        orderUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderUserId"));
        
        TableColumn<Order, String> orderStatusColumn = new TableColumn<>("Order Status");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        
        TableColumn<Order, Date> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
               
        tableView.getColumns().addAll(orderIdColumn, orderUserIdColumn, orderStatusColumn, orderDateColumn);
        tableView.setItems(FXCollections.observableArrayList(orderController.getAllOrdersByOrderStatus("Pending")));
        
        return tableView;
    }
    
	public void loadOrdersData() // Load Orders
	{
	    ArrayList<Order> preparedOrders = orderController.getAllOrdersByOrderStatus("Pending");
	    menuItemTable.getItems().setAll(preparedOrders);
	}
	
	private TableView<OrderItem> orderItemTable;
	private Button addMenuButton, updateQuantityButton;
	private TextField quantityField;
	
	private HBox openOrderItemPage() // Open Order Items Page
	{
		HBox orderItemsSection = new HBox(15);

		ObjectProperty<MenuItems> selected = new SimpleObjectProperty<>(null);

		orderItemTable = createOrderDetailsTableView();
    	
		quantityField = new TextField();
    	quantityField.setDisable(true);
    	
    	addMenuButton = new Button("Add New Menu Item");
    	addMenuButton.setDisable(true);
    	updateQuantityButton = new Button("Update Quantity");
    	updateQuantityButton.setDisable(true);
	
    	// Select Listeners
    	orderItemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	quantityField.setText(String.valueOf(newSelection.getQuantity()));
            	quantityField.setDisable(false);
            	updateQuantityButton.setDisable(false);
            	selected.set(newSelection.getMenuItem());
            }
        });
    	
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        form.add(addMenuButton, 0, 0);
        form.add(new Label("Quantity"), 0, 1);
        form.add(quantityField, 1, 1);
        form.add(updateQuantityButton, 0, 2);
        
        addMenuButton.setOnAction(e -> // --- createOrderItem() ---
        {
        	// Add Menu Item Here
        	new AddOrderItemPanel(this, selectedOrderId).show();
        	loadOrderItemsData();
        });
        updateQuantityButton.setOnAction(e ->
        {
        	System.out.println(selected.get().getMenuItemName());
			String res = orderItemController.updateOrderItem(selectedOrderId, selected.get(), Integer.parseInt(quantityField.getText()) ); // --- updateOrderItem() ---
            if (res.contains("SUCCESS"))
            {
                showDialog("Success","Update success");
            } else {
                showDialog("Failed", res);
            }
            loadOrderItemsData();
            
        });
        
        orderItemsSection.getChildren().addAll(orderItemTable, form);
        return orderItemsSection;
	}
	
	public void loadOrderItemsData() 
	{
	    ArrayList<OrderItem> orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrderId);
	    orderItemTable.getItems().setAll(orderItems);
	}
	
	private TableView<OrderItem> createOrderDetailsTableView()
	{
		TableView<OrderItem> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<OrderItem, String> menuItemNameColumn = new TableColumn<>("Menu Item Name");
        menuItemNameColumn.setCellValueFactory(cellData -> {
            OrderItem orderItem = cellData.getValue();
            if (orderItem != null)
            {
                MenuItems menuItem = orderItem.getMenuItem();
                if (menuItem != null)
                {
                    return new SimpleStringProperty(menuItem.getMenuItemName());
                }
            }
            return new SimpleStringProperty("");
        });
        menuItemNameColumn.setPrefWidth(150);
    	    
//        TableColumn<OrderItem, Double> menuItemPriceColumn = new TableColumn<>("Price");
//        menuItemPriceColumn.setCellValueFactory(cellData ->
//        {
//            OrderItem orderItem = cellData.getValue();
//            if (orderItem != null)
//            {
//                MenuItems menuItem = orderItem.getMenuItem();
//                if (menuItem != null)
//                {
//                    return new SimpleDoubleProperty(menuItem.getMenuItemPrice());
//                }
//            }
//        });
//        menuItemPriceColumn.setPrefWidth(150);
        
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(150);
               
        tableView.getColumns().addAll(menuItemNameColumn, quantityColumn);
        tableView.setItems(FXCollections.observableArrayList(orderItemController.getAllOrderItemsByOrderId(selectedOrderId)));
        
        return tableView;
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
