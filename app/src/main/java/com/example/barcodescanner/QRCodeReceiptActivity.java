package com.example.barcodescanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.graphics.*;

import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.lang.Object;


public class QRCodeReceiptActivity extends AppCompatActivity {

    ArrayList<Product> cartItems;
    ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_receipt);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        ListView listView = findViewById(R.id.checkoutItems);
        adapter = new ArrayAdapter<Product>(getApplicationContext(), android.R.layout.simple_spinner_item, cartItems);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    /*
    public Bitmap generateQRCode () {

    }

    public String generateQRString () {
        String qr = "";
        if (cartItems.size()>0){
            qr = cartItems.get(0).productCode;
        }
        for (int i = 0; i < cartItems.size(); i++) {
            qr += ";" + cartItems.get(i).productCode;
        }
        return qr;
    }
    */
}
