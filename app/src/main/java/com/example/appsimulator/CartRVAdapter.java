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
    TextView totalCostView;
    private OnItemListener monItemListener;

    public CartRVAdapter(Context ct, ArrayList<Products> items, TextView totalCostView, OnItemListener monItemListener) {
        this.ct = ct;
        this.items = items;
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

        String brandItemName = items.get(position).getBrand() + " - " + items.get(position).getItem(); //Combined brand and item name
        holder.itemName.setText(brandItemName);

        holder.price.setText(items.get(position).getPrice());
        holder.quantity.setText(items.get(position).getQuantity());
        String a = items.get(position).getPrice().substring(1);
        //String[] a = items.get(position).getPrice().split("[^\\d\\.]");
        double t_price = Double.parseDouble(a);
        t_price = t_price * Integer.parseInt(items.get(position).getQuantity());
        holder.cost.setText("$" + t_price);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //TextView brand;
        TextView itemName;
        TextView price;
        TextView quantity;
        TextView cost;
        Button increaseQ;
        Button decreaseQ;
        Button remove;
        OnItemListener onItemListener;

        // for updating cart
        double t_price;
        int count;

        public MyViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartPrice);
            quantity = itemView.findViewById(R.id.cartQuantity);
            cost = itemView.findViewById(R.id.cartCost);
            increaseQ = itemView.findViewById(R.id.increaseQuantity);
            decreaseQ = itemView.findViewById(R.id.decreaseQuantity);
            remove = itemView.findViewById(R.id.removeItem);
            TextView totalCost = itemView.findViewById(R.id.totalCost);

            this.onItemListener = onItemListener;

            increaseQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // the if statement will prevent out of bound errors, make sures we click on something
                    // before moving forward
                    if (!(getAdapterPosition() == -1)) {
                        count = Integer.parseInt(items.get(getAdapterPosition()).getQuantity());
                        count++;
                        String a = price.getText().toString().substring(1);
                        //String[] a = price.getText().toString().split("[^\\d\\.]");
                        double t_price = Double.parseDouble(a);
                        t_price = t_price * count;
                        quantity.setText("" + count);
                        cost.setText("$" + String.valueOf(t_price));
                        String [] temp = itemName.getText().toString().split(" - ");
                        onItemListener.onItemAdd(temp[1].hashCode(), count);
                    }
                }
            });

            decreaseQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(getAdapterPosition() == -1)) {
                        count = Integer.parseInt(items.get(getAdapterPosition()).getQuantity());
                        count--;
                        //String[] a = price.getText().toString().split("[^\\d\\.]");
                        String a = price.getText().toString().substring(1);
                        double t_price = Double.parseDouble(a);
                        t_price = t_price * count;
                        quantity.setText("" + count);
                        cost.setText("$" + String.valueOf(t_price));
                        String [] temp = itemName.getText().toString().split(" - ");
                        onItemListener.onItemRemove(temp[1].hashCode(), count);
                    }
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(getAdapterPosition() == -1)) {
                        String [] temp = itemName.getText().toString().split(" - ");
                        onItemListener.onItemDelete(temp[1].hashCode());
                    }
                }
            });
        }
    }
    // Creating Interface
    public interface OnItemListener{
        void onItemAdd(int pos, int repeats); // Increments Quantity
        void onItemRemove(int pos, int repeats); // Decrements Quantity
        void onItemDelete(int pos); // Removes Item
        void updateTotalCost();
    }
}
