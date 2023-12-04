# Mystic Grills

## SQL Schema
```sql
CREATE DATABASE mysticgrills;

CREATE TABLE users(
    userId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userRole VARCHAR(20) NOT NULL,
    userName VARCHAR(30) NOT NULL,
    userEmail VARCHAR(30) NOT NULL,
    userPassword VARCHAR(30) NOT NULL  
);

CREATE TABLE menuitems(
    menuItemId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    menuItemName VARCHAR(30) NOT NULL,
    menuItemDescription VARCHAR(30) NOT NULL,
    menuItemPrice DOUBLE NOT NULL 
);

CREATE TABLE orders(
    orderId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    orderStatus VARCHAR(30) NOT NULL,
    orderDate DATE NOT NULL,
    
    CONSTRAINT fk_user_id FOREIGN KEY (userId) REFERENCES users(userId)  
);


CREATE TABLE orderitems(
    orderId INT NOT NULL,
    menuItemId INT NOT NULL,
    quantity INT NOT NULL,

    PRIMARY KEY (orderId, menuItemId),
    CONSTRAINT fk_menu_item_ids FOREIGN KEY (menuItemId) REFERENCES menuitem(menuItemId),
    CONSTRAINT fk_order_id FOREIGN KEY (orderId) REFERENCES orders(orderId)
);

CREATE TABLE receipts(
    receiptId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    orderId INT NOT NULL,
    receiptPaymentAmount DOUBLE NOT NULL,
    receiptPaymentDate DATE NOT NULL,
    receiptPaymentType VARCHAR(30) NOT NULL,
    
    CONSTRAINT fk_order_ids FOREIGN KEY (orderId) REFERENCES orders(orderId)  
);
```

## SQL Dummies
```sql
INSERT INTO users (userRole, userName, userEmail, userPassword)
VALUES
    ('Admin', 'Admin User', 'admin@example.com', 'adminpassword'),
    ('Customer', 'John Doe', 'john.doe@example.com', 'johnpassword'),
    ('Customer', 'Jane Doe', 'jane.doe@example.com', 'janepassword'),
    ('Customer', 'Alice Smith', 'alice.smith@example.com', 'alicepassword'),
    ('Customer', 'Bob Johnson', 'bob.johnson@example.com', 'bobpassword'),
    ('Admin', 'Super Admin', 'superadmin@example.com', 'superadminpassword');

INSERT INTO menuitems (menuItemName, menuItemDescription, menuItemPrice)
VALUES
    ('Grilled Chicken', 'Juicy grilled chicken served with vegetables', 15.99),
    ('Vegetarian Pasta', 'Pasta with a mix of fresh vegetables', 12.99),
    ('BBQ Ribs', 'Tender BBQ ribs with a side of coleslaw', 18.99),
    ('Salmon Steak', 'Grilled salmon steak with lemon butter sauce', 22.99),
    ('Margherita Pizza', 'Classic pizza with tomato, mozzarella, and basil', 14.99),
    ('Shrimp Scampi', 'Garlic butter shrimp served over pasta', 19.99);

INSERT INTO orders (userId, orderStatus, orderDate)
VALUES
    (2, 'Pending', '2023-12-01'),
    (3, 'Completed', '2023-12-02'),
    (2, 'Processing', '2023-12-03'),
    (4, 'Completed', '2023-12-04'),
    (5, 'Pending', '2023-12-05'),
    (4, 'Processing', '2023-12-06');

INSERT INTO orderitems (orderId, menuItemId, quantity)
VALUES
    (1, 2),
    (2, 1),
    (3, 3),
    (1, 1),
    (2, 2),
    (3, 2),
    (2, 1),
    (1, 3),
    (2, 2),
    (3, 1);

INSERT INTO receipts (orderId, receiptPaymentAmount, receiptPaymentDate, receiptPaymentType)
VALUES
    (1, 43.97, '2023-12-01', 'Credit Card'),
    (2, 56.97, '2023-12-02', 'PayPal'),
    (3, 74.97, '2023-12-03', 'Cash'),
    (4, 45.98, '2023-12-04', 'Debit Card'),
    (5, 29.99, '2023-12-05', 'Cash'),
    (6, 78.96, '2023-12-06', 'Credit Card');
```
