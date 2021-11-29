package com.example.appsimulator;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties

public class customerStore{
    public String email;
    public String storeID;
    @Exclude
    public Orders order = new Orders();

    public customerStore(String email, String storeID){
        this.email = email;
        this.storeID = storeID;
    }

    public void setOrder(Orders o){
        order = o;
    }
}
