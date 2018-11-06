package com.martinb.meli.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.martinb.meli.R;

public class PurchaseShippingMethodsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_method_purchase);

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

    //Todo: Aca deberia llamar a un end point que me diga cuanto sale el costo de envio.
    public void shipping_local(View view) {
        Intent intent = new Intent(this, PurchasePaymentMethodsActivity.class);
        startActivity(intent);

//        Toast.makeText(this, "Retiro en local", Toast.LENGTH_SHORT).show();
    }

    public void shipping_home(View view) {
        Intent intent = new Intent(this, PurchaseDeliveryHomeActivity.class);
        startActivity(intent);

//        Toast.makeText(this, "Envio a domicilio", Toast.LENGTH_SHORT).show();
    }
}
