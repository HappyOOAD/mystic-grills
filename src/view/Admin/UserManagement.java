package view.Admin;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserManagement extends Stage {
    public UserManagement() {
        setTitle("User Management - Admin View");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // User Role Dropdown
        Label userRoleLabel = new Label("Select User Role:");
        grid.add(userRoleLabel, 0, 0);

        ComboBox<String> userRoleDropdown = new ComboBox<>();
        userRoleDropdown.getItems().addAll("Admin", "Chef", "Waiter", "Cashier", "Customer");
        grid.add(userRoleDropdown, 1, 0);

        // User List
        Label userListLabel = new Label("User List:");
        grid.add(userListLabel, 0, 1);

        ListView<String> userListView = new ListView<>();
        userListView.getItems().addAll("User1", "User2", "User3");  // Add sample user names
        grid.add(userListView, 1, 1);

        // Remove User Button
        Button removeUserButton = new Button("Remove User");
        grid.add(removeUserButton, 1, 2);

        // Change User Role Button
        Button changeRoleButton = new Button("Change User Role");
        grid.add(changeRoleButton, 1, 3);

        // Event handlers for buttons
        removeUserButton.setOnAction(event -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Perform logic to remove the selected user
                userListView.getItems().remove(selectedUser);
                System.out.println("User removed: " + selectedUser);
            } else {
                System.out.println("Please select a user to remove.");
            }
        });

        changeRoleButton.setOnAction(event -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            String selectedRole = userRoleDropdown.getValue();
            if (selectedUser != null && selectedRole != null) {
                // Perform logic to change user role
                System.out.println("Changed role for " + selectedUser + " to " + selectedRole);
            } else {
                System.out.println("Please select a user and a role to change.");
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        setScene(scene);
    }
}
