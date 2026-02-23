package com.mycompany.cafeshopmanagementsystemtest;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReceiptController implements Initializable {

    @FXML
    private TableColumn<ReceiptItem, String> colName;

    @FXML
    private TableColumn<ReceiptItem, String> colPrice;

    @FXML
    private TableColumn<ReceiptItem, Integer> colQty;

    @FXML
    private TableColumn<ReceiptItem, Double> colType;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<ReceiptItem> tblItems;

    @FXML
    private Button receiptRoot;

    private final ObservableList<ReceiptItem> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblItems.setItems(items);
    }

    public void loadReceipt(int customerId) {
        lblCustomerId.setText(" " + customerId);
        lblDate.setText(new Date().toString());

        items.clear();

        String sqlItems = "SELECT prod_name, type, quantity, price FROM customer WHERE customer_id = ?";
        String sqlTotal = "SELECT total, date FROM receipt WHERE customer_id = ? ORDER BY id DESC LIMIT 1";

        try (Connection con = database.connectDB()) {

            try (PreparedStatement ps = con.prepareStatement(sqlItems)) {
                ps.setInt(1, customerId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    items.add(new ReceiptItem(
                            rs.getString("prod_name"),
                            rs.getString("type"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    ));
                }
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlTotal)) {
                ps2.setInt(1, customerId);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    lblTotal.setText(" " + rs2.getDouble("total") + "$");
                    lblDate.setText(String.valueOf(rs2.getDate("date")));
                } else {
                    lblTotal.setText("Total: $0.0");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void printReceipt() {
        javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(receiptRoot.getScene().getWindow())) {
            boolean ok = job.printPage(receiptRoot);
            if (ok) {
                job.endJob();
            }
        }
    }

}
