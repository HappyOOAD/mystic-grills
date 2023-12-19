package view.Cashier;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import view.AddOrderItemPanel.IAddOrderItemParentPanel;

import java.util.ArrayList;
import java.sql.Date;

import controller.OrderController;
import controller.OrderItemController;
import controller.ReceiptController;

public class CashierPanel extends Stage implements IAddOrderItemParentPanel
{
	// CONTROLLERS
	private OrderController orderController = new OrderController();
	private OrderItemController orderItemController = new OrderItemController();
	private ReceiptController receiptController = new ReceiptController();
	
	// SCENES
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    VBox topSection = new VBox(15);;
    HBox bottomSection = new HBox(40);
    HBox orderItemSection = openOrderItemPage();
    
    // NODES
    TableView<Order> ordersTable;
    Button processButton;
    
    // GLOBAL DATA
    private Order selectedOrder = null;
    
	public CashierPanel()
	{
		// GENERALS
		super(StageStyle.DECORATED);
		setTitle("Mystic Grills - Cashier Panel");
        root = new BorderPane();
        Scene scene = new Scene(root, 1280, 720);
        setScene(scene);

        menuBar = createMenuBar();
        root.setTop(menuBar);
        
        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        topSection.setAlignment(Pos.CENTER);
        topSection.getChildren().addAll(contentArea);
        root.setCenter(topSection);

        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setPadding(new Insets(20));
        root.setBottom(bottomSection);
        
        openOrderPage();
	}
	
	private MenuBar createMenuBar()
	{
		 menuBar = new MenuBar();
        Menu orderMenu = new Menu("Order");
        MenuItem orderMenuItem= new MenuItem("Show");
        
        Menu ReceiptMenu = new Menu("Receipt");
        MenuItem ReceiptMenuItem= new MenuItem("Show");

        orderMenu.getItems().addAll(orderMenuItem);
        menuBar.getMenus().addAll(orderMenu);
        
        ReceiptMenu.getItems().addAll(ReceiptMenuItem);
        menuBar.getMenus().addAll(ReceiptMenu);
        
        root.setTop(menuBar);
        
        orderMenuItem.setOnAction(e -> {
        	openOrderPage();
        });
        
        ReceiptMenuItem.setOnAction(e -> {
        	openReceiptPage();
        });

        return menuBar;
    }

	public void openOrderPage()
	{
    	contentArea.getChildren().clear();
    	ordersTable = createOrderTableView();
		contentArea.getChildren().add(ordersTable);
		bottomSection.getChildren().clear();
        GridPane form = OrderForm();
        bottomSection.getChildren().addAll(form, orderItemSection);
    	selectedReceipt = null;
    	loadOrderItemsData();
	}
	
	private GridPane OrderForm()
	{
    	ObservableList<String> options = FXCollections.observableArrayList("Cash", "Debit", "Credit");
    	ComboBox<String> paymentTypeField = new ComboBox<String>(options);
    	
		ordersTable.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedOrder = newSelection;
            	loadOrderItemsData();
            	processButton.setDisable(false);
            	paymentTypeField.setDisable(false);
            }
        });
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
        processButton = new Button("Process Order Payment");
        processButton.setDisable(true);
        form.add(processButton, 0, 1);
        form.add(new Label("Payment Type: "), 0, 0);
        paymentTypeField.setDisable(true);
        form.add(paymentTypeField, 1, 0);
        
        processButton.setOnAction(e ->
        {
			ArrayList<OrderItem> orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());
         
			Date date = new Date(System.currentTimeMillis());
			orderController.updateOrder(selectedOrder.getOrderId(), orderItems, "Paid");
			String res = receiptController.createReceipt(selectedOrder, paymentTypeField.getValue(), selectedOrder.getOrderTotal(), date);
            
            if (res.contains("SUCCESS")) showDialog("Success", "Process Payment success");
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
        tableView.setItems(FXCollections.observableArrayList(orderController.getAllOrdersByOrderStatus("Served")));
        
        return tableView;
    }
    
    @Override
	public void loadOrdersData()
	{
	    ArrayList<Order> preparedOrders = orderController.getAllOrdersByOrderStatus("Served");
	    ordersTable.getItems().setAll(preparedOrders);
	}
	
	private TableView<OrderItem> orderItemTable;
	
	private HBox openOrderItemPage() // Open Order Items Page
	{
		HBox orderItemsSection = new HBox(15);

		ObjectProperty<MenuItems> selected = new SimpleObjectProperty<>(null);

		orderItemTable = createOrderDetailsTableView();
    	
		
        orderItemsSection.getChildren().addAll(orderItemTable);
        return orderItemsSection;
	}
	
	@Override
	public void loadOrderItemsData() 
	{
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		if(selectedOrder != null)
		{
			orderItems = orderItemController.getAllOrderItemsByOrderId(selectedOrder.getOrderId());			
		}
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
	
	// --------------------------------------------------------------------------------------------------------------------------------------------
	//
	//  RECEIPT SECTION
	//
	// --------------------------------------------------------------------------------------------------------------------------------------------
	
	private Receipt selectedReceipt = null;
	private TableView<Receipt> receiptTable;
	
	public void openReceiptPage()
	{
    	contentArea.getChildren().clear();
    	receiptTable = createReceiptTableView();
		contentArea.getChildren().add(receiptTable);
		ReceiptForm();
		bottomSection.getChildren().clear();
        bottomSection.getChildren().addAll(orderItemSection);
    	selectedOrder = null;
    	loadReceiptItemsData();
	}
	
	private void ReceiptForm()
	{
		receiptTable.getSelectionModel().selectedItemProperty()
		.addListener((obs, oldSelection, newSelection) ->
		{
            if (newSelection != null)
            {
            	selectedReceipt = newSelection;
            	loadReceiptItemsData();
            }
        });
	}
	
    private TableView<Receipt> createReceiptTableView()
    {
    	TableView<Receipt> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Receipt, Integer> receiptIdColumn = new TableColumn<>("Receipt ID");
        receiptIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
    	    
        TableColumn<Receipt, String> receiptUserNameColumn = new TableColumn<>("Receipt User Name");
        receiptUserNameColumn.setCellValueFactory(cellData ->
        {
            Receipt receipt = cellData.getValue();
            return new SimpleStringProperty(receipt.getReceiptOrder().getOrderUser().getUserName());
        });
        
        TableColumn<Receipt, Date> receiptDateColumn = new TableColumn<>("Receipt Date");
        receiptDateColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentDate"));

        TableColumn<Receipt, Date> paymentTypeColumn = new TableColumn<>("Payment Type");
        paymentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentType"));

        TableColumn<Receipt, Double> receiptTotalColumn = new TableColumn<>("Receipt Total");
        receiptTotalColumn.setCellValueFactory(cellData ->
        {
            Receipt receipt = cellData.getValue();
            Double orderTotal = receipt.getReceiptOrder().getOrderTotal();
            return Bindings.createObjectBinding(() -> orderTotal);
        });
        
        tableView.getColumns().addAll(receiptIdColumn, receiptUserNameColumn, receiptDateColumn, paymentTypeColumn, receiptTotalColumn);
        tableView.setItems(FXCollections.observableArrayList(receiptController.getAllReceipts()));
        
        return tableView;
    }
	
	public void loadReceiptItemsData() 
	{
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		if(selectedReceipt != null)
		{
			orderItems = orderItemController.getAllOrderItemsByOrderId(selectedReceipt.getReceiptOrder().getOrderId());						
		}
		
	    orderItemTable.getItems().setAll(orderItems);
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
