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

public class AdapterRv extends RecyclerView.Adapter<AdapterRv.ViewHolder> {

    ArrayList<Products> plist;
    Context context;
    transferOrder data;

    public AdapterRv(Context context, ArrayList<Products> plist, transferOrder data){
        this.plist = plist;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v, data).linkAdapter(this);
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
        private AdapterRv rv;
        transferOrder data;
        Button button;

        public ViewHolder(@NonNull View itemView, transferOrder data) {
            super(itemView);
            item = itemView.findViewById(R.id.item_text);
            brand = itemView.findViewById(R.id.brand_text);
            price = itemView.findViewById(R.id.price_text);
            quantity = itemView.findViewById(R.id.quantity_text);
            this.data = data;

            button = itemView.findViewById(R.id.button14);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Products p = rv.plist.get(getAdapterPosition());
                    rv.plist.remove(getAdapterPosition());
                    rv.notifyDataSetChanged();
                    rv.notifyItemRangeChanged(getAdapterPosition(), rv.getItemCount());
                    data.setEmailPassword("", p);
                }
            });
        }

        public ViewHolder linkAdapter (AdapterRv rv){
            this.rv = rv;
            return this;
        }
    }
}
