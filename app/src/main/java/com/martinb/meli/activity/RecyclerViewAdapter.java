package com.martinb.meli.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.martinb.meli.R;
import com.martinb.meli.model.Product;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ProductViewHolders> {

    private List<Product> products;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Product> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, null);
        ProductViewHolders productViewHolders = new ProductViewHolders(layoutView);
        return productViewHolders;
    }

    @Override
    public void onBindViewHolder(ProductViewHolders holder, int position) {
        Product product = products.get(position);
        holder.image.setImageResource(product.getImage());
        holder.price.setText(product.getPrice());
        holder.title.setText(product.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }
}
