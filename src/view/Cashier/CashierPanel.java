package view.Cashier;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Order;
import model.User;

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
        Menu MenuCashierReceipts = new Menu("Receipts");
        MenuItem MenuCashierReceiptsItem = new MenuItem("Show");

        MenuCashierReceipts.getItems().addAll(MenuCashierReceiptsItem);
        menuBar.getMenus().addAll(MenuCashierReceipts);
        
        Menu MenuCashierOrders = new Menu("Orders");
        MenuItem MenuCashierOrdersItem = new MenuItem("Show");

        MenuCashierOrders.getItems().addAll(MenuCashierOrdersItem);
        menuBar.getMenus().addAll(MenuCashierOrders);
        
        root.setTop(menuBar);
        
        MenuCashierReceiptsItem.setOnAction(e -> {
        	System.out.println("Clicked");
        	Receipts();
        });
        MenuCashierOrdersItem.setOnAction(e -> {
        	System.out.println("Clicked");
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
		contentArea.getChildren().clear();
        
        TableView<Order> accountsTable = createAccountsTableView();
        contentArea.getChildren().add(accountsTable);
        
        setupTableSelectionListener();
        GridPane form = createUserForm(accountsTable);
        
        VBox.setMargin(form, new Insets(20));
        contentArea.getChildren().add(form);
		
	}

	private void Receipts() {
		// TODO Auto-generated method stub
		
	}

}