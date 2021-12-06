package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private String customerEmail;
    private ArrayList<Products> cartItems;
    private CartRVAdapter myAdapter;
    private ValueEventListener listener;
    private Button placeOrder;
    private Double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Bundle bundle = getIntent().getExtras();
        customerID = bundle.getString("customerID");
        customerEmail = bundle.getString("customerEmail");
        i = getIntent();
        // get cart data from CustomerScreen (on MyCart clicked)
        //ArrayList<Products> cartItems = i.getParcelableArrayListExtra("itemsArray");

        cartItems = new ArrayList<>();
        //customerID = i.getStringExtra("customerID");

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

    public void onPlaceOrder(View v){
        ref = FirebaseDatabase.getInstance().getReference("Users").child("Customer")
                .child(customerID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.child("email").getValue(String.class);
                cartItems.clear();

                // Populates the cartItems by reading the Cart tree
                for(DataSnapshot ds : snapshot.child("Cart").getChildren()){
                    Products p = ds.getValue(Products.class);
                    cartItems.add(p);
                }

                // going into store owner tree
                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users").
                        child("Store Owner");

                for (Products p : cartItems)
                    Log.i("Items", p.getItem());

                // add to respective Store Owners
                for (int i = 0; i < cartItems.size(); i++){
                    customerStore cs = new customerStore(email, cartItems.get(i).getStoreID());
                    for (int j = 0; j < cartItems.size(); j++){
                        // check if the other products have the same StoreID
                        if (cartItems.get(i).getStoreID().equals(cartItems.get(j).getStoreID())) {
                            cs.order.add(cartItems.get(j));
                        }
                    }
                    ref2.child(cartItems.get(i).getStoreID()).child("Customers").child(customerID).setValue(cs);
                }

                // delete from our cart
                for(DataSnapshot ds : snapshot.child("Cart").getChildren()){
                    Products p = ds.getValue(Products.class);
                    cartItems.remove(p);
                    ds.getRef().removeValue();
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // We leave activity
        Intent intent;
        intent = new Intent(this, CustomerScreen.class);
        intent.putExtra("ID", customerID);
        startActivity(intent);
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
                    String a = p.getPrice().substring(1);
                    double t_price = Double.parseDouble(a);
                    totalPrice += Integer.parseInt(p.getQuantity()) * t_price;
                }
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
/*
    @Override
    public void setCartItems(ArrayList<Products> cartItems) {
        tempCart = cartItems;
    }

 */

    @Override
    public void onPause() {
        if(ref != null && listener != null){
            ref.removeEventListener(listener);
        }
        super.onPause();
    }
}