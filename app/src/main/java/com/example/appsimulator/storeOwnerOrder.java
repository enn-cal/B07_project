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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class storeOwnerOrder extends AppCompatActivity implements ownerOrderAdapter.IMyViewHolder{


    private RecyclerView rView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private ValueEventListener listener;
    private String sessionID;
    private ArrayList<String> customerList;
    private ArrayList<Products> productsList;
    private ownerOrderAdapter adapter;
    private String email;
    private String emailTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_order);

        Bundle bundle = getIntent().getExtras();
        sessionID = bundle.getString("ID");
        emailTemp = bundle.getString("email");

        rView = findViewById(R.id.storeOwnerRCView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        customerList = new ArrayList<>();
        productsList = new ArrayList<>();
        adapter = new ownerOrderAdapter(emailTemp,storeOwnerOrder.this, customerList, productsList,this);
        rView.setAdapter(adapter);

        ref2 = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner")
                .child(sessionID).child("Customers");
/*
        Products products = new Products("Pie", "Pizza Hut", "$12.5", "30","1");
        Products products2 = new Products("Pizza", "Pizza Hut", "$50", "1","1");
        Products products3 = new Products("Pizza", "Pizza Hut", "$50", "1","1");
        Stores s = new Stores(sessionID);
        //Orders o = new Orders("12345678");
        //o.addProduct(products);
        //o.addProduct(products2);
        //o.addProduct(products3);
        //o.storeID = (sessionID);
        customerStore cs = new customerStore("c@gmail.com",sessionID);
        cs.addProduct(products);
        cs.addProduct(products2);
        cs.addProduct(products3);
        //cs.setOrder(o);
        //String key_path = ref2.push().getKey();
        ref2.child(Integer.toString("c@gmail.com".hashCode())).setValue(cs);
        /*
        int i = 0;
        for (Products p : cs.orderList.order){
            ref2.child(Integer.toString("c@gmail.com".hashCode())).child("order").child(Integer.toString(++i)).setValue(p);
        }

         */



        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerList.clear();
                productsList.clear();
                //loop through customers given store owner id
                for (DataSnapshot customers : snapshot.getChildren()){
                    for(DataSnapshot customerInfo: customers.getChildren()){
                        if(customerInfo.exists() && customerInfo.getKey().equals("email")){
                            email = customerInfo.getValue().toString();
                        }
                        if(customerInfo.exists() && customerInfo.getKey().equals("order")){
                            for(DataSnapshot product: customerInfo.getChildren()){
                                Products p = product.getValue(Products.class);
                                productsList.add(p);
                                customerList.add(email);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref2.addValueEventListener(listener);
    }

    @Override
    public void orderComplete(String customer, String email, Products p, String customerEmail) {
        DatabaseReference ref = db.getReference("Users").child("Store Owner").child(sessionID).child("Customers").child(customer);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //notify when 1 item left as it will be completed/deleted from database
                if(snapshot.child("order").getChildrenCount() == 1){
                    notifyCustomer(customer, email);

                }
                deleteOrder(customerEmail, p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void notifyCustomer(String customer, String email){
        DatabaseReference ref = db.getReference("Users").child("Customer").child(customer);
        ref.child("orderCompleted").child(sessionID).setValue(new customerStore(email, sessionID));
    }

    public void deleteOrder(String orderEmail, Products orderProduct){
        DatabaseReference ref = db.getReference("Users").child("Store Owner").child(sessionID).child("Customers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot customers : snapshot.getChildren()){
                    for(DataSnapshot customerInfo: customers.getChildren()){
                        if(customerInfo.exists() && customerInfo.getKey().equals("email")){
                            //if current customer is not the customer's order that was just completed
                            if(!(customerInfo.getValue().toString().equals(orderEmail))){
                                break;
                            }
                        }
                        //if the emails matched then check the orders
                        if(customerInfo.exists() && customerInfo.getKey().equals("order")){
                            for(DataSnapshot product: customerInfo.getChildren()){
                                Products p = product.getValue(Products.class);
                                //if the current product is the one that has been completed
                                if(p.equals(orderProduct)){
                                    //remove the product from orders
                                    product.getRef().removeValue();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clickBack(View v){
        Intent intent;
        intent = new Intent(this, OwnerScreen.class);
        intent.putExtra("ID", sessionID);
        intent.putExtra("email", emailTemp);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        if(ref2 != null && listener != null){
            ref2.removeEventListener(listener);
        }
        super.onPause();
    }

}