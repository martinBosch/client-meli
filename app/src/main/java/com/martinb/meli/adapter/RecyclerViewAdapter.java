package com.martinb.meli.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.martinb.meli.R;
import com.martinb.meli.model.ProductItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ProductViewHolders> {

    private List<ProductItem> products;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ProductItem> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null);
        ProductViewHolders productViewHolders = new ProductViewHolders(layoutView);
        return productViewHolders;
    }

    @Override
    public void onBindViewHolder(ProductViewHolders holder, int position) {
        ProductItem product = products.get(position);
//        holder.image.setImageResource(product_item.getEncodedThumbnail());
        holder.image.setImageBitmap(product.getThumbnail());
        holder.price.setText( String.format("$ %s", product.getPrice()) );
        holder.title.setText(product.getTitle());
        holder.setId(product.getId());
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }
}
