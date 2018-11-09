package com.martinb.meli.activity.purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.model.Purchase;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchaseResumeActivity extends AppCompatActivity {

    private Purchase purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_resume);

        Intent intent = getIntent();
        purchase = (Purchase) intent.getSerializableExtra(PURCHASE);

        setupToolbar();
        setupResumePayment();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupResumePayment() {
        TextView product = findViewById(R.id.product);
        Float price = purchase.getPrice();
        product.setText( String.format("%s", price) );

        TextView amount_text = findViewById(R.id.amount);
        Integer amount = purchase.getAmount();
        amount_text.setText( String.format("%s", amount) );

        TextView subtotal_text = findViewById(R.id.subtotal);
        Float subtotal = amount * price;
        subtotal_text.setText( String.format("%s", subtotal) );

        TextView deliveryCost_text = findViewById(R.id.delivery_cost);
        Float deliveryCost = purchase.getDelivery_cost();
        deliveryCost_text.setText( String.format("%s", deliveryCost) );

        TextView total_text = findViewById(R.id.total);
        Float total = subtotal + deliveryCost;
        total_text.setText( String.format("%s", total) );
    }

    public void confirm_purchase(View view) {
        Toast.makeText(PurchaseResumeActivity.this, "Compraste", Toast.LENGTH_SHORT).show();
    }
}
