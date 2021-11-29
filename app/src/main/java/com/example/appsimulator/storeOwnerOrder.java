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

        ref2 = db.getReference("Users").child("Store Owner").child(sessionID).child("Customers");
//

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Daniyals version
                productsList.clear();
                for (DataSnapshot ds1 : snapshot.getChildren()){
                    for (DataSnapshot ds2 : ds1.getChildren()){
                        // adds email to customerList;
                        if(ds2.exists() && ds2.getKey().equals("email")){
                            //customerList.add(ds2.getValue().toString());
                             email = ds2.getValue().toString();
                        }
//                        DataSnapshot orders = ds2.child("order"); // goes to last order path to get all orders made by customer
                        // final loop to get the orders
                        for (DataSnapshot ds3 : ds2.getChildren()){
                            Products product = ds3.getValue(Products.class);
                            Log.i(ds3.getKey(), ds3.getValue().toString());
                            productsList.add(product);
                        }
                        for (DataSnapshot ds3 : ds2.getChildren()){
                            Products product = ds3.getValue(Products.class);
                            Log.i(ds3.getKey(), ds3.getValue().toString());
                            customerList.add(email);
                            productsList.add(product);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
                //Williams version
//                for (DataSnapshot datasnap : snapshot.getChildren()){
//                    for(DataSnapshot ds: datasnap.getChildren()){
//                        if(ds.exists() && ds.getKey().equals("email")){
//                            customerList.add(ds.getValue().toString());
//                        }
//                        if(ds.exists() && ds.getKey().equals("order")){
//                            for(DataSnapshot d: ds.getChildren()){
//                                if(d.exists() && d.getKey().equals("order")){
//                                    for(DataSnapshot p: d.getChildren()){
//                                        Products product = p.getValue(Products.class);
//                                        productsList.add(product);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void completeOrder(){


    }
}