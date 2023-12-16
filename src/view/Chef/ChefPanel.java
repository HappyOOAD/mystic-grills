package view.Chef;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import controller.OrderController;

public class ChefPanel extends Stage
{
	private BorderPane root;
    private VBox contentArea;
    private MenuBar menuBar;
    TableView<Order>menuItemTable;
    private OrderController controller = new OrderController();
    
	public ChefPanel()
	{
		super(StageStyle.DECORATED);

		setTitle("Mystic Grills - Chef Panel");
        root = new BorderPane();
        Scene scene = new Scene(root, 1200, 600);
        this.setScene(scene);

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
        
        VBox contentDivide = new VBox(15);
        openOrderPage();
        contentDivide.setAlignment(Pos.TOP_CENTER);
        contentDivide.getChildren().addAll(contentArea);
        root.setCenter(contentDivide);
	}
	
	private void loadOrdersData() // Load Orders
	{
	    ArrayList<Order> preparedOrders = controller.getAllOrdersByOrderStatus("Pending");
	    menuItemTable.getItems().setAll(preparedOrders);
	}
	
	private void openOrderPage()
	{
    	TextField orderId = new TextField();
    	TextField userId = new TextField();
    	TextField orderStatus = new TextField();
    	TextField orderDate = new TextField();
		
    	contentArea.getChildren().clear();
    	menuItemTable = createOrderTableView();
		contentArea.getChildren().add(menuItemTable);
	
		Listener(menuItemTable, orderId, userId, orderStatus, orderDate);
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
		Button updateButton = new Button("Update");
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
        
        form.add(updateButton, 0, 5);
        form.add(deleteButton, 1, 5);
        form.add(prepareButton, 2, 5);
        
        updateButton.setOnAction(e ->
        {
			int id= Integer.parseInt(orderId.getText());
			String status = orderStatus.getText();
			
			
			Order x = controller.getOrderByOrderId(id);
			
			ArrayList<OrderItem> orderItem = x.getOrderItems(); 
            String updatingOrder = controller.updateOrder(id, orderItem, status);
            
            if (updatingOrder.contains("SUCCESS"))
            {
                showSuccessDialog("Update success");
                loadOrdersData();
            } else {
                showErrorDialog(updatingOrder);
                loadOrdersData();
            }
            
        });
        
        deleteButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(orderId.getText());
        	String deleteOrder = controller.deleteOrder(id);
        	
        	if (deleteOrder.contains("SUCCESS"))
        	{
                showSuccessDialog("Delete success");
                loadOrdersData();
            } 
        	else 
        	{
                showErrorDialog(deleteOrder);
                loadOrdersData();
            }
        });
        
        prepareButton.setOnAction(e ->
        {
        	int id= Integer.parseInt(orderId.getText());
			String status = "Prepared";
			
			Order x = Order.getOrderById(id);
			ArrayList<OrderItem> orderItem = x.getOrderItems(); 
            String updatingOrder = controller.updateOrder(id, orderItem, status);
            
            if (updatingOrder.contains("SUCCESS"))
            {
                showSuccessDialog("Update success");
                loadOrdersData();
            } 
            else 
            {
                showErrorDialog(updatingOrder);
                loadOrdersData();
            }
        });
        
        contentArea.getChildren().add(form);
	}
	
    private void showSuccessDialog(String successMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(successMessage);
        alert.showAndWait();
    }
    
    
    private void showErrorDialog(String errorMessage) {
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
	
    private TableView<Order> createOrderTableView()
    {
    	TableView<Order> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdColumn.setPrefWidth(150);
    	    
        TableColumn<Order, Integer> orderUserIdColumn = new TableColumn<>("Order User ID");
        orderUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderUserId"));
        orderUserIdColumn.setPrefWidth(150);
        
        TableColumn<Order, String> orderStatusColumn = new TableColumn<>("Order Status");
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderStatusColumn.setPrefWidth(150);
        
        TableColumn<Order, Date> orderDateColumn = new TableColumn<>("Order Date");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderDateColumn.setPrefWidth(150);
               
        tableView.getColumns().addAll(orderIdColumn, orderUserIdColumn, orderStatusColumn, orderDateColumn);
        tableView.setItems(FXCollections.observableArrayList(controller.getAllOrdersByOrderStatus("Pending")));
        
        return tableView;
    }
}
