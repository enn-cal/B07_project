package com.example.appsimulator;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String pwd;
    public String dob;
    public String postal;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String Name, String email, String pwd,String postal,
                String dob) {
        this.name = Name;
        this.email = email;
        this.pwd = pwd;
        this.dob = dob;
        this.postal = postal;
    }

}
