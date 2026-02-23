package com.mycompany.cafeshopmanagementsystemtest;

import java.sql.Date;

public class ArchiveReceiptData {

    private final int id;
    private final int customerId;
    private final double total;
    private final Date date;
    private final String cashier;
    private String src;

    public ArchiveReceiptData(int id, int customerId, double total, java.sql.Date date, String cashier, String src) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.date = date;
        this.cashier = cashier;
        this.src = src;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public String getCashier() {
        return cashier;
    }

    public String getSrc() {
        return src;
    }
}
