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
    private ArrayList<String> cartItemQuantities;
    private  CartRVAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        i = getIntent();
        // get cart data from CustomerScreen (on MyCart clicked)
        //ArrayList<Products> cartItems = i.getParcelableArrayListExtra("itemsArray");

        cartItems = new ArrayList<>();
        cartItemQuantities = i.getStringArrayListExtra("quantitiesArray");
        //Log.i("Cart", "cartItemQuantities :" + cartItemQuantities.toString());
        customerID = i.getStringExtra("customerID");

        recyclerView = findViewById(R.id.cartItemsList);

        totalCost = findViewById(R.id.totalCost);
        totalCost.setText(updateTotalCost(cartItems, cartItemQuantities));

        myAdapter = new CartRVAdapter(this, cartItems, cartItemQuantities, totalCost, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //

        //

        //Gets cart data from database
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child("1302843028").child("Cart"); //TODO remove fixed path
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
        DecimalFormat df = new DecimalFormat("###.##");
        for (int i=0; i<mCartItems.size(); i++) {
            cost += Double.parseDouble(mCartItems.get(i).getPrice().replaceAll("[^\\d\\.]", "")) *
                    Double.parseDouble(mCartItemQuantities.get(i).replaceAll("[^\\d\\.]", ""));
        }
        return "$" + df.format(cost);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("updatedCartItems", cartItems);
        resultIntent.putStringArrayListExtra("updatedCartItemQuantities", cartItemQuantities);
        Log.d("Cart", "on back pressed: " + cartItems.size());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onItemAdd(int pos, int repeats) {
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child("1302843028").child("Cart"); //TODO remove fixed path
        for(int i = 1; i < repeats; i++)
            ref.child(Integer.toString(myAdapter.getItemCount())).setValue(cartItems.get(pos));
    }

    @Override
    public void onItemRemove(int pos, int repeats) {
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer").child("1302843028").child("Cart"); //TODO remove fixed path
        // assuming item names are unique
        Query itemQuery = ref.orderByChild("item").equalTo(cartItems.get(pos).getItem()).limitToLast(repeats);
        itemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                    ds.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}