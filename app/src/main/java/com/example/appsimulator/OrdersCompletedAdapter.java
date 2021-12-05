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

public class OrdersCompletedAdapter extends RecyclerView.Adapter<OrdersCompletedAdapter.ocViewHolder>{

    ArrayList<String> storeOwnerList;
    Context context;
    IocViewHolder ioc;

    public OrdersCompletedAdapter(Context context, ArrayList<String> storeOwnerList, IocViewHolder ioc) {
        this.context = context;
        this.storeOwnerList = storeOwnerList;
        this.ioc = ioc;
    }

    @NonNull
    @Override
    public ocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.orders_completed_items, parent, false);
        return new ocViewHolder(v, ioc).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ocViewHolder holder, int position) {
        String owner = storeOwnerList.get(holder.getAdapterPosition());
        holder.storeOwner.setText(owner);
    }

    @Override
    public int getItemCount() {
        return storeOwnerList.size();
    }

    public static class ocViewHolder extends RecyclerView.ViewHolder{

        TextView storeOwner;
        private OrdersCompletedAdapter oca;
        Button button;
        IocViewHolder ioc;

        public ocViewHolder(@NonNull View itemView, IocViewHolder ioc) {
            super(itemView);
            this.ioc = ioc;

            storeOwner = itemView.findViewById(R.id.from_store_owner);

            button = itemView.findViewById(R.id.okay_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = oca.storeOwnerList.get(getAdapterPosition());
                    oca.storeOwnerList.remove(getAdapterPosition());
                    oca.notifyDataSetChanged();
                    ioc.pickedUp(s);
                }
            });
        }

        public ocViewHolder linkAdapter(OrdersCompletedAdapter oca){
            this.oca = oca;
            return this;
        }
    }

    interface IocViewHolder{
        public void pickedUp(String storeEmail);
    }
}
