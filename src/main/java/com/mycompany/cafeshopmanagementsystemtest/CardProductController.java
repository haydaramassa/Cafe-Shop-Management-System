package com.mycompany.cafeshopmanagementsystemtest;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardProductController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Button prod_addBtn;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Spinner<Integer> prod_spinner;

    private SpinnerValueFactory<Integer> spin;

    // Product fields cached from productData
    private String prodID;      // product.prod_id
    private String type;        // product.type
    private String prod_image;  // product.image path
    private String prod_date;   // product.date (stored as String)
    private productData prodData;

    private Image image;

    // DB objects used by addBtn()
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    // Quantity selected from spinner
    private int qty;

    // totalP = qty * price, pr = single item price
    private double totalP;
    private double pr;

    // Reference back to the main controller so we can refresh the Menu table/total
    private MainFormController mainForm;

    public void setMainFormController(MainFormController mainForm) {
        // Allows this card to call methods on MainFormController (refresh order table/total)
        this.mainForm = mainForm;
    }

    /**
     * Receives productData from MainFormController and fills this card UI:
     * - name, price, image
     * Also caches important product fields used later in addBtn().
     */
    public void setData(productData prodData) {

        // Cache product fields (used later when inserting into customer table)
        prod_image = prodData.getImage();
        prod_date = String.valueOf(prodData.getDate());
        type = prodData.getType();
        this.prodData = prodData;
        prodID = prodData.getProductId();

        // Fill UI labels
        prod_name.setText(prodData.getProductName());
        prod_price.setText("$" + prodData.getPrice());

        // Store price per item
        pr = prodData.getPrice();

        // Load image safely (file path -> URI -> JavaFX Image)
        String img = prodData.getImage();
        if (img != null && !img.isBlank()) {

            // NOTE (tricky): If DB stored "\\\\" you convert back to "\" for Windows file paths
            img = img.replace("\\\\", "\\");

            java.io.File f = new java.io.File(img);
            if (f.exists()) {
                String uri = f.toURI().toString();
                image = new Image(uri, 216, 76, false, true);
                prod_imageView.setImage(image);
            } else {
                // Image path exists in DB but file is missing on disk
                prod_imageView.setImage(null);
            }
        } else {
            // No image saved
            prod_imageView.setImage(null);
        }
    }

    // Initializes the spinner so user can select quantity.
    public void setQuantity() {
        // Spinner range: 0..100, default 0
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    public void addBtn() throws SQLException {

        // Read selected quantity from spinner
        qty = prod_spinner.getValue();

        String check = ""; // holds product status (Available/Unavailable)

        // NOTE (tricky): SQL concatenation (works because prodID comes from DB, but still not ideal)
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '"
                + prodID + "'";

        connect = database.connectDB();

        try {
            int checkStck = 0;

            // Check current stock in DB
            String checkStock = "SELECT stock FROM product WHERE  prod_id = '"
                    + prodID + "'";

            prepare = connect.prepareStatement(checkStock);
            result = prepare.executeQuery();

            if (result.next()) {
                checkStck = result.getInt("stock");
            }

            // If stock is 0, force product status to Unavailable in DB
            if (checkStck == 0) {

                String updateStock0 = "UPDATE product SET prod_name = ?, type = ?, stock = 0, price = ?, status = 'Unavailable', image = ?, date = ? WHERE prod_id = ?";

                prepare = connect.prepareStatement(updateStock0);
                prepare.setString(1, prod_name.getText());
                prepare.setString(2, type);
                prepare.setDouble(3, pr);
                prepare.setString(4, prod_image);
                prepare.setString(5, prod_date);
                prepare.setString(6, prodID);
                prepare.executeUpdate();

            }

            // Read current availability status
            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if (result.next()) {
                check = result.getString("status");
            }

            // Block add if not available OR qty is 0
            if (!check.equals("Available") || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            } else {

                // Block add if requested quantity is more than stock
                if (checkStck < qty) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Out of stock");
                    alert.showAndWait();
                } else {
                    // Ensure current order/customer id is ready (data.cID)
                    if (mainForm != null) {
                        mainForm.customerID();
                    }

                    // If same product already exists for same customer_id, update it instead of adding a new row
                    String checkExisting = "SELECT id, quantity, price FROM customer "
                            + "WHERE customer_id = ? AND prod_id = ? LIMIT 1";

                    PreparedStatement psCheck = connect.prepareStatement(checkExisting);
                    psCheck.setInt(1, data.cID);
                    psCheck.setString(2, prodID);
                    ResultSet rsExisting = psCheck.executeQuery();

                    if (rsExisting.next()) {
                        // Existing row found: increase quantity and update price total
                        int rowId = rsExisting.getInt("id");
                        int oldQty = rsExisting.getInt("quantity");
                        double oldPrice = rsExisting.getDouble("price");

                        int newQty = oldQty + qty;
                        double newPrice = oldPrice + (qty * pr);

                        String updateExisting = "UPDATE customer SET quantity = ?, price = ?, date = ? WHERE id = ?";
                        PreparedStatement psUpdate = connect.prepareStatement(updateExisting);
                        psUpdate.setInt(1, newQty);
                        psUpdate.setDouble(2, newPrice);

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        psUpdate.setString(3, String.valueOf(sqlDate));

                        psUpdate.setInt(4, rowId);
                        psUpdate.executeUpdate();

                    } else {

                        // Insert a new row into customer table (acts like "add to cart")
                        String insertData = "INSERT INTO customer "
                                + "(customer_id, prod_id , prod_name, type, quantity, price, date, image, em_username) "
                                + "VALUES(?,?,?,?,?,?,?,?,?)";

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, String.valueOf(data.cID));
                        prepare.setString(2, prodID);
                        prepare.setString(3, prod_name.getText());
                        prepare.setString(4, type);
                        prepare.setString(5, String.valueOf(qty));

                        // total price for this line item = qty * unit price
                        totalP = (qty * pr);
                        prepare.setString(6, String.valueOf(totalP));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(7, String.valueOf(sqlDate));
                        prepare.setString(8, prod_image);
                        prepare.setString(9, data.username);

                        prepare.executeUpdate();
                    }

                    // Update product stock after adding to order
                    int upStock = checkStck - qty;

                    System.out.println("Date: " + prod_date);
                    System.out.println("Image: " + prod_image);

                    // NOTE (tricky): You keep the old status string in 'check' (typically "Available")
                    // Status is not recalculated here when stock becomes 0 after purchase.
                    String updateStock = "UPDATE product SET prod_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? WHERE prod_id = ?";

                    prepare = connect.prepareStatement(updateStock);
                    prepare.setString(1, prod_name.getText());
                    prepare.setString(2, type);
                    prepare.setInt(3, upStock);
                    prepare.setDouble(4, pr);
                    prepare.setString(5, check);
                    prepare.setString(6, prod_image);   
                    prepare.setString(7, prod_date);
                    prepare.setString(8, prodID);
                    prepare.executeUpdate();

                    // Refresh main screen order table/total after add
                    if (mainForm != null) {
                        mainForm.menuShowOrderData();
                        mainForm.menuDisplayTotal();
                    }

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // Reset spinner after successful add
                    prod_spinner.getValueFactory().setValue(0);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize UI controls for this card
        setQuantity();
    }
}