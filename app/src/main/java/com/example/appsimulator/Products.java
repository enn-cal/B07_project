package com.example.appsimulator;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import android.os.Parcel;
import android.os.Parcelable;
@IgnoreExtraProperties

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

    public void setItem(String item){this.item = item;}

    public void setBrand(String brand){this.brand = brand;}

    public void setPrice(String price){this.price= price;}

    public void setQuantity(String quantity){this.quantity = quantity;}



    @Override
    public int hashCode(){
        return this.item.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Products other = (Products) obj;
        return item.equals(other.item) && brand.equals(other.brand) && price.equals(other.price);
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
