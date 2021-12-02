package com.example.appsimulator;

import java.util.ArrayList;

public class Orders {

    public ArrayList<Products> order;
    //public String storeID;
    private boolean activator = false; //tells us if the orders are complete (when list is empty) only if activator is true (see pinned db pic on discord)

    public Orders(){
        this.order = new ArrayList<Products>();
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

    public boolean orderComplete (){
        return (activator == true) && (order.isEmpty());
    }

    /*
    public void setStoreID(String ID){
        storeID = ID;
    }

     */
}
