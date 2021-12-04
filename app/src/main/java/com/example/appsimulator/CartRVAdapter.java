package com.example.appsimulator;

import android.content.Context;
import android.util.Log;
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
    private OnItemListener monItemListener;

    public CartRVAdapter(Context ct, ArrayList<Products> items, ArrayList<String> itemQuantities, TextView totalCostView, OnItemListener monItemListener) {
        this.ct = ct;
        this.items = items;
        this.itemQuantities = itemQuantities;
        this.totalCostView = totalCostView;
        this.monItemListener = monItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.cart_items, parent, false);
        return new MyViewHolder(view, monItemListener);
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
//        holder.increaseQ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("IncreaseQ", "Clicked");
////                itemQuantities.set(holder.getAdapterPosition(), String.valueOf(Integer.parseInt(itemQuantities.get(holder.getAdapterPosition())) + 1));
////                holder.quantity.setText(itemQuantities.get(holder.getAdapterPosition()));
////
////                holder.cost.setText("$" + df.format(Double.parseDouble(holder.price.getText().toString().replaceAll("[^\\d\\.]", "")) *
////                                Double.parseDouble(holder.quantity.getText().toString().replaceAll("[^\\d\\.]", "")))
////                );
////                totalCostView.setText(((Cart)ct).updateTotalCost(items, itemQuantities));
////                notifyDataSetChanged();
//            }
//        });

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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView brand;
        TextView itemName;
        TextView price;
        TextView quantity;
        TextView cost;
        Button increaseQ;
        Button decreaseQ;
        Button remove;
        OnItemListener onItemListener;

        // for updating cart
        int count = 1;
        double t_price;

        public MyViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            brand = itemView.findViewById(R.id.cartBrandText);
            itemName = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartPrice);
            quantity = itemView.findViewById(R.id.cartQuantity);
            cost = itemView.findViewById(R.id.cartCost);
            increaseQ = itemView.findViewById(R.id.increaseQuantity);
            decreaseQ = itemView.findViewById(R.id.decreaseQuantity);
            remove = itemView.findViewById(R.id.removeItem);
            this.onItemListener = onItemListener;

            increaseQ.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            count++;
            String[] a = price.getText().toString().split("[^\\d\\.]");
            Log.i("TAG", a[0]);
            double t_price = Double.parseDouble(a[1]);
            t_price = t_price * count;
            quantity.setText("" + count);
            cost.setText(String.valueOf(t_price));
            onItemListener.onItemAdd(getAdapterPosition(), count);
        }
    }
    // Creating Interface
    public interface OnItemListener{
        void onItemAdd(int pos, int repeats);
    }
}
