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
    private TextView totalCost;
    private DatabaseReference ref;
    private String customerID;
    private ArrayList<Products> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        i = getIntent();
        // get cart data from CustomerScreen (on MyCart clicked)
        //ArrayList<Products> cartItems = i.getParcelableArrayListExtra("itemsArray");

        cartItems = new ArrayList<>();
        ArrayList<String> cartItemQuantities = i.getStringArrayListExtra("quantitiesArray");
        Log.i("Cart", "cartItemQuantities :" + cartItemQuantities.toString());
        customerID = i.getStringExtra("customerID");

        Log.i("Cart", "Cart Size: " + cartItems.size());
        recyclerView = findViewById(R.id.cartItemsList);

        totalCost = findViewById(R.id.totalCost);
        totalCost.setText(updateTotalCost(cartItems, cartItemQuantities));

        CartRVAdapter myAdapter = new CartRVAdapter(this, cartItems, cartItemQuantities, totalCost);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Gets cart data from database
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(customerID).child("Cart");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    cartItems.add(ds.getValue(Products.class));
                    //Log.i("Cart", "Cart Size: " + cartItems.size());
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String updateTotalCost(ArrayList<Products> mCartItems, ArrayList<String> mCartItemQuantities) {
        double cost = 0;

        for (int i=0; i<mCartItems.size(); i++) {
            cost += Double.parseDouble(mCartItems.get(i).getPrice().replaceAll("[^\\d\\.]", "")) *
                    Double.parseDouble(mCartItemQuantities.get(i).replaceAll("[^\\d\\.]", ""));
        }
        return "$" + String.valueOf(cost);
    }
}