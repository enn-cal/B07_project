package com.example.appsimulator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CartProductDetails implements Parcelable {

//        ArrayList<Products> item;
//        ArrayList<String> quantity;
//        ArrayList<String> storeID;
//        ArrayList<String> price;

    Products item;
    String quantity;
    String storeID;

//        CartProductDetails(ArrayList<Products> item, ArrayList<String> quantity, ArrayList<String> storeID, ArrayList<String> price) {
        CartProductDetails(Products item, String quantity, String storeID) {

            this.item = item;
            this.quantity = quantity;
            this.storeID = storeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected CartProductDetails(Parcel in) {
        item = in.readParcelable(getClass().getClassLoader());
        storeID = in.readString();
        quantity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeParcelable(item);
        parcel.writeString(storeID);
        parcel.writeString(quantity);
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

//    public void add (Products item, String quantity, String storeID, String price) {
//            this.item.add(item);
//            this.quantity.add(quantity);
//            this.storeID.add(storeID);
//            this.price.add(price);
//    }
}