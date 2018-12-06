package com.martinb.meli.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.martinb.meli.R;

public class MyActivitiesViewHolder extends RecyclerView.ViewHolder {

    public TextView productName;
    public TextView price_units;
    public TextView deliveryStatus;
    public TextView paymentStatus;
    private String _id;

    public MyActivitiesViewHolder(@NonNull View itemView) {
        super(itemView);

        productName = itemView.findViewById(R.id.product_name);
        price_units = itemView.findViewById(R.id.price_units);
        deliveryStatus = itemView.findViewById(R.id.delivery_status);
        paymentStatus = itemView.findViewById(R.id.payment_status);
    }

    public void setId(String _id) {
        this._id = _id;
    }
}
