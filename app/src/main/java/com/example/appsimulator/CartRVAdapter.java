package com.example.appsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.lang.Double;

public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.MyViewHolder> {
    Context ct;
    ArrayList<Products> items;
    ArrayList<String> itemQuantities;

    public CartRVAdapter(Context ct, ArrayList<Products> items, ArrayList<String> itemQuantities) {
        this.ct = ct;
        this.items = items;
        this.itemQuantities = itemQuantities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.cart_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRVAdapter.MyViewHolder holder, int position) {
        holder.brand.setText(items.get(position).getBrand());
        holder.itemName.setText(items.get(position).getItem());
        holder.price.setText(items.get(position).getPrice());
        holder.quantity.setText(itemQuantities.get(position));
        // multiplying price and quantity to obtain cost
        holder.cost.setText(
                String.valueOf(Double.parseDouble(holder.price.getText().toString()) *
                        Double.parseDouble(holder.quantity.getText().toString()))
        );

//        holder.cost.setText(
//                String.valueOf(Double.parseDouble(items.get(position).getPrice()) *
//                        Double.parseDouble(items.get(position).getQuantity()))
//        );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView brand;
        TextView itemName;
        TextView price;
        TextView quantity;
        TextView cost;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.cartBrandText);
            itemName = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartPrice);
            quantity = itemView.findViewById(R.id.cartQuantity);
            cost = itemView.findViewById(R.id.cartCost);
        }
    }
}
