package com.mycompany.cafeshopmanagementsystemtest;

public class ArchiveItemData {

    private final int customerId;
    private final String type;
    private final int qty;
    private final double price;

    public ArchiveItemData(int customerId, String type, int qty, double price) {
        this.customerId = customerId;
        this.type = type;
        this.qty = qty;
        this.price = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getType() {
        return type;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }
}
