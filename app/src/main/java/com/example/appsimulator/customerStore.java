package com.example.appsimulator;

import java.util.ArrayList;


public class customerStore{
    public String email;
    public String storeID;
    public ArrayList<Products> order = new ArrayList<>();

    public customerStore(){
    }

    public customerStore(String email, String storeID){
        this.email = email;
        this.storeID = storeID;
    }

    public void addProduct(Products p){
        order.add(p);
    }
}
