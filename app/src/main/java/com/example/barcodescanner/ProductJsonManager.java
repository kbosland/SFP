package com.example.barcodescanner;

import android.util.JsonReader;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ProductJsonManager {
    String filename;
    String jsonString;
    List<Product> productList;

    public ProductJsonManager (String name, InputStream is) {
        this.filename = name;
        try {
            this.jsonString = readJsonFromFile(is);
        } catch (IOException ie) {
            this.jsonString = "";
        }
        this.productList = getProductList();
    }

    public List<Product> getProductList () {

        ArrayList<Product> prodList = new ArrayList<Product>();
        try {
            JSONObject obj = new JSONObject(this.jsonString);
            JSONArray productsArray = obj.getJSONArray("products");
            prodList = new ArrayList<Product>();

            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject insideObj = productsArray.getJSONObject(i);
                prodList.add(new Product(insideObj.getString("id"),
                                            Double.parseDouble(insideObj.getString("price")),
                                            insideObj.getString("name"),
                                            Boolean.parseBoolean(insideObj.getString("xsAvailability")),
                                            Boolean.parseBoolean(insideObj.getString("sAvailability")),
                                            Boolean.parseBoolean(insideObj.getString("mAvailability")),
                                            Boolean.parseBoolean(insideObj.getString("lAvailability")),
                                            Boolean.parseBoolean(insideObj.getString("xlAvailability"))
                                            ));
            }


        } catch (JSONException je) {

        }

        return prodList;
    }

    public String readJsonFromFile (InputStream is) throws IOException {
        String json = "";
        try {
            InputStream inputStream = is;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ie) {
            return json;
        }
    }

    public Product findProduct (String prodID) {
        for (int i = 0; i < this.productList.size(); i++) {
            if (this.productList.get(i).productCode.equals(prodID)) {
                double price = this.productList.get(i).price;
                String productName = this.productList.get(i).productName;
                Boolean xs = this.productList.get(i).xsAvailability;
                Boolean s = this.productList.get(i).sAvailability;
                Boolean m = this.productList.get(i).mAvailability;
                Boolean l = this.productList.get(i).lAvailability;
                Boolean xl = this.productList.get(i).xlAvailability;
                return new Product(prodID, price, productName, xs, s, m, l, xl);
            }
        }

        return null;
    }
}

