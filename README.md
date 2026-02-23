# Cafe Shop Management System (JavaFX + MySQL)

A JavaFX desktop POS system for a cafe/restaurant.  
It includes inventory management with product images, order workflow, payment & receipt printing, customers history, and a dashboard with sales charts.  
Data is stored in a MySQL database (managed via phpMyAdmin).

---

## Features

- **Authentication**: Login / Register + forgot password.
- **Dashboard**:
  - Sales & customers charts
  - Summary cards (customers, income, etc.)
  - Period reset / close and access to receipts archive
- **Inventory**:
  - Add / update / delete products
  - Stock, price, status, type
  - Import product images
- **Menu & Orders**:
  - Product cards with images
  - Add items with quantities to cart
- **Payments & Receipts**:
  - Payment with total/amount/change
  - Generate receipt and **print**
- **Customers**:
  - View customers served with totals, date, cashier
- **Receipts Archive / History**:
  - Browse old receipts
  - View receipt details and **print** (single or all)

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

### Option A: Run from NetBeans (recommended)
1. Open the project in NetBeans.
2. Let Maven download dependencies.
3. Run the main class: `App.java`

### Option B: Build with Maven
```bash
mvn clean package