package com.mycompany.cafeshopmanagementsystemtest;

public class ReceiptItem {

    private final String name;
    private final String type;
    private final int qty;
    private final double price;

    public ReceiptItem(String name, String type, int qty, double price) {
        this.name = name;
        this.type = type;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
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
