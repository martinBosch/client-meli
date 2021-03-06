package com.martinb.meli.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.activity.ProductDetailsActivity;

public class ProductViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView image;
    public TextView price;
    public TextView title;
    private String _id;

    public static final String ID_PRODUCTO = "id";

    public ProductViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        image = (ImageView) itemView.findViewById(R.id.image);
        price = (TextView) itemView.findViewById(R.id.price);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void setId(String _id) {
        this._id = _id;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ProductDetailsActivity.class);
        intent.putExtra(ID_PRODUCTO, this._id);
        view.getContext().startActivity(intent);
    }
}
