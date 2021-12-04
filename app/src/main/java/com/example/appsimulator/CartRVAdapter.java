package com.example.appsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.lang.Double;

public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.MyViewHolder> {
    Context ct;
    ArrayList<Products> items;
    ArrayList<String> itemQuantities;
    TextView totalCostView;

    public CartRVAdapter(Context ct, ArrayList<Products> items, ArrayList<String> itemQuantities, TextView totalCostView) {
        this.ct = ct;
        this.items = items;
        this.itemQuantities = itemQuantities;
        this.totalCostView = totalCostView;
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
        DecimalFormat df = new DecimalFormat("###.##");

        holder.brand.setText(items.get(position).getBrand());
        holder.itemName.setText(items.get(position).getItem());
        holder.price.setText(items.get(position).getPrice());

        //TODO Fix itemQuantities
        //holder.quantity.setText(itemQuantities.get(position));
        // multiplying price and quantity to obtain cost
//        holder.cost.setText("$" +
//                String.valueOf(Double.parseDouble(holder.price.getText().toString().replaceAll("[^\\d\\.]", "")) *
//                        Double.parseDouble(holder.quantity.getText().toString().replaceAll("[^\\d\\.]", "")))
//        );

        // increase quantity
        holder.increaseQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemQuantities.set(holder.getAdapterPosition(), String.valueOf(Integer.parseInt(itemQuantities.get(holder.getAdapterPosition())) + 1));
                holder.quantity.setText(itemQuantities.get(holder.getAdapterPosition()));

                holder.cost.setText("$" + df.format(Double.parseDouble(holder.price.getText().toString().replaceAll("[^\\d\\.]", "")) *
                                Double.parseDouble(holder.quantity.getText().toString().replaceAll("[^\\d\\.]", "")))
                );
                totalCostView.setText(((Cart)ct).updateTotalCost(items, itemQuantities));
                notifyDataSetChanged();
            }
        });

        // decrease quantity
        holder.decreaseQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(itemQuantities.get(holder.getAdapterPosition())) > 1) {
                    itemQuantities.set(holder.getAdapterPosition(), String.valueOf(Integer.parseInt(itemQuantities.get(holder.getAdapterPosition())) - 1));
                    holder.quantity.setText(itemQuantities.get(holder.getAdapterPosition()));

                    holder.cost.setText("$" + df.format(Double.parseDouble(holder.price.getText().toString().replaceAll("[^\\d\\.]", "")) *
                                    Double.parseDouble(holder.quantity.getText().toString().replaceAll("[^\\d\\.]", "")))
                    );
                    totalCostView.setText(((Cart)ct).updateTotalCost(items, itemQuantities));
                    notifyDataSetChanged();
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(holder.getAdapterPosition());
                itemQuantities.remove(holder.getAdapterPosition());
                totalCostView.setText(((Cart)ct).updateTotalCost(items, itemQuantities));
                notifyDataSetChanged();
            }
        });
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
        Button increaseQ;
        Button decreaseQ;
        Button remove;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.cartBrandText);
            itemName = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartPrice);
            quantity = itemView.findViewById(R.id.cartQuantity);
            cost = itemView.findViewById(R.id.cartCost);
            increaseQ = itemView.findViewById(R.id.increaseQuantity);
            decreaseQ = itemView.findViewById(R.id.decreaseQuantity);
            remove = itemView.findViewById(R.id.removeItem);
        }
    }
}
