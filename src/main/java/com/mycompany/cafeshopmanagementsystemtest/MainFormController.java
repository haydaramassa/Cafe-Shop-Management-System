package com.mycompany.cafeshopmanagementsystemtest;

import com.sun.source.tree.NewArrayTree;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class MainFormController implements Initializable {

    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn< productData, String> inventory_col_date;

    @FXML
    private TableColumn<productData, String> inventory_col_price;

    @FXML
    private TableColumn<productData, String> inventory_col_productID;

    @FXML
    private TableColumn<productData, String> inventory_col_productName;

    @FXML
    private TableColumn<productData, String> inventory_col_status;

    @FXML
    private TableColumn<productData, String> inventory_col_stock;

    @FXML
    private TableColumn<productData, String> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TableView<productData> inventory_tableView;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private FontAwesomeIconView logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Label username;
    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_productName;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private TextField inventory_stock;

    @FXML
    private ComboBox<?> inventory_type;

    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<productData, String> menu_col_price;

    @FXML
    private TableColumn<productData, String> menu_col_productName;

    @FXML
    private TableColumn<productData, String> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<productData> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private TableColumn<customersData, String> customers_col_cashier;

    @FXML
    private TableColumn<customersData, String> customers_col_customerID;

    @FXML
    private TableColumn<customersData, String> customers_col_date;

    @FXML
    private TableColumn<customersData, String> customers_col_total;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableView<customersData> customers_tableView;

    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;

    @FXML
    private AreaChart<?, ?> dashboard_IncomeChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NSP;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TotalI;

    @FXML
    private Button reset_btn;

    @FXML
    private AnchorPane customers_content;

    @FXML
    private TableView<ArchiveReceiptData> tblReceipts;
    @FXML
    private TableColumn<ArchiveReceiptData, Integer> colReceiptId;
    @FXML
    private TableColumn<ArchiveReceiptData, Integer> colCustomerId;
    @FXML
    private TableColumn<ArchiveReceiptData, java.sql.Date> colReceiptDate;
    @FXML
    private TableColumn<ArchiveReceiptData, Double> colReceiptTotal;
    @FXML
    private TableColumn<ArchiveReceiptData, String> colReceiptCashier;

    @FXML
    private TableView<ArchiveItemData> tblReceiptItems;
    @FXML
    private TableColumn<ArchiveItemData, Integer> colItemCustomerId;
    @FXML
    private TableColumn<ArchiveItemData, String> colItemType;
    @FXML
    private TableColumn<ArchiveItemData, Integer> colItemQty;
    @FXML
    private TableColumn<ArchiveItemData, Double> colItemPrice;

    @FXML
    private AnchorPane Archive_Of_Receipts_Form;

    @FXML
    private Button archive_printAllReceiptsBtn;

    @FXML
    private Button archive_printDetailsBtn;

    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Image image;
    private int lastPaidCustomerId = 0;
    //private double paidAmount = 0; // total money customer has paid so far

    @FXML
    private Label details_cashierLabel;

// Holds the list of products shown in the Menu screen as "cards" (GridPane items).
// ObservableList is required by JavaFX so UI updates automatically when the list changes.
    ObservableList<productData> cardListData = FXCollections.observableArrayList();

    public void dashboardDisplayNC() {
// NC = Number of Customers/Receipts (here: number of rows in receipt table)
        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connectDB();

        try {
            int nc = 0;
            // Run query: COUNT(id) returns a single row with a single column
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                // NOTE (tricky): Your column label is "COUNT(id)" because you didn't use an alias in SQL.
                // Safer style is: SELECT COUNT(id) AS cnt ... then result.getInt("cnt")
                nc = result.getInt("COUNT(id)");
            }
            // Show result on dashboard label
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardDisplayTI() {
        // TI = Today's Income (sum of totals for receipts with today's date)

        // Get today's date as java.sql.Date so it matches the DB DATE column format
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        // NOTE (tricky): You are building SQL by concatenation.
        // It works here because sqlDate prints as YYYY-MM-DD, but prepared params are safer.
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";

        connect = database.connectDB();

        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                // NOTE (tricky): The column label is "SUM(total)" because no alias was used.
                // Safer style is: SELECT SUM(total) AS sum_total ... then result.getDouble("sum_total")
                ti = result.getDouble("SUM(total)");
                // Alternative safe way: ti = result.getDouble(1);
            }
            // Display as currency format
            dashboard_TI.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardTotalI() {
// Total Income (all-time) from the live receipt table
        String sql = "SELECT SUM(total) FROM receipt";

        connect = database.connectDB();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                // NOTE (tricky): column name becomes "SUM(total)" because SQL has no alias
                // Safer style later: SELECT SUM(total) AS total_sum ...
                ti = result.getFloat("SUM(total)");
            }
            // Show total income in the dashboard label
            dashboard_TotalI.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardNSP() {
        // NSP = Number of Sold Products (sum of quantities in current customer orders table)
        String sql = "SELECT SUM(quantity) FROM customer";

        connect = database.connectDB();

        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                // NOTE (tricky): same "no alias" situation -> column label is "SUM(quantity)"
                q = result.getInt("SUM(quantity)");
            }
            // Display sold products count
            dashboard_NSP.setText(String.valueOf(q));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardIncomeChart() {
        // Reset the chart first to avoid stacking old data on every refresh
        dashboard_IncomeChart.getData().clear();

        // Builds a monthly chart using BOTH live receipts + archived receipts (UNION ALL)
        // Then groups by date to get "total income per day" for the current month only
        String sql
                = "SELECT date, SUM(total) "
                + "FROM ( "
                + "   SELECT date, total FROM receipt "
                + "   WHERE DATE_FORMAT(date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m') "
                + "   UNION ALL "
                + "   SELECT date, total FROM receipt_archive "
                + "   WHERE DATE_FORMAT(date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m') "
                + ") t "
                + "GROUP BY date "
                + "ORDER BY date";

        connect = database.connectDB();

        // A single series of points (X = date, Y = sum(total))
        XYChart.Series chart = new XYChart.Series();
        chart.setName("Total Income per Day");

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                // Using column index: 1 = date, 2 = SUM(total)
                chart.getData().add(new XYChart.Data(result.getString(1), result.getFloat(2)));
            }
            // Push the series into the AreaChart
            dashboard_IncomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardCustomerChart() {
        // Clear existing chart data before reloading
        dashboard_CustomerChart.getData().clear();

        // Counts how many receipts exist per day (live + archive combined)
        // NOTE: UNION ALL keeps duplicates (needed here because each receipt row counts)
        String sql
                = "SELECT date, COUNT(*) "
                + "FROM ( "
                + "   SELECT date FROM receipt "
                + "   UNION ALL "
                + "   SELECT date FROM receipt_archive "
                + ") t "
                + "GROUP BY date "
                + "ORDER BY date";

        connect = database.connectDB();

        // One series: (date -> number of receipts)
        XYChart.Series chart = new XYChart.Series();
        chart.setName("Number of Receipts per Day");

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            // Guard: don't add an empty series (can cause weird UI behavior
            if (!chart.getData().isEmpty()) {
                dashboard_CustomerChart.getData().add(chart);
            }

            // Force layout refresh so bars update immediately
            dashboard_CustomerChart.layout();
            dashboard_CustomerChart.requestLayout();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void monthlyReset() {

        // Confirmation dialog: this operation will archive data then clear tables for a new month
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Monthly Reset");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure? This will archive receipts then clear orders for a new month.");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.isEmpty() || option.get() != ButtonType.OK) {
            // User cancelled (or closed the dialog)
            return;
        }

        connect = database.connectDB();

        try {
            // Start a DB transaction: either everything succeeds, or everything is rolled back
            connect.setAutoCommit(false); // ✅ transaction

            // 1) Archive customer items (order details) and link them to the matching receipt_id
            // NOTE (tricky): LEFT JOIN matches customer.customer_id to receipt.customer_id
            // and stores receipt.id as receipt_id inside customer_archive          
            String archiveItems
                    = "INSERT INTO customer_archive (receipt_id, customer_id, prod_id, prod_name, type, quantity, price, date, em_username) "
                    + "SELECT r.id, c.customer_id, c.prod_id, c.prod_name, c.type, c.quantity, c.price, c.date, c.em_username "
                    + "FROM customer c "
                    + "LEFT JOIN receipt r ON r.customer_id = c.customer_id "
                    + "WHERE r.id IS NOT NULL";

            prepare = connect.prepareStatement(archiveItems);
            prepare.executeUpdate();

            // 2) Archive receipts (move all rows from receipt -> receipt_archive)
            String archiveReceipts
                    = "INSERT INTO receipt_archive (id, customer_id, total, date, em_username) "
                    + "SELECT id, customer_id, total, date, em_username FROM receipt";
            prepare = connect.prepareStatement(archiveReceipts);
            prepare.executeUpdate();

            // 3) Insert/Update a monthly summary row (one record per month)
            // NOTE (tricky): ON DUPLICATE KEY UPDATE means:
            // - if month_year already exists (unique key), update that row instead of inserting a new one         
            String insertMonthly
                    = "INSERT INTO monthly_close (month_year, total_income, total_receipts, sold_products) "
                    + "SELECT DATE_FORMAT(CURDATE(), '%Y-%m') AS month_year, "
                    + "IFNULL((SELECT SUM(total) FROM receipt), 0) AS total_income, "
                    + "IFNULL((SELECT COUNT(*) FROM receipt), 0) AS total_receipts, "
                    + "IFNULL((SELECT SUM(quantity) FROM customer), 0) AS sold_products "
                    + "ON DUPLICATE KEY UPDATE "
                    + "total_income = VALUES(total_income), "
                    + "total_receipts = VALUES(total_receipts), "
                    + "sold_products = VALUES(sold_products)";

            prepare = connect.prepareStatement(insertMonthly);
            prepare.executeUpdate();

            // 4) Clear live tables to start fresh (new month)
            prepare = connect.prepareStatement("DELETE FROM customer");
            prepare.executeUpdate();
            prepare = connect.prepareStatement("ALTER TABLE customer AUTO_INCREMENT = 1");
            prepare.executeUpdate();

            prepare = connect.prepareStatement("DELETE FROM receipt");
            prepare.executeUpdate();
            prepare = connect.prepareStatement("ALTER TABLE receipt AUTO_INCREMENT = 1");
            prepare.executeUpdate();

            // Commit transaction: changes become permanent
            connect.commit();
            connect.setAutoCommit(true);

            // Reset payment state in UI/session
            paidAmount = 0;

            // Generate a new customer/order id after clearing tables
            customerID();

            // 5) Refresh UI (Dashboard + Menu + Customers) so everything shows updated values
            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();

            customersShowData();
            menuShowOrderData();
            menuDisplayTotal();     // total = 0
            menuDisplayCard();      // cards update optional

            // Refresh archive tables section
            setupArchiveReceiptsTable();
            tblReceiptItems.getItems().clear();
            details_cashierLabel.setText("Cashier:");

            // Success message
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Done");
            alert.setHeaderText(null);
            alert.setContentText("Monthly reset completed successfully ✅");
            alert.showAndWait();

        } catch (Exception e) {
            // Any failure => rollback so DB remains consistent
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (Exception ex) {
                // Intentionally left empty: if rollback itself fails, we don't want to crash the app here.
                // At this point we're already handling the main error; this just "best-effort" attempts rollback.
            }
            e.printStackTrace();

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Reset failed. Nothing was changed (rolled back).");
            alert.showAndWait();
        }
    }

    public void inventoryAddBtn() {
        // Validate required fields (text inputs + combo box selections)
        boolean fieldsMissing
                = inventory_productID.getText().isBlank()
                || inventory_productName.getText().isBlank()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isBlank()
                || inventory_price.getText().isBlank()
                || inventory_status.getSelectionModel().getSelectedItem() == null;

        // Image path is stored globally in data.path after the user imports an image
        boolean imageMissing = (data.path == null || data.path.isBlank());

        if (fieldsMissing) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all required fields.");
            alert.showAndWait();
            return;// stop: can't insert incomplete product
        }

        if (imageMissing) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please import an image first.");
            alert.showAndWait();
            return; // stop: image is mandatory for this product

        } else {

            // Check if the Product ID already exists (must be unique)
            // NOTE (tricky): this uses string concatenation -> can cause SQL injection or quote issues if input is weird
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventory_productID.getText() + "'";

            // Open database connection
            connect = database.connectDB();

            try {

                // Execute the SELECT query to check if prod_id is already taken
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    // If a row exists => prod_id is already taken
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_productID.getText() + "is already taken!");
                    alert.showAndWait();
                } else {
                    // Insert new product row into product table
                    String insertData = "INSERT INTO product "
                            + "(prod_id, prod_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    // Prepare INSERT statement (uses placeholders ? for values)
                    prepare = connect.prepareStatement(insertData);

                    // Fill placeholders from UI inputs
                    prepare.setString(1, inventory_productID.getText());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                    // Store image path in DB (later used to reload image in inventorySelectData)
                    // NOTE: you previously considered escaping backslashes for Windows paths (commented code)
                    prepare.setString(7, data.path);

                    // Store current date (as a SQL date string)
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    // Execute the INSERT query
                    prepare.executeUpdate();

                    // Success feedback + refresh UI table + clear inputs
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }
            } catch (Exception e) {
                // Any DB / SQL error gets printed to console (useful during development)
                e.printStackTrace();
            }
        }
    }

    public void inventoryUpdateBtn() {

        // Update product row by its internal DB primary key (id)
        // NOTE: Uses PreparedStatement placeholders (?) which is safer than concatenation
        String updateData = "UPDATE product SET prod_id = ?, prod_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? WHERE id = ?";

        connect = database.connectDB();
        try {
            // Confirmation dialog before applying update
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Product ID: "
                    + inventory_productID.getText() + " ?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {

                // Fill SQL parameters from the current form inputs + cached selection data (data.id, data.date, data.path)
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, inventory_productID.getText());
                prepare.setString(2, inventory_productName.getText());
                prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                prepare.setString(4, inventory_stock.getText());
                prepare.setString(5, inventory_price.getText());
                prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                // NOTE (tricky): data.path usually comes from inventoryImportBtn() OR from inventorySelectData()
                // If user doesn't import a new image, it should still contain the selected product image path
                prepare.setString(7, data.path);

                // NOTE (tricky): data.date is taken from the selected product (inventorySelectData)
                // so update keeps the original date unless you change it elsewhere
                prepare.setString(8, data.date);

                // NOTE (tricky): data.id must match the selected table row, otherwise update will affect wrong row or none
                prepare.setInt(9, data.id);

                prepare.executeUpdate();

                // Success feedback + refresh table + clear inputs
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated ");
                alert.showAndWait();

                inventoryShowData();
                inventoryClearBtn();

            } else {
                // User cancelled update
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void inventoryDeleteBtn() {

        // Validate: make sure a product row is selected (data.id set by inventorySelectData)
        if (data.id == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            // Confirmation dialog before delete
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: "
                    + inventory_productID.getText() + " ?");

            Optional<ButtonType> option = alert.showAndWait();

            // NOTE (tricky): option.get() can throw if dialog is closed; but you rely on user clicking a button
            if (option.get().equals(ButtonType.OK)) {

                // Delete selected product by DB id
                // NOTE: This is SQL concatenation (works because id is int), but PreparedStatement is cleaner
                String deleteData = "DELETE FROM product WHERE id = " + data.id;

                connect = database.connectDB();
                if (connect == null) {
                    System.out.println("DB connection is null in delete!");
                    return;
                }

                try {
                    // Execute delete query
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // Refresh UI after delete
                    inventoryShowData(); // update your tableview
                    inventoryClearBtn(); //to clear the fields

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                // User cancelled the delete
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(" Cancelled. ");
                alert.showAndWait();
            }
        }
    }

    public void inventoryClearBtn() {

        // Reset form fields + reset cached selected-row state (data.id, data.path)
        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();

        // Clear cached selection/image data
        data.path = "";
        data.id = 0;

        // Clear preview image
        inventory_imageView.setImage(null);

    }

    public void inventoryImportBtn() {
        // Opens OS file picker for importing product image
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*.png", "*.jpg"));  // Allow only image files with these extensions

        // Open dialog attached to the current window (so it appears on top)
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {

            // Save absolute path to a global variable for later DB insert/update
            data.path = file.getAbsolutePath();

            // Load image for preview in UI (fixed size: 120x127)
            image = new Image(file.toURI().toString(), 120, 127, false, true);

            // Show preview show it in the ImageView (important)
            inventory_imageView.setImage(image);
        }

    }

    //merge all datas
    public ObservableList<productData> inventoryDataList() {

        // This list is the data source for the Inventory TableView
        ObservableList<productData> listData = FXCollections.observableArrayList(); // Data source for TableView

        // Fetch all products from DB
        String sql = "SELECT * FROM product";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);            // Prepare SQL statement
            result = prepare.executeQuery();                    // Execute query and get ResultSet

            productData prodData;  // Temporary object (one product per DB row)

            while (result.next()) { // Iterate over rows

                // Map DB columns -> productData object (your model class)
                prodData = new productData(
                        result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date")
                );

                // Add the object into the ObservableList so JavaFX can display it
                listData.add(prodData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Returned list will be used by inventoryShowData()
        return listData;
    }

    private ObservableList<productData> inventoryListData; // Cached list so TableView can reuse/update it // Keeps current inventory list bound to the TableView

    public void inventoryShowData() {

        // Reload fresh data from DB
        inventoryListData = inventoryDataList();

        // Bind TableColumns to productData property names
        // NOTE (tricky): "productId" must match getter/property inside productData (e.g., getProductId())
        inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Push list into the TableView (Inventory screen)
        inventory_tableView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        // Get selected product from the Inventory TableView
        productData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        // If nothing is selected, stop here
        if ((num - 1) < -1) {
            return;
        }

        // Fill the form fields with the selected product data
        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        // Store some values globally (used later for update/delete)
        // Cache selection info for UPDATE/DELETE buttons
        data.path = prodData.getImage();
        data.date = String.valueOf(prodData.getDate()); // Keep the selected date
        data.id = prodData.getId();                     // Keep DB record id

        // Load and show product image preview
        // NOTE (tricky): Windows paths sometimes contain "\" so you manually normalize it
        // You also convert local file path -> URI -> JavaFX Image
        String uri = new File(data.path).toURI().toString();

        String img = data.path.replace("\\\\", "\\");
        image = new Image(new java.io.File(img).toURI().toString(), 120, 127, false, true);
        inventory_imageView.setImage(image);

    }

    private String[] typeList = {"Meals", "Drinks"}; // Product categories shown in ComboBox

    public void inventoryTypeList() {

        // Convert array -> List -> ObservableList so ComboBox can display it so Create a dynamic List to collect the types from the array
        List<String> typeL = new ArrayList<>();

        for (String data : typeList) { // Loop through each element in the array           
            typeL.add(data);  // Add the current type into the List (NOT into the array)
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);  // Convert the normal List into an ObservableList (JavaFX uses it for UI controls)        
        inventory_type.setItems(listData); // Set the ComboBox items so the user can select one of the types

    }

    private String[] statusList = {"Available", "Unavailable"}; // List of product types that will be shown in the UI (ComboBox)

    public void inventoryStatusList() {

        // Convert array -> List -> ObservableList so ComboBox can display it
        List<String> statusL = new ArrayList<>();  // Create a dynamic List to collect the types from the array

        for (String data : statusList) { // Loop through each element in the array           
            statusL.add(data);  // Add the current type into the List (NOT into the array)
        }
        ObservableList listData = FXCollections.observableArrayList(statusL);  // Convert the normal List into an ObservableList (JavaFX uses it for UI controls)        
        inventory_status.setItems(listData); // Set the ComboBox items so the user can select one of the types

    }

    public ObservableList<productData> menuGetData() {

        // Same idea as inventoryDataList() but returns productData objects for the Menu cards
        String sql = "SELECT * FROM product"; // SQL to fetch all products from the database

        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {    // Read each row and convert it into productData

                // Build productData object (note: this constructor here doesn't include status)
                prod = new productData(
                        result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                // Add the product to the list used by the UI (cards/menu)
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData; // Returmn the filled list
    }

    ////////////////////////////////////////////////////////////////////////-----------------------
    // Builds the Menu screen UI as product "cards" inside the GridPane.
    // It clears the grid, loads cardProduct.fxml for each product, and positions cards in rows/columns.
    public void menuDisplayCard() {
        // Reload products list (used to build cards)
        cardListData.clear();
        cardListData.addAll(menuGetData());

        // Grid position trackers (row/column) coordinates (3 cards per row)
        int row = 0;
        int column = 0;

        // Clear old cards/layout before rebuilding
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        // Loop over each product in the list to create one UI card per item - Create one UI card per product
        for (int q = 0; q < cardListData.size(); q++) {

            try {
                // Load one card UI (FXML) for a single product
                FXMLLoader load = new FXMLLoader();  // Create an FXMLLoader to load one card UI from FXML
                load.setLocation(getClass().getResource("/fxml/cardProduct.fxml"));
                AnchorPane pane = load.load(); // Load the FXML and get the root node (the card container)

                // Pass product data into the card controller
                CardProductController cardC = load.getController();// Get the controller of the loaded card so we can pass data to it               
                cardC.setData(cardListData.get(q));// Send the product data to the card controller to fill labels/images, etc.
                cardC.setMainFormController(this);

                if (column == 3) {// after 3 cards, move to next row
                    column = 0;
                    row += 1;
                }

                // Add the card to the GridPane at (column, row), then move to next column
                menu_gridPane.add(pane, column++, row);

                // to leave spaces between card products
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ObservableList<productData> menuGetOrder() {

        // Loads current order items for the active customer/order id (data.cID)
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + data.cID;

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {

                // Map customer table row -> productData object (used by Menu orders table)
                prod = new productData(
                        result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date")
                );

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<productData> menuOrderListData;

    public void menuShowOrderData() {
        // Refresh Menu orders table data
        menuOrderListData = menuGetOrder();

        // Bind columns to productData properties (must match getters)
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }

    private int getid;

    public void menuSelectOrder() {

        // Store selected order row id (used for delete/remove)
        productData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        getid = prod.getId();// this is the DB id of the row in customer table

    }

    private double totalP;

    public void menuGetTotal() {

        // Calculates total for current order (sum of prices for the active customer_id)
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + data.cID;

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble(1); // SUM(price)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDisplayTotal() {

        // Refresh totalP then display it
        menuGetTotal();
        menu_total.setText("$" + totalP);

    }

    private double paidAmount = 0; // total money customer has paid so far (accumulates multiple payments)

    public void menuAmount() {

        // Read total from label (label format must stay "$<number>")
        double total = Double.parseDouble(menu_total.getText().replace("$", "").trim());

        if (menu_amount.getText().isEmpty() || total == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
            return;
        }

        try {
            double newPayment = Double.parseDouble(menu_amount.getText()); // one new payment
            paidAmount += newPayment;                                      // accumulate payments

            double change = paidAmount - total; // negative = still owes, positive = give back

            menu_change.setText("$" + String.format("%.2f", change));
            menu_amount.setText(""); // clear input after each entry

        } catch (NumberFormatException e) {
            menu_amount.setText("");
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid number");
            alert.showAndWait();
        }
    }

    public void menuRemoveBtn() {

        // Remove one selected order item (row) from the "customer" table
        if (getid == 0) {
            // No row selected (getid is set in menuSelectOrder())
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove! ");
            alert.showAndWait();
        } else {

            // Delete the selected row by its DB primary key (id)
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connectDB();
            try {
                // Confirmation before delete
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                // NOTE (tricky): option.get() assumes a button was chosen (closing dialog may cause issues)
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                // Refresh the orders table after removal
                menuShowOrderData();

                // Reset totals + payment session because the order changed
                menuDisplayTotal();
                paidAmount = 0;
                menu_amount.setText("");
                menu_change.setText("$0.00");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuPayBtn() {

        // Refresh totalP from DB before validating payment
        menuGetTotal(); // refresh totalP

        if (totalP == 0) {
            // No items in the current order
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
            return;
        }

        // Payment must cover the total (paidAmount is accumulated via menuAmount())
        if (paidAmount < totalP) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Customer still owes money!");
            alert.showAndWait();
            return;
        }

        // Insert one receipt row to finalize the order
        String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) VALUES(?,?,?,?)";
        connect = database.connectDB();

        try {
            // Confirm payment action
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {

                // Save receipt record (customer_id links the whole order)
                prepare = connect.prepareStatement(insertPay);
                prepare.setInt(1, data.cID);
                prepare.setDouble(2, totalP);

                // Store today's date in receipt
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setDate(3, sqlDate);

                // Store cashier username
                prepare.setString(4, data.username);

                prepare.executeUpdate();

                // Reset payment UI/session after successful - save reset money inputs
                paidAmount = 0;
                menu_amount.setText("");
                menu_change.setText("$0.00");

                lastPaidCustomerId = data.cID;

                // Start a NEW order by generating a new customer_id
                customerID();

                // Refresh UI: new cID has no rows => table becomes empty and total becomes 0
                menuShowOrderData();
                menuDisplayTotal();

                // reset after successful payment - Reset again (redundant but harmless)
                paidAmount = 0;
                menu_amount.setText("");
                menu_change.setText("$0.00");

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Payment saved!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuReceiptBtn() {

        // Open receipt window for the MOST RECENT paid order
        if (lastPaidCustomerId == 0) {
            // No successful payment happened yet in this session
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("No paid receipt yet!");
            alert.showAndWait();
            return;
        }

        try {
            // Load receipt UI (new window)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/receipt.fxml"));
            Parent root = loader.load();

            // Pass the customer id into ReceiptController so it can query and render the receipt
            ReceiptController rc = loader.getController();
            rc.loadReceipt(lastPaidCustomerId);

            Stage stage = new Stage();
            stage.setTitle("Receipt");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int cID;

    public void customerID() {

        // Generates/updates the current order id (data.cID) based on MAX(customer_id)
        // This tries to avoid re-using an id that already exists in receipt
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                // NOTE (tricky): column label is "MAX(customer_id)" because no alias is used
                cID = result.getInt("MAX(customer_id)");
            }

            // Check the last used customer_id in receipt table as well
            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();

            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            // If customer table is empty -> start at 1
            // If latest customer_id equals the latest receipt customer_id -> increment to new id
            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            // Store the active order/customer id globally
            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<customersData> customersDataList() {

        // Loads live receipts into the Customers TableView
        ObservableList<customersData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM receipt";
        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customersData cData;

            while (result.next()) {

                // Map receipt table row -> customersData model
                cData = new customersData(
                        result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username")
                );

                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<customersData> customersListData;

    public void customersShowData() {
        // Refresh Customers screen table
        customersListData = customersDataList();

        // Bind columns to customersData properties (must match getters)
        customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customers_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

        customers_tableView.setItems(customersListData);
    }

    @FXML
    public void switchForm(ActionEvent event) {

        // Default inside Customers screen: show Customers list and hide Archive section
        if (customers_form != null) {
            customers_content.setVisible(true);
            Archive_Of_Receipts_Form.setVisible(false);
        }

        if (event.getSource() == dashboard_btn) {

            // Show Dashboard and refresh its numbers/charts
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();

        } else if (event.getSource() == inventory_btn) {

            // Show Inventory and refresh dropdowns/table
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();

        } else if (event.getSource() == menu_btn) {

            // Show Menu screen (cards + current order)
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);

            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();

        } else if (event.getSource() == customers_btn) {

            // Show Customers screen (live receipts)
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);

            customers_content.setVisible(true);
            Archive_Of_Receipts_Form.setVisible(false);

            customersShowData();
        }
    }

    public ObservableList<ArchiveReceiptData> archiveReceiptList() {

        // Builds one unified list of receipts (LIVE + ARCHIVE) to show in the archive table
        ObservableList<ArchiveReceiptData> list = FXCollections.observableArrayList();

        // UNION ALL keeps all rows; 'src' tells us where the row came from (LIVE vs ARCHIVE)
        String sql
                = "SELECT id, customer_id, total, date, em_username, 'LIVE' AS src FROM receipt "
                + "UNION ALL "
                + "SELECT id, customer_id, total, date, em_username, 'ARCHIVE' AS src FROM receipt_archive "
                + "ORDER BY date DESC";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                // Map each row into ArchiveReceiptData (used by tblReceipts)
                list.add(new ArchiveReceiptData(
                        result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"),
                        result.getString("src") // this value is either "LIVE" or "ARCHIVE"
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void openArchiveForm(ActionEvent event) {

        // Switch UI to Customers screen but show the Archive section instead of live customers table
        dashboard_form.setVisible(false);
        inventory_form.setVisible(false);
        menu_form.setVisible(false);

        customers_form.setVisible(true);

        customers_content.setVisible(false);       // hide live customers table
        Archive_Of_Receipts_Form.setVisible(true); // show archive section

        // Prepare tables and clear previous details
        setupArchiveReceiptsTable();
        setupArchiveItemsTable();
        tblReceiptItems.getItems().clear();
    }

    public void showArchiveReceipts() {

        // Bind receipt columns to ArchiveReceiptData properties
        colReceiptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colReceiptDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReceiptTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colReceiptCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));

        // Load and display receipts (live + archive)
        tblReceipts.setItems(archiveReceiptList());
    }

    private void setupArchiveReceiptsTable() {

        // Same bindings as showArchiveReceipts() (helper for reloading the table)
        colReceiptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colReceiptDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colReceiptTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colReceiptCashier.setCellValueFactory(new PropertyValueFactory<>("cashier"));

        tblReceipts.setItems(archiveReceiptList());
    }

    private void setupArchiveItemsTable() {

        // Bind detail-items table columns (items for the selected receipt)
        colItemCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("type"));
        // NOTE (tricky): My ArchiveItemData uses "qty" as the property name (not "quantity")
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadLiveItems(int customerId) {

        // Loads details for LIVE receipts from the customer table (by customer_id)
        ObservableList<ArchiveItemData> list = FXCollections.observableArrayList();

        // PreparedStatement param avoids SQL injection and handles int safely
        String sql = "SELECT customer_id, type, quantity, price FROM customer WHERE customer_id = ?";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, customerId);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(new ArchiveItemData(
                        result.getInt("customer_id"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price")
                ));
            }

            // Display details rows in the items table
            tblReceiptItems.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadArchivedItemsByReceiptId(int receiptId) {

        // Loads details for ARCHIVED receipts from customer_archive (by receipt_id)
        ObservableList<ArchiveItemData> list = FXCollections.observableArrayList();

        // NOTE: archived items are linked by receipt_id (more accurate than customer_id for history)
        String sql = "SELECT customer_id, type, quantity, price FROM customer_archive WHERE receipt_id = ?";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, receiptId);
            result = prepare.executeQuery();

            while (result.next()) {
                list.add(new ArchiveItemData(
                        result.getInt("customer_id"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price")
                ));
            }

            tblReceiptItems.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printNodeAsImage(Node node) {

        // Prints any JavaFX Node by snapshotting it into an image, then scaling to fit printable area
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            return; // no printer available
        }

        // Let user pick printer/settings
        if (!job.showPrintDialog(main_form.getScene().getWindow())) {
            return; // user cancelled printing
        }

        PageLayout layout = job.getJobSettings().getPageLayout();

        // Ensure CSS/layout are applied before snapshot (important for TableView visuals)
        node.applyCss();
        if (node instanceof javafx.scene.Parent p) {
            p.layout();
        }

        // Snapshot the node as an image
        WritableImage img = node.snapshot(new SnapshotParameters(), null);
        ImageView iv = new ImageView(img);
        iv.setPreserveRatio(true);

        // Scale snapshot to fit the printer's printable width/height (keeps aspect ratio)
        double scale = Math.min(
                layout.getPrintableWidth() / img.getWidth(),
                layout.getPrintableHeight() / img.getHeight()
        );

        iv.setFitWidth(img.getWidth() * scale);
        iv.setFitHeight(img.getHeight() * scale);

        boolean success = job.printPage(layout, iv);
        if (success) {
            job.endJob();
        }
    }

    private void printFullTable(TableView<?> table) {

        // Temporarily expand the TableView height so ALL rows become visible before snapshot/printing
        double oldPrefH = table.getPrefHeight();
        double oldMinH = table.getMinHeight();
        double oldMaxH = table.getMaxHeight();

        // Use a fixed row height so we can calculate full table height reliably
        if (table.getFixedCellSize() <= 0) {
            table.setFixedCellSize(24);
        }

        // Estimate header height (CSS lookup) to include it in total height
        double headerH = table.lookup(".column-header-background") != null
                ? table.lookup(".column-header-background").prefHeight(-1)
                : 28;

        // Full height = header + (rows * rowHeight)
        double newH = headerH + (table.getItems().size() * table.getFixedCellSize()) + 2;

        table.setPrefHeight(newH);
        table.setMinHeight(newH);
        table.setMaxHeight(newH);

        table.applyCss();
        table.layout();

        // Print the now-expanded table as image
        printNodeAsImage(table);

        // Restore old size after printing
        table.setPrefHeight(oldPrefH);
        table.setMinHeight(oldMinH);
        table.setMaxHeight(oldMaxH);
    }

    @FXML
    public void archivePrintDetails(ActionEvent event) {

        // Prints the details/items table for the selected receipt
        if (tblReceipts.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Select a receipt first!");
            alert.showAndWait();
            return;
        }

        // Hide button so it doesn't appear in the printed snapshot
        archive_printDetailsBtn.setVisible(false);
        printFullTable(tblReceiptItems);  // prints full details table scaled to fit page
        archive_printDetailsBtn.setVisible(true);
    }

    @FXML
    public void archivePrintAllReceipts(ActionEvent event) {

        // Prints the entire receipts list table
        if (tblReceipts.getItems() == null || tblReceipts.getItems().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR, "No receipts to print!");
            alert.showAndWait();
            return;
        }

        // Hide button so it doesn't appear in the printed snapshot
        archive_printAllReceiptsBtn.setVisible(false);
        printFullTable(tblReceipts);       // prints full receipts table scaled to fit page
        archive_printAllReceiptsBtn.setVisible(true);
    }

    public void logout() {

        // 1) Ask user to confirm logout
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> option = alert.showAndWait(); //Show the alert and wait for the user's choice (OK / Cancel)

        // 2) If user clicked OK (safe even if user closes the dialog)
        if (option.orElse(ButtonType.CANCEL) == ButtonType.OK) {

            try {
                // 3) Load the login UI from FXML
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/auth.fxml"));

                // 4) Show login window
                Stage stage = new Stage();
                stage.setTitle("Cafe Shop Management System");
                stage.setScene(new Scene(root));
                stage.show();

                // 5) Close current window AFTER new window is shown
                main_form.getScene().getWindow().hide();   // safer than logout_btn

            } catch (IOException e) {
                e.printStackTrace();

                // Optional: show error dialog if loading fails
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error");
                err.setHeaderText(null);
                err.setContentText("Failed to load /fxml/auth.fxml");
                err.showAndWait();
            }
        }
    }

    public void displayUsername() {

        // Display the logged-in username (stored in data.username)
        String user = data.username;

        // Capitalize the first letter:
        // - user.substring(0,1)  -> first character
        // - .toUpperCase()       -> make it uppercase
        // - user.substring(1)    -> the rest of the string
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user); // Show the formatted username on the label in the UI

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Runs automatically after FXML loading and @FXML injection
        displayUsername();

        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();

        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        menuDisplayCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
        customersShowData();

        // When a receipt is selected, load its items (LIVE from customer, ARCHIVE from customer_archive)
        tblReceipts.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {

                if ("LIVE".equals(newSel.getSrc())) {
                    loadLiveItems(newSel.getCustomerId());
                } else {
                    loadArchivedItemsByReceiptId(newSel.getId());
                }

                details_cashierLabel.setText("Cashier: " + newSel.getCashier());
            }
        });
    }

}
