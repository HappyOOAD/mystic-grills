package view.UpdateOrder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import model.OrderItem;



public class UpdateOrderPanel extends Stage{
	
	public UpdateOrderPanel(int orderId) {
        TableView<OrderItem> tableView = new TableView<>();
        TableColumn<OrderItem, Integer> orderIdColumn = new TableColumn<>("Order ID");
        TableColumn<OrderItem, Integer> menuItemIdColumn = new TableColumn<>("Menu Item ID");
        TableColumn<OrderItem, Integer> quantityColumn = new TableColumn<>("Quantity");       

        // Set cell value factories to extract data from OrderItem properties
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        menuItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableView.getColumns().addAll(orderIdColumn, menuItemIdColumn, quantityColumn);

        ObservableList<OrderItem> orderItems = FXCollections.observableArrayList(OrderItem.getAllOrderItemsByOrderId(orderId));
        tableView.setItems(orderItems);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 1200, 600);

        setScene(scene);
        setTitle("Order Items Table View");
        
        setupTableSelectionListener1();
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
	
}
