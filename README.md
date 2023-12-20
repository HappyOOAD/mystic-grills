# Mystic Grills

## SQL Dummies
```sql
INSERT INTO users (userRole, userName, userEmail, userPassword)
VALUES
    ('Admin', 'Admin User', 'admin@example.com', 'adminpassword'),
    ('Customer', 'John Doe', 'john.doe@example.com', 'johnpassword'),
    ('Customer', 'Jane Doe', 'jane.doe@example.com', 'janepassword'),
    ('Customer', 'Alice Smith', 'alice.smith@example.com', 'alicepassword'),
    ('Customer', 'Bob Johnson', 'bob.johnson@example.com', 'bobpassword'),
    ('Admin', 'Super Admin', 'superadmin@example.com', 'superadminpassword'),
    ('Chef', 'Gandi', 'chef@gmail.com', 'chef'),
    ('Waiter', 'Makro', 'waiter@gmail.com', 'waiter'),
    ('Customer', 'Joni', 'customer@gmail.com', 'customer'),
    ('Admin', 'Kevin', 'admin@gmail.com', 'admin'),
    ('Cashier', 'Salsha', 'cashier@gmail.com', 'cashier');

INSERT INTO menuitems (menuItemName, menuItemDescription, menuItemPrice)
VALUES
    ('Grilled Chicken', 'Juicy grilled chicken served with vegetables', 15.99),
    ('Vegetarian Pasta', 'Pasta with a mix of fresh vegetables', 12.99),
    ('BBQ Ribs', 'Tender BBQ ribs with a side of coleslaw', 18.99),
    ('Salmon Steak', 'Grilled salmon steak with lemon butter sauce', 22.99),
    ('Margherita Pizza', 'Classic pizza with tomato, mozzarella, and basil', 14.99),
    ('Shrimp Scampi', 'Garlic butter shrimp served over pasta', 19.99),
        ('Steakhouse Burger', 'Juicy beef patty with cheese and bacon', 16.99),
    ('Chicken Caesar Salad', 'Fresh salad with grilled chicken and Caesar dressing', 13.99),
    ('Vegetable Stir-Fry', 'Assorted vegetables stir-fried in a savory sauce', 11.99),
    ('Tiramisu', 'Classic Italian dessert with coffee-flavored layers', 8.99),
    ('Fish Tacos', 'Tacos filled with grilled fish and slaw', 17.99),
    ('Mango Sorbet', 'Refreshing mango-flavored sorbet', 6.99);

INSERT INTO orders (userId, orderStatus, orderDate)
VALUES
    (2, 'Pending', '2023-12-01'),
    (3, 'Pending', '2023-12-02'),
    (2, 'Pending', '2023-12-03'),
    (4, 'Pending', '2023-12-04'),
    (5, 'Pending', '2023-12-05'),
    (4, 'Pending', '2023-12-06'),
    (6, 'Served', '2023-12-07'),
    (7, 'Served', '2023-12-08'),
    (8, 'Served', '2023-12-09');

INSERT INTO orderitems (orderId, menuItemId, quantity)
VALUES
    (1, 2, 2),
    (2, 1, 1),
    (3, 3, 3),
    (1, 1, 1),
    (2, 2, 2),
    (3, 2, 2),
    (1, 3, 1),
    (3, 1, 1),
    (4, 4, 2),
    (5, 5, 3),
    (6, 6, 1),
    (4, 1, 2),
    (5, 2, 1),
    (6, 3, 2),
    (7, 1, 1),
    (7, 2, 2),
    (8, 3, 1);

INSERT INTO receipts (orderId, receiptPaymentAmount, receiptPaymentDate, receiptPaymentType)
VALUES
    (1, 43.97, '2023-12-01', 'Credit'),
    (2, 56.97, '2023-12-02', 'Cash'),
    (3, 74.97, '2023-12-03', 'Cash'),
    (4, 45.98, '2023-12-04', 'Debit'),
    (5, 29.99, '2023-12-05', 'Cash'),
    (6, 78.96, '2023-12-06', 'Credit'),
    (7, 33.98, '2023-12-07', 'Credit'),
    (8, 45.97, '2023-12-08', 'Cash'),
    (9, 26.99, '2023-12-09', 'Debit');
```
