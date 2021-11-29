package com.example.appsimulator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerRVAdapter extends RecyclerView.Adapter<CustomerRVAdapter.MyViewHolder> {

    ArrayList<String> ownerNames;
    ArrayList<String> ownerIDs;
    Context ct;

    public CustomerRVAdapter(Context ct, ArrayList<String> ownerNames, ArrayList<String> ownerIDs) {
        this.ct = ct;
        this.ownerNames = ownerNames;
        this.ownerIDs = ownerIDs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.store_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.storeName.setText(ownerNames.get(position));
    }

    @Override
    public int getItemCount() {
        return ownerNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button storeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.storeName);
            storeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.i("TestClick", String.valueOf(getLayoutPosition()));
                    Intent intent = new Intent(ct, Shop.class);
                    intent.putExtra("StoreName", ownerNames.get(getLayoutPosition()));
                    intent.putExtra("OwnerID", ownerIDs.get(getLayoutPosition()));
                    ct.startActivity(intent);
                }
            });
        }
    }
}
