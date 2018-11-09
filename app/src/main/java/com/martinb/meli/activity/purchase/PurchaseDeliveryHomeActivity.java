package com.martinb.meli.activity.purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.martinb.meli.R;
import com.martinb.meli.model.Purchase;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchaseDeliveryHomeActivity extends AppCompatActivity {

    private String ubication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_delivery_home);

        setupToolbar();
        setupDeliveryDestination();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupDeliveryDestination() {
        PlaceAutocompleteFragment placeAutocomplete = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.delivery_direccion);

        placeAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                ubication = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {
                showMessage(status.getStatusMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void continueToPayment(View view) {
        Intent intent = getIntent();
        Purchase purchase = (Purchase) intent.getSerializableExtra(PURCHASE);
        purchase.setDestination_address(ubication);
//        purchase.setDestination_latitude();
//        purchase.setDestination_longitude();
        //Todo: aca deberia llamar al endpoint del server que calcula el costo del viaje
        Float cost = 0.0f;
        purchase.setDelivery_cost(cost);

        Intent i = new Intent(this, PurchasePaymentMethodsActivity.class);
        i.putExtra(PURCHASE, purchase);
        startActivity(i);
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}
