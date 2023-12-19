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
import view.AddOrderItemPanel.AddOrderItemPanel;
import view.AddOrderItemPanel.IAddOrderItemParentPanel;

import java.util.ArrayList;
import java.util.Date;

import controller.OrderController;
import controller.OrderItemController;

public class ChefPanel extends Stage implements IAddOrderItemParentPanel
{
	// CONTROLLERS
	private OrderController orderController = new OrderController();
	private OrderItemController orderItemController = new OrderItemController();
	
	// SCENES
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    
    // TABLES
    TableView<Order> ordersTable;
    
    // GLOBAL DATA
    private Order selectedOrder = null;
    
	public ChefPanel()
	{
		// GENERALS
		super(StageStyle.DECORATED);
		setTitle("Mystic Grills - Chef Panel");
        root = new BorderPane();
        Scene scene = new Scene(root, 1580, 900);
        setScene(scene);
        
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
	
	private GridPane openOrderPage()
	{
    	TextField orderIdField = new TextField();
    	TextField orderUserNameField = new TextField();
    	TextField orderDateField = new TextField();
    	TextField orderTotalField = new TextField();
		
    	contentArea.getChildren().clear();
    	ordersTable = createOrderTableView();
		contentArea.getChildren().add(ordersTable);
	
		ordersTable.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedOrder = newSelection;
            	loadOrderItemsData();
            	addMenuButton.setDisable(false);
            	updateQuantityButton.setDisable(true);
            	quantityField.setDisable(true);
            	
            	orderIdField.setText(String.valueOf(selectedOrder.getOrderId()));
            	orderUserNameField.setText(selectedOrder.getOrderUser().getUserName());
            	orderDateField.setText(String.valueOf(newSelection.getOrderDate()));
            	orderTotalField.setText(String.valueOf(newSelection.getOrderTotal()));
            }
        });
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
        Button prepareButton = new Button("Prepare Order");
        Button deleteButton = new Button("Delete Order");
        
        form.add(new Label("Order ID:"), 0, 0);
        orderIdField.setDisable(true);
        form.add(orderIdField, 1, 0);
        form.add(new Label("Customer Name:"), 0, 1);
        orderUserNameField.setDisable(true);
        form.add(orderUserNameField, 1, 1);
        form.add(new Label("Order Date:"), 0, 3);
        orderDateField.setDisable(true);
        form.add(orderDateField, 1, 2);
        form.add(new Label("Order Total:"), 0, 2);
        orderTotalField.setDisable(true);
        form.add(orderTotalField, 1, 3);
        
        form.add(prepareButton, 0, 5);
        form.add(deleteButton, 1, 5);
        
        deleteButton.setOnAction(e ->
        {
        	String res = orderController.deleteOrder(selectedOrder.getOrderId());
        	
        	if (res.contains("SUCCESS")) showDialog("Success", "Delete success");
            else showDialog("Failed", res);
        	loadOrdersData();
        });
        
        prepareButton.setOnAction(e ->
        {
			ArrayList<OrderItem> orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
         
			String res = orderController.updateOrder(selectedOrder.getOrderId(), orderItems, "Prepared");
            
            if (res.contains("SUCCESS")) showDialog("Success", "Prepare success");
            else  showDialog("Failed", res);
            loadOrdersData();
        });
        
        return form;
	}
	
    private TableView<Order> createOrderTableView()
    {
    	TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    	    
        TableColumn<Order, String> orderUserIdColumn = new TableColumn<>("Order User ID");
        orderUserIdColumn.setCellValueFactory(cellData ->
        {
            Order order = cellData.getValue();
            return new SimpleStringProperty(order.getOrderUser().getUserName());
        });
        
        TableColumn<Order, Date> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, Double> orderTotalColumn = new TableColumn<>("Order Total");
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
        
        tableView.getColumns().addAll(orderIdColumn, orderUserIdColumn, orderDateColumn, orderTotalColumn);
        tableView.setItems(FXCollections.observableArrayList(orderController.getAllOrdersByOrderStatus("Pending")));
        
        return tableView;
    }
    
    @Override
	public void loadOrdersData()
	{
	    ArrayList<Order> preparedOrders = orderController.getAllOrdersByOrderStatus("Pending");
	    ordersTable.getItems().setAll(preparedOrders);
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
        	new AddOrderItemPanel(this, selectedOrder.getOrderId()).show();
        	loadOrderItemsData();
        	loadOrdersData();
        });
        updateQuantityButton.setOnAction(e ->
        {
        	int quantity = Integer.parseInt(quantityField.getText());
			String res = orderItemController.updateOrderItem(selectedOrder.getOrderId(), selected.get(), quantity); // --- updateOrderItem() ---
            if (res.contains("SUCCESS"))
            {
                showDialog("Success","Update Quantity success");
            } else {
                showDialog("Failed", res);
            }
            loadOrderItemsData();
            loadOrdersData();
        });
        
        orderItemsSection.getChildren().addAll(orderItemTable, form);
        return orderItemsSection;
	}
	
	@Override
	public void loadOrderItemsData() 
	{
	    ArrayList<OrderItem> orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
	    orderItemTable.getItems().setAll(orderItems);
	}
	
	private TableView<OrderItem> createOrderDetailsTableView()
	{
		TableView<OrderItem> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<OrderItem, String> menuItemNameColumn = new TableColumn<>("Menu Item Name");
        menuItemNameColumn.setCellValueFactory(cellData ->
        {
            OrderItem orderItem = cellData.getValue();
            MenuItems menuItem = orderItem.getMenuItem();
            return new SimpleStringProperty(menuItem.getMenuItemName());
        });
        menuItemNameColumn.setPrefWidth(150);
        
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setPrefWidth(150);
               
        tableView.getColumns().addAll(menuItemNameColumn, quantityColumn);
        if(selectedOrder == null)
        {
        	tableView.setItems(FXCollections.observableArrayList(new ArrayList<>()));        	        	
        }
        else
        {
        	tableView.setItems(FXCollections.observableArrayList(orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId())));        	        	
        }
        
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
