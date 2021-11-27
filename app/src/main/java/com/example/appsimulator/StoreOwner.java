package com.example.appsimulator;

import java.util.ArrayList;

public class StoreOwner extends User{

    ArrayList<Products> products;
    ArrayList<Orders> customerOrders;

    public StoreOwner(String Name, String email, String pwd,String postal,String dob, ArrayList<Products> p, ArrayList<Orders> o){
        super(Name, email, pwd, postal, dob);
        products = p;
        customerOrders = o;
    }

    public void completeOrder(Orders order){
        if(!customerOrders.contains(order)){
            return;
        }
        customerOrders.remove(order);
    }



}
