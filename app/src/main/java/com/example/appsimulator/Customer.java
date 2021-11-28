package com.example.appsimulator;

import java.util.ArrayList;

public class Customer extends User {

    //Order order;

    public Customer() {
    }

    public Customer(String Name, String email, String pwd,String postal,String dob) {
        this.name = Name;
        this.email = email;
        this.pwd = pwd;
        this.dob = dob;
        this.postal = postal;
        //this.order = order;
    }
}
