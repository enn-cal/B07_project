package com.example.appsimulator;

public class Products {

    private String item;
    private String brand;
    private String price; //price and quantity are string must ensure user enters ints
    private String quantity;

    public Products(){} //default constructor

    public Products(String item, String brand, String price, String quantity) {
        this.item = item;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode(){
        return this.item.hashCode();
    }
}
