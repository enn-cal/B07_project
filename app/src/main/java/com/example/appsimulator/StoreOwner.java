package com.example.appsimulator;

import java.util.ArrayList;

public class StoreOwner extends User{

    ArrayList<Products> products;
    ArrayList<Orders> customerOrders;

    public StoreOwner(String Name, String email, String pwd,String postal,String dob){
        super(Name, email, pwd, postal, dob);
        products = new ArrayList<>();
        customerOrders = new ArrayList<>();
    }

    public void completeOrder(){

    }



}
