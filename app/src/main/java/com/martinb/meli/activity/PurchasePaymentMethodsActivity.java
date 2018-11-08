package com.martinb.meli.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.model.Purchase;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchasePaymentMethodsActivity extends AppCompatActivity {

    private static final String CREDIT = "credit";
    private static final String DEBIT = "debit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_payment_methods);

        setupToolbar();
        setupPaymentMethods();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupPaymentMethods() {
        LinearLayout credit_card = findViewById(R.id.credit_card);
        credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPaymentCardScreen(CREDIT);
//                Toast.makeText(PurchasePaymentMethodsActivity.this, "Credit card", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout debit_card = findViewById(R.id.debit_card);
        debit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPaymentCardScreen(DEBIT);
//                Toast.makeText(PurchasePaymentMethodsActivity.this, "Debit card", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goPaymentCardScreen(String paymentMethod) {
        Intent intent = getIntent();
        Purchase purchase = (Purchase) intent.getSerializableExtra(PURCHASE);
        purchase.setPayment_method(paymentMethod);

        Intent i = new Intent(this, PurchasePaymentCardActivity.class);
        i.putExtra(PURCHASE, purchase);
        startActivity(i);
    }
}
