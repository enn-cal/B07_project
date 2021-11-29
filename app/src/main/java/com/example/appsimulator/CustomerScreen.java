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
//    private FirebaseAuth.AuthStateListener authStateListener;
//    private  FirebaseAuth authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);

        recyclerView = findViewById(R.id.storeList);

        ArrayList<String> ownerNames = new ArrayList<>();

        CustomerRVAdapter myAdapter = new CustomerRVAdapter(this, ownerNames);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    ownerNames.add(user.getName() + "'s Store");
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        signOut = (Button) findViewById(R.id.button6);
////        setSignOut();
////        authStateListener = FirebaseAuth.getInstance();
//
//
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
////            setSignOut();
//            }
//        });


    }
//
//    private void setSignOut() {
////        authStateListener.signOut();
////        authStateListener = new FirebaseAuth.AuthStateListener() {
//        FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser() == null){
//                    //logout successful
//                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerScreen.this, R.style.DialogStyle);
//                    builder.setMessage("Logout successful !!");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
//                        }
//                    });
//                    builder.show();
//                }else{
//                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerScreen.this, R.style.DialogStyle);
//                    builder.setMessage("Oops..please try again !!");
//                    builder.setPositiveButton("OK", null);
//                    builder.show();
//                }
//            }
//        };
////        authStateListener = new FirebaseAuth.AuthStateListener() {
////            @Override
////            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
////                FirebaseUser user = firebaseAuth.getCurrentUser();
////
////                if (user != null) {
////                    Log.d("CustomerScreen", "onAuthStateChanged: signed_in: " + user.getUid());
////                } else {
////                    Log.d("CustomerScreen", "onAuthChanged: signed_out");
////                    Toast.makeText(CustomerScreen.this, "Signed Out", Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(CustomerScreen.this, MainActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//////                    startActivity(intent);
////                }
////            }
////        };
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        // remove lister to prevent it listening at all times
//        if(authStateListener != null) {
//            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
//        }
//    }
}