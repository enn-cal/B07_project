package com.example.appsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRv extends RecyclerView.Adapter<AdapterRv.ViewHolder> {

    ArrayList<Products> plist;
    Context context;

    public AdapterRv(Context context, ArrayList<Products> plist){
        this.plist = plist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = plist.get(position);
        holder.item.setText(product.getItem());
        holder.brand.setText(product.getBrand());
        holder.price.setText(product.getPrice());
        holder.quantity.setText(product.getQuantity());
    }

    @Override
    public int getItemCount() {
        return plist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView item, brand, price, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_text);
            brand = itemView.findViewById(R.id.brand_text);
            price = itemView.findViewById(R.id.price_text);
            quantity = itemView.findViewById(R.id.quantity_text);
        }
    }
}
