package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private Intent i;
    private RecyclerView recyclerView;
    private TextView totalCost;
    ArrayList<Products> cartItems;
    ArrayList<String> cartItemQuantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        i = getIntent();
        // get cart data from CustomerScreen (on MyCart clicked)
        cartItems = i.getParcelableArrayListExtra("itemsArray");
        cartItemQuantities = i.getStringArrayListExtra("quantitiesArray");

        recyclerView = findViewById(R.id.cartItemsList);

        totalCost = findViewById(R.id.totalCost);
        totalCost.setText(updateTotalCost(cartItems, cartItemQuantities));

        CartRVAdapter myAdapter = new CartRVAdapter(this, cartItems, cartItemQuantities, totalCost);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}