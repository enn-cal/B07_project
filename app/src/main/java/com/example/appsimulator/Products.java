package com.example.appsimulator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties

public class Products {

    private String item;
    private String brand;
    private String price; //price and quantity are string must ensure user enters ints
    private String quantity;
    @Exclude
    private String storeID;

    public Products(){} //default constructor

    public Products(String item, String brand, String price, String quantity, String storeID) {
        this.item = item;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.storeID = storeID;
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

    public String getStoreID() { return storeID; }

    public void setItem(String item){this.item = item;}

    public void setBrand(String brand){this.brand = brand;}

    public void setPrice(String price){this.price= price;}

    public void setQuantity(String quantity){this.quantity = quantity;}

    public void setStoreID(String storeID) {this.storeID = storeID;}



    @Override
    public int hashCode(){
        return this.item.hashCode();
    }
}
