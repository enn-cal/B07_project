package com.example.appsimulator;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    ArrayList<Products> cartItems;
    String storeOwnerID;

    public ShopRVAdapter(Context ct, ArrayList<Products> storeProducts, ArrayList<Products> cartItems, String storeOwnerID) {
        this.ct = ct;
        this.storeProducts = storeProducts;
        this.cartItems = cartItems;
        this.storeOwnerID = storeOwnerID;
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
        holder.itemName.setText(storeProducts.get(position).getItem());
        holder.price.setText(storeProducts.get(position).getPrice());
        holder.brandName.setText(storeProducts.get(position).getBrand());
        if (cartItems.contains(storeProducts.get(position)))
            holder.addToCart.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return storeProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView brandName;
        TextView itemName;
        TextView price;

        Button addToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.shopItemName);
            price = itemView.findViewById(R.id.shopPrice);
            brandName = itemView.findViewById(R.id.shopBrandName);
            addToCart = itemView.findViewById(R.id.addToCartButton);

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(cartItems.contains(storeProducts.get(getLayoutPosition())))) {
                        Toast.makeText(ct, itemName.getText() + " added to cart.", Toast.LENGTH_SHORT).show();
                        Products p = storeProducts.get(getLayoutPosition());
                        p.setStoreID(storeOwnerID);
                        cartItems.add(p);
                        addToCart.setBackgroundColor(Color.WHITE);
                    } else {
                        Toast.makeText(ct, itemName.getText() + " is already added to cart.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
