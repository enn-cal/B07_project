package com.example.appsimulator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties

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

    public void setItem(String item){this.item = item;}

    public void setBrand(String brand){this.brand = brand;}

    public void setPrice(String price){this.price= price;}

    public void setQuantity(String quantity){this.quantity = quantity;}



    @Override
    public int hashCode(){
        return this.item.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Products other = (Products) obj;
        //if both products have same name for item
        return this.item.equals(other.item);
    }
}
