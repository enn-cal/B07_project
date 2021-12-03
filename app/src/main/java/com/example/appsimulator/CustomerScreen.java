package com.example.appsimulator;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerScreen extends AppCompatActivity {

    private FirebaseDatabase f_db;
    private DatabaseReference ref;
    private Button myCart;
    private Button signOut;
    private RecyclerView mRecyclerView;
    private int customerID;
    private Intent i;
    ArrayList<Products> cartItems;
    ArrayList<String> cartItemQuantities;
    CustomerRVAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);

        i = getIntent();
        customerID = Integer.parseInt(i.getStringExtra("ID"));
        //Log.i("TestID", String.valueOf(customerID));

        signOut = (Button) findViewById(R.id.storeListSignOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerScreen.this);
                builder.setMessage(R.string.sign_out_msg)
                        .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(CustomerScreen.this, "Signed Out", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CustomerScreen.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        mRecyclerView = findViewById(R.id.storeList);
        ArrayList<String> ownerNames = new ArrayList<>();
        ArrayList<String> ownerIDs = new ArrayList<>();
        cartItems = new ArrayList<>();
        cartItemQuantities = new ArrayList<>();
//        cartItemDetails = new ArrayList<>();

        myAdapter = new CustomerRVAdapter(this, ownerNames, ownerIDs, cartItems, cartItemQuantities);
//        myAdapter = new CustomerRVAdapter(this, ownerNames, ownerIDs, cartItemsDetails);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner"); //Might have to change this

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ownerNames.clear();
                ownerIDs.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    ownerNames.add(user.getName() + "'s Store");
                    ownerIDs.add(String.valueOf(user.hashCode()));
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myCart = findViewById(R.id.myCartButton);
        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerScreen.this, Cart.class);
                intent.putParcelableArrayListExtra("itemsArray", cartItems);
                intent.putStringArrayListExtra("quantitiesArray", cartItemQuantities);
                startActivityForResult(intent, 200);
            }
        });
    }

//    // method for MyCart button
//    public void viewCart (View view) {
//        Intent intent = new Intent(this, Cart.class);
//        intent.putParcelableArrayListExtra("itemsArray", cartItems);
//        intent.putStringArrayListExtra("quantitiesArray", cartItemQuantities);
//        startActivity(intent);
//    }

    // used to receive updated cart details from Shop activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the Shop activity with an OK result
        if (requestCode == 100 | requestCode == 200) {
            if (resultCode == RESULT_OK) {
                // Get cart data from Intent
                cartItems = data.getParcelableArrayListExtra("updatedCartItems");
                cartItemQuantities = (ArrayList<String>) data.getStringArrayListExtra("updatedCartItemQuantities");
                Log.d("CustomerScreen", "after result: " + cartItems.size());
                // update adapter to reflect changes
                myAdapter.updateAdapter(cartItems, cartItemQuantities);

            }
        }
    }
}