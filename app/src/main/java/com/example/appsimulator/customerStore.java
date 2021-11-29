package com.example.appsimulator;

public class customerStore{

    public String email;
    public String storeID;
    public Orders order = new Orders();

    public customerStore(String email, String storeID){
        this.email = email;
        this.storeID = storeID;
    }

    public void setOrder(Orders o){
        order = o;
    }
}
