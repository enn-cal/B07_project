package com.example.appsimulator;

import java.util.ArrayList;

public class Stores {

    public ArrayList<Products> products;
    public ArrayList<Orders> customerOrders;
    private String storeID;

    public Stores(String storeID){

        this.storeID = storeID;
        products = new ArrayList<Products>();
        customerOrders = new ArrayList<Orders>();
    }

    public void completeOrder(Orders order){
        if(!customerOrders.contains(order)){
            return;
        }
        customerOrders.remove(order);
    }

    public void addProductStore(Products product){
        products.add(product);
    }

    public String getStoreID(){
        return storeID;
    }

    public ArrayList<Products> getProducts(){
        return products;
    }
}
