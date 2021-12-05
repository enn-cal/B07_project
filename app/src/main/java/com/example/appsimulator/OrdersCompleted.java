package com.example.appsimulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersCompleted extends AppCompatActivity implements OrdersCompletedAdapter.IocViewHolder {

    private RecyclerView ocView;
    private ArrayList<String> storeOwnersList;
    private OrdersCompletedAdapter ocAdapter;
    private String sessionID;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_completed);

        ocView = findViewById(R.id.orders_completed_screen);
        ocView.setHasFixedSize(true);
        ocView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        sessionID = bundle.getString("ID");

        storeOwnersList = new ArrayList<>();

        ocAdapter = new OrdersCompletedAdapter(OrdersCompleted.this, storeOwnersList, this);

        ocView.setAdapter(ocAdapter);

        ref = db.getReference("Users").child("Customer").child(sessionID).child("orderCompleted");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeOwnersList.clear();
                for(DataSnapshot orders: snapshot.getChildren()){
                    for(DataSnapshot email: orders.getChildren()){
                        //if the current key holds email values
                        if(email.exists() && email.getKey().equals("email")){
                            String s = email.getValue().toString();
                            storeOwnersList.add(s);
                        }
                    }
                }
                ocAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void pickedUp(String storeEmail) {
        DatabaseReference ref = db.getReference("Users").child("Customer").child(sessionID).child("orderCompleted");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot store_owners: snapshot.getChildren()){
                    if(store_owners.getKey().equals(Integer.toString(storeEmail.hashCode()))){
                        store_owners.getRef().removeValue();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}