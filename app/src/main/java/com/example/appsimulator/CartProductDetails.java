package com.example.appsimulator;

import java.util.ArrayList;

public class CartProductDetails {

        ArrayList<Products> item;
        ArrayList<String> quantity;
        ArrayList<String> storeID;
        ArrayList<String> price;

        CartProductDetails(ArrayList<Products> item, ArrayList<String> quantity, ArrayList<String> storeID, ArrayList<String> price) {

            this.item = item;
            this.quantity = quantity;
            this.storeID = storeID;
            this.price = price;
    }

    public void add (Products item, String quantity, String storeID, String price) {
            this.item.add(item);
            this.quantity.add(quantity);
            this.storeID.add(storeID);
            this.price.add(price);
    }
}