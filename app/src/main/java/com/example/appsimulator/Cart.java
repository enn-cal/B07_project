package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private Intent i;
    private RecyclerView recyclerView;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        i = getIntent();
        // get cart data from CustomerScreen (on MyCart clicked)
        ArrayList<Products> cartItems = i.getParcelableArrayListExtra("itemsArray");
        ArrayList<String> cartItemQuantities = i.getStringArrayListExtra("quantitiesArray");

        recyclerView = findViewById(R.id.cartItemsList);

        CartRVAdapter myAdapter = new CartRVAdapter(this, cartItems, cartItemQuantities);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("Cart", "after MyCart pressed: " + cartItems.size());


        // TODO!!!
        // CHANGE THIS STUFF!!
        // use cartItems and cartItemQuantities to populate the recyclerView

//        items.add(new Products("Chips", "Lays", "$3", "200"));
//        itemQuantities.add("1");

//        ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(storeOwnerID).child("Store");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                items.clear();
//                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
//                    items.add(productSnapshot.getValue(Products.class));
//                }
//
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}