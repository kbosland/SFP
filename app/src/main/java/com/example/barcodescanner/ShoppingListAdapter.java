package com.example.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingItemViewHolder> {

    ArrayList<Product> checkoutItems = new ArrayList<Product>();

    private ShoppingItemListener listener;

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_item_view, viewGroup, false);
        ShoppingItemViewHolder viewHolder = new ShoppingItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingItemViewHolder shoppingItemViewHolder, int i) {
        final Product prod = checkoutItems.get(i);
        shoppingItemViewHolder.shoppingTitle.setText(prod.productName);
        shoppingItemViewHolder.shoppingPrice.setText(Double.toString(prod.price));
        Glide.with(shoppingItemViewHolder.itemView)
                .load("https://cache.mrporter.com/images/products/978085/978085_mrp_in_l.jpg")
                .apply(new RequestOptions().circleCrop())
                .into(shoppingItemViewHolder.imageView);

        shoppingItemViewHolder.prod = prod;

        shoppingItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    return;
                }

                listener.shoppingItemPressed(shoppingItemViewHolder.prod);
            }
        });
//        try {
//            URL url = new URL("https://cache.mrporter.com/images/products/978085/978085_mrp_in_l.jpg");
//            Bitmap img = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            shoppingItemViewHolder.imageView.setImageBitmap(img);
//
//        } catch (Exception e) {
//
//        }
    }

    public void setListener (ShoppingItemListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return checkoutItems.size();
    }

    public void setCheckoutItems (ArrayList<Product> prods) {
        this.checkoutItems = prods;
    }
}

class ShoppingItemViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView shoppingTitle;
    TextView shoppingPrice;

    Product prod;

    public ShoppingItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.shoppingImage);
        this.shoppingTitle = itemView.findViewById(R.id.shoppingTitle);
        this.shoppingPrice = itemView.findViewById(R.id.shoppingPrice);
    }
}
