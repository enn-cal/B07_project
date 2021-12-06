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

public class Shop extends AppCompatActivity implements ShopRVAdapter.IShop{

    private TextView shopText;
    private Intent i;
    private RecyclerView recyclerView;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;
    private String storeOwnerID;
    private ArrayList<Products> cartItems;
    private ArrayList<Products> products;
    private ValueEventListener listener;
    private String customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        i = getIntent();
        customerID = i.getStringExtra("customerID");
        // to add store owner's name to the store page
        shopText = findViewById(R.id.shopText);
        shopText.setText(i.getStringExtra("StoreName"));
        storeOwnerID = i.getStringExtra("OwnerID");

        // to update items for cart
        cartItems = i.getParcelableArrayListExtra("itemsArray");

        recyclerView = findViewById(R.id.userShopList);
        products = new ArrayList<>();

        ShopRVAdapter myAdapter = new ShopRVAdapter(Shop.this, products, cartItems, storeOwnerID, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(storeOwnerID).child("Store");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Products p = productSnapshot.getValue(Products.class);
                    products.add(p);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);
        /*
        ref.addListenerForSingleValueEvent(new ValueEventListener() {//changed
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

         */
    }

    // to prevent calling of CustomerScreen onCreate on backPressed
    // to prevent creation of new ArrayList for cartItems and cartItemQuantities
    // so that they accumulate over activities
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("updatedCartItems", cartItems);
        Log.d("Shop", "on back pressed: " + cartItems.size());
        setResult(RESULT_OK, resultIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        if(ref != null && listener != null){
            ref.removeEventListener(listener);
        }
        super.onPause();
    }

    @Override
    public void addToCartDB(Products product) {
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(customerID).child("Cart");
        ref.child(Integer.toString(product.hashCode())).setValue(product);
    }
}
