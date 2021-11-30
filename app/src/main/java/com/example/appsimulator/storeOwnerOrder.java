package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class storeOwnerOrder extends AppCompatActivity implements transferOrder{


    private RecyclerView rView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private String sessionID;
    private ArrayList<String> customerList;
    private ArrayList<Products> productsList;
    private ownerOrderAdapter adapter;
    private String email;
    private String orderEmail;
    private Products orderProduct;


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
        adapter = new ownerOrderAdapter(storeOwnerOrder.this, customerList, productsList,this);

        rView.setAdapter(adapter);

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(sessionID).child("Customers");
                Products products = new Products("Pie", "Pizza Hut", "$12.5", "30", "2");
                Products products2 = new Products("Pizza", "Pizza Hut", "$50", "1", sessionID);
                Products products3 = new Products("Pizza", "Pizza Hut", "$50", "1", sessionID);
                Stores s = new Stores(sessionID);
                Orders o = new Orders();
                o.addProduct(products);
                o.addProduct(products2);
                o.addProduct(products3);
                //o.setStoreID(sessionID);
                customerStore cs = new customerStore("d@gmail.com",sessionID);
                cs.setOrder(o.getStoreProducts(sessionID));
                String key_path = ref2.push().getKey();
                ref2.child(key_path).setValue(cs);
                int i = 0;
                for (Products p : cs.orderList.order)ref2.child(key_path).child("order").child(Integer.toString(++i)).setValue(p);
/*
        //hard code testing stuff
        ref2 = db.getReference("Users").child("Store Owner").child(sessionID).child("Customers");
        Orders o = new Orders();
        Products p1 = new Products("A", "B", "$5.0", "5");
        Products p2 = new Products("C", "D", "$5.0", "5");
        o.addProduct(p1);
        o.addProduct(p2);
        customerStore cs = new customerStore("d@gmail.com", sessionID);
        cs.order = o;
        ref2.setValue(cs);
        //hard code testing stuff

 */

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerList.clear();
                productsList.clear();
                //loop through customers given store owner id
                for (DataSnapshot customers : snapshot.getChildren()){
                    for(DataSnapshot customerInfo: customers.getChildren()){
                        if(customerInfo.exists() && customerInfo.getKey().equals("email")){
                            email = customerInfo.getValue().toString();
                            //customerList.add(ds.getValue().toString());
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
        });
    }
    public void completeOrder(){
    }

    @Override
    public void setEmailPassword(String email, Products p) {
        orderEmail = email;
        orderProduct = p;
        deleteOrder(orderEmail, orderProduct);
    }

    public void deleteOrder(String orderEmail, Products orderProduct){
        DatabaseReference ref = db.getReference("Users").child("Store Owner").child(sessionID).child("Customers");
        ref.addValueEventListener(new ValueEventListener() {
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
                                if(p.getItem().equals(orderProduct.getItem()) && p.getBrand().equals(orderProduct.getBrand())){
                                    //remove the product from orders
                                    product.getRef().removeValue();
                                    //break to avoid deleting more than one product
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
}