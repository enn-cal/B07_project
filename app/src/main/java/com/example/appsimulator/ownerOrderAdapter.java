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
    //transferOrder data;
    IMyViewHolder im;

    public ownerOrderAdapter(Context context, ArrayList<String> uList, ArrayList<Products> pList, IMyViewHolder im){
        this.uList = uList;
        this.pList = pList;
        this.context = context;
        this.im = im;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.owner_rc_view, parent, false);
        return new MyViewHolder(v, im).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products product = pList.get(holder.getAdapterPosition());
        String s = uList.get(holder.getAdapterPosition());

        holder.customerName.setText(s);
        holder.item.setText(product.getItem());
        holder.brand.setText(product.getBrand());
        holder.price.setText(product.getPrice());
        email = s;
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView customerName, item, brand, price;
        private ownerOrderAdapter oa;
        //transferOrder data;
        IMyViewHolder im;
        Button button;

        public MyViewHolder(@NonNull View itemView, IMyViewHolder im) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerOrdered);
            item = itemView.findViewById(R.id.item_text_2);
            brand = itemView.findViewById(R.id.brand_text_2);
            price = itemView.findViewById(R.id.price_text_2);
            this.im = im;

            button = itemView.findViewById(R.id.button13);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Save the values temporarily
                    String email = oa.uList.get(getAdapterPosition());
                    Products p = oa.pList.get(getAdapterPosition());
                    oa.uList.remove(getAdapterPosition());
                    oa.pList.remove(getAdapterPosition());
                    oa.notifyItemRemoved(getAdapterPosition());
                    oa.notifyItemRangeChanged(getAdapterPosition(), oa.getItemCount());
                    im.getEmailProduct(email,p);

                }
            });
        }

        public MyViewHolder linkAdapter(ownerOrderAdapter oa){
            this.oa = oa;
            return this;
        }
    }

    interface IMyViewHolder{
        public void getEmailProduct(String email, Products p);
    }
}
