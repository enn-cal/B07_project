package com.example.appsimulator;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModelLogin implements Contracts.ModelLogin {
    boolean match = false;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;
    private Contracts.PresenterLogin listener;

    public ModelLogin(FirebaseDatabase f_db, DatabaseReference ref, Contracts.PresenterLogin listener){
        this.f_db =f_db;
        this.ref = ref;
        this.listener = listener;
    }

    @Override
    public boolean loginError(String email, String password) {
        //Consider the type to compare corresponding regex with
        if (email.isEmpty()) {
            listener.loginFailed("Empty Email. Try Again");
            return true;
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            listener.loginFailed("Invalid Email. Try Again");
            return true;
        }
        if (password.isEmpty()) {
            listener.loginFailed("Empty Password. Try Again");
            return true;
        }
        return false;
    }

    @Override
    public boolean match(String email, String password) {
        /*
        ref.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //if there was matching email and password in database Users/Customer
                    if(snapshot.child("email").equals(email) && snapshot.child("pwd").equals(password)){
                        match = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        */
        ref = ref.child("Store Owner");
        ValueEventListener v = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String em = snapshot.child("email").getValue(String.class);
                    String pw = snapshot.child("password").getValue(String.class);
                    //Log.i("TestMessage", "email: " +em + "|| password: " +pw);
                    //if there was matching email and password in database Users/Store Owner
                    if(em.equals(email) && pw.equals(password)){
                        match = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        /*
        ref.child("Store Owner").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */
        if(!match){
            listener.loginFailed("Failed to Login. Try Again");
        }
        return match;
    }

    @Override
    public void login(String email, String password) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(loginError(email,password) || !match(email,password)){
                    return;
                }
                listener.loginSuccess();
            }
        });
    }


}
