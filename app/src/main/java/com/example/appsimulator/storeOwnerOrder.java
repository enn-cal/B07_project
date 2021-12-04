package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    private String sessionID;
    private ArrayList<String> customerList;
    private ArrayList<Products> productsList;
    private ownerOrderAdapter adapter;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_order);

        Bundle bundle = getIntent().getExtras();
        sessionID = bundle.getString("ID");

        //sessionID = "1902570695";

        rView = findViewById(R.id.storeOwnerRCView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        customerList = new ArrayList<>();
        productsList = new ArrayList<>();
        adapter = new ownerOrderAdapter(storeOwnerOrder.this, customerList, productsList,this);
        //DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(sessionID).child("Customers");
        rView.setAdapter(adapter);

        ref2 = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(sessionID).child("Customers");
/*
        Products products = new Products("Pie", "Pizza Hut", "$12.5", "30");
        Products products2 = new Products("Pizza", "Pizza Hut", "$50", "1");
        Products products3 = new Products("Pizza", "Pizza Hut", "$50", "1");
        Stores s = new Stores(sessionID);
        Orders o = new Orders("12345678");
        o.addProduct(products);
        o.addProduct(products2);
        o.addProduct(products3);
        //o.storeID = (sessionID);
        customerStore cs = new customerStore("d@gmail.com",sessionID);
        cs.setOrder(o);
        String key_path = ref2.push().getKey();
        ref2.child(key_path).setValue(cs);
        int i = 0;
        for (Products p : cs.orderList.order){
            ref2.child(key_path).child("order").child(Integer.toString(++i)).setValue(p);
        }

 */


        //hard code testing stuff
/*
        ref2 = db.getReference("Users").child("Store Owner").child("1902570695").child("Customers").child("m");
        Orders o = new Orders();
        Products p1 = new Products("A", "B", "$5.0", "5");
        Products p2 = new Products("C", "D", "$5.0", "5");
        o.addProduct(p1);
        o.addProduct(p2);
        customerStore cs = new customerStore("d@gmail.com", sessionID);
        cs.orderList = o;
        ref2.setValue(cs);
        //hard code testing stuff

 */
        //ref2 = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child(sessionID).child("Customers");
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
    @Override
    public void getEmailProduct(String email, Products p) {
        deleteOrder(email, p);
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
        startActivity(intent);
    }
}