package com.example.appsimulator;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Model implements Contract.Model {

    private FirebaseDatabase f_db;
    private DatabaseReference ref;

    public Model(FirebaseDatabase f_db, DatabaseReference ref){
        this.f_db =f_db;
        this.ref = ref;
    }

    @Override
    public boolean userExists(User user) {
        boolean exists = false;
        DatabaseReference r = ref.child("Customer");

        /*
        ref.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(user.hashCode() == snapshot.getValue().hashCode()){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        */

        return exists;
    }

    @Override
    public void addUserDB(User user, String spinner) {
        ref.child("Users").child(spinner).child(Integer.toString(user.hashCode())).setValue(user);
    }
}
