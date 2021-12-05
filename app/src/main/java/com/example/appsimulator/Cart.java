package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart extends AppCompatActivity implements CartRVAdapter.OnItemListener {

    private Intent i;
    private RecyclerView recyclerView;
    private TextView totalCost;
    private DatabaseReference ref;
    private String customerID;
    private ArrayList<Products> cartItems;
    private CartRVAdapter myAdapter;
    private ValueEventListener listener;
    private Double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        i = getIntent();
        // get cart data from CustomerScreen (on MyCart clicked)
        //ArrayList<Products> cartItems = i.getParcelableArrayListExtra("itemsArray");

        cartItems = new ArrayList<>();
        customerID = i.getStringExtra("customerID");

        recyclerView = findViewById(R.id.cartItemsList);

        totalCost = findViewById(R.id.totalCost);
        updateTotalCost();

        myAdapter = new CartRVAdapter(this, cartItems, totalCost, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Gets cart data from database
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(customerID).child("Cart");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    cartItems.add(ds.getValue(Products.class));
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);
    }

    @Override
    public void updateTotalCost() {
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(customerID).child("Cart");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPrice = 0.0;
                for(DataSnapshot ds : snapshot.getChildren()){
                    Products p = ds.getValue(Products.class);
                    String[] a = p.getPrice().split("[^\\d\\.]");
                    double t_price = Double.parseDouble(a[1]);
                    totalPrice += Integer.parseInt(p.getQuantity()) * t_price;
                }
                Log.i("Cart", "$" + totalPrice);
                totalCost.setText("$" + totalPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);

        //return "$" + totalPrice;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("updatedCartItems", cartItems);
        Log.d("Shop", "on back pressed: " + cartItems.size());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onItemAdd(int pos, int repeats) {
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(customerID).child("Cart");
        ref.child(Integer.toString(pos)).child("quantity").setValue(Integer.toString(repeats));
        //cartItems.get(pos).setQuantity(Integer.toString(repeats));
        updateTotalCost();
    }

    @Override
    public void onItemRemove(int pos, int repeats) {
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child(customerID).child("Cart");
        // if repeats is zero then we remove item
        if (repeats == 0) {
            ref.child(Integer.toString(pos)).removeValue();
        }else
            ref.child(Integer.toString(pos)).child("quantity").setValue(Integer.toString(repeats));
        updateTotalCost();
    }

    @Override
    public void onItemDelete(int pos) {
        ref.child(Integer.toString(pos)).removeValue();
        updateTotalCost();
    }

    @Override
    public void onPause() {
        if(ref != null && listener != null){
            ref.removeEventListener(listener);
        }
        super.onPause();
    }
}