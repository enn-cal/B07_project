package com.example.appsimulator;

import java.util.ArrayList;

public class Stores {

    private ArrayList<Products> products;
    private int ID;

    public Stores( int ID){
        this.ID = ID;
        products = new ArrayList<Products>();
    }

    public void addProductStore(Products product){
        products.add(product);
    }

    public int getStoreID(){
        return  ID;
    } // assuming unique names

    public ArrayList<Products> getProducts(){
        return products;
    }
}
