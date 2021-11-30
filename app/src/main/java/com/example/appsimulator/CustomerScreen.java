package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private RecyclerView recyclerView;
    private int customerID;
    private Intent i;

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
//                Log.d("CustomerScreen", "onClick: attempting to sign out the user");
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(CustomerScreen.this, "Signed Out", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(CustomerScreen.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);

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

        recyclerView = findViewById(R.id.storeList);
        ArrayList<String> ownerNames = new ArrayList<>();
        ArrayList<String> ownerIDs = new ArrayList<>();

        CustomerRVAdapter myAdapter = new CustomerRVAdapter(this, ownerNames, ownerIDs);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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


    }
}