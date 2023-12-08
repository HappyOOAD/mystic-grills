package view.Cashier;

import java.sql.Date;

import controller.MenuItemController;
import controller.ReceiptController;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MenuItems;
import model.Order;
import model.Receipt;

public class CashierPanel extends Stage
{

	/* 
		Cashier
		----------------------
		- Can View Receipts
		- Can View Orders
		- Can Process Order
		- Can View Order Details
	*/
	
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
	
	public CashierPanel() {
		 super(StageStyle.DECORATED);

        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

        menuBar = new MenuBar();
        Menu Receipts = new Menu("Receipts");
        MenuItem ReceiptsItem= new MenuItem("Receipt");

        Receipts.getItems().addAll(ReceiptsItem);
        menuBar.getMenus().addAll(Receipts);
        
        Menu Orders = new Menu("Orders");
        MenuItem OrdersItem = new MenuItem("Show");

        Orders.getItems().addAll(OrdersItem);
        menuBar.getMenus().addAll(Orders);
        
        root.setTop(menuBar);
        
        ReceiptsItem.setOnAction(e -> {
        	Receipts();
        });
        OrdersItem.setOnAction(e -> {
        	Orders();
        });

        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(20));
        contentArea.setAlignment(Pos.CENTER);
        
        VBox contentDivide = new VBox(15);
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll( contentArea);
        root.setCenter(contentDivide);
	}

	private void Orders() {
		Label orderIdLabel = new Label("Order ID:");
        Label totalOrderPriceLabel = new Label("Total Order Price:");
        Label paymentTypeLabel = new Label("Payment Type:");
        Label paymentAmountLabel = new Label("Payment Amount:");
		
		contentArea.getChildren().clear();
    	TableView<Order>OrderTable = createOrderTableView();
		contentArea.getChildren().add(OrderTable);
		
		OrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            	orderDetail();
            }
        });
	}

	private void orderDetail() {
		contentArea.getChildren().clear();
    	
		
	}

	private TableView<Order> createOrderTableView() {
		TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Order, Integer> OrderIDColumn = new TableColumn<>("OrderID");
        OrderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        OrderIDColumn.setPrefWidth(150);
        
        TableColumn<Order, Integer> UserIDColumn = new TableColumn<>("UserID");
        UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        UserIDColumn.setPrefWidth(150);
        
        TableColumn<Order, String> OrderStatusColumn = new TableColumn<>("Status");
        OrderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        OrderStatusColumn.setPrefWidth(150);
        
        TableColumn<Order, Date> OrderDateColumn = new TableColumn<>("Date");
        OrderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        OrderDateColumn.setPrefWidth(150);
        
        tableView.getColumns().addAll(OrderIDColumn, UserIDColumn, OrderStatusColumn, OrderDateColumn);
        tableView.setItems(FXCollections.observableArrayList(Order.getAllOrders()));
        
		return tableView;
	}

	private void Receipts() {
		contentArea.getChildren().clear();
    	TableView<Receipt>ReceiptsTable = createReceiptsTableView();
		contentArea.getChildren().add(ReceiptsTable);
		
	}

	private TableView<Receipt> createReceiptsTableView() {
		TableView<Receipt> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Receipt, Integer> ReceiptIDColumn = new TableColumn<>("ReceiptID");
        ReceiptIDColumn.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        ReceiptIDColumn.setPrefWidth(150);
        
        TableColumn<Receipt, Integer> OrderIDColumn = new TableColumn<>("OrderID");
        ReceiptIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ReceiptIDColumn.setPrefWidth(150);
        
        TableColumn<Receipt, Double> ReceiptAmountColumn = new TableColumn<>("Amount");
        ReceiptIDColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentAmount"));
        ReceiptIDColumn.setPrefWidth(150);
        
        TableColumn<Receipt, Date> ReceiptDateColumn = new TableColumn<>("Date");
        ReceiptIDColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentDate"));
        ReceiptIDColumn.setPrefWidth(150);
        
        TableColumn<Receipt, String> ReceiptPaymentTypeColumn = new TableColumn<>("Payment Type");
        ReceiptIDColumn.setCellValueFactory(new PropertyValueFactory<>("receiptPaymentType"));
        ReceiptIDColumn.setPrefWidth(150);
        
        tableView.getColumns().addAll(ReceiptIDColumn, OrderIDColumn, ReceiptAmountColumn, ReceiptDateColumn, ReceiptPaymentTypeColumn);
        tableView.setItems(FXCollections.observableArrayList(Receipt.getAllReceipts()));
        
		return tableView;
	}

}
