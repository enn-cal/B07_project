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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerScreen extends AppCompatActivity{

    private FirebaseDatabase f_db;
    private DatabaseReference ref;
    private Button myCart;
    private Button signOut;
    private Button ordersCompleted;
    private RecyclerView mRecyclerView;
    private String customerID;
    private String sessionID;
    private String customerEmail;
    private Intent i;
    ArrayList<Products> cartItems;
    CustomerRVAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);

        Bundle bundle = getIntent().getExtras();
        sessionID = bundle.getString("ID");
        customerEmail = bundle.getString("email");

        i = getIntent();
        customerID = i.getStringExtra("ID");

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


        ordersCompleted = findViewById(R.id.storeListOrders);

        ordersCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerScreen.this, OrdersCompleted.class);
                intent.putExtra("ID", sessionID);
                startActivity(intent);
            }
        });


        mRecyclerView = findViewById(R.id.storeList);
        ArrayList<String> ownerNames = new ArrayList<>();
        ArrayList<String> ownerIDs = new ArrayList<>();
        cartItems = new ArrayList<>();


        myAdapter = new CustomerRVAdapter(this, ownerNames, ownerIDs, cartItems, sessionID);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {//changed
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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                        .child("Store Owner");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            if (ds.child("Customers").child(customerID).exists()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerScreen.this);
                                builder.setMessage("Adding new Orders will cancel previous orders")
                                        .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(CustomerScreen.this, "Previous Orders will be cancelled on new order", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CustomerScreen.this, Cart.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("customerID", customerID);
                                                intent.putExtra("customerEmail", customerEmail);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton(R.string.cancel, null);
                                AlertDialog alert = builder.create();
                                alert.show();
                                return;
                            } else {
                                Intent intent = new Intent(CustomerScreen.this, Cart.class);
                                intent.putParcelableArrayListExtra("itemsArray", cartItems);
                                intent.putExtra("customerID", customerID);
                                intent.putExtra("customerEmail", customerEmail);
                                startActivityForResult(intent, 200);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    // used to receive updated cart details from Shop activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the Shop activity with an OK result
        if (requestCode == 100 | requestCode == 200) {
            if (resultCode == RESULT_OK) {
                // Get cart data from Intent
                cartItems = data.getParcelableArrayListExtra("updatedCartItems");
                Log.d("CustomerScreen", "after result: " + cartItems.size());
                // update adapter to reflect changes
                myAdapter.updateAdapter(cartItems);

            }
        }
    }
}