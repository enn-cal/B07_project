package com.example.appsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShopRVAdapter extends RecyclerView.Adapter<ShopRVAdapter.MyViewHolder> {

    Context ct;
    ArrayList<Products> storeProducts;

    public ShopRVAdapter(Context ct, ArrayList<Products> storeProducts) {
        this.ct = ct;
        this.storeProducts = storeProducts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.shop_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopRVAdapter.MyViewHolder holder, int position) {
        holder.brand.setText(storeProducts.get(position).getBrand());
        holder.itemName.setText(storeProducts.get(position).getItem());
        holder.price.setText(storeProducts.get(position).getPrice());
        holder.quantity.setText(storeProducts.get(position).getQuantity());

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO!
                // code to add item details to cart_items array
                Toast.makeText(ct, holder.itemName.getText() + " added to cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView brand;
        TextView itemName;
        TextView price;
        TextView quantity;
        Button addToCart;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.shopBrandText);
            itemName = itemView.findViewById(R.id.shopItemName);
            price = itemView.findViewById(R.id.shopPrice);
            quantity = itemView.findViewById(R.id.shopQuantity);
            addToCart = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
