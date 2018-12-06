package com.martinb.meli.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.martinb.meli.R;
import com.martinb.meli.network.object_response.MyActivity;

import java.util.ArrayList;

public class MyActivitiesAdapter extends RecyclerView.Adapter<MyActivitiesViewHolder>
        implements View.OnClickListener  {

    private Context context;
    private ArrayList<MyActivity> myActivities;

    private View.OnClickListener listener;

    public MyActivitiesAdapter(Context context, ArrayList<MyActivity> myPurchases) {
        this.context = context;
        this.myActivities = myPurchases;
    }

    @NonNull
    @Override
    public MyActivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_activity, viewGroup, false);
        layoutView.setOnClickListener(this);
        return new MyActivitiesViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyActivitiesViewHolder holder, int i) {
        MyActivity myPurchase = myActivities.get(i);

        holder.productName.setText(myPurchase.getProductName());
        String price_units = "$ " + Float.toString(myPurchase.getValue())
                + " (" + Integer.toString(myPurchase.getUnits()) + " unidades" + ")";
        holder.price_units.setText(price_units);
        holder.setId(myPurchase.getActivityId());
    }

    @Override
    public int getItemCount() {
        return myActivities.size();
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (this.listener != null) {
            listener.onClick(v);
        }

    }
}
