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
    private static int counter = 0; // counts the number of users

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String Name, String email, String pwd,String postal,String dob) {
        this.name = Name;
        this.email = email;
        this.pwd = pwd;
        this.dob = dob;
        this.postal = postal;
        counter++;
    }

    public int get_counter() { return counter;}

    @Override
    public int hashCode(){
        // i will use all fields to come up with a unique user id (hashcode)

        //gets an array of ASCII values
        byte[] s1 = this.name.getBytes(StandardCharsets.UTF_8);
        byte[] s2 = this.pwd.getBytes(StandardCharsets.UTF_8);
        byte[] s3 = this.postal.getBytes(StandardCharsets.UTF_8);
        byte[] s4 = this.email.getBytes(StandardCharsets.UTF_8);
        byte[] s5 = this.dob.getBytes(StandardCharsets.UTF_8);

        // adds up the sum from the arrays
        int sum = 0;
        for (byte i:s1) {sum+=i;}
        for (byte i:s2) {sum+=i;}
        for (byte i:s3) {sum+=i;}
        for (byte i:s4) {sum+=i;}
        for (byte i:s5) {sum+=i;}

        return sum;
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

        if (!this.name.equals(other.name))
            return false;
        if (!this.email.equals(other.email))
            return false;
        if (!this.pwd.equals(other.pwd))
            return false;
        if (!this.dob.equals(other.dob))
            return false;
        if (!this.postal.equals(other.postal))
            return false;

        return true;
    }

}
