package view.Cashier;


import java.util.ArrayList;
import java.sql.Date;
import java.util.stream.Collectors;

import controller.MenuItemController;
import controller.OrderController;
import controller.ReceiptController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Order;
import model.OrderItem;
import model.Receipt;

public class CashierPanel extends Stage
{
	
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    TableView<Order>menuItemTable;
    TableView<Receipt>ReceiptTable;
    
	public CashierPanel()
	{
		super(StageStyle.DECORATED);

		setTitle("Mystic Grills - Cashier");
        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

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
        	openNewPageOrder();
        });
        
        ReceiptMenuItem.setOnAction(e -> {
        	openNewPageReceipt();
        });

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
	}

	
    private void openNewPageReceipt() {
    	contentArea.getChildren().clear();
    	ReceiptTable = createReceiptTableView();
		contentArea.getChildren().add(ReceiptTable);
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
        
        contentArea.getChildren().add(form);
	}


	private void showSuccessDialog(String successMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }
    
    
    private void showErrorDialog(String errorMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
	
	private void Listener(TableView<Order>menuItemTable, TextField orderId, TextField userId, TextField orderStatus, TextField orderDate) {
		menuItemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	orderId.setText(newSelection.getOrderId()+"");
            	userId.setText(newSelection.getOrderUserId()+"");
            	orderStatus.setText(String.valueOf(newSelection.getOrderStatus()));
            	orderDate.setText(String.valueOf(newSelection.getOrderDate()));
            }
        });
	}
	
	private void openNewPageOrder() {
    	TextField orderId = new TextField();
    	TextField userId = new TextField();
    	TextField orderStatus = new TextField();
    	TextField orderDate = new TextField();
		
    	TextField receiptId = new TextField();
    	TextField receiptPaymentAmount = new TextField();
    	TextField receiptPaymentType = new TextField();
    	
    	contentArea.getChildren().clear();
    	menuItemTable = createOrderTableView();
		contentArea.getChildren().add(menuItemTable);
	
		Listener(menuItemTable, orderId, userId, orderStatus, orderDate);
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
        Button processButton = new Button("Process Order Payment");
        
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
        form.add(orderDate, 1, 3);
        orderDate.setDisable(true);
        
        
        form.add(new Label("Receipt ID:"), 2, 0);
        form.add(receiptId, 3, 0);
        receiptId.setDisable(true);
        form.add(new Label("Receipt Payment Amount:"), 2, 1);
        form.add(receiptPaymentAmount, 3, 1);
        
        form.add(new Label("Receipt Payment Type:"), 2, 2);
        form.add(receiptPaymentType, 3, 2);
        
        form.add(processButton, 0, 5);
        
        
        processButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(orderId.getText());
        	Double receiptsPaymentAmount = Double.parseDouble(receiptPaymentAmount.getText());
        	String receiptsPaymentType = receiptPaymentType.getText();
        	Date date = new Date(System.currentTimeMillis());
			String status = "Paid";
			
			Order x = Order.getOrderById(id);
			ArrayList<OrderItem> orderItem = x.getOrderItems();
            
            String receiptAdd = ReceiptController.createReceipt(x, receiptsPaymentType, receiptsPaymentAmount, date);
            
            if ("Create Receipt Success".equals(receiptAdd)) {
                showSuccessDialog("Add Receipt success");
                
                
                String orderUpdating = OrderController.updateOrder(id, orderItem, status);
                
                if ("Success Update Order".equals(orderUpdating)) {
                    showSuccessDialog("Update Order success");
                    loadOrdersData();
                } else {
                    showErrorDialog(orderUpdating);
                    loadOrdersData();
                }
                
                loadOrdersData();
            } else {
                showErrorDialog(receiptAdd);
                loadOrdersData();
            }
            
        });
        
        contentArea.getChildren().add(form);
	}
	
	private void loadOrdersData() {
	    ArrayList<Order> allOrders = OrderController.getAllOrders();

	    ArrayList<Order> prepareOrders = allOrders.stream()
	            .filter(order -> "served".equalsIgnoreCase(order.getOrderStatus()))
	            .collect(Collectors.toCollection(ArrayList::new));

	    menuItemTable.getItems().setAll(prepareOrders);
	}
	
    private TableView<Order> createOrderTableView() {
    	TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdColumn.setPrefWidth(150);
    	    
        TableColumn<Order, Integer> orderUserIdColumn = new TableColumn<>("order User Id");
        orderUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderUserId"));
        orderUserIdColumn.setPrefWidth(150);
        
        TableColumn<Order, String> orderStatusColumn = new TableColumn<>("order Status");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderStatusColumn.setPrefWidth(150);
        
        TableColumn<Order, Date> orderDateColumn = new TableColumn<>("order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderDateColumn.setPrefWidth(150);
        
        tableView.getColumns().addAll(orderIdColumn, orderUserIdColumn, orderStatusColumn, orderDateColumn);
        tableView.setItems(FXCollections.observableArrayList(OrderController.getAllOrdersByOrderStatus("Served")));
        
        return tableView;
    }
    
    private TableView<Receipt> createReceiptTableView() {
    	TableView<Receipt> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Receipt, Integer> receiptIdColumn = new TableColumn<>("receipt ID");
        receiptIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        receiptIdColumn.setPrefWidth(150);
    	    
        TableColumn<Receipt, Integer> orderIdColumn = new TableColumn<>("order Id");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("receiptOrderId"));
        orderIdColumn.setPrefWidth(150);
        
        TableColumn<Receipt, Double> receiptPaymentAmountColumn = new TableColumn<>("Receipt Payment Amount");
        receiptPaymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentAmount"));
        receiptPaymentAmountColumn.setPrefWidth(150);
        
        TableColumn<Receipt, Date> receiptPaymentDate = new TableColumn<>("Receipt Payment Date");
        receiptPaymentDate.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentDate"));
        receiptPaymentDate.setPrefWidth(150);
        
        TableColumn<Receipt, String> receiptPaymentType = new TableColumn<>("Receipt Payment Type");
        receiptPaymentType.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentType"));
        receiptPaymentType.setPrefWidth(150);
        
        
        tableView.getColumns().addAll(receiptIdColumn, orderIdColumn, receiptPaymentAmountColumn, receiptPaymentType, receiptPaymentDate);
        tableView.setItems(FXCollections.observableArrayList(ReceiptController.getAllReceipts()));
        
        return tableView;
    }


}