package com.example.appsimulator;

import android.content.Context;
import android.graphics.Color;
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

    public ShopRVAdapter(Context ct, ArrayList<Products> storeProducts, ArrayList<Products> cartItems) {
        this.ct = ct;
        this.storeProducts = storeProducts;
        this.cartItems = cartItems;
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
        String brandItemName = storeProducts.get(position).getBrand() + " - " + storeProducts.get(position).getItem(); //Combined brand and item name
        holder.itemName.setText(brandItemName);
        holder.price.setText(storeProducts.get(position).getPrice());
        holder.quantity.setText(storeProducts.get(position).getQuantity());
        if (cartItems.contains(storeProducts.get(position)))
            holder.addToCart.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return storeProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView price;
        TextView quantity;
        Button addToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.shopItemName);
            price = itemView.findViewById(R.id.shopPrice);
            quantity = itemView.findViewById(R.id.shopQuantity);
            addToCart = itemView.findViewById(R.id.addToCartButton);

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(cartItems.contains(storeProducts.get(getLayoutPosition())))) {
                        Toast.makeText(ct, itemName.getText() + " added to cart.", Toast.LENGTH_SHORT).show();
                        cartItems.add(storeProducts.get(getLayoutPosition()));
                        addToCart.setBackgroundColor(Color.WHITE);
                    } else {
                        Toast.makeText(ct, itemName.getText() + " is already added to cart.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
