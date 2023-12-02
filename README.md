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

CREATE TABLE menuitem(
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


CREATE TABLE orderitem(
    orderItemId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    orderId INT NOT NULL,
    menuItemId INT NOT NULL,
    quantity INT NOT NULL,
    
    CONSTRAINT fk_menu_item_ids FOREIGN KEY (menuItemId) REFERENCES menuitem(menuItemId),
    CONSTRAINT fk_order_id FOREIGN KEY (orderId) REFERENCES orders(orderId)
);

CREATE TABLE receipt(
    receiptId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    orderId INT NOT NULL,
    receiptPaymentAmount DOUBLE NOT NULL,
    receiptPaymentDate DATE NOT NULL,
    receiptPaymentType VARCHAR(30) NOT NULL,
    
    
    CONSTRAINT fk_order_ids FOREIGN KEY (orderId) REFERENCES orders(orderId)  
);
```
