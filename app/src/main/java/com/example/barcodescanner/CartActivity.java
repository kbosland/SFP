package com.example.barcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

public class CartActivity extends AppCompatActivity {

    ArrayList<Product> cartItems;
    ArrayAdapter<Product> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        ListView listView = findViewById(R.id.checkoutItems);
        adapter = new ArrayAdapter<Product>(getApplicationContext(), android.R.layout.simple_spinner_item, cartItems);
        listView.setAdapter(adapter);

        TextView itemTotal  = findViewById(R.id.itemTotal);
        itemTotal.setText("$" + calculateItemTotal());

        TextView hst = findViewById(R.id.hst);
        hst.setText("$" + calculateHST());

        TextView total = findViewById(R.id.finalTotal);
        total.setText("$" + calculateTotal());

    }

    public double calculateItemTotal () {
        double total = 0.0;
        for (int i = 0; i < cartItems.size(); i++) {
            total += cartItems.get(i).price;
        }

        return total;
    }

    public double calculateHST () {
        return 0.13 * calculateItemTotal();
    }

    public double calculateTotal () {
        return calculateItemTotal() + calculateHST();
    }
}
