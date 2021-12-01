package com.example.appsimulator;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable {

    private String item;
    private String brand;
    private String price; //price and quantity are string must ensure user enters ints
    private String quantity;

    public Products() {
    } //default constructor

    public Products(String item, String brand, String price, String quantity) {
        this.item = item;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    protected Products(Parcel in) {
        item = in.readString();
        brand = in.readString();
        price = in.readString();
        quantity = in.readString();
    }

    public String getItem() {
        return item;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode(){
        return this.item.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(item);
        parcel.writeString(brand);
        parcel.writeString(price);
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
}
