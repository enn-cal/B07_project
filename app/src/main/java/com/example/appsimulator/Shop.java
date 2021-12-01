package com.example.appsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Shop extends AppCompatActivity {

    private TextView shopText;
    private Intent i;
    private RecyclerView recyclerView;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;
    private String storeOwnerID;
    private ArrayList<Products> cartItems;
    private ArrayList<String> cartItemQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        i = getIntent();
        // to add store owner's name to the store page
        shopText = findViewById(R.id.shopText);
        storeOwnerID = i.getStringExtra("OwnerID");
        shopText.setText(i.getStringExtra("StoreName"));
        // to update items for cart
        cartItems = i.getParcelableArrayListExtra("itemsArray");
        cartItemQuantities = i.getStringArrayListExtra("quantitiesArray");


        recyclerView = findViewById(R.id.userShopList);
        ArrayList<Products> products = new ArrayList<>();

        ShopRVAdapter myAdapter = new ShopRVAdapter(this, products, cartItems, cartItemQuantities);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(storeOwnerID).child("Store");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    products.add(productSnapshot.getValue(Products.class));
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // to prevent calling of CustomerScreen onCreate on backPressed
    // to prevent creation of new ArrayList for cartItems and cartItemQuantities
    // so that they accumulate over activities
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("updatedCartItems", cartItems);
        resultIntent.putStringArrayListExtra("updatedCartItemQuantities", cartItemQuantities);
        Log.d("Shop", "on back pressed: " + cartItems.size());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
