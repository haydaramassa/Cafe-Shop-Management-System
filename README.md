# Cafe Shop Management System (JavaFX + MySQL)

A JavaFX desktop POS system for a cafe/restaurant.  
It includes inventory management with product images, order workflow, payment & receipt printing, customers history, and a dashboard with sales charts.  
Data is stored in a MySQL database (managed via phpMyAdmin).

---

## Features

- **Authentication**: Login / Register + forgot password.
- **Dashboard**:
  - Summary cards:
    - Number of Customers
    - Today’s Income
    - Total Income
    - Number of Sold Products
  - Charts:
    - Income’s Chart (Total Income per Day)
    - Customers’ Chart (Number of Receipts per Day)
  - Actions:
    - Reset / Close Period
    - Receipts Archive / History (Archive of Receipts):
      - Receipts List / Transactions table columns:
        - Receipt ID
        - Customer ID
        - Date
        - Total
        - Cashier
    - Receipt Details section:
     - Shows Cashier name
     - Details table columns:
        - Customer ID
        - Type
        - Qty
        - Price
  - Printing:
    - Print Details (prints selected receipt details)
    - Print All Receipts
- **Inventory**:
  - Add / update / clear / delete products
  - Stock, price, status, type
  - Import product images
- **Menu & Orders**:
  - Product cards with images
  - Add items with quantities to cart
  - Payments & Receipts:
      - Payment with total/amount/change
      - Generate receipt and **print**
      - Delete an order **Remove**
- **Customers**:
  - View customers served with totals, date, cashier

---

## Tech Stack

- Java
- JavaFX
- MySQL (phpMyAdmin)
- Maven (`pom.xml`)
- NetBeans (project)

---

## Project Structure (important files)

- **Main class**: `src/main/java/com/mycompany/cafeshopmanagementsystemtest/App.java`
- **Database connection**: `src/main/java/com/mycompany/cafeshopmanagementsystemtest/database.java`

> ⚠️ Do not commit real database passwords. Use placeholders or a local config file ignored by Git.

---

## Database Setup (MySQL)

1. Create a database in phpMyAdmin (example: `cafe_shop_db`).
2. Create the required tables (products, orders, receipts, customers, etc.)
3. Update your DB credentials in:
   - `database.java` (host / port / db name / username / password)

If you have an `.sql` export, import it using phpMyAdmin for fastest setup.

---

## How to Run

### Run from NetBeans (recommended)
1. Open the project in NetBeans.
2. Let Maven download dependencies.
3. Run the main class: `App.java`


