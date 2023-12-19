package view.AddOrderItemPanel;

import controller.MenuItemController;
import controller.OrderItemController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MenuItems;

public class AddOrderItemPanel extends Stage
{
	IAddOrderItemParentPanel parent;
	OrderItemController orderItemController = new OrderItemController();
	TableView<MenuItems> table;
	int orderId;
	MenuItems selected;
	TextField quantityField;
	Button addButton;
	
	
	public void setSelected(MenuItems menuItem)
	{
		this.selected = menuItem;
	}
	
	public AddOrderItemPanel(IAddOrderItemParentPanel parent, int orderId)
	{
		this.parent = parent;
		this.orderId = orderId;
		setTitle("Add New Menu Item");
		
		VBox panel = getPage();
		
		Scene scene = new Scene(panel, 800, 600);
        setScene(scene);
	}
	
    // MENU ITEM PAGE
    private VBox getPage()
    {
    	VBox container = new VBox(15);
    	container.setPadding(new Insets(20));
    	
    	//Creating a TableView for MenuItem
    	table = createMenuItemTableView();
		
    	//Adding Listner for MenuItem TableView
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			setSelected(newSelection);
			quantityField.setDisable(false);
			addButton.setDisable(false);
        });
		
		GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(10);
		
        quantityField = new TextField();
        
        addButton = new Button("Add");
        
        form.add(new Label("Quantity:"), 0, 0);
        quantityField.setDisable(true);
        form.add(quantityField, 1, 0);
        addButton.setDisable(true);
        form.add(addButton, 0, 1);
        
        addButton.setOnAction(e ->
        {
        	//Getting the value
			int quantity = Integer.parseInt(quantityField.getText());
			
			//Creating a new OrderItem
			String result = orderItemController.createOrderItem(orderId, selected, quantity);
			parent.loadOrderItemsData();
			parent.loadOrdersData();
			this.close();
        });
        
        container.getChildren().add(table);
        container.getChildren().add(form);
		return container;
    }
    
    //Creating A Table View for MenuItem
    private TableView<MenuItems> createMenuItemTableView()
    {
    	TableView<MenuItems> tableView = new TableView<>();
    	tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	    
        TableColumn<MenuItems, String> menuItemNameColumn = new TableColumn<>("Name");
        menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        menuItemNameColumn.setPrefWidth(150);
        
        TableColumn<MenuItems, String> menuItemDescriptionColumn = new TableColumn<>("Description");
        menuItemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemDescription"));
        menuItemDescriptionColumn.setPrefWidth(150);
        
        TableColumn<MenuItems, Integer> menuItemPriceColumn = new TableColumn<>("Price");
        menuItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemPrice"));
        menuItemPriceColumn.setPrefWidth(150);       
        
        tableView.getColumns().addAll(menuItemNameColumn, menuItemDescriptionColumn, menuItemPriceColumn);
        tableView.setItems(FXCollections.observableArrayList(MenuItemController.getAllMenuItem()));
        
        return tableView;
    }
}
