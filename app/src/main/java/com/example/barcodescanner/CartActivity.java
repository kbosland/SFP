package com.example.barcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    ArrayList<Product> cartItems = new ArrayList<Product>();
    private ShoppingListAdapter mAdapter;

    private RecyclerView shoppingListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartItems = new ArrayList<Product>(); //getIntent().getParcelableArrayListExtra("cartItems");

        this.shoppingListRecyclerView = findViewById(R.id.shopping_list);
        this.mAdapter = new ShoppingListAdapter();

        this.shoppingListRecyclerView.setHasFixedSize(true);
        this.shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.shoppingListRecyclerView.setAdapter(this.mAdapter);

        final TextView itemTotal  = findViewById(R.id.itemTotal);
        itemTotal.setText("$" + calculateItemTotal());

        TextView hst = findViewById(R.id.hst);
        hst.setText("$" + calculateHST());

        TextView total = findViewById(R.id.finalTotal);
        total.setText("$" + calculateTotal());

        FloatingActionButton camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snapPicture();
                final TextView itemTotal  = findViewById(R.id.itemTotal);
                itemTotal.setText("$" + calculateItemTotal());
                TextView hst = findViewById(R.id.hst);
                hst.setText("$" + calculateHST());
                TextView total = findViewById(R.id.finalTotal);
                total.setText("$" + calculateTotal());
            }
        });

    }

    public void snapPicture () {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            try {
                InputStream is = getAssets().open("products.json");
                ProductJsonManager pjm = new ProductJsonManager("products.json", is);
                Product scannedProd = pjm.findProduct(scanResult.getContents());

                if (!cartItems.contains(scannedProd)) {
                    cartItems.add(scannedProd);
                }

                this.mAdapter.setCheckoutItems(cartItems);
                this.mAdapter.notifyDataSetChanged();


            } catch (IOException ie) {

            }

            //scannedItems.add(new Product(scanResult.getContents()));
        } else {

        }
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
