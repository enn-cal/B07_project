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

public class ownerOrderAdapter extends RecyclerView.Adapter<ownerOrderAdapter.MyViewHolder>{

    ArrayList<Products> pList;
    ArrayList<String> uList;
    String email;
    Context context;

    public ownerOrderAdapter(Context context, ArrayList<String> uList, ArrayList<Products> pList){
        this.uList = uList;
        this.pList = pList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.owner_rc_view, parent, false);
        return new MyViewHolder(v).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String s = uList.get(position);
        Products product = pList.get(position);

        holder.customerName.setText(s);
        holder.item.setText(product.getItem());
        holder.brand.setText(product.getBrand());
        holder.price.setText(product.getPrice());
        holder.quantity.setText(product.getQuantity());
        email = s;


    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView customerName, item, brand, price, quantity;
        private ownerOrderAdapter oa;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerOrdered);
            item = itemView.findViewById(R.id.item_text_2);
            brand = itemView.findViewById(R.id.brand_text_2);
            price = itemView.findViewById(R.id.price_text_2);
            quantity = itemView.findViewById(R.id.quantity_text_2);

            itemView.findViewById(R.id.button13).setOnClickListener(view -> {
                oa.uList.remove(getAdapterPosition());
                oa.pList.remove(getAdapterPosition());
                oa.notifyItemRemoved(getAdapterPosition());
            });

/*
            button = itemView.findViewById(R.id.button13);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oa.uList.remove(getAdapterPosition());
                    oa.pList.remove(getAdapterPosition());
                    oa.notifyItemRemoved(getAdapterPosition());
                    oa.notifyItemRangeChanged(getAdapterPosition(), oa.uList.size());
                }
            });

 */
        }

        public MyViewHolder linkAdapter(ownerOrderAdapter oa){
            this.oa = oa;
            return this;
        }
    }

}
