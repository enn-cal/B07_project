package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
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

public class OwnerScreen extends AppCompatActivity implements transferOrder {

    private RecyclerView recycleView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private AdapterRv adapterRv;
    private ArrayList<Products> list;
    public String sessionID;
    public String storeEmail;
    private ValueEventListener listener;
    private Button signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_screen);

        Bundle bundle = getIntent().getExtras();
        sessionID = bundle.getString("ID");
        storeEmail = bundle.getString("email");
        ref = db.getReference("Users").child("Store Owner").child(sessionID).child("Store");

        recycleView = findViewById(R.id.recyclerview);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapterRv = new AdapterRv(OwnerScreen.this, list, this);

        recycleView.setAdapter(adapterRv);

        Button b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Products product = ds.getValue(Products.class);
                    list.add(product);
                }
                adapterRv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);

        signOut = (Button) findViewById(R.id.button5);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OwnerScreen.this);
                builder.setMessage(R.string.sign_out_msg)
                        .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(OwnerScreen.this, "Signed Out", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OwnerScreen.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.cancel, null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    //creates Dialog and adds product to recycler view
    public void openDialog() {
        addProductDialog pd = new addProductDialog();
        pd.setSessionID(sessionID);
        pd.show(getSupportFragmentManager(), "example dialog");
        if(pd.getProduct().getItem() != null && pd.getProduct().getBrand() != null && pd.getProduct().getPrice() != null){
            list.add(pd.getProduct());
            adapterRv.notifyDataSetChanged();
        }
    }

//    public void loginScreen(View v) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }

    public void orderScreen(View v) {
        Intent intent = new Intent(this, storeOwnerOrder.class);
        intent.putExtra("ID", sessionID);
        intent.putExtra("email", storeEmail);
        startActivity(intent);
    }


    @Override
    public void setProduct(Products p) {
        deleteProduct(p);
    }

    public void deleteProduct(Products p) {
        DatabaseReference ref = db.getReference("Users").child("Store Owner").child(sessionID).child("Store");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot current_products : snapshot.getChildren()) {
                    Products product = current_products.getValue(Products.class);
                    //if product that needs to be removed is found in database
                    if (product.equals(p)) {
                        current_products.getRef().removeValue();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPause() {
        if(ref != null && listener != null){
            ref.removeEventListener(listener);
            Log.i("TAG", "worked");
        }
        super.onPause();
    }



}