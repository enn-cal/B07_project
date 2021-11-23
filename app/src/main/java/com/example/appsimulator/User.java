package com.example.appsimulator;

import com.google.firebase.database.IgnoreExtraProperties;

import java.nio.charset.StandardCharsets;

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public String pwd;
    public String dob;
    public String postal;
    public boolean isCustomer; //Should be a boolean
    private static int counter = 0; // counts the number of users

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String Name, String email, String pwd,String postal,String dob, boolean isCustomer) {
        this.name = Name;
        this.email = email;
        this.pwd = pwd;
        this.dob = dob;
        this.postal = postal;
        this.isCustomer = isCustomer;
        counter++;
    }

    public int get_counter() { return counter;}

    @Override
    public int hashCode(){
        return this.email.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        User other = (User) obj;

        return this.email.equals(other.email);
    }

}
