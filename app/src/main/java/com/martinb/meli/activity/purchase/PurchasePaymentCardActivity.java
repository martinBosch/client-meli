package com.martinb.meli.activity.purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.martinb.meli.R;
import com.martinb.meli.model.Purchase;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchasePaymentCardActivity extends AppCompatActivity {

    private Purchase purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_payment_card);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void continueToPurchaseResume(View view) {
        Intent intent = getIntent();
        Purchase purchase = (Purchase) intent.getSerializableExtra(PURCHASE);
        loadPurchasePayment(purchase);

        Intent i = new Intent(this, PurchaseResumeActivity.class);
        i.putExtra(PURCHASE, purchase);
        startActivity(i);

//        Toast.makeText(PurchasePaymentCardActivity.this, "Card payment", Toast.LENGTH_SHORT).show();
    }

    private void loadPurchasePayment(Purchase purchase) {
        EditText card_number_text = findViewById(R.id.number_card);
        Integer card_number = Integer.parseInt( card_number_text.getText().toString() );

        EditText card_holder_text = findViewById(R.id.card_holder);
        String card_holder = card_holder_text.getText().toString();

        EditText expiration_date_text = findViewById(R.id.expiration_date);
        String expiration_date = expiration_date_text.getText().toString();

        EditText card_cvc_text = findViewById(R.id.card_cvc);
        Integer card_cvc = Integer.parseInt( card_cvc_text.getText().toString() );

        purchase.setCard_number(card_number);
        purchase.setCard_holder(card_holder);
        purchase.setExpiration_date(expiration_date);
        purchase.setCard_cvc(card_cvc);
    }
}
