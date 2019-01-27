package com.example.barcodescanner;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.JsonReader;

import org.json.*;
import java.io.*;
import java.util.*;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Parcel;

import android.app.Activity;

public class Product implements Parcelable {

    private int mData;

    String productCode;
    double price;
    String productName;
    Boolean xsAvailability;
    Boolean sAvailability;
    Boolean mAvailability;
    Boolean lAvailability;
    Boolean xlAvailability;

    public Product (String code, double price, String name, Boolean xsAvailability, Boolean sAvailability, Boolean mAvailability, Boolean lAvailability, Boolean xlAvailability) {
        this.productCode = code;
        this.price = price;
        this.productName = name;
        this.xsAvailability = xsAvailability;
        this.sAvailability = sAvailability;
        this.mAvailability = mAvailability;
        this.lAvailability = lAvailability;
        this.xlAvailability = xlAvailability;
    }

    public int describeContents () {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeSerializable((Serializable) this.productCode);
        out.writeSerializable((Serializable) this.price);
        out.writeSerializable((Serializable) this.productName);
        out.writeSerializable((Serializable) this.xsAvailability);
        out.writeSerializable((Serializable) this.sAvailability);
        out.writeSerializable((Serializable) this.mAvailability);
        out.writeSerializable((Serializable) this.lAvailability);
        out.writeSerializable((Serializable) this.xlAvailability);
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        this.productCode = (String) in.readSerializable();
        this.price = (Double) in.readSerializable();
        this.productName = (String) in.readSerializable();
        this.xsAvailability = (Boolean) in.readSerializable();
        this.sAvailability = (Boolean) in.readSerializable();
        this.mAvailability = (Boolean) in.readSerializable();
        this.lAvailability = (Boolean) in.readSerializable();
        this.xlAvailability = (Boolean) in.readSerializable();
    }

    @Override
    public String toString () {
        return "Product Name: " + this.productName + " - Price: " + this.price;
    }
}