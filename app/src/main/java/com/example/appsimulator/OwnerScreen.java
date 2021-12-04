package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerScreen extends AppCompatActivity {

    private RecyclerView recycleView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref = db.getReference("Users").child("Store Owner").child("1902570695").child("Products"); // path is hardcoded
    private AdapterRv adapterRv;
    private ArrayList<Products> list;
    public Stores s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_screen);

        //s = new Stores(1902570695); //hardcoded

        recycleView = findViewById(R.id.recyclerview);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapterRv = new AdapterRv(this, list);

        recycleView.setAdapter(adapterRv);

        Button b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){
                    Products product = ds.getValue(Products.class);
                    list.add(product);
                    //s.products.add(product);
                }
                adapterRv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    //adds Store's products to DB
    public void updateStoreProductDB(Stores store){
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Stores");
        for(Products p: store.products){
            dref.child(Integer.toString(store.getStoreID())).setValue(p);
        }
    }

     */

    //creates Dialog and adds product to recycler view
    public void openDialog(){
        addProductDialog pd = new addProductDialog();
        pd.show(getSupportFragmentManager(), "example dialog");
        list.add(pd.getProduct());
        adapterRv.notifyItemInserted(list.size()-1);

        //ADD TO DATABASE CODE
        //s.products.add(pd.getProduct());
        //Log.i("TAG", s.products.toString()); THIS GIVES NULL POINTER EXCEPTION. S.PRODUCTS = NULL FOR SOME REASON
        //updateStoreProductDB(s);
    }


    public void loginScreen(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}