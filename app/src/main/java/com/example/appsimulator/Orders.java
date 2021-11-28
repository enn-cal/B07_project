package com.example.appsimulator;

import java.util.ArrayList;

public class Orders {

    public ArrayList<Products> order = new ArrayList<Products>();
    private int customerID;

    public Orders(int customerID){
        this.customerID = customerID;
    }

    public int countProduct(Products product){
        int quantity = 0;
        for(Products p: order){
            if(p.equals(product)){
                quantity++;
            }
        }
        return quantity;
    }

    public void addProduct(Products product){
        order.add(product);
    }

    public void removeProduct(Products product){
        if(!order.contains(product)){
            return;
        }
        order.remove(product);
    }

    public int getCustomerID(){
        return customerID;
    }
}