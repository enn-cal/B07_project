package com.example.appsimulator;

import java.util.ArrayList;

public class Customer extends User {

    public ArrayList<Orders> cart;

    public Customer(String name, String email, String pwd, String dob, String postal, ArrayList<Orders> cart) {
        super(name, email, pwd, postal, dob);
        this.cart = cart;
    }

    public Orders findOrder(String storeID) {
        if (storeID.isEmpty()) {
            return null;
        }
        for (Orders o : cart) {
            if (o.storeID.equals(storeID)) {
                return o;
            }
        }
        return null;
    }

    public void addToCart(Orders o) {
        if(o != null) {
            cart.add(o);
        }
    }

}
