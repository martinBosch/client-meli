package com.martinb.meli.activity.purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.martinb.meli.R;
import com.martinb.meli.model.Purchase;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchaseShippingMethodsActivity extends AppCompatActivity {

    private Purchase purchase;

    public static final String LOCAL = "local";
    public static final String DELIVERY = "delivery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_shipping_method);

        Intent intent = getIntent();
        purchase = (Purchase) intent.getSerializableExtra(PURCHASE);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void shipping_local(View view) {
        purchase.setShipping_method(LOCAL);
        purchase.setDelivery_cost(0.0f);
        Intent intent = new Intent(this, PurchasePaymentMethodsActivity.class);
        intent.putExtra(PURCHASE, purchase);
        startActivity(intent);
    }

    public void shipping_home(View view) {
        purchase.setShipping_method(DELIVERY);
        Intent intent = new Intent(this, PurchaseDeliveryHomeActivity.class);
        intent.putExtra(PURCHASE, purchase);
        startActivity(intent);
    }
}
