package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class storeOwnerOrder extends AppCompatActivity {


    private RecyclerView rView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private String sessionID;
    private ArrayList<String> customerList;
    private ArrayList<Products> productsList;
    private ownerOrderAdapter adapter;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_order);

        //Bundle bundle = getIntent().getExtras();
        //sessionID = bundle.getString("ID");

        sessionID = "1902570695";

        rView = findViewById(R.id.storeOwnerRCView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        customerList = new ArrayList<>();
        productsList = new ArrayList<>();
        adapter = new ownerOrderAdapter(this, customerList, productsList);

        rView.setAdapter(adapter);


        //hard code testing stuff
        /*
        ref2 = db.getReference("Users").child("Store Owner").child("1902570695").child("Customers").child("m");
        Orders o = new Orders();
        o.setStoreID("123456");
        Products p3 = new Products("PIE", "pizzahut", "$50", "3");
        o.addProduct(p3);
        customerStore cs = new customerStore("m@gmail.com", "123456");
        cs.order = o;
        ref2.setValue(cs);

         */
        ref2 = db.getReference("Users").child("Store Owner").child(sessionID).child("Customers");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.exists()){
                        for(DataSnapshot d: ds.getChildren()){
                            if(d.exists() && d.getKey().equals("email")){
                                Log.i("TAG", d.getValue().toString());
                                email = d.getValue().toString();
                                //customerList.add(d.getValue().toString());
                            }
                            if(d.exists() && d.getKey().equals("order")){
                                for(DataSnapshot p: d.getChildren()){
                                    Products product = p.getValue(Products.class);
                                    Log.i("TAG", product.getItem());
                                    customerList.add(email);
                                    productsList.add(product);
                                }
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void completeOrder(){
        /*
        Intent intent = new Intent(this, customerOrderList.class);
        intent.putExtra("storeID",sessionID);
        intent.putExtra("userID", adapter.email);
        startActivity(intent);
        */

    }
}