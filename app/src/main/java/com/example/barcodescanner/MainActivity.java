package com.example.barcodescanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> checkoutItems = new ArrayList<Product>();
    private ShoppingListAdapter mAdapter;

    private RecyclerView shoppingListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.shoppingListRecyclerView = findViewById(R.id.shopping_list);
        this.mAdapter = new ShoppingListAdapter();

        this.shoppingListRecyclerView.setHasFixedSize(true);
        this.shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.shoppingListRecyclerView.setAdapter(this.mAdapter);
        InputStream is = null;
        try {
            is = getAssets().open("products.json");
        } catch (IOException ie) {

        }

        //ProductJsonManager pjm = new ProductJsonManager("products.json", is);
        //this.checkoutItems = (ArrayList<Product>) pjm.getProductList();

        FloatingActionButton fab = findViewById(R.id.cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                i.putParcelableArrayListExtra("cartItems", checkoutItems);
                startActivity(i);
            }
        });

        FloatingActionButton camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snapPicture();
            }
        });



    }

    public String getCart () {
        String cart = "";
        if (checkoutItems.size() > 0) {
            cart = checkoutItems.get(0).productName;
        }

        for (int i = 1; i < checkoutItems.size(); i++) {
            cart += "; " + checkoutItems.get(i).productName;
        }

        return cart;
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

                if (!checkoutItems.contains(scannedProd)) {
                    checkoutItems.add(scannedProd);
                }

                this.mAdapter.setCheckoutItems(checkoutItems);
                this.mAdapter.notifyDataSetChanged();

            } catch (IOException ie) {

            }

            //scannedItems.add(new Product(scanResult.getContents()));
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
